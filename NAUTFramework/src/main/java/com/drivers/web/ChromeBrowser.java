package com.drivers.web;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.framework.utils.FileManager;
import com.framework.utils.GlobalVariables;

public class ChromeBrowser implements BrowserInterface{
	
	private WebDriver driver;

	@Override
	public synchronized WebDriver getDriver() {

		String userDir = System.getProperty("user.dir"); //this gives you absolute path of the user directory
		System.setProperty("webdriver.chrome.driver",userDir+"/src/main/resources/drivers/chromedriver_"+WebDriverManager.getDriverVerison()[0]+".exe");

		Map<String, Object> preferences = new HashMap<String, Object>();

		/* do not show any pop up(windows pop up) while downloading any file */
		preferences.put("profile.default_content_settings.popups", 0);

		/* set default folder */
		preferences.put("download.default_directory", FileManager.downloadFolderFilePath());

		/* disable keep/discard warning messages form Chrome */
		preferences.put("safebrowsing.enabled", "true");

		/* handling certification issue */
		preferences.put("CapabilityType.ACCEPT_SSL_CERTS", "true");

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", preferences);

//		if (WebDriverManager.getBrowserName().contains("incognito")) {
//			options.addArguments("--incognito");
//		}
		//--ignore-certificate-errors

		this.driver = new ChromeDriver(options);
		
		
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime);
		driver.manage().timeouts().pageLoadTimeout(GlobalVariables.waitTime);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		return driver;

	
	}

}
