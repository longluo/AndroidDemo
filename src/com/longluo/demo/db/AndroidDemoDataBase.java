package com.longluo.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AndroidDemoDataBase extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "AndroidDemo";
	
	

	public AndroidDemoDataBase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL();
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL(sql);
		
	}

}
