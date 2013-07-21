package com.netease.amazing.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.amazing.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeListAdapter extends ListViewBasedAdapter {

	public NoticeListAdapter(Context context,DataSource dataSource) {
		super(context, dataSource);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.notice_item_new,null);
        
		ImageView image1 = (ImageView)view.findViewById(R.id.notice_item_updown_image);
		Map<String,Object> m = (Map<String,Object>)getItem(position);
		
		Integer i = (Integer)m.get("image");
		image1.setImageResource(i);
		
		TextView titleView = (TextView)view.findViewById(R.id.notice_item_title);
		titleView.setText(m.get("title").toString());
		
		TextView infoView =(TextView)view.findViewById(R.id.notice_item_date);
		infoView.setText(m.get("date").toString());
		
		TextView newsFromView = (TextView)view.findViewById(R.id.notice_item_content);
		newsFromView.setText(m.get("content").toString());
		
		return view;
	}

}
