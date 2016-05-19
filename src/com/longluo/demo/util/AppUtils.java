package com.longluo.demo.util;

import com.longluo.demo.R;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;

/**
 * 跟App相关的辅助类
 */
public class AppUtils {

	private AppUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void createShortCut(Context contxt) {
	    // if (isInstallShortcut()) {// 如果已经创建了一次就不会再创建了
	    // return;
	    // }
	 
	    Intent sIntent = new Intent(Intent.ACTION_MAIN);
	    sIntent.addCategory(Intent.CATEGORY_LAUNCHER);// 加入action,和category之后，程序卸载的时候才会主动将该快捷方式也卸载
//	    sIntent.setClass(contxt, Login.class);
	 
	    Intent installer = new Intent();
	    installer.putExtra("duplicate", false);
	    installer.putExtra("android.intent.extra.shortcut.INTENT", sIntent);
	    installer.putExtra("android.intent.extra.shortcut.NAME", "名字");
	    installer.putExtra("android.intent.extra.shortcut.ICON_RESOURCE",
	            Intent.ShortcutIconResource
	                    .fromContext(contxt, R.drawable.icon));
	    installer.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	    contxt.sendBroadcast(installer);
	}

	public static boolean isInstallShortcut(Context context) {
		boolean isInstallShortcut = false;
		final ContentResolver cr = context.getContentResolver();
		String AUTHORITY = "com.android.launcher.settings";
		Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
				+ "/favorites?notify=true");

		Cursor c = cr.query(CONTENT_URI,
				new String[] { "title", "iconResource" }, "title=?",
				new String[] { "名字" }, null);
		if (c != null && c.getCount() > 0) {
			isInstallShortcut = true;
		}

		if (c != null) {
			c.close();
		}

		if (isInstallShortcut) {
			return isInstallShortcut;
		}

		AUTHORITY = "com.android.launcher2.settings";
		CONTENT_URI = Uri.parse("content://" + AUTHORITY
				+ "/favorites?notify=true");
		c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" },
				"title=?", new String[] { "名字" }, null);
		if (c != null && c.getCount() > 0) {
			isInstallShortcut = true;
		}

		if (c != null) {
			c.close();
		}

		AUTHORITY = "com.baidu.launcher";
		CONTENT_URI = Uri.parse("content://" + AUTHORITY
				+ "/favorites?notify=true");
		c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" },
				"title=?", new String[] { "名字" }, null);
		if (c != null && c.getCount() > 0) {
			isInstallShortcut = true;
		}

		if (c != null) {
			c.close();
		}

		return isInstallShortcut;
	}
	

}
