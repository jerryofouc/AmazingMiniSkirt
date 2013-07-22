package com.netease.amazing.component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.activity.NewsPersonalIndexActivity;
import com.netease.amazing.util.DataSource;
import com.netease.amazing.util.ListViewBasedAdapter;
import com.netease.amazing.util.NewsDataSource;
import com.netease.amazing.util.NewsListAdapter;
import com.netease.amazing.util.RefreshableListView;
import com.netease.amazing.util.RefreshableListView.OnRefreshListener;

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
		private Bitmap bitmap;
		@Override
		protected Object doInBackground(Object... arg0) {
			newsDataSource.updateValue(NewsDataSource.NEWS_INIT_DATA);
			listAdapter = new NewsListAdapter(getActivity(), newsDataSource);
			set(listAdapter, itemClickListener);
			fragmentHandler.sendEmptyMessage(1);
			bitmap = returnBitMap("http://content.52pk.com/files/100623/2230_102437_1_lit.jpg");
			return listAdapter;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			result = (NewsListAdapter) listAdapter;
			mRefreshListView.setAdapter(listAdapter);
			ImageView imageView = (ImageView)view.findViewById(R.id.news_index_image); 

			imageView.setImageBitmap(bitmap); 
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

	public Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			URLConnection conn = myFileUrl
					.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bitmap = BitmapFactory.decodeStream(bis);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
