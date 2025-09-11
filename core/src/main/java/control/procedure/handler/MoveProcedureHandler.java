package control.procedure.handler;


import control.file.FileMoveExecutor;
import exception.ProcedureHandlerException;
import model.procedure.Procedure;
import model.procedure.ProcedureOption;
import model.procedure.ProcedureOptionType;
import model.procedure.ProcedureType;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Implementation of {@link ProcedureHandler} that handles procedures of type {@link ProcedureType#MOVE}.
 */
public final class MoveProcedureHandler implements ProcedureHandler
{

	private static ProcedureOption getOption (final Collection<ProcedureOption> options, final ProcedureOptionType type) throws ProcedureHandlerException
	{
		return options.stream()
			       .filter(opt -> opt.getType() == type)
			       .findFirst()
			       .orElseThrow(() -> new ProcedureHandlerException("No " + type + " option specified."));
	}



	private static List<Pattern> getPatterns (final Collection<ProcedureOption> options, final ProcedureOptionType type)
	{
		return options.stream()
			       .filter(opt -> opt.getType() == type)
			       .map(opt -> Pattern.compile(opt.getValue()))
			       .toList();
	}



	private static void validateOptionsInstance (final Collection<ProcedureOption> options) throws ProcedureHandlerException
	{
		if (options == null || options.isEmpty())
		{
			throw new ProcedureHandlerException("No options specified for move procedure.");
		}
	}



	private static void validateOptionsContent (final Collection<ProcedureOption> options) throws ProcedureHandlerException
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



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate (final Procedure procedure) throws ProcedureHandlerException
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
	public void handle (final Procedure procedure) throws ProcedureHandlerException
	{
		// validate content of procedure before running - throws if invalid
		this.validate(procedure);

		/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		 *  Mapping
		 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

		final List<ProcedureOption> options = procedure.getOptions();
		final Path sourcePath = Path.of(getOption(options, ProcedureOptionType.SOURCE).getValue());
		final Path targetPath = Path.of(getOption(options, ProcedureOptionType.TARGET).getValue());
		final List<Pattern> includes = getPatterns(options, ProcedureOptionType.INCLUDE);
		final List<Pattern> excludes = getPatterns(options, ProcedureOptionType.EXCLUDE);

		final FileMoveExecutor fileMoveExecutor = new FileMoveExecutor.Builder()
			                                          .withSourcePath(sourcePath)
			                                          .withTargetPath(targetPath)
			                                          .withIncludes(includes)
			                                          .withExcludes(excludes)
			                                          .build();

		fileMoveExecutor.run();
	}
}
