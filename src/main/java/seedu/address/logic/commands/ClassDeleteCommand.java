package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;

/**
 * Deletes a class from the classroom list.
 */
public class ClassDeleteCommand extends Command {
    public static final String COMMAND_WORD = "class delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a class with students assigned to it "
            + " for a module for the system. "
            + "Parameters: "
            + PREFIX_CLASS_NAME + "CLASS_NAME "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASS_NAME + "T16 "
            + PREFIX_MODULE_CODE + "CG1111 ";

    public static final String MESSAGE_SUCCESS = "Class deleted: %1$s,"
            + " Module code: %2$s,"
            + " Enrollment size: %3$s";
    public static final String MESSAGE_FAIL = "Class belonging to module not found!";

    private final Classroom classToDelete;
    private final ClassroomManager classroomManager;

    /**
     * Command deletes a classroom.
     */
    public ClassDeleteCommand(String className, String moduleCode) {
        requireAllNonNull(className, moduleCode);
        classroomManager = ClassroomManager.getInstance();
        this.classToDelete = classroomManager.findClassroom(className, moduleCode);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (classToDelete == null) {
            throw new CommandException(MESSAGE_FAIL);
        }

        classroomManager.deleteClassroom(classToDelete);
        classroomManager.saveClassroomList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, classToDelete.getClassName(),
                classToDelete.getModuleCode(), classToDelete.getMaxEnrollment()),
                classroomManager.getClassroomHtmlRepresentation());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassDeleteCommand // instanceof handles nulls
                && classToDelete.equals(((ClassDeleteCommand) other).classToDelete));

    }
}
