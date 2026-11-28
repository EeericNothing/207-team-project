package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class EventTest {

    private static final LocalDateTime START = LocalDateTime.of(2026, 11, 24, 9, 0);

    private final CommonUserFactory users = new CommonUserFactory();
    private final User author = users.createStored(1, "alice", "alice@example.com", "pw");
    private final User bob = users.createStored(2, "bob", "bob@example.com", "pw");
    private final TimeSlot slot = new TimeSlot(START, START.plusHours(1));

    @Test
    void getTimeReturnsTheTimeItWasCreatedWith() {
        final Event event = new Event(1, "Standup", "Daily", slot, author);

        assertSame(slot, event.getTime());
    }

    @Test
    void theAuthorAttendsFromTheStart() {
        final Event event = new Event(1, "Standup", "", slot, author);

        assertTrue(event.isAuthor(author));
        assertFalse(event.isAuthor(bob));
        assertTrue(event.isAttending(author));
        assertEquals(Set.of(1), event.getAttendeeIDs());
    }

    @Test
    void joiningAndLeavingChangesWhoIsAttending() {
        final Event event = new Event(1, "Standup", "", slot, author);

        event.joinEvent(bob);
        assertTrue(event.isAttending(bob));

        event.leaveEvent(bob);
        assertFalse(event.isAttending(bob));
    }

    @Test
    void modifyEventReplacesNameDescriptionAndTime() {
        final Event event = new Event(1, "Standup", "Daily", slot, author);
        final TimeSlot later = new TimeSlot(START.plusDays(1), START.plusDays(1).plusHours(2));

        event.modifyEvent("Retro", "Weekly", later);

        assertEquals("Retro", event.getEventName());
        assertEquals("Weekly", event.getEventDescription());
        assertSame(later, event.getTime());
    }

    @Test
    void eventsAreEqualWhenTheirIdsMatch() {
        final Event original = new Event(1, "Standup", "", slot, author);
        final Event copyFromStorage = new Event(1, "Standup", "", slot, author);
        final Event other = new Event(2, "Standup", "", slot, author);

        assertEquals(original, copyFromStorage);
        assertEquals(original.hashCode(), copyFromStorage.hashCode());
        assertNotEquals(original, other);
    }

    @Test
    void userIsAttendingWhenTheEventListsThem() {
        final Event event = new Event(1, "Standup", "", slot, author);

        // User.attendingEvents is filled by whoever stores the user; here it holds this event.
        final User attendee = new User(1, "alice", "pw", "alice@example.com", new ArrayList<>(List.of(event)));

        assertTrue(attendee.isAttendingEvents(event));
        assertFalse(bob.isAttendingEvents(event));
    }
}
