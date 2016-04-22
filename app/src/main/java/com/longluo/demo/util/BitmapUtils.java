package com.longluo.demo.util;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.MeasureSpec;

public class BitmapUtils {
	
	public static Bitmap getViewBitmap(View v) {
		v.setDrawingCacheEnabled(true);

		// this is the important code :)
		// Without it the view will have a dimension of 0,0 and the bitmap will
		// be null
		v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

		v.buildDrawingCache(true);
		Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
		v.setDrawingCacheEnabled(false); // clear drawing cache

		return bmp;
	}
}
