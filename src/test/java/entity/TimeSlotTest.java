package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TimeSlotTest {

    private static final LocalDateTime NOON = LocalDateTime.of(2026, 11, 24, 12, 0);

    @Test
    void storesStartEndAndDuration() {
        final TimeSlot slot = new TimeSlot(NOON, NOON.plusMinutes(90));

        assertEquals(NOON, slot.getStart());
        assertEquals(NOON.plusMinutes(90), slot.getEnd());
        assertEquals(Duration.ofMinutes(90), slot.getDuration());
    }

    @Test
    void endMustBeAfterStart() {
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(NOON, NOON));
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(NOON, NOON.minusMinutes(1)));
    }

    @Test
    void missingTimesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(null, NOON));
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(NOON, null));
    }
}
