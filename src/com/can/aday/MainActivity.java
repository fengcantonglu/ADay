package com.can.aday;

import com.can.aday.fragment.BookFragment;
import com.can.aday.fragment.MusicFragment;
import com.can.aday.fragment.VideoFragment;
import com.can.aday.tools.DensityUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	/**
	 * 标题栏菜单按钮
	 */
	View titleMenu;

	/**
	 * 标题栏左
	 */
	ImageView titleRight;
	/**
	 * 标题栏左预留布局
	 */
	LinearLayout titleRightLayout;
	TextView classify;// 分类
	TextView number;// 排第几
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

	Fragment[] pagers = new Fragment[3];
	/**
	 * 视频页面
	 */
	VideoFragment mVieoPager;

	BookFragment mBookPager;
	/**
	 * 音频模块
	 */
	private MusicFragment mMusicPager;

	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_left:// 标题菜单按钮

				break;
			case R.id.title_right_btn:
				showImageBox();
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
		manager = getSupportFragmentManager();
		findView();
		initBottomView();
		initView();
		initFragment();
	}

	/**
	 * 控件ID
	 */
	private void findView() {
		titleMenu = findViewById(R.id.title_left);
		titleRight = (ImageView) findViewById(R.id.title_right_btn);
		titleRightLayout = (LinearLayout) findViewById(R.id.title_right_layout);
		findViewById(R.id.title_right_layout).setVisibility(View.GONE);
		bottomGroup = (RadioGroup) findViewById(R.id.main_bottom_group);
		bottomBtns[0] = (RadioButton) findViewById(R.id.book_btn);
		bottomBtns[1] = (RadioButton) findViewById(R.id.music_btn);
		bottomBtns[2] = (RadioButton) findViewById(R.id.video_btn);
	}

	/**
	 * 初始化底部栏
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void initBottomView() {
		// FragmentTransaction transaction = manager.beginTransaction();
		for (int i = 0; i < bottomBtns.length; i++) {
			if (bottomBtns[i].isChecked()) {
				bottomBtns[i].setTextColor(getResources().getColor(R.color.bottom_text_selet_color));
				if (i == 2) {
					titleRight.setOnClickListener(click);
					titleRightLayout.setVisibility(View.VISIBLE);
				} else {
					titleRightLayout.setVisibility(View.GONE);
				}
				// transaction.show(pagers[i]);
			} else {
				bottomBtns[i].setTextColor(getResources().getColor(R.color.bottom_text_not_selet_color));
				// transaction.hide(pagers[i]);
			}
			// transaction.commit();
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

		FragmentTransaction transaction = manager.beginTransaction();
		mVieoPager = new VideoFragment();
		mBookPager = new BookFragment();
		mMusicPager = new MusicFragment(findViewById(R.id.include1));
		pagers[0] = mBookPager;
		pagers[1] = mMusicPager;
		pagers[2] = mVieoPager;
		transaction.add(R.id.main_layout, pagers[0]);
		transaction.add(R.id.main_layout, pagers[1]);
		transaction.add(R.id.main_layout, pagers[2]);
		transaction.hide(mVieoPager);
		transaction.hide(mBookPager);
		transaction.commit();
	}

	PopupWindow pop;

	/**
	 * 视频下拉window的切换效果
	 */
	private void showImageBox() {
		if (pop == null || !pop.isShowing()) {
			titleRight.setImageResource(R.drawable.pack_up_icon);
			titleRight.setBackgroundColor(Color.parseColor("#10000000"));
			showPopwindow();
		} else {
			titleRight.setImageResource(R.drawable.pack_down_icon);
			titleRight.setBackgroundColor(0);
			pop = null;
		}
	}

	/**
	 * 视频页面下拉popwindow的显示
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void showPopwindow() {
		View pupView = getLayoutInflater().inflate(R.layout.video_fragment_layout, null);
		OnClickListener l = new OnClickListener() {

			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.video_fragment_classify:
					mVieoPager.seleteType();
					break;
				default:
					mVieoPager.seletePaihang();
					break;
				}
			}
		};
		pupView.findViewById(R.id.video_fragment_classify).setOnClickListener(l);
		pupView.findViewById(R.id.video_fragment_number).setOnClickListener(l);
		pop = new PopupWindow(pupView, DensityUtil.dip2px(this, 52), -2);
		pop.setOutsideTouchable(true);
		pop.setTouchable(true);
		pop.setFocusable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		OnDismissListener onDismissListener = new OnDismissListener() {

			@Override
			public void onDismiss() {
				titleRight.setImageResource(R.drawable.pack_down_icon);

			}
		};
		pop.setOnDismissListener(onDismissListener);
		pop.showAsDropDown(titleRightLayout);
	}
}
