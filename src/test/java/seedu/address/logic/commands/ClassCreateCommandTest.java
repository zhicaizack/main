package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ClassCreateCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class ClassCreateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final String className = "T16";
        final String moduleCode = "CG1111";
        final String maxEnrollment = "20";
        assertCommandFailure(new ClassCreateCommand(className, moduleCode, maxEnrollment), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, className, moduleCode, maxEnrollment));
    }
    @Test
    public void equals() {
        final ClassCreateCommand standardCommand = new ClassCreateCommand(VALID_CLASS_T16, VALID_MODULE_CODE, VALID_MAX_ENROLLMENT);
        // same values -> returns true
        ClassCreateCommand commandWithSameValues = new ClassCreateCommand(VALID_CLASS_T16, VALID_MODULE_CODE, VALID_MAX_ENROLLMENT);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}
