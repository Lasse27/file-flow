package model.file.discover;

import exception.FileDiscoverException;
import model.listener.Listener;
import model.listener.ProgressEvent;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RecursiveDiscoverStrategy implements FileDiscoverStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Path> discover(final Path sourcePath, final Listener listener) throws FileDiscoverException
    {
        try (final Stream<Path> pathStream = Files.walk(sourcePath))
        {
            final List<Path> paths = pathStream.toList();
            final long total = paths.size();
            long processed = 0;
            long progress = 0;
            final List<Path> discoveredFiles = new ArrayList<>();
            for (final Path path : paths)
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
                if (Files.isSymbolicLink(path))
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
