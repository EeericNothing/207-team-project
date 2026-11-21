# Team Project

Please keep this up-to-date with information about your project throughout the term.

The readme should include information such as:
- a summary of what your application is all about
- a list of the user stories, along with who is responsible for each one
- information about the API(s) that your project uses 
- screenshots or animations demonstrating current functionality

By keeping this README up-to-date,
your team will find it easier to prepare for the final presentation
at the end of the term.

Calender and Scheduling App:

# Project Name
## Team Information
### Team Members
- Mingfei (Eric) He
- Rocky Shi
- Shawn Yu
- Berk Akbay
- Ishaan Kellar
---
## Project Description
### Domain
[Project description]
A calendar app that allows you to schedule events with other people, and filter for availability
---
## User Stories
### User Story 1
As a user I want to be able to login to my own account and
see my group's availability and my own availability so that I know when people are available
### User Story 2
As a user I want to be able to be able fill in my own availability
so that other people can plan around me
### User Story 3
As a user I want to be able to create events and assign people who are available so that you can plan events
### User Story 4
As a user I want to be able to filter users and events by categories so
that I can more clearly see when things are happening
### User Story 5
As a user I want to know when changes are made to my events by notification
so that I can plan my own life around it

---
## Use Cases
### Use Case 1: Sign up

Primary actor: New user

Success Outcome: Create a personal account so that the user can be scheduled with by their group.

Preconditions: The user is on the sign-up screen and does not yet have an account.

Trigger: The user submits the sign-up form.

Main success scenario:
1. The user enters a username, a password, and a repeated password.
2. The user submits the form.
3. The system verifies that no account with that username exists.
4. The system verifies that the two passwords match.
5. The system creates the account and stores it.
6. The system confirms the account was created and takes the user to the log-in screen.

Extensions / failure flows

- 1a. Username or password is empty: The system reports which field is required and does not create an account.
- 3a. Username already taken: The system reports that the username is unavailable and leaves the entered data on screen so the user can change only the username.
- 4a. Passwords do not match: The system reports the mismatch and clears both password fields.
- 5a. The account cannot be stored (e.g. the service is unreachable): The system reports that sign-up failed and that no account was created, so the user can retry.

Postconditions

- Success: an account exists and persists across restarts of the program; the user is not yet logged in.
- Failure: no account is created and no partial data is stored.


### Use Case 2: Log In

Primary actor: Registered user

Success Outcome: Access the user’s own calendar and their group’s availability.

Preconditions: The user has an account and is on the log-in screen.

Trigger: The user submits the log-in form.

Main success scenario:

1. The user enters their username and password.
2. The user submits the form.
3. The system verifies that an account with that username exists.
4. The system verifies that the password matches the account.
5. The system records that this user is the current user.
6. The system opens the main calendar screen, where the user’s own availability and their group’s availability can be viewed.

Extensions / failure flows:

- 1a. Empty field: The system reports which field is required.
- 3a. No such account: The system reports that the account was not found and offers to go to the sign-up screen.
- 3b/4b. The account information cannot be retrieved: The system reports that log-in is temporarily unavailable and the user stays on the log-in screen.
- 4a. Incorrect password: The system reports that the password is incorrect and clears the password field only.

Postconditions

- Success: a current user is set for the session, and the user’s stored calendar data is available to the rest of the program.
- Failure: no current user is set.
### Use Case 3: Logout

Primary actor: Logged-in user

Success Outcome: End the session so that another person can use the program on the same machine.

Preconditions: A user is logged in.

Trigger: The user selects “Log out”.

Main success scenario

1. The user selects “Log out”.
2. The system clears the current user.
3. The system returns to the log-in screen.

Extensions / failure flows

- 2a. Unsaved changes exist: The system saves them before clearing the current user, so no work is lost.

Postconditions
- No current user is set, and no other user’s data is visible on screen.

### Use Case 4: Set Up Own Availability
Primary actor: Logged-in user

Goal: Record the times the user is free, so that the rest of the group can plan events around them.

Preconditions: The user is logged in and is viewing their own calendar.

Trigger: The user chooses to edit their availability.

Main success scenario:

