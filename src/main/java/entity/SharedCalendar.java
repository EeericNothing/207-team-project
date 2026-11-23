package entity;

import java.util.ArrayList;
import java.util.Set;

public class SharedCalendar {
    private final int calendarId;
    private final String calendarName;
    private final String calendarPassword;
    private final Set<Integer> memberIds;
    private final ArrayList<Integer> events;

    public SharedCalendar(int calendarId, String calendarName, String calendarPassword, Set<Integer> memberIds, ArrayList<Integer> events) {
        this.calendarId = calendarId;
        this.calendarName = calendarName;
        this.calendarPassword = calendarPassword;
        this.memberIds = memberIds;
        this.events = events;
    }
}
