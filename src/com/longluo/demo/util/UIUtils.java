package com.longluo.demo.util;

import com.longluo.demo.MainActivity;

import android.app.ActionBar;
import android.app.Activity;

public class UIUtils {

	public static void initActionBar(final Activity activity) {
		if (activity == null) {
			return;
		}

		ActionBar bar = activity.getActionBar();
		if (activity instanceof MainActivity) {
			bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
					| ActionBar.DISPLAY_SHOW_CUSTOM
					| ActionBar.DISPLAY_SHOW_HOME);
		} else {
			bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
					| ActionBar.DISPLAY_HOME_AS_UP
					| ActionBar.DISPLAY_SHOW_CUSTOM);
		}
	}

}
