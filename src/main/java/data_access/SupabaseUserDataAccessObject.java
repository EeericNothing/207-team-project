package data_access;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import use_case.CurrentUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * Keeps accounts in the project's Supabase database.
 *
 * <p>It implements the interfaces the sign-up, log-in and log-out use cases
 * declare, which is exactly what the in-memory version does. Swapping one for the
 * other changes a single line where the application is assembled.</p>
 *
 * <p>Who is currently logged in is held in memory only: a session belongs to this run
 * of the program, not to the stored data.</p>
 */
public class SupabaseUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface, LogoutUserDataAccessInterface, CurrentUserDataAccessInterface {

    private static final String TABLE = "users";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private final SupabaseClient client;
    private final UserFactory userFactory;

    private User currentUser;

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

    /**
     * {@inheritDoc}
     *
     * <p>The identifier is deliberately left out of the row that is sent. The database
     * assigns it, so sending one would either be rejected or disturb the sequence. It
     * is read back out of the stored row instead.</p>
     */
    @Override
    public User save(User user) {
        final JSONObject row = new JSONObject();
        row.put(USERNAME, user.getUsername());
        row.put(EMAIL, user.getEmail());
        row.put(PASSWORD, user.getPassword());

        final JSONArray stored = client.insert(TABLE, row);
        return toUser(stored.getJSONObject(0));
    }

    /**
     * {@inheritDoc}
     *
     * <p>A row without an identifier column still produces a usable user, with the
     * identifier left unset. Logging in asks only for a username and a password, so it
     * keeps working against a table that predates the identifier column.</p>
     */
    @Override
    public User get(String username) {
        final JSONArray rows = client.selectWhereEquals(TABLE, USERNAME, username);

        final User found;
        if (rows.isEmpty()) {
            found = null;
        }
        else {
            found = toUser(rows.getJSONObject(0));
        }
        return found;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Turns a stored row into a user.
     *
     * @param row a row of the users table
     * @return the account that row holds
     */
    private User toUser(JSONObject row) {
        return userFactory.createStored(
                row.optInt(ID, CommonUserFactory.UNSAVED_ID),
                row.getString(USERNAME),
                row.getString(EMAIL),
                row.getString(PASSWORD));
    }
}
