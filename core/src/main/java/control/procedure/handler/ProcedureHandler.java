package control.procedure.handler;


import exception.ProcedureHandlerException;
import model.procedure.Procedure;


public interface ProcedureHandler
{
	/**
	 * Validates the specified procedure to ensure it meets the necessary criteria for execution. Implementations of this method should define the specific validation rules.
	 *
	 * @param procedure the procedure to validate. This object contains details such as its type, name, and options.
	 *
	 * @throws ProcedureHandlerException if the procedure is invalid or does not meet the necessary criteria for execution.
	 */
	public void validate (final Procedure procedure) throws ProcedureHandlerException;



	/**
	 * Executes the handling of a procedure. The specific implementation details are determined by the classes implementing this method. This method serves as the entry point
	 * for any operations that need to be performed as part of the procedure's handling process.
	 *
	 * @param procedure the procedure to be handled. Must specify a valid type through {@link Procedure#getType()}.
	 *
	 * @throws ProcedureHandlerException if an error occurs during the procedure handling.
	 */
	public void handle (final Procedure procedure) throws ProcedureHandlerException;
}
