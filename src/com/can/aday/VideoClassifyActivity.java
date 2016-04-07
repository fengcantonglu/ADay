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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
/**
 * 视频分类的页面
 * @author Administrator
 *
 */
public class VideoClassifyActivity extends Activity {	
	GridView videoGV;
	SimpleAdapter spAdapter;
	List<Map<String,Object>> videoList;
	String videoName[]={"#创意","#旅行","#剧情","#美食","#动漫","#时尚","#运动","#动物"};
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
		videoGV=(GridView)findViewById(R.id.video_classify_GridView);
		
		videoGV.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		videoList=new ArrayList<Map<String,Object>>();
		getData();
		
		String from[]={"image","text"};
		int to[]={R.id.video_classify_image,R.id.video_classify_text};
		spAdapter=new SimpleAdapter(this, videoList, R.layout.video_classify_layout, from, to);
		videoGV.setAdapter(spAdapter);
		videoGV.setOnItemClickListener(clickListener);
	}
	/**
	 * 获取数据
	 */
	private void getData() {
		for(int i=0;i<videoName.length;i++){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("image", R.drawable.aday_originality_classify);
			map.put("text", videoName[i]);
			videoList.add(map);
		}
	}
	/**
	 * GridView的点击事件
	 */
    OnItemClickListener clickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent=new Intent(VideoClassifyActivity.this,VideoClassifyDetailsActivity.class);
			startActivity(intent);
		}
	};
}
