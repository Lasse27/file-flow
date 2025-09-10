package model.procedure;


import lombok.Data;

import java.util.List;


@Data
public class ProcedureGroup
{
	private String name;


	private String description;


	private List<Procedure> procedures;

}
