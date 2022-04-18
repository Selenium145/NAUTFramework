package com.drivers.api;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.testng.Assert;

import com.action.util.GraphQLAction;
import com.action.util.RESTAction;
import com.action.util.SOAPAction;
import com.framework.utils.GlobalVariables;
import com.framework.utils.Reporter;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.testdata.DBManager;

public class APIManager {
	
	public static InheritableThreadLocal<APIActionEngine> apiAction = new InheritableThreadLocal<APIActionEngine>();
	public static String apiType;
	public static String apiDataType = GlobalVariables.configProp.getProperty("APIDataType");

	public static String jsonPath = "./src/test/resources/TestData/JSON/";

	public static void InitializeClient() {
		apiType = DBManager.getData("Type");
		switch (apiType) {
		case "REST":
			apiAction.set(new RESTAction());
			break;
		case "SOAP":
			apiAction.set(new SOAPAction());			
			break;
		case "GRAPH":
			apiAction.set(new GraphQLAction());			
			break;
		default:
			break;
		}
	}

	/**
	 * Executes GET/POST/PUT/DELETE Request
	 *
	 */
	public static void executeRequest(String methodType) {

		switch (methodType.toUpperCase()) {

		case "POST": {			
			apiAction.get().POST(DBManager.getData(methodType),
					apiDataType.equals("JSON") ? getJson() : DBManager.getData("PostRequestBody"));
			break;
		}
		case "GET": {
			if(!apiType.equals("GRAPH")) {
			apiAction.get().GET(DBManager.getData(methodType));}
			else {
			apiAction.get().POST(DBManager.getData("POST"),
					apiDataType.equals("JSON") ? getJson() : DBManager.getData("GET"));}
			break;
		}
		case "PUT": {
			if(!apiType.equals("GRAPH")) {
			apiAction.get().PUT(DBManager.getData(methodType),
					apiDataType.equals("JSON") ? getJson() : DBManager.getData("PutRequestBody"));}
			else {
				apiAction.get().POST(DBManager.getData("POST"),
						apiDataType.equals("JSON") ? getJson() : DBManager.getData("PutRequestBody"));}
			break;
		}
		case "DELETE": {
			if(!apiType.equals("GRAPH")) {
			apiAction.get().DELETE(DBManager.getData(methodType));}
			else {
				apiAction.get().POST(DBManager.getData("POST"),
						apiDataType.equals("JSON") ? getJson() : DBManager.getData("DELETE"));}
			break;
		}
		default: {
			Reporter.addStepLog("<strong><font style='color:red'> !!! INCORRECT METHOD TYPE PROVIDED !!! </font> : "
					+ methodType.toUpperCase() + "</strong>");

			Assert.fail("!!! INCORRECT METHOD TYPE PROVIDED !!! " + methodType);

		}
		}
	}
	

	/*
	 * Gson to read data from JSON file
	 * 
	 */
	public static String getJson() {		
		Gson gson = new Gson();
		String entity = "";
		try {
			System.out.println(jsonPath + GlobalVariables.currentTag + ".json");
			Object jsonObject = gson.fromJson(new FileReader(jsonPath + GlobalVariables.currentTag + ".json"),
					Object.class);
			entity = gson.toJson(jsonObject);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

}
