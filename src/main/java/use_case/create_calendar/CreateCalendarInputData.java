package use_case.create_calendar;

/**
 * The calendar name and password submitted by the user.
 */
public final class CreateCalendarInputData {

    private final String name;
    private final String password;

    /**
     * Captures the submitted values for validation by the use case.
     *
     * @param name the submitted calendar name
     * @param password the calendar password as entered
     */
    public CreateCalendarInputData(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Returns the submitted calendar name.
     *
     * @return the calendar name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the password exactly as submitted.
     *
     * @return the calendar password
     */
    public String getPassword() {
        return password;
    }
}
