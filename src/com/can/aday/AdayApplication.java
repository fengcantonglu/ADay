package com.can.aday;

import org.json.JSONException;
import org.json.JSONObject;

import com.can.aday.data.Book;
import com.can.aday.data.Music;
import com.can.aday.data.User;
import com.can.aday.utils.CacheTools;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AdayApplication extends Application {
	public static final String SERVICE_IP = "http://cloud.bmob.cn/7d49339e79332815/";
	public static final String SERVICE_BOOK = "http://192.168.15.253:1002/";
	private User currentUser;
	private String token;
	public boolean isOnline = true;// 是否与服务器保持连接

	/**
	 * 当前显示的音乐数据
	 */
	public Music currentMusic;

	/**
	 * 当前显示的读物数据
	 */
	public Book currentBook;

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
			currentUser = User.parseJSONObject(jo.getJSONObject("user"));

		} catch (JSONException e) {

		}
		try {
			currentMusic = Music.parseJSONObject(jo.getJSONObject("music"));
			CacheTools.getLocalOrSaveMusicData(getApplicationContext(), currentMusic);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		Book b;
		try {
			b = Book.parseJSONObject(jo.getJSONObject("book"));
			CacheTools.getBookDataOrSave(getApplicationContext(), b);
			currentBook = b;
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
				// 判断当前网络状态是否为连接状态
				if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
					System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
					return true;
				}
			}
		}
		return false;
	}
}
