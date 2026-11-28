package use_case.view_calendar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import entity.Event;
import entity.SharedCalendar;
import entity.User;
import use_case.CurrentUserDataAccessInterface;
import use_case.DataAccessException;
import use_case.NoCurrentUserException;

/**
 * Opens a shared calendar for the logged-in user (Use Case 8, Calendar View).
 *
 * <p>Loads the calendar, checks that the user is a member, then loads its events and
 * its members' names. It depends only on interfaces, so the same logic runs against
 * the in-memory store in tests and against Supabase in the app.</p>
 */
public class ViewCalendarInteractor implements ViewCalendarInputBoundary {

    static final String NOT_FOUND_MESSAGE = "That calendar does not exist.";
    static final String NOT_A_MEMBER_MESSAGE = "You are not a member of this calendar.";
    static final String COULD_NOT_LOAD_MESSAGE = "Could not load the calendar. Please try again.";

    private final ViewCalendarDataAccessInterface calendarDataAccess;
    private final CurrentUserDataAccessInterface currentUserDataAccess;
    private final ViewCalendarOutputBoundary presenter;

    /**
     * Creates the interactor.
     *
     * @param calendarDataAccess    where calendars, events and usernames are read from
     * @param currentUserDataAccess who is logged in
     * @param presenter             where the result goes
     */
    public ViewCalendarInteractor(ViewCalendarDataAccessInterface calendarDataAccess,
                                  CurrentUserDataAccessInterface currentUserDataAccess,
                                  ViewCalendarOutputBoundary presenter) {
        this.calendarDataAccess = calendarDataAccess;
        this.currentUserDataAccess = currentUserDataAccess;
        this.presenter = presenter;
    }

    /**
     * Opens the calendar.
     *
     * @param inputData which calendar to open
     * @throws NoCurrentUserException if nobody is logged in, which means the screens were wired wrongly
     */
    @Override
    public void execute(ViewCalendarInputData inputData) {
        final User currentUser = currentUserDataAccess.getCurrentUser();
        if (currentUser == null) {
            throw new NoCurrentUserException();
        }

        try {
            final SharedCalendar calendar = calendarDataAccess.getCalendar(inputData.getCalendarId());
            if (calendar == null) {
                presenter.prepareFailView(NOT_FOUND_MESSAGE);
            }
            else if (!calendar.hasMember(currentUser.getId())) {
                presenter.prepareFailView(NOT_A_MEMBER_MESSAGE);
            }
            else {
                presenter.prepareSuccessView(buildOutput(calendar));
            }
        }
        catch (DataAccessException exception) {
            presenter.prepareFailView(COULD_NOT_LOAD_MESSAGE);
        }
    }

    private ViewCalendarOutputData buildOutput(SharedCalendar calendar) {
        final List<EventSummary> events = new ArrayList<>();
        for (Event event : calendarDataAccess.getEvents(calendar.getEventIDs())) {
            events.add(new EventSummary(event.getId(), event.getEventName(),
                    event.getTime().getStart(), event.getTime().getEnd()));
        }
        events.sort(Comparator.comparing(EventSummary::getStart)
                .thenComparing(EventSummary::getEnd)
                .thenComparingInt(EventSummary::getEventId));

        final Map<Integer, String> usernames = calendarDataAccess.getUsernames(calendar.getMemberIds());
        final List<String> memberNames = new ArrayList<>(usernames.values());
        memberNames.sort(String.CASE_INSENSITIVE_ORDER);

        return new ViewCalendarOutputData(calendar.getCalendarId(), calendar.getCalendarName(),
                events, memberNames);
    }
}
