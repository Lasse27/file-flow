package control.procedure.handler;


import control.file.FileMoveHandler;
import exception.ProcedureHandlerException;
import model.procedure.Procedure;
import model.procedure.ProcedureOption;
import model.procedure.ProcedureOptionType;
import model.procedure.ProcedureType;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;


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
        // validate content of procedure before running - throws if invalid
        this.validate(procedure);

        /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
         *  Mapping
         * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

        final List<ProcedureOption> options = procedure.getOptions();
        final ProcedureOption sourceOption = options.stream()
                .filter(option -> option.getType() == ProcedureOptionType.SOURCE)
                .findFirst()
                .orElseThrow(() -> new ProcedureHandlerException("No source option specified for move procedure."));

        final ProcedureOption target = options.stream()
                .filter(option -> option.getType() == ProcedureOptionType.TARGET)
                .findFirst()
                .orElseThrow(() -> new ProcedureHandlerException("No target option specified for move procedure."));

        final List<ProcedureOption> includes = options.stream()
                .filter(option -> option.getType() == ProcedureOptionType.INCLUDE)
                .toList();

        final List<ProcedureOption> excludes = options.stream()
                .filter(option -> option.getType() == ProcedureOptionType.EXCLUDE)
                .toList();

        final List<ProcedureOption> rules = options.stream()
                .filter(option -> option.getType() == ProcedureOptionType.RULE)
                .toList();

        final Path sourcePath = Path.of(sourceOption.getValue());

        final Path targetPath = Path.of(target.getValue());

        // Build FileMoveHandler
        final FileMoveHandler fileMoveHandler = new FileMoveHandler.Builder()
                .withSourcePath(sourcePath)
                .withTargetPath(targetPath)
                .build();

        // Run the actual process
        fileMoveHandler.run();
    }


    private static void validateOptionsInstance(final Collection<ProcedureOption> options) throws ProcedureHandlerException
    {
        if (options == null || options.isEmpty())
        {
            throw new ProcedureHandlerException("No options specified for move procedure.");
        }
        else if (options.size() < 2)
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
