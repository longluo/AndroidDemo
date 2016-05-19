package com.longluo.demo.forcetouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.longluo.demo.R;

public class AppIconPressDemoActivity extends Activity {
	private AppIconForceTouchView mForceTouchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_app_icon_press_demo);

		initViews();
	}

	@SuppressLint("NewApi")
	private void initViews() {
		mForceTouchView = (AppIconForceTouchView) findViewById(R.id.forcetouch);
		mForceTouchView.setBackground(getResources().getDrawable(
				R.drawable.homescreen));
	}
}
