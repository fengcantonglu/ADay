package com.can.aday;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 读者界面,包括音乐读物,和杂志之类的
 * 
 * @author kk0927
 *
 */
public class BookActivity extends Activity {
	/**
	 * 返回按钮
	 */
	ImageView toReturn;
	/**
	 * 收藏按钮
	 */
	View collection;
	/**
	 * 评论按钮
	 */
	View comment;
	/**
	 * 分享按钮
	 */
	View share;
	/**
	 * 音乐播放时间
	 */
	TextView currutTime;
	/**
	 * 音乐时长
	 */
	TextView musicTime;
	/**
	 * 进度条
	 */
	View progress;
	/**
	 * 音乐模块进度条布局(普通杂志文章隐藏)
	 */
	View progressLayout;

	/**
	 * 播放暂停按钮(普通杂志隐藏此)
	 */
	ImageView playOrPause;

	/**
	 * 日期图片
	 */
	ImageView dateImage;
	/**
	 * 文章或音乐类杂志标题
	 */
	TextView artTitle;

	/**
	 * 作者
	 */
	TextView artAthor;
	/**
	 * 作者简介
	 */
	TextView athorDetail;
	/**
	 * 正文布局
	 */
	LinearLayout contentLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);
		initTitleView();
		findView();

	}

	/**
	 * 初始化标题栏
	 */
	@SuppressLint("InflateParams")
	private void initTitleView() {
		toReturn = (ImageView) findViewById(R.id.title_left);
		View v = getLayoutInflater().inflate(R.layout.function_icon_layout, null);
		findViewById(R.id.title_right_btn).setVisibility(View.GONE);
		LinearLayout tl = (LinearLayout) findViewById(R.id.title_right_layout);
		tl.addView(v);
		toReturn.setImageResource(R.drawable.return_icon);
		collection = v.findViewById(R.id.function_collection);
		comment = v.findViewById(R.id.function_comment);
		share = v.findViewById(R.id.function_share);
	}

	private void findView() {
		currutTime = (TextView) findViewById(R.id.currut_time);
		musicTime = (TextView) findViewById(R.id.music_time_length);
		progress = findViewById(R.id.progress_image);
		progressLayout = findViewById(R.id.progress_layout);
		playOrPause = (ImageView) findViewById(R.id.play_or_pause);
		dateImage = (ImageView) findViewById(R.id.date_image);
		artTitle = (TextView) findViewById(R.id.arc_title);
		artAthor = (TextView) findViewById(R.id.article_athor);
		athorDetail = (TextView) findViewById(R.id.athor_details);
		contentLayout = (LinearLayout) findViewById(R.id.article_centent_layout);
	}
}
