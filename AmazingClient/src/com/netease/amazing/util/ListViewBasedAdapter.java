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
 * 		ListView���������������࣬ ��������Դ����ʾ��ʽ�Զ�����������չʾ��ʽ
 * @author Administrator
 *
 */
public abstract class ListViewBasedAdapter extends BaseAdapter{
	
	protected int count;//���ݵĸ���
	protected LayoutInflater inflater;//��ǰ��Activity
	protected List<Map<String, Object>> dataSource;//����Դ
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

	
	//����չʾ����ʽ
	@Override
	public abstract View getView(int position, View view, ViewGroup parent);

}
