package com.can.aday;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.can.aday.utils.CacheTools;

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
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					isShowWelcome();
				}
			}, 3000);
		}
	}

	/**
	 * 执行进入登陆界面或者首页
	 */
	public void isShowWelcome() {
		Intent intent = new Intent();
		if (!isLogin)
			intent.setClass(WelcomeActivity.this, LoginAndRegisteredActivity.class);
		else
			intent.setClass(WelcomeActivity.this, MainActivity.class);
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
