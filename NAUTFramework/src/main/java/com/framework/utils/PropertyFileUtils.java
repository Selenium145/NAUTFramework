package com.framework.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * Class to read and write property file
 * 
 * @author 10675365
 * 
 */

public class PropertyFileUtils {
	Properties properties;
	String filepath;

	/*
	 * This constructor loads property file
	 */
	public PropertyFileUtils(String propertyFilePath) {
		BufferedReader reader;
		this.filepath = propertyFilePath;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			properties.load(reader);
			reader.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e1) {

		}
	}

	/*
	 * @return properties
	 */
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}

	/*
	 * Set key value in property file
	 */
	public void setProperty(String propertyName, String propertyValue) throws ConfigurationException {

		PropertiesConfiguration conf = new PropertiesConfiguration(filepath);
		conf.setProperty(propertyName, propertyValue);
		conf.save();
	}

}
