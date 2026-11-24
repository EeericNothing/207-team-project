package use_case.create_calendar;

/**
 * Identifies the newly saved calendar so the presenter can open it and show its ID.
 */
public final class CreateCalendarOutputData {

    private final int calendarId;
    private final String calendarName;

    /**
     * Creates the result of a successful create calendar request.
     *
     * @param calendarId the unique ID assigned by storage
     * @param calendarName the name of the saved calendar
     */
    public CreateCalendarOutputData(int calendarId, String calendarName) {
        this.calendarId = calendarId;
        this.calendarName = calendarName;
    }

    /**
     * Returns the ID the creator can share with other users.
     *
     * @return the saved calendar's ID
     */
    public int getCalendarId() {
        return calendarId;
    }

    /**
     * Returns the name to display when opening the new calendar.
     *
     * @return the saved calendar's name
     */
    public String getCalendarName() {
        return calendarName;
    }
}
