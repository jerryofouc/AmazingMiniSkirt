package com.netease.amazing.component;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.activity.NewsPersonalIndexActivity;
import com.netease.amazing.adapter.ListViewBasedAdapter;
import com.netease.amazing.adapter.NewsListAdapter;
import com.netease.amazing.datasource.DataSource;
import com.netease.amazing.datasource.NewsDataSource;
import com.netease.amazing.pojo.News;
import com.netease.amazing.util.RefreshableListView;
import com.netease.amazing.util.RefreshableListView.OnRefreshListener;
import com.netease.amazing.util.ReturnBitmapFromInternet;
import com.netease.amazing.util.UserInfoStore;

/**
 * Updated by Huang Xiao Jun 2013.7.20
 * 
 * @author Huang Xiao Jun Class Desciption:
 *         ListViewFragment用於列表顯示，并且包括上拉和下拉刷新功能，響應item點擊事件
 */
public class NewsFragment extends Fragment implements OnRefreshListener {
	
	private RefreshableListView mRefreshListView;
	private DataSource newsDataSource = new NewsDataSource();
	private ListViewBasedAdapter listAdapter;

	private int fragmentLayout = R.layout.news_index; // fragment的布局
	private int viewListLayout = R.id.newsList; // viewList的布局
	private ProgressDialog proDialog;
	
	LinearLayout view;
	// item的响应事件
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "click" + String.valueOf(arg3),
					Toast.LENGTH_SHORT).show();
		}

	};
	private NewsListViewFragmentHandler fragmentHandler = new NewsListViewFragmentHandler();

	public void set(ListViewBasedAdapter listAdapter,
			OnItemClickListener itemClickListener) {
		this.listAdapter = listAdapter;
		this.itemClickListener = itemClickListener;
	}

	class GetInitNewsDataTask extends AsyncTask {
		private Bitmap backgroundBitmap;
		private Bitmap headBitmap;
		@Override
		protected Object doInBackground(Object... arg0) {
			newsDataSource.updateValue(NewsDataSource.NEWS_INIT_DATA);
			listAdapter = new NewsListAdapter(getActivity(), newsDataSource);
			set(listAdapter, itemClickListener);
			fragmentHandler.sendEmptyMessage(1);
			backgroundBitmap = ReturnBitmapFromInternet.returnBitMap(UserInfoStore.url+UserInfoStore.backgroundImageDir);
			headBitmap = ReturnBitmapFromInternet.returnBitMap(UserInfoStore.url+UserInfoStore.imageDir);
			return listAdapter;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			result = (NewsListAdapter) listAdapter;
			mRefreshListView.setAdapter(listAdapter);
			((ImageView)view.findViewById(R.id.news_index_image)).setImageBitmap(backgroundBitmap); 
			((ImageView)view.findViewById(R.id.news_user_img)).setImageBitmap(headBitmap); 
			((TextView)view.findViewById(R.id.news_user_name)).setText(UserInfoStore.username);
			((TextView)view.findViewById(R.id.news_user_signature)).setText(UserInfoStore.signature);
		}

	}

	private class NewsListViewFragmentHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 如果连接中。。对话框还在 dismiss它
			if (proDialog != null) {
				Log.i("testDialog", "Dialog");
				proDialog.dismiss();
			}
		}

	}

	/**/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		view = (LinearLayout) inflateAndSetupView(inflater,
				container, savedInstanceState, fragmentLayout);
				
		mRefreshListView = (RefreshableListView) view
				.findViewById(viewListLayout);

		proDialog = ProgressDialog.show(getActivity(), "连接中..", "连接中..请稍后....",
				true, true);
		GetInitNewsDataTask task = new GetInitNewsDataTask();
		task.execute("news");

		// 添加ItemClick响应事件
		mRefreshListView.setOnItemClickListener(itemClickListener);
		mRefreshListView.setonRefreshListener(this);
		view.findViewById(R.id.news_user_img).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity(),
								NewsPersonalIndexActivity.class);
						intent.putExtra(News.NEWS_USER_ID, UserInfoStore.userId);
						startActivity(intent);
					}

				});
		return view;
	}

	protected void changeListView(int type) {
		newsDataSource.updateValue(type);
	}

	private Handler handler = new Handler() {
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
				changeListView(NewsDataSource.NEWS_UP_REFRESH_DATA);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	@Override
	public void onPullDownRefresh() {
		new Thread() {
			public void run() {
				changeListView(NewsDataSource.NEWS_DOWN_REFRESH_DATA);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	private View inflateAndSetupView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState, int layoutResourceId) {
		return inflater.inflate(layoutResourceId, container, false);
	}
}
