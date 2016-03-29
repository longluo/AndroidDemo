package com.longluo.demo.forcetouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ShortcutListPopWindowView extends View {
	private static final String TAG = "ShortcutListPopWindowView";

	private static final int MSG_ANIMATION_BEGIN = 101;
	private static final int MSG_MAIN_ANIMATION_EXPAND = 102;
	private static final int MSG_ANIMATION_END = 104;
	private static final int MSG_BG_CLEAR = 105;

	Paint paint;
	Paint paint1;
	Paint paint2;
	Paint paint3;
	Paint paint4;

	int alpha = 0;
	int alpha1 = 0;
	int alpha2 = 0;
	int alpha3 = 0;

	int radius = 0;
	int radius1 = 0;
	int radius2 = 0;
	int radius3 = 0;
	int radius4 = 0;

	private int xDown = 300;
	private int yDown = 610;

	private float mRadius = 0;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case MSG_ANIMATION_BEGIN:
				invalidate();
				mHandler.sendEmptyMessage(MSG_MAIN_ANIMATION_EXPAND);
				break;

			case MSG_MAIN_ANIMATION_EXPAND:
				flushState();
				invalidate();
				break;

			case MSG_ANIMATION_END:
				invalidate();
				break;

			default:
				break;
			}
		}

		private void flushState() {
			Log.d(TAG, "flushState, mRadius=" + mRadius);

			if (mRadius >= 60.0) {
				return;
			}

			mRadius += 2;
		}
	};

	public ShortcutListPopWindowView(Context context) {
		super(context);

	}

	public ShortcutListPopWindowView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ShortcutListPopWindowView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initPaint();

	}

	@Override
	protected void onDraw(Canvas canvas) {

	}

	int width = 5;

	private void initPaint() {
		int colorGray = 0xB3ffffff;

		// 新建画笔1
		paint1 = new Paint();
		paint1.setAntiAlias(true);
		paint1.setStrokeWidth(width);

		// 初始化画笔 1
		paint1.setStyle(Paint.Style.FILL);// .STROKE);
		paint1.setAlpha(alpha);
		paint1.setColor(colorGray);

		// 新建画笔2
		paint2 = new Paint();
		paint2.setAntiAlias(true);
		paint2.setStrokeWidth(width);

		// 初始化画笔 2
		paint2.setStyle(Paint.Style.FILL);// .STROKE);
		paint2.setAlpha(alpha);
		paint2.setColor(colorGray);

		// 新建画笔3
		paint3 = new Paint();
		paint3.setAntiAlias(true);
		paint3.setStrokeWidth(width);

		// 初始化画笔 3
		paint3.setStyle(Paint.Style.FILL);// .STROKE);
		paint3.setAlpha(alpha);
		paint3.setColor(colorGray);

		// 新建画笔4
		paint4 = new Paint();
		paint4.setAntiAlias(true);
		paint4.setStrokeWidth(width);

		// 初始化画笔 4
		paint4.setStyle(Paint.Style.FILL);// .STROKE);
		paint4.setAlpha(alpha);
		paint4.setColor(colorGray);
	}

	void shrink(Canvas canvas) {
		drawLarge(canvas, radius1, paint1);
		drawLarge(canvas, radius2, paint2);
		drawLarge(canvas, radius3, paint3);
		drawLarge(canvas, radius4, paint4);
	}

	void drawLarge(Canvas canvas, float r, Paint p) {
		if (r > 0) {
			RectF mRect = new RectF();

			mRect.left = xDown - (16 * r) / 6;
			mRect.top = yDown - (16 * r) / 6;
			mRect.right = xDown + (16 * r) / 6;
			mRect.bottom = yDown + (16 * r) / 6;

			canvas.drawRoundRect(mRect, r, r, p);
		}
	}

}
