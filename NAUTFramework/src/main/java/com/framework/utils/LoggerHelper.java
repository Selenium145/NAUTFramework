package com.framework.utils;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

/*
 * Class to handle log file
 * 
 * @author 10675365 
 * 
 */
public class LoggerHelper {
	private static boolean root = false;
	public static String logPath;
	private static String logFilePath;

	public synchronized static void createLogFolder() {
		String userDir = System.getProperty("user.dir");
		String folderName = CalendarUtils.getTimeStamp("ddMMyyyy_HHmmss");
		logPath = userDir + "\\logs\\" + folderName;
		logFilePath=logPath+"\\logs.log";
		new File(LoggerHelper.logPath).mkdir();
	}

	public synchronized static Logger getLogger(Class<?> cls) {				
		if (root) {
			return LogManager.getLogger(cls);
		}
		System.out.println("Start "+CalendarUtils.getTimeStamp("yyyy-MM-dd HH:mm:ss.SSS"));
		LoggerHelper.createLogFolder();
		
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
		LayoutComponentBuilder standard = builder.newLayout("PatternLayout");
		standard.addAttribute("pattern", "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n");
		AppenderComponentBuilder logFile = builder.newAppender("File", "File");
		logFile.addAttribute("fileName", logFilePath);
		logFile.add(standard);
		builder.add(logFile);
		
		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(GlobalVariables.configProp.getProperty("logLevel"));		
		rootLogger.add(builder.newAppenderRef("File"));
		builder.add(rootLogger);	
					
		root = true;
		Configurator.initialize(builder.build());		
		return LogManager.getLogger(cls);
	}
}
