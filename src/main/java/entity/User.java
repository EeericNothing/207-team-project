package entity;

import java.util.ArrayList;

public class User {
    private final int id;
    private final String username;
    private final String password;
    private final String email;
    private final ArrayList<Event> attendingEvents;
    private ArrayList<TimeSlot> availability;
    private ArrayList<SharedCalendar> calendars;

    public User(int id, String username, String password, String email, ArrayList<Event> attendingEvents) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.attendingEvents = attendingEvents;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public Boolean isAttendingEvents(Event event) {
        for (Event e : attendingEvents) {
            if (e.getId() == event.getId()) {
                return true;
            }
        }
        return false;
    }
}
