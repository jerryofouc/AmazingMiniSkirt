package com.netease.amazing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.amazing.R;
import com.netease.amazing.pojo.Notice;

public class NoticeActivity extends Activity{

	TextView noticeContent;
	TextView noticeTitle;
	Button noticeBack;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);
		noticeBack = (Button)findViewById(R.id.notice_view_back);
		noticeTitle =(TextView)findViewById(R.id.notice_view_title);
		noticeContent = (TextView)findViewById(R.id.notice_view_content);
		Intent intent = getIntent();
		Notice notice = (Notice)(intent.getExtras().getSerializable("itemNotice"));
		noticeTitle.setText(notice.getTitle());
		noticeContent.setText(notice.getContent());
		noticeBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				noticeBack.setBackgroundColor(Color.BLUE);
				NoticeActivity.this.finish();
			}
		});
		
	}
}
