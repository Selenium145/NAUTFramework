package pageObjects;

import org.apache.http.HttpStatus;

import com.drivers.api.APIManager;
import com.drivers.api.ApiUtils;
import com.testdata.DBManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonAPIStepDefs {

	@Given("^user creates client instance for api call$")
	public void user_creates_client_instance_for_api_call() throws Throwable {
		APIManager.InitializeClient();
	}

	@When("^user execute \"([^\"]*)\" request$")
	public void user_execute_something_request(String methodType) throws Throwable {
		APIManager.executeRequest(methodType);
	}

	@And("^user verifies response \"([^\"]*)\" body$")
	public void user_verifies_response_body(String json) throws Throwable {
		ApiUtils.verifyJson(APIManager.apiAction.get().getResponseBody(), DBManager.getData(json));
	}

	@And("^user verifies generated id$")
	public void user_verifies_generated_id() throws Throwable {
		ApiUtils.verifyKeyValue(APIManager.apiAction.get().getResponseBody(), "id");
	}
	
	@And("^user verifies generated value \"([^\"]*)\"$")
    public void user_verifies_generated_value_something(String value) throws Throwable {
		ApiUtils.verifyXMLValue(APIManager.apiAction.get().getResponseBody(), value);
	}

	@Then("^user verifies response code as \"([^\"]*)\"$")
	public void user_verifies_response_code_as_something(String status) throws Throwable {
		switch (status) {
		case "201": {			
			ApiUtils.verifyStatusCode(APIManager.apiAction.get().getResponse(), HttpStatus.SC_CREATED);
			break;
		}
		case "200": {
			ApiUtils.verifyStatusCode(APIManager.apiAction.get().getResponse(), HttpStatus.SC_OK);
			break;
		}
		case "204": {
			ApiUtils.verifyStatusCode(APIManager.apiAction.get().getResponse(), HttpStatus.SC_NO_CONTENT);
			break;
		}
		case "400": {
			ApiUtils.verifyStatusCode(APIManager.apiAction.get().getResponse(), HttpStatus.SC_BAD_REQUEST);
			break;
		}
	  }
	}

	@And("^user resets client parameters$")
	public void user_resets_client_parameters() throws Throwable {
		APIManager.apiAction.get().resetClient();
	}

}
