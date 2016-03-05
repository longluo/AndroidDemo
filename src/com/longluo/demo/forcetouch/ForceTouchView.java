package com.longluo.demo.forcetouch;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.longluo.demo.R;

public class ForceTouchView extends View {
	private static final String TAG = "ForceTouchView";
	
	private static final int MSG_MAIN_ANIMATION = 0;
	private static final int MSG_ICON_ZOOM_OUT = 1;
	private static final int MSG_INIT_ICON_ZOOM_ = 2;
	private static final int MSG_INIT_ICON_ZOOM_IN= 3;
	private static final int MSG_ICON_ZOOM_FINISH = 4;
	private static final int MSG_ICON_PROGRESS = 5;
	
	Context mContext;
	
	private Paint mPaint1;
	private Paint mPaint2;
	
	int mRadius = 0;
	private int mRadius1 = 0;
	private int mRadius2 = 0;
	
	private int mAlpha = 0;
	
	private int t = 0;
	
	private int t1 = 0;
	private int t2 = 0;

	private int mTimes = 0;
	
	private int width = 5;

	private int xDown = 300;
	private int yDown = 610;

	private int screenX = 720;
	private int screenY = 1080;

	private boolean isExpand = false;

	private final int MAX_TIMES = 100;
	private final int MAX_ALPHA = 200;

	private final int ALPHA_SCALE = 4000;
	private final int DEVIDE = 6;
	private final int CYCLE = 10;
	private final int TURN = 10;
	private final int MAX_RADIUS = 415;

	private final int MAX_SMALL_RADIUS = 20;
	private final int INIT_SMALL_RADIUS = 420;

	private final double PI = 3.1415926;

	private Paint mRoundPaints;
	private RectF mRoundOval;
	private int mPaintColorDark;
	private int mPaintColor;

	private double mCurProgress;

	private int mMaxProgress;
	private int mIconWidth;
	private int mIconHeight;
	private int mIconWidthSmall;
	private int mIconHeightSmall;
	private int mIconWidthLarge;
	private int mIconHeightLarge;

	private Drawable mBgDrawable;
	private Drawable mBrushDrawable;

	final double INIT_PROGRESS = 37.5;
	final float START_ANGLE = -90;
	float mStartAngle = START_ANGLE;
	boolean mIsRunning = false;

	final double SINGLE_TURN_SPEED = 0.001;
	final double MIN_TURN_SPEED = 0.1;
	final double MAX_TURN_SPEED = 0.25;
	double mTurnSpeed = MAX_TURN_SPEED;

	final double INIT_PERCENT = 0.8;
	final double MIN_PERCENT = 0.7;
	final double SINGLE_TURN_PERCENT = 0.016;
	double mTurnPercent = SINGLE_TURN_PERCENT;
	double mPercent = INIT_PERCENT;

	final private int MAX_PROGRESS = 80;

	boolean cleanComplete = false;


	public ForceTouchView(Context context) {
		super(context);
		
	}

	public ForceTouchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		
	}

	public ForceTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mContext = context;

