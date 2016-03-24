package com.longluo.demo.contact.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;

public class BaseIntentUtil {
	public static int DEFAULT_ENTER_ANIM;
	public static int DEFAULT_EXIT_ANIM;

	public static Intent intent;

	public static void intentDIY(Activity activity, Class<?> classes) {
		IntentDIY(activity, classes, null, DEFAULT_ENTER_ANIM,
				DEFAULT_EXIT_ANIM);
	}

	public static void IntentDIY(Activity activity, Class<?> classes,
			Map<String, String> paramMap, int enterAnim, int exitAnim) {
		intent = new Intent(activity, classes);
		organizeAndStart(activity, classes, paramMap);
		if (enterAnim != 0 && exitAnim != 0) {
			activity.overridePendingTransition(enterAnim, exitAnim);
		}
	}

	public static void intentSysDefault(Activity activity, Class<?> classes,
			Map<String, String> paramMap) {
		organizeAndStart(activity, classes, paramMap);
	}

	private static void organizeAndStart(Activity activity, Class<?> classes,
			Map<String, String> paramMap) {
		intent = new Intent(activity, classes);
		if (null != paramMap) {
			Set<String> set = paramMap.keySet();
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				intent.putExtra(key, paramMap.get(key));
			}
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
	}
}
