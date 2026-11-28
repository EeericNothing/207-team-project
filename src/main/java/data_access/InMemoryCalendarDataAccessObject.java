package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Event;
import entity.SharedCalendar;
import use_case.view_calendar.ViewCalendarDataAccessInterface;

/**
 * Keeps calendars, events and usernames in memory for the lifetime of the program.
 *
 * <p>The Calendar View tests run against this, and the app can use it with seed data
 * to demo the calendar screen before the Supabase version exists. The two are meant to
 * be interchangeable: an interactor cannot tell which one it was given.</p>
 */
public class InMemoryCalendarDataAccessObject implements ViewCalendarDataAccessInterface {

    private final Map<Integer, SharedCalendar> calendars = new HashMap<>();
    private final Map<Integer, Event> events = new HashMap<>();
    private final Map<Integer, String> usernames = new HashMap<>();

    /**
     * Stores a calendar, replacing any with the same id.
     *
     * @param calendar the calendar to store
     */
    public void saveCalendar(SharedCalendar calendar) {
        calendars.put(calendar.getCalendarId(), calendar);
    }

    /**
     * Stores an event, replacing any with the same id.
     *
     * @param event the event to store
     */
    public void saveEvent(Event event) {
        events.put(event.getId(), event);
    }

    /**
     * Records a user's username, so that member lists can show names.
     *
     * @param userId   the user's id
     * @param username the user's username
     */
    public void saveUsername(int userId, String username) {
        usernames.put(userId, username);
    }

    @Override
    public SharedCalendar getCalendar(int calendarId) {
        return calendars.get(calendarId);
    }

    @Override
    public List<Event> getEvents(List<Integer> eventIds) {
        final List<Event> found = new ArrayList<>();
        for (Integer eventId : eventIds) {
            final Event event = events.get(eventId);
            if (event != null) {
                found.add(event);
            }
        }
        return found;
    }

    @Override
    public Map<Integer, String> getUsernames(Set<Integer> userIds) {
        final Map<Integer, String> found = new HashMap<>();
        for (Integer userId : userIds) {
            final String username = usernames.get(userId);
            if (username != null) {
                found.put(userId, username);
            }
        }
        return found;
    }
}