1. The user opens their own availability for a chosen period.
2. The system shows the period as a grid of time slots, with the slots the user has already recorded as free shown as selected.
3. The user marks the times they are free by selecting slots on the grid, either one slot at a time or by dragging across a range of slots.
4. The system shows each slot as free as soon as it is selected, so the user can see the result while selecting.
5. The user unselects slots they no longer want to be free, in the same way.
6. The user repeats steps 3-5 until the grid matches when they are actually free.
7. The user confirms the changes.
8. The system saves the updated availability and confirms that it was saved.
9. The user's availability is now visible to the other members of their calendar.

Extensions / failure flows:

- 3a. The user drags across slots that are already selected: The system treats the whole dragged range as free, so the same free time is never recorded twice.
- 3b. The user drags past the edge of the period shown: The selection stops at the last slot of the period, and no time outside it is recorded.
- 3c. The user selects a slot at which they are already attending an event: The system warns the user that they are marking themselves free during an event they joined, and lets the user either keep or undo the selection.
- 5a. The user unselects a slot at which they are attending an event: The system warns that the event falls outside their availability, and the event itself is left unchanged.
- 7a. The user cancels instead of confirming: The system discards the changes and the previously saved availability stays in effect.
- 8a. The availability cannot be saved (e.g. the service is unreachable): The system reports that the change was not saved, leaves the grid as the user left it so nothing has to be selected again, and the previously saved availability stays in effect.

Postconditions:

- Success: The user's availability is stored, persists across restarts of the program, and is what other members see when they look for a time to meet.
- Failure: The user's previously saved availability is unchanged.

### Use Case 5: Create Calendar

Primary actor: Logged-in user

Goal: Start a shared calendar where a group can schedule events.

Preconditions: The user is logged in and can access the main calendar screen.

Trigger: The user selects “Create calendar”.

Main success scenario

1. The user enters a calendar name and chooses a calendar password.
2. The user selects “Create”.
3. The system assigns a unique calendar ID and saves the calendar with the creator as its first member.
4. The system opens the empty calendar and shows its ID so the creator can share it with the group.

Extensions / failure flows

- 2a. If either field is blank, the system identifies the missing field and lets the user complete the form.
- 3a. If the calendar and its initial membership cannot be saved together, the system reports the failure and offers a retry.

Postconditions

- Success: The creator can reopen the calendar after logging in again. Other users can join with its ID and calendar password (Use Case 6).
- Failure: No partial calendar or membership is saved.

### Use Case 6: Join Calendar

Primary actor: Logged-in user

Goal: Become a member of an existing group calendar so the user can view its schedule.

Preconditions: The user is logged in. A group member has shared the calendar ID and calendar password with them.

Trigger: The user selects “Join calendar”.

Main success scenario

1. The user enters the calendar ID and calendar password, then selects “Join”.
2. The system finds the calendar and verifies its password.
3. The system adds the user to the calendar’s member list and saves the membership.
4. The system opens the shared calendar and displays its existing events, if any.

Extensions / failure flows

- 1a. If a field is blank or the ID is not a number, the system asks the user to correct the entry.
- 2a. If the ID and password do not match a calendar, the system asks the user to check both entries before trying again.
- 3a. If the user is already a member, the system opens the calendar using their existing membership.
- 3b. If the membership cannot be saved, the system reports that joining failed and lets the user retry.

Postconditions

- Success: The user appears once in the member list and can reopen the calendar after logging in again.
- Failure: The member list is unchanged and the attempted join grants no access.

### Use Case 7: Leave Calendar
Primary Actor: Logged-in User with Calendar

Success Outcome: The Specified Calendar no longer shows up for the user

Preconditions: 
- User is logged in
- User is a part of the calendar they want to leave

Trigger: User Selects Leave Calendar

Main Success Scenario:

1. The User clicks "Leave Calendar"
2. The system removes the user from all events on said calendar 
3. The system removes the user from the calendar
4. The system returns the user to the main screen

Extensions/ failure flows:
- 2a, see use case 11
- 3a, if the user is the last person in the calendar delete the calendar object too

Postconditions: 
- the user is no longer able to see/is part of the calendar they wanted to leave and is no longer part of the events in the calendar

### Use Case 8: Calendar View
Primary Actor: Logged-in User with Calendar

Success Outcome: The user sees the Calendar they wanted to view

Preconditions:
- the user is logged in
- user is a part of the calendar they want ot leave

