package com.can.aday;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;

import com.can.aday.tools.HttpGet;
import com.can.aday.tools.HttpPost.OnSendListener;
import com.can.aday.utils.CacheTools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class WelcomeActivity extends Activity {
	private boolean isLogin;
	private int isLoginSuccessed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (isFristRun()) {
			showWelcome();
		} else {
			RelativeLayout layout = new RelativeLayout(this);
			layout.setLayoutParams(new LayoutParams(-1, -1));
			View mView = new View(this);
			mView.setLayoutParams(new LayoutParams(-1, -1));
			mView.setBackgroundResource(R.drawable.welcome_image_1);
			layout.addView(mView);
			setContentView(layout);
			isLogin = CacheTools.getLoginState(this);

			if (isLogin)
				loginInbackgroud();
			mHandler.postDelayed(new Runnable() {
				public void run() {
					mHandler.sendEmptyMessage(1);
				}
			}, 3000);
		}
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		int count;

		public void handleMessage(android.os.Message msg) {
			count++;
			if (count == 5)
				isLoginSuccessed = -2;
			isShowWelcome();
		};
	};

	/**
	 * 执行进入登陆界面或者首页
	 */
	public void isShowWelcome() {
		Intent intent = new Intent();
		if (!isLogin || isLoginSuccessed == -1)
			intent.setClass(WelcomeActivity.this, LoginAndRegisteredActivity.class);
		else if (isLoginSuccessed == -2) {// 离线浏览模式
			com.can.aday.AdayApplication app = (com.can.aday.AdayApplication) getApplication();
			app.isOnline = false;
			app.currentMusic = CacheTools.getLocalMusicData(getApplicationContext(), 0);
			app.currentBook = CacheTools.getLocalBookData(getApplicationContext(), 0);
			Log.w("Welcome", "与服务器链接超时,正在处于离线模式浏览");
			intent.setClass(WelcomeActivity.this, MainActivity.class);
		} else {
			if (isLoginSuccessed == 0) {
				mHandler.postDelayed(new Runnable() {
					public void run() {
						mHandler.sendEmptyMessage(1);
					}
				}, 600);
				return;
			}
			intent.setClass(WelcomeActivity.this, MainActivity.class);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		finish();
	}

	/**
	 * 第一次运行欢迎见面，将引导界面设置已读信息设置为true
	 */
	public void cachedPageGuide() {
		SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("welcome", true);
		editor.commit();
	}

	/**
	 * 判断是否第一次运行本app
	 * 
	 * @return
	 */
	public boolean isFristRun() {
		SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
		boolean isshare = sharedPreferences.getBoolean("welcome", false);
		return !isshare;
	}

	private void loginInbackgroud() {
		final AdayApplication app = (AdayApplication) getApplication();

		if (!app.checkNetWork()) {
			isLoginSuccessed = -2;
			return;
		}
		String[] ap = new String[2];
		CacheTools.getAccountCache(getApplicationContext(), ap);
		HttpGet httpGet = HttpGet.parseUrl(AdayApplication.SERVICE_IP + "login");
		httpGet.putString("username", ap[0]);
		httpGet.putString("password", ap[1]);
		httpGet.setOnSendListener(new OnSendListener() {

			public void start() {

			}

			public void end(String result) {
				try {
					JSONObject jo = new JSONObject(result);

					app.loginDataExec(jo);
					isLoginSuccessed = 1;

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isLoginSuccessed = -1;
				}
			}
		});
		httpGet.send();
	}

	/**
	 * 如果第一次运行本程序，执行欢迎界面
	 */
	public void showWelcome() {
		Class<?> localClass = ViewPagerActivity.class;
		try {
			Constructor<?> localConstructor = localClass.getConstructor(new Class[] {});
			Object instance = localConstructor.newInstance(new Object[] {});
			Method setProxy = localClass.getMethod("setProxy", new Class[] { Activity.class });
			setProxy.setAccessible(true);
			setProxy.invoke(instance, new Object[] { this });
			Method onCreate = localClass.getDeclaredMethod("onCreate", new Class[] { Bundle.class });
			onCreate.setAccessible(true);
			onCreate.invoke(instance, new Object[] { new Bundle() });
			cachedPageGuide();
			Log.i("ProxyActivity", "寄生页面加载成功");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
