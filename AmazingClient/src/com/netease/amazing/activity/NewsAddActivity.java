package com.netease.amazing.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
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
	public final static String PUBLISH_NEWS_TITLE = "发布动态";
	public final static String PUBLISH_NEWS = "发布";  
	public final static int NEW_INPUT_MAX_LENGTH = 10;
	public ImageButton buttonBack;
	public ImageButton buttonSend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_add);
		getView();
		setListener();
	}

	public void getView() {
		buttonBack = (ImageButton)findViewById(R.id.news_add_titlebar_back_btn);
		buttonSend = (ImageButton)findViewById(R.id.news_add_titlebar_save_btn);
	}
	
	public void setListener() {
		buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = ((EditText)findViewById(R.id.newsAdd_input_editText)).getText().toString();
				if(text.trim().length() > NEW_INPUT_MAX_LENGTH*2){
					Toast.makeText(NewsAddActivity.this, "请输入"+NEW_INPUT_MAX_LENGTH+"字以内的动态！",
						     Toast.LENGTH_SHORT).show();
				}else if(text.trim().length() > 0){
					new PublishNewsExecute().execute(text.trim());
				}else{
					Toast.makeText(NewsAddActivity.this, "请输入文字",
						     Toast.LENGTH_SHORT).show();
				}

			}
		});
		
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NewsAddActivity.this.finish();
			}
		});
	}

    /**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    发布动态的异步操作
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
			 * 清空动态消息
			 */
			if(result){
				Toast.makeText(NewsAddActivity.this, "添加成功",
					     Toast.LENGTH_SHORT).show();
				((EditText)findViewById(R.id.newsAdd_input_editText)).setText("");
			}else{
				Toast.makeText(NewsAddActivity.this, "添加失败",
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
