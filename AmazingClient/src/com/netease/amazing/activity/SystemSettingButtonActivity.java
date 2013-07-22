package com.netease.amazing.activity;

import com.example.amazing.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SystemSettingButtonActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_system);
		
	}
}
