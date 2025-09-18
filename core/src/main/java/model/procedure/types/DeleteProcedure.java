package model.procedure.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import model.file.FileFilterStrategy;
import model.file.delete.FileDeleteStrategy;
import model.file.discover.FileDiscoverStrategy;
import model.procedure.Procedure;

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
