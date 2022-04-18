package com.drivers.api;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.framework.utils.LoggerHelper;
import com.framework.utils.Reporter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pojo.User;

public class ApiUtils {

	static Logger log = LoggerHelper.getLogger(ApiUtils.class);
	static Gson gson = new Gson();
	static String responseBody;
	
	public static synchronized void verifyStatusCode(HttpResponse response, long status) {
		log.debug("Verifying status code" + status);

		Reporter.addStepLog("<strong>Actual Status   :</strong>" + response.getStatusLine().getStatusCode());
		Reporter.addStepLog("<strong>Expected Status :</strong>" + status);

		Assert.assertEquals(response.getStatusLine().getStatusCode(), status,
				"Status code verification failed.Received Status code:" + response.getStatusLine().toString());		
		
	}
	
	public static synchronized void verifyKeyValue(String responseBody, String key) {		
		JsonObject jobj = new Gson().fromJson(responseBody, JsonObject.class);
		String value = jobj.get(key).toString();
		
		Reporter.addStepLog("<strong>Generated Key and Value : </strong>" + key+" : "+ value);
		Assert.assertNotEquals(value, "","Key value does not exist:" + key);		
	}
	
	public static synchronized void verifyJson(String actResponse, String expResponse) throws ParseException, IOException {
		
		User actObj = gson.fromJson(actResponse, User.class);
		User expObj = gson.fromJson(expResponse,  User.class);		
		
		Reporter.addStepLog("<strong>Actual Param   :</strong>" + actObj.toString());
		Reporter.addStepLog("<strong>Expected Param :</strong>" + expObj.toString());

		Assert.assertEquals(actObj.name, expObj.name, "Response body verification failed. Received User name :" 
				+ actObj.name);
		Assert.assertEquals(actObj.job, expObj.job, "Response body verification failed. Received User job :" 
				+ actObj.name);
		Reporter.addStepLog("<strong>Field values are validated successfully</strong>");
		
	}
	
	public static synchronized void verifyXMLValue(String responseBody, String key) {		
				
		Reporter.addStepLog("<strong>Generated Value : </strong>" + key);
		assertTrue(responseBody.contains(key));		
	}
	
//	public static void verifyOrderIndependentJson(HttpResponse actResponse, HttpResponse expResponse) {	
//		JsonObject actObj = (JsonObject) JsonParser.parseString(actResponse.getEntity().toString());		
//		JsonObject expObj = (JsonObject) JsonParser.parseString(actResponse.getEntity().toString());
//		
//		Reporter.addStepLog("<strong>Actual Status   :</strong>" + actObj.toString());
//		Reporter.addStepLog("<strong>Expected Status :</strong>" + expObj.toString());
//
//		Assert.assertEquals(actObj, expObj, "Response body verification failed. Received response json:" 
//				+ actResponse.getEntity().toString());
//		
//	}
		
}