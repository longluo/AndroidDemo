package com.longluo.demo.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.longluo.demo.R;

public class ViewDemoActivity extends Activity {
	private static final String TAG = "ViewDemoActivity";
	
	private TextView mTvContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_demo);
		
		initViews();
		init();
	}
	
	private void initViews() {
		mTvContent = (TextView) findViewById(R.id.tv_view);
		
	}
	
	private void init() {
		int touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
		Log.d(TAG, "touchSlop=" + touchSlop);
		
		mTvContent.setText(" " + touchSlop);
		
		mTvContent.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				return false;
			}
		});
		
	}
	

}
