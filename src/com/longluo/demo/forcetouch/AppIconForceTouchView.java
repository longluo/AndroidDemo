package com.longluo.demo.forcetouch;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.longluo.demo.R;

public class AppIconForceTouchView extends View {
	private static final String TAG = "ForceTouchView";

	private static final int MSG_ANIMATION_BEGIN = 101;
	private static final int MSG_MAIN_ANIMATION_EXPAND = 102;
	private static final int MSG_MAIN_ANIMATION_SHRINK = 103;
	private static final int MSG_ANIMATION_END = 104;
	private static final int MSG_BG_CLEAR = 105;

	private Context mContext;

	private boolean mIsExpand = false;
	private boolean mIsShrink = false;

	private Paint mPaint;

	private float mRadius = 0;

	private int mAlpha = 0;

	private float mSlop;

	private int xDown = 300;
	private int yDown = 610;

	private int mIconWidth;
	private int mIconHeight;

	private Drawable mBgDrawable;
	private Drawable mBrushDrawable;

	final double INIT_PERCENT = 0.8;
	final double MIN_PERCENT = 0.7;
	final double SINGLE_TURN_PERCENT = 0.016;
	double mTurnPercent = SINGLE_TURN_PERCENT;
	double mPercent = INIT_PERCENT;

	private static boolean mIsVibrate = false;

	private CheckLongPressHelper mLongPressHelper;

	private float mPressValue;

	public AppIconForceTouchView(Context context) {
		super(context);

	}

	public AppIconForceTouchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public AppIconForceTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mContext = context;

		mAlpha = 100;

		mRadius = 0;

		mLongPressHelper = new CheckLongPressHelper(this);
		mSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();

		initPaint();
		initParam();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
	
		/*
		if (mIsExpand) {
			expand(canvas);
		} else {
			shrink(canvas);
		}
		*/
		
		mRadius = 18 + mPressValue * 80;
		
		if (mRadius > 31) {
			mRadius = 31;
		}
		
