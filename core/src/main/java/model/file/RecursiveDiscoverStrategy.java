package model.file;

import exception.FileDiscoverException;

import java.nio.file.Path;
import java.util.List;

public class RecursiveDiscoverStrategy implements FileDiscoverStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Path> discover(final Path sourcePath) throws FileDiscoverException
    {
        return List.of();
    }
}
