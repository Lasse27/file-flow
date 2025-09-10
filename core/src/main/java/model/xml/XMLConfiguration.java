package model.xml;


import lombok.Data;
import lombok.NonNull;
import lombok.ToString;


/**
 * Encapsulates XML configuration-related data, including metadata, options, and procedures.
 */
@Data
public class XMLConfiguration
{

	private XMLConfigurationMeta meta;


	private XMLConfigurationOptions options;


	private XMLConfigurationProcedures procedures;


	/**
	 * A builder class for constructing an {@link XMLConfiguration} instance in a step-by-step manner.
	 *
	 * <p>Usage example:</p>
	 * <pre>{@code
	 * XMLConfiguration config = new XMLConfiguration.Builder()
	 *     .withMeta(new XMLConfigurationMeta())
	 *     .withOptions(new XMLConfigurationOptions())
	 *     .withProcedures(new XMLConfigurationProcedures())
	 *     .build();
	 * }</pre>
	 */
	@ToString
	public static class Builder
	{

		private final XMLConfiguration configuration;



		/**
		 * Creates a new {@code Builder} instance with a default {@link XMLConfiguration} initialized with an empty {@link XMLConfigurationMeta}.
		 */
		public Builder ()
		{
			this.configuration = new XMLConfiguration();
		}



		/**
		 * Sets the {@link XMLConfigurationMeta} for the {@link XMLConfiguration}.
		 *
		 * @param meta the metadata configuration to set, must not be {@code null}
		 *
		 * @return this builder instance for method chaining
		 */
		public Builder withMeta (@NonNull final XMLConfigurationMeta meta)
		{
			this.configuration.setMeta(meta);
			return this;
		}



		/**
		 * Sets the {@link XMLConfigurationOptions} for the {@link XMLConfiguration}.
		 *
		 * @param options the option configuration to set, must not be {@code null}
		 *
		 * @return this builder instance for method chaining
		 */
		public Builder withOptions (@NonNull final XMLConfigurationOptions options)
		{
			this.configuration.setOptions(options);
			return this;
		}



		/**
		 * Sets the {@link XMLConfigurationProcedures} for the {@link XMLConfiguration}.
		 *
		 * @param procedures the procedure configuration to set, must not be {@code null}
		 *
		 * @return this builder instance for method chaining
		 */
		public Builder withProcedures (@NonNull final XMLConfigurationProcedures procedures)
		{
			this.configuration.setProcedures(procedures);
			return this;
		}



		/**
		 * Builds and returns the configured {@link XMLConfiguration}.
		 *
		 * @return the fully configured {@link XMLConfiguration} instance
		 */
		public XMLConfiguration build ()
		{
			return this.configuration;
		}
	}

}
