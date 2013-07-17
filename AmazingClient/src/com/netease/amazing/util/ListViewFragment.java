package com.netease.amazing.util;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.netease.amazing.util.RefreshableListView.OnRefreshListener;

/**
 * 
 * @author Huang Xiao Jun
 * Class Desciption:
 *   ListViewFragment����б��@ʾ�����Ұ�������������ˢ�¹��ܣ�푑�item�c���¼�
 *
 */
public class ListViewFragment extends Fragment implements OnRefreshListener {
	
	private RefreshableListView mRefreshListView;
	private DataSource mDataSource;
	private ListViewBasedAdapter listAdapter;
	private String listViewAdapter;
	private final static int LIST_VIEW_PAGE_SIZE = 10;
	
	
	private int fragmentLayout;   //fragment�Ĳ���
	private int viewListLayout;   //viewList�Ĳ���
	
	private OnItemClickListener itemClickListener; //itemClick��Ӧ�¼�
	
	/**
	 * 
	 * @param fragmentLayout fragment����
	 * @param viewListLayout viewList����
	 * @param dataSource ����Դ
	 * @param adapter �m�������
	 * @param itemClickListener 푑���item�¼�
	 */
	public void set(int fragmentLayout, 
			int viewListLayout, 
			DataSource dataSource,
			String adapter,
			OnItemClickListener itemClickListener){
		this.fragmentLayout = fragmentLayout;
		this.viewListLayout = viewListLayout;
		this.mDataSource = dataSource;
		this.itemClickListener = itemClickListener;
		this.listViewAdapter = adapter;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		if (container == null) {
			return null;
		}
		LinearLayout view = (LinearLayout) inflateAndSetupView(inflater,
				container, savedInstanceState, fragmentLayout);
		mRefreshListView = (RefreshableListView) view
				.findViewById(viewListLayout);
		try{
			Class<?> c = Class.forName(listViewAdapter);
			Class<?>[] paraTypes = {Context.class,List.class};
			Object[] paras = {getActivity(), this.mDataSource.getmDataSource()};
			Constructor<?> cons = c.getConstructor(paraTypes);
			listAdapter = (ListViewBasedAdapter) cons.newInstance(paras);
		}catch(Exception e){
			e.printStackTrace();
		}

		
		mRefreshListView.setAdapter(listAdapter);
		
		//���ItemClick��Ӧ�¼�
		mRefreshListView.setOnItemClickListener(itemClickListener);
		mRefreshListView.setonRefreshListener(this);
		return view;
	}

	protected void changeListView(int pageStart, int pageSize){
		List<Map<String, Object>> data = mDataSource.updateValue(pageStart, pageSize);
		for (Map<String, Object> map : data) {
			this.mDataSource.getmDataSource().add(map);
		}
		data = null;
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			listAdapter.setCount(listAdapter.getCount()+LIST_VIEW_PAGE_SIZE);
			listAdapter.notifyDataSetChanged();
			mRefreshListView.onRefreshComplete();
			super.handleMessage(msg);
		}
	};

	@Override
	public void onPullUpRefresh() {
		new Thread() {
			public void run() {
				try {
					//ģ����������ʱ��
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				changeListView(mDataSource.getmDataSource().size() + 1, LIST_VIEW_PAGE_SIZE);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	@Override
	public void onPullDownRefresh() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				changeListView(mDataSource.getmDataSource().size() + 1, LIST_VIEW_PAGE_SIZE);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}


	private View inflateAndSetupView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState, int layoutResourceId) {
		return inflater.inflate(layoutResourceId, container, false);
	}
}
