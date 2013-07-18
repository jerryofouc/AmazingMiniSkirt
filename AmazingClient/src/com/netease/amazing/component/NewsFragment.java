package com.netease.amazing.component;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.example.amazing.R;
import com.netease.amazing.util.DataSource;
import com.netease.amazing.util.ListViewBasedAdapter;
import com.netease.amazing.util.NewsDataSource;
import com.netease.amazing.util.NewsListAdapter;
import com.netease.amazing.util.RefreshableListView;
import com.netease.amazing.util.RefreshableListView.OnRefreshListener;

/**
 * 
 * @author Huang Xiao Jun
 * Class Desciption:
 *   ListViewFragment����б��@ʾ�����Ұ�������������ˢ�¹��ܣ�푑�item�c���¼�
 *
 */
public class NewsFragment extends Fragment implements OnRefreshListener {
	
	private RefreshableListView mRefreshListView;
	private DataSource mDataSource = new NewsDataSource();
	private ListViewBasedAdapter listAdapter;
	private String listViewAdapter;
	private final static int LIST_VIEW_PAGE_SIZE = 10;
	
	private int fragmentLayout = R.layout.fragment2;   //fragment�Ĳ���
	private int viewListLayout = R.id.mineList;   //viewList�Ĳ���
	
	private OnItemClickListener itemClickListener; //itemClick��Ӧ�¼�
	private MyListViewFragmentHandler fragmentHandler = new MyListViewFragmentHandler();
	private ProgressDialog proDialog;
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
	
	public void set(ListViewBasedAdapter listAdapter,OnItemClickListener itemClickLister) {
		this.listAdapter = listAdapter;
		this.itemClickListener = itemClickListener;
	}
	
	/**/
	private class GetInitDataThread extends Thread {
		
		MyListViewFragmentHandler handler;
		
		GetInitDataThread(MyListViewFragmentHandler handler) {
			this.handler = handler;
		}
		
		@Override 
		public void run() {
			mDataSource.setmDataSource(mDataSource.updateValue(DataSource.PAGE_START, DataSource.PAGE_END));
			listAdapter = new NewsListAdapter(getActivity(), mDataSource.getmDataSource());
			set(listAdapter,itemClickListener);
			mRefreshListView.setAdapter(listAdapter);
			handler.sendEmptyMessage(1);
		}
	}
	
	private class MyListViewFragmentHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//��������С����Ի����� dismiss��
			if(proDialog != null) {
				proDialog.dismiss();
			}
		}
		
	}
	/**/
	
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
		
		new GetInitDataThread(fragmentHandler).start();
//		proDialog = ProgressDialog.show(getActivity(), "������..",
//				"������..���Ժ�....", true, true);
		
		
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
