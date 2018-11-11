package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ClassDeleteStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClassDeleteStudentCommand object
 */
public class ClassDeleteStudentCommandParser implements Parser<ClassDeleteStudentCommand> {
    /**
     * Parses {@code args} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code args} does not conform the expected format
     */
    @Override
    public ClassDeleteStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASS_NAME, PREFIX_MODULE_CODE, PREFIX_MATRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS_NAME, PREFIX_MODULE_CODE, PREFIX_MATRIC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClassDeleteStudentCommand.MESSAGE_USAGE));
        }

        String className = argMultimap.getValue(PREFIX_CLASS_NAME).get().toUpperCase();
        ParserUtil.parseClassName(className);
        String moduleCode = argMultimap.getValue(PREFIX_MODULE_CODE).get().toUpperCase();
        ParserUtil.parseModuleCode(moduleCode);
        String matricNo = ParserUtil.parseMatric(argMultimap.getValue(PREFIX_MATRIC).get()).toString(); // TBC
        ParserUtil.parseMatric(matricNo);

        return new ClassDeleteStudentCommand(className, moduleCode, matricNo);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
