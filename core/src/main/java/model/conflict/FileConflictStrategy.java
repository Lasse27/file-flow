package model.conflict;

/**
 * A functional interface for defining a strategy to resolve file conflicts that may occur when a source
 * file is being moved, copied, or otherwise interacting with a target file.
 */
@FunctionalInterface
public interface FileConflictStrategy
{
    /**
     * Resolves a file conflict using the implemented conflict resolution strategy.
     *
     * @param conflict the file conflict to resolve, represented as a {@link FileMove} object. It
     *                 contains details about the source file, target file, and its resolution state.
     * @return a {@link FileMove} object representing the result of the resolution. The returned
     * object indicates whether the conflict was isResolved, skipped, or remains unresolved.
     */
    FileMove resolve(FileMove conflict);
}
