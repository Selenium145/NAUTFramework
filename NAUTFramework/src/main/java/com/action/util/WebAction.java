package com.action.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.drivers.web.WebDriverManager;
import com.framework.utils.ExceptionHandler;
import com.framework.utils.Report;


/*
 * Class to handle web UI actions
 * 
 * @author 10675365
 * 
 */

public class WebAction {	
	
	/*
	 * get - Open webURL
	 * 
	 */
	public static final void openURL(String openUrl) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().get(openUrl);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("get");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * click - To click on WebElement 
	 * 
	 */
	public static final void click(String locator, String objValue) {
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);			
			element.click();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("click");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * click - To double click on WebElement 
	 * 
	 */
	public static final void doubleClick(String locator, String objValue) {
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);			
			new Actions(WebDriverManager.getDriver()).doubleClick(element).perform();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("doubleClick");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * click - To contextClick on WebElement
	 *  
	 */
	public static final void contextClick(String locator, String objValue) {
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);			
			new Actions(WebDriverManager.getDriver()).contextClick(element).perform();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("contextClick");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * click - To clear text on Textbox 
	 * 
	 */
	public static final void clear(String locator, String objValue) {
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);			
			element.clear();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Clear");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * Sendkeys - To type text in textbox  
	 * 
	 */
	public static final void sendKeys(String locator, String objValue, String value) {
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);			
			element.sendKeys(value);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("sendKeys");
			Report.printValue(value);
			Report.printStatus(status);
		}
	}
	
	/*
	 * closeBrowser - To close current instance of browser  
	 * 
	 */
	public static final void closeBrowser() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().close();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Close Browser");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * Quit Browser - To quit all browser instance  
	 * 
	 */
	public static final void quitBrowser() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().quit();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Quit Browser");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * Pause - Hard wait to pause the execution of thread. 
	 * 
	 */
	public static final void pause(long timeInMillisec) {
		String status = "FAIL";
		try {
			Thread.sleep(timeInMillisec);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("PAUSED");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * isAlertPresent - Return true if Alert popup exist  
	 * 
	 */
	public static final boolean isAlertPresent() {
		boolean isAlertPresent = false;
		String status = "FAIL";
		try {
			ExpectedCondition<Alert> alertIsPresent = ExpectedConditions.alertIsPresent();
			
			if (alertIsPresent != null)
				isAlertPresent = true;
			status = "PASS";			
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("isAlertPresent");			
			Report.printStatus(status);
		}
		return isAlertPresent;
	}
	
	/*
	 * Switch to Alert popup  
	 * 
	 */
	public static final Alert switchToAlert() {
		Alert alert = null;
		String status = "FAIL";
		try {
			alert = WebDriverManager.getDriver().switchTo().alert();			
			status = "PASS";			
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Switch to Alert");			
			Report.printStatus(status);
		}
		return alert;
	}
	
	/*
	 * Accept Alert popup  
	 * 
	 */
	public static final void acceptAlert(Alert alert) {
		String status = "FAIL";
		try {
			alert.accept();			
			status = "PASS";			
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Accept Alert");			
			Report.printStatus(status);
		}		
	}
	
	/*
	 * Dismiss Alert popup  
	 * 
	 */
	public static final void dismissAlert(Alert alert) {
		String status = "FAIL";
		try {
			alert.dismiss();			
			status = "PASS";			
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Dismiss Alert");			
			Report.printStatus(status);
		}		
	}
	
	/*
	 * Dismiss Alert popup  
	 * 
	 */
	public static final String getAlertText(Alert alert) {
		String alertText = null;
		String status = "FAIL";
		try {
			alertText = alert.getText();			
			status = "PASS";			
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Getting Alert text");			
			Report.printStatus(status);
		}	
		return alertText;
	}
	
	/*
	 * Move to element 
	 * 
	 */
	public static final void moveToElement(String locator, String objValue) {		
		String status = "FAIL";
		try {
			WebDriver driver = WebDriverManager.getDriver();
			WebElement element = getElement(driver, locator, objValue);
			new Actions(driver).moveToElement(element);			
			status = "PASS";			
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Move to Element");			
			Report.printStatus(status);
		}		
	}
	
	/*
	 * Drag and drop 
	 * 
	 */
	public static final void dragAndDrop(String locator1, String objValue1, String locator2, String objValue2) {		
		String status = "FAIL";
		try {
			WebDriver driver = WebDriverManager.getDriver();
			WebElement source = getElement(driver, locator1, objValue1);
			WebElement destination = getElement(driver, locator2, objValue2);
			new Actions(driver).dragAndDrop(source, destination);			
			status = "PASS";			
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Drag and drop");			
			Report.printStatus(status);
		}		
	}
	
	/*
	 * getText - Get text of UI element 
	 * 
	 */
	public static final String getText(String locator, String objValue) {
		String text = "";
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
			text = element.getText();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Text");			
			Report.printStatus(status);
		}
		return text;
	}
	
	/*
	 * getAttributeValue - Get Value of UI element 
	 * 
	 */
	public static final String getAttribute(String locator, String objValue, String attribute) {
		String value = "";
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
			value = element.getAttribute(attribute);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get attribute " + attribute);			
			Report.printStatus(status);
		}
		return value;
	}
	
	/*
	 * isElementPresent - Return true if element exist in DOM  
	 * 
	 */
	public static final void isPresent(String locator, String objValue) {
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
			if(element!=null)
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("isElementPresent");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * Highlight WebElement
	 * 
	 */
	public static final void highlightElement(String locator, String objValue) {
		String status = "FAIL";
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
			js.executeScript("arguments[0].setAttribute('style','background:yellow; border:2px solid red;');", element);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Highlight Element");
			Report.printStatus(status);
		}
	}
	
	/*
	 * IsDisplayed - Return true if element displayed  
	 * 
	 */
	public static final boolean isDisplayed(String locator, String objValue) {
		boolean isDisplayed = false;
		String status = "FAIL";
		try {			
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
			isDisplayed = element.isDisplayed();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("IsDisplayed");			
			Report.printStatus(status);
		}
		return isDisplayed;
	}
	
	/*
	 * IsEnabled - Return true if element Enabled  
	 * 
	 */
	public static final boolean isEnabled(String locator, String objValue) {
		boolean isEnabled = false;
		String status = "FAIL";
		try {			
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
			isEnabled = element.isEnabled();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("IsEnabled");			
			Report.printStatus(status);
		}
		return isEnabled;
	}
	
	/*
	 * IsSelected - Return true if element Selected  
	 * 
	 */
	public static final boolean isSelected(String locator, String objValue) {
		boolean isSelected = false;
		String status = "FAIL";
		try {			
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
			isSelected = element.isSelected();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("IsSelected");			
			Report.printStatus(status);
		}
		return isSelected;
	}
	
	/*
	 * javascript click 
	 * 
	 */
	public static final void jsClick(String locator, String objValue) {
		WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("arguments[0].click();", element);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("java script click");
			Report.printStatus(status);
		}
	}
	
	/*
	 * getting page url 
	 * 
	 */
	public final String getCurrentUrl() {
		String currentUrl = "undefine";
		String status = "FAIL";
		try {
			currentUrl = WebDriverManager.getDriver().getCurrentUrl();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("get current url");
			Report.printStatus(status);
		}
		return currentUrl;
	}
	
	/*
	 * Getting page source
	 * 
	 */
	public final String getPageSource() {
		String pageSource = "undefine";
		String status = "FAIL";
		try {
			pageSource = WebDriverManager.getDriver().getPageSource();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Getting page source");
			Report.printStatus(status);
		}
		return pageSource;
	}
	
	/*
	 * Scroll to bottom
	 * 
	 */
	public final static void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll to bottom");
			Report.printStatus(status);
		}
	}
	
	/*
	 * Scroll to up
	 * 
	 */
	public final static void scrollToUp() {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("window.scrollBy(0,-350)", "");
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll up");
			Report.printStatus(status);
		}
	}
	
	/*
	 * Scroll by pixel
	 *  
	 */
	public static final void ScrollByPixel(String value) {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("window.scrollBy(0," + value + ")");
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll By pixel");
			Report.printStatus(status);
		}
	}
	
	/*
	 * Scroll to element
	 *  
	 */
	public static final void ScrollToElement(String locator, String objValue) {
		WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("arguments[0].scrollIntoView();", element);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll To element");
			Report.printStatus(status);
		}
	}
	
	/*
	 * Switch frame by name
	 *  
	 */
	public static final void switchFrameByName(String name) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().frame(name);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch frame by name");
			Report.printValue(name);
			Report.printStatus(status);
		}
	}
	
	/*
	 * switch frame by index
	 *  
	 */
	public static final void switchFrameByIndex(int index) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().frame(index);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch frame by index");
			Report.printValue(index + "");
			Report.printStatus(status);
		}
	}
	
	/*
	 * switch frame by frame webelement
	 * 
	 */
	public final void switchFrameByWebElement(String locator, String objValue) {
		String status = "FAIL";
		try {
			WebElement element = getElement(WebDriverManager.getDriver(), locator, objValue);
			WebDriverManager.getDriver().switchTo().frame(element);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch frame by webelelement");
			Report.printStatus(status);
		}
	}
	
	/*
	 * switch to default content
	 * 
	 */
	public static final void switchToDefaultContent() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().defaultContent();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch to default content");
			Report.printStatus(status);
		}
	}
	
	/*
	 * switch to Parant frame
	 * 
	 */
	public static final void switchToParentFrame() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().parentFrame();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch to parent frame");
			Report.printStatus(status);
		}
	}
	
	/*
	 * select by value
	 * 
	 */
	public final void selectByValue(Select select, String value) {
		String status = "FAIL";
		try {
			select.selectByValue(value);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("select by value");
			Report.printStatus(status);
		}
	}
	
	/*
	 * select by index
	 * 
	 */
	public static final void selectByIndex(Select select, int index) {
		String status = "FAIL";
		try {
			select.selectByIndex(index);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("select by index");
			Report.printValue(index + "");
			Report.printStatus(status);
		}
	}
	
	/*
	 * select by visible text
	 * 
	 */
	public static final void selectByVisibleText(Select select, String visibleText) {
		String status = "FAIL";
		try {
			select.selectByVisibleText(visibleText);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("select by visible text");
			Report.printValue(visibleText);
			Report.printStatus(status);
		}
	}
	
	/*
	 * Deselect all the select options in the select dropdown
	 * 
	 */
	public static final void deslectAllOptions(Select select) {
		String status = "FAIL";
		try {
			select.deselectAll();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("deselect all options");
			Report.printStatus(status);
		}
	}
	
	/*
	 * public utils to get all the options available in dropdown 
	 * 
	 */
	public final List<WebElement> getSelectOptions(Select select) {
		String status = "FAIL";
		List<WebElement> options = null;
		try {
			options = select.getOptions();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("select all options");
			Report.printStatus(status);
		}
		return options;
	}
	
	/*
	 * JSwait for page load
	 */
	public static final boolean waitForPageLoad() {
		String status = "FAIL";
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		boolean isPageLoad = false;
		try {
			while (isPageLoad == false) {
				isPageLoad = js.executeScript("return document.readyState").toString().equals("complete");
				status = "PASS";
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait for page load");
			Report.printStatus(status);
		}
		return isPageLoad;
	}
	
	/*
	 * get list of element by directly passing locator and locator value
	 * 
	 * 
	 */
	public final List<WebElement> getElements(String locator, String objValue) {
		List<WebElement> lstElement = null;
		String status = "FAIL";
		try {
			lstElement = getElements(WebDriverManager.getDriver(), locator, objValue);						
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Elements");
			Report.printValue(objValue);
			Report.printValueType(locator);
			Report.printStatus(status);
		}
		return lstElement;
	}
	
	
	/*
	 * validatePageTitle - Get page title from DOM and validate it with input string  
	 */
	public static final void validateTitle(String pageTitle) {
		String status = "FAIL";
		try {			
			if(WebDriverManager.getDriver().getTitle().equals(pageTitle))
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("validatePageTitle");			
			Report.printStatus(status);
		}
	}
	
	/*
	 * public final util to navigate forward
	 * 
	 */
	public static final void navigateForward() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().navigate().forward();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Navigate Forward");
			Report.printStatus(status);
		}
	}

	/*
	 * public final util to navigate backward
	 * 
	 */
	public static final void navigateBackward() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().navigate().back();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Navigate Backward");
			Report.printStatus(status);
		}
	}
	
	/*
	 * public final util to refresh browser
	 * 
	 */
	public static final void refresh() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().navigate().refresh();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Refresh");
			Report.printStatus(status);
		}
	}
	
	/*
	 * get window handles
	 * 
	 */
	public static final Set<String> getWindowHandles() {
		Set<String> handles = null;
		String status = "FAIL";
		try {
			handles = WebDriverManager.getDriver().getWindowHandles();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get window handles");
			Report.printStatus(status);
		}
		return handles;
	}
	
	/*
	 * Navigate to n'th window
	 *
	 */
	public static final String getWindowHandle(Set<String> windowHandles, int nthWindow) {
		String windowHandle = "undefine";
		String status = "FAIL";
		Iterator<String> iterator = windowHandles.iterator();
		try {
			int i = 1;
			while (iterator.hasNext()) {
				if (i == nthWindow) {
					windowHandle = iterator.next();
					break;
				} else {
					iterator.next();

				}
				i += 1;
				status = "PASS";
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get nthwindow handle");
			Report.printStatus(status);
		}
		return windowHandle;
	}
	
	/*
	 * switch to window
	 * 
	 */
	public static final void switchToWindow(String windowHandle) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().window(windowHandle);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Switch to Window");
			Report.printStatus(status);
		}
	}
	
	public static WebElement getElement(WebDriver driver, String locator, String attributeValue)
	{
		WebElement wb = null;
		try 
		{
			switch(locator.toLowerCase())
			{
			case "id":
				wb = driver.findElement(By.id(attributeValue));
				break;
			case "name":
				wb = driver.findElement(By.name(attributeValue));
				break;
			case "classname":
				wb = driver.findElement(By.className(attributeValue));
				break;
			case "tagname":
				wb = driver.findElement(By.tagName(attributeValue));
				break;
			case "linktext":
				wb = driver.findElement(By.linkText(attributeValue));
				break;
			case "partiallinktext":
				wb = driver.findElement(By.partialLinkText(attributeValue));
				break;
			case "css":
				wb = driver.findElement(By.cssSelector(attributeValue));
				break;
			case "xpath":
				wb = driver.findElement(By.xpath(attributeValue));
				break;						
		}
			
			return wb;
		}
		catch (Exception e)
		{
			ExceptionHandler.handleException(e);			
			return null;
		}
				
	}
	
	public static List<WebElement> getElements(WebDriver driver, String locator, String attributeValue)
	{
		List<WebElement> wb = null;
		try 
		{
			switch(locator.toLowerCase())
			{
			case "id":
				wb = driver.findElements(By.id(attributeValue));
				break;
			case "name":
				wb = driver.findElements(By.name(attributeValue));
				break;
			case "classname":
				wb = driver.findElements(By.className(attributeValue));
				break;
			case "tagname":
				wb = driver.findElements(By.tagName(attributeValue));
				break;
			case "linktext":
				wb = driver.findElements(By.linkText(attributeValue));
				break;
			case "partiallinktext":
				wb = driver.findElements(By.partialLinkText(attributeValue));
				break;
			case "css":
				wb = driver.findElements(By.cssSelector(attributeValue));
				break;
			case "xpath":
				wb = driver.findElements(By.xpath(attributeValue));
				break;						
		}
			
			return wb;
		}
		catch (Exception e)
		{
			ExceptionHandler.handleException(e);			
			return null;
		}
				
	}

}
