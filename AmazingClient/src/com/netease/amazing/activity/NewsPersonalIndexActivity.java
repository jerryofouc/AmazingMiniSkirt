package com.netease.amazing.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.adapter.ListViewBasedAdapter;
import com.netease.amazing.adapter.NewsPersonalListAdapter;
import com.netease.amazing.datasource.DataSource;
import com.netease.amazing.datasource.NewsDataSource;
import com.netease.amazing.datasource.NewsPersonalDataSource;
import com.netease.amazing.pojo.News;
import com.netease.amazing.util.RefreshableListView;
import com.netease.amazing.util.RefreshableListView.OnRefreshListener;
import com.netease.amazing.util.ReturnBitmapFromInternet;
import com.netease.amazing.util.UserInfoStore;
/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   个人主页，即成长日志
 */
public class NewsPersonalIndexActivity extends Activity implements OnRefreshListener{
	public final static String PERSONAL_NEWS_INDEX_TITLE = "成长日志";
	public final static String MESSAGE_HISTORY = "历史消息"; 
	public final static String GENERATE_PHOTOS = "生成相册"; 
	
	private RefreshableListView personalNewsList;
	private DataSource mDataSource = new NewsPersonalDataSource();
	private ListViewBasedAdapter personalNewsListAdapter;
	
	private int newsPersonalLayout = R.layout.news_index;
	private int newsPersonalViewListLayout = R.id.newsList;
	
	private OnItemClickListener itemClickListener; //itemClick响应事件
	private PersonalNewsListViewHandler inithandler = new PersonalNewsListViewHandler();
	private ProgressDialog proDialog;
	
	private long newsIndexUserId;//当前传递过来的用户ID
	private TextView newsIndexUserName;
	private TextView newsIndexUserSignature;
	private ImageView newsIndexUserHeadImage;
	private ImageView newsIndexUserBackgroudImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(newsPersonalLayout);
		Intent intent = getIntent();
		newsIndexUserId = intent.getLongExtra(News.NEWS_USER_ID, 0);
		
		((NewsPersonalDataSource)mDataSource).setUserId(newsIndexUserId);
		
		newsIndexUserName = (TextView) findViewById(R.id.news_user_name);
		newsIndexUserSignature = (TextView) findViewById(R.id.news_user_signature);
		newsIndexUserHeadImage = (ImageView) findViewById(R.id.news_user_img);
		newsIndexUserBackgroudImage = (ImageView) findViewById(R.id.news_index_image);
		personalNewsList = (RefreshableListView)findViewById(newsPersonalViewListLayout);
		setUpActionBar();
		proDialog = ProgressDialog.show(NewsPersonalIndexActivity.this, "连接中..",
				"连接中..请稍后....", true, true);
		new GetPersonalNewsInitListDataTask().execute(newsIndexUserId);
		personalNewsList.setOnItemClickListener(itemClickListener);
		personalNewsList.setonRefreshListener(this);
	}
	
	public void set(ListViewBasedAdapter listAdapter,OnItemClickListener itemClickLister) {
		this.personalNewsListAdapter = listAdapter;
		this.itemClickListener = itemClickListener;
	}
	/**
	 * 
	 * @author Huang Xiao Jun
	 *
	 */
	class GetPersonalNewsInitListDataTask extends AsyncTask<Long,String,Object> {
		private long userId;
		private Bitmap headImage;
		private Bitmap backgroundImage;

		@Override
		protected Object doInBackground(Long... arg0) {
			this.userId = arg0[0];
			mDataSource.updateValue(NewsDataSource.NEWS_INIT_DATA);
			personalNewsListAdapter = new NewsPersonalListAdapter(NewsPersonalIndexActivity.this, mDataSource);
			set(personalNewsListAdapter,itemClickListener);
			if(userId == UserInfoStore.userId){
				headImage = ReturnBitmapFromInternet.
						returnBitMap(UserInfoStore.url+UserInfoStore.imageDir);
				backgroundImage = ReturnBitmapFromInternet.
						returnBitMap(UserInfoStore.url+UserInfoStore.backgroundImageDir);
			}else{
				/**
				 * 查看他人主页获取的信息
				 */
			}
			inithandler.sendEmptyMessage(1);
			return personalNewsListAdapter;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			result = (NewsPersonalListAdapter)personalNewsListAdapter;
			personalNewsList.setAdapter(personalNewsListAdapter);
			if(userId == UserInfoStore.userId){
				newsIndexUserName.setText(UserInfoStore.username);
				newsIndexUserSignature.setText(UserInfoStore.signature);
				newsIndexUserHeadImage.setImageBitmap(headImage);
				newsIndexUserBackgroudImage.setImageBitmap(backgroundImage);
			}else{
				/**
				 * 查看他人主页设置相关的View
				 */
			}
			
		}
		
	}
	
	private class PersonalNewsListViewHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//如果连接中。。对话框还在 dismiss它
			if(proDialog != null) {
				proDialog.dismiss();
			}
		}
		
	}
	
    private void setUpActionBar() {
    	ActionBar actionBar = getActionBar();
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);//设置no tabs
    	actionBar.setDisplayHomeAsUpEnabled(true);//显示up back button
    	//设置actionBar的title bar ， 自定义样式
    	actionBar.setDisplayShowCustomEnabled(true);
    	actionBar.setCustomView(R.layout.apk_actionbar_title);
    	TextView actionBarTitle = (TextView)findViewById(R.id.main_title);
    	actionBarTitle.setText(PERSONAL_NEWS_INDEX_TITLE);
    }
    
    @Override
    /**
     * 点击menu按钮时
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        menu.getItem(0).setTitle(MESSAGE_HISTORY);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(NewsPersonalIndexActivity.this, MESSAGE_HISTORY,
					     Toast.LENGTH_SHORT).show();
				return true;
			}
        	
        });
        
        menu.getItem(1).setTitle(GENERATE_PHOTOS);
        menu.getItem(1).setVisible(true);
        menu.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.getItem(1).setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(NewsPersonalIndexActivity.this, GENERATE_PHOTOS,
					     Toast.LENGTH_SHORT).show();
				return true;
			}
        	
        });
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * back listener
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {       
        onBackPressed();
        return true;
    }
    
	protected void changeListView(int type){
		mDataSource.updateValue(type);
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			personalNewsListAdapter.setCount();
			personalNewsListAdapter.notifyDataSetChanged();
			personalNewsList.onRefreshComplete();
			super.handleMessage(msg);
		}
	};

	@Override
	public void onPullUpRefresh() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				changeListView(NewsPersonalDataSource.NEWS_GROWTH_UP_REFRESH_DATA);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	@Override
	public void onPullDownRefresh() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				changeListView(NewsPersonalDataSource.NEWS_GROWTH_DOWN_REFRESH_DATA);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}
}