package com.netease.amazing.sdk.client;


import com.google.gson.Gson;
import com.netease.amazing.sdk.dto.ContactDTO;
import com.netease.amazing.sdk.utils.RequestURLConstants;

public class ContactRestClient extends AbstractBaseClient {

	public ContactRestClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}
	
	public ContactDTO getAllContacts(){
		/*String requestUrl = baseUrl + RequestURLConstants.GET_ALL_CONTACTS;
		client.addFilter(new HTTPBasicAuthFilter(loginName, password));
		WebResource webResource = client.resource(requestUrl);
		ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String entity = clientResponse.getEntity(String.class);
		Gson gson = new Gson();
		ContactDTO retValue = gson.fromJson(entity, ContactDTO.class);
		return retValue;*/
		return null;
	}

}
