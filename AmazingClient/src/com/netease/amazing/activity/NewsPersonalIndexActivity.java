package com.netease.amazing.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.util.DataSource;
import com.netease.amazing.util.ListViewBasedAdapter;
import com.netease.amazing.util.NewsDataSource;
import com.netease.amazing.util.NewsPersonalDataSource;
import com.netease.amazing.util.NewsPersonalListAdapter;
import com.netease.amazing.util.RefreshableListView;
import com.netease.amazing.util.RefreshableListView.OnRefreshListener;
/**
 * 
 * @author Huang Xiao Jun
 *
 */
public class NewsPersonalIndexActivity extends Activity implements OnRefreshListener{
	public final static String PERSONAL_NEWS_INDEX_TITLE = "�ɳ���־";
	public final static String MESSAGE_HISTORY = "��ʷ��Ϣ"; 
	public final static String GENERATE_PHOTOS = "�������"; 
	
	private RefreshableListView personalNewsList;
	private DataSource mDataSource = new NewsPersonalDataSource();
	private ListViewBasedAdapter personalNewsListAdapter;
	
	private int newsPersonalLayout = R.layout.news_index;
	private int newsPersonalViewListLayout = R.id.newsList;
	
	private OnItemClickListener itemClickListener; //itemClick��Ӧ�¼�
	private PersonalNewsListViewHandler inithandler = new PersonalNewsListViewHandler();
	private ProgressDialog proDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(newsPersonalLayout);
		personalNewsList = (RefreshableListView)findViewById(newsPersonalViewListLayout);
		setUpActionBar();
		proDialog = ProgressDialog.show(NewsPersonalIndexActivity.this, "������..",
				"������..���Ժ�....", true, true);
		new GetPersonalNewsInitListDataTask().execute("OK");
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
	class GetPersonalNewsInitListDataTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			mDataSource.updateValue(NewsDataSource.NEWS_INIT_DATA);
			personalNewsListAdapter = new NewsPersonalListAdapter(NewsPersonalIndexActivity.this, mDataSource);
			set(personalNewsListAdapter,itemClickListener);
			inithandler.sendEmptyMessage(1);
			return personalNewsListAdapter;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			result = (NewsPersonalListAdapter)personalNewsListAdapter;
			personalNewsList.setAdapter(personalNewsListAdapter);
			
		}
		
	}
	
	private class PersonalNewsListViewHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//��������С����Ի����� dismiss��
			if(proDialog != null) {
				proDialog.dismiss();
			}
		}
		
	}
	
    private void setUpActionBar() {
    	ActionBar actionBar = getActionBar();
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);//����no tabs
    	actionBar.setDisplayHomeAsUpEnabled(true);//��ʾup back button
    	//����actionBar��title bar �� �Զ�����ʽ
    	actionBar.setDisplayShowCustomEnabled(true);
    	actionBar.setCustomView(R.layout.apk_actionbar_title);
    	TextView actionBarTitle = (TextView)findViewById(R.id.main_title);
    	actionBarTitle.setText(PERSONAL_NEWS_INDEX_TITLE);
    }
    
    @Override
    /**
     * ���menu��ťʱ
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
				try {
					//ģ����������ʱ��
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
		// TODO Auto-generated method stub
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
}