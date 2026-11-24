package use_case.create_calendar;

/**
 * Accepts requests to create a shared calendar for the current user.
 */
public interface CreateCalendarInputBoundary {

    /**
     * Processes a create calendar request and reports the outcome through the output boundary.
     *
     * @param input the submitted calendar name and password
     */
    void execute(CreateCalendarInputData input);
}
