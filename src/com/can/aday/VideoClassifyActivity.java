package com.can.aday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.can.aday.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 视频分类的页面
 * 
 * @author Administrator
 *
 */
public class VideoClassifyActivity extends Activity {
	/**
	 * 该页面的名字
	 */
	TextView titleName;
	/**
	 * 退出该页面的按钮
	 */
	ImageView backImage;
	/**
	 * GridView控件
	 */
	GridView videoGV;
	/**
	 * GridView的Adapter
	 */
	SimpleAdapter spAdapter;
	/**
	 * 装GridView所需的数据
	 */
	List<Map<String, Object>> videoList;
	/**
	 * 自定义字符数组
	 */
	String videoName[] = { "#创意", "#旅行", "#剧情", "#美食", "#动漫", "#时尚", "#运动", "#动物" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_classify_main);
		findView();
	}

	/**
	 * 控件ID
	 */
	private void findView() {
		titleName=(TextView)findViewById(R.id.title_bar_title_text);
		backImage=(ImageView)findViewById(R.id.title_bar_back_icon);
		videoGV = (GridView) findViewById(R.id.video_classify_GridView);

		videoGV.setSelector(new ColorDrawable(Color.TRANSPARENT));

		videoList = new ArrayList<Map<String, Object>>();
		getData();

		String from[] = { "image", "text" };
		int to[] = { R.id.video_classify_image, R.id.video_classify_text };
		spAdapter = new SimpleAdapter(this, videoList, R.layout.video_classify_layout, from, to);
		videoGV.setAdapter(spAdapter);
		videoGV.setOnItemClickListener(clickListener);
		
		backImage.setOnClickListener(listener);
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		for (int i = 0; i < videoName.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", R.drawable.aday_originality_classify);
			map.put("text", videoName[i]);
			videoList.add(map);
		}
	}

	/**
	 * GridView的点击事件
	 */
	OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(VideoClassifyActivity.this, VideoClassifyDetailsActivity.class);
			startActivity(intent);
		}
	};
	/**
	 * 点击事件
	 */
	OnClickListener listener=new OnClickListener() {
		
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
