package com.netease.amazing.util;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Updated By Huang Xiao Jun 2013-7-17 11:23
 * Class Descripton:
 * 		ListView的数据适配器基类， 根据数据源，显示样式自定义配置数据展示方式
 * @author Administrator
 *
 */
public abstract class ListViewBasedAdapter extends BaseAdapter{
	
	protected int count;//数据的个数
	protected LayoutInflater inflater;//当前的Activity
	protected List<Map<String, Object>> dataSource;//数据源
	public ListViewBasedAdapter(Context context,
			List<Map<String, Object>> dataSource) {
		this.inflater =LayoutInflater.from(context);
		this.dataSource=dataSource;
		this.count = dataSource.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public Object getItem(int position) {
		return dataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	//定义展示的样式
	@Override
	public abstract View getView(int position, View view, ViewGroup parent);

}
