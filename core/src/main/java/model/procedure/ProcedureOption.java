package model.procedure;


import lombok.Data;

import java.util.HashMap;


@Data
public class ProcedureOption
{
	private String name;


	private String value;


	private HashMap<String, String> attributes;
}
