package use_case.view_calendar;

import java.time.LocalDateTime;

/**
 * The part of an event that the calendar screen shows: enough to draw it on the grid
 * and to open it in View Event when clicked.
 *
 * <p>A plain data object, so that presenters and views never touch the {@code Event}
 * entity.</p>
 */
public class EventSummary {

    private final int eventId;
    private final String name;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates the summary.
     *
     * @param eventId the event's id
     * @param name    the event's name
     * @param start   when the event starts
     * @param end     when the event ends
     */
    public EventSummary(int eventId, String name, LocalDateTime start, LocalDateTime end) {
        this.eventId = eventId;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public int getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
