package com.netease.amazing.sdk;

public class AccountRestClient extends AbstractClient {
	
	public AccountRestClient(String baseUrl, String loginName, String password) {
		super(baseUrl, loginName, password);
	}
	
	/**
	 * ����Ƿ��¼�ɹ�
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean testLogin(String userName, String password){
		return false;
	}
}
