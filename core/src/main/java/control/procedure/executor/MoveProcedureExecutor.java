package control.procedure.executor;

import exception.FileDiscoverException;
import exception.FileMoverException;
import lombok.Data;
import lombok.ToString;
import model.file.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The {@code FileMoveHandler} class provides functionality for moving files from a source location to a target location.
 */
@Data
public class MoveProcedureExecutor implements ProcedureExecutor
{
    private Path sourcePath;

    private Path targetPath;

    private List<Pattern> includes = List.of();

    private List<Pattern> excludes = List.of();

    private FileDiscoverStrategy discoverStrategy = new FlatDiscoverStrategy();

    private FileMoveStrategy fileMoveStrategy = new FlatMoveStrategy();

    private boolean keepStructure = false;

    private boolean copyAttributes = false;


    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionResult execute()
    {
        final List<Path> discoveredFiles = this.discover();

        final List<Path> filteredFiles = this.filter(discoveredFiles);

        final Map<Path, Path> conflicts = this.move(filteredFiles);

        return ExecutionResult.ok();
    }


    /**
     * Discovers and retrieves a list of file paths from the source path using the configured discovery strategy.
     * In case of an error during the discovery process, it wraps the exception in a {@code FileMoverException}.
     *
     * @return a list of {@code Path} objects representing the discovered files.
     * @throws FileMoverException if the discovery process fails due to a {@code FileDiscoverException}.
     */
    private List<Path> discover()
    {
        try
        {
            return this.discoverStrategy.discover(this.sourcePath);
        }
        catch (final FileDiscoverException exception)
        {
            throw new FileMoverException(
                    "Failed to discover files in source path: " + this.sourcePath.toString() + " due to: " + exception.getMessage(), exception);
        }
    }


    private boolean checkDiscover(final Collection<Path> discoveredPaths)
    {
        if (discoveredPaths.isEmpty())
        {
            System.out.println("No files found in source path: " + this.sourcePath.toString() + " to move.");
            return false;
        }
        System.out.println("Discovered files:");
        for (final Path path : discoveredPaths)
        {
            System.out.println("\t+ " + path.toString());
        }
        return true;
    }


    private List<Path> filter(final List<Path> paths)
    {
        if (this.includes.isEmpty() && this.excludes.isEmpty())
        {
            return paths;
        }
        final List<Path> filteredPaths = new ArrayList<>();
        for (final Path path : paths)
        {
            // ONLY EXCLUDES
            if (this.includes.isEmpty() && !this.excludes.isEmpty())
            {
                if (this.excludes.stream().anyMatch(pattern -> pattern.matcher(path.toString()).find()))
                {
                    continue;
                }
                filteredPaths.add(path);
            }
            // ONLY INCLUDES
            else if (!this.includes.isEmpty() && this.excludes.isEmpty())
            {
                if (this.includes.stream().anyMatch(pattern -> pattern.matcher(path.toString()).find()))
                {
                    filteredPaths.add(path);
                }
            }
            // BOTH
            else if (this.includes.stream().anyMatch(pattern -> pattern.matcher(path.toString()).find()))
            {
                if (this.excludes.stream().anyMatch(pattern -> pattern.matcher(path.toString()).find()))
                {
                    continue;
                }
                filteredPaths.add(path);
            }
        }
        return filteredPaths;
    }


    private Map<Path, Path> move(final List<Path> filteredFiles)
    {
        return this.fileMoveStrategy.move(filteredFiles, this.targetPath, FileMoveRule.KEEP_ATTRIBUTES);
    }


    private boolean checkMove(final Map<Path, Path> conflicts)
    {
        if (conflicts.isEmpty())
        {
            System.out.println("No conflicts occurred.");
            return false;
        }
        System.out.println("Conflicts:");
        for (final Map.Entry<Path, Path> entry : conflicts.entrySet())
        {
            System.out.println("\t+ " + entry.getKey().toString() + " -> " + entry.getValue().toString());
        }
        return true;
    }


    @ToString
    public static class Builder
    {
        private final MoveProcedureExecutor handler;


        /**
         * Creates a new Builder instance with a default FileMoveHandler.
         */
        public Builder()
        {
            this.handler = new MoveProcedureExecutor();
        }


        /**
         * Sets the source path for file operations.
         *
         * @param sourcePath the path where files will be moved from
         * @return this builder instance for method chaining
         */
        public Builder withSourcePath(final Path sourcePath)
        {
            this.handler.setSourcePath(sourcePath);
            return this;
        }


        /**
         * Sets the target path for file operations.
         *
         * @param targetPath the path where files will be moved to
         * @return this builder instance for method chaining
         */
        public Builder withTargetPath(final Path targetPath)
        {
            this.handler.setTargetPath(targetPath);
            return this;
        }


        /**
         * Sets patterns for including files in the operation.
         *
         * @param includes list of patterns that files must match to be included
         * @return this builder instance for method chaining
         */
        public Builder withIncludes(final List<Pattern> includes)
        {
            this.handler.setIncludes(includes);
            return this;
        }


        /**
         * Sets patterns for excluding files from the operation.
         *
         * @param excludes list of patterns that if matched will exclude files
         * @return this builder instance for method chaining
         */
        public Builder withExcludes(final List<Pattern> excludes)
        {
            this.handler.setExcludes(excludes);
            return this;
        }


        /**
         * Sets the strategy for discovering files.
         *
         * @param strategy the file discovery strategy to use
         * @return this builder instance for method chaining
         */
        public Builder withDiscoverStrategy(final FileDiscoverStrategy strategy)
        {
            this.handler.setDiscoverStrategy(strategy);
            return this;
        }


        /**
         * Sets whether to maintain directory structure when moving files.
         *
         * @param keepStructure true to maintain structure, false otherwise
         * @return this builder instance for method chaining
         */
        public Builder withKeepStructure(final boolean keepStructure)
        {
            this.handler.setKeepStructure(keepStructure);
            return this;
        }


        /**
         * Sets whether to copy file attributes during move operation.
         *
         * @param copyAttributes true to copy attributes, false otherwise
         * @return this builder instance for method chaining
         */
        public Builder withCopyAttributes(final boolean copyAttributes)
        {
            this.handler.setCopyAttributes(copyAttributes);
            return this;
        }


        /**
         * Builds and returns the configured FileMoveHandler instance.
         *
         * @return the configured FileMoveHandler
         */
        public MoveProcedureExecutor build()
        {
            return this.handler;
        }
    }
}
