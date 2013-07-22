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
	 * @param bottomNewsId ��ǰ
	 * @param count ��̬����
	 * @return �����û��������ر�idΪbottomNewsId���緢���ĳɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)��
	 * 		        ���صĶ�̬����Ϊcount��������緢���Ķ�̬����count�����򷵻������緢���Ķ�̬
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
	 * @return �����û����� ���ر�topNewsId����(���·���)�����гɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)
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
	 * @return �����û��������ر�topNewsId����(���·���)��count���ɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)�����������������:
	 *      case 1:���������б�topNewsId��������Ϣ��������count��ʱ�����ط����������µ�count�����ݣ����Ұ�ʱ�����򱣴���List��
	 *      case 2:���������б�topNewsId��������Ϣ����(����Ϊn��)С��count��ʱ����ֻ��Ҫ������n�����ݣ����Ұ�ʱ�����򱣴���List��
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
