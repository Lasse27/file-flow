package control.procedure;


import control.procedure.handler.*;
import exception.ProcedureDispatcherException;
import exception.ProcedureHandlerException;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.procedure.Procedure;
import model.procedure.ProcedureType;

import java.util.Map;


/**
 * This class provides the ability to execute a given {@link Procedure}.
 */
@ToString
@NoArgsConstructor
public class ProcedureDispatcher
{

	/**
	 * A static, immutable map that associates each {@link ProcedureType} with its corresponding {@link ProcedureHandler}.
	 */
	private static final Map<ProcedureType, ProcedureHandler> handlers = Map.of(
		ProcedureType.MOVE, new MoveProcedureHandler(),
		ProcedureType.CLEAN, new CleanProcedureHandler(),
		ProcedureType.COPY, new CopyProcedureHandler(),
		ProcedureType.DELETE, new DeleteProcedureHandler(),
		ProcedureType.ZIP, new ZipProcedureHandler(),
		ProcedureType.UNZIP, new UnzipProcedureHandler(),
		ProcedureType.RENAME, new RenameProcedureHandler()
	);



	/**
	 * Executes the given procedure using the appropriate handler based on its type.
	 *
	 * @param procedure the procedure to execute. It must provide a valid type through {@link Procedure#getType()}.
	 *
	 * @throws ProcedureDispatcherException if the procedure type is unsupported, or if an error occurs during execution.
	 */
	public void dispatch (final Procedure procedure)
	{
		final ProcedureHandler handler = handlers.get(procedure.getType());
		if (handler == null)
		{
			throw new ProcedureDispatcherException("Unsupported procedure type: " + procedure.getType());
		}
		try
		{
			handler.handle(procedure);
		}
		catch (final ProcedureHandlerException exception)
		{
			throw new ProcedureDispatcherException("Error executing procedure: " + procedure.getName(), exception);
		}
	}
}
