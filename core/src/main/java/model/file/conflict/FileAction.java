package model.file.conflict;

import java.nio.file.Path;


public record FileAction(Path sourceFile, Path targetFile, boolean resolved)
{

    public static FileAction RESOLVED(final Path sourceFile, final Path targetFile)
    {
        return new FileAction(sourceFile, targetFile, true);
    }


    public static FileAction UNRESOLVED(final Path sourceFile, final Path targetFile)
    {
        return new FileAction(sourceFile, targetFile, false);
    }
}
