package com.jira.util;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class JiraClient {
	
	static String URL = "https://nautjira.atlassian.net/rest/api/2/issue";
	static String jiraUserName = "TestUserNewJira@gmail.com";
	static String jiraPassword = "DokKSz3zhos4nYIJzOBU5BC0";
	String attachement = "C:\\Users\\10675365\\Desktop\\implinks.txt";
	String summary = "Automation Summary";
	String description = "Automation description";
	
	
	public static void main(String[] args) {
		
		try {
			createDefectinJira("Test","Test","Test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
	public static void createDefectinJira(String summary, String description, String attachement) throws ClientProtocolException, IOException {
		
		
	}

}
