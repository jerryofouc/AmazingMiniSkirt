package com.netease.amazing.component;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.activity.NoticeActivity;
import com.netease.amazing.adapter.ListViewBasedAdapter;
import com.netease.amazing.adapter.NoticeListAdapter;
import com.netease.amazing.datasource.NoticeDataSource;
import com.netease.amazing.pojo.Notice;
import com.netease.amazing.util.RefreshableListView;
import com.netease.amazing.util.RefreshableListView.OnRefreshListener;

/**
 * 
 * @author Huang Xiao Jun
 * Class Desciption:
 *   ListViewFragment用於列表顯示，并且包括上拉和下拉刷新功能，響應item點擊事件
 *
 */
public class NoticeFragment extends Fragment implements OnRefreshListener {
	
	private RefreshableListView mRefreshListView;
	private NoticeDataSource mDataSource = new NoticeDataSource();
	private ListViewBasedAdapter listAdapter;
	private final static int LIST_VIEW_PAGE_SIZE = 10;
	
	private int fragmentLayout = R.layout.notice_index;   //fragment的布局
	private int viewListLayout = R.id.mineList;   //viewList的布局
	
	private OnItemClickListener itemClickListener = new MyOnItemClickListener(); //itemClick响应事件
	private MyListViewFragmentHandler fragmentHandler = new MyListViewFragmentHandler();
	private ProgressDialog proDialog;
	private boolean status =true;
	
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
			NoticeDataSource dataSource,
			OnItemClickListener itemClickListener){
		this.fragmentLayout = fragmentLayout;
		this.viewListLayout = viewListLayout;
		this.mDataSource = dataSource;
		this.itemClickListener = itemClickListener;
	}
	
	public void set(ListViewBasedAdapter listAdapter) {
		this.listAdapter = listAdapter;
	}
	
	class GetInitDataTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			status = mDataSource.updateValue(0);
			listAdapter = new NoticeListAdapter(getActivity(), mDataSource);
			set(listAdapter);
			return listAdapter;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			
			result = (NoticeListAdapter)listAdapter;
			mRefreshListView.setAdapter(listAdapter);
			fragmentHandler.sendEmptyMessage(1);
		}
		
	}
	class MyListViewFragmentHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//如果连接中。。对话框还在 dismiss它
			if(proDialog != null) {
				proDialog.dismiss();
			}
			if(!status) {
				Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
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
		
		
		//添加ItemClick响应事件
		mRefreshListView.setOnItemClickListener(itemClickListener);
		mRefreshListView.setonRefreshListener(this);
		return view;
	}

	class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Log.i("position","1");
			Log.i("the length", mDataSource.getNoticeList().size() +"");
			Notice itemNotice = mDataSource.getNoticeList().get(position-1);
			Bundle bundle = new Bundle();
			bundle.putSerializable("itemNotice", itemNotice);
			Intent intent = new Intent(getActivity(),NoticeActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	}
	
	protected void changeListView(int type){
		Log.i("you are pull downing", "test changeListView");
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
				changeListView(2);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	@Override
	public void onPullDownRefresh() {
		new Thread() {
			public void run() {
				changeListView(1);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}


	private View inflateAndSetupView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState, int layoutResourceId) {
		return inflater.inflate(layoutResourceId, container, false);
	}
}
