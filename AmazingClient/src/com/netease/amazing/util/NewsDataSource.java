package com.netease.amazing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.amazing.R;
import com.netease.amazing.dbhandler.NewsDataHandler;
import com.netease.amazing.pojo.News;


public class NewsDataSource extends DataSource {

	protected List<News> newsList = new ArrayList<News>();
	private int fetchSize = FETCH_SIZE;
	
	private NewsDataHandler ndh = new NewsDataHandler();
	public NewsDataSource() {
	//	mDataSource = updateValue(PAGE_START, PAGE_END);
	}
	
	
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
			map.put("icon", R.drawable.ic_launcher);
			map.put("title", "G");
			map.put("info", "google ");
			map.put("imag", R.drawable.ic_pulltorefresh_arrow);
			map.put("newsFrom", "message from...");
			list.add(map);
		}
		return list;
	}
	
	public void initFetchNews() {
		newsList = ndh.getInitNews(fetchSize);
		
	}
	
	public void fetchNewsDown() {
		News topNews = newsList.get(0);
		List<News> result = ndh.getNewsByDownRefresh(topNews.getNewsId(), fetchSize);
		newsList.addAll(result);
	}
	
	public void fetchNewsUp() {
		News bottomNews= newsList.get(newsList.size()-1);
		List<News> result = ndh.getNewsByUpRefresh(bottomNews.getNewsId(),fetchSize);
		result.addAll(newsList);
		newsList = result;
	}
}
