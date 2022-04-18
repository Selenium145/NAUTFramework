package com.drivers.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.framework.utils.GlobalVariables;

public class EdgeBrowser implements BrowserInterface{
	
	private WebDriver driver;

	@Override
	public synchronized WebDriver getDriver() {
		
		System.setProperty("webdriver.edge.driver", "src/main/resources/drivers/edge_"+WebDriverManager.getDriverVerison()[2]+".exe");
		this.driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime);
		driver.manage().timeouts().pageLoadTimeout(GlobalVariables.waitTime);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;

	}

}
