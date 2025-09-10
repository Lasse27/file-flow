package control.file;

import lombok.Data;
import lombok.ToString;
import model.file.OnDuplicateRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * The {@code FileMoveHandler} class provides functionality for moving files from a source location to a target location.
 */
@Data
public class FileMoveHandler
{
    private Path sourcePath;

    private Path targetPath;

    private List<Object> includes;

    private List<Object> excludes;

    private boolean recursive = false;

    private boolean includeDirectories = false;

    private boolean keepStructure = false;

    private boolean copyAttributes = false;

    private OnDuplicateRule onDuplicateRule;


    public void run()
    {
        if (this.recursive)
        {
            this.moveRecursive();
            return;
        }
        this.moveSingle();
    }


    private void moveSingle()
    {

        // get all file system items needed
        try (final Stream<Path> pathStream = Files.list(this.sourcePath))
        {
            final Iterator<Path> iterator = pathStream.iterator();
            while (iterator.hasNext())
            {
                final Path path = iterator.next();
                if (Files.isDirectory(path) && this.includeDirectories)
                {
                    Files.move(path, this.targetPath.resolve(path.getFileName()));
                }
            }
        }
        catch (final IOException e)
        {
            throw new RuntimeException(e);
        }

        // filter by includes/excludes

        // move to the target directory
    }


    private void moveRecursive()
    {

    }


    @ToString
    public static class Builder
    {
        private final FileMoveHandler handler;


        public Builder()
        {
            this.handler = new FileMoveHandler();
        }


        public Builder withSourcePath(final Path sourcePath)
        {
            this.handler.setSourcePath(sourcePath);
            return this;
        }


        public Builder withTargetPath(final Path targetPath)
        {
            this.handler.setTargetPath(targetPath);
            return this;
        }


        public Builder withIncludes(final List<Object> includes)
        {
            this.handler.setIncludes(includes);
            return this;
        }


        public Builder withExcludes(final List<Object> excludes)
        {
            this.handler.setExcludes(excludes);
            return this;
        }


        public Builder withRecursive(final boolean recursive)
        {
            this.handler.setRecursive(recursive);
            return this;
        }


        public Builder withIncludeDirectories(final boolean includeDirectories)
        {
            this.handler.setIncludeDirectories(includeDirectories);
            return this;
        }


        public Builder withKeepStructure(final boolean keepStructure)
        {
            this.handler.setKeepStructure(keepStructure);
            return this;
        }


        public Builder withCopyAttributes(final boolean copyAttributes)
        {
            this.handler.setCopyAttributes(copyAttributes);
            return this;
        }


        public Builder withOnDuplicateRule(final OnDuplicateRule onDuplicateRule)
        {
            this.handler.setOnDuplicateRule(onDuplicateRule);
            return this;
        }


        public FileMoveHandler build()
        {
            return this.handler;
        }
    }
}
