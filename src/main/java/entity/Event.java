package entity;

import java.util.ArrayList;
import java.util.Set;

public class Event {
    private final int id;
    private String eventName;
    private String eventDescription;
    private TimeSlot time;
    private final User author;
    private Set<Integer> attendeeIDs;

    public Event(int id, String eventName, String eventDescription, TimeSlot time,
                 User author, Set<Integer> attendees) {
        this.id = id;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.time = time;
        this.author = author;
        this.attendeeIDs = attendees;
    }

    public int getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public TimeSlot getTime() {
        return time;
    }

    public boolean isAuthor(User author) {
        return this.author.getId() == author.getId();
    }

    public Set<Integer> getAttendeeIDs() {
        return attendeeIDs;
    }

}
