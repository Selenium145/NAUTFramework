package pageObjects;

import java.net.MalformedURLException;

import com.drivers.mobile.MobileDriverManager;
import com.drivers.web.WebDriverManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class CommonUIStepDefs {
	

	@Given("^user launches the browser$")
    public void user_launch_the_browser(){
		WebDriverManager.startDriver();		
    }

    @And("^user closes the browser$")
    public void user_quit_the_browser(){
    	WebDriverManager.quitDriver();     	
    }
    
    @Given("^user launches the Android Mobile Instance$")
    public void user_launch_the_Android_Mobile_Instance() throws MalformedURLException{
		MobileDriverManager.androidDriver();		
    }

    @And("^user closes the Android Mobile Instance$")
    public void user_quit_the_Android_Mobile_Instance(){
    	MobileDriverManager.teardown();    	
    }
    
    @Given("^user launches the IOS Mobile Instance$")
    public void user_launch_the_IOS_Mobile_Instance() throws MalformedURLException{
    	MobileDriverManager.iOSDriver();	
    }

    @And("^user closes the IOS Mobile Instance$")
    public void user_quit_the_IOS_Mobile_Instance(){
    	MobileDriverManager.teardown();	
    }

}
