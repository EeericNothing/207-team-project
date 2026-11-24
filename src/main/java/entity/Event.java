package entity;

import java.util.ArrayList;

public class Event {
    private final int id;
    private String eventName;
    private String eventDescription;
    private TimeSlot time;
    private final User author;
    private final ArrayList<Integer> attendeeIDs;

    public Event(int id, String eventName, String eventDescription, User author, ArrayList<Integer> attendees) {
        this.id = id;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
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

    public ArrayList<Integer> getAttendeeIDs() {
        return attendeeIDs;
    }

}
