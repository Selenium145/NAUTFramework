package pageObjects;

import com.action.util.WebAction;
import com.testdata.DBManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginPage {

	public static String signIn = "login";
	public static String txtUsername = "email";
	public static String txtPassword = "passwd";
	public static String loginBtn = "SubmitLogin";

	@Given("^I am on the TestApp login page$")
	public void i_am_on_the_testapp_login_page() {

		WebAction.openURL(DBManager.getData("URL"));
		WebAction.click("classname", signIn);
	}

	@When("^I enter \"([^\"]*)\" in the email field$")
	public void i_enter_something_in_the_email_field(String username) {
		WebAction.sendKeys("id", txtUsername, DBManager.getData(username));
	}

	@And("^I enter \"([^\"]*)\" in the password field$")
	public void i_enter_something_in_the_password_field(String password) {
		WebAction.sendKeys("id", txtPassword, DBManager.getData(password));
	}

	@And("^I press Login button$")
	public void i_press_login_button() {
		WebAction.click("id", loginBtn);		
	}

}
