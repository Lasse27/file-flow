package model.procedure;


import lombok.Data;
import lombok.experimental.SuperBuilder;


/**
 * Represents a procedure as part of a configuration or workflow.
 */
@Data
@SuperBuilder
public abstract class Procedure
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
     * Default protected constructor for the Procedure class.
     */
    protected Procedure() {}
}
