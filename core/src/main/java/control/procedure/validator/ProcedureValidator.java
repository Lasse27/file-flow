package control.procedure.validator;

import model.listener.Listener;
import model.procedure.Procedure;
import shared.Registrable;

/**
 * Functional interface for validating instances of {@link Procedure} or its derived types.
 * Implementations of this interface define custom validation logic for specific types of procedures.
 *
 * @param <T> the type of {@link Procedure} this validator will handle, must extend the {@link Procedure} class
 */
public interface ProcedureValidator<T extends Procedure> extends Registrable<Listener>
{
    /**
     * Validates the specified procedure instance.
     *
     * @param procedure the procedure to be validated, must be an instance of {@link Procedure} or its derived types
     */
    void validate(T procedure);
}
