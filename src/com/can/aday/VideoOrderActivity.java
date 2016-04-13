package com.can.aday;

import java.util.ArrayList;

import com.can.aday.adapter.VideoOrderBaseAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 视频排行
 * 
 * @author 冯灿
 *
 */
public class VideoOrderActivity extends Activity {
	/**
	 * 该页面添加视频的listView
	 */
	ListView listview;
	/**
	 * 该页面的标题
	 */
	TextView titleName;
	/**
	 * 一个返回按钮
	 */
	ImageView backimage;
	/**
	 * 为了点击两次backimage设置的
	 */
	boolean backnumber=true;
	/**
	 * RadioGroup的控件
	 */
	RadioGroup radioGroup;
	/**
	 * 评论的图标
	 */
	ImageView commentIcon;
	/**
	 * 该页面添加include
	 */
	View vInclude;
	/**
	 * listview的Adapter
	 */
	VideoOrderBaseAdapter vOBaseAdapter;
	/**
	 * 用来装listView所需的数据
	 */
	ArrayList<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_order_main);
		findView();

		getData();
		vOBaseAdapter = new VideoOrderBaseAdapter(list, this);
		listview.setAdapter(vOBaseAdapter);
	}

	/**
	 * listview的点击事件
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			listview.setVisibility(View.GONE);
			vInclude.setVisibility(View.VISIBLE);
			backnumber=false;
		}
	};

	/**
	 * 添加数据
	 */
	private void getData() {
		list = new ArrayList<String>();
		for (int i = 0; i < 6; i++) {
			list.add("");
		}
	}

	/**
	 * 控件ID
	 */
	private void findView() {

		listview = (ListView) findViewById(R.id.viseo_order_listview);
		radioGroup = (RadioGroup) findViewById(R.id.viseo_order_radiogroup);
		backimage = (ImageView) findViewById(R.id.title_bar_back_icon);
		titleName = (TextView) findViewById(R.id.title_bar_title_text);
		vInclude = findViewById(R.id.viseo_order_include);
		commentIcon=(ImageView)findViewById(R.id.video_order_details_comment);

		titleName.setText("排行");

		backimage.setOnClickListener(clickListener);
		commentIcon.setOnClickListener(clickListener);

		listview.setOnItemClickListener(itemClickListener);
		
		radioGroup.setOnCheckedChangeListener(listener);

	}
	/**
	 * RadioGroup的点击事件
	 */
	OnCheckedChangeListener listener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.viseo_order_week:
				vInclude.setVisibility(View.GONE);
				listview.setVisibility(View.VISIBLE); 
				backnumber=true;
				break;
			case R.id.viseo_order_month:
				vInclude.setVisibility(View.GONE);
				listview.setVisibility(View.VISIBLE); 
				backnumber=true;
				break;
			case R.id.viseo_order_total:
				vInclude.setVisibility(View.GONE);
				listview.setVisibility(View.VISIBLE); 
				backnumber=true;
				break;
			default:

				break;
			}
			
		}
	};
	/**
	 * 简单的点击事件
	 */
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_bar_back_icon:
				if(backnumber){
					finish();
				}else{
					vInclude.setVisibility(View.GONE);
					listview.setVisibility(View.VISIBLE); 
					backnumber=true;
				}
				break;
			case R.id.video_order_details_comment:
				Intent intent=new Intent(VideoOrderActivity.this,CommentActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
}
