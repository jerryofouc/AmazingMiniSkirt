package com.netease.amazing.component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.amazing.R;
import com.netease.amazing.activity.NoticeActivity;
import com.netease.amazing.pojo.Notice;
import com.netease.amazing.util.NoticeDBSimulateHandler;

public class OldNoticeFragment extends Fragment {

	//���ڴ洢֪ͨ���б� ��fragment����
	ArrayList<Notice> notices = new ArrayList<Notice>();
	
	//notice�����ݴ�����
	NoticeDBSimulateHandler noticeHandler = NoticeDBSimulateHandler.getInstance();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflateAndSetupView(inflater, container, savedInstanceState, R.layout.notice_index);
	}
	
	private View inflateAndSetupView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState, int layoutResourceId) {
		View layout = inflater.inflate(layoutResourceId, container, false);
		return layout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
//		View view = getView();
//		
//		
//		int lastReceivedNoticeId = -1;
//		if(notices.size() !=0) {
//			lastReceivedNoticeId = notices.get(notices.size()-1).getId();
//		}
//		
//		//lastReceivedNoticeId��ʾ�ϴδӷ������˻�ȡ������֪ͨ�У������б�β����id�����ڷ������˵��ж�
//		notices = noticeHandler.getNotice(10,10);
//		
//		//���ڴ洢listView���map����
//		ArrayList<Map<String,Object>> noticeList = new ArrayList<Map<String,Object>>();
//		Iterator<Notice> it = notices.iterator();
//		
//		while(it.hasNext()) {
//			Notice notice = it.next();
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("image", R.drawable.download);
//			map.put("title", notice.cutTitle(30,"UTF-8"));
//			map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(notice.getNoticeDate()));
//			map.put("content", notice.cutContent(10,"UTF-8"));
//			noticeList.add(map);
//		}
//		ListView lv = (ListView)view.findViewById(R.id.myListView);
//		lv.setAdapter(new SimpleAdapter(getActivity(),noticeList,R.layout.noticeitem,
//				      new String[]{"image","title","date","content"},
//					  new int[]{R.id.notice_item_updown_image,R.id.notice_item_title,R.id.notice_item_date,R.id.notice_item_content}));
//		lv.setOnItemClickListener(new MyOnItemClickListener());	
	}
	
	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int id,
				long position) {
			Bundle bundle = new Bundle();
			Notice notice = notices.get(id);
			
			//Ϊ�¸�activity������ѡ�е�notice
			bundle.putSerializable("notice", notice);
			Intent intent = new Intent(getActivity(),NoticeActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	}


}
