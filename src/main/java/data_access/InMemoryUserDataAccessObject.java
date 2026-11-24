package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * Keeps accounts in memory for the lifetime of the program.
 *
 * <p>Nothing here outlives the program, so this is what the tests run against and what
 * the rest of the team can build on before the Supabase implementation is ready.</p>
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface, LogoutUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;

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
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
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
