package com.netease.amazing.sdk.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.google.gson.Gson;

public class ContactsRestClientTest extends BaseTest{
	@Test
	public void getContactsTest() throws ClientProtocolException, IOException{
		//AccountRestClient accountRestClient = new AccountRestClient(BASE_URL,USER_NAME,PASSWORD);
		assertTrue(AccountRestClient.testLogin(BASE_URL,USER_NAME, PASSWORD));
		ContactRestClient contactRestClient = new ContactRestClient(BASE_URL,USER_NAME, PASSWORD);
		Gson gson = new Gson();
		System.out.println(gson.toJson(contactRestClient.getAllContacts()));
	}
}
