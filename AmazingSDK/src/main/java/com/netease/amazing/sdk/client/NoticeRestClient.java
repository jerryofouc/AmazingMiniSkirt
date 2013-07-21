package com.netease.amazing.sdk.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
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
	
	/**
	 * 最新的通知消息
	 * @param count
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public List<NoticeDTO> getLatestNotices(int count) throws ClientProtocolException, IOException, URISyntaxException{
		String requestUrl = baseUrl + RequestURLConstants.GET_LATEST_NOTICES + "?count=" + count;
		HttpGet httpget = new HttpGet(requestUrl);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	
	/**
	 * 当前notice_id之后的消息count的消息
	 * @param beginId
	 * @param count
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<NoticeDTO> getDownRangeNotice(long beginId, int count) throws URISyntaxException, ClientProtocolException, IOException{
		String requestUrl = baseUrl + RequestURLConstants.GET_RANGE_DOWN_NOTICES  + "?count=" + count +"&beginId=" + beginId;
		HttpGet httpget = new HttpGet(requestUrl);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	/**
	 * 得到当前beginId向上的所有消息
	 * @param beginId
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<NoticeDTO> getUpRangeNotice(long beginId) throws URISyntaxException, ClientProtocolException, IOException{
		String requestUrl = baseUrl + RequestURLConstants.GET_RANGE_UP_NOTICES + "?beginId=" + beginId;
		HttpGet httpget = new HttpGet(requestUrl);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	/**
	 * 家长和老师发送通知的借口
	 * @param notice
	 * @param sendObjectIds
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public boolean sendNewNotice(NoticeDTO notice) throws ClientProtocolException, IOException{
		String requestUrl = baseUrl + RequestURLConstants.NOTICE_OPERATION_URL;
		HttpPost httpPost = new HttpPost(requestUrl);
		Gson gson = new Gson();
		String sendJson = gson.toJson(notice);
		StringEntity stringEntity = new StringEntity(sendJson,"UTF-8");
		stringEntity.setContentType("application/json; charset=utf-8");
		httpPost.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		httpPost.setEntity(stringEntity);
		HttpResponse response = httpclient.execute(httpPost);
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean deleteNotice(long noticeId) throws ClientProtocolException, IOException{
		String requestUrl = baseUrl + RequestURLConstants.NOTICE_OPERATION_URL;
		requestUrl += "/" + noticeId;
		HttpDelete httpDelete = new HttpDelete(requestUrl);
		httpDelete.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpDelete);
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
			return true;
		}else{
			return false;
		}
		
	}

	private List<NoticeDTO> DeserializeFromHttpReponse(HttpResponse response)
			throws IOException {
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
