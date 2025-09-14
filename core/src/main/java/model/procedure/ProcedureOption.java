package model.procedure;


import lombok.Builder;
import lombok.Data;

import java.util.Map;


@Data
@Builder
public class ProcedureOption
{

    private ProcedureOptionType type;


    private String value;


    private Map<String, String> attributes;
}
