package com.netease.amazing.sdk.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

public class AccountRestClientTest extends BaseTest{
	@Test
	public void loginTest() throws ClientProtocolException, IOException{
		//AccountRestClient accountRestClient = new AccountRestClient(BASE_URL,USER_NAME,PASSWORD);
		assertTrue(AccountRestClient.testLogin(BASE_URL,USER_NAME, PASSWORD));
	}
}
