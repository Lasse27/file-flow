package model.xml;


import lombok.Builder;
import lombok.Data;
import model.procedure.Procedure;

import java.util.List;


/**
 * Encapsulates XML configuration-related data, including metadata, options, and procedures.
 */
@Data
@Builder
public class XMLConfiguration
{

    private XMLConfigurationMeta meta;


    private XMLConfigurationOptions options;


    private List<Procedure> procedures;
}
