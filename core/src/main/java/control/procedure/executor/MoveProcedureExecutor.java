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
import model.listener.ListenerEvent;
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
public class MoveProcedureExecutor implements ProcedureExecutor<MoveProcedure>
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
        this.listeners.onStart(ListenerEvent.builder()
                .message(String.format("Discovering files for procedure: %s", procedure.getName()))
                .build());

        final FileDiscoverStrategy strategy = procedure.getDiscoverStrategy();
        final Path sourcePath = procedure.getSourcePath();
        final List<Path> discovered = strategy.discover(sourcePath, this.listeners);

        this.listeners.onEnd(ListenerEvent.builder()
                .message(String.format("Discovering files finished. %s files found.", discovered.size()))
                .build());
        return discovered;
    }


    private List<Path> filter(final Collection<Path> paths, final MoveProcedure procedure)
    {
        this.listeners.onStart(ListenerEvent.builder()
                .message(String.format("Filtering files for procedure: %s", procedure.getName()))
                .build());

        final FileFilterStrategy strategy = procedure.getFilterStrategy();
        final List<Path> filtered = paths.stream()
                .filter(strategy::accept)
                .toList();

        this.listeners.onEnd(ListenerEvent.builder()
                .message(String.format("Filtering files finished. %s files remaining.", filtered.size()))
                .build());

        return filtered;
    }


    private Map<Path, Path> move(final List<Path> filteredFiles, final MoveProcedure procedure)
    {
        this.listeners.onStart(ListenerEvent.builder()
                .message(String.format("Moving files procedure: %s", procedure.getName()))
                .build());

        final FileMoveStrategy strategy = procedure.getFileMoveStrategy();
        final Path targetPath = procedure.getTargetPath();
        final Map<Path, Path> conflicts = strategy.move(filteredFiles, targetPath, FileMoveRule.KEEP_ATTRIBUTES);

        this.listeners.onEnd(ListenerEvent.builder()
                .message(String.format("Files moved. %s conflicts occurred.", conflicts.size()))
                .build());
        return conflicts;
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
