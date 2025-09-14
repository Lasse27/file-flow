package control.procedure.handler;


import exception.ProcedureHandlerException;
import model.procedure.Procedure;
import model.procedure.ProcedureType;


/**
 * Implementation of {@link ProcedureHandler} that handles procedures of type {@link ProcedureType#CLEAN}.
 */
public final class CleanProcedureHandler implements ProcedureHandler
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(final Procedure procedure) throws ProcedureHandlerException
    {

    }
}
