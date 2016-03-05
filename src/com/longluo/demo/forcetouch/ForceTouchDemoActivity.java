package com.longluo.demo.forcetouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

import com.longluo.demo.R;

public class ForceTouchDemoActivity extends Activity {
	private static final String TAG = "ForceTouch";

	private ForceTouchView mForceTouchView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_forcetouch_demo);

		mForceTouchView = (ForceTouchView) findViewById(R.id.forcetouch);
		mForceTouchView.setBackground(getResources().getDrawable(
				R.drawable.homescreen));
		
		/*
		mForceTouchView.setOnTouchListener(new OnTouchListener() {
			
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				Log.d(TAG, "onTouch, v=" + v + ",event=" + ev.getAction());
				
				switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.d(TAG, "ACTION_DOWN ");
					
					break;
					
				case MotionEvent.ACTION_MOVE:
					Log.d(TAG, "ACTION_MOVE ");
					
//					mForceTouchView.startRipple();
					
					break;
					
				case MotionEvent.ACTION_UP:
					Log.d(TAG, "ACTION_UP");
					
//					mForceTouchView.stopRipple();
					
					break;
				}
				
				return true;
			}
		});
		
		*/

	}

}
