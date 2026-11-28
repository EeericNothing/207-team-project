package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A calendar that several users share, holding the ids of its members and events.
 */
public class SharedCalendar {
    private final int calendarId;
    private final String calendarName;
    private final String calendarPassword;
    private final Set<Integer> memberIds;
    private final List<Integer> eventIDs;

    /**
     * Creates a shared calendar.
     *
     * @param calendarId       the calendar's id
     * @param calendarName     the calendar's name
     * @param calendarPassword the password needed to join it
     * @param memberIds        the ids of its members
     * @param events           the ids of its events
     */
    public SharedCalendar(int calendarId, String calendarName, String calendarPassword, Set<Integer> memberIds,
                          List<Integer> events) {
        this.calendarId = calendarId;
        this.calendarName = calendarName;
        this.calendarPassword = calendarPassword;
        this.memberIds = new HashSet<>(Objects.requireNonNull(memberIds, "memberIds"));
        this.eventIDs = new ArrayList<>(Objects.requireNonNull(events, "events"));
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

    /**
     * Returns the ids of the members.
     *
     * @return a read-only view; use {@link #addMember} and {@link #removeMember} to change it
     */
    public Set<Integer> getMemberIds() {
        return Collections.unmodifiableSet(memberIds);
    }

    /**
     * Returns the ids of the events.
     *
     * @return a read-only view of the event ids
     */
    public List<Integer> getEventIDs() {
        return Collections.unmodifiableList(eventIDs);
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
