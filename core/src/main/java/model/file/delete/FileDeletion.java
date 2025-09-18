package model.file.delete;

import java.nio.file.Path;

/**
 * Represents the status of a file deletion operation, including its target path and resolution state.
 * This record is used to encapsulate the required information about a file deletion,
 * such as whether the operation has been resolved or remains unresolved.
 */
public record FileDeletion (Path targetPath, boolean isResolved)
{
    /**
     * Creates a {@code FileDeletion} instance with the specified target path and marks the deletion operation as resolved.
     * This is typically used to indicate that the target file or directory has been successfully deleted without conflicts.
     *
     * @param targetPath the path of the file or directory that has been successfully deleted.
     * @return a {@code FileDeletion} instance representing the resolved state of the deletion operation.
     */
    public static FileDeletion RESOLVED (final Path targetPath)
    {
        return new FileDeletion(targetPath, true);
    }

    /**
     * Creates a {@code FileDeletion} instance where the deletion operation is marked as unresolved.
     * This indicates that the deletion process for the specified target path has not yet been completed
     * or resolved and may require further actions or processing.
     *
     * @param targetPath the path of the file or directory for which the deletion operation is unresolved.
     * @return a {@code FileDeletion} instance representing the unresolved state of the deletion operation.
     */
    public static FileDeletion UNRESOLVED (final Path targetPath)
    {
        return new FileDeletion(targetPath, false);
    }
}
