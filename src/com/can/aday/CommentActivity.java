package com.can.aday;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 评论页面
 * @author Administrator
 *
 */
public class CommentActivity extends Activity {
	ListView listView;
	TextView titleText;//标题
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_main);
		findView();
	}
	/**
	 * 控件ID
	 */
	private void findView() {
		titleText=(TextView)findViewById(R.id.title_bar_title_text);
		listView=(ListView)findViewById(R.id.comment_listview);
		
		titleText.setText("评论");
	}

}
