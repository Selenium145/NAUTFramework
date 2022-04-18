package com.framework.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.Assert;

public class ExceptionHandler {
	private static ThreadLocal<Exception> TException = new ThreadLocal<Exception>();
	private static Logger log = LoggerHelper.getLogger(ExceptionHandler.class);

	public synchronized static void handleException(Object exceptionObj) {
		TException.set((Exception) exceptionObj);
		/* logging exception stack trace in debug log file */
		log.debug("!!! Exception Stack Trace below", TException.get());

		/* selenium Exception handled below */
		if (exceptionObj instanceof ElementNotVisibleException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "Although an element is present in the Dom it is not visible(Cannot be interacted with) !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof ElementNotSelectableException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "Although an element is present in the Dom it may be disable(Cannot be clicked/selected) !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof InvalidSelectorException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Selector used to find an element does not return a WebElement !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof NoSuchElementException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Webdriver is unable to identify the elements during runtime !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof NoSuchFrameException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Webdriver is switching to an invalid frame which is not available !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof NoAlertPresentException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Webdriver is switching to an invalid alert which is not available !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof NoSuchWindowException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Webdriver is switching to an invalid window which is not available !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof StaleElementReferenceException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! The reference element is no longer present or got changed in the dom !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof SessionNotCreatedException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! The webdriver is performing the action immediately after 'quitting' the browser !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof TimeoutException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Wait time out Exception !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof WebDriverException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Webdriver is performing the action immediately after closing the browser !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof JavascriptException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Exceptions occured while executing javascript supplied by a user !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof ScreenshotException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Exception occured as it is impossible to screenshot  !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		}
		/* Java Exception Handled below */
		else if (exceptionObj instanceof NullPointerException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Exception is raised for referring to the members of null object !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof ArrayIndexOutOfBoundsException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Exception is thrown to indicate that an array is accessed with illegal index !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof StringIndexOutOfBoundsException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Exception is thrown by String class methods to indicate that the index is either negative or size of string has been full !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
		} else if (exceptionObj instanceof ClassNotFoundException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Exception is raised when trying to access a class whose defination is not found !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());

		}else if (exceptionObj instanceof FileNotFoundException) {
			Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
					+ "!!! Exception is raised when a file is not accessible or does not open !!! For more detail check debug log."
					+ exceptionObj.getClass().getSimpleName());
	}else if (exceptionObj instanceof IOException) {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Exception thrown when an input,output operation got failed or interrupted !!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}else if (exceptionObj instanceof InterruptedException) {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Exception thrown when a therading is waiting,sleeping,or doing some processing and it is interrupted !!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}
		
	else if(exceptionObj instanceof ConnectException) {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Exception occured while attempting to connect a socket to a remote address or a port!!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}else if(exceptionObj instanceof BindException) {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Unable to bind a socket to a local host!!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}else if(exceptionObj instanceof NoRouteToHostException) {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Exception due to a network error,it is impossible to find a route to remote host!!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}else if(exceptionObj instanceof InterruptedIOException) {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Read operation is blocked for sufficient time to cause a network timeout!!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}
	/*SQL exception*/
	else if(exceptionObj instanceof SQLSyntaxErrorException) {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Wrong Sql Syntax Provided !!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}else if(exceptionObj instanceof SQLException) {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Sql Exception has occured !!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}
		/*Other Exceptions*/
	else {
		Assert.fail(CalendarUtils.getTimeStamp("MM/dd/yyyy HH:mm:ss")
				+ "!!! Failure due to exception !!! For more detail check debug log."
				+ exceptionObj.getClass().getSimpleName());
	}
	}
		
}
	
