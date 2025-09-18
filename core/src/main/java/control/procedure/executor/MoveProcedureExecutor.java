package control.procedure.executor;

import exception.FileDiscoverException;
import exception.FileMoverException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.file.FileFilterStrategy;
import model.file.conflict.FileConflictStrategy;
import model.file.conflict.FileMove;
import model.file.discover.FileDiscoverStrategy;
import model.file.move.FileMoveStrategy;
import model.listener.Listener;
import model.listener.ListenerCollection;
import model.listener.ListenerEvent;
import model.listener.ProgressEvent;
import model.procedure.types.MoveProcedure;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
            if (discoveredFiles.isEmpty())
            {
                this.listeners.onCancel(ListenerEvent.builder()
                        .message(String.format("Cancelling: No files found for procedure: %s", procedure.getName()))
                        .build());
                return;
            }
            final List<Path> filteredFiles = this.filter(discoveredFiles, procedure);
            if (filteredFiles.isEmpty())
            {
                this.listeners.onCancel(ListenerEvent.builder()
                        .message(String.format("Cancelling: No files remaining after filtering for procedure: %s", procedure.getName()))
                        .build());
                return;
            }
            final List<FileMove> conflicts = this.move(filteredFiles, procedure);
            if (conflicts.isEmpty())
            {
                return;
            }
            final List<FileMove> remainder = this.resolve(conflicts, procedure);
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


    private List<Path> filter(final List<Path> paths, final MoveProcedure procedure)
    {
        this.listeners.onStart(ListenerEvent.builder()
                .message(String.format("Filtering files for procedure: %s", procedure.getName()))
                .build());

        final FileFilterStrategy strategy = procedure.getFilterStrategy();
        final List<Path> filtered = new ArrayList<>();
        final int all = paths.size();
        for (int i = 0; i < paths.size(); i++)
        {
            final Path path = paths.get(i);
            if (strategy.accept(path))
            {
                final int progress = (int) (((double) i / all) * 100);
                filtered.add(path);
                this.listeners.onProgress(ProgressEvent.builder()
                        .progress(progress)
                        .message(String.format("Accepted file: %s.", path))
                        .build());
            }
        }

        this.listeners.onEnd(ListenerEvent.builder()
                .message(String.format("Filtering files finished. %s files remaining.", filtered.size()))
                .build());
        return filtered;
    }


    private List<FileMove> move(final List<Path> filteredFiles, final MoveProcedure procedure)
    {
        this.listeners.onStart(ListenerEvent.builder()
                .message(String.format("Moving files for procedure: %s", procedure.getName()))
                .build());

        final List<FileMove> actions = new ArrayList<>();
        final FileMoveStrategy strategy = procedure.getFileMoveStrategy();
        for (int i = 0; i < filteredFiles.size(); i++)
        {
            final int progress = (int) (((double) i / filteredFiles.size()) * 100);

            final Path sourcePath = filteredFiles.get(i);
            final FileMove fileMove = strategy.move(sourcePath, procedure.getTargetDirectory());
            if (fileMove.isResolved())
            {
                this.listeners.onProgress(ProgressEvent.builder()
                        .progress(progress)
                        .message(String.format("Moved %s -> %s", sourcePath, fileMove.targetFile()))
                        .build());
            }
            else
            {
                this.listeners.onProgress(ProgressEvent.builder()
                        .progress(progress)
                        .message(String.format("Conflict %s -> %s.", sourcePath, fileMove.targetFile()))
                        .build());
                actions.add(fileMove);
            }
        }
        this.listeners.onEnd(ListenerEvent.builder()
                .message(String.format("Files moved. %s conflicts occurred.", actions.size()))
                .build());
        return actions;
    }


    private List<FileMove> resolve(final List<FileMove> conflicts, final MoveProcedure procedure)
    {
        this.listeners.onStart(ListenerEvent.builder()
                .message(String.format("Resolving conflicts for procedure: %s", procedure.getName()))
                .build());

        final FileMoveStrategy strategy = procedure.getFileMoveStrategy();
        final FileConflictStrategy conflictStrategy = procedure.getFileConflictStrategy();
        final List<FileMove> remainder = new ArrayList<>(conflicts.size());
        final int all = conflicts.size();
        for (int i = 0; i < conflicts.size(); i++)
        {
            final int progress = (int) (((double) i / all) * 100);
            final FileMove conflict = conflicts.get(i);
            final FileMove postResolve = conflictStrategy.resolve(conflict);
            if (postResolve == null)
            {continue;}
            if (postResolve.isResolved())
            {
                final FileMove moved = strategy.move(postResolve.sourceFile(), postResolve.targetFile());
                if (moved.isResolved())
                {
                    this.listeners.onProgress(ProgressEvent.builder()
                            .progress(progress)
                            .message(String.format("Resolved conflict %s -> %s.", conflict.sourceFile(), conflict.targetFile()))
                            .build());
                }
                else
                {
                    this.listeners.onProgress(ProgressEvent.builder()
                            .progress(progress)
                            .message(String.format("Failed to resolve conflict %s -> %s.", conflict.sourceFile(), conflict.targetFile()))
                            .build());
                    remainder.add(conflict);
                }
            }
        }

        this.listeners.onEnd(ListenerEvent.builder()
                .message(String.format("Resolving conflicts finished. %s files unresolved.", remainder.size()))
                .build());

        return remainder;
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
