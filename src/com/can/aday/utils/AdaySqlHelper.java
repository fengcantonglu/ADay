package com.can.aday.utils;

import com.can.aday.data.Music;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdaySqlHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "aday";
	public static final int VERSION = 1;
	public static final String MUSIC_TABLE = "music";
	public static final String BOOK_TABLE = "book";

	public AdaySqlHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	/**
	 * 创建表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db != null) {
			String sql = "Create table " + MUSIC_TABLE + " (musicid INTENGER PRIMARY KEY,"
					+ "musicname VERCHAR(50) NOT NULL," + "singer VERCHAR(50) ," + " musicpath VERCHAR(300) NOT NULL,"
					+ "story TEXT," + "music_local_path VARCHAR(300)," + "songWords VERCHAR(300),"
					+ "song_words_local_path VERCHAR(300)," + "introduce TEXT," + "backgroundpath VERCHAR(300),"
					+ "addtime INTENGER," + "backgroundlocalpath VERCHAR(300)); ";
			db.execSQL(sql);
			sql = "Create table " + BOOK_TABLE + " (id INTENGER PRIMARY KEY," + "title VERCHAR(50) NOT NULL,"
					+ "introduction VERCHAR(150) ," + " articleimg VERCHAR(300)," + "content TEXT,"
					+ "articleimg_local_path VARCHAR(300)," + "author VERCHAR(50)," + "authordescrip VERCHAR(300),"
					+ "addtime INTENGER );";
			db.execSQL(sql);
		}
	}

	/**
	 * 更新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = " DROP TABLE IF EXSTS " + MUSIC_TABLE;
		db.execSQL(sql);
		onCreate(db);
	};

	public void inputData() {
		SQLiteDatabase databass = getDatabase();
		Music mc = new Music();
		String sql = "insert into " + AdaySqlHelper.MUSIC_TABLE + " values(?,?,?,?,?);";
		databass.execSQL(sql, new Object[] { mc.getId(), mc.getMusicname(), "", "", "", "", "", "", 1, 1 });
	}

	public SQLiteDatabase getDatabase() {
		// AdaySqlHelper aday = new AdaySqlHelper(getApplicationContext());
		// SQLiteDatabase database = aday.getWritableDatabase();
		// return database;
		return null;
	}
}
