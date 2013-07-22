package com.netease.amazing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.amazing.R;
import com.netease.amazing.pojo.Notice;

public class NoticeActivity extends Activity{

	TextView noticeContent;
	ImageButton noticeBack;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notice);
		noticeBack = (ImageButton)findViewById(R.id.notice_titlebar_back_btn);
		noticeContent = (TextView)findViewById(R.id.notice_view_content);
		Intent intent = getIntent();
		Notice notice = (Notice)(intent.getExtras().getSerializable("itemNotice"));
		noticeContent.setText(notice.getContent());
		noticeBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				noticeBack.setBackgroundColor(0xffEE0000);
				NoticeActivity.this.finish();
			}
		});
		
	}
}
