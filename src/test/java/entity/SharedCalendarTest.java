package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class SharedCalendarTest {

    @Test
    void acceptsImmutableInputsAndStillAddsMembers() {
        final SharedCalendar calendar = new SharedCalendar(1, "Team", "pw", Set.of(1), List.of(5));

        calendar.addMember(2);

        assertTrue(calendar.hasMember(2));
        assertEquals(Set.of(1, 2), calendar.getMemberIds());
    }

    @Test
    void returnedCollectionsAreReadOnly() {
        final SharedCalendar calendar = new SharedCalendar(1, "Team", "pw", Set.of(1), List.of(5));

        assertThrows(UnsupportedOperationException.class, () -> calendar.getMemberIds().add(9));
        assertThrows(UnsupportedOperationException.class, () -> calendar.getEventIDs().add(9));
    }

    @Test
    void removingTheLastMemberLeavesItEmpty() {
        final SharedCalendar calendar = new SharedCalendar(1, "Team", "pw", Set.of(1), List.of());

        calendar.removeMember(1);

        assertTrue(calendar.isMemberEmpty());
        assertFalse(calendar.hasEvent(5));
    }
}
