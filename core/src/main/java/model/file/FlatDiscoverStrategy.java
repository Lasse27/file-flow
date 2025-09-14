package model.file;

import exception.FileDiscoverException;
import model.listener.Listener;
import model.listener.ProgressEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * A file discovery strategy that retrieves all files located directly within a given directory.
 * <br>
 * Implements {@link FileDiscoverStrategy}, providing a concrete realization of file discovery
 * for flat directory structures.
 */
public class FlatDiscoverStrategy implements FileDiscoverStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Path> discover(final Path sourcePath, final Listener listener) throws FileDiscoverException
    {
        try (final Stream<Path> pathStream = Files.list(sourcePath))
        {
            final List<Path> allPaths = pathStream.toList(); // alle auf einmal laden
            final int total = allPaths.size();
            final List<Path> discoveredFiles = new ArrayList<>();

            long processed = 0;
            long progress = 0;

            for (final Path path : allPaths)
            {
                processed++;
                progress = Math.round((processed / (double) total) * 100);

                listener.onProgress(ProgressEvent.builder()
                        .progress(progress)
                        .message(String.format("%s.", path))
                        .build());

                if (Files.isDirectory(path))
                {
                    continue;
                }
                discoveredFiles.add(path);
            }
            return discoveredFiles;
        }
        catch (final IOException exception)
        {
            throw new FileDiscoverException(
                    "An error occurred while discovering files in the specified source path: " + sourcePath,
                    exception
            );
        }
    }
}
