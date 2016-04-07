package com.can.aday.fragment;

import com.can.aday.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class VideoFragment extends Fragment {
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
}
