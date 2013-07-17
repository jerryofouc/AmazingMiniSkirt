package com.netease.amazing.sdk.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.netease.amazing.sdk.dto.UserDTO;
import com.netease.amazing.sdk.utils.RequestURLConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class AccountRestClient extends AbstractClient {
	private static Client client = Client.create();
	public AccountRestClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}
	
	/**
	 * ����Ƿ��¼�ɹ�
	 * @param baseURL ����������
	 * @param userName	�û���
	 * @param password	����
	 * @return  �����Ƿ�ɹ�
	 */
	public static boolean  testLogin(String baseURL, String userName, String password){
		String requestUrl = baseURL + RequestURLConstants.TEST_LOGIN_URL;
		client.addFilter(new HTTPBasicAuthFilter(userName, password));
		WebResource webResource = client.resource(requestUrl);
		ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		if(Status.OK.getStatusCode() == clientResponse.getStatus()){
			return true;
		}else{
			return false;
		}
	}
}
