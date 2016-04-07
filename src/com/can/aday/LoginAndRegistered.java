package com.can.aday;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginAndRegistered extends Activity {
	View titleBar;// 标题
	ImageView toReturn;
	TextView title;
	ImageView head;// 头像
	EditText account;// 帐号
	EditText password;// 密码
	View login;// 登录按钮
	View register;// 注册按钮
	View weiXin;// 微信登录
	View weiBo;// 微博登录
	View qq;// qq登录
	TextView clause;// 服务条款

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aday_login_register);
		findView();

	}

	private void findView() {
		titleBar = findViewById(R.id.login_register_title_bar);
		toReturn = (ImageView) findViewById(R.id.title_bar_back_icon);
		title = (TextView) findViewById(R.id.title_bar_title_text);
		head = (ImageView) findViewById(R.id.login_register_image_head);
		account = (EditText) findViewById(R.id.login_register_edit_account);
		password = (EditText) findViewById(R.id.login_register_edit_password);
		login = findViewById(R.id.login_register_relation_login);
		register = findViewById(R.id.login_register_relation_register);
		weiXin = findViewById(R.id.login_register_image_wei_xin);
		weiBo = findViewById(R.id.login_register_image_wei_bo);
		qq = findViewById(R.id.login_register_image_qq);
		clause = (TextView) findViewById(R.id.login_register_text_clause);

		title.setText(R.string.login);
		title.setTextColor(Color.WHITE);
		titleBar.setBackgroundColor(Color.parseColor("#40000000"));
		toReturn.setImageResource(R.drawable.backtrack_icon);
	}
}
