package com.can.aday;

import java.util.ArrayList;

import com.can.aday.adapter.VideoClassifyDetailsBaseAdapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 分类子页面
 * @author Administrator
 *
 */
public class VideoClassifyDetailsActivity extends Activity {
	/**
	 * 该页面的名字
	 */
	TextView titleName;
	/**
	 * 该页面的返回按钮
	 */
	ImageView backImage;
	/**
	 * 该页面添加视频的listview
	 */
	ListView listview;
	/**
	 * listview的Adapter
	 */
	VideoClassifyDetailsBaseAdapter vCDBaseAdapter;
	/**
	 * 用来装listView所需的数据
	 */
	ArrayList<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_classify_details_main);
		findView();
		getData();
		vCDBaseAdapter = new VideoClassifyDetailsBaseAdapter(list, this);
		listview.setAdapter(vCDBaseAdapter);
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		String str[] = { "#冲浪", "#冲浪", "#冲浪", "#冲浪", "#冲浪", "#冲浪" };
		list = new ArrayList<String>();
		for (int i = 0; i < 6; i++) {
			list.add(str[i]);
		}
	}

	/**
	 * 控件ID
	 */
	private void findView() {
		titleName = (TextView) findViewById(R.id.title_bar_title_text);
		listview = (ListView) findViewById(R.id.video_classify_details_listview);
		backImage = (ImageView) findViewById(R.id.title_bar_back_icon);

		titleName.setText("运动");
		listview.setSelector(new ColorDrawable(Color.TRANSPARENT));

		backImage.setOnClickListener(clickListener);
	}

	/**
	 * 简单的点击事件
	 */
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_bar_back_icon:
				finish();
				break;
			default:
				break;
			}
		}
	};
}
