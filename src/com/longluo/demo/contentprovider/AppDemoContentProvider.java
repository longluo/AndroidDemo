package com.longluo.demo.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.longluo.demo.db.AndroidDemoDataBase;

public class AppDemoContentProvider extends ContentProvider {

	private SQLiteDatabase mDbReader;
	private SQLiteDatabase mDbWriter;

	/** 对onCreate方法的重写 **/
	@Override
	public boolean onCreate() {
		// 在onCreate方法中实现数据库的实例，以及数据库可读可写对象
		AndroidDemoDataBase my = new AndroidDemoDataBase(getContext());
		mDbReader = my.getReadableDatabase();
		mDbWriter = my.getWritableDatabase();

		return false;
	}

	/** 对查询方法的封装 **/
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return mDbReader.query(ProviderConstants.CP_TABLE_NAME, null, selection, selectionArgs,
				null, null, sortOrder);
	}

	/** 重写getType方法，用以得到Uri的字符串 **/
	@Override
	public String getType(Uri uri) {
		return uri.toString();
	}

	/** 对插入方法的封装 **/
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		mDbWriter.insert(ProviderConstants.CP_TABLE_NAME, null, values);
		return uri;
	}

	/** 对删除操作方法的封装 **/
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return mDbWriter.delete(ProviderConstants.CP_TABLE_NAME, selection, selectionArgs);
	}

	/** 对更新操作方法的封装 **/
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return mDbWriter
				.update(ProviderConstants.CP_TABLE_NAME, values, selection, selectionArgs);
	}

}
