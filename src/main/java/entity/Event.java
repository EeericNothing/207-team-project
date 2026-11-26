package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Event {
    private final int id;
    private String eventName;
    private String eventDescription;
    private TimeSlot time;
    private final int authorID;
    private final Set<Integer> attendeeIDs;

    public Event(int id, String eventName, String eventDescription, TimeSlot time,
                 User author) {
        this.id = id;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.time = time;
        this.authorID = author.getId();
        this.attendeeIDs = new HashSet<Integer>();
        this.attendeeIDs.add(author.getId());
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
        return authorID == author.getId();
    }

    public Set<Integer> getAttendeeIDs() {
        return attendeeIDs;
    }

    public boolean isAttending(User user) {
        return attendeeIDs.contains(user.getId());
    }

    public void joinEvent(User attendee) {
        this.attendeeIDs.add(attendee.getId());
    }

    public void leaveEvent(User attendee) {
        this.attendeeIDs.remove(attendee.getId());
    }

    public void modifyEvent(String name, String description, TimeSlot time) {
        this.eventName = name;
        this.eventDescription = description;
        this.time = time;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Event && ((Event) other).id == id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
