package model.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A concrete implementation of {@link FileMoveStrategy} that provides functionality
 * to move files into a flat structure at the target directory.
 */
public class FlatMoveStrategy implements FileMoveStrategy
{
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
    public Map<Path, Path> move(final List<Path> sourceFiles, final Path targetDirectory, final FileMoveRule... rules)
    {
        final Map<Path, Path> conflicts = new HashMap<>();
        for (final Path sourcePath : sourceFiles)
        {
            final Path targetPath = Path.of(targetDirectory.toString(), sourcePath.getFileName().toString());
            try
            {
                final BasicFileAttributes attributes = Files.readAttributes(sourcePath, BasicFileAttributes.class);
                final PosixFileAttributes posixAttrs = readPosixFileAttributes(sourcePath);
                Files.move(sourcePath, targetPath);
                if (Arrays.stream(rules).anyMatch(rule -> rule == FileMoveRule.KEEP_ATTRIBUTES))
                {
                    restoreFileAttributes(targetPath, attributes, posixAttrs);
                }
            }
            catch (final IOException exception)
            {
                conflicts.put(sourcePath, targetPath);
            }
        }
        return conflicts;
    }
}


