package com.can.aday;

import java.util.ArrayList;

import com.can.aday.adapter.CommentBaseAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 评论页面
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
public class CommentActivity extends Activity {
	ListView listView;
	TextView titleText;//标题
	View view;//添加listView的头饰图时的一个转换
	
	Gallery gallery;
	
	ArrayList<String> list;
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
		listView=(ListView)findViewById(R.id.comment_listview);
		view=getLayoutInflater().inflate(R.layout.comment_head_layout,null);
		gallery=(Gallery)view.findViewById(R.id.comment_head_gallery);
		
		listView.addHeaderView(view);
		
		titleText.setText("评论");
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
	
}
