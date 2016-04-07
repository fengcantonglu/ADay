package com.can.aday;

import com.can.aday.fragment.VideoFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {
	/**
	 * 标题栏菜单按钮
	 */
	View titleMenu;

	/**
	 * 底部radioGroup
	 */
	RadioGroup bottomGroup;
	/**
	 * 底部RadioButton数组
	 */
	RadioButton[] bottomBtns = new RadioButton[3];

	/**
	 * fragment管理器
	 */
	FragmentManager manager;

	/**
	 * 视频页面
	 */
	VideoFragment mVieoPager;

	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_left:// 标题菜单按钮

				break;

			default:
				break;
			}

		}
	};

	private OnCheckedChangeListener btnChange = new OnCheckedChangeListener() {

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			initBottomView();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		initBottomView();
		initView();
		initFragment();
	}

	private void findView() {
		titleMenu = findViewById(R.id.title_left);
		findViewById(R.id.title_right_layout).setVisibility(View.GONE);
		bottomGroup = (RadioGroup) findViewById(R.id.main_bottom_group);
		bottomBtns[0] = (RadioButton) findViewById(R.id.book_btn);
		bottomBtns[1] = (RadioButton) findViewById(R.id.music_btn);
		bottomBtns[2] = (RadioButton) findViewById(R.id.video_btn);
	}

	/**
	 * 初始化底部栏
	 */
	@SuppressWarnings("deprecation")
	private void initBottomView() {
		for (int i = 0; i < bottomBtns.length; i++) {
			if (bottomBtns[i].isChecked()) {
				bottomBtns[i].setTextColor(getResources().getColor(R.color.bottom_text_selet_color));
			} else {
				bottomBtns[i].setTextColor(getResources().getColor(R.color.bottom_text_not_selet_color));
			}
		}
	}

	/**
	 * 初始化view,点击事件绑定在此
	 */
	private void initView() {
		titleMenu.setOnClickListener(click);
		bottomGroup.setOnCheckedChangeListener(btnChange);
	}

	/**
	 * 获取fragment管理器,和初始化并添加碎片
	 */
	private void initFragment() {
		manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		mVieoPager = new VideoFragment();
		transaction.add(R.id.main_layout, mVieoPager);
		transaction.commit();
	}
}
