package data_access;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.DataAccessException;

/**
 * Talks to one Supabase project over its REST interface.
 *
 * <p>Everything this program knows about HTTP, JSON bodies and API keys is here. A
 * data access object asks for rows and sends rows; it never builds a request. Nothing
 * above the data access layer knows this class exists.</p>
 */
public class SupabaseClient {

    private static final MediaType JSON_TYPE = MediaType.parse("application/json");
    private static final String REST_PATH = "rest/v1";
    private static final int FIRST_ERROR_CODE = 300;

    private final OkHttpClient httpClient = new OkHttpClient();
    private final String baseUrl;
    private final String apiKey;

    /**
     * Creates a client for one project.
     *
     * @param baseUrl the project URL, for example https://abcdef.supabase.co
     * @param apiKey  the publishable key for that project
     */
    public SupabaseClient(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    /**
     * Returns the rows of a table whose column equals the given value.
     *
     * @param table  the table to read
     * @param column the column to match on
     * @param value  the value it must equal
     * @return the matching rows, which may be empty
     */
    public JSONArray selectWhereEquals(String table, String column, String value) {
        final HttpUrl url = tableUrl(table)
                .addQueryParameter(column, "eq." + value)
                .addQueryParameter("select", "*")
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .header("apikey", apiKey)
                .build();

        return readArray(request);
    }

    /**
     * Adds a row to a table and returns it as it was stored.
     *
     * <p>The stored row is worth having back, because it carries what the database
     * filled in rather than the caller: the identifier above all. Asking for it is
     * what the Prefer header does; without it the server answers with an empty body
     * and the identifier it just assigned is lost.</p>
     *
     * @param table the table to write to
     * @param row   the row to add
     * @return the row as stored, including the columns the database filled in
     */
    public JSONArray insert(String table, JSONObject row) {
        final Request request = new Request.Builder()
                .url(tableUrl(table).build())
                .post(RequestBody.create(row.toString(), JSON_TYPE))
                .header("apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Prefer", "return=representation")
                .build();

        return readArray(request);
    }

    private HttpUrl.Builder tableUrl(String table) {
        final HttpUrl parsed = HttpUrl.parse(baseUrl);
        if (parsed == null) {
            throw new DataAccessException("The Supabase URL in config.properties is not a URL: " + baseUrl);
        }
        return parsed.newBuilder().addPathSegments(REST_PATH).addPathSegment(table);
    }

    /**
     * Sends a request and returns its body, failing if the server refused it.
     *
     * @param request the request to send
     * @return the body of the response
     */
    private String send(Request request) {
        try (Response response = httpClient.newCall(request).execute()) {
            final String body;
            if (response.body() == null) {
                body = "";
            }
            else {
                body = response.body().string();
            }

            if (response.code() >= FIRST_ERROR_CODE) {
                throw new DataAccessException(
                        "Supabase answered " + response.code() + " for " + request.method()
                                + " " + request.url().encodedPath() + ": " + body);
            }
            return body;
        }
        catch (IOException exception) {
            throw new DataAccessException("Supabase could not be reached.", exception);
        }
    }

    private JSONArray readArray(Request request) {
        final String body = send(request);
        try {
            return new JSONArray(body);
        }
        catch (JSONException exception) {
            throw new DataAccessException("Supabase answered with something that is not a list of rows.", exception);
        }
    }
}
