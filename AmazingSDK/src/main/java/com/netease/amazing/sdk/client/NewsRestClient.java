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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.netease.amazing.sdk.dto.NewsCommentsDTO;
import com.netease.amazing.sdk.dto.NewsDTO;
import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.sdk.utils.RequestURLConstants;
import com.netease.amazing.sdk.utils.Utils;

public class NewsRestClient extends AbstractBaseClient{

	public NewsRestClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}
	/**
	 * ���µ�֪ͨ��Ϣ
	 * @param count
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public List<NewsDTO> getLatestNews(int count) throws ClientProtocolException, IOException, URISyntaxException{
		String requestUrl = baseUrl + RequestURLConstants.GET_LATEST_NEWS;
		HttpGet httpget = new HttpGet(requestUrl + "?count=" + count);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	
	/**
	 * 
	 * @param bottomNewsId ��ǰ
	 * @param newsCount ��̬����
	 * @return ���ر�idΪbottomNewsId���緢���Ķ�̬��
	 * 		        ���صĶ�̬����ΪnewsCount��������緢���Ķ�̬����newsCount�����򷵻������緢���Ķ�̬
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public  List<NewsDTO> getNewsByUpRefresh(long bottomNewsId,int count) throws ClientProtocolException, IOException {
		String requestUrl = baseUrl + RequestURLConstants.GET_TWEET_RANGE_DOWN;
		HttpGet httpget = new HttpGet(requestUrl + "?count=" + count + "&bottomId=" + bottomNewsId);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return ���ر�topNewsId����(���·���)�����ж�̬
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public  List<NewsDTO> getNews(long topNewsId) throws ClientProtocolException, IOException {
		String requestUrl = baseUrl + RequestURLConstants.GET_TWEET_RANGE_UP;
		HttpGet httpget = new HttpGet(requestUrl + "?topId=" + topNewsId);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	
	/**
	 * 
	 * @param topNewsId
	 * @param newsCount
	 * @return ���ر�topNewsId����(���·���)��newsCount����̬�����������������:
	 *      case 1:���������б�topNewsId��������Ϣ��������newsCount��ʱ�����ط����������µ�newsCount�����ݣ����Ұ�ʱ�����򱣴���List��
	 *      case 2:���������б�topNewsId��������Ϣ����(����Ϊn��)С��newsCount��ʱ����ֻ��Ҫ������n�����ݣ����Ұ�ʱ�����򱣴���List��
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public  List<NewsDTO> getNewsByDownRefresh(long topNewsId, int newsCount) throws ClientProtocolException, IOException {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		String requestUrl = baseUrl + RequestURLConstants.GET_TWEET_RANGE_UP;
		HttpGet httpget = new HttpGet(requestUrl + "?topId=" + topNewsId + "&count="+newsCount);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeFromHttpReponse(response);
	}
	
	public  List<NewsCommentsDTO> getNewsCommentToNewsIndexByNewsId(long newsId, int newsCommentCount) throws ClientProtocolException, IOException{
		String requestUrl = baseUrl + RequestURLConstants.TWEET_OP +"/" +newsId + "/comments";
		HttpGet httpget = new HttpGet(requestUrl);
		httpget.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpget);
		return DeserializeNewsCommentsDTOFromHttpReponse(response);
		
	}
	
	public boolean setLikeNews(long newsId) throws ClientProtocolException, IOException{
		String requestUrl = baseUrl + RequestURLConstants.TWEET_OP +"/" +newsId + "/like?id=" + newsId;
		HttpPost httpPost = new HttpPost(requestUrl);
		httpPost.setHeader("Authorization",Utils.HttpBasicEncodeBase64(loginName, password));
		HttpResponse response = httpclient.execute(httpPost);
		if(response.getStatusLine().getStatusCode() == 201){
			return true;
		}else{
			return false;
		}
	}
	
	
	private List<NewsCommentsDTO> DeserializeNewsCommentsDTOFromHttpReponse(HttpResponse response)
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
		NewsCommentsDTO[] retValue = gson.fromJson(EntityUtils.toString(response.getEntity()), NewsCommentsDTO[].class);
		return Arrays.asList(retValue);
	}
	
	private List<NewsDTO> DeserializeFromHttpReponse(HttpResponse response)
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
		NewsDTO[] retValue = gson.fromJson(EntityUtils.toString(response.getEntity()), NewsDTO[].class);
		return Arrays.asList(retValue);
	}
}
