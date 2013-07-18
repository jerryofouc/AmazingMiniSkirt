package com.netease.amazing.sdk.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.netease.amazing.sdk.dto.ContactDTO;
import com.netease.amazing.sdk.utils.RequestURLConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class ContactRestClient extends AbstractBaseClient {

	public ContactRestClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}
	
	public ContactDTO getAllContacts(){
		String requestUrl = baseUrl + RequestURLConstants.GET_ALL_CONTACTS;
		client.addFilter(new HTTPBasicAuthFilter(loginName, password));
		WebResource webResource = client.resource(requestUrl);
		ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String entity = clientResponse.getEntity(String.class);
		Gson gson = new Gson();
		ContactDTO retValue = gson.fromJson(entity, ContactDTO.class);
		return retValue;
	}

}
