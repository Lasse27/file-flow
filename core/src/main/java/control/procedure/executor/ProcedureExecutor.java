package control.procedure.executor;

/**
 * Represents a functional interface for executing a procedure and getting the result of the execution.
 * The {@code ProcedureExecutor} interface serves as a contract for executing operations that return
 * an {@code ExecutionResult}, which encapsulates the success status and potential exception details.
 */
@FunctionalInterface
public interface ProcedureExecutor
{
    /**
     * Executes a procedure and returns the result of the execution. The result encapsulates the success status
     * and any exception details if applicable.
     *
     * @return an {@code ExecutionResult} representing the outcome of the execution, including whether it was successful
     * and any associated exception information in case of failure.
     */
    public ExecutionResult execute();
}
