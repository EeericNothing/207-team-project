package use_case.view_calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data_access.InMemoryCalendarDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.Event;
import entity.SharedCalendar;
import entity.TimeSlot;
import entity.User;
import use_case.DataAccessException;
import use_case.NoCurrentUserException;

/**
 * Tests the rules of Use Case 8, Calendar View.
 */
class ViewCalendarInteractorTest {

    private static final int CALENDAR_ID = 10;
    private static final int ALICE_ID = 1;
    private static final int BOB_ID = 2;
    private static final int OUTSIDER_ID = 3;
    private static final LocalDateTime MONDAY_9AM = LocalDateTime.of(2026, 11, 23, 9, 0);

    private final CommonUserFactory userFactory = new CommonUserFactory();
    private InMemoryCalendarDataAccessObject calendars;
    private InMemoryUserDataAccessObject session;
    private RecordingPresenter presenter;
    private User alice;

    @BeforeEach
    void setUp() {
        calendars = new InMemoryCalendarDataAccessObject();
        session = new InMemoryUserDataAccessObject();
        presenter = new RecordingPresenter();
        alice = userFactory.createStored(ALICE_ID, "alice", "alice@example.com", "pw");
        calendars.saveUsername(ALICE_ID, "alice");
        calendars.saveUsername(BOB_ID, "Bob");
        session.setCurrentUser(alice);
    }

    @Test
    void successShowsTheCalendarsNameMembersAndEvents() {
        calendars.saveEvent(event(100, "Standup", MONDAY_9AM, 1));
        saveCalendar(Set.of(ALICE_ID, BOB_ID), List.of(100));

        open(calendars);

        final ViewCalendarOutputData output = presenter.success;
        assertNull(presenter.failure);
        assertEquals(CALENDAR_ID, output.getCalendarId());
        assertEquals("CSC207 team", output.getCalendarName());
        assertEquals(List.of("alice", "Bob"), output.getMemberNames());
        assertEquals(1, output.getEvents().size());
        final EventSummary standup = output.getEvents().get(0);
        assertEquals(100, standup.getEventId());
        assertEquals("Standup", standup.getName());
        assertEquals(MONDAY_9AM, standup.getStart());
        assertEquals(MONDAY_9AM.plusHours(1), standup.getEnd());
    }

    @Test
    void eventsComeBackSortedByStartTime() {
        calendars.saveEvent(event(1, "Wednesday", MONDAY_9AM.plusDays(2), 1));
        calendars.saveEvent(event(2, "Monday", MONDAY_9AM, 1));
        calendars.saveEvent(event(3, "Tuesday", MONDAY_9AM.plusDays(1), 1));
        saveCalendar(Set.of(ALICE_ID), List.of(1, 2, 3));

        open(calendars);

        assertEquals(List.of("Monday", "Tuesday", "Wednesday"), names(presenter.success.getEvents()));
    }

    @Test
    void eventsStartingTogetherAreOrderedByEndThenId() {
        calendars.saveEvent(event(7, "Long", MONDAY_9AM, 2));
        calendars.saveEvent(event(6, "Short B", MONDAY_9AM, 1));
        calendars.saveEvent(event(5, "Short A", MONDAY_9AM, 1));
        saveCalendar(Set.of(ALICE_ID), List.of(7, 6, 5));

        open(calendars);

        assertEquals(List.of("Short A", "Short B", "Long"), names(presenter.success.getEvents()));
    }

    @Test
    void calendarWithNoEventsGivesAnEmptyListNotAFailure() {
        saveCalendar(Set.of(ALICE_ID), List.of());

        open(calendars);

        assertNull(presenter.failure);
        assertTrue(presenter.success.getEvents().isEmpty());
    }

    @Test
    void eventIdsWithNoStoredEventAreLeftOut() {
        calendars.saveEvent(event(1, "Kept", MONDAY_9AM, 1));
        saveCalendar(Set.of(ALICE_ID), List.of(1, 999));

        open(calendars);

        assertEquals(List.of("Kept"), names(presenter.success.getEvents()));
    }

    @Test
    void userWhoIsNotAMemberIsTurnedAway() {
        saveCalendar(Set.of(BOB_ID), List.of());
        session.setCurrentUser(userFactory.createStored(OUTSIDER_ID, "outsider", "o@example.com", "pw"));

        open(calendars);

        assertNull(presenter.success);
        assertEquals(ViewCalendarInteractor.NOT_A_MEMBER_MESSAGE, presenter.failure);
    }

    @Test
    void calendarThatDoesNotExistFails() {
        open(calendars);

        assertNull(presenter.success);
        assertEquals(ViewCalendarInteractor.NOT_FOUND_MESSAGE, presenter.failure);
    }

    @Test
    void storageThatCannotBeReachedFailsWithCouldNotLoad() {
        open(new UnreachableStorage());

        assertNull(presenter.success);
        assertEquals(ViewCalendarInteractor.COULD_NOT_LOAD_MESSAGE, presenter.failure);
    }

    @Test
    void storageFailingWhileLoadingEventsAlsoFailsWithCouldNotLoad() {
        saveCalendar(Set.of(ALICE_ID), List.of(1));
        final ViewCalendarDataAccessInterface failsOnEvents = new InMemoryCalendarDataAccessObject() {
            @Override
            public SharedCalendar getCalendar(int calendarId) {
                return calendars.getCalendar(calendarId);
            }

            @Override
            public List<Event> getEvents(List<Integer> eventIds) {
                throw new DataAccessException("events table is down");
            }
        };

        open(failsOnEvents);

        assertNull(presenter.success);
        assertEquals(ViewCalendarInteractor.COULD_NOT_LOAD_MESSAGE, presenter.failure);
    }

    @Test
    void nobodyLoggedInIsAWiringBug() {
        saveCalendar(Set.of(ALICE_ID), List.of());
        session.setCurrentUser(null);

        assertThrows(NoCurrentUserException.class, () -> open(calendars));
        assertFalse(presenter.called());
    }

    private void open(ViewCalendarDataAccessInterface storage) {
        new ViewCalendarInteractor(storage, session, presenter).execute(new ViewCalendarInputData(CALENDAR_ID));
    }

    private void saveCalendar(Set<Integer> members, List<Integer> eventIds) {
        calendars.saveCalendar(new SharedCalendar(CALENDAR_ID, "CSC207 team", "secret", members, eventIds));
    }

    private Event event(int id, String name, LocalDateTime start, int hours) {
        return new Event(id, name, "", new TimeSlot(start, start.plusHours(hours)), alice);
    }

    private static List<String> names(List<EventSummary> events) {
        final List<String> names = new ArrayList<>();
        for (EventSummary event : events) {
            names.add(event.getName());
        }
        return names;
    }

    /**
     * Storage that is never reachable.
     */
    private static final class UnreachableStorage implements ViewCalendarDataAccessInterface {

        @Override
        public SharedCalendar getCalendar(int calendarId) {
            throw new DataAccessException("Supabase is down");
        }

        @Override
        public List<Event> getEvents(List<Integer> eventIds) {
            throw new DataAccessException("Supabase is down");
        }

        @Override
        public Map<Integer, String> getUsernames(Set<Integer> userIds) {
            throw new DataAccessException("Supabase is down");
        }
    }

    /**
     * A presenter that remembers what it was told, so a test can check it.
     */
    private static final class RecordingPresenter implements ViewCalendarOutputBoundary {

        private ViewCalendarOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(ViewCalendarOutputData outputData) {
            this.success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failure = errorMessage;
        }

        boolean called() {
            return success != null || failure != null;
        }
    }
}
