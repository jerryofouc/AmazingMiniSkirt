package com.netease.amazing.sdk.utils;

public class RequestURLConstants {
	private RequestURLConstants(){}
	public static final String TEST_LOGIN_URL = "/api/user";
	public static final String HAS_LOGIN_URL = TEST_LOGIN_URL + "/hasLogin";
	public static final String GET_ALL_CONTACTS = "/api/contacts";
	public static final String NOTICE_OPERATION_URL = "/api/notification";
	public static final String GET_LATEST_NOTICES = NOTICE_OPERATION_URL + "/latest";
	public static final String GET_RANGE_DOWN_NOTICES = NOTICE_OPERATION_URL + "/rangeDown";
	public static final String GET_RANGE_UP_NOTICES = NOTICE_OPERATION_URL + "/rangeUp";
	public static final String TWEET_OP = "/api/tweet";
	public static final String GET_LATEST_NEWS = TWEET_OP + "/latest";
	public static final String GET_TWEET_RANGE_DOWN = TWEET_OP + "/rangeDown";
	public static final String GET_TWEET_RANGE_UP = TWEET_OP + "/rangeUp";
	public static final String GROWTH_LOG = "/api/growthlog";
}
