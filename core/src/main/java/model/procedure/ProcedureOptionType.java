package model.procedure;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public enum ProcedureOptionType
{
    TARGET("Target"),

    SOURCE("Source"),

    INCLUDE("Include"),

    EXCLUDE("Exclude"),

    RULE("Rule");


    private final String identifier;



    ProcedureOptionType (final String identifier)
    {
        this.identifier = identifier;
    }
}
