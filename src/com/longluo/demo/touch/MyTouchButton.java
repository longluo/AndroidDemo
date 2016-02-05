package com.longluo.demo.touch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class MyTouchButton extends Button {

	private static final String TAG = MyTouchButton.class.getSimpleName();

	public MyTouchButton(Context context) {
		super(context);
	}

	public MyTouchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyTouchButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.e(TAG, "onTouchEvent ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e(TAG, "onTouchEvent ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			Log.e(TAG, "onTouchEvent ACTION_UP");
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.e(TAG, "dispatchTouchEvent ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e(TAG, "dispatchTouchEvent ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			Log.e(TAG, "dispatchTouchEvent ACTION_UP");
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(event);
	}

}
