package com.netease.amazing.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.amazing.R;
import com.netease.amazing.dbhandler.NewsDataHandler;
import com.netease.amazing.pojo.News;
import com.netease.amazing.pojo.Notice;


public class NewsDataSource extends DataSource1 {

	protected ArrayList<News> newsList = new ArrayList<News>();
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
//	@Override
//	public List<Map<String, Object>> updateValue(int pageStart, int pageSize) {
//		
//		//GetNews()
//		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//		
//		
//		for(int i =0;i<pageSize;i++) {
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("icon", R.drawable.ic_menu_camera);
//			map.put("title", "G" +pageStart);
//			map.put("info", "google " + pageStart);
//			map.put("imag", R.drawable.ic_input_add);
//			map.put("newsFrom", "message from...");
//			++pageStart;
//			list.add(map);
//		}
//		
//		return list;
//	}
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
