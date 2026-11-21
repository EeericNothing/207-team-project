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
As a user I want to be able to ask the AI window questions about my
calendar so that I don't have to spend my own efforts

---
## Use Cases
### Use Case 1: Sign up

Primary actor: New user

Goal: Create a personal account so that the user can be scheduled with by their group.

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

Goal: Access the user’s own calendar and their group’s availability.

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

Goal: End the session so that another person can use the program on the same machine.

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

### Use Case 8: Calendar View

### Use Case 9: Adding Events

### Use Case 10: Modifying Events

### Use Case 11: Deleting Events

### Use Case 12: Join Events

### 


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
startTime: java.time, endTime, java.time)
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
