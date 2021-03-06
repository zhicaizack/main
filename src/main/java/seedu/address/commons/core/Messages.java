package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_BLANK_FIELD = "Please do not leave any field blank.";
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date!\n"
            + "Please enter a valid date in any of the following formats:\n"
            + "1) dd-MM-yyyy\n"
            + "2) dd/MM-yyyy\n"
            + "3) dd.MM.yyyy\n"
            + "4) dd-MMM-yyyy\n"
            + "5) dd MMM yyyy\n"
            + "6) dd-MMM-yy\n"
            + "7) dd MMM yy";
    public static final String MESSAGE_INVALID_DAY_OF_MONTH =
            "Invalid day of month! The number of days for the month of %1$s in the year %2$s is %3$s.";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_TIME_FORMAT = "Invalid time!\n"
            + "Please enter a time in the 12-hour format (hh:mm AM/PM). Example: 12:45 PM";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_WELCOME = "Welcome to Trajectory.";
    public static final String MESSAGE_LOCKED_SYSTEM = "Trajectory is locked. "
            + "Please unlock access to the system via the 'unlock' command.";
    public static final String MESSAGE_UNLOCKED_SYSTEM = "Trajectory is now unlocked.";
    public static final String MESSAGE_WRONG_PASSWORD = "Invalid password. Please try again.";
}
