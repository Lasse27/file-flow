package model.procedure;


import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ProcedureGroup
{
    private String name;


    private String description;


    private List<Procedure> procedures;
}
