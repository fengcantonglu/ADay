package com.can.aday.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Music {
	private int id;
	private String musicname;
	private String singer;
	private String musicpath;
	private String story;
	private String song_words_path;
	private String introduce;
	private String backgroundpath;
	private int addtime;
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMusicname() {
		return musicname;
	}

	public void setMusicname(String musicname) {
		this.musicname = musicname;
	}

	public String getSinger() {
		return singer;
	}

	public String getSong_words_path() {
		return song_words_path;
	}

	public void setSong_words_path(String song_words_path) {
		this.song_words_path = song_words_path;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getMusicpath() {
		return musicpath;
	}

	public void setMusicpath(String musicpath) {
		this.musicpath = musicpath;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getBackgroundpath() {
		return backgroundpath;
	}

	public void setBackgroundpath(String backgroundpath) {
		this.backgroundpath = backgroundpath;
	}

	public int getAddtime() {
		return addtime;
	}

	public void setAddtime(int addtime) {
		this.addtime = addtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static Music musicJSONObject(JSONObject jo) {
		Music mc = new Music();
		try {
			mc.setId(jo.getInt("musicid"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			mc.setMusicname(jo.getString("musicname"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mc.setSinger(jo.getString("singer"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mc.setMusicpath(jo.getString("musicpath"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mc.setStory(jo.getString("story"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mc.setSong_words_path(jo.getString("song_words_path"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mc.setIntroduce(jo.getString("introduce"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mc.setBackgroundpath(jo.getString("backgroundpath"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			mc.setAddtime(jo.getInt("addtime"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mc.setStatus(jo.getInt("status"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mc;
	}
}
