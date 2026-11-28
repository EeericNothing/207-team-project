package use_case.view_calendar;

/**
 * Where the Calendar View interactor sends its result. The presenter implements this.
 */
public interface ViewCalendarOutputBoundary {

    /**
     * Shows the opened calendar.
     *
     * @param outputData the calendar's name, events and members
     */
    void prepareSuccessView(ViewCalendarOutputData outputData);

    /**
     * Reports why the calendar could not be opened. The user stays where they were.
     *
     * @param errorMessage what to tell the user
     */
    void prepareFailView(String errorMessage);
}
