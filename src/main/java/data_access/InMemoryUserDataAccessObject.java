package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import use_case.CurrentUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * Keeps accounts in memory for the lifetime of the program.
 *
 * <p>Nothing here outlives the program, so this is what the tests run against and what
 * the rest of the team can build on before the Supabase implementation is ready.</p>
 *
 * <p>Identifiers are handed out here the way the database hands them out, starting at
 * one and rising. That matters: the entities refer to people by identifier, so a store
 * that gave every account the same identifier would pass its own tests and then fail
 * against the real database.</p>
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface, LogoutUserDataAccessInterface, CurrentUserDataAccessInterface {

    private static final int FIRST_ID = 1;

    private final Map<String, User> users = new HashMap<>();
    private final UserFactory userFactory;

    private int nextId = FIRST_ID;
    private User currentUser;

    /**
     * Creates an empty store.
     */
    public InMemoryUserDataAccessObject() {
        this(new CommonUserFactory());
    }

    /**
     * Creates an empty store that builds its users with the given factory.
     *
     * @param userFactory how a stored account becomes a user
     */
    public InMemoryUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        boolean found = false;
        for (User user : users.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public User save(User user) {
        final User stored = userFactory.createStored(
                nextId, user.getUsername(), user.getEmail(), user.getPassword());
        nextId++;

        users.put(stored.getUsername(), stored);
        return stored;
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
