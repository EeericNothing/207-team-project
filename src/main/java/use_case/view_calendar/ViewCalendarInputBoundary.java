package use_case.view_calendar;

/**
 * Opens a shared calendar (Use Case 8, Calendar View).
 *
 * <p>The controller calls this. Every teammate whose use case changes an event can also
 * call it again to refresh the calendar on screen.</p>
 */
public interface ViewCalendarInputBoundary {

    /**
     * Loads the calendar and hands the result to the output boundary.
     *
     * @param inputData which calendar to open
     */
    void execute(ViewCalendarInputData inputData);
}
