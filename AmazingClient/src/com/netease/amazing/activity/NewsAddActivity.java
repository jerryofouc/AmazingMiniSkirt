package com.netease.amazing.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.dbhandler.NewsDataHandler;
import com.netease.amazing.pojo.News;
/**
 * 
 * @author Huang Xiao Jun
 *
 */
public class NewsAddActivity extends Activity{
	public final static String PUBLISH_NEWS_TITLE = "������̬";
	public final static String PUBLISH_NEWS = "����";  
	public final static int NEW_INPUT_MAX_LENGTH = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.news_add);
		setUpActionBar();
	}
    private void setUpActionBar() {
    	ActionBar actionBar = getActionBar();
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);//����no tabs
    	actionBar.setDisplayHomeAsUpEnabled(true);//��ʾup back button
    	//����actionBar��title bar �� �Զ�����ʽ
    	actionBar.setDisplayShowCustomEnabled(true);
    	actionBar.setCustomView(R.layout.apk_actionbar_title);
    	TextView actionBarTitle = (TextView)findViewById(R.id.main_title);
    	actionBarTitle.setText(PUBLISH_NEWS_TITLE);
    }
    @Override
    /**
     * ���menu��ťʱ
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        menu.getItem(0).setTitle(PUBLISH_NEWS);
        menu.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				String text = ((EditText)findViewById(R.id.newsAdd_input_editText)).getText().toString();
				if(text.trim().length() > NEW_INPUT_MAX_LENGTH*2){
					Toast.makeText(NewsAddActivity.this, "������"+NEW_INPUT_MAX_LENGTH+"�����ڵĶ�̬��",
						     Toast.LENGTH_SHORT).show();
				}else if(text.trim().length() > 0){
					new PublishNewsExecute().execute(text.trim());
				}else{
					Toast.makeText(NewsAddActivity.this, "����������",
						     Toast.LENGTH_SHORT).show();
				}

				return true;
			}
        	
        });
        return super.onCreateOptionsMenu(menu);
    }
    /**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    ������̬���첽����
	 */		
	class PublishNewsExecute extends AsyncTask<String,Integer,Boolean> {
		Bitmap bit;

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			return NewsDataHandler.addNews(params[0], null, News.NEWS_WITH_NOTHING);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			/**
			 * ��ն�̬��Ϣ
			 */
			if(result){
				Toast.makeText(NewsAddActivity.this, "��ӳɹ�",
					     Toast.LENGTH_SHORT).show();
				((EditText)findViewById(R.id.newsAdd_input_editText)).setText("");
			}else{
				Toast.makeText(NewsAddActivity.this, "���ʧ��",
					     Toast.LENGTH_SHORT).show();
			}
			
		}
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
}
