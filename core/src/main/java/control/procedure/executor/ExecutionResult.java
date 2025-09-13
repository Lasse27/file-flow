package control.procedure.executor;

import exception.ExceptionInfo;


/**
 * Represents the result of an execution process, encapsulating success status and exception information (if applicable).
 * This class is designed to provide a unified way to represent the outcome of operations, allowing callers to distinguish between successful and failed
 * executions and handle associated exceptions if the operation fails.
 *
 * @param isSuccess     a boolean indicating whether the execution was successful
 * @param exceptionInfo an {@code ExceptionInfo} object capturing details of the exception if the execution failed, otherwise {@code null}
 */
public record ExecutionResult(boolean isSuccess, ExceptionInfo exceptionInfo)
{

    /**
     * Creates and returns a successful {@code ExecutionResult} instance.
     * The returned result indicates that an operation was completed successfully with no exceptions.
     *
     * @return an {@code ExecutionResult} with {@code isSuccess} set to true and {@code exceptionInfo} set to null.
     */
    public static ExecutionResult ok()
    {
        return new ExecutionResult(true, null);
    }


    /**
     * Creates a new instance of {@code ExecutionResult} representing a failed execution
     * with the specified exception details.
     *
     * @param exceptionInfo an {@code ExceptionInfo} object containing details about the exception that caused the failure
     * @return an {@code ExecutionResult} instance indicating failure status and associated exception details
     */
    public static ExecutionResult fail(final ExceptionInfo exceptionInfo)
    {
        return new ExecutionResult(false, exceptionInfo);
    }
}
