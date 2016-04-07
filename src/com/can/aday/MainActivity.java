package com.can.aday;

import com.can.aday.fragment.VideoFragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
	 * 标题栏左的两次点击事件
	 */
	boolean titleRightUD=true;
	PopupWindow popupWindow;//自定义PopupWindow
	TextView classify;//分类
	TextView number;//排第几
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
			case R.id.title_right_btn:
				if(titleRightUD){
					popupwindow(v);
					titleRight.setImageResource(R.drawable.pack_up_icon);
					titleRightUD=false;
				}else{
					titleRight.setImageResource(R.drawable.pack_down_icon);
					titleRightUD=true;
					popupWindow.dismiss();
				}
				break;
			default:
				break;
			}

		}
	};
	
	/**
	 * 自定义PopupWindow
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void popupwindow(View view) {
		View v=LayoutInflater.from(this).inflate(R.layout.video_fragment_layout, null);
		classify=(TextView)v.findViewById(R.id.video_fragment_classify);
		number=(TextView)v.findViewById(R.id.video_fragment_number);
		popupWindow = new PopupWindow(v, 180, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setFocusable(false);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(view);
	}
	
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
    /**
     * 控件ID
     */
	private void findView() {
		titleMenu = findViewById(R.id.title_left);
		titleRight=(ImageView)findViewById(R.id.title_right_btn);
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
				if(i==2){
					findViewById(R.id.title_right_layout).setVisibility(View.VISIBLE);
					titleRight.setOnClickListener(click);
					titleRight.setImageResource(R.drawable.pack_down_icon);
					titleRightUD=true;
				}else{
					findViewById(R.id.title_right_layout).setVisibility(View.GONE);
				}
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
