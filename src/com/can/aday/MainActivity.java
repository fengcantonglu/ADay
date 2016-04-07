package com.can.aday;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

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
		findView();
	}

	private void findView() {
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

}
