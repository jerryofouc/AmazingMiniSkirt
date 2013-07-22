package com.netease.amazing.activity;

import com.example.amazing.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SetUserInfoActivity extends Activity {
	
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_user_info);
		getView();
		setListener();
	}
	
	public void getView() {
		backButton = (Button)findViewById(R.id.set_user_info_titlebar_back_btn);
	}
	
	public void setListener() {
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SetUserInfoActivity.this.finish();
			}
		});
	}
}
