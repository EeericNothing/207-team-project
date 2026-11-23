package entity;

/**
 * The ordinary kind of user, with no special rights.
 */
public class CommonUser implements User {

    private final String name;
    private final String email;
    private final String password;

    /**
     * Creates a user.
     *
     * @param name     the username, which identifies the user
     * @param email    the address the user can be reached at
     * @param password the password, as it is stored
     */
    public CommonUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
