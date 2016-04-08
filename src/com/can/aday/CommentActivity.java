package com.can.aday;

import java.util.ArrayList;

import com.can.aday.adapter.CommentBaseAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 评论页面
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
public class CommentActivity extends Activity {
	/**
	 * 添加评论的listview
	 */
	ListView listView;
	/**
	 * 该页面的标题
	 */
	TextView titleText;
	/**
	 * 退出该页面的按钮
	 */
	ImageView backImage;
	/**
	 * 添加listview的头饰图时的一个转换
	 */
	View view;
	/**
	 * listview的头饰图里面添加的一个画廊控件
	 */
	Gallery gallery;
	/**
	 * 用来装listview的数据
	 */
	ArrayList<String> list;
	/**
	 * listview的Adapter
	 */
	CommentBaseAdapter commentAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_main);
		findView();
		getData();
		
		commentAdapter=new CommentBaseAdapter(list,this);
		gallery.setAdapter(commentAdapter);
		listView.setAdapter(commentAdapter);
	}
	/**
	 * 控件ID
	 */
	@SuppressLint("InflateParams")
	private void findView() {
		titleText=(TextView)findViewById(R.id.title_bar_title_text);
		backImage=(ImageView)findViewById(R.id.title_bar_back_icon);
		listView=(ListView)findViewById(R.id.comment_listview);
		view=getLayoutInflater().inflate(R.layout.comment_head_layout,null);
		gallery=(Gallery)view.findViewById(R.id.comment_head_gallery);
		
		listView.addHeaderView(view);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		titleText.setText("评论");
		backImage.setOnClickListener(clickListener);
	}
    /**
     * 添加数据
     */
	private void getData(){
		list=new ArrayList<String>();
		String[] str={"1","2","3","4","5","6"};
		for(int i=0;i<6;i++){
			list.add(str[i]);
		}
	}
	/**
	 * 点击事件
	 */
	OnClickListener clickListener=new OnClickListener() {
		
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
