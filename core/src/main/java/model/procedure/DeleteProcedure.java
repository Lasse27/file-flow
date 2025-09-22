package model.procedure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import model.filter.FileFilterStrategy;
import model.delete.FileDeleteStrategy;
import model.discover.FileDiscoverStrategy;

import java.nio.file.Path;

/**
 * Represents a procedure for moving files from a source path.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DeleteProcedure extends Procedure
{
    private Path sourcePath;

    private FileDiscoverStrategy discoverStrategy;

    private FileFilterStrategy filterStrategy;

    private FileDeleteStrategy deleteStrategy;
}
