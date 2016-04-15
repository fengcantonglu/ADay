package com.can.aday.tools;

import java.io.IOException;
import java.util.TreeMap;

public class LRCInfo {
	private String title;// 歌曲名
	private String singer;// 演唱者
	private String album;// 专辑
	private TreeMap<Integer, String> infos = new TreeMap<Integer, String>();// 保存歌词信息和时间点一一对应的Map
	// 以下为getter() setter()

	/**
	 * 获得歌词歌名
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 歌手名
	 * 
	 * @return
	 */
	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	/**
	 * 获得专辑名
	 * 
	 * @return
	 */
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * 歌词
	 * 
	 * @return
	 */
	public TreeMap<Integer, String> getInfos() {
		return infos;
	}

	public void setInfos(TreeMap<Integer, String> infos) {
		this.infos = infos;
	}
	LRCInfo() {
	}
	public static LRCInfo loadLRCFile(String path) throws IOException{	
		return new LRCParser().parser(path);
	}
}
