package com.netease.amazing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.amazing.R;

public class SystemSettingButtonActivity extends Activity {
	
	private Button backButton;
	private Button themeButton;
	private ToggleButton taskBarButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_system);
		getView();
		setListener();
		
	}
	
	public void getView() {
		backButton = (Button)findViewById(R.id.setting_system_back_btn);
		themeButton = (Button)findViewById(R.id.setSystem_theme_btn);
		taskBarButton = (ToggleButton)findViewById(R.id.setSystem_notice_toggle);
	}
	public void setListener() {
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SystemSettingButtonActivity.this.finish();
			}
		});
		
		themeButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			}
		});
		
		taskBarButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			}
		});
	}
}
