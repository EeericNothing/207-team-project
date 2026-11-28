package use_case.view_calendar;

import java.util.List;

/**
 * Everything the calendar screen needs once a calendar has been opened.
 *
 * <p>Events are sorted by start time and member names alphabetically, so the presenter
 * only formats them and never has to reorder them.</p>
 */
public class ViewCalendarOutputData {

    private final int calendarId;
    private final String calendarName;
    private final List<EventSummary> events;
    private final List<String> memberNames;

    /**
     * Creates the output.
     *
     * @param calendarId   the calendar's id, shown so members can share it
     * @param calendarName the calendar's name
     * @param events       the calendar's events, sorted by start time
     * @param memberNames  the members' usernames, sorted alphabetically
     */
    public ViewCalendarOutputData(int calendarId, String calendarName, List<EventSummary> events,
                                  List<String> memberNames) {
        this.calendarId = calendarId;
        this.calendarName = calendarName;
        this.events = List.copyOf(events);
        this.memberNames = List.copyOf(memberNames);
    }

    public int getCalendarId() {
        return calendarId;
    }

    public String getCalendarName() {
        return calendarName;
    }

    /**
     * Returns the events.
     *
     * @return a read-only list sorted by start time
     */
    public List<EventSummary> getEvents() {
        return events;
    }

    /**
     * Returns the members' usernames.
     *
     * @return a read-only list sorted alphabetically
     */
    public List<String> getMemberNames() {
        return memberNames;
    }
}
