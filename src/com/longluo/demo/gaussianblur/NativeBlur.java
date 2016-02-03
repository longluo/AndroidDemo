package com.longluo.demo.gaussianblur;

import android.graphics.Bitmap;

public class NativeBlur {
	public native void functionToBlur(Bitmap bitmapOut, int radius,
			int threadCount, int threadIndex, int round);
}
