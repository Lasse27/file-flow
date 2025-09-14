package control.procedure.executor;

import exception.FileDiscoverException;
import exception.FileMoverException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.file.FileDiscoverStrategy;
import model.file.FileFilterStrategy;
import model.file.FileMoveRule;
import model.file.FileMoveStrategy;
import model.listener.Listener;
import model.listener.ListenerCollection;
import model.procedure.types.MoveProcedure;
import model.shared.Registrable;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The {@code FileMoveHandler} class provides functionality for moving files from a source location to a target location.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveProcedureExecutor implements ProcedureExecutor<MoveProcedure>, Registrable<Listener>
{
    /**
     * A collection of {@link Listener} objects associated with the {@code MoveProcedureExecutor}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    @Builder.Default
    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final MoveProcedure procedure)
    {
        try
        {
            final List<Path> discoveredFiles = this.discover(procedure);
            final List<Path> filteredFiles = this.filter(discoveredFiles, procedure);
            final Map<Path, Path> conflicts = this.move(filteredFiles, procedure);
        }
        catch (final Exception exception)
        {
            throw new FileMoverException("An error occurred while executing the move procedure.", exception);
        }
    }


    /**
     * Discovers and retrieves a list of file paths from the source path using the configured discovery strategy.
     * In case of an error during the discovery process, it wraps the exception in a {@code FileMoverException}.
     *
     * @return a list of {@code Path} objects representing the discovered files.
     * @throws FileMoverException if the discovery process fails due to a {@code FileDiscoverException}.
     */
    private List<Path> discover(final MoveProcedure procedure) throws FileDiscoverException
    {
        // Map to local vars
        final FileDiscoverStrategy strategy = procedure.getDiscoverStrategy();
        final Path sourcePath = procedure.getSourcePath();

        // Run strategy -> throws on fail
        return strategy.discover(sourcePath);
    }


    private List<Path> filter(final Collection<Path> paths, final MoveProcedure procedure)
    {
        // Map to local vars
        final FileFilterStrategy strategy = procedure.getFilterStrategy();

        // Run filter
        return paths.stream()
                .filter(strategy::accept)
                .toList();
    }


    private Map<Path, Path> move(final List<Path> filteredFiles, final MoveProcedure procedure)
    {
        // Map to local vars
        final FileMoveStrategy strategy = procedure.getFileMoveStrategy();
        final Path targetPath = procedure.getTargetPath();

        // Run move
        return strategy.move(filteredFiles, targetPath, FileMoveRule.KEEP_ATTRIBUTES);
    }


    @Override
    public void register(final Listener listener)
    {
        this.listeners.register(listener);
    }


    @Override
    public void unregister(final Listener listener)
    {
        this.listeners.unregister(listener);
    }
}
