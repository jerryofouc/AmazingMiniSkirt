package com.netease.amazing.component;

import android.app.Application;

public class MyApplication extends Application {
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		setUsername("");
		setPassword("");
	}
	
}
