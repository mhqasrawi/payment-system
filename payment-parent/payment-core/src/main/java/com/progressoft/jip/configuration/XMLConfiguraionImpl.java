package com.progressoft.jip.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * @author u612
 *
 */
public class XMLConfiguraionImpl implements Configuration {

	private static final long serialVersionUID = -475401904344609780L;

	private static final String CONFIGURATION_FILE_PATH = "configuration.file.path";

	private XMLConfiguration configuration;

	public void init() throws ConfigurationException {
		String configuraitonFilePath = System.getProperty(CONFIGURATION_FILE_PATH);
		configuration = new XMLConfiguration(configuraitonFilePath);
	}

	@Override
	public String getProperty(String key) {
		Object property = configuration.getProperty(key);
		if (property == null)
			return null;
		return (String) property;
	}

}
