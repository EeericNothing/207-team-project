package entity;

/**
 * A person with an account in the application.
 *
 * <p>A user is identified by their username, which is unique across the application.
 * The email address is the one the application writes to when an event the user joined
 * changes or is cancelled.</p>
 */
public interface User {

    /**
     * Returns the username that identifies this user.
     *
     * @return the username
     */
    String getName();

    /**
     * Returns the email address this user can be reached at.
     *
     * @return the email address
     */
    String getEmail();

    /**
     * Returns the password that lets this user log in.
     *
     * @return the password, as it is stored
     */
    String getPassword();
}
