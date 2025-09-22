package model.procedure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import model.filter.FileFilterStrategy;
import model.conflict.FileConflictStrategy;
import model.discover.FileDiscoverStrategy;
import model.move.FileMoveStrategy;

import java.nio.file.Path;

/**
 * Represents a procedure for moving files from a source path to a target path.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public final class MoveProcedure extends Procedure
{
    private Path sourcePath;

    private Path targetDirectory;

    private FileDiscoverStrategy discoverStrategy;

    private FileFilterStrategy filterStrategy;

    private FileMoveStrategy fileMoveStrategy;

    private FileConflictStrategy fileConflictStrategy;
}
