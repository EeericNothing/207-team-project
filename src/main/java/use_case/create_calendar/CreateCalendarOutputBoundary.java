package use_case.create_calendar;

/**
 * Presents the outcome of a request to create a shared calendar.
 */
public interface CreateCalendarOutputBoundary {

    /**
     * Opens the newly saved calendar and displays its shareable ID.
     *
     * @param output the saved calendar's ID and name
     */
    void prepareSuccessView(CreateCalendarOutputData output);

    /**
     * Shows a validation or storage failure on the create calendar form.
     *
     * @param message the message explaining what happened
     */
    void prepareFailView(String message);
}
