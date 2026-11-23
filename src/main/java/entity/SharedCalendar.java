package entity;

import java.util.ArrayList;
import java.util.Set;

public class SharedCalendar {
    private final int calendarId;
    private final String calendarName;
    private final String calendarPassword;
    private final Set<Integer> memberIds;
    private final ArrayList<Integer> eventIDs;

    public SharedCalendar(int calendarId, String calendarName, String calendarPassword, Set<Integer> memberIds, ArrayList<Integer> events) {
        this.calendarId = calendarId;
        this.calendarName = calendarName;
        this.calendarPassword = calendarPassword;
        this.memberIds = memberIds;
        this.eventIDs = events;
    }

    public int getCalendarId() {
        return calendarId;
    }
    public String getCalendarName() {
        return calendarName;
    }
    public String getCalendarPassword() {
        return calendarPassword;
    }
    public Set<Integer> getMemberIds() {
        return memberIds;
    }
    public ArrayList<Integer> getEventIDs() {
        return eventIDs;
    }

    public boolean hasMember(int memberId) {
        return memberIds.contains(memberId);
    }
    public void addMember(int memberId) {
        memberIds.add(memberId);
    }
    public void removeMember(int memberId) {
        memberIds.remove(memberId);
    }
    public boolean isMemberEmpty() {
        return memberIds.isEmpty();
    }

    public boolean hasEvent(int eventId) {
        return eventIDs.contains(eventId);
    }
}
