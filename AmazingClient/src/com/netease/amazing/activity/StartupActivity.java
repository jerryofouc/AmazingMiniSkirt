package com.netease.amazing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.amazing.R;

public class StartupActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(this,LoginActivity.class);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.startup_screen);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		startActivity(intent);
	}
}
