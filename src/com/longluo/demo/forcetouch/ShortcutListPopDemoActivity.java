package com.longluo.demo.forcetouch;

import com.longluo.demo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class ShortcutListPopDemoActivity extends Activity {

	private ShortcutListPopWindowView mShortcutPopView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_shortcut_list_pop_window_demo);

		initViews();
	}

	@SuppressLint("NewApi")
	private void initViews() {
//		mShortcutPopView = (ShortcutListPopWindowView) findViewById(R.id.forcetouch);
//		mShortcutPopView.setBackground(getResources().getDrawable(
//				R.drawable.homescreen));
	}
	
}
