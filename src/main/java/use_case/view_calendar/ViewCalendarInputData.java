package use_case.view_calendar;

/**
 * What the user asked for: which calendar to open.
 *
 * <p>Who is asking is not part of the input. The interactor reads it from the shared
 * session, so a caller cannot open a calendar on someone else's behalf.</p>
 */
public class ViewCalendarInputData {

    private final int calendarId;

    /**
     * Creates the input.
     *
     * @param calendarId the id of the calendar to open
     */
    public ViewCalendarInputData(int calendarId) {
        this.calendarId = calendarId;
    }

    public int getCalendarId() {
        return calendarId;
    }
}
