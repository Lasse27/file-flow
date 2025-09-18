package control.procedure.handler;


import exception.ProcedureHandlerException;
import model.listener.Listener;
import model.procedure.Procedure;
import model.shared.Registrable;


/**
 * Interface defining the contract for handling and validating procedures.
 * <br> <br>
 * Implementations of this interface are responsible for providing specific logic to process and validate procedures based on their type and provided attributes.
 *
 * @param <T> type of procedure
 * @see Procedure
 * @see ProcedureHandlerException
 */
public interface ProcedureHandler<T extends Procedure> extends Registrable<Listener>
{
    /**
     * Executes the handling of a procedure. The specific implementation details are determined by the classes implementing this method. This method serves as the entry point
     * for any operations that need to be performed as part of the procedure's handling process.
     *
     * @param procedure the procedure to be handled.
     * @throws ProcedureHandlerException if an error occurs during the procedure handling.
     */
    public void handle(final T procedure) throws ProcedureHandlerException;
}
