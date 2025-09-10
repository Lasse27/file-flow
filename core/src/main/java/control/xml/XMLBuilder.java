package control.xml;


import lombok.Getter;
import model.xml.XMLConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


@Getter
public final class XMLBuilder
{

	private final Document document;



	public XMLBuilder (final Document document)
	{
		this.document = document;
	}



	/**
	 * Writes metadata elements into the given XML document based on the provided configuration.
	 *
	 * @param configuration the {@link XMLConfiguration} object containing metadata information
	 *
	 * @return an {@link Element} object representing the "Metadata" element.
	 */
	public Element buildMetadata (final XMLConfiguration configuration)
	{
		// Build nodes/elements
		final Element metadata = this.document.createElement("Metadata");
		final Element metaName = this.document.createElement("Name");
		final Element metaVersion = this.document.createElement("Version");
		final Element metaDescription = this.document.createElement("Description");
		final Element metaAuthor = this.document.createElement("Author");
		final Element metaCreated = this.document.createElement("Created");

		// Fill nodes
		metaName.setTextContent(configuration.getMeta().getName());
		metaVersion.setTextContent(configuration.getMeta().getVersion());
		metaDescription.setTextContent(configuration.getMeta().getDescription());
		metaAuthor.setTextContent(configuration.getMeta().getAuthor());
		metaCreated.setTextContent(configuration.getMeta().getCreated());

		// Bundle nodes/elements
		metadata.appendChild(metaName);
		metadata.appendChild(metaVersion);
		metadata.appendChild(metaDescription);
		metadata.appendChild(metaAuthor);
		metadata.appendChild(metaCreated);
		return metadata;
	}



	/**
	 * Writes options elements into the provided XML document based on the given configuration.
	 *
	 * @param configuration the {@link XMLConfiguration} object containing information used to create the "Options" element
	 *
	 * @return an {@link Element} object representing the "Options" element.
	 */
	public Element buildOptions (final XMLConfiguration configuration)
	{
		final Element options = this.document.createElement("Options");
		return options;
	}



	public Element buildProcedures (final XMLConfiguration configuration)
	{
		final Element procedures = this.document.createElement("Procedures");
		return procedures;
	}



	public Element buildAll (final XMLConfiguration configuration)
	{
		final Element root = this.document.createElement("FileFlow");
		root.appendChild(this.buildMetadata(configuration));
		root.appendChild(this.buildOptions(configuration));
		root.appendChild(this.buildProcedures(configuration));
		return root;
	}
}
