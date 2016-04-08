package com.can.aday.fragment;

import android.support.v4.app.Fragment;

public class AdayFragment extends Fragment {

	/**
	 * 显示的时候调用
	 */
	public void onShow() {
	}

	/**
	 * 隐藏的时候调用
	 */
	public void onDismiss() {
	}

	/**
	 * 当Activity 执行finish时调用,返回true者归还给Activity页面执行,返回false则自己消化
	 * 
	 * @return
	 */
	public boolean finish() {
		return true;
	}
}
