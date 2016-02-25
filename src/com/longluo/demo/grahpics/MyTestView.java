package com.longluo.demo.grahpics;

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

	private Bitmap mBackground;
	private Context mContext;

	public MyTestView(Context context) {
		this(context, null);
	}

	public MyTestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyTestView(Context context, AttributeSet attrs, int defStyle,
			View view) {
		super(context, attrs, defStyle);

		mContext = context;

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.setLayoutParams(params);

		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		
		Bitmap bmp1 = view.getDrawingCache();

		Bitmap bmp2 = Bitmap.createBitmap(bmp1, 100, 100, 400, 500);

		initViews(bmp2);
	}

	private void initViews(Bitmap bkg) {
		Bitmap overlay = Bitmap.createBitmap(500, 600, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(overlay);
		Paint paint = new Paint();

		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		paint.setColor(Color.RED);

		Path path = new Path();
		RectF viewBounus = new RectF(100, 100, 500, 600);

		path.addRoundRect(viewBounus, 30, 30, Path.Direction.CCW);

		canvas.clipPath(path);
		canvas.drawBitmap(bkg, 100, 100, paint);
	}

}
