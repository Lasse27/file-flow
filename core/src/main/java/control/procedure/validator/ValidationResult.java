package control.procedure.validator;

import exception.ExceptionInfo;
import org.jetbrains.annotations.NotNull;

/**
 * A generic record representing the result of a validation process.
 *
 * @param isValid       a boolean indicating whether the validation was successful
 * @param exceptionInfo an optional {@code ExceptionInfo} providing details about the exception, if validation was unsuccessful
 */
public record ValidationResult(boolean isValid, ExceptionInfo exceptionInfo)
{

    /**
     * Creates and returns a {@code ValidationResult} instance representing a successful validation result.
     *
     * @return A {@code ValidationResult} object with {@code isValid} set to {@code true}
     * and {@code exceptionInfo} set to {@code null}.
     */
    public static @NotNull ValidationResult ok()
    {
        return new ValidationResult(true, null);
    }


    /**
     * Creates and returns a {@code ValidationResult} instance representing a failed validation result.
     *
     * @param exceptionInfo an {@code ExceptionInfo} object providing details about the exception
     *                      that caused the validation to fail.
     * @return A {@code ValidationResult} object with {@code isValid} set to {@code false} and
     * the specified {@code exceptionInfo}.
     */
    public static ValidationResult fail(final ExceptionInfo exceptionInfo)
    {
        return new ValidationResult(false, exceptionInfo);
    }
}
