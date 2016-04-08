package com.can.aday;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class WelcomeActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(-1, -1));
		View mView = new View(this);
		mView.setLayoutParams(new LayoutParams(-1, -1));
		mView.setBackgroundResource(R.drawable.welcome_image_1);
		layout.addView(mView);
		setContentView(layout);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				isShowWelcome();
			}
		}, 3000);
	}

	/**
	 * 执行进入引导页面
	 */
	public void isShowWelcome() {
		Intent intent = new Intent();
		SharedPreferences sharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
		boolean isshare = sharedPreferences.getBoolean("welcome", false);
		if (!isshare) {
			intent.setClass(WelcomeActivity.this, ViewPagerActivity.class);
			cachedPageGuide();
			startActivity(intent);
			finish();
		} else {
			intent.setClass(WelcomeActivity.this, LoginAndRegisteredActivity.class);
			startActivity(intent);
			finish();
		}
	}

	/**
	 * 缓存引导页信息
	 */
	public void cachedPageGuide() {
		SharedPreferences sharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("welcome", true);
		editor.commit();
	}

}
