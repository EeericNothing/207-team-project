package data_access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.CommonUserFactory;
import entity.Event;
import entity.SharedCalendar;
import entity.TimeSlot;
import entity.User;

class InMemoryCalendarDataAccessObjectTest {

    private final InMemoryCalendarDataAccessObject store = new InMemoryCalendarDataAccessObject();
    private final User author = new CommonUserFactory().createStored(1, "alice", "alice@example.com", "pw");

    @Test
    void returnsTheSavedCalendarOrNull() {
        final SharedCalendar calendar = new SharedCalendar(5, "Team", "pw", Set.of(1), List.of());
        store.saveCalendar(calendar);

        assertSame(calendar, store.getCalendar(5));
        assertNull(store.getCalendar(6));
    }

    @Test
    void getEventsLeavesOutUnknownIds() {
        final LocalDateTime start = LocalDateTime.of(2026, 11, 24, 10, 0);
        final Event event = new Event(3, "Demo", "", new TimeSlot(start, start.plusHours(1)), author);
        store.saveEvent(event);

        assertEquals(List.of(event), store.getEvents(List.of(3, 4)));
    }

    @Test
    void getUsernamesLeavesOutUnknownIds() {
        store.saveUsername(1, "alice");
        store.saveUsername(2, "bob");

        assertEquals(Map.of(1, "alice"), store.getUsernames(Set.of(1, 9)));
    }
}
