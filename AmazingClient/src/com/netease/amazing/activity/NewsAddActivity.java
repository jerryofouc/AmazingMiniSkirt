package com.netease.amazing.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;
/**
 * 
 * @author Huang Xiao Jun
 *
 */
public class NewsAddActivity extends Activity{
	public final static String PUBLISH_NEWS_TITLE = "发布动态";
	public final static String PUBLISH_NEWS = "发布";  
	public final static int NEW_INPUT_MAX_LENGTH = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.news_add);
		setUpActionBar();
	}
    private void setUpActionBar() {
    	ActionBar actionBar = getActionBar();
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);//设置no tabs
    	actionBar.setDisplayHomeAsUpEnabled(true);//显示up back button
    	//设置actionBar的title bar ， 自定义样式
    	actionBar.setDisplayShowCustomEnabled(true);
    	actionBar.setCustomView(R.layout.apk_actionbar_title);
    	TextView actionBarTitle = (TextView)findViewById(R.id.main_title);
    	actionBarTitle.setText(PUBLISH_NEWS_TITLE);
    }
    @Override
    /**
     * 点击menu按钮时
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
					Toast.makeText(NewsAddActivity.this, "请输入"+NEW_INPUT_MAX_LENGTH+"字以内的动态！",
						     Toast.LENGTH_SHORT).show();
				}else if(text.trim().length() > 0){
					Toast.makeText(NewsAddActivity.this, text.trim(),
						     Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(NewsAddActivity.this, "请输入文字",
						     Toast.LENGTH_SHORT).show();
				}

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
}
