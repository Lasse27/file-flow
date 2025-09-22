package control.procedure.executor;

import model.listener.Listener;
import model.listener.ListenerCollection;
import model.listener.ProgressEvent;
import model.procedure.CleanProcedure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * The CleanProcedureExecutor class is an implementation of the {@link ProcedureExecutor} interface
 * specifically for handling {@link CleanProcedure} executions. It provides execution logic for cleaning
 * procedures while supporting listener management for lifecycle events.
 */
public class CleanProcedureExecutor implements ProcedureExecutor<CleanProcedure>
{
    /**
     * A collection of {@link Listener} objects associated with the {@code CleanProcedureExecutor}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final CleanProcedure procedure)
    {
        // Remove empty directories from the source path
        this.removeEmptyDirectoriesRecursively(procedure.getSourcePath());
    }


    private void removeEmptyDirectoriesRecursively(final Path sourcePath)
    {
        try (final Stream<Path> stream = Files.list(sourcePath))
        {
            final List<Path> all = stream.toList();
            final Path[] directories = all.stream().filter(Files::isDirectory).toArray(Path[]::new);
            final Path[] files = all.stream().filter(Files::isRegularFile).toArray(Path[]::new);

            for (final Path directory : directories)
            {
                this.removeEmptyDirectoriesRecursively(directory);
            }

            if (files.length == 0)
            {
                this.listeners.onProgress(ProgressEvent .builder()
                        .progress(-1)
                        .message(String.format("Deleting: %s.", sourcePath.toAbsolutePath()))
                        .build());
                Files.delete(sourcePath);
            }
        }
        catch (final IOException ioException)
        {
            throw new RuntimeException(ioException);
        }
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
