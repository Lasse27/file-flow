package model.file.discover;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class RecursiveDiscoverVisitor extends SimpleFileVisitor<Path>
{
    /**
     * {@inheritDoc
     */
    @Override
    public @NotNull FileVisitResult visitFile(@NotNull final Path file, @NotNull final BasicFileAttributes attrs) throws IOException
    {
        return super.visitFile(file, attrs);
    }
}
