package entity;

import java.util.ArrayList;

public class Event {
    private final int id;
    private String eventName;
    private String eventDescription;
    private TimeSlot time;
    private final User author;
    private final ArrayList<Integer> attendees;

    public Event(int id, String eventName, String eventDescription, User author, ArrayList<Integer> attendees) {
        this.id = id;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.author = author;
        this.attendees = attendees;
    }
}
