package com.netease.amazing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.amazing.R;
import com.netease.amazing.pojo.Notice;

public class NoticeActivity extends Activity{

	TextView noticeText;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);
		TextView noticeText = (TextView)findViewById(R.id.notice);
		Intent intent = getIntent();
		Notice notice = (Notice)(intent.getExtras().getSerializable("notice"));
		noticeText.setText("noticeµÄidÎª: " +notice.getId() + "                  " +notice.getContent());
	}
}
