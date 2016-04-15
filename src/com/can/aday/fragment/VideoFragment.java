package com.can.aday.fragment;

import com.can.aday.R;
import com.can.aday.VideoClassifyActivity;
import com.can.aday.VideoOrderActivity;
import com.can.aday.VideoPlayActivity;
import com.can.aday.tools.DensityUtil;
import com.can.aday.utils.FastBlur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class VideoFragment extends AdayFragment {
	View mView;
	/**
	 * 正文布局
	 */
	View topLayout;
	/**
	 * 介绍布局
	 */
	View bottomLayout;

	/**
	 * 上映月份
	 */
	TextView monthText;

	/**
	 * 上映日期
	 */
	TextView dayText;
	/**
	 * 播放按钮
	 */
	View playBtn;
	/**
	 * 影片名
	 */
	TextView nameText;

	/**
	 * 影片类型与片长（格式: #奇幻冒险/05' 34"）
	 */
	TextView timeText;
	/**
	 * 故事介绍正文(格式:\t\t故事内容)
	 */
	TextView storyText;
	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_right_btn:// 左边标题栏点击效果
				showImageBox();
				break;
			case R.id.video_play:
				Intent intent=new Intent(getActivity(),VideoPlayActivity.class);
				startActivity(intent);
			default:
				break;
			}

		}
	};

	public VideoFragment(View titleLayout) {
		titleRightLayout = titleLayout.findViewById(R.id.title_right_layout);
		titleRight = (ImageView) titleLayout.findViewById(R.id.title_right_btn);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout lay = new LinearLayout(getActivity());
		lay.setLayoutParams(new LayoutParams(-1, -1));
		mView = inflater.inflate(R.layout.video_fragment_main, lay);
		findView();
		initView();
		loadData();
		return mView;
	}

	private void findView() {
		topLayout = mView.findViewById(R.id.video_top_layout);
		bottomLayout = mView.findViewById(R.id.video_bottom_layout);
		monthText = (TextView) mView.findViewById(R.id.video_month);
		dayText = (TextView) mView.findViewById(R.id.video_day);
		playBtn = mView.findViewById(R.id.video_play);
		nameText = (TextView) mView.findViewById(R.id.video_name);
		timeText = (TextView) mView.findViewById(R.id.video_time);
		storyText = (TextView) mView.findViewById(R.id.video_context);
		
		playBtn.setOnClickListener(click);
	}

	private void initView() {

	}

	Bitmap topBkg;
	Bitmap btmBkg;

	private void loadData() {
		Bitmap bkg = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_image_4);
		setBackgroud(bkg);
		String mouth = "Mar";
		int day = 6;
		String name = "星银岛\t\t预告";
		String type = "奇幻冒险";
		String time = "05' 34\"";
		String content = "我们通过红色边框的四个角来控制裁剪的大小,移动红色框体来控制裁剪的位置区域。 接下来我们看看源码的实现: 首先点击剪切按钮的时候，我们应该生成一个Bitmap对象，传递给另一个Activty处理  具体做法如下：  ";
		setData(mouth, day, name, type, time, content);
	}

	/**
	 * 设置背景
	 * 
	 * @param bkg
	 */
	@SuppressWarnings("deprecation")
	public void setBackgroud(Bitmap bkg) {
		int w = bkg.getWidth();
		int mBh = (int) (bkg.getHeight() * 2.8f / 3.8f);
		int bBh = bkg.getHeight() - mBh;
		topBkg = Bitmap.createBitmap(bkg, 0, 0, w, mBh);
		Bitmap bBkg = Bitmap.createBitmap(bkg, 0, mBh, w, bBh);
		btmBkg = FastBlur.doBlur(bBkg, 24, false);

		bBkg.recycle();
		bkg.recycle();
		topLayout.setBackgroundDrawable(new BitmapDrawable(topBkg));
		bottomLayout.setBackgroundDrawable(new BitmapDrawable(btmBkg));
	}

	/**
	 * 
	 * @param mouth
	 *            上映月份
	 * @param day
	 *            上映日期
	 * @param name
	 *            影片名
	 * @param type
	 *            影片类型
	 * @param time
	 *            时长
	 * @param content
	 *            故事介绍
	 */
	public void setData(String mouth, int day, String name, String type, String time, String content) {
		monthText.setText(mouth);
		dayText.setText(day + "");
		nameText.setText(name);
		timeText.setText("#" + type + "/" + time);
		storyText.setText("\t\t" + content);
	}

	/**
	 * 点击分类
	 */
	public void seleteType() {
		Intent intent = new Intent(getActivity(), VideoClassifyActivity.class);
		startActivity(intent);
	}

	/**
	 * 点击排行
	 */
	public void seletePaihang() {
		Intent intent = new Intent(getActivity(), VideoOrderActivity.class);
		startActivity(intent);
	}

	@Override
	public void onShow() {
		titleRight.setOnClickListener(click);
		titleRightLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDismiss() {
		titleRightLayout.setVisibility(View.GONE);
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	PopupWindow pop;
	private View titleRightLayout;
	private ImageView titleRight;

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
		View pupView = getActivity().getLayoutInflater().inflate(R.layout.video_fragment_layout, null);
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.video_fragment_classify:
					seleteType();
					break;
				default:
					seletePaihang();
					break;
				}
			}
		};
		pupView.findViewById(R.id.video_fragment_classify).setOnClickListener(l);
		pupView.findViewById(R.id.video_fragment_number).setOnClickListener(l);
		pop = new PopupWindow(pupView, DensityUtil.dip2px(getActivity(), 52), -2);
		pop.setOutsideTouchable(true);
		pop.setTouchable(true);
		pop.setFocusable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		OnDismissListener onDismissListener = new OnDismissListener() {

			@Override
			public void onDismiss() {
				titleRight.setImageResource(R.drawable.pack_down_icon);
				titleRight.setBackgroundColor(0);
			}
		};
		pop.setOnDismissListener(onDismissListener);
		pop.showAsDropDown(titleRightLayout);
	}
}