		drawRectBg(canvas, mRadius);		
	}

	private void initPaint() {

		int colorGray = 0xB3ffffff;

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(10);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAlpha(mAlpha);
		mPaint.setColor(colorGray);
	}

	public void onClick(int x, int y) {
		xDown = x;
		yDown = y;

		mRadius = 20;
		mAlpha = 100;

		initParam();

		mHandler.sendEmptyMessage(MSG_ANIMATION_BEGIN);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case MSG_ANIMATION_BEGIN:
				initIconRoundRect();
				invalidate();
				mHandler.sendEmptyMessage(MSG_MAIN_ANIMATION_EXPAND);
				break;

			case MSG_MAIN_ANIMATION_EXPAND:
				flushState();
				invalidate();
				break;

			case MSG_MAIN_ANIMATION_SHRINK:
				flushShrinkState();
				invalidate();
				break;

			case MSG_ANIMATION_END:
				initIconRoundRect();
				invalidate();
				break;

			case MSG_BG_CLEAR:
				mRadius = 10;
				initIconRoundRect();
				invalidate();
				break;

			default:
				break;
			}
		}

		private void flushState() {
			Log.d(TAG, "flushState, mRadius=" + mRadius);
		
			mRadius = 30 + mPressValue * 80;
			
			if (mRadius > 42) {
				mRadius = 42;
			}
			
			
		}

		private void flushShrinkState() {
			Log.d(TAG, "flushShrinkState, mRadius=" + mRadius);

			if (mRadius <= 16.0) {
				return;
			}

			mRadius -= 3;
			mHandler.sendEmptyMessage(MSG_MAIN_ANIMATION_SHRINK);
		}
	};

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

	private void drawRectBg(Canvas canvas, float radius) {
		drawLarge(canvas, radius, mPaint);

//		setCurrentProgress(canvas);
	}

	private void expand(Canvas canvas) {
		drawLarge(canvas, mRadius, mPaint);

		setCurrentProgress(canvas);
	}

	private void shrink(Canvas canvas) {
		drawLarge(canvas, mRadius, mPaint);

		setCurrentProgress(canvas);
	}

	private final RectF mArcRect = new RectF();

	static final int SECTOR_TOP_LEFT = 1;
	static final int SECTOR_TOP_RIGHT = 2;
	static final int SECTOR_BOTTOM_LEFT = 3;
	static final int SECTOR_BOTTOM_RIGHT = 4;

	// sector1((x0 - (16r/3)/2 + r, y0 - (16r/3)/2 + r)
	// x0 - (16r/3)/2, y0 - (16r/3)/2

	// sector2((x0 + (16r/3)/2 - r, y0 - (16r/3)/2 + r)
	// x0 + (16r/3)/2, y0 - (16r/3)/2

	// sector3((x0 - (16r/3)/2 + r, y0 + (16r/3)/2 - r)
	// x0 - (16r/3)/2, y0 + (16r/3)/2

	// sector4((x0 + (16r/3)/2 - r, y0 + (16r/3)/2 - r)
	// x0 + (16r/3)/2, y0 + (16r/3)/2

	void drawArt(Canvas canvas, int x, int y, int r, int type, Paint p) {
		Log.d(TAG, "drawArt, x=" + x + ",y=" + y + ",r=" + r);

		int x0 = 0;
		int y0 = 0;
		int start = 0;

		switch (type) {
		case SECTOR_TOP_LEFT: {
			x0 = x - (16 * r) / 6 + r;
			y0 = y - (16 * r) / 6 + r;
			start = 180;
		}
			break;

		case SECTOR_TOP_RIGHT: {
			x0 = x + (16 * r) / 6 - r;
			y0 = y - (16 * r) / 6 + r;
			start = 270;
		}
			break;

		case SECTOR_BOTTOM_LEFT: {
			x0 = x - (16 * r) / 6 + r;
			y0 = y + (16 * r) / 6 - r;
			start = 90;
		}
			break;

		case SECTOR_BOTTOM_RIGHT: {
			x0 = x + (16 * r) / 6 - r;
			y0 = y + (16 * r) / 6 - r;
			start = 0;
		}
			break;

		default:
			break;
		}

		mArcRect.top = y0 - r;
		mArcRect.bottom = y0 + r;
		mArcRect.left = x0 - r;
		mArcRect.right = x0 + r;

		canvas.drawArc(mArcRect, start, 90, true, p);
	}

	static final int RECT_TOP = 1;
	static final int RECT_MIDDLE = 2;
	static final int RECT_BOTTOM = 3;

	Rect mRect = new Rect();

	// rect1=105×31 ((10r/3)×r)
	// (x0 - (16r/3)/2 + r, y0 - (16r/3)/2)
	// (x0 + (16r/3)/2 - r, y0 - (16r/3)/2 + r)

	// rect2=168×105 ((16r/3)×(10r/3))
	// (x0 - (16r/3)/2, y0 - (16r/3)/2) + r)
	// (x0 + (16r/3)/2, y0 + (16r/3)/2 - r)

	// rect3=105×31 ((10r/3)×r)
	// (x0 - (16r/3)/2 + r, y0 + (16r/3)/2) - r
	// (x0 + (16r/3)/2 - r, y0 + (16r/3)/2)

	void drawRect(Canvas canvas, int x, int y, int r, int type, Paint p) {
		Log.d(TAG, "drawRect, x=" + x + ",y=" + y + ",r=" + r);

		switch (type) {
		case RECT_TOP: {
			mRect.left = x - (16 * r) / 6 + r;
			mRect.top = y - (16 * r) / 6;
			mRect.right = x + (16 * r) / 6 - r;
			mRect.bottom = y - (16 * r) / 6 + r;
		}
			break;

		case RECT_MIDDLE: {
			mRect.left = x - (16 * r) / 6;
			mRect.top = y - (16 * r) / 6 + r;
			mRect.right = x + (16 * r) / 6;
			mRect.bottom = y + (16 * r) / 6 - r;
		}
			break;

		case RECT_BOTTOM: {
			mRect.left = x - (16 * r) / 6 + r;
			mRect.top = y + (16 * r) / 6 - r;
			mRect.right = x + (16 * r) / 6 - r;
			mRect.bottom = y + (16 * r) / 6;
		}
			break;

		default:
			break;
		}

		canvas.drawRect(mRect, p);
	}

	private int mPaintColorDark;
	private int mPaintColor;
	private int mIconWidthSmall;
	private int mIconHeightSmall;
	private int mIconWidthLarge;
	private int mIconHeightLarge;

	private void initParam() {
		mPaintColorDark = 0xff7c644c;// 0xff482400;
		mPaintColor = 0xffc4b592;// 0xffb08068;

		if (mBgDrawable == null) {
			Resources res = getContext().getResources();
			mBgDrawable = res.getDrawable(R.drawable.clear_background);
			mBrushDrawable = res.getDrawable(R.drawable.clear_brush);
			mIconWidth = mBgDrawable.getIntrinsicWidth();
			mIconHeight = mBgDrawable.getIntrinsicHeight();

			mIconWidthSmall = mIconWidth;
			mIconHeightSmall = mIconHeight;
			mIconWidthLarge = 12 * mIconWidth / 10;
			mIconHeightLarge = 12 * mIconHeight / 10;
		}

		initIconDrawableRect();
	}

	void initIconDrawableRect() {
		mBgDrawable.setBounds(xDown - mIconWidth / 2, yDown - mIconHeight / 2,
				xDown + mIconWidth / 2, yDown + mIconHeight / 2);
		mBrushDrawable.setBounds(xDown - mIconWidth / 2, yDown - mIconHeight
				/ 2, xDown + mIconWidth / 2, yDown + mIconHeight / 2);

	}

	void initIconRoundRect() {
		// mBrushDrawable.setBounds(xDown
		// - (int) ((mPercent + 0.2) * mIconWidth / 2), yDown
		// - (int) ((mPercent + 0.2) * mIconHeight / 2), xDown
		// + (int) ((mPercent + 0.2) * mIconWidth / 2), yDown
		// + (int) ((mPercent + 0.2) * mIconHeight / 2));
	}

	private synchronized void setCurrentProgress(Canvas canvas) {
		mBgDrawable.draw(canvas);
		mBrushDrawable.draw(canvas);
	}

	/*
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float pressValue = event.getPressure();

		Log.d(TAG, "onTouchEvent, pressValue=" + pressValue);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = (int) event.getX();
			yDown = (int) event.getY();
			onClick(xDown, yDown);
			mIsExpand = true;
			mLongPressHelper.postCheckForLongPress();
			break;

		case MotionEvent.ACTION_MOVE:
			if (!pointInView(this, event.getX(), event.getY(), mSlop)) {
				mLongPressHelper.cancelLongPress();
			}

			Log.d(TAG, "ACTION_MOVE, pressure=" + event.getPressure());

			if (pressValue > 0.4f) {
				mIsExpand = false;
				Toast.makeText(mContext, "Good", Toast.LENGTH_LONG);
				mLongPressHelper.cancelLongPress();

				mHandler.sendEmptyMessage(MSG_MAIN_ANIMATION_EXPAND);

				if (!mIsVibrate) {
					Vibrator vib;
					vib = (Vibrator) mContext
							.getSystemService(Service.VIBRATOR_SERVICE);
					vib.vibrate(100);
					mIsVibrate = true;
				}
			} else {
				mHandler.sendEmptyMessage(MSG_MAIN_ANIMATION_EXPAND);

			}
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mIsExpand = false;
			mIsVibrate = false;
			mLongPressHelper.cancelLongPress();
			mHandler.removeMessages(MSG_MAIN_ANIMATION_EXPAND);
			mHandler.sendEmptyMessage(MSG_MAIN_ANIMATION_SHRINK);
			break;

		default:
			break;
		}

		return true;
	}
	*/

	public boolean pointInView(View v, float localX, float localY, float slop) {
		return localX >= -slop && localY >= -slop
				&& localX < (v.getWidth() + slop)
				&& localY < (v.getHeight() + slop);
	}
	
	public void setPressValue(float pressValue) {
		mPressValue = pressValue;
		invalidate();
	}

}
