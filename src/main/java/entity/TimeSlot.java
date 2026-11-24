package entity;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * A stretch of time with a start and an end.
 */
public class TimeSlot {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates a time slot.
     *
     * @param start when it begins
     * @param end   when it ends; must be after the start
     * @throws IllegalArgumentException if either time is missing or the end is not after the start
     */
    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !end.isAfter(start)) {
            throw new IllegalArgumentException("A time slot needs a start and a later end.");
        }
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }
}
