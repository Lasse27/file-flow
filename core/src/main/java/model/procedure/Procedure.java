package model.procedure;


import lombok.Builder;
import lombok.Data;

import java.util.List;


/** Represents a procedure as part of a configuration or workflow. */
@Data
@Builder
public class Procedure
{
    /**
     * A unique identifier for the procedure, used to distinguish it from other procedures.
     */
    private String id;


    /**
     * Represents the name of the procedure.
     */
    private String name;


    /**
     * Specifies the type of the procedure represented by this instance.
     */
    private ProcedureType type;


    /**
     * Contains a list of {@link ProcedureOption} objects defining specific procedure configurations and params.
     */
    private List<ProcedureOption> options;
}
