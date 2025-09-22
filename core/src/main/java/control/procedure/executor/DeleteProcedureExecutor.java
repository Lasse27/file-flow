package control.procedure.executor;

import exception.FileDiscoverException;
import exception.FileMoverException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.filter.FileFilterStrategy;
import model.delete.FileDeleteStrategy;
import model.delete.FileDeletion;
import model.discover.FileDiscoverStrategy;
import model.listener.Listener;
import model.listener.ListenerCollection;
import model.listener.ListenerEvent;
import model.listener.ProgressEvent;
import model.procedure.DeleteProcedure;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static shared.ListenerMessage.*;

/**
 * The {@code DeleteProcedureExecutor} class provides functionality for deleting files from a source directory.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProcedureExecutor implements ProcedureExecutor<DeleteProcedure>
{
    /**
     * A collection of {@link Listener} objects associated with the {@code DeleteProcedureExecutor}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    @Builder.Default
    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final DeleteProcedure procedure)
    {
        try
        {
            final List<Path> discoveredFiles = this.discover(procedure);
            if (discoveredFiles.isEmpty())
            {
                this.listeners.onCancel(ListenerEvent.builder()
                        .message(String.format(NO_FILES_FOUND, procedure.getName())).build());
                return;
            }
            final List<Path> filteredFiles = this.filter(discoveredFiles, procedure);
            if (filteredFiles.isEmpty())
            {
                this.listeners.onCancel(ListenerEvent.builder()
                        .message(String.format(NO_FILES_REMAINING, procedure.getName())).build());
                return;
            }
            final List<FileDeletion> deletions = this.delete(filteredFiles, procedure);
        }
        catch (final Exception exception)
        {
            throw new FileMoverException("An error occurred while executing the move procedure.", exception);
        }
    }


    /**
     * Discovers and retrieves a list of file paths from the source path using the configured discovery strategy.
     *
     * @return a list of {@code Path} objects representing the discovered files.
     */
    private List<Path> discover(final DeleteProcedure procedure) throws FileDiscoverException
    {
        this.listeners.onStart(ListenerEvent.builder()
                .message(String.format("Discovering files for procedure: %s", procedure.getName()))
                .build());

        final FileDiscoverStrategy strategy = procedure.getDiscoverStrategy();
        final Path sourcePath = procedure.getSourcePath();
        final List<Path> discovered = strategy.discover(sourcePath, this.listeners);

        this.listeners.onEnd(ListenerEvent.builder()
                .message(String.format(FILE_PROCESSED, procedure.getName(), discovered.size()))
                .build());
        return discovered;
    }


    private List<Path> filter(final List<Path> paths, final DeleteProcedure procedure)
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
                .message(String.format(FILE_PROCESSED, procedure.getName(), filtered.size()))
                .build());
        return filtered;
    }


    private List<FileDeletion> delete(final List<Path> filteredFiles, final DeleteProcedure procedure)
    {
        final FileDeleteStrategy strategy = procedure.getDeleteStrategy();
        final List<FileDeletion> conflicts = new ArrayList<>(filteredFiles.size());
        final int all = filteredFiles.size();
        for (int i = 0; i < filteredFiles.size(); i++)
        {
            final Path path = filteredFiles.get(i);
            final int progress = (int) (((double) i / all) * 100);
            final FileDeletion deletion = strategy.delete(path, this.listeners);
            if (deletion.isResolved())
            {
                this.listeners.onProgress(ProgressEvent.builder()
                        .progress(progress)
                        .message(String.format("Deleted  %s.", path))
                        .build());
            }
            else
            {
                this.listeners.onProgress(ProgressEvent.builder()
                        .progress(progress)
                        .message(String.format("Conflict %s.", path))
                        .build());
                conflicts.add(deletion);
            }
        }
        return conflicts;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void register(final Listener listener)
    {
        this.listeners.register(listener);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void unregister(final Listener listener)
    {
        this.listeners.unregister(listener);
    }
}
