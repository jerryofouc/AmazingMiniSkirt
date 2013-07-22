package com.netease.amazing.server.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static final long A_DAY_IN_MINISED = 24*60*60*60*1000;
	public static final long A_YEAR_IN_MINISED = 365*A_DAY_IN_MINISED;
	public static SimpleDateFormat getDateFormat(Date newsDate) {
		Date curDate = Calendar.getInstance().getTime();
		SimpleDateFormat simpleDateFormat;
		if(curDate.getTime() - newsDate.getTime() < A_DAY_IN_MINISED ){
			simpleDateFormat = new SimpleDateFormat("今天  HH:mm");
		}else if(curDate.getTime() - newsDate.getTime() < A_YEAR_IN_MINISED){
			simpleDateFormat = new SimpleDateFormat("MM月dd日  HH:mm");
		}else{
			simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		}
		return simpleDateFormat;
	}
}
