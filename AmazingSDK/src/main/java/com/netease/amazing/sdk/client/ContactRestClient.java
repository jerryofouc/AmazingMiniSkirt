package com.netease.amazing.sdk.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.netease.amazing.sdk.dto.ContactDTO;
import com.netease.amazing.sdk.utils.RequestURLConstants;
import com.netease.amazing.sdk.utils.Utils;

public class ContactRestClient extends AbstractBaseClient {

	public ContactRestClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}

	public ContactDTO getAllContacts() throws ClientProtocolException,
			IOException {
		String requestUrl = baseUrl + RequestURLConstants.GET_ALL_CONTACTS;
		HttpGet httpget = new HttpGet(requestUrl);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		Gson gson = new Gson();
		ContactDTO retValue = gson.fromJson(EntityUtils.toString(response.getEntity()), ContactDTO.class);
		return retValue;
	}

}
