package model.procedure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.nio.file.Path;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CleanProcedure extends Procedure
{
    private Path sourcePath;
}
