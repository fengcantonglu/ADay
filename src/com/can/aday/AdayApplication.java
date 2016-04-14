package com.can.aday;

import com.can.aday.data.User;

import android.app.Application;

public class AdayApplication extends Application {
	public static final String SERVICE_IP = "http://192.168.15.238:88/";
	private User currentUser;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * 获取登陆用户数据
	 * @return
	 */
	public User getCurrentUser() {
		return currentUser;
	}
}
