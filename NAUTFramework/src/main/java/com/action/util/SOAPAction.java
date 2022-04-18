package com.action.util;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import com.drivers.api.APIActionEngine;
import com.framework.utils.GlobalVariables;
import com.framework.utils.Reporter;

/*
 * Class to handle all REST calls(POST/GET/PUT/DELETE)
 * 
 * @author 10675365
 * 
 */

public class SOAPAction implements APIActionEngine{
	
	public static HttpClientBuilder clientBuilder;
	private static CloseableHttpClient httpClient;
	private static InheritableThreadLocal<HttpResponse> response = new InheritableThreadLocal<HttpResponse>();
	private static InheritableThreadLocal<String> responseBody = new InheritableThreadLocal<String>();
	
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
			// Code yet to implement
		}
	}
	
	/*
	 * Post method - HttpPost
	 * 
	 */
	public void POST(String endPoint, String xmlBody) {

		try {

			URI url = new URIBuilder(endPoint).build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("content-type", "text/xml; charset=utf-8");

			StringEntity stringEntity = new StringEntity(xmlBody);
			httpPost.setEntity(stringEntity);
			Reporter.addStepLog("<strong>Request URL: </strong><br> <pre> " + httpPost.getURI() + "</pre>");
			Reporter.addStepLog("<strong>Request Xml: </strong><br> <pre> <textarea>" + xmlBody + "</textarea> </pre>");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			String resEntity = EntityUtils.toString(httpResponse.getEntity());
			Reporter.addStepLog("<strong>Response Xml: </strong><br> <pre> <textarea>" + resEntity + "</textarea> </pre>");
			if (httpResponse != null) {
				response.set(httpResponse);
				responseBody.set(resEntity);
			}

		} catch (Exception e) {
			e.getMessage();
		}
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

	@Override
	public void GET(String endpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PUT(String endpoint, String jsonBody) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DELETE(String endpoint) {
		// TODO Auto-generated method stub
		
	}

}
