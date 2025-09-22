package model.filter;

import lombok.Data;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * An implementation of {@link FileFilterStrategy} that filters file paths based on include and exclude patterns using regular expressions.
 * Excludes take priority over includes, if any pattern matches, either include or exclude, the respective action will be made.
 */
@Data
public class PatternFilterStrategy implements FileFilterStrategy
{

    /**
     * A list of regular expression patterns used to define inclusion criteria for file filtering.
     * Only file paths matching at least one of the patterns in this list will be included.
     * If no patterns are specified, all files are included unless excluded by other criteria.
     */
    private List<Pattern> includePatterns = Collections.emptyList();

    /**
     * A list of regular expression patterns used to define exclusion criteria for file filtering.
     * Any file path that matches at least one of the patterns in this list will be excluded.
     * If no patterns are specified, no files are excluded unless excluded by other criteria.
     */
    private List<Pattern> excludePatterns = Collections.emptyList();


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final Path file)
    {
        final String filename = file.getFileName().toString();

        // Exclude has priority
        for (final Pattern pattern : this.excludePatterns)
        {
            if (pattern.matcher(filename).matches())
            {
                return false;
            }
        }

        // Always allow if include patterns are empty
        if (this.includePatterns.isEmpty())
        {
            return true;
        }

        // Match if includes patterns are not empty
        for (final Pattern pattern : this.includePatterns)
        {
            if (pattern.matcher(filename).matches())
            {
                return true;
            }
        }

        return false;
    }
}
