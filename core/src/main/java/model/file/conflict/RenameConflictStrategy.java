package model.file.conflict;

import java.nio.file.Files;
import java.nio.file.Path;

public class RenameConflictStrategy implements FileConflictStrategy
{
    private static final char FILE_EXTENSION_SEPARATOR = '.';


    /**
     * {@inheritDoc}
     */
    @Override
    public FileAction resolve(final FileAction conflict)
    {
        Path temp = conflict.targetFile();
        while (Files.exists(temp))
        {
            final String fileName = temp.getFileName().toString();
            final String fileNameToDot = fileName.substring(0, fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR));
            final String extension = fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1);
            final String newName = "%s%d%s%s".formatted(
                    fileNameToDot,
                    System.currentTimeMillis(),
                    FILE_EXTENSION_SEPARATOR,
                    extension
            );
            temp = temp.resolveSibling(newName);
        }
        return FileAction.RESOLVED(conflict.sourceFile(), temp);
    }
}
