package com.action.util;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.drivers.api.APIActionEngine;
import com.framework.utils.GlobalVariables;
import com.framework.utils.Reporter;

/*
 * Class to handle all REST calls(POST/GET/PUT/DELETE)
 * 
 * @author 10675365
 * 
 */

public class RESTAction implements APIActionEngine {
	
	public static HttpClientBuilder clientBuilder;
	private static CloseableHttpClient httpClient;
	private InheritableThreadLocal<HttpResponse> response = new InheritableThreadLocal<HttpResponse>();
	private InheritableThreadLocal<String> responseBody = new InheritableThreadLocal<String>();	

	/*
	 * Client for SSL
	 * 
	 */
	static {
		String authMethod = GlobalVariables.configProp.getProperty("Authentication");
		if (authMethod.equalsIgnoreCase("Basic")) {
			clientBuilder = HttpClients.custom();
			clientBuilder.setSSLSocketFactory(getSSLContext());
			CloseableHttpClient client = clientBuilder.build();
			httpClient = client;
		}
		if (authMethod.equalsIgnoreCase("OAuth2")) {
			clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultHeaders(Arrays.asList(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
			try {
				clientBuilder.setDefaultHeaders(Arrays.asList(new BasicHeader(HttpHeaders.AUTHORIZATION,
				getAccessToken())));
			} catch (OAuthSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthProblemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clientBuilder.setSSLSocketFactory(getSSLContext());
			CloseableHttpClient client = clientBuilder.build();
			httpClient = client;			
			httpClient = client;
			
			//Client creation yet to be added based on env details			
			
		}
		if (authMethod.equalsIgnoreCase("OAuth2")) {
			// Code yet to implement
		}
	}

	/*
	 * Get method - httpGet
	 * 
	 */
	public void GET(String endpoint) {
		try {
			URI uri = new URIBuilder(endpoint).build();
			HttpGet httpGet = new HttpGet(uri);
			Reporter.addStepLog("<strong>Request URL: </strong><br> <pre>" + httpGet.getURI() + "</pre>");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			String resEntity = EntityUtils.toString(httpResponse.getEntity());
			Reporter.addStepLog("<strong>Response Json: </strong><br> <pre>" + resEntity + "</pre>");
			if (httpResponse != null) {
				response.set(httpResponse);
				responseBody.set(resEntity);
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	/*
	 * Post method - HttpPost
	 * 
	 */
	public void POST(String endPoint, String jsonBody) {

		try {

			URI url = new URIBuilder(endPoint).build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("content-type", "application/json");

			StringEntity stringEntity = new StringEntity(jsonBody);
			httpPost.setEntity(stringEntity);
			Reporter.addStepLog("<strong>Request URL: </strong><br> <pre>" + httpPost.getURI() + "</pre>");
			Reporter.addStepLog("<strong>Request Json: </strong><br> <pre>" + jsonBody + "</pre>");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			String resEntity = EntityUtils.toString(httpResponse.getEntity());
			Reporter.addStepLog("<strong>Response Json: </strong><br> <pre>" + resEntity + "</pre>");
			if (httpResponse != null) {
				response.set(httpResponse);
				responseBody.set(resEntity);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	/*
	 * Put method - HttpPut
	 * 
	 */
	public void PUT(String endPoint, String jsonBody) {

		try {

			URI url = new URIBuilder(endPoint).build();
			HttpPut httpPut = new HttpPut(url);
			httpPut.setHeader("content-type", "application/json");
			StringEntity stringEntity = new StringEntity(jsonBody);
			httpPut.setEntity(stringEntity);

			// Execute the request
			Reporter.addStepLog("<strong>Request URL: </strong><br>" + httpPut.getURI());
			Reporter.addStepLog("<strong>Request Json: </strong><br> <pre>" + jsonBody + "</pre>");
			HttpResponse httpResponse = httpClient.execute(httpPut);

			String resEntity = EntityUtils.toString(httpResponse.getEntity());
			Reporter.addStepLog("<strong>Response Json: </strong><br> <pre>" + resEntity + "</pre>");
			if (httpResponse != null) {
				response.set(httpResponse);
				responseBody.set(resEntity);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	/*
	 * Delete method - HttpDelete
	 * 
	 */
	public void DELETE(String endpoint) {
		try {
			URI uri = new URIBuilder(endpoint).build();
			HttpDelete httpDelete = new HttpDelete(uri);
			Reporter.addStepLog("<strong>Request URL: </strong><br> <pre>" + httpDelete.getURI() + "</pre>");
			HttpResponse httpResponse = httpClient.execute(httpDelete);
			if (httpResponse != null) {
				response.set(httpResponse);
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	/*
	 * Client for multi thread
	 * 
	 */
	public static CloseableHttpClient getConcurrentClient(int threadPoolCount) {
		// Create the pool connection manager
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		// Set the pool size
		connectionManager.setMaxTotal(threadPoolCount);

		// Make the client builder
		HttpClientBuilder clientBuilder = HttpClients.custom();
		// Set the connection manager
		clientBuilder.setConnectionManager(connectionManager);
		// Build the client
		CloseableHttpClient client = clientBuilder.build();
		return client;
	}

	public HttpResponse getResponse() {
		return response.get();
	}

	public String getResponseBody() {
		return responseBody.get();
	}

	public void resetClient() throws IOException {
		httpClient.close();
	}

	// Default client
	public static CloseableHttpClient getDefaultClient() {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		return closeableHttpClient;
	}
	
	public static String getAccessToken() throws OAuthSystemException, OAuthProblemException {
		String clientID="";
		String clientSecret="";
		String clientTokenURL="";
		
		String accessToken="";
		
		
		OAuthClient client = new OAuthClient(new URLConnectionClient());
		OAuthClientRequest request = new OAuthClientRequest.TokenRequestBuilder(clientTokenURL)
				.setGrantType(GrantType.CLIENT_CREDENTIALS).buildBodyMessage();
		
		String encodedValue = Base64.getEncoder().encodeToString((clientID +":"+clientSecret).getBytes(StandardCharsets.UTF_8));
		
		request.addHeader("Authorization", "Basic " + encodedValue);
		
		OAuthAccessTokenResponse response = client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class);
		
		accessToken = response.getAccessToken();
		
		return "Bearer " + accessToken;
		
	}

	// In case your service is secure with SSL and Certs
	private static SSLConnectionSocketFactory getSSLContext() {

		TrustStrategy trustStrategy = new TrustStrategy() {

			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				return true;
			}
		};

		HostnameVerifier allVerifier = new NoopHostnameVerifier();
		SSLConnectionSocketFactory connFactory = null;
		try {
			connFactory = new SSLConnectionSocketFactory(
					SSLContextBuilder.create().loadTrustMaterial(trustStrategy).build(), allVerifier);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connFactory;
	}

}
