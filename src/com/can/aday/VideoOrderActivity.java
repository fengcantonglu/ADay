package com.can.aday;

import java.util.ArrayList;

import com.can.aday.adapter.VideoOrderBaseAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
/**
 * 视频排行
 * @author 冯灿
 *
 */
public class VideoOrderActivity extends Activity {
	ListView listview;
	TextView titleName;//标题
	ImageView backimage;//返回
	RadioGroup radioGroup;
	
	VideoOrderBaseAdapter vOBaseAdapter;
	ArrayList<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_order_main);
		findView();
		
		getData();
		vOBaseAdapter=new VideoOrderBaseAdapter(list,this);
		listview.setAdapter(vOBaseAdapter);
	}
	/**
	 * 添加数据
	 */
	private void getData() {
		list=new ArrayList<String>();
		for(int i=0;i<6;i++){
			list.add("");
		}
	}
	/**
	 * 控件ID
	 */
	private void findView() {
		listview=(ListView)findViewById(R.id.viseo_order_listview);
		radioGroup=(RadioGroup)findViewById(R.id.viseo_order_radiogroup);
		backimage=(ImageView)findViewById(R.id.title_bar_back_icon);
		titleName=(TextView)findViewById(R.id.title_bar_title_text);
		
		titleName.setText("排行");
		
		backimage.setOnClickListener(clickListener);
		
	}
	/**
	 * 简单的点击事件
	 */
	OnClickListener clickListener=new OnClickListener() {
		
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
