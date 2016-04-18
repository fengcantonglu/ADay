package com.can.aday;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.can.aday.data.Music;
import com.can.aday.data.User;
import com.can.aday.tools.HttpPost;
import com.can.aday.tools.HttpPost.OnSendListener;
import com.can.aday.utils.CacheTools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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

public class LoginAndRegisteredActivity extends Activity {
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
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aday_login_register);
		intent = getIntent();
		findView();
		initView();

	}

	/**
	 * 查找控件id与设置初始值
	 */
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
		toReturn.setVisibility(View.GONE);

		register.setOnClickListener(clickListener);
		login.setOnClickListener(clickListener);

	}

	@SuppressLint("NewApi")
	private void initView() {

		String acc = intent.getStringExtra("acc");
		String pass = intent.getStringExtra("pass");
		if (acc == null || pass == null) {
			isShowWelcome();
		} else {
			account.setText(acc);
			password.setText(pass);
		}
		Log.i("Text", account.getText().toString());
	}

	/**
	 * 点击事件监听
	 */
	OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			intent = new Intent();
			switch (v.getId()) {
			case R.id.login_register_relation_register:
				intent.setClass(LoginAndRegisteredActivity.this, RegisterActivity.class);
				startActivity(intent);
				break;
			case R.id.login_register_relation_login:
				singUP();

				break;
			default:
				break;
			}
		}
	};

	/**
	 * 登陆验证账户密码是否为空
	 */
	private void singUP() {

		final String acc = account.getText().toString();
		final String pass = password.getText().toString();
		if (!TextUtils.isEmpty(acc) && !TextUtils.isEmpty(pass)) {
			try {
				HttpPost httpPost = HttpPost.parseUrl(AdayApplication.SERVICE_IP + "api/login");
				httpPost.putString("user", acc);
				httpPost.putString("password", pass);
				httpPost.setOnSendListener(new OnSendListener() {
					ProgressDialog dialog = new ProgressDialog(LoginAndRegisteredActivity.this);

					@Override
					public void start() {
						dialog.show();
					}

					@Override
					public void end(String result) {
						dialog.dismiss();
						Log.i("LoginandRegister", result);

						try {
							JSONObject jo = new JSONObject(result);

							if (jo.getInt("status") == 1) {
								AdayApplication app = (AdayApplication) getApplication();
								String token = jo.getString("token");
								User user = User.userJSONObject(jo.getJSONObject("user"));
								Music mc = Music.musicJSONObject(jo.getJSONObject("data1"));
								JSONObject jj = jo.getJSONObject("data2");
								String str = jj.getString("message");
								Music mc1 = Music.musicJSONObject(jj.getJSONObject("music"));
								intent.setClass(LoginAndRegisteredActivity.this, MainActivity.class);
								startActivity(intent);
								app.setCurrentUser(user);
								app.setToken(token);
								finish();
								CacheTools.cachedPageGuide(acc, pass, getApplicationContext());
							} else {
								Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_SHORT)
										.show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
				httpPost.send();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(), "账号或密码为空", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 加载账户缓存
	 */
	public void isShowWelcome() {
		String[] ap = new String[2];
		CacheTools.getAccountCache(this, ap);
		account.setText(ap[0]);
		password.setText(ap[1]);
	}

}
