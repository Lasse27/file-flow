package control.procedure.handler;


import exception.ProcedureHandlerException;
import model.procedure.Procedure;
import model.procedure.ProcedureOption;
import model.procedure.ProcedureOptionType;
import model.procedure.ProcedureType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;


/**
 * Implementation of {@link ProcedureHandler} that handles procedures of type {@link ProcedureType#MOVE}.
 */
public final class MoveProcedureHandler implements ProcedureHandler
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final Procedure procedure) throws ProcedureHandlerException
    {
        final List<ProcedureOption> options = procedure.getOptions();

        // Check if instance valid
        validateOptionsInstance(options);

        // Check if required values are existing
        validateOptionsContent(options);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(final Procedure procedure) throws ProcedureHandlerException
    {
        // before running validate content
        this.validate(procedure);

        // run move procedure

    }


    private static void validateOptionsInstance(final Collection<ProcedureOption> options) throws ProcedureHandlerException
    {
        if (options == null || options.isEmpty())
        {
            throw new ProcedureHandlerException("No options specified for move procedure.");
        }
        else if (options.size() >= 2)
        {
            throw new ProcedureHandlerException("Move procedure must have exactly two options.");
        }
    }


    private static void validateOptionsContent(final Collection<ProcedureOption> options) throws ProcedureHandlerException
    {
        // Check if a source option exists
        if (options.stream().noneMatch(option -> option.getType() == ProcedureOptionType.SOURCE))
        {
            throw new ProcedureHandlerException("No source option specified for move procedure.");
        }

        // Check if a target option exists
        else if (options.stream().noneMatch(option -> option.getType() == ProcedureOptionType.TARGET))
        {
            throw new ProcedureHandlerException("No target option specified for move procedure.");
        }
    }
}
