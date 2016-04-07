package com.can.aday;

import com.can.aday.fragment.AdayFragment;
import com.can.aday.fragment.BookFragment;
import com.can.aday.fragment.MusicFragment;
import com.can.aday.fragment.VideoFragment;
import com.can.aday.tools.DensityUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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

	AdayFragment[] pagers = new AdayFragment[3];
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
			FragmentTransaction transaction = manager.beginTransaction();
			initBottomView(transaction);
		}
	};

	private SlidingMenu mMenu;
	View set;// 设置
	View back;// 返回
	ImageView head;// 头像
	TextView nickName;// 昵称
	TextView collection;// 收藏
	TextView comments;// 评论
	TextView contribute;// 我要投稿
	TextView more;// 更多应用
	TextView agreement;// 用户协议
	TextView feedback;// 意见反馈
	TextView report;// 版权举报
	TextView video;// 视频功能声明
	TextView score;// 前往评分

	View menu;
	View menu_set;

	@SuppressLint("WrongCall")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_menu);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		manager = getSupportFragmentManager();
		findView();
		initView();
		findMenuView();
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
	private void initBottomView(FragmentTransaction transaction) {

		for (int i = 0; i < bottomBtns.length; i++) {
			transaction.hide(pagers[i]);
			if (bottomBtns[i].isChecked()) {
				bottomBtns[i].setTextColor(getResources().getColor(R.color.bottom_text_selet_color));
				if (i == 2) {
					titleRight.setOnClickListener(click);
					titleRightLayout.setVisibility(View.VISIBLE);
				} else {
					titleRightLayout.setVisibility(View.GONE);
				}
				pagers[i].onShow();
				transaction.show(pagers[i]);
			} else {
				pagers[i].onDismiss();
				bottomBtns[i].setTextColor(getResources().getColor(R.color.bottom_text_not_selet_color));

			}
		}
		transaction.commit();
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
		initBottomView(transaction);

	}

	private void findMenuView() {
		set = findViewById(R.id.menu_image_set_icon);
		back = findViewById(R.id.menu_title_bar_back_icon);
		head = (ImageView) findViewById(R.id.login_register_image_head);
		nickName = (TextView) findViewById(R.id.menu_text_nick_name);
		collection = (TextView) findViewById(R.id.menu_text_my_collection);
		comments = (TextView) findViewById(R.id.menu_text_my_comments);
		contribute = (TextView) findViewById(R.id.menu_text_contribute);
		more = (TextView) findViewById(R.id.menu_text_more_application);
		agreement = (TextView) findViewById(R.id.menu_text_user_apreement);
		feedback = (TextView) findViewById(R.id.menu_text_feedback);
		report = (TextView) findViewById(R.id.menu_text_copyright);
		video = (TextView) findViewById(R.id.menu_text_video_function);
		score = (TextView) findViewById(R.id.menu_text_to_score);
		menu = findViewById(R.id.menu);
		menu_set = findViewById(R.id.menu_set);

		back.setOnClickListener(clickListener);
		set.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu_title_bar_back_icon:
				menu.setVisibility(View.VISIBLE);
				menu_set.setVisibility(View.GONE);
				break;
			case R.id.menu_image_set_icon:
				menu_set.setVisibility(View.VISIBLE);
				menu.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}
	};

	public void toggleMenu(View view) {
		mMenu.toggle();
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
