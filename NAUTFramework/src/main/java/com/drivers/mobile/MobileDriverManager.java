package com.drivers.mobile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.framework.utils.GlobalVariables;
import com.framework.utils.LoggerHelper;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class MobileDriverManager {

	private static Logger log = LoggerHelper.getLogger(MobileDriverManager.class);
	String OS;	
	public static WebDriver driver = null;
	public static String buildType = GlobalVariables.configProp.getProperty("OSType");
	public static Method methodName = null;
	public static String buildPath = null;
	

	/*
	 * This method starts Appium server depending upon your OS.
	 * 
	 * @throws Exception Unable to start appium server
	 */
	public static void configureDriver() throws Exception {
		String OS = System.getProperty("os.name").toLowerCase();
		try {
			startAppiumServer(OS);
			log.info("Appium server started successfully");			
		} catch (Exception e) {
			log.error("Unable to start appium server");
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * This method stops Appium server depending upon your OS.
	 * 
	 * @throws Exception Unable to stop appium server
	 */
	public void stopAppium() throws Exception {
		try {
			stopAppiumServer(OS);
			log.info("Appium server stopped successfully");

		} catch (Exception e) {
			log.error("Unable to stop appium server");
			throw new Exception(e.getMessage());
		}
	}

	public static void startAppiumServer(String os) throws ExecuteException, IOException, InterruptedException {
		if (os.contains("windows")) {
			CommandLine command = new CommandLine("cmd");
			command.addArgument("/c");
			command.addArgument("C:/Program Files/nodejs/node.exe");
			command.addArgument("C:/Appium/node_modules/appium/bin/appium.js");
			command.addArgument("--address", false);
			command.addArgument("127.0.0.1");
			command.addArgument("--port", false);
			command.addArgument("4723");
			command.addArgument("--full-reset", false);

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);
			Thread.sleep(5000);
		} else if (os.contains("mac os x")) {
			CommandLine command = new CommandLine("/Applications/Appium.app/Contents/Resources/node/bin/node");
			command.addArgument("/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js", false);
			command.addArgument("--address", false);
			command.addArgument("127.0.0.1");
			command.addArgument("--port", false);
			command.addArgument("4723");
			command.addArgument("--full-reset", false);
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);
			Thread.sleep(5000);
		} else if (os.contains("linux")) {
			// Start the appium server
			System.out.println("ANDROID_HOME : ");
			System.getenv("ANDROID_HOME");
			// System.out.println("PATH :" +System.getenv("PATH"));
			CommandLine command = new CommandLine("/bin/bash");
			command.addArgument("-c");
			command.addArgument("~/.linuxbrew/bin/node");
			command.addArgument("~/.linuxbrew/lib/node_modules/appium/lib/appium.js", true);
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);
			Thread.sleep(5000); // Wait for appium server to start

		} else {
			log.info(os + "is not supported yet");
		}
	}

	public void stopAppiumServer(String os) throws ExecuteException, IOException {
		if (os.contains("windows")) {
			CommandLine command = new CommandLine("cmd");
			command.addArgument("/c");
			command.addArgument("Taskkill /F /IM node.exe");

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);
		} else if (os.contains("mac os x")) {
			String[] command = { "/usr/bin/killall", "-KILL", "node" };
			Runtime.getRuntime().exec(command);
			log.info("Appium server stopped");
		} else if (os.contains("linux")) {
			// need to add
		}
	}
	
	public static String choosebuildfile(String invokeDriver){
		String appPath = null;
		if(invokeDriver.equals("android")){
			appPath = GlobalVariables.configProp.getProperty("AndroidAppPath");
			return appPath;
		}
		else if(invokeDriver.equals("iOS")){
			appPath = GlobalVariables.configProp.getProperty("IOSAppPath");
			return appPath;
		}

		return appPath;
	}
	
	/*
	 *  This method creates the android driver 
	 */
	@SuppressWarnings("rawtypes")
	public synchronized static void androidDriver() throws MalformedURLException{
		File app = new File(buildPath);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformName","Android");
		capabilities.setCapability("appPackage", "net.slideshare.mobile");
		capabilities.setCapability("appActivity", "net.slideshare.mobile.ui.SplashActivity");
		capabilities.setCapability("name", methodName.getName());
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
		capabilities.setCapability("automationName", "UiAutomator2");
		driver = new AndroidDriver( new URL("http://localhost:4723/wd/hub"), capabilities);

	}
	
	/*
	 *  This method creates the iOS driver
	 */
	@SuppressWarnings("rawtypes")
	public static void iOSDriver() throws MalformedURLException {
		File app = new File(buildPath);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName","iOS");
		capabilities.setCapability("platformVersion", "8.2");
		capabilities.setCapability("appiumVersion", "1.3.7");
		capabilities.setCapability("name", methodName.getName());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"iPhone 5s"); 
		capabilities.setCapability("app", app.getAbsolutePath());
		driver  = new IOSDriver( new URL("http://localhost:4723/wd/hub"), capabilities);

	}
	
	/*
	 * This method quit the driver after the execution 
	 */	
	public static void teardown(){
		log.info("Shutting down driver");
		driver.quit();
	}
	
	/*
	 * This method returns driver instance 
	 */	
	public static WebDriver getWebDriver(){
		return driver;
	}

}
