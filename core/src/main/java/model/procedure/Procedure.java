package model.procedure;


import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


/** Represents a procedure as part of a configuration or workflow. */
@Data
public class Procedure
{
    /** A unique identifier for the procedure, used to distinguish it from other procedures. */
    private String id;


    /** Represents the name of the procedure. */
    private String name;


    /** Specifies the type of the procedure represented by this instance. */
    private ProcedureType type;


    /** Contains a list of {@link ProcedureOption} objects defining specific procedure configurations and params. */
    private List<ProcedureOption> options;


    @ToString
    public static class Builder
    {
        private final Procedure procedure;


        public Builder()
        {
            this.procedure = new Procedure();
        }


        public Builder withId(final String id)
        {
            this.procedure.setId(id);
            return this;
        }


        public Builder withName(final String name)
        {
            this.procedure.setName(name);
            return this;
        }


        public Builder withType(final ProcedureType type)
        {
            this.procedure.setType(type);
            return this;
        }


        public Builder withOptions(final List<ProcedureOption> options)
        {
            this.procedure.setOptions(options);
            return this;
        }


        public Builder withOption(final ProcedureOption option)
        {
            if (this.procedure.getOptions() == null)
            {
                this.procedure.setOptions(new ArrayList<>());
            }
            this.procedure.getOptions().add(option);
            return this;
        }


        public Procedure build()
        {
            return this.procedure;
        }
    }

}
