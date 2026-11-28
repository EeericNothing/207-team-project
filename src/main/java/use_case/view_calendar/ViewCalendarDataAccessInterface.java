package use_case.view_calendar;

import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Event;
import entity.SharedCalendar;

/**
 * What opening a calendar needs from storage, and nothing more.
 *
 * <p>Every method only reads, so viewing a calendar can never change stored data.
 * Any method may throw {@link use_case.DataAccessException} when storage cannot be
 * reached.</p>
 */
public interface ViewCalendarDataAccessInterface {

    /**
     * Returns the calendar with the given id.
     *
     * @param calendarId the calendar's id
     * @return the calendar, or null if there is none with that id
     */
    SharedCalendar getCalendar(int calendarId);

    /**
     * Returns the events with the given ids. Ids with no stored event are left out.
     *
     * @param eventIds the ids to look up
     * @return the events found, in no particular order
     */
    List<Event> getEvents(List<Integer> eventIds);

    /**
     * Returns the usernames of the given users. Ids with no stored user are left out.
     *
     * @param userIds the ids to look up
     * @return each found id mapped to its username
     */
    Map<Integer, String> getUsernames(Set<Integer> userIds);
}
