package com.can.aday.data;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private int id;
	private String username;
	private String usertel;
	private int createtime;
	private int logintime;
	private int logincount;
	private String imgpath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertel() {
		return usertel;
	}

	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}

	public int getCreatetime() {
		return createtime;
	}

	public void setCreatetime(int createtime) {
		this.createtime = createtime;
	}

	public int getLogintime() {
		return logintime;
	}

	public void setLogintime(int logintime) {
		this.logintime = logintime;
	}

	public int getLogincount() {
		return logincount;
	}

	public void setLogincount(int logincount) {
		this.logincount = logincount;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public static User userJSONObject(JSONObject jo) {
		User us = new User();
		try {
			us.setId(jo.getInt("id"));
			us.setUsername(jo.getString("username"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		try {
			us.setUsertel(jo.getString("usertel"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			us.setCreatetime(jo.getInt("createtime"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			us.setLogintime(jo.getInt("logintime"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			us.setLogincount(jo.getInt("logincount"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			us.setImgpath(jo.getString("imgpath"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return us;
	}
}
