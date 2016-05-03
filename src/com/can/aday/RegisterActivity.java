package com.can.aday;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.can.aday.tools.HttpGet;
import com.can.aday.tools.HttpPost;
import com.can.aday.tools.HttpPost.OnSendListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

	@Override
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

		@Override
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
		final String acc = account.getText().toString();
		final String pass = password.getText().toString();
		String passTwo = passwordTwo.getText().toString();
		if (!TextUtils.isEmpty(acc)) {
			if (!TextUtils.isEmpty(pass)) {
				if (pass.equals(passTwo)) {
					/*
					 * HttpPost httpPost =
					 * HttpPost.parseUrl(AdayApplication.SERVICE_IP +
					 * "api/register"); httpPost.putString("username", acc);
					 * httpPost.putString("password", pass);
					 * httpPost.putString("repassword", passTwo);
					 * httpPost.setOnSendListener(new OnSendListener() {
					 */
					HttpGet httpGet = HttpGet.parseUrl(AdayApplication.SERVICE_IP + "register");
					httpGet.putString("username", acc);
					httpGet.putString("password", pass);

					httpGet.setOnSendListener(new OnSendListener() {
						@Override
						public void start() {

						}

						@Override
						public void end(String result) {
							Log.i("post", result);
							JSONObject jo = null;
							try {
								jo = new JSONObject(result);
								if (jo.getString("objectId") != null || jo.getString("objectId") != "") {
									Intent intent = getIntent();
									intent.setClass(RegisterActivity.this, LoginAndRegisteredActivity.class);
									intent.putExtra("acc", acc);
									intent.putExtra("pass", pass);
									startActivity(intent);
									finish();
								} else {
									Toast.makeText(getApplicationContext(), jo.getInt("code") + "", Toast.LENGTH_SHORT)
											.show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
									Toast.makeText(getApplicationContext(), "账号已存在", Toast.LENGTH_SHORT)
											.show();
							}
						}
					});
					// httpPost.send();
					httpGet.send();

				} else {
					Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
		}
	}
}
