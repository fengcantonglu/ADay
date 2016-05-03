package com.can.aday.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.can.aday.AdayApplication;
import com.can.aday.utils.CacheTools;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

public class Music {
	private int id;
	private String objectId;
	private String musicname;
	private String singer;
	private String musicpath;
	private String musicLocalPath;
	private String story;
	private String songWords;
	private String songWordsLocalPath;
	private String introduce;
	private String introduction;
	private String backgroundpath;
	private String backgroundlocalpath;
	private int likeNumber;
	private int addtime;

	public String getSongWords() {
		return songWords;
	}

	public void setSongWords(String songWords) {
		this.songWords = songWords;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}

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

	public String getBackgroundlocalpath() {
		return backgroundlocalpath;
	}

	public void setBackgroundlocalpath(String backgroundlocalpath) {
		this.backgroundlocalpath = backgroundlocalpath;
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

	public String getMusicLocalPath() {
		return musicLocalPath;
	}

	public void setMusicLocalPath(String musicLocalPath) {
		this.musicLocalPath = musicLocalPath;
	}

	public String getSongWordsLocalPath() {
		return songWordsLocalPath;
	}

	public void setSongWordsLocalPath(String songWordsLocalPath) {
		this.songWordsLocalPath = songWordsLocalPath;
	}

	/**
	 * 执行缓存下载音乐和对应歌词;执行时会判断 musicLocalPath 和
	 * songWordsLocalPath是否为空，不为空、且存在时,不会执行下载,直接调用DownloadEnd方法;
	 */
	@SuppressLint("HandlerLeak")
	public void cacheMusicAndLrc(final OnCacheMusicListener listener) {
		final long READ_BUFFER = 400 * 1024;
		final Handler hand = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (listener != null)
						listener.musicCanRead(musicLocalPath);
					break;
				case 2:
					if (listener != null)
						listener.musicDownloadEnd(musicLocalPath, true);
					break;
				case 3:
					if (listener != null)
						listener.lrcDownloadEnd((String) msg.obj, true);
					break;
				case 4:
					if (listener != null)
						listener.musicDownloadEnd(musicLocalPath, false);
					break;
				case 5:
					if (listener != null)
						listener.lrcDownloadEnd(songWordsLocalPath, false);
					break;
				default:
					break;
				}
			}
		};
		if (musicLocalPath == null || !new File(musicLocalPath).exists()) {
			new Thread() {
				@Override
				public void run() {
					try {
						URL url;
						if (musicpath.startsWith("http")) {
							url = new URL(musicpath);
						} else {
							url = new URL(AdayApplication.SERVICE_IP + musicpath);
						}
						InputStream in = url.openStream();
						File file = new File(CacheTools.getMusicFile(), musicname + ".mp3");
						long readSize = file.length();
						FileOutputStream fos = new FileOutputStream(file);
						long lastSize = 0;
						byte[] buf = new byte[1024 * 4];
						int len = 0;
						musicLocalPath = file.getPath();
						while ((len = in.read(buf)) != -1) {
							fos.write(buf, 0, len);
							fos.flush();
							readSize += len;
							if (readSize - lastSize > READ_BUFFER) {

								hand.sendEmptyMessage(1);
							}

						}
						fos.close();
						in.close();

						hand.sendEmptyMessage(2);
					} catch (MalformedURLException e) {

						e.printStackTrace();
					} catch (IOException e) {

						e.printStackTrace();
					}
				};
			}.start();
		} else {

			hand.sendEmptyMessage(4);
		}

		if (songWordsLocalPath == null || !new File(songWordsLocalPath).exists()) {
			new Thread() {
				@Override
				public void run() {
					try {
						URL url;
						if (songWords.startsWith("http")) {
							url = new URL(songWords);
						} else {
							url = new URL(AdayApplication.SERVICE_IP + songWords);
						}
						InputStream in = url.openStream();
						File file = new File(CacheTools.getLRCFile(), musicname + ".lrc");
						FileOutputStream fos = new FileOutputStream(file);
						byte[] buf = new byte[1024];
						int len = 0;
						Message msg = Message.obtain();
						msg.obj = file.getPath();
						msg.what = 3;
						while ((len = in.read(buf)) != -1) {
							fos.write(buf, 0, len);
						}
						fos.flush();
						fos.close();
						in.close();
						songWordsLocalPath = file.getPath();
						hand.sendMessage(msg);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
			}.start();
		} else {
			hand.sendEmptyMessage(5);
		}

	}

	/**
	 * 缓存背景图
	 */
	public void cacheBackGroudImage() {

	}

	/**
	 * 音乐缓存监听
	 * 
	 * @author kk0927
	 *
	 */
	public interface OnCacheMusicListener {
		/**
		 * 音乐缓存一定可播放的程度
		 * 
		 * @param path
		 */
		void musicCanRead(String path);

		/**
		 * 音乐下载完毕
		 * 
		 * @param path
		 * @param isDownload
		 *            是否执行了下载
		 */
		void musicDownloadEnd(String path, boolean isDownload);

		/**
		 * 歌词下载完成
		 * 
		 * @param isDownload
		 *            是否执行了下载
		 * @param path
		 */
		void lrcDownloadEnd(String path, boolean isDownload);
	}

	public static Music parseJSONObject(JSONObject jo) {
		Music mc = new Music();
		try {
			mc.setId(jo.getInt("id"));
			mc.objectId=jo.getString("objectId");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		try {
			mc.setMusicname(jo.getString("musicname"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mc.setSinger(jo.getString("singer"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mc.setMusicpath(jo.getJSONObject("musicpath").getString("url"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mc.setStory(jo.getString("story"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mc.setSongWords(jo.getJSONObject("songWords").getString("url"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mc.setIntroduce(jo.getString("introduce"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mc.setBackgroundpath(jo.getString("backgroundpath"));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			mc.setAddtime(jo.getInt("addtime"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mc.setStatus(jo.getInt("status"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mc.setIntroduction(jo.getString("introduction"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mc.setLikeNumber(jo.getInt("likeNumber"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mc;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}
