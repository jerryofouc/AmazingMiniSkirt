package com.netease.amazing.component;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
 * @author Huang Xiao Jun
 * Class Desciption:
 *   ListViewFragment用於列表顯示，并且包括上拉和下拉刷新功能，響應item點擊事件
 */
public class NewsFragment extends Fragment implements OnRefreshListener {
	
	private RefreshableListView mRefreshListView;
	private DataSource mDataSource = new NewsDataSource();
	private ListViewBasedAdapter listAdapter;
	private String listViewAdapter;
	private final static int LIST_VIEW_PAGE_SIZE = 10;
	
	private int fragmentLayout = R.layout.news_index;   //fragment的布局
	private int viewListLayout = R.id.newsList;   //viewList的布局
	
	private OnItemClickListener itemClickListener; //itemClick响应事件
	private MyListViewFragmentHandler fragmentHandler = new MyListViewFragmentHandler();
	private ProgressDialog proDialog;
	/**
	 * 
	 * @param fragmentLayout fragment布局
	 * @param viewListLayout viewList布局
	 * @param dataSource 數據源
	 * @param adapter 適配器類型
	 * @param itemClickListener 響應的item事件
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
	
	class GetInitDataTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			mDataSource.updateValue(0);
			listAdapter = new NewsListAdapter(getActivity(), mDataSource);
			set(listAdapter,itemClickListener);
			fragmentHandler.sendEmptyMessage(1);
			return listAdapter;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			result = (NewsListAdapter)listAdapter;
			mRefreshListView.setAdapter(listAdapter);
			
		}
		
	}
	
	private class MyListViewFragmentHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//如果连接中。。对话框还在 dismiss它
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
		
		proDialog = ProgressDialog.show(getActivity(), "连接中..",
				"连接中..请稍后....", true, true);
		GetInitDataTask task = new GetInitDataTask();  
		task.execute("no");
		Log.i("a","aaaaaa");
		
		
		//添加ItemClick响应事件
		mRefreshListView.setOnItemClickListener(itemClickListener);
		mRefreshListView.setonRefreshListener(this);
		return view;
	}

	protected void changeListView(int type){
		mDataSource.updateValue(type);
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			listAdapter.setCount();
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
					//模拟网络请求时间
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				changeListView(1);
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
				changeListView(2);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}


	private View inflateAndSetupView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState, int layoutResourceId) {
		return inflater.inflate(layoutResourceId, container, false);
	}
}
