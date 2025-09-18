package model.file.conflict;

import org.jetbrains.annotations.Nullable;

public class SkipConflictStrategy implements FileConflictStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable FileMove resolve(final FileMove conflict)
    {
        return null;
    }
}
