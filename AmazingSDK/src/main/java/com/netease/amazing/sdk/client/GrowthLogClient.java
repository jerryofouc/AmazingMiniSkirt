package com.netease.amazing.sdk.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.netease.amazing.sdk.dto.NewsGrowthLogDTO;
import com.netease.amazing.sdk.utils.RequestURLConstants;
import com.netease.amazing.sdk.utils.Utils;


public class GrowthLogClient extends AbstractBaseClient{

	public GrowthLogClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}
	
	public  List<NewsGrowthLogDTO> getInitNewsGrowthLog(long userId,int count) throws ClientProtocolException, IOException{
		String requestUrl = baseUrl + RequestURLConstants.GROWTH_LOG + "/"+userId;
		HttpGet httpget = new HttpGet(requestUrl + "?count=" + count);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	

	/**
	 * @param userName
	 * @param bottomNewsId 当前
	 * @param count 动态条数
	 * @return 根据用户名，返回比id为bottomNewsId更早发布的成长记录(当前用户原创和收录的动态)，
	 * 		        返回的动态条数为count，如果更早发布的动态不足count条，则返回所有早发布的动态
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public  List<NewsGrowthLogDTO> getNewsGrowthLogByUpRefresh(long userId, long bottomNewsId,int count) throws ClientProtocolException, IOException {
		String requestUrl = baseUrl + RequestURLConstants.GROWTH_LOG + "/"+userId + "/pre?bottomNewsId=" + bottomNewsId ;
		HttpGet httpget = new HttpGet(requestUrl + "&count=" + count);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	/**
	 * @param userName
	 * @param topNewsId 
	 * @return 根据用户名， 返回比topNewsId晚发布(即新发布)的所有成长记录(当前用户原创和收录的动态)
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public  List<NewsGrowthLogDTO> getNewsGrowthLog(long userId,long topNewsId) throws ClientProtocolException, IOException {
		String requestUrl = baseUrl + RequestURLConstants.GROWTH_LOG + "/"+userId + "/allNewGrowlog?topNewsId=" + topNewsId ;
		HttpGet httpget = new HttpGet(requestUrl);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	
	private List<NewsGrowthLogDTO> DeserializeFromHttpReponse(HttpResponse response)
			throws IOException {
		// Creates the json object which will manage the information received 
		GsonBuilder builder = new GsonBuilder(); 
		// Register an adapter to manage the date types as long values 
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
					return new Date(json.getAsJsonPrimitive().getAsLong()); 
			}
		});
		Gson gson = builder.create();
		NewsGrowthLogDTO[] retValue = gson.fromJson(EntityUtils.toString(response.getEntity()), NewsGrowthLogDTO[].class);
		return Arrays.asList(retValue);
	}
	

	/**
	 * @param userName
	 * @param topNewsId
	 * @param newsCount
	 * @return 根据用户名，返回比topNewsId晚发布(即新发布)的count条成长记录(当前用户原创和收录的动态)，存在以下两种情况:
	 *      case 1:当服务器中比topNewsId晚发布的信息条数大于count条时，返回服务器中最新的count条数据，并且按时间逆序保存在List中
	 *      case 2:当服务器中比topNewsId晚发布的信息条数(假设为n条)小于count条时，则只需要返回这n条数据，并且按时间逆序保存在List中
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public  List<NewsGrowthLogDTO> getNewsGrowthLogByDownRefresh(long userId, long topNewsId, int count) throws ClientProtocolException, IOException {
		String requestUrl = baseUrl + RequestURLConstants.GROWTH_LOG + "/"+userId + "/topNews?topNewsId=" + topNewsId ;
		HttpGet httpget = new HttpGet(requestUrl + "&count=" + count);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
}
