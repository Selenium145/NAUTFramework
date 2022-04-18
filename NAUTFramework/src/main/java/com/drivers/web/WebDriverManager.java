package com.drivers.web;

import java.net.URL;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.framework.utils.ExceptionHandler;
import com.framework.utils.FileManager;
import com.framework.utils.GlobalVariables;
import com.framework.utils.LoggerHelper;
import com.framework.utils.SystemProcess;

public class WebDriverManager {
	// private static String browser = null;
	private static String[] driverVerison = null;
	private static String browserVersion = null;
	private static String platform = null;
	private static boolean remote = false;
	private static String huburl = null;

	public static DesiredCapabilities capabilities;

	// Initializing the webdriver object as a thread local for parallel execution
	private static ThreadLocal<WebDriver> TDriver = new ThreadLocal<WebDriver>();
	private static Logger log = LoggerHelper.getLogger(WebDriverManager.class);
	
	//private static BrowserInterface browserInterface;

	/* configuring remote */
	@SuppressWarnings("unused")
	private static void configRemote() {
		/* reading from cmdline */
		String cmdRemote = System.getProperty("remote");
		if (cmdRemote != null) {
			remote = Boolean.parseBoolean(cmdRemote);
		} else {
			try {
				/* reading from a property file */
				remote = Boolean.parseBoolean(GlobalVariables.configProp.getProperty("remote"));
			} catch (Exception e) {
				// if remote is not available in property file remote=false;
			}
		}
	}

	/* configuring browser version */
	private static void configDriverVersion() {

		/* reading from cmdline */

		String cmdBrowserVersion = System.getProperty("driverVerison");
		if (cmdBrowserVersion != null) {
			driverVerison = cmdBrowserVersion.split(",");
		} else {
			/* reading from a property file */
			driverVerison = GlobalVariables.configProp.getProperty("driverVerison").split(",");
		}

	}

	/* configuring browser version - Romote */
	private static void configBrowserVersion() {
		/* these settings is only configured for remote execution */
		if (remote == true) {
			/* reading from cmdline */
			String cmdBrowserVersion = System.getProperty("browserVersion");
			if (cmdBrowserVersion != null) {
				browserVersion = cmdBrowserVersion;
			} else {
				/* reading from a property file */
				browserVersion = GlobalVariables.configProp.getProperty("browserVersion");
			}
		}
	}

	/* configuring platform */
	private static void configPlatform() {
		/* these settings is only configured for remote execution */
		if (remote == true) {
			/* reading from cmdline */
			String cmdPlatform = System.getProperty("platform");

			if (cmdPlatform != null) {
				platform = cmdPlatform;
			} else {
				/* reading from a property file */
				platform = GlobalVariables.configProp.getProperty("platform");
			}
		}
	}

	/* configuring Hub url */
	private static void configHubUrl() {
		/* these settings is only configured for remote execution */
		if (remote == true) {
			/* reading from cmdline */
			String cmdHubUrl = System.getProperty("huburl");

			if ((cmdHubUrl != null)) {
				huburl = cmdHubUrl;
			} else {
				/* reading from a property file */
				huburl = GlobalVariables.configProp.getProperty("huburl");
			}
		}
	}

	private static void setPlatfromCapability() {
		switch (platform) {
		case "WINDOWS":
			capabilities.setPlatform(Platform.WINDOWS);
			break;
		case "WIN10":
			capabilities.setPlatform(Platform.WIN10);
			break;
		case "ANY":
			capabilities.setPlatform(Platform.ANY);
			break;
		default:
			log.debug("!!!INCORRECT PLATFORM PROVIDED!!!" + platform);
		}
	}

	/**
	 * setting remote webdriver
	 *
	 */
	private static void setRemoteWebDriver(String browserName) {
		switch (browserName) {
		case "incognitochrome":
		case "chrome": {
			ChromeOptions options = new ChromeOptions();

			// capabilities = DesiredCapabilities.chrome();
			capabilities.setVersion(browserVersion);
			setPlatfromCapability();
			capabilities.setCapability("profile.default_content_settings.popups", 0);
			capabilities.setCapability("download.default_directory", FileManager.downloadFolderFilePath());
			capabilities.setCapability("safebrowsing.enabled", true);
			capabilities.acceptInsecureCerts();

			// if (WebDriverManager.getBrowserName().contains("incognito")) {
			if (GlobalVariables.currentBrowser.get().contains("incognito")) {
				options.addArguments("--incognito");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			}
			break;
		}

		case "edge": {
			// capabilities = DesiredCapabilities.edge();
			capabilities.setVersion(browserVersion);
			setPlatfromCapability();
			break;
		}
		case "firefox": {
			// capabilities = DesiredCapabilities.firefox();
			capabilities.setVersion(browserVersion);
			setPlatfromCapability();
			/* disabling download popup */
			capabilities.setCapability("browser.helperApps.neverAsk.saveToDisk",
					"image/jpeg,application/pdf,application/octet-stream,application/zip");
			/* user specified download folder config */
			capabilities.setCapability("browser.download,folderList", 2);
			/* download folder path */
			capabilities.setCapability("browser,download.dir", FileManager.downloadFolderFilePath());
			capabilities.setCapability("pdfjs.disabled", "true");
			capabilities.acceptInsecureCerts();
			break;
		}
		default: {
			Assert.fail("!!!incorrect browser name provided !!!" + browserName);
		}
		}
		try {
			TDriver.set(new RemoteWebDriver(new URL(huburl), capabilities));
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	

	/* setting driver */
	private static void setDriver(String browserName) {
		switch (browserName) {
		case "incognitochrome":
		case "chrome": {
			TDriver.set(new ChromeBrowser().getDriver());
			break;
		}
		case "edge": {
			TDriver.set(new EdgeBrowser().getDriver());
			break;
		}
		case "firefox": {
			TDriver.set(new FirefoxBrowser().getDriver());
			break;
		}
		default: {
			Assert.fail("!!!incorrect browser name provided !!!" + browserName);
		}
		}
	}

	/* configuring driver */
	public static void configureDriver() {
		/* configuring browser */
		// configBrowser();
		/* configuring remote value */
		//configRemote();

		/* configuring chrome driver version */
		configDriverVersion();

		if (remote) {
			configBrowserVersion();
			configHubUrl();
			configPlatform();

		}
	}

	

	public static String[] getDriverVerison() {
		return driverVerison;
	}

	public static String getBrowserVersion() {
		return browserVersion;
	}

	/* start driver */
	public static void startDriver() {
		if (remote == true) {
			setRemoteWebDriver(GlobalVariables.currentBrowser.get());
		} else if (remote == false) {			
			setDriver(GlobalVariables.currentBrowser.get());			
		}
	}

	/* get driver */
	public static WebDriver getDriver() {
		return TDriver.get();
	}

	/* set driver */
	public static void setDriver(WebDriver driver) {
		TDriver.set(driver);
	}

	/**
	 * close browser
	 * 
	 */
	public static void closeDriver() {
		TDriver.get().close();
	}

	/**
	 * quit browser
	 * 
	 */
	public static void quitDriver() {
		TDriver.get().quit();
	}

	/**
	 * kills driver processes
	 * 
	 */
	public static void killDriverProcess() {
		try {
			switch (GlobalVariables.currentBrowser.get()) {
			case "chrome":
				SystemProcess.killProcess("chromedriver.exe");
				break;
			case "firefox":
				SystemProcess.killProcess("geckodriver.exe");
				break;
			case "edge":
				SystemProcess.killProcess("Microsoft Web Driver.exe");
				break;
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

}