package com.testng.generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class DynamicTestNG {
	/*
	 * Getting thread count from command line
	 * 
	 */
	public static int getThreadCount() {
		/* reading from cmdline */
		String threadCount = System.getProperty("threadCount");
		return Integer.parseInt(threadCount);
	}

	public static int getDataProviderThreadCount() {
		/* reading from cmdline */
		String threadCountDP = System.getProperty("threadCountDP");
		return Integer.parseInt(threadCountDP);
	}

	public static String[] getInstances() {
		/* reading from cmdline */
		String[] browsers = System.getProperty("browsers").split(",");
		return browsers;
	}

	/*
	 * write to file
	 * 
	 * @param filePath
	 * @param writestr
	 */
	public static void write(String filePath, String writeStr) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(writeStr);
			writer.close();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void main(String args[]) {
		System.out.println("!!! Main class executed !!!");
		/* create an instance of xml suite and assign a name for it */
		XmlSuite suite = new XmlSuite();
		suite.setName("Suite");

		suite.addListener("retryAnalyser.RetryListener");
		suite.setParallel(XmlSuite.ParallelMode.TESTS);
		suite.setThreadCount(getThreadCount());
		suite.setDataProviderThreadCount(getDataProviderThreadCount());

		/* create an instance of xml test and assign to it */
		XmlClass cls = new XmlClass();
		cls.setName("cucumberExecutor.CucumberRunner");

		for (String browser : getInstances()) {
			XmlTest test = new XmlTest(suite);
			test.addParameter("instance", browser);
			test.setClasses(Arrays.asList(cls));
			test.setName("Session " + browser);
		}

		String xml = suite.toXml();
		write("./testng.xml", xml);

	}
		
}
