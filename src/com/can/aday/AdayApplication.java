package com.can.aday;

import org.json.JSONException;
import org.json.JSONObject;

import com.can.aday.data.Music;
import com.can.aday.data.User;
import com.can.aday.utils.CacheTools;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AdayApplication extends Application {
	public static final String SERVICE_IP = "http://192.168.15.238:88/";
	private User currentUser;
	private String token;
	public boolean isOnline = true;// 是否与服务器保持连接

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

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean checkNetWork() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		@SuppressWarnings("deprecation")
		NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
		if (networkInfo != null && networkInfo.length > 0) {
			for (int i = 0; i < networkInfo.length; i++) {
				System.out.println(i + "===状态===" + networkInfo[i].getState());
				System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
				// 判断当前网络状态是否为连接状态
				if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
}
