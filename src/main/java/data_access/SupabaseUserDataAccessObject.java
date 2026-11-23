package data_access;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * Keeps accounts in the project's Supabase database.
 *
 * <p>It implements the three interfaces the sign-up, log-in and log-out use cases
 * declare, which is exactly what the in-memory version does. Swapping one for the
 * other changes a single line where the application is assembled.</p>
 *
 * <p>Who is currently logged in is held in memory only: a session belongs to this run
 * of the program, not to the stored data.</p>
 */
public class SupabaseUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface, LogoutUserDataAccessInterface {

    private static final String TABLE = "users";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private final SupabaseClient client;
    private final UserFactory userFactory;

    private String currentUsername;

    /**
     * Creates the data access object.
     *
     * @param client      the project to talk to
     * @param userFactory how a stored row becomes a user
     */
    public SupabaseUserDataAccessObject(SupabaseClient client, UserFactory userFactory) {
        this.client = client;
        this.userFactory = userFactory;
    }

    @Override
    public boolean existsByName(String username) {
        return !client.selectWhereEquals(TABLE, USERNAME, username).isEmpty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return !client.selectWhereEquals(TABLE, EMAIL, email).isEmpty();
    }

    @Override
    public void save(User user) {
        final JSONObject row = new JSONObject();
        row.put(USERNAME, user.getName());
        row.put(EMAIL, user.getEmail());
        row.put(PASSWORD, user.getPassword());

        client.insert(TABLE, row);
    }

    @Override
    public User get(String username) {
        final JSONArray rows = client.selectWhereEquals(TABLE, USERNAME, username);

        final User found;
        if (rows.isEmpty()) {
            found = null;
        }
        else {
            final JSONObject row = rows.getJSONObject(0);
            found = userFactory.create(row.getString(USERNAME), row.getString(EMAIL), row.getString(PASSWORD));
        }
        return found;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }
}
