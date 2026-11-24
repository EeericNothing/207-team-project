package use_case;

import entity.User;

/**
 * Who is using the program right now.
 *
 * <p>Any use case that acts on behalf of the person at the keyboard needs to know who
 * that is, so this interface is shared rather than declared once per use case. It
 * answers with the whole account rather than a username because the entities refer to
 * people by identifier: an event keeps the identifiers of its attendees, and a shared
 * calendar the identifiers of its members. A use case holding only a username would
 * have to ask storage for the account again before it could do anything with either.</p>
 *
 * <p>A session lasts as long as the program runs. Nothing about who is logged in is
 * written down, so closing the program ends the session.</p>
 */
public interface CurrentUserDataAccessInterface {

    /**
     * Returns the account of whoever is logged in.
     *
     * @return the current user, or null if nobody is logged in
     */
    User getCurrentUser();
}
