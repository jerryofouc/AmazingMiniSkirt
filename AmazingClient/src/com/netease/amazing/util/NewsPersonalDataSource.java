package com.netease.amazing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.amazing.R;
import com.netease.amazing.dbhandler.NewsDataHandler;
import com.netease.amazing.pojo.News;

public class NewsPersonalDataSource extends DataSource {

	protected ArrayList<News> newsList = new ArrayList<News>();
	private int fetchSize = FETCH_SIZE;
	
	private NewsDataHandler ndh = new NewsDataHandler();
	
	@Override
	public boolean updateValue(int type) {
		switch(type) {
		case 0:
			initFetchNews();
			break;
		case 1:
			fetchNewsDown();
			break;
		case 2:
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
		while(it.hasNext()) {
			News tempNews = it.next();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(NewsPersonalListAdapter.NEWS_PERSONAL_ITEM_JOIN_CLASS_DAYS, "38天");
			map.put(NewsPersonalListAdapter.NEWS_PERSONAL_ITEM_CONTENT, "今天表现不错");
			map.put(NewsPersonalListAdapter.NEWS_PERSONAL_ITEM_FROM, "杭州新华幼儿园");
			map.put(NewsPersonalListAdapter.NEWS_PERSONAL_ITEM_IMAGE, R.drawable.ic_pulltorefresh_arrow);
			map.put(NewsPersonalListAdapter.NEWS_PERSONAL_ITEM_SAVER, "由妈妈收录自抱抱熊");
			map.put(NewsPersonalListAdapter.NEWS_PERSONAL_ITEM_PUBLISH_DATE, "2013.7.15");
			list.add(map);
		}
		return list;
	}

	public void initFetchNews() {
		newsList = ndh.getInitNews(fetchSize);
		
	}
	
	public void fetchNewsDown() {
		News bottomNews = newsList.get(newsList.size()-1);
		ArrayList<News> result = ndh.getNews(bottomNews.getId(), fetchSize);
		newsList.addAll(result);
	}
	
	public void fetchNewsUp() {
		News topNews = newsList.get(0);
		ArrayList<News> result = ndh.getNews(topNews.getId());
		result.addAll(newsList);
		newsList = result;
	}

}
