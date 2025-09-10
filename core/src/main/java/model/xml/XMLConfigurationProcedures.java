package model.xml;


import lombok.Data;
import model.procedure.ProcedureGroup;

import java.util.List;


/**
 * Represents a collection of procedures within an XML configuration. This class serves as a container that holds a list of {@link ProcedureGroup} objects.
 * <br>
 * This class is integrated with various helper and builder methods within the parent {@link XMLConfiguration} to facilitate step-by-step creation and management of procedural
 * configuration.
 */
@Data
public class XMLConfigurationProcedures
{
    private List<ProcedureGroup> procedureGroups;
}
