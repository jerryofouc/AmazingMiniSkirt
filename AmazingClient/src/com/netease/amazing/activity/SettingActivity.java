package com.netease.amazing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.amazing.R;

public class SettingActivity extends Activity {

	private Button userInfoButton;
	private Button dimCodeButton;
	private Button accountSettingButton;
	private Button systemSettingButton;
	private Button aboutUsButton;
	private Button contactUsButton;
	private Button logoutButton;
	private ImageButton backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting);
		getView();
		setListener();
	}
	
	public void getView() {
		backButton = (ImageButton)findViewById(R.id.setting_titlebar_back_btn);
		userInfoButton  = (Button)findViewById(R.id.setting_userInfo_btn);
		dimCodeButton  = (Button)findViewById(R.id.setting_dimCode_btn);
		accountSettingButton  = (Button)findViewById(R.id.setting_accountSetting_btn);
		systemSettingButton  = (Button)findViewById(R.id.setting_systemSetting_btn);
		aboutUsButton  = (Button)findViewById(R.id.setting_aboutUs_btn);
		contactUsButton  = (Button)findViewById(R.id.setting_contactUs_btn);
		logoutButton  = (Button)findViewById(R.id.setting_logout_btn);
	}
	
	public void setListener() {
		userInfoButton.setOnClickListener(new MyOnclickLisenter(SetUserInfoActivity.class));
		dimCodeButton.setOnClickListener(new MyOnclickLisenter(DimCodeButtonActivity.class));
		accountSettingButton.setOnClickListener(new MyOnclickLisenter(AccountSettingButtonActivity.class));
		systemSettingButton.setOnClickListener(new MyOnclickLisenter(SystemSettingButtonActivity.class));
		aboutUsButton.setOnClickListener(new MyOnclickLisenter(AboutUsButtonActivity.class));
		contactUsButton.setOnClickListener(new MyOnclickLisenter(ContactUsButtonActivity.class));
		logoutButton.setOnClickListener(new LogoutOnClickListener());
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SettingActivity.this.finish();
			}
		});
	}
	
	class MyOnclickLisenter implements OnClickListener {
		
		Class activityClass;
		public MyOnclickLisenter(Class activityClass) {
			super();
			this.activityClass = activityClass;
		}
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(SettingActivity.this,activityClass);
			startActivity(intent);
		}
	}
	
	class LogoutOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 startActivity(intent);
		}
		
	}
}
