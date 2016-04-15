package com.can.aday.utils;

import com.can.aday.data.Music;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdaySqlHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "aday";
	private static final int VERSION = 1;
	private static final String TABLE_NAME = "music";

	public AdaySqlHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	/**
	 * 创建表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db != null) {
			String sql = "Create table " + TABLE_NAME + " (musicid INTENGER PRIMARY KEY,"
					+ "musicname VERCHAR(50) NOT NULL," + "singer VERCHAR(50) ," + " musicpath VERCHAR(100) NOT NULL,"
					+ "story TEXT," + "song_words_path VERCHAR(100)," + "introduce TEXT,"
					+ "backgroundpath VERCHAR(100)," + "addtime INTENGER," + "status INTENGER ); ";
			db.execSQL(sql);
		}
	}

	/**
	 * 更新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = " DROP TABLE IF EXSTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	};

	public void inputData() {
		SQLiteDatabase databass = getDatabase();
		Music mc = new Music();
		String sql = "insert into " + AdaySqlHelper.TABLE_NAME + " values(?,?,?,?,?);";
		databass.execSQL(sql, new Object[] { mc.getId(), mc.getMusicname(), "", "", "", "", "", "", 1, 1 });
	}

	public SQLiteDatabase getDatabase() {
		// AdaySqlHelper aday = new AdaySqlHelper(getApplicationContext());
		// SQLiteDatabase database = aday.getWritableDatabase();
		// return database;
		return null;
	}
}
