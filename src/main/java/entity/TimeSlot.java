package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;

public class TimeSlot {
    private java.time.LocalDateTime start;
    private java.time.LocalDateTime end;

    public TimeSlot(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
}