//		mAlpha = 0;
		mAlpha = 100;
		mRadius = 0;

		DisplayMetrics mDisplayMetrics = new DisplayMetrics();

		((ForceTouchDemoActivity) context).getWindowManager()
				.getDefaultDisplay().getMetrics(mDisplayMetrics);

		screenX = mDisplayMetrics.widthPixels;
		screenY = mDisplayMetrics.heightPixels;

		initPaint();
		initParam();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");

		if (isExpand) {
			expand(canvas);
		} else {
			shrink(canvas);
		}
	}

	public void onClick(int x, int y) {
		cleanComplete = false;

		xDown = x;
		yDown = y;

		mRadius = MAX_RADIUS;
		mRadius1 = 0;
		mRadius2 = 0;
		
		t1 = 0;
		t2 = 0;
		mTimes = 0;
		
//		mAlpha = 15;
		mAlpha = 100;
		
		initParam();

		mTurnPercent = 0 - SINGLE_TURN_PERCENT;
		mPercent = INIT_PERCENT;

		mHandler.sendEmptyMessage(3);
	}

	/** * 初始化paint */
	private void initPaint() {

		int colorGray = 0xB3ffffff;

		mPaint1 = new Paint();
		mPaint1.setAntiAlias(true);
		mPaint1.setStrokeWidth(10);
		mPaint1.setStyle(Paint.Style.FILL);
		mPaint1.setAlpha(mAlpha);
		mPaint1.setColor(Color.RED);
		
		mPaint2 = new Paint();
		mPaint2.setAntiAlias(true);
		mPaint2.setStrokeWidth(10);
		mPaint2.setStyle(Paint.Style.FILL);
		mPaint2.setAlpha(mAlpha);
		mPaint2.setColor(Color.BLUE);
		
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {

			case 0:// 实现主要动画
				flushState();
				invalidate();
				
				if (isExpand) {
					if (mAlpha != 0) {
						mHandler.sendEmptyMessageDelayed(0, 250);
					}
				} else {
					if ((mRadius1 + mRadius2) > 0) {
						mHandler.sendEmptyMessageDelayed(0, 150);
					} else {
						mTurnSpeed = MAX_TURN_SPEED;
						float total = mStartAngle % 360 - 1080;
						mStartAngle = total;
						mHandler.sendEmptyMessage(5);
					}
				}
				break;

			case 1:// 扩大图标
				Log.d(TAG, "Msg 1: mIconWidth=" + mIconWidth + ",mIconHeight=" + mIconHeight
						+ ",mIconWidthLarge=" + mIconWidthLarge);
				
				if (mIconWidth == mIconWidthLarge) {
					mIsRunning = true;
					mTurnSpeed = MIN_TURN_SPEED;
					mHandler.sendEmptyMessage(0);
				} else {
					mIconWidth++;
					mIconHeight++;

					initIconDrawableRect();
					invalidate();
					mHandler.sendEmptyMessage(1);
				}
				break;

			case 2:// 缩小图标
				Log.d(TAG, "Msg 2: mIconWidth=" + mIconWidth + ",mIconHeight=" + mIconHeight
						+ ",mIconWidthSmall=" + mIconWidthSmall);
				
				if (mIconWidth > mIconWidthSmall) {
					mIconWidth--;
					mIconHeight--;

					initIconDrawableRect();
					invalidate();
					mHandler.sendEmptyMessage(2);
				} else {
					mTurnPercent = 0 - SINGLE_TURN_PERCENT;
					mHandler.sendEmptyMessage(4);
				}
				break;

			case 3:// 起始缩放
				mPercent += mTurnPercent;
				
				Log.d(TAG, "Msg 3: mPercent=" + mPercent + ",mTurnPercent=" + mTurnPercent);
				
				initIconRoundRect();
				invalidate();
				if (mTurnPercent != SINGLE_TURN_PERCENT) {
					if (mPercent <= MIN_PERCENT) {
						mTurnPercent = SINGLE_TURN_PERCENT;
					}
					mHandler.sendEmptyMessage(3);
				} else {
					if (mPercent >= INIT_PERCENT) {
						mHandler.sendEmptyMessage(1);
					} else {
						mHandler.sendEmptyMessage(3);
					}
				}
				break;

			case 4: // 结束缩放
				Log.d(TAG, "Msg 4: mPercent=" + mPercent + ",mTurnPercent=" + mTurnPercent);
				
				mPercent += mTurnPercent;
				initIconRoundRect();
				invalidate();
				if (mTurnPercent != SINGLE_TURN_PERCENT) {
					if (mPercent <= MIN_PERCENT) {
						mTurnPercent = SINGLE_TURN_PERCENT;
					}
					mHandler.sendEmptyMessage(4);
				} else {
					if (mPercent < INIT_PERCENT) {
						mHandler.sendEmptyMessage(4);
					} else {
						// getContext().unregisterReceiver(mUpdateMemoryReceiver);
						// CleanMemoryView.this.setVisibility(View.GONE);
					}
				}
				break;

			case 5:
				Log.d(TAG, "Msg 5: mTurnSpeed=" + mTurnSpeed + ",mStartAngle=" + mStartAngle);
				
				if (mTurnSpeed > MIN_TURN_SPEED) {
					mTurnSpeed -= SINGLE_TURN_SPEED;
				}
				if (mCurProgress > INIT_PROGRESS) {
					mCurProgress -= mCurProgress / 20;
				} else {
					mCurProgress = INIT_PROGRESS;
				}

				invalidate();
				
				if (mStartAngle < START_ANGLE) {
					mHandler.sendEmptyMessageDelayed(5, 30);
				} else {
					mIsRunning = false;
					mStartAngle = START_ANGLE;
					mHandler.sendEmptyMessage(2);
				}
				break;

			default:
				break;
			}
		}

		/** * 刷新状态    */
		private void flushState() {
			// 起始增速，后半段减速
			if (mTimes < MAX_TIMES / 2 && mCurProgress < 80) {
				mCurProgress = INIT_PROGRESS + (int) (1.5 * mTimes); 
				if (mTurnSpeed < MAX_TURN_SPEED) {
					mTurnSpeed += SINGLE_TURN_SPEED;
				}
			}

			if (mTimes < CYCLE) {
				mRadius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				t1++;
			} else if (mTimes < CYCLE * 2) {
				mRadius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				t1++;
				t2++;
			} else if (mTimes < MAX_TIMES && !cleanComplete) {
				t1 = mTimes % (CYCLE * 2);
				if (t1 < CYCLE) {
					t2 = t1 + CYCLE * 3;
				} else if (t1 < (CYCLE * 2)) {
					t2 = t1 - CYCLE;
				} 

				mRadius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				mRadius2 = MAX_RADIUS - (t2 + TURN) * (t2 + TURN) / DEVIDE;
			} else {
				t1++;
				t2++;

				if (t1 > CYCLE * 2) {
					mRadius1 = 0;
				} else {
					mRadius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				}
				if (t2 > CYCLE * 2) {
					mRadius2 = 0;
				} else {
					mRadius2 = MAX_RADIUS - (t2 + TURN) * (t2 + TURN) / DEVIDE;
				}
			}

			if (mRadius1 > 0) {
//				mAlpha = (t1 > 2 * CYCLE ? 2 * CYCLE : t1) / 2;
				mAlpha = 100;
				mPaint1.setAlpha(mAlpha);
			}
			if (mRadius2 > 0) {
//				mAlpha = (t2 > 2 * CYCLE ? 2 * CYCLE : t2) / 2;
				mAlpha = 100;
				mPaint2.setAlpha(mAlpha);
			}

			mTimes++;
		}
	};

	void drawLarge(Canvas canvas, int r, Paint p) {
		if (r > 0) {
//			drawArt(canvas, xDown, yDown, r, SECTOR_TOP_LEFT, p);
//			drawArt(canvas, xDown, yDown, r, SECTOR_TOP_RIGHT, p);
//			drawArt(canvas, xDown, yDown, r, SECTOR_BOTTOM_LEFT, p);
//			drawArt(canvas, xDown, yDown, r, SECTOR_BOTTOM_RIGHT, p);
//			drawRect(canvas, xDown, yDown, r, RECT_TOP, p);
//			drawRect(canvas, xDown, yDown, r, RECT_MIDDLE, p);
//			drawRect(canvas, xDown, yDown, r, RECT_BOTTOM, p);
			
			RectF mRect = new RectF();
			mRect.left = xDown - (16 * r) / 6;
			mRect.top = yDown - (16 * r) / 6;
			mRect.right = xDown + (16 * r) / 6;
			mRect.bottom = yDown + (16 * r) / 6;
			
			canvas.drawRoundRect(mRect, r, r, p);
		}
	}

	void shrink(Canvas canvas) {
//		drawLarge(canvas, mRadius1, mPaint1);
		drawLarge(canvas, mRadius2, mPaint2);

		// 画图标
		setCurrentProgress(canvas, mCurProgress);
	}
	
	void expand(Canvas canvas) {
		drawLarge(canvas, mRadius1, mPaint1);
		drawLarge(canvas, mRadius2, mPaint2);

		// 画图标
		setCurrentProgress(canvas, mCurProgress);
	}
	

	private float mProgress;

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

	private void initParam() {
		mPaintColorDark = 0xff7c644c;// 0xff482400;
		mPaintColor = 0xffc4b592;// 0xffb08068;

		if (mRoundPaints == null) {
			mRoundPaints = new Paint();
			mRoundPaints.setAntiAlias(true);
			mRoundPaints.setStyle(Paint.Style.STROKE);

			mRoundPaints.setStrokeWidth(5);
		}

		mStartAngle = START_ANGLE;
		mCurProgress = INIT_PROGRESS;
		mMaxProgress = 100;
		mRoundPaints.setColor(mPaintColorDark);

		if (mRoundOval == null) {
			mRoundOval = new RectF(xDown, yDown, xDown, yDown);
		}

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

		mRoundOval.set(xDown - (int) (mPercent * mIconWidth / 2), yDown
				- (int) (mPercent * mIconHeight / 2), xDown
				+ (int) (mPercent * mIconWidth / 2), yDown
				+ (int) (mPercent * mIconHeight / 2));
	}

	void initIconRoundRect() {
		mBrushDrawable.setBounds(xDown
				- (int) ((mPercent + 0.2) * mIconWidth / 2), yDown
				- (int) ((mPercent + 0.2) * mIconHeight / 2), xDown
				+ (int) ((mPercent + 0.2) * mIconWidth / 2), yDown
				+ (int) ((mPercent + 0.2) * mIconHeight / 2));

		mRoundOval.set(xDown - (int) (mPercent * mIconWidth / 2), yDown
				- (int) (mPercent * mIconHeight / 2), xDown
				+ (int) (mPercent * mIconWidth / 2), yDown
				+ (int) (mPercent * mIconHeight / 2));
	}

	private synchronized void setCurrentProgress(Canvas canvas, double progress) {
		mBgDrawable.draw(canvas);
		mBrushDrawable.draw(canvas);
		
		float rate = (float) progress / mMaxProgress;
		float sweep = 360 * rate;
		
		Log.d(TAG, "setCurrentProgress, progress=" + progress + ",mStartAngle=" + mStartAngle);

		if (mIsRunning) {
			if (mTurnSpeed > MAX_TURN_SPEED) {
				mTurnSpeed = MAX_TURN_SPEED;
			}
			if (mStartAngle >= START_ANGLE) {
				mStartAngle += (int) (sweep * mTurnSpeed);
			} else {
				mStartAngle += (int) (sweep * mTurnSpeed) / 2;
				if (mStartAngle > START_ANGLE) {
					mStartAngle = START_ANGLE;
				}
			}
		} else {
			mStartAngle = START_ANGLE;
		}

		mRoundPaints.setColor(mPaintColorDark);
		canvas.drawArc(mRoundOval, mStartAngle, sweep, false, mRoundPaints);
		mRoundPaints.setColor(mPaintColor);
		canvas.drawArc(mRoundOval, mStartAngle + sweep, 360 - sweep, false,
				mRoundPaints);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (isExpand) {
				mRadius = 0;
				mAlpha = MAX_ALPHA;
				xDown = (int) event.getX();
				yDown = (int) event.getY();
				onClick(xDown, yDown);
			} else {
				xDown = (int) event.getX();
				yDown = (int) event.getY();
				onClick(xDown, yDown);
			}
			break;

		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
			break;

		default:
			break;
		}

		return true;
	}

}
