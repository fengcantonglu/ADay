package com.can.aday.fragment;

import com.can.aday.R;
import com.can.aday.tools.DensityUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class VideoFragment extends AdayFragment {

	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_right_btn:// 左边标题栏点击效果
				showImageBox();
				break;

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
		View view = inflater.inflate(R.layout.video_fragment_main, lay);
		return view;
	}

	/**
	 * 点击分类
	 */
	public void seleteType() {

	}

	/**
	 * 点击排行
	 */
	public void seletePaihang() {

	}

	public void onShow() {
		titleRight.setOnClickListener(click);
		titleRightLayout.setVisibility(View.VISIBLE);
	}

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

			}
		};
		pop.setOnDismissListener(onDismissListener);
		pop.showAsDropDown(titleRightLayout);
	}
}
