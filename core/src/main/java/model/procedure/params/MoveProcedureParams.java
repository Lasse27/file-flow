package model.procedure.params;

import lombok.Builder;
import lombok.Data;
import model.file.FileDiscoverStrategy;
import model.file.FileFilterStrategy;
import model.file.FileMoveStrategy;

import java.nio.file.Path;

/**
 * Represents the parameters required for executing a file move procedure.
 * This class defines the source and target paths, inclusion and exclusion patterns,
 * strategies for file discovery and movement, and additional configuration options
 * for managing the file move operation.
 */
@Data
@Builder
public class MoveProcedureParams implements ProcedureParams
{
    private Path sourcePath;

    private Path targetPath;

    private FileDiscoverStrategy discoverStrategy;

    private FileFilterStrategy filterStrategy;

    private FileMoveStrategy fileMoveStrategy;
}
