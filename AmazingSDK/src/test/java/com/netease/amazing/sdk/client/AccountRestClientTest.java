package com.netease.amazing.sdk.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

public class AccountRestClientTest {
	private static final String BASE_URL = "http://10.240.34.42:8080/server";
	private static final String USER_NAME = "xukai";
	private static final String PASSWORD = "123456";
	@Test
	public void loginTest() throws ClientProtocolException, IOException{
		//AccountRestClient accountRestClient = new AccountRestClient(BASE_URL,USER_NAME,PASSWORD);
		assertTrue(AccountRestClient.testLogin(BASE_URL,USER_NAME, PASSWORD));
	}
}
