package model.procedure.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import model.file.conflict.FileConflictStrategy;
import model.file.discover.FileDiscoverStrategy;
import model.file.FileFilterStrategy;
import model.file.move.FileMoveStrategy;
import model.procedure.Procedure;

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
