package com.netease.amazing.util;

import com.netease.amazing.sdk.dto.UserDTO;


public class UserInfoStore {
	public static UserDTO.Role userRole;
	public static String username;
	public static String password;
	public static String loginName;
	public static long userId;
	public static String imageDir;
	public static String signature;
	public static String backgroundImageDir;
	public static String serverIpAddress ="10.242.23.222";
	public static String serverPort ="8080";
	public static String url = "http://" + serverIpAddress + ":"+serverPort + "/server";
}
