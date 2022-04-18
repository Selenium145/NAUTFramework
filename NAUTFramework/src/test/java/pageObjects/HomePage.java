package pageObjects;

import com.action.util.WebAction;
import com.testdata.DBManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class HomePage {

	public static String username = "//span[contains(text(),'vinoth kuamr')]";
	public static String logout = "logout";
	

	@Then("^I should be on the TestApp Home page$")
	public void i_should_be_on_the_testapp_home_page() {

		WebAction.validateTitle(DBManager.getData("PageTitle"));

	}

	@And("^I should see my FirstName \"([^\"]*)\" in user title$")
	public void i_should_see_my_firstname_something_in_user_title(String firstName) {

		WebAction.isPresent("xpath", username);

	}

	@And("^I press Logout button$")
	public void i_press_logout_button() {
				
		WebAction.click("classname", logout);

	}
}
