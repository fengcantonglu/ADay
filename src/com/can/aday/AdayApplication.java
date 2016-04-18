package com.can.aday;

import org.json.JSONException;
import org.json.JSONObject;

import com.can.aday.data.Music;
import com.can.aday.data.User;
import com.can.aday.utils.CacheTools;

import android.app.Application;
import android.util.Log;

public class AdayApplication extends Application {
	public static final String SERVICE_IP = "http://192.168.15.238:88/";
	private User currentUser;
	private String token;

	/**
	 * 当前显示的音乐数据
	 */
	public Music currentMusic;

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
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * 登陆数据处理
	 * 
	 * @param jo
	 *            登陆成功过后,的json数据
	 */
	public void loginDataExec(JSONObject jo) {
		try {
			Log.i("LoginResult", jo.toString());
			token = jo.getString("token");// parse
			currentUser = User.parseJSONObject(jo.getJSONObject("user"));

		} catch (JSONException e) {

		}
		try {
			currentMusic = Music.parseJSONObject(jo.getJSONObject("currentmusic"));
			CacheTools.getLocalOrSaveMusicData(getApplicationContext(), currentMusic);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			Music lastMusic = Music.parseJSONObject(jo.getJSONObject("lastmusic"));
			CacheTools.getLocalOrSaveMusicData(getApplicationContext(), lastMusic);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			Music nextMusic = Music.parseJSONObject(jo.getJSONObject("nextmusic"));
			CacheTools.getLocalOrSaveMusicData(getApplicationContext(), nextMusic);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
