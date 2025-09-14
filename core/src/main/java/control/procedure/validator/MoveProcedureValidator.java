package control.procedure.validator;

import exception.ProcedureValidatorException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.procedure.ProcedureOption;
import model.procedure.ProcedureOptionType;
import model.procedure.types.MoveProcedure;

import java.util.Collection;

/**
 * A specific implementation of the {@link ProcedureValidator} interface that validates the parameters required for executing a file move procedure.
 */
@Data
@Builder
@AllArgsConstructor
public class MoveProcedureValidator implements ProcedureValidator<MoveProcedure>
{
    /**
     * Validates that the given collection of procedure options is not null and not empty.
     * If the collection is invalid, throws a {@link ProcedureValidatorException}.
     *
     * @param options the collection of {@link ProcedureOption} objects to be validated
     * @throws ProcedureValidatorException if the collection is null or empty
     */
    private static void validateOptionsInstance(final Collection<ProcedureOption> options)
    {
        if (options == null || options.isEmpty())
        {
            throw new ProcedureValidatorException("No options specified for move procedure.");
        }
    }


    /**
     * Validates the content for the given collection of procedure options.
     *
     * @param options the collection of {@link ProcedureOption} objects to be validated
     * @throws ProcedureValidatorException if no source option or no target option is found
     */
    private static void validateOptionsContent(final Collection<ProcedureOption> options)
    {
        // Check if a source option exists
        if (options.stream().noneMatch(option -> option.getType() == ProcedureOptionType.SOURCE))
        {
            throw new ProcedureValidatorException("No source option specified for move procedure.");
        }

        // Check if a target option exists
        else if (options.stream().noneMatch(option -> option.getType() == ProcedureOptionType.TARGET))
        {
            throw new ProcedureValidatorException("No target option specified for move procedure.");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final MoveProcedure procedure)
    {
//        final List<ProcedureOption> options = procedure.getOptions();
//
//        // Check if instance valid
//        validateOptionsInstance(options);
//
//        // Check if required values are existing
//        validateOptionsContent(options);
    }
}
