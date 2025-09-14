package control.procedure.validator;

import model.procedure.Procedure;

/**
 * Functional interface for validating instances of {@link Procedure} or its derived types.
 * Implementations of this interface define custom validation logic for specific types of procedures.
 *
 * @param <T> the type of {@link Procedure} this validator will handle, must extend the {@link Procedure} class
 */
@FunctionalInterface
public interface ProcedureValidator<T extends Procedure>
{
    /**
     * Validates the specified procedure instance.
     *
     * @param procedure the procedure to be validated, must be an instance of {@link Procedure} or its derived types
     */
    void validate(T procedure);
}
