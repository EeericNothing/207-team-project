package data_access;

import use_case.PasswordHasher;

/**
 * Stores passwords exactly as the user typed them.
 *
 * <p>This is what the team agreed on for now. It is deliberately the only place in
 * the program that decides how a password is stored, so replacing it with an
 * implementation that hashes is a one-line change where the application is assembled.</p>
 */
public class PlainTextPasswordHasher implements PasswordHasher {

    @Override
    public String hash(String rawPassword) {
        return rawPassword;
    }
}
