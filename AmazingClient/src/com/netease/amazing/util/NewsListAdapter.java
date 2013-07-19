package com.netease.amazing.util;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amazing.R;

public class NewsListAdapter extends ListViewBasedAdapter1 {

	public NewsListAdapter(Context context,DataSource1 dataSource) {
		super(context, dataSource);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = inflater.inflate(R.layout.newsitem,null);
		Map<String,Object> m = (Map<String,Object>)getItem(position);
		
		ImageView image1 = (ImageView)view.findViewById(R.id.news_item_icon);
		image1.setImageResource((Integer)m.get("icon"));
		
		TextView titleView = (TextView)view.findViewById(R.id.news_item_sender);
		titleView.setText(m.get("title").toString());
		
		TextView infoView =(TextView)view.findViewById(R.id.news_item_content);
		infoView.setText(m.get("info").toString());
		
		ImageView image2 = (ImageView)view.findViewById(R.id.news_item_image);
		image2.setImageResource((Integer)m.get("imag"));
		
		TextView newsFromView = (TextView)view.findViewById(R.id.news_item_from);
		newsFromView.setText(m.get("newsFrom").toString());
		
		return view;
	}

	
}
