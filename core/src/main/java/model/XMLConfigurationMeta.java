package model;

import lombok.Data;

/**
 * Represents metadata information for an XML configuration.
 */
@Data
public class XMLConfigurationMeta
{
    private final String name;

    private final String version;

    private final String description;

    private final String author;

    private final String created;
}
