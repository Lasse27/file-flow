package model.file.conflict;

/**
 * A conflict resolution strategy that resolves file conflicts by overwriting the target file
 * with the source file. This strategy assumes that the source file can safely replace the existing file at the target location.
 * <br>
 * This implementation conforms to the {@link FileConflictStrategy} interface.
 */
public class OverwriteConflictStrategy implements FileConflictStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public FileMove resolve(final FileMove conflict)
    {
        return null;
    }
}
