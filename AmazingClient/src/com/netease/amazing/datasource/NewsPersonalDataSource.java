package com.netease.amazing.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.amazing.R;
import com.netease.amazing.dbhandler.NewsDataHandler;
import com.netease.amazing.pojo.NewsGrowthLog;

public class NewsPersonalDataSource extends DataSource {
	//根据刷新的动作，选择相应的取数据方式
	public final static int NEWS_GROWTH_INIT_DATA = 0;
	public final static int NEWS_GROWTH_UP_REFRESH_DATA = 2;
	public final static int NEWS_GROWTH_DOWN_REFRESH_DATA = 1;
	
	public final static String NEWS_PERSONAL_ITEM_JOIN_CLASS_DAYS = "newsPersonalJoinClassItemDays";
	public final static String NEWS_PERSONAL_ITEM_SAVER = "newsPersonalItemSaver";
	public final static String NEWS_PERSONAL_ITEM_IMAGE = "newsPersonalItemImage";
	public final static String NEWS_PERSONAL_ITEM_CONTENT = "newsPersonalItemContent";
	public final static String NEWS_PERSONAL_ITEM_PUBLISH_DATE = "newsPersonalItemPublishDate";
	public final static String NEWS_PERSONAL_ITEM_FROM = "newsPersonalItemFrom";
	public final static String NEWS_PERSONAL_ITEM_ID = "newsId";
	public final static String NEWS_GROWTH_TYPE = "newsType";
	
	private long userId;///
	

	protected List<NewsGrowthLog> newsList = new ArrayList<NewsGrowthLog>();
	private int fetchSize = FETCH_SIZE;
		
	@Override
	public boolean updateValue(int type) {
		switch(type) {
		case NEWS_GROWTH_INIT_DATA:
			initFetchNewsGrowth();
			break;
		case NEWS_GROWTH_DOWN_REFRESH_DATA:
			fetchNewsGrowthDown();
			break;
		case NEWS_GROWTH_UP_REFRESH_DATA:
			fetchNewsGrowthUp();
			break;
		default:
			break;
		}
		return true;
	}

	public List<Map<String, Object>> toMapList() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<NewsGrowthLog> it = newsList.iterator();
		while(it.hasNext()) {
			NewsGrowthLog tempNews = it.next();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(NEWS_PERSONAL_ITEM_JOIN_CLASS_DAYS, 
					" "+tempNews.getUserClass()+"\n 第"+tempNews.getUserJoinInClassDays()+"天");
			map.put(NEWS_PERSONAL_ITEM_CONTENT, tempNews.getNewsContent());
			map.put(NEWS_PERSONAL_ITEM_FROM, tempNews.getNewPublisherFrom());
			map.put(NEWS_PERSONAL_ITEM_IMAGE, R.drawable.ic_pulltorefresh_arrow);
			if(tempNews.getNewsGrowthLogType() == NewsGrowthLog.NEWS_GROWTH_TYPE_TAKE_DOWN){
				map.put(NEWS_PERSONAL_ITEM_SAVER, " 由 "+tempNews.getNewsTakeDownUserName()+
						" 收录自 "+tempNews.getNewsPublisherName());
			}else{
				map.put(NEWS_PERSONAL_ITEM_SAVER, " 由 "+tempNews.getNewsPublisherName()+
						" 记录");
			}

			map.put(NEWS_PERSONAL_ITEM_PUBLISH_DATE, tempNews.getNewsPublishDate()+"   ");
			map.put(NEWS_PERSONAL_ITEM_ID, tempNews.getNewsId());
			map.put(NEWS_GROWTH_TYPE, tempNews.getNewsGrowthLogType());
			list.add(map);
		}
		return list;
	}

	public void initFetchNewsGrowth() {
		newsList = NewsDataHandler.getInitNewsGrowthLog(userId, fetchSize);
		
	}
	
	public void fetchNewsGrowthDown() {
		NewsGrowthLog topNews = newsList.get(0);
		List<NewsGrowthLog> result = NewsDataHandler.getNewsGrowthLogByDownRefresh(userId, topNews.getNewsId(), fetchSize);
		result.addAll(newsList);
		newsList = result;
		
	}
	
	public void fetchNewsGrowthUp() {
		NewsGrowthLog bottomNews= newsList.get(newsList.size()-1);
		List<NewsGrowthLog> result = NewsDataHandler.getNewsGrowthLogByUpRefresh(userId, bottomNews.getNewsId(),fetchSize);
		newsList.addAll(result);
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