Trigger: User clicks the calendar they want to view

Main Success Scenario:

1. User clicks the Calendar they want to view
2. The system retrieves the information about the calendar
3. The system builds the view for the calendar
4. The systems shows the User the calendar with the calendar information

Extensions/ failure flows:
- None, they should not be able to click a calendar they have not joined, the system should always build the calendar correctly

Postconditions:
- the user sees the information of the calendar they selected

### Use Case 9: Adding Events

Primary actor: Logged-in user

Success outcome: Create a new event on the global calendar.

Preconditions: The user is logged in and on the global calendar.

Trigger: The user selects "New Event".

Main success scenario

1. The user select a block of time on the calendar.
2. The user selects “New Event”.
2. The system shows a window that allow the user to set the information about the event (Name, Description, Time).
3. The event is shown on the global calendar. 

Extensions / failure flows

- 2a. If no time is selected: The system display an "Invalid Time Selected" Error.

Postconditions
- The event is on the global calendar allowing other user to see and join it.
- The user is the author of the event

### Use Case 10: Modifying Events

Primary actor: Logged-in user who created the event

Success outcome: The details of the event (Name, Description, Time) is updated and shown on the global calendar. 

Preconditions: The user is logged in and on the global calendar and is the author of the event.

Trigger: The user selects "Edit" while viewing an event.

Main success scenario

1. The user selects "Edit".
2. The user can edit the name and description by typing in the text field. 
2. The user can select a new block of time on the calendar to edit the time of the event. 
3. The user can select save to save the new details, or cancel to return to the event view. 
4. The event is shown on the global calendar with the new fields.

Extensions / failure flows

- 3a. Cancel is selected: The changes are discarded and the user returns to the event view.

Postconditions
- The event is on the global calendar showing new details for all users.

### Use Case 11: Deleting Events

Primary actor: Logged-in user who created the event.

Goal: Delete an event the user created.

Preconditions: The user is looking to delete a preexisting event they've created.

Trigger: The user clicks on the event and presses the delete event button.

Main success scenario
1. The user selects an event on the calendar.
2. The selected event is one the user created.
3. The user clicks the delete event button.
4. The system deletes the event entity.
5. All instances of this event in this user and other users who are attached to this event are freed in this time block.

Extension / failure flows

2a. If the user did not create the event, the system notifies the users and prevents them from deleting.

Postconditions

- Success: The user was able to delete the event and all users attached to that event have this event removed from their calendars.
- Failure: The system remains unchanged because the user did not have permission to delete the event.

### Use Case 12: Join Events

Goal: Join an existing event on the calendar.

Preconditions: The user is logged in and added to a calendar which contains the event they want to join.

Trigger: The user selects the event and clicks join event to add themselves.

Main success scenario

1. The user enters clicks on the event in the global calendar and clicks join in the event popup.
2. The system finds event and adds them to the attendee lst.
3. The system updates the user's calendar to reflect the added event in their personal calendar.

Extensions / failure flows

- 2a. If the user is already attending the event the system shows the leave event button instead of the join event button see use case 14
- 3a. If the user cannot be added to an event the event remains unchanged and no changes are made to the user's personal calendar.

Postconditions
- Success: The user joins an event and this is reflected in the event itself and the user's calendar.
- Failure: The user is unable to join the event, the event is unchanged and no changes are made to the user's calendar.

### Use Case 13: View Event

Primary actor: Logged-in user

Goal: See everything about an event, so that the user knows what it is, when it takes place and who else is going.

Preconditions: The user is logged in and is viewing a calendar they belong to, and that calendar has at least one event.

Trigger: The user selects an event.

Main success scenario:

1. The user selects an event from the calendar.
2. The system shows the event's name, description, start time and end time.
3. The system shows who is attending the event.
4. The system indicates whether the user themselves is attending it.
5. The user closes the event and returns to the calendar.

Extensions / failure flows:

- 1a. The event was removed by someone else since the calendar was last shown: The system reports that the event no longer exists and refreshes the calendar.
- 2a. The event cannot be retrieved (e.g. the service is unreachable): The system reports that the event cannot be shown and the user stays on the calendar.
- 2b. The event has no description: The system shows the remaining details and leaves the description empty.
- 3a. Nobody has joined the event yet: The system states that the event has no attendees.
- 4a. The user is not attending the event: The system offers the user the option to join it.

