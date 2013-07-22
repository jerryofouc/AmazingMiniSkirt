package com.netease.amazing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.example.amazing.R;


public class AboutUsButtonActivity extends Activity {
	
	private ImageButton backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_aboutus);
		getView();
		setListener();
	}
	public void getView() {
		backButton = (ImageButton)findViewById(R.id.set_user_info_titlebar_back_btn);
	}
	
	public void setListener() {
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				AboutUsButtonActivity.this.finish();
			}
		});
	}
}
