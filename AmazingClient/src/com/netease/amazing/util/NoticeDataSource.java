package com.netease.amazing.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.amazing.R;
import com.netease.amazing.dbhandler.NoticeDataHandler;
import com.netease.amazing.pojo.Notice;

public class NoticeDataSource extends DataSource1 {

	protected ArrayList<Notice> noticeList = new ArrayList<Notice>();
	private int fetchSize = FETCH_SIZE;
	
	NoticeDataHandler ndh = new NoticeDataHandler();
	
	public ArrayList<Notice> getNoticeList() {
		return noticeList;
	}
	public void setNoticeList(ArrayList<Notice> noticeList) {
		this.noticeList = noticeList;
	}
	
	public int getFetchSize() {
		return fetchSize;
	}
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}
	@Override
	public boolean updateValue(int type) {
		switch(type) {
		case 0:
			initFetchNotice();
			break;
		case 1:
			fetchNoticeDown();
			break;
		case 2:
			fetchNoticeUp();
			break;
		default:
			break;
		}
		return true;
	}
	
	public List<Map<String, Object>> toMapList() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<Notice> it = noticeList.iterator();
		while(it.hasNext()) {
			Notice tempNotice = it.next();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("image", R.drawable.download);
			map.put("title", tempNotice.cutTitle(30,"UTF-8"));
			map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(tempNotice.getNoticeDate()));
			map.put("content", tempNotice.cutContent(10,"UTF-8"));
			list.add(map);
		}
		return list;
	}
	
	public void initFetchNotice() {
		noticeList = ndh.getInitNotice(fetchSize);
		
	}
	
	public void fetchNoticeDown() {
		Notice bottomNotice = noticeList.get(noticeList.size()-1);
		ArrayList<Notice> result = ndh.getNotice(bottomNotice.getId(), fetchSize);
		noticeList.addAll(result);
	}
	
	public void fetchNoticeUp() {
		Notice topNotice = noticeList.get(0);
		ArrayList<Notice> result = ndh.getNotice(topNotice.getId());
		result.addAll(noticeList);
		noticeList = result;
	}
}


