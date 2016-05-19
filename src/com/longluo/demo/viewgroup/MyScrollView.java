package com.longluo.demo.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.e("TAG",
				(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED)
						+ "");
		Log.e("TAG",
				(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST)
						+ "");
		Log.e("TAG",
				(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)
						+ "");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

}
