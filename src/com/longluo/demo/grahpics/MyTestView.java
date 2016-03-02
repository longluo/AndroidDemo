package com.longluo.demo.grahpics;

import com.longluo.demo.gaussianblur.NativeBlur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class MyTestView extends FrameLayout {
	private GrahpicsDemoActivity mActivity;

	public MyTestView(Context context, Bitmap bmp) {
		super(context);

		mActivity = (GrahpicsDemoActivity) context;

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.setLayoutParams(params);

		Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight());
		initViews(bitmap);
	}

	private void initViews(Bitmap bkg) {
		Bitmap overlay = Bitmap.createBitmap(bkg.getWidth(), bkg.getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(overlay);
		
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);

		Path path = new Path();
		RectF viewBounus = new RectF(10, 60, 300, 300);
		path.addRoundRect(viewBounus, 30, 30, Path.Direction.CCW);

		canvas.clipPath(path);
		
		canvas.drawBitmap(bkg, 0, 0, paint);

		final NativeBlur nativeBlur = new NativeBlur();
		mActivity.getImageView().setImageBitmap(nativeBlur.blur(overlay, 60));
	}

}
