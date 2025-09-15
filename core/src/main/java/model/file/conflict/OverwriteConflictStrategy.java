package model.file.conflict;

import java.nio.file.Path;

public class OverwriteConflictStrategy implements FileConflictStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public FileAction resolve(final FileAction conflict)
    {
        return null;
    }
}
