package com.netease.amazing.sdk.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.sdk.utils.RequestURLConstants;
import com.netease.amazing.sdk.utils.Utils;

public class NoticeRestClient extends AbstractBaseClient{
	public NoticeRestClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}
	
	public List<NoticeDTO> getLatestNotices(int count) throws ClientProtocolException, IOException, URISyntaxException{
		String requestUrl = baseUrl + RequestURLConstants.GET_LATEST_NOTICES;
		URIBuilder urlbuilder = new URIBuilder(requestUrl);
		urlbuilder.addParameter("count", ""+count);
		HttpGet httpget = new HttpGet(urlbuilder.toString());
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		// Creates the json object which will manage the information received 
		GsonBuilder builder = new GsonBuilder(); 
		// Register an adapter to manage the date types as long values 
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
					return new Date(json.getAsJsonPrimitive().getAsLong()); 
			}
		});
		Gson gson = builder.create();
		NoticeDTO[] retValue = gson.fromJson(EntityUtils.toString(response.getEntity()), NoticeDTO[].class);
		return Arrays.asList(retValue);
	}
}
