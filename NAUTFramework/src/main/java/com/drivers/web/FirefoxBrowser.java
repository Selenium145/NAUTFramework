package com.drivers.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.framework.utils.FileManager;
import com.framework.utils.GlobalVariables;


public class FirefoxBrowser implements BrowserInterface{
	
	private WebDriver driver;

	@Override
	public synchronized WebDriver getDriver() {
		String userDir = System.getProperty("user.dir"); //this gives you absolute path of the user directory
		System.setProperty("webdriver.gecko.driver", userDir+"/src/main/resources/drivers/gecko_"+WebDriverManager.getDriverVerison()[1]+".exe");
		
		FirefoxProfile profile = new FirefoxProfile();
		/* disabling download pop*/
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"image/jpeg,application/pdf,application/octet-stream,application/zip");
		
		/* user specified download folder config */
		profile.setPreference("browser.download,folderList", 2);
		
		/*download folder path*/
		profile.setPreference("browser,download.dir",
				FileManager.downloadFolderFilePath());
		
		profile.setPreference("pdfjs.disabled", "true");
		
		/*handling certificate issue*/
		profile.setAcceptUntrustedCertificates(true);
		
		FirefoxOptions option = new FirefoxOptions();
		option.setProfile(profile);
		
		driver=new FirefoxDriver(option);
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime);
		driver.manage().timeouts().pageLoadTimeout(GlobalVariables.waitTime);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}

}
