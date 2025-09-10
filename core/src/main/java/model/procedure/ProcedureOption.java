package model.procedure;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureOption
{
    private ProcedureOptionType type;

    private String value;

    private Map<String, String> attributes;


    @ToString
    public static class Builder
    {
        private final ProcedureOption option;


        public Builder()
        {
            this.option = new ProcedureOption();
        }


        public Builder withType(final ProcedureOptionType type)
        {
            this.option.setType(type);
            return this;
        }


        public Builder withValue(final String value)
        {
            this.option.setValue(value);
            return this;
        }


        public Builder withAttributes(final Map<String, String> attributes)
        {
            this.option.setAttributes(attributes);
            return this;
        }


        public Builder withAttribute(final String key, final String value)
        {
            if (this.option.getAttributes() == null)
            {
                this.option.setAttributes(new HashMap<>());
            }
            this.option.getAttributes().put(key, value);
            return this;
        }


        public ProcedureOption build()
        {
            return this.option;
        }
    }
}
