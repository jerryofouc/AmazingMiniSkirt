package com.netease.amazing.sdk.utils;

import org.apache.commons.codec.binary.Base64;

public class Utils {
	public static String HttpBasicEncodeBase64(String userName, String password){
		 byte[] encoding = Base64.encodeBase64((userName + ":" +password).getBytes());
		 return  "Basic " + new String(encoding);
	}
}
