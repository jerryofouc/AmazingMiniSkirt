package com.netease.amazing.util;

import com.netease.amazing.sdk.dto.UserDTO;

public class UserInfoStore {
	public static String username;
	public static String password;
	public static long userId;
	public static String serverIpAddress ="10.240.34.42";
	public static String serverPort ="8080";
	public static String url = "http://" + serverIpAddress + ":"+serverPort + "/server";
}
