package com.can.aday.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.can.aday.tools.HttpPost.OnSendListener;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

public class HttpGet {
	String mUrl;
	private final StringBuilder mBuilder = new StringBuilder();

	/**
	 * 通过 url 直接获得HttpGet对象
	 * 
	 * @param url
	 */
	public HttpGet(String url) {
		mUrl = url;

	};

	/**
	 * 通过 解析url地址获得一个HttpPost对象;
	 * 
	 * @param url
	 * @return HttpGet
	 * 
	 */
	public static HttpGet parseUrl(String url) {
		return new HttpGet(url);
	}

	/**
	 * GET 方式传输的一组数据, key值不能为空，为空put无效！
	 * </p>
	 * 例：
	 * </p>
	 * post.putString("name","jack");
	 * 
	 * @param k
	 * @param v
	 */
	public void putString(String k, String v) {
		if (TextUtils.isEmpty(k))
			return;
		if (mBuilder.length() != 0) {
			mBuilder.append("&");
		} else {
			mBuilder.append("?");
		}
		mBuilder.append(k + "=" + v);
	}

	/**
	 * GET 方式传输的多组数据,key值不能为空，为空的那条传输无效！
	 * </p>
	 * Map<String, String> map=new HashMap();
	 * </p>
	 * map.put("name","jack");
	 * </p>
	 * map.put("password","123456");
	 * </p>
	 * get.putMap(map);
	 * 
	 * @param map
	 */
	public void putMap(Map<String, String> map) {
		if (map != null) {
			for (java.util.Map.Entry<String, String> set : map.entrySet()) {
				putString(set.getKey(), set.getValue());
			}
		}
	}

	/**
	 * 开始执行发送，须在子线程操作！
	 * 
	 * @return -1》》io异常！-2》》请求异常
	 */
	public String sendInBack() {
		HttpURLConnection conn;
		try {
			URL url = new URL(getUrl());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			StringBuilder result = new StringBuilder();
			InputStream in;
			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = reader.readLine();
				while ((line != null && !line.isEmpty())) {
					result.append(line);
					line = reader.readLine();
				}
				reader.close();
				in.close();
				return result.toString();
			} else {
				Log.i("conn code", conn.getResponseCode() + "");
				return "-2";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "-1";
		}
	}

	/**
	 * 开始执行post请求,注意：不需开支线程请求！
	 * </p>
	 * 在发送之前可设置OnSendListener监听，在监听的end(String result)方法里实现ui的更新。
	 * 
	 */
	public void send() {
		if (mListener != null)
			mListener.start();
		new MyAyckTast().execute();

	}

	public String getUrl() {
		return mUrl + mBuilder.toString();
	}

	private OnSendListener mListener;

	/**
	 * 设置请求监听
	 * </p>
	 * 重写start() end()两个方法 实现ui更新
	 * 
	 * @param OnSendListener
	 */
	public void setOnSendListener(OnSendListener mListener) {
		this.mListener = mListener;
	}

	private class MyAyckTast extends AsyncTask<Void, Integer, String> {

		@Override
		protected String doInBackground(Void... params) {

			return sendInBack();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mListener != null)
				mListener.end(result);
		}
	}
}
