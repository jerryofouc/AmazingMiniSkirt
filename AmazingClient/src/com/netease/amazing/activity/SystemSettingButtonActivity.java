package com.netease.amazing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.example.amazing.R;

public class SystemSettingButtonActivity extends Activity {
	
	private Button backButton;
	private Button themeButton;
	private Button taskBarButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_system);
		getView();
		setListener();
		
	}
	
	public void getView() {
	}
	public void setListener() {
		
	}
}
