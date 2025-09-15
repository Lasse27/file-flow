package model.file.conflict;

import org.jetbrains.annotations.Nullable;

public class SkipConflictStrategy implements FileConflictStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable FileAction resolve(final FileAction conflict)
    {
        return null;
    }
}
