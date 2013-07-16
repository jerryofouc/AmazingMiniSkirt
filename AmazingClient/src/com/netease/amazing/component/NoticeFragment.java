package com.netease.amazing.component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.widget.TextView;

import com.example.amazing.R;
import com.netease.amazing.activity.NoticeActivity;
import com.netease.amazing.pojo.Notice;

public class NoticeFragment extends Fragment {

	
	private TextView tv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflateAndSetupView(inflater, container, savedInstanceState, R.layout.fragment1);
	}
	
	private View inflateAndSetupView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState, int layoutResourceId) {
		View layout = inflater.inflate(layoutResourceId, container, false);
		
		return layout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		View view = getView();
		
		int counts= 0;
		Notice notice = new Notice();
		ArrayList<Map<String,Object>> noticeList = new ArrayList<Map<String,Object>>();
				//从服务器中动态地获取最新数据添加到notice中
		while(counts++ <10) {
			Map<String,Object> map = new HashMap<String,Object>();
			notice = new Notice(true);
			map.put("image", R.drawable.download);
			map.put("title", notice.cutTitle(30,"UTF-8"));
			map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(notice.getNoticeDate()));
			map.put("content", notice.cutContent(10,"UTF-8"));
			noticeList.add(map);
		}
		ListView lv = (ListView)view.findViewById(R.id.myListView);
		lv.setAdapter(new SimpleAdapter(getActivity(),noticeList,R.layout.item,
				      new String[]{"image","title","date","content"},
					  new int[]{R.id.itemImage,R.id.titleText,R.id.dateText,R.id.contentText}));
		lv.setOnItemClickListener(new MyOnItemClickListener());	
	}
	
	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int id,
				long position) {
			Bundle bundle = new Bundle();
			bundle.putInt("noticeIdInList", id);
			ArrayList<Notice> myLists = new ArrayList<Notice>();
			for(int i =0;i<10;i++)
			myLists.add(new Notice(true));
			bundle.putSerializable("currentNotice",myLists.get(id));
			Intent intent = new Intent(getActivity(),NoticeActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	}


}
