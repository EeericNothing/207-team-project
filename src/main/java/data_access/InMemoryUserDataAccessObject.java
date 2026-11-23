package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.User;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * Keeps accounts in memory for the lifetime of the program.
 *
 * <p>Nothing here outlives the program, so this is what the tests run against and what
 * the rest of the team can build on before the Supabase implementation is ready.</p>
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

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
        users.put(user.getName(), user);
    }
}