Postconditions:

- The user has seen the details of the event.
- Nothing is changed by viewing: the event, its list of attendees and the user's own availability all stay as they were.

### Use Case 14: Leave Event

Primary actor: Logged-in user who is in an event

Goal: Leave an event.

Preconditions: The user is logged in and is a participant of the event.

Trigger: The user selects leave event.

Main success scenario:

1. The user selects the leave event button when viewing an event.
2. The user is removed from the list of participants of the event.
3. the user's availability is unblocked for the event time.

Extensions / failure flows:

- 2a. Author Leaves: If the user is the author of the event, then the event is deleted. 

Postconditions:

- The user is no longer in the list participants of the event. 
- The event is no longer in the list of event the user is participating in. 

### Use Case 15: Filter Calendars to see user availability

Primary actor: Logged-in user

Goal: The filter shows the availabilities of a user or users depending on what filters are applied.

Preconditions: The user is logged in and is viewing a calendar they belong to.

Trigger: The user selects a drop down menu and clicks the filters they want to apply.

Main success scenario:

1. The user selects the filter button at the top of the calendar.
2. The user selects the filters (people) whose availabilities they want to see.
3. The system updates the calendar to show the heat map for the availabilities of the users in the filter.

Extensions / failure flows:

- 2a. Adding additional filters on top of those of people.

Postconditions:

- The filters that the user selects are applied to the calendar so the user can see the desired availabilities.

### Use Case 16: Notify When Event Details Change

Primary actor: Attendee of an event

Supporting actor: The email service

Goal: Find out that an event the user joined has changed or been cancelled, without having to open the app and check.

Preconditions: The event exists and has at least one attendee other than the person making the change. Every account has an email address recorded.

Trigger: Someone changes the details of an event, or the owner of an event leaves it.

Main success scenario (details changed):

1. A user changes the details of an event.
2. The system saves the change.
3. The system determines who is attending the event.
4. The system leaves out the user who made the change, since they already know about it.
5. The system sends every remaining attendee an email saying that "event name" details have been changed, and to check the app to see the changes.
6. The attendee receives the email and opens the app to see what is different.

Alternate flow (event cancelled):

1. The owner of an event leaves it.
2. The event is cancelled, as an event cannot exist without its owner.
3. The system determines who was attending the event.
4. The system leaves out the owner.
5. The system sends every remaining attendee an email saying that "event name" has been cancelled.
6. The attendee receives the email and knows the event is no longer happening.

Extensions / failure flows:

- 1a. The same event is changed several times in a row: Each confirmed change sends its own email, so attendees are never told about a change they were not sent.
- 3a. The event has no attendees other than the person who made the change: No email is sent, and the change still takes effect.
- 5a. An attendee has no email address recorded: The remaining attendees are still emailed, and the system records that this attendee could not be notified.
- 5b. The email service is unreachable: The change to the event, or its cancellation, stays in effect, and the system records that the attendees could not be notified. A failure to notify never undoes the change itself.

Postconditions:

- Success: Every attendee except the person who made the change has been sent an email about it. Sending the notification leaves the event, its attendees and everyone's availability unchanged.
- Failure: The change or cancellation still stands, and the system has a record of which attendees could not be notified.


---
## Minimal Viable Product (MVP)
| Team Member | MVP Use Case |
|-------------|--------------|
| Name | Use Case |
| Name | Use Case |
| Name | Use Case |
---
## Entities
### User: 
(userID: int, username: string, userPassword: string, 
attendingEvents: ArrayList<event>, availability: ArrayList<array[2]>)
### Event: 
(eventID: int, eventName: string, description: string,
startTime: java.time, endTime, java.time, Final Author: User)
### GlobalCalendar: 
(calendarID: int, calendarName: string, calendarPassword: string, 
users: ArrayList[User], events: ArrayList[Event])

---
## API Usage
**API Name: Supabase** 

**Documentation:** [https://supabase.com/docs]

**How it is used in the program: we are using supabase to store 
information about our users and calendars**
---
## Design Diagrams
### UML Class Diagram
### Sequence Diagrams
---
## How to Run
[Instructions for running the program.]
---
## Notes
[Any additional project information.]
