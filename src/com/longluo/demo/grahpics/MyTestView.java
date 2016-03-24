package com.longluo.demo.grahpics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;

import com.longluo.demo.gaussianblur.NativeBlur;

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

	@SuppressLint("NewApi")
	private void initViews(Bitmap bkg) {
		Bitmap overlay = Bitmap.createBitmap(bkg.getWidth(), bkg.getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(overlay);
		
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);

		Path path = new Path();
		RectF viewBounus = new RectF(50, 50, 300, 500);
		path.addRoundRect(viewBounus, 30, 30, Path.Direction.CCW);

		canvas.clipPath(path);
		
		canvas.drawBitmap(bkg, 0, 0, paint);
		
		final NativeBlur nativeBlur = new NativeBlur();
		
//		mActivity.getImageView().setImageBitmap(nativeBlur.blur(overlay, 60));
//		mActivity.getImageView().setImageBitmap(overlay);
		
//		mActivity.getImageView().setAlpha(0.6f);
		
//		final BitmapDrawable bg = new BitmapDrawable(nativeBlur.blur(overlay, 50));
		
		final BitmapDrawable bg = new BitmapDrawable(nativeBlur.blur(overlay, 50));
		setBackground(bg);
		
		mActivity.getImageView().setBackground(bg);
//		mActivity.getImageView().setAlpha(0.3f);
		
//		setBackgroundColor(Color.BLUE);
//		setAlpha((float) 0.2);
		
	}

}
