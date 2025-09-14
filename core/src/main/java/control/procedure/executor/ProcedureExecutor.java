package control.procedure.executor;

import model.procedure.Procedure;

/**
 * Represents a functional interface for executing procedures that extend the {@link Procedure} class.
 * Implementations of this interface provide specific execution logic based on the procedure type.
 *
 * @param <T> the type of {@link Procedure} to be executed. Must be a subclass of {@link Procedure}.
 */
@FunctionalInterface
public interface ProcedureExecutor<T extends Procedure>
{
    /**
     * Executes the specified procedure.
     *
     * @param procedure the procedure to be executed, must be a non-null instance of a class extending {@link Procedure}.
     */
    public void execute(T procedure);
}
