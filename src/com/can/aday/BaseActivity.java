package com.can.aday;


import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentViewId());
		findViews();
		init();
	}

	protected void findViews() {
	};


	/**
	 * 获取显示view的xml文件ID
	 *
	 * @version 1.0
	 * @createTime 2015年2月4日,上午11:32:28
	 * @updateTime 2015年2月4日,上午11:32:28
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @return
	 */
	protected abstract int getContentViewId();

	/**
	 * 初始化
	 *
	 * @version 1.0
	 * @createTime 2015年2月4日,上午11:33:07
	 * @updateTime 2015年2月4日,上午11:33:07
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected abstract void init();

}
