package com.netease.amazing.sdk.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

public class ContactsRestClientTest {
	private static final String BASE_URL = "http://10.240.34.42:8090/MiniSkirtServer";
	private static final String USER_NAME = "xukai";
	private static final String PASSWORD = "123456";
	@Test
	public void getContactsTest(){
		//AccountRestClient accountRestClient = new AccountRestClient(BASE_URL,USER_NAME,PASSWORD);
		/*assertTrue(AccountRestClient.testLogin(BASE_URL,USER_NAME, PASSWORD));
		ContactRestClient contactRestClient = new ContactRestClient(BASE_URL,USER_NAME, PASSWORD);
		Gson gson = new Gson();
		System.out.println(gson.toJson(contactRestClient.getAllContacts()));*/
	}
}
