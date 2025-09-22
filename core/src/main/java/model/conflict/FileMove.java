package model.conflict;

import java.nio.file.Path;


/**
 * Represents a file move operation, including the source file, target file,
 * and whether the operation has been isResolved.
 * This record is commonly used to encapsulate the state of a file movement
 * process, particularly in scenarios where file conflicts may arise.
 */
public record FileMove(Path sourceFile, Path targetFile, boolean isResolved)
{

    /**
     * Creates a {@code FileMove} instance where the file move operation is marked as isResolved.
     * This is used to represent a successful resolution of a file move,
     * indicating that the source file has been moved to the target location without conflicts.
     *
     * @param sourceFile the source file that is being moved
     * @param targetFile the target file location to which the source file is moved
     * @return a {@code FileMove} instance representing the isResolved file move operation
     */
    public static FileMove RESOLVED(final Path sourceFile, final Path targetFile)
    {
        return new FileMove(sourceFile, targetFile, true);
    }


    /**
     * Creates a {@code FileMove} instance where the file move operation is marked as unresolved.
     * This is typically used to represent a file move operation that has not yet been isResolved
     * and may require further action or processing to complete.
     *
     * @param sourceFile the source file that is being moved
     * @param targetFile the target file location to which the source file is intended to be moved
     * @return a {@code FileMove} instance representing the unresolved file move operation
     */
    public static FileMove UNRESOLVED(final Path sourceFile, final Path targetFile)
    {
        return new FileMove(sourceFile, targetFile, false);
    }
}
