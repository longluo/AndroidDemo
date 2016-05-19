package com.longluo.demo.contentprovider;

import android.net.Uri;

import com.longluo.demo.db.AndroidDemoDataBase;

public class ProviderConstants {
	
	public static final String CP_TABLE_NAME = AndroidDemoDataBase.TABLE_NAME;
	public static final String CP_ID = AndroidDemoDataBase.ID;
	public static final String CP_NAME = AndroidDemoDataBase.NAME;
	public static final String CP_SEX = AndroidDemoDataBase.SEX;
	
	public static final Uri URI = Uri
			.parse("content://com.longluo.demo.contentprovider");
	
}
