package com.framework.utils;

import java.time.Duration;
import java.util.Set;

import com.aventstack.extentreports.ExtentReports;

public class GlobalVariables {
public static PropertyFileUtils configProp;
public static Set<String> featureList;
public static InheritableThreadLocal<String> currentFeature = new InheritableThreadLocal<String>();
public static InheritableThreadLocal<String> currentBrowser = new InheritableThreadLocal<String>();
public static String currentTag;

public static String browserName;
public static String driverName;
public static Duration waitTime=Duration.ofSeconds(10);
public static String applicationName;

public static String applicationUrl=null;

//public static ExtentReportManager extentReportManagerObj;
public static ExtentReports extentReport;
}
