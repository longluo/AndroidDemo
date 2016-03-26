package com.longluo.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AndroidDemoDataBase extends SQLiteOpenHelper {
	private static final String DB_NAME = "AndroidDemo";

	public static final String TABLE_NAME = "student";
	public static final String ID = "_id";
	public static final String NAME = "name";
	public static final String SEX = "sex";

	public AndroidDemoDataBase(Context context) {
		super(context, TABLE_NAME, null, 1);
		
	}

	public AndroidDemoDataBase(Context context, String name,
			CursorFactory factory, int version) {

		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
				+ " TEXT NOT NULL," + SEX + " TEXT NOT NULL)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
