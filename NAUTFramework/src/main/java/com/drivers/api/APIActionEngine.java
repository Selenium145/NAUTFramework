package com.drivers.api;

import java.io.IOException;

import org.apache.http.HttpResponse;

public interface APIActionEngine {	
	public void GET(String endpoint);
	public void POST(String endpoint, String jsonBody);
	public void PUT(String endpoint, String jsonBody);
	public void DELETE(String endpoint);
	
	public HttpResponse getResponse();
	public String getResponseBody();
	public void resetClient() throws IOException;
}
