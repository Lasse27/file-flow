package exception;

public record ExceptionInfo(ExceptionCode code, String message)
{
    private static String getMessage(final ExceptionCode code)
    {
        return switch (code)
        {
            case MOVE_PROCEDURE_EXECUTION_FAILED -> "Failed to execute move procedure.";
            case MOVE_PROCEDURE_VALIDATION_FAILED -> "Failed to validate move procedure.";
        };
    }
}
