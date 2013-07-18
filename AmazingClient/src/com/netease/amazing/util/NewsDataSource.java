package com.netease.amazing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R;

public class NewsDataSource extends DataSource {

	public NewsDataSource() {
	//	mDataSource = updateValue(PAGE_START, PAGE_END);
	}
	
	@Override
	public List<Map<String, Object>> updateValue(int pageStart, int pageSize) {
		
		//GetNews()
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		
		for(int i =0;i<pageSize;i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("icon", R.drawable.ic_menu_camera);
			map.put("title", "G" +pageStart);
			map.put("info", "google " + pageStart);
			map.put("imag", R.drawable.ic_input_add);
			map.put("newsFrom", "message from...");
			++pageStart;
			list.add(map);
		}
		
		return list;
	}

}
