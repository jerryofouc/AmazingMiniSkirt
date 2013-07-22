package com.netease.amazing.activity;

import com.example.amazing.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class DimCodeButtonActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
	}
}
