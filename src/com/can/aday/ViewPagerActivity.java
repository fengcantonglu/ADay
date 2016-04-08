package com.can.aday;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ViewPagerActivity extends Activity {

	private ViewPager mView;
	PagerAdapter pager;
	// 底部小店图片
	private int[] dots = { R.drawable.welcome_progress_1, R.drawable.welcome_progress_2,
			R.drawable.welcome_progress_3 };
	private int[] pics = { R.drawable.welcome_image_1, R.drawable.welcome_image_2, R.drawable.welcome_image_4 };
	RadioButton[] radioButton;
	RadioGroup radioGroup;
	ImageView image;
	View text;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		radioGroup = (RadioGroup) findViewById(R.id.welocm_radio_group);
		mView = (ViewPager) findViewById(R.id.welcom_view_pager);
		image = (ImageView) findViewById(R.id.welcom_image_view);
		text = findViewById(R.id.welcom_text);
		pager = new PagerAdapter();
		radioButton = new RadioButton[] { (RadioButton) findViewById(R.id.welcom_radio_1),
				(RadioButton) findViewById(R.id.welcom_radio_2), (RadioButton) findViewById(R.id.welcom_radio_3) };
		mView.setAdapter(pager);
		mView.setOnPageChangeListener(pageChang);
		radioGroup.setOnCheckedChangeListener(change);
		handler.postDelayed(run, 5000);
	}

	Runnable run = new Runnable() {

		@Override
		public void run() {
			handler.sendEmptyMessage(1);
		}
	};
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (mView.getCurrentItem() < pics.length) {
				mView.setCurrentItem(mView.getCurrentItem() + 1);
				handler.postDelayed(run, 5000);
			}
		};
	};

	private OnCheckedChangeListener change = new OnCheckedChangeListener() {

		private OnClickListener oncl = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewPagerActivity.this, LoginAndRegisteredActivity.class);
				startActivity(intent);
			}
		};

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			for (int i = 0; i < radioButton.length; i++) {
				if (radioButton[i].isChecked()) {
					image.setImageResource(dots[i]);
					mView.setCurrentItem(i);
					if (i == radioButton.length - 1) {
						text.setVisibility(View.VISIBLE);
						text.setOnClickListener(oncl);
					} else {
						text.setVisibility(View.GONE);
					}
				}
			}
		}
	};

	class PagerAdapter extends android.support.v4.view.PagerAdapter {

		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(container.findViewById(1000 + position));

		}

		// 获得当前界面数

		public int getCount() {
			return pics.length;
		}

		public Object instantiateItem(ViewGroup container, int position) {
			View view = new View(getApplicationContext());
			view.setBackgroundResource(pics[position]);
			container.addView(view, -1, -1);
			view.setId(1000 + position);
			return view;
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}

	}

	MypageChangeListener pageChang = new MypageChangeListener();

	private class MypageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int position) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int arg0) {
			radioButton[arg0].setChecked(true);
		}

	}
}
