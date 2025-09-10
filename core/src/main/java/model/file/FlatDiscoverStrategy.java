package model.file;

import exception.FileDiscoverException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
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
    public List<Path> discover(final Path sourcePath) throws FileDiscoverException
    {
        try (final Stream<Path> pathStream = Files.list(sourcePath))
        {
            final Iterator<Path> pathIterator = pathStream.iterator();
            final List<Path> discoveredFiles = new ArrayList<>();
            while (pathIterator.hasNext())
            {
                final Path path = pathIterator.next();
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
            throw new FileDiscoverException("An error occurred while discovering files in the specified source path: " + sourcePath, exception);
        }
    }
}
