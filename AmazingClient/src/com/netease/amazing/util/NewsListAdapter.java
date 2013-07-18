package com.netease.amazing.util;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amazing.R;

public class NewsListAdapter extends ListViewBasedAdapter {

	public NewsListAdapter(Context context,List<Map<String,Object>> dataSource) {
		super(context, dataSource);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = inflater.inflate(R.layout.newsitem,null);
		
		ImageView image1 = (ImageView)view.findViewById(R.id.news_item_icon);
		image1.setImageResource((Integer)dataSource.get(position).get("icon"));
		
		TextView titleView = (TextView)view.findViewById(R.id.news_item_sender);
		titleView.setText(dataSource.get(position).get("title").toString());
		
		TextView infoView =(TextView)view.findViewById(R.id.news_item_content);
		infoView.setText(dataSource.get(position).get("info").toString());
		
		ImageView image2 = (ImageView)view.findViewById(R.id.news_item_image);
		image2.setImageResource((Integer)dataSource.get(position).get("imag"));
		
		TextView newsFromView = (TextView)view.findViewById(R.id.news_item_from);
		newsFromView.setText(dataSource.get(position).get("newsFrom").toString());
		
		return view;
	}

	
}
