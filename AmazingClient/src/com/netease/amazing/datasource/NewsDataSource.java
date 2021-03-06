package com.netease.amazing.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.amazing.R;
import com.netease.amazing.dbhandler.NewsDataHandler;
import com.netease.amazing.pojo.News;
import com.netease.amazing.util.UserInfoStore;


public class NewsDataSource extends DataSource {
	
	//根据刷新的动作，选择相应的取数据方式
	public final static int NEWS_INIT_DATA = 0;
	public final static int NEWS_UP_REFRESH_DATA = 2;
	public final static int NEWS_DOWN_REFRESH_DATA = 1;

	public final static String NEWS_ID = "newsId";
	public final static String NEWS_PUBLISHER_NAME = "newsPublisherName";
	public final static String NEWS_PUBLISHER_IMAGE = "newsPublisherImg";
	public final static String NEWS_CONTENT = "newsContent";
	public final static String NEWS_PUBLISH_DATE = "newsPublishDate";
	public final static String NEWS_PUBLISHER_FROM = "newPublisherFrom";
	public final static String NEWS_WITH_IMAGE = "newsWithImage";
	public final static String NEWS_CURRENT_USER_LIKE = "isCurrentUserLike";
	public final static String NEWS_CURRENT_USER_TAKE_DOWN = "isCurrentUserTakeDown";

	protected List<News> newsList = new ArrayList<News>();
	private int fetchSize = FETCH_SIZE;

	public NewsDataSource() {
	//	mDataSource = updateValue(PAGE_START, PAGE_END);
	}


	@Override
	public boolean updateValue(int type) {
		switch(type) {
		case NEWS_INIT_DATA:
			initFetchNews();
			break;
		case NEWS_DOWN_REFRESH_DATA:
			fetchNewsDown();
			break;
		case NEWS_UP_REFRESH_DATA:
			fetchNewsUp();
			break;
		default:
			break;
		}
		return true;
	}

	public List<Map<String, Object>> toMapList() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<News> it = newsList.iterator();
		Map<String,Object> map;
		while(it.hasNext()) {
			News news = it.next();
			map = new HashMap<String,Object>();
			map.put(NEWS_PUBLISHER_IMAGE, UserInfoStore.url+news.getNewsPublisherImg());
			map.put(NEWS_PUBLISHER_NAME, news.getNewsPublisherName());
			map.put(NEWS_CONTENT, news.getNewsContent());
			if(news.getNewsType() == News.NEWS_WITH_IMAGE)
				map.put(NEWS_WITH_IMAGE, R.drawable.ic_pulltorefresh_arrow);
			map.put(NEWS_PUBLISHER_FROM, news.getNewPublisherFrom());
			map.put(NEWS_PUBLISH_DATE, news.getNewsPublishDate());
			map.put(NEWS_ID, news.getNewsId());
			map.put(NEWS_CURRENT_USER_LIKE, news.isNewsCurrentUserLike());
			map.put(NEWS_CURRENT_USER_TAKE_DOWN, news.isNewsCurrentUserTakeDown());
			list.add(map);
		}
		return list;
	} 

	public void initFetchNews() {
		newsList = NewsDataHandler.getInitNews(fetchSize);
	}

	public void fetchNewsDown() {
		News topNews = newsList.get(0);
		List<News> result = NewsDataHandler.getNewsByDownRefresh(topNews.getNewsId(), fetchSize);
		result.addAll(newsList);
		newsList = result;
	}

	public void fetchNewsUp() {
		News bottomNews= newsList.get(newsList.size()-1);
		List<News> result = NewsDataHandler.getNewsByUpRefresh(bottomNews.getNewsId(),fetchSize);
		newsList.addAll(result);
	}
}