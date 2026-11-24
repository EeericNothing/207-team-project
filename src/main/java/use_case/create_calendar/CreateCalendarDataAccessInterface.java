package use_case.create_calendar;

import use_case.DataAccessException;

/** Stores new calendars and their initial memberships. */
public interface CreateCalendarDataAccessInterface {

    /**
     * Assigns a unique ID and saves an empty calendar with its creator as the sole member.
     * The calendar and membership must be written together or rolled back together.
     *
     * @param name the validated calendar name
     * @param passwordHash the calendar password hash
     * @param creatorId the stored user ID of the first member
     * @return the assigned calendar ID
     * @throws DataAccessException if storage cannot confirm the save
     */
    int createWithFirstMember(String name, String passwordHash, int creatorId);
}
