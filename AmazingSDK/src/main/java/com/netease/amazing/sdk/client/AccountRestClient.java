package com.netease.amazing.sdk.client;


import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.netease.amazing.sdk.dto.ContactDTO;
import com.netease.amazing.sdk.dto.UserDTO;
import com.netease.amazing.sdk.utils.RequestURLConstants;
import com.netease.amazing.sdk.utils.Utils;

public class AccountRestClient extends AbstractBaseClient {
	public AccountRestClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}
	
	public UserDTO getUserInfo() throws ClientProtocolException, IOException{
		 String requestUrl = baseUrl + RequestURLConstants.TEST_LOGIN_URL;
		 HttpGet httpget = new HttpGet(requestUrl); 
		 httpget.setHeader("Authorization", Utils.HttpBasicEncodeBase64(loginName, password));
		 HttpResponse response = httpclient.execute(httpget);
		 Gson gson = new Gson();
		 UserDTO userDTO = gson.fromJson(EntityUtils.toString(response.getEntity()), UserDTO.class);
		 return userDTO;
	}
	/**
	 * 检测是否登录成功
	 * @param baseURL 服务器域名
	 * @param userName	用户名
	 * @param password	密码
	 * @return  测试是否成功
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static boolean  testLogin(String baseURL, String userName, String password) throws ClientProtocolException, IOException{
		 HttpClient httpclient = new DefaultHttpClient();		
		 String requestUrl = baseURL + RequestURLConstants.TEST_LOGIN_URL;
		 HttpGet httpget = new HttpGet(requestUrl); 
		/* byte[] encoding = Base64.encodeBase64("xukai:123456".getBytes());*/
		 httpget.setHeader("Authorization", Utils.HttpBasicEncodeBase64(userName, password));
		 HttpResponse response = httpclient.execute(httpget);
		 if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				return true;
			}else{
				return false;
		}
	}
	
	public boolean hasLogin() throws ClientProtocolException, IOException{
		String requestUrl = baseUrl + RequestURLConstants.HAS_LOGIN_URL;
		 HttpGet httpget = new HttpGet(requestUrl); 
		 httpget.setHeader("Authorization", Utils.HttpBasicEncodeBase64(loginName, password));
		 HttpResponse response = httpclient.execute(httpget);
		 Gson gson = new Gson();
		 Boolean hasLogin = gson.fromJson(EntityUtils.toString(response.getEntity()), Boolean.class);
		 return hasLogin;
	}
}
