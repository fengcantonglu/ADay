package com.can.aday.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class CacheTools {

	/**
	 * 缓存登陆行用户名和密码且保存登陆状态为true
	 */
	public static void cachedPageGuide(String account, String password, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("account", account);
		editor.putString("password", password);
		editor.commit();
		loginStateSave(context, true);
	}

	/**
	 * 保存登陆状态,isLogin为true存已登陆状态,则跳转不会进入登陆页面,isLogin为false为注销缓存登陆状态
	 * 
	 * @param context
	 * @param isLogin
	 */
	public static void loginStateSave(Context context, boolean isLogin) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("isLogin", isLogin);
		editor.commit();
	}

	/**
	 * 获取缓存登陆状态
	 * 
	 * @param context
	 * @return true 已登陆,false 未登录
	 */
	public static boolean getLoginState(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin", Activity.MODE_PRIVATE);
		return sharedPreferences.getBoolean("isLogin", false);
	}

	/**
	 * 获取缓存里面的登陆数据,
	 * </p>
	 * ap[0]为获取的帐户名； ap[1]为获取的账户密码
	 * 
	 * @param context
	 * @param ap
	 * @throws NullPointerException
	 *             如果 context或者 ap为空时
	 * @throws ArrayIndexOutOfBoundsException
	 *             ap.length<2
	 */
	public static void getAccountCache(Context context, String[] ap)
			throws NullPointerException, ArrayIndexOutOfBoundsException {
		SharedPreferences sharedPreferences = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
		ap[0] = sharedPreferences.getString("account", "");
		ap[1] = sharedPreferences.getString("password", "");
	}
}
