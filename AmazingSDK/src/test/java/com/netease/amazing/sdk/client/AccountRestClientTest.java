package com.netease.amazing.sdk.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.netease.amazing.sdk.dto.UserDTO;

public class AccountRestClientTest extends BaseTest{
	@Test
	public void loginTest() throws ClientProtocolException, IOException{
		//AccountRestClient accountRestClient = new AccountRestClient(BASE_URL,USER_NAME,PASSWORD);
		assertTrue(AccountRestClient.testLogin(BASE_URL,USER_NAME, PASSWORD));
		
	}
	@Test
	public void getUserInfo() throws ClientProtocolException, IOException{
		AccountRestClient accountRestClient = new AccountRestClient(this.BASE_URL,this.USER_NAME,this.PASSWORD);
		UserDTO user = accountRestClient.getUserInfo();
		System.out.println(user.getId());
	}
	
	@Test
	public void hasLoginTest() throws ClientProtocolException, IOException{
		AccountRestClient accountRestClient = new AccountRestClient(this.BASE_URL,this.USER_NAME,this.PASSWORD);
		boolean haslogin = accountRestClient.hasLogin();
		System.out.println(haslogin);
	}
}
