package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * The settings the program reads at startup from config.properties.
 *
 * <p>That file is not in the repository, because it holds the key to the team's
 * Supabase project. When it is missing the program still runs, against storage that
 * lasts only as long as the program does, so a teammate without the key can work.</p>
 */
public final class AppConfig {

    private static final Path CONFIG_FILE = Paths.get("config.properties");
    private static final String URL_KEY = "supabase.url";
    private static final String API_KEY = "supabase.key";

    private final String supabaseUrl;
    private final String supabaseKey;

    private AppConfig(String supabaseUrl, String supabaseKey) {
        this.supabaseUrl = supabaseUrl;
        this.supabaseKey = supabaseKey;
    }

    /**
     * Reads config.properties if it is there.
     *
     * @return the settings, which may say that Supabase is not configured
     */
    public static AppConfig load() {
        AppConfig config = new AppConfig(null, null);
        if (Files.exists(CONFIG_FILE)) {
            final Properties properties = new Properties();
            try (InputStream input = new FileInputStream(CONFIG_FILE.toFile())) {
                properties.load(input);
                config = new AppConfig(properties.getProperty(URL_KEY), properties.getProperty(API_KEY));
            }
            catch (IOException exception) {
                throw new IllegalStateException("config.properties exists but could not be read.", exception);
            }
        }
        return config;
    }

    /**
     * Says whether both Supabase settings are present.
     *
     * @return true if the program can talk to Supabase
     */
    public boolean hasSupabase() {
        return isFilledIn(supabaseUrl) && isFilledIn(supabaseKey);
    }

    public String getSupabaseUrl() {
        return supabaseUrl;
    }

    public String getSupabaseKey() {
        return supabaseKey;
    }

    private static boolean isFilledIn(String value) {
        return value != null && !value.trim().isEmpty() && !value.startsWith("YOUR-");
    }
}
