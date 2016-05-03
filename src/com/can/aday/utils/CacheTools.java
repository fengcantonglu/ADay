package com.can.aday.utils;

import java.io.File;
import java.security.MessageDigest;

import com.can.aday.data.Book;
import com.can.aday.data.Music;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public final class CacheTools {

	/**
	 * 获得Aday的文件缓存路径
	 * 
	 * @return
	 */
	public static String getAdayFile() {
		File file;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "aday");
		} else {
			file = new File(Environment.getDataDirectory().getPath() + File.separator + "aday");
		}
		if (!file.exists())
			file.mkdir();
		return file.getPath();
	}

	/**
	 * 获得音乐类缓存文件夹
	 * 
	 * @return
	 */
	public static String getMusicFile() {
		File file = new File(getAdayFile() + File.separator + "music");
		if (!file.exists())
			file.mkdir();
		return file.getPath();
	}

	/**
	 * 获得图片缓存文件夹
	 * 
	 * @return
	 */
	public static String getImageFile() {
		File file = new File(getAdayFile() + File.separator + "image");
		if (!file.exists())
			file.mkdir();
		return file.getPath();
	}

	/**
	 * 获得歌词缓存文件夹
	 * 
	 * @return
	 */
	public static String getLRCFile() {
		File file = new File(getMusicFile() + File.separator + "lrc");
		if (!file.exists())
			file.mkdir();
		return file.getPath();

	}

	/**
	 * 缓存登陆行用户名和密码且保存登陆状态为true
	 */
	public static void cachedPageGuide(String account, String password, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("account", account);
		editor.putString("password", password);
		editor.commit();
		loginStateSave(context, true);
	}

	/**
	 * 保存登陆状态,isLogin为true存已登陆状态,则跳转不会进入登陆页面,isLogin为false为注销缓存登陆状态
	 * 
	 * @param context
	 * @param isLogin
	 */
	public static void loginStateSave(Context context, boolean isLogin) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("isLogin", isLogin);
		editor.commit();
	}

	/**
	 * 获取缓存登陆状态
	 * 
	 * @param context
	 * @return true 已登陆,false 未登录
	 */
	public static boolean getLoginState(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean("isLogin", false);
	}

	/**
	 * 获取缓存里面的登陆数据,
	 * </p>
	 * ap[0]为获取的帐户名； ap[1]为获取的账户密码
	 * 
	 * @param context
	 * @param ap
	 * @throws NullPointerException
	 *             如果 context或者 ap为空时
	 * @throws ArrayIndexOutOfBoundsException
	 *             ap.length<2
	 */
	public static void getAccountCache(Context context, String[] ap)
			throws NullPointerException, ArrayIndexOutOfBoundsException {
		SharedPreferences sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
		ap[0] = sharedPreferences.getString("account", "");
		ap[1] = sharedPreferences.getString("password", "");
	}

	/**
	 * 缓存音乐表
	 * 
	 * @param context
	 * @param data
	 */
	public static void cacheMusicData(Context context, Music data) {
		SQLiteDatabase db = getDatabase(context);
		ContentValues cv = new ContentValues();
		cv.put("musicname", data.getMusicname());
		cv.put("musicid", data.getId());
		cv.put("musicpath", data.getMusicpath());
		cv.put("story", data.getStory());
		cv.put("singer", data.getSinger());
		cv.put("introduce", data.getIntroduce());
		cv.put("songWords", data.getSongWords());
		cv.put("backgroundpath", data.getBackgroundpath());
		cv.put("addtime", data.getAddtime());

		db.insert(AdaySqlHelper.MUSIC_TABLE, null, cv);
		db.close();
	}

	/**
	 * 更新音乐本地数据库表,
	 * </p>
	 * 示例:</br>
	 * </p>
	 * Music data=new Music();
	 * </p>
	 * data.setId(12);//要更新的表的id，
	 * </p>
	 * data.setMusicLocalPath("/mnt/sdcard/..");//要更新的内容字段,若不需更新,则不去设置即可，
	 * 只设置要更新的内容.因而不支持修改id。
	 * </p>
	 * CacheTools.updateMusicData(context,data);
	 * 
	 * @param context
	 * @param data
	 */
	public static void updateMusicData(Context context, Music data) {
		SQLiteDatabase db = getDatabase(context);
		ContentValues cv = new ContentValues();
		if (data.getMusicname() != null) {
			cv.put("musicname", data.getMusicname());
		}
		if (data.getSinger() != null) {
			cv.put("singer", data.getSinger());
		}
		if (data.getMusicpath() != null) {
			cv.put("musicpath", data.getMusicpath());
		}
		if (data.getMusicLocalPath() != null) {
			cv.put("music_local_path", data.getMusicLocalPath());
		}
		if (data.getSongWords() != null) {
			cv.put("songWords", data.getSongWords());
		}
		if (data.getSongWordsLocalPath() != null) {
			cv.put("song_words_local_path", data.getSongWordsLocalPath());
		}
		if (data.getStory() != null) {
			cv.put("story", data.getStory());
		}
		if (data.getIntroduce() != null) {
			cv.put("introduce", data.getIntroduce());
		}
		if (data.getBackgroundpath() != null) {
			cv.put("backgroundpath", data.getBackgroundpath());
		}
		if (data.getBackgroundlocalpath() != null) {
			cv.put("backgroundlocalpath", data.getBackgroundlocalpath());
		}
		String[] whereArgs = { data.getId() + "" };
		db.update(AdaySqlHelper.MUSIC_TABLE, cv, "musicid=?", whereArgs);
		db.close();
	}

	/**
	 * 根据服务器获取的Music数据，在本地进行校验，本地是否进行了缓存，若进行了缓存，为传入的data进行setMusicLocalPath,
	 * setSongWordsLocalPath,setBackgroundlocalpath一系列处理；
	 * </p>
	 * 相反，若这是条新数据,本地并没有缓存，则进行缓存，注意这里不做文件的缓存，只做数据缓存
	 * 
	 * @param context
	 * @param data
	 * @return
	 */
	public static void getLocalOrSaveMusicData(Context context, Music data) {
		if (data == null || data.getId() == 0) {
			return;
		}
		SQLiteDatabase db = getDatabase(context);

		Cursor c = db.query(AdaySqlHelper.MUSIC_TABLE, null, "musicid=?", new String[] { "" + data.getId() }, null,
				null, null);
		if (c.moveToNext()) {
			data.setMusicLocalPath(c.getString(c.getColumnIndex("music_local_path")));
			data.setSongWordsLocalPath(c.getString(c.getColumnIndex("song_words_local_path")));
			data.setBackgroundlocalpath(c.getString(c.getColumnIndex("backgroundlocalpath")));
			Log.i("CacheTools", "load Music");
		} else {
			ContentValues cv = new ContentValues();
			cv.put("musicname", data.getMusicname());
			cv.put("musicid", data.getId());
			cv.put("musicpath", data.getMusicpath());
			cv.put("story", data.getStory());
			cv.put("singer", data.getSinger());
			cv.put("introduce", data.getIntroduce());
			cv.put("songWords", data.getSongWords());
			cv.put("backgroundpath", data.getBackgroundpath());
			cv.put("addtime", data.getAddtime());
			db.insert(AdaySqlHelper.MUSIC_TABLE, null, cv);
			Log.i("CacheTools", "Save Music");
		}
		c.close();
		db.close();
	}

	/**
	 * 在未连接网络的时候,执行此方法，完全获取本地缓存的最新的一条数据.若没有则返回null
	 * 
	 * @param context
	 * @param index
	 *            查询第几条，0代表第一条
	 * @return
	 */
	public static Music getLocalMusicData(Context context, int index) {

		SQLiteDatabase db = getDatabase(context);
		Cursor c = db.query(AdaySqlHelper.MUSIC_TABLE, null, null, null, null, null, "musicid desc", index + ",1");
		Music data = null;
		if (c.moveToNext()) {
			data = new Music();
			data.setId(c.getInt(c.getColumnIndex("musicid")));
			data.setAddtime(c.getInt(c.getColumnIndex("addtime")));
			data.setMusicname(c.getString(c.getColumnIndex("musicname")));
			data.setMusicpath(c.getString(c.getColumnIndex("musicpath")));
			data.setMusicLocalPath(c.getString(c.getColumnIndex("music_local_path")));
			data.setSongWords(c.getString(c.getColumnIndex("songWords")));
			data.setSongWordsLocalPath(c.getString(c.getColumnIndex("song_words_local_path")));
			data.setBackgroundpath(c.getString(c.getColumnIndex("backgroundpath")));
			data.setBackgroundlocalpath(c.getString(c.getColumnIndex("backgroundlocalpath")));
			data.setStory(c.getString(c.getColumnIndex("story")));
			data.setIntroduce(c.getString(c.getColumnIndex("introduce")));
			data.setSinger(c.getString(c.getColumnIndex("singer")));
		}
		c.close();
		db.close();
		return data;
	}

	public static void cacheBookData(Context context, Book data) {
		SQLiteDatabase db = getDatabase(context);
		ContentValues cv = new ContentValues();
		cv.put("title", data.getTitle());
		cv.put("id", data.getId());
		cv.put("articleimg", data.getArticleimg());
		cv.put("content", data.getContentToString());
		cv.put("author", data.getAuthor());
		cv.put("introduction", data.getIntroduction());
		cv.put("articleimg_local_path", data.getArticleimg_local_path());
		cv.put("authordescrip", data.getAuthordescrip());
		// cv.put("addtime", data.getAddtime());
		db.insert(AdaySqlHelper.BOOK_TABLE, null, cv);
		db.close();
	}

	public static void getBookDataOrSave(Context context, Book data) {

		if (data == null || data.getId() == 0) {
			return;
		}
		SQLiteDatabase db = getDatabase(context);

		Cursor c = db.query(AdaySqlHelper.BOOK_TABLE, null, "id=?", new String[] { "" + data.getId() }, null, null,
				null);
		if (c.moveToNext()) {
			data.setArticleimg_local_path(c.getString(c.getColumnIndex("articleimg_local_path")));
		} else {
			ContentValues cv = new ContentValues();
			cv.put("title", data.getTitle());
			cv.put("id", data.getId());
			cv.put("articleimg", data.getArticleimg());
			cv.put("content", data.getContentToString());
			cv.put("author", data.getAuthor());
			cv.put("introduction", data.getIntroduction());
			cv.put("authordescrip", data.getAuthordescrip());
			// cv.put("addtime", data.getAddtime());
			db.insert(AdaySqlHelper.BOOK_TABLE, null, cv);
		}
		c.close();
		db.close();
	}

	/**
	 * 在未连接网络的时候,执行此方法，完全获取本地缓存的最新的一条数据.若没有则返回null
	 * 
	 * @param context
	 * @param index
	 *            查询第几条，0代表第一条
	 * @return
	 */
	public static Book getLocalBookData(Context context, int index) {

		SQLiteDatabase db = getDatabase(context);
		Cursor c = db.query(AdaySqlHelper.BOOK_TABLE, null, null, null, null, null, "id desc", index + ",1");
		Book data = null;
		if (c.moveToNext()) {
			data = new Book();
			data.setId(c.getInt(c.getColumnIndex("id")));
			// data.setAddtime(c.getInt(c.getColumnIndex("addtime")));
			data.setTitle(c.getString(c.getColumnIndex("title")));
			data.setArticleimg(c.getString(c.getColumnIndex("articleimg")));
			data.setArticleimg_local_path(c.getString(c.getColumnIndex("articleimg_local_path")));
			data.setAuthor(c.getString(c.getColumnIndex("author")));
			data.setAuthordescrip(c.getString(c.getColumnIndex("authordescrip")));
			data.parseStringToStage(c.getString(c.getColumnIndex("content")));
			data.setIntroduction(c.getString(c.getColumnIndex("introduction")));
		}
		c.close();
		db.close();
		return data;
	}

	/**
	 * 获得数据库对象
	 * 
	 * @param context
	 * @return
	 */
	public static SQLiteDatabase getDatabase(Context context) {
		AdaySqlHelper helper = new AdaySqlHelper(context);
		return helper.getWritableDatabase();
	}

	/**
	 * MD5加密字符串
	 * 
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

}
