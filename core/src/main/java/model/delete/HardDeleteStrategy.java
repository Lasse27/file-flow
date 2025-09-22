package model.delete;

import model.listener.Listener;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implementation of the {@link FileDeleteStrategy} interface that provides a hard delete mechanism
 * for files or directories. This strategy performs a permanent deletion of the specified target,
 * bypassing any intermediate steps such as moving the file to a trash or recycle bin.
 */
public class HardDeleteStrategy implements FileDeleteStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public FileDeletion delete(final Path targetPath, final Listener listener)
    {
        try
        {
            Files.deleteIfExists(targetPath);
        }
        catch (final Exception exception)
        {
            return FileDeletion.UNRESOLVED(targetPath);
        }

        return FileDeletion.RESOLVED(targetPath);
    }
}
