package com.can.aday;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	View titleBar;// 标题
	ImageView toReturn;
	TextView title;
	ImageView head;// 头像
	EditText account;// 帐号
	EditText password;// 密码
	EditText passwordTwo;// 密码
	TextView clause;// 服务条款
	View confirm;// 登录按钮

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aday_register);
		findView();
	}

	private void findView() {
		titleBar = findViewById(R.id.register_title_bar);
		toReturn = (ImageView) findViewById(R.id.title_bar_back_icon);
		title = (TextView) findViewById(R.id.title_bar_title_text);
		head = (ImageView) findViewById(R.id.register_image_head);
		account = (EditText) findViewById(R.id.register_edit_account);
		password = (EditText) findViewById(R.id.register_edit_password);
		passwordTwo = (EditText) findViewById(R.id.register_edit_password2);
		clause = (TextView) findViewById(R.id.register_text_clause);
		confirm = findViewById(R.id.register_relation_confirm);

		title.setText(R.string.register);
		title.setTextColor(Color.WHITE);
		titleBar.setBackgroundColor(Color.parseColor("#40000000"));
		toReturn.setImageResource(R.drawable.backtrack_icon);

		toReturn.setOnClickListener(clickListener);
		confirm.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_bar_back_icon:
				finish();
				break;
			case R.id.register_relation_confirm:
				singIN();

				break;
			default:
				break;
			}
		}
	};

	private void singIN() {
		String acc = account.getText().toString();
		String pass = password.getText().toString();
		String passTwo = passwordTwo.getText().toString();
		if (acc.equals(null) && acc != "") {
			if (pass.equals(null) && pass != "") {
				if (passTwo.equals(null) && pass.equals(passTwo) && passTwo != "") {
					Intent intent = getIntent();
					intent.setClass(RegisterActivity.this, MainActivity.class);
					startActivity(intent);

					finish();
				} else {
					Toast.makeText(getApplicationContext(), "密码为空或两次密码输入不一致", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 缓存登录信息
	 */
	public void cachedPageGuide(String account, String password) {
		SharedPreferences sharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("account", account);
		editor.putString("password", password);
		editor.commit();
	}
}
