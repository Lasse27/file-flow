package model;

import lombok.Data;

@Data
public class XMLConfiguration
{

    private XMLConfigurationMeta meta;


    public XMLConfiguration(final XMLConfigurationMeta meta)
    {
        this.meta = meta;
    }

}
