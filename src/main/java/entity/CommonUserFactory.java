package entity;

import java.util.ArrayList;

/**
 * Creates ordinary users: accounts with no special rights.
 */
public class CommonUserFactory implements UserFactory {

    /**
     * The identifier a user carries before the database has assigned a real one.
     *
     * <p>Identifiers the database hands out start at one, so zero cannot collide with
     * a stored account.</p>
     */
    public static final int UNSAVED_ID = 0;

    @Override
    public User create(String username, String email, String password) {
        return createStored(UNSAVED_ID, username, email, password);
    }

    @Override
    public User createStored(int id, String username, String email, String password) {
        return new User(id, username, password, email, new ArrayList<>());
    }
}
