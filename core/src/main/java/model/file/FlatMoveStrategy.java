package model.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.file.conflict.FileAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;
import java.util.Map;

/**
 * A concrete implementation of {@link FileMoveStrategy} that provides functionality
 * to move files into a flat structure at the target directory.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlatMoveStrategy implements FileMoveStrategy
{
    @Builder.Default
    private boolean restoreAttributes = true;


    private static PosixFileAttributes readPosixFileAttributes(final Path sourcePath) throws IOException
    {
        PosixFileAttributes posixAttrs = null;
        try
        {
            posixAttrs = Files.readAttributes(sourcePath, PosixFileAttributes.class);
        }
        catch (final UnsupportedOperationException ignored)
        {
            // Nicht-POSIX-Systeme ueberspringen
        }
        return posixAttrs;
    }


    private static void restoreFileAttributes(final Path path, final BasicFileAttributes basicFileAttributes, final PosixFileAttributes posixFileAttributes) throws IOException
    {
        if (posixFileAttributes != null)
        {
            Files.setPosixFilePermissions(path, posixFileAttributes.permissions());
            Files.setOwner(path, posixFileAttributes.owner());
        }

        Files.setLastModifiedTime(path, basicFileAttributes.lastModifiedTime());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public FileAction move(final Path sourceFile, final Path targetFile)
    {
        try
        {
            final BasicFileAttributes attributes = Files.readAttributes(sourceFile, BasicFileAttributes.class);
            final PosixFileAttributes posixAttrs = readPosixFileAttributes(sourceFile);
            Files.move(sourceFile, targetFile);
            if (this.restoreAttributes)
            {
                restoreFileAttributes(targetFile, attributes, posixAttrs);
            }
        }
        catch (final IOException exception)
        {
            return FileAction.UNRESOLVED(sourceFile, targetFile);
        }

        return FileAction.RESOLVED(sourceFile, targetFile);
    }
}


