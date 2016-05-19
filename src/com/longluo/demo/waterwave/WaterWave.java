package com.longluo.demo.waterwave;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
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

public class WaterWave extends View {
	
	private static final String TAG = "WaterWave";

	// 大动画参数
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

	int t1 = 0;
	int t2 = 0;
	int t3 = 0;
	int t4 = 0;

	// 小方框动画参数
	Paint paintA;
	Paint paintB;
	Paint paintC;

	int radiusA = 0;
	int radiusB = 0;
	int radiusC = 0;

	int alphaA = 0;
	int alphaB = 0;
	int alphaC = 0;

	int times = 0;
	
	int width = 5;

	int xDown = 300;
	int yDown = 610;

	int screenX = 720;
	int screenY = 1080;

	boolean isExpand = false;

	final int MAX_TIMES = 100;
	final int MAX_ALPHA = 200;

	final int ALPHA_SCALE = 4000;
	final int DEVIDE = 6;
	final int CYCLE = 10;
	final int TURN = 10;
	final int MAX_RADIUS = 415;

	final int MAX_SMALL_RADIUS = 20;
	final int INIT_SMALL_RADIUS = 420;

	final double pai = 3.1416;

	// x = r*sin(DIRECTION_A); y = r*cos(DIRECTION_A);
	double DIRECTION_A;
	double DIRECTION_B;
	double DIRECTION_C;

	double SIN_A;
	double SIN_B;
	double SIN_C;
	
	double COS_A;
	double COS_B;
	double COS_C;

	Context mContext;

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

	private long mTotalMemory;
	private long mAvailMemory;
	private int mProgressBegin;
	private int mProgressEnd;
	final private int MAX_PROGRESS = 80;

	boolean cleanComplete = false;

	public WaterWave(Context context) {
		super(context);

	}

	public WaterWave(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public WaterWave(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mContext = context;

		alpha = 0;
		radius = 0;

		DisplayMetrics mDisplayMetrics = new DisplayMetrics();

		((WaterWaveDemoActivity) context).getWindowManager()
				.getDefaultDisplay().getMetrics(mDisplayMetrics);

		screenX = mDisplayMetrics.widthPixels;
		screenY = mDisplayMetrics.heightPixels;

		initPaint();
		initParam();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (isExpand) {
			// expand(canvas);

		} else {
			shrink(canvas);
		}
	}

	public void onClick(int x, int y) {
		cleanComplete = false;

		xDown = x;
		yDown = y;

		radius = MAX_RADIUS;
		
		radius1 = 0;
		radius2 = 0;
		radius3 = 0;
		radius4 = 0;

		t1 = 0;
		t2 = 0;
		t3 = 0;
		t4 = 0;
		times = 0;

		radiusA = 0;
		radiusB = 0;
		radiusC = 0;

		alphaA = 0;
		alphaB = 0;
		alphaC = 0;

		alpha = 15;

		if (xDown < 7 * screenX / 24 && yDown < screenY / 3) {
			DIRECTION_A = 3 * pai / 4;
			DIRECTION_B = 11 * pai / 12;
			DIRECTION_C = 7 * pai / 12;
		} else if (xDown < 7 * screenX / 24 && yDown <= 2 * screenY / 3) {
			DIRECTION_A = pai / 4;
			DIRECTION_B = 11 * pai / 12;
			DIRECTION_C = 7 * pai / 12;
		} else if (xDown < 7 * screenX / 24 && yDown > 2 * screenY / 3) {
			DIRECTION_A = pai / 4;
			DIRECTION_B = pai / 12;
			DIRECTION_C = 5 * pai / 12;
		} else if (xDown <= 17 * screenX / 24 && yDown < screenY / 3) {
			DIRECTION_A = 3 * pai / 4;
			DIRECTION_B = 13 * pai / 12;
			DIRECTION_C = 17 * pai / 12;
		} else if (xDown <= 17 * screenX / 24 && yDown <= 2 * screenY / 3) {
			DIRECTION_A = 7 * pai / 4;
			DIRECTION_B = 5 * pai / 4;
			DIRECTION_C = 5 * pai / 6;
		} else if (xDown <= 17 * screenX / 24 && yDown > 2 * screenY / 3) {
			DIRECTION_A = 7 * pai / 4;
			DIRECTION_B = pai / 12;
			DIRECTION_C = 5 * pai / 12;
		} else if (xDown > 17 * screenX / 24 && yDown < screenY / 3) {
			DIRECTION_A = 5 * pai / 4;
			DIRECTION_B = 13 * pai / 12;
			DIRECTION_C = 11 * pai / 8;
		} else if (xDown > 17 * screenX / 24 && yDown <= 2 * screenY / 3) {
			DIRECTION_A = 11 * pai / 8;
			DIRECTION_B = 7 * pai / 4;
			DIRECTION_C = 5 * pai / 4;
		} else if (xDown > 17 * screenX / 24 && yDown > 2 * screenY / 3) {
			DIRECTION_A = 7 * pai / 4;
			DIRECTION_B = 19 * pai / 12;
			DIRECTION_C = 11 * pai / 6;
		}

		SIN_A = Math.sin(DIRECTION_A);
		SIN_B = Math.sin(DIRECTION_B);
		SIN_C = Math.sin(DIRECTION_C);

		COS_A = Math.cos(DIRECTION_A);
		COS_B = Math.cos(DIRECTION_B);
		COS_C = Math.cos(DIRECTION_C);

		initParam();

		mTurnPercent = 0 - SINGLE_TURN_PERCENT;
		mPercent = INIT_PERCENT;

		handler.sendEmptyMessage(3);

		/*
		 * float rate = (float)mEndProgress / mMaxProgress; mInitSweep = 360 *
		 * rate; mStartAngle = START_ANGLE; startBackAnimation(mEndProgress, 0,
		 * 1.0f, new onAnimateBackCallBack() {
		 * 
		 * @Override public void onAnimateStart() {
		 * 
		 * }
		 * 
		 * @Override public void onAnimateEnd() { mAvailMemory =
		 * ImproveProcessLogic.getAvailMemory(getContext()); int percent =
		 * (int)((float)(mTotalMemory - mAvailMemory) * 100/ mTotalMemory);
		 * startAdvanceAnimation(0, percent, 1.0f); } });
		 */
	}

	/** * 初始化paint */
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

		// 新建画笔A
		paintA = new Paint();
		paintA.setAntiAlias(true);
		paintA.setStrokeWidth(width);

		// 初始化画笔 A
		paintA.setStyle(Paint.Style.FILL);// .STROKE);
		paintA.setAlpha(alpha);
		paintA.setColor(colorGray);

		// 新建画笔B
		paintB = new Paint();
		paintB.setAntiAlias(true);
		paintB.setStrokeWidth(width);

		// 初始化画笔 B
		paintB.setStyle(Paint.Style.FILL);// .STROKE);
		paintB.setAlpha(alpha);
		paintB.setColor(colorGray);

		// 新建画笔C
		paintC = new Paint();
		paintC.setAntiAlias(true);
		paintC.setStrokeWidth(width);

		// 初始化画笔 C
		paintC.setStyle(Paint.Style.FILL);// .STROKE);
		paintC.setAlpha(alpha);
		paintC.setColor(colorGray);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {

			case 0:// 实现主要动画
				flushState();
				invalidate();
				
				if (isExpand) {
					if (alpha != 0) {
						// 如果透明度没有到0，则继续刷新，否則停止刷新
						handler.sendEmptyMessageDelayed(0, 50);
					}
				} else {
					// 如果adius1、2、3还大于0，则继续刷新，否則停止刷新
					// if (mStartAngle >= START_ANGLE) {
					if ((radius1 + radius2 + radius3 + radius4) > 0) {
						handler.sendEmptyMessageDelayed(0, 30);
					} else {
						mTurnSpeed = MAX_TURN_SPEED;
						float total = mStartAngle % 360 - 1080;
						mStartAngle = total;
						handler.sendEmptyMessage(5);
					}
				}
				break;

			case 1:// 扩大图标
				if (mIconWidth == mIconWidthLarge) {
					mIsRunning = true;
					mTurnSpeed = MIN_TURN_SPEED;
					handler.sendEmptyMessage(0);
					// handler.sendEmptyMessageDelayed(0, 15);
				} else {
					mIconWidth++;
					mIconHeight++;

					initIconDrawableRect();
					invalidate();
					handler.sendEmptyMessage(1);
					;
				}
				break;

			case 2:// 缩小图标
				if (mIconWidth > mIconWidthSmall) {
					mIconWidth--;
					mIconHeight--;

					initIconDrawableRect();
					invalidate();
					handler.sendEmptyMessage(2);
				} else {
					mTurnPercent = 0 - SINGLE_TURN_PERCENT;
					handler.sendEmptyMessage(4);
				}
				break;

			case 3:// 起始缩放
				mPercent += mTurnPercent;
				initIconRoundRect();
				invalidate();
				if (mTurnPercent != SINGLE_TURN_PERCENT) {
					if (mPercent <= MIN_PERCENT) {
						mTurnPercent = SINGLE_TURN_PERCENT;
					}
					handler.sendEmptyMessage(3);
				} else {
					if (mPercent >= INIT_PERCENT) {
						handler.sendEmptyMessage(1);
					} else {
						handler.sendEmptyMessage(3);
					}
				}
				break;

			case 4: // 结束缩放
				mPercent += mTurnPercent;
				initIconRoundRect();
				invalidate();
				if (mTurnPercent != SINGLE_TURN_PERCENT) {
					if (mPercent <= MIN_PERCENT) {
						mTurnPercent = SINGLE_TURN_PERCENT;
					}
					handler.sendEmptyMessage(4);
				} else {
					if (mPercent < INIT_PERCENT) {
						handler.sendEmptyMessage(4);
					} else {
						// getContext().unregisterReceiver(mUpdateMemoryReceiver);
						// CleanMemoryView.this.setVisibility(View.GONE);
					}
				}
				break;

			case 5:
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
					handler.sendEmptyMessageDelayed(5, 30);
				} else {
					mIsRunning = false;
					mStartAngle = START_ANGLE;
					handler.sendEmptyMessage(2);
				}
				break;

			default:
				break;
			}
		}

		/** * 刷新状态    */
		private void flushState() {
			// 起始增速，后半段减速
			if (times < MAX_TIMES / 2 && mCurProgress < 80) {
				mCurProgress = INIT_PROGRESS + (int) (1.5 * times); // (100*times)/MAX_TIMES;
				if (mTurnSpeed < MAX_TURN_SPEED) {
					mTurnSpeed += SINGLE_TURN_SPEED;
				}
			}

			// 小框距离点击位置大于50，则继续缩小
			if (radiusA > 50) {
				radiusA -= 8;
			}
			if (radiusB > 50) {
				radiusB -= 8;
			}
			if (radiusC > 50) {
				radiusC -= 8;
			}

			if (MAX_TIMES - times == 70) {
				radiusA = INIT_SMALL_RADIUS;
			}
			if (MAX_TIMES - times == 40) {
				radiusB = INIT_SMALL_RADIUS;
			}
			if (MAX_TIMES - times == 20) {
				radiusC = INIT_SMALL_RADIUS;
			}

			if (times < CYCLE) {
				radius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				t1++;
			} else if (times < CYCLE * 2) {
				radius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				radius2 = MAX_RADIUS - (t2 + TURN) * (t2 + TURN) / DEVIDE;
				t1++;
				t2++;
			} else if (times < CYCLE * 3) {
				radius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				radius2 = MAX_RADIUS - (t2 + TURN) * (t2 + TURN) / DEVIDE;
				radius3 = MAX_RADIUS - (t3 + TURN) * (t3 + TURN) / DEVIDE;
				t1++;
				t2++;
				t3++;
			} else if (times < CYCLE * 4) {
				radius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				radius2 = MAX_RADIUS - (t2 + TURN) * (t2 + TURN) / DEVIDE;
				radius3 = MAX_RADIUS - (t3 + TURN) * (t3 + TURN) / DEVIDE;
				radius4 = MAX_RADIUS - (t4 + TURN) * (t4 + TURN) / DEVIDE;
				t1++;
				t2++;
				t3++;
				t4++;
			} else if (times < MAX_TIMES && !cleanComplete) {

				t1 = times % (CYCLE * 4);
				if (t1 < CYCLE) {
					t2 = t1 + CYCLE * 3;
					t3 = t2 - CYCLE;
					t4 = t3 - CYCLE;
				} else if (t1 < (CYCLE * 2)) {
					t2 = t1 - CYCLE;
					t3 = t1 + CYCLE * 2;
					t4 = t3 - CYCLE;
				} else if (t1 < (CYCLE * 3)) {
					t2 = t1 - CYCLE;
					t3 = t2 - CYCLE;
					t4 = t1 + CYCLE;
				} else {
					t2 = t1 - CYCLE;
					t3 = t2 - CYCLE;
					t4 = t3 - CYCLE;
				}

				radius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				radius2 = MAX_RADIUS - (t2 + TURN) * (t2 + TURN) / DEVIDE;
				radius3 = MAX_RADIUS - (t3 + TURN) * (t3 + TURN) / DEVIDE;
				radius4 = MAX_RADIUS - (t4 + TURN) * (t4 + TURN) / DEVIDE;

			} else {
				t1++;
				t2++;
				t3++;
				t4++;

				if (t1 > CYCLE * 4) {
					radius1 = 0;
				} else {
					radius1 = MAX_RADIUS - (t1 + TURN) * (t1 + TURN) / DEVIDE;
				}
				if (t2 > CYCLE * 4) {
					radius2 = 0;
				} else {
					radius2 = MAX_RADIUS - (t2 + TURN) * (t2 + TURN) / DEVIDE;
				}
				if (t3 > CYCLE * 4) {
					radius3 = 0;
				} else {
					radius3 = MAX_RADIUS - (t3 + TURN) * (t3 + TURN) / DEVIDE;
				}

				if (t4 > CYCLE * 4) {
					radius4 = 0;
				} else {
					radius4 = MAX_RADIUS - (t4 + TURN) * (t4 + TURN) / DEVIDE;
				}
			}

			if (radius1 > 0) {
				alpha = (t1 > 4 * CYCLE ? 4 * CYCLE : t1) / 2;
				paint1.setAlpha(alpha);
			}
			if (radius2 > 0) {
				alpha = (t2 > 4 * CYCLE ? 4 * CYCLE : t2) / 2;
				paint2.setAlpha(alpha);
			}
			if (radius3 > 0) {
				alpha = (t3 > 4 * CYCLE ? 4 * CYCLE : t3) / 2;
				paint3.setAlpha(alpha);
			}
			if (radius4 > 0) {
				alpha = (t4 > 4 * CYCLE ? 4 * CYCLE : t4) / 2;
				paint4.setAlpha(alpha);
			}

			if (radiusA > 50) {
				alphaA = 50 * MAX_ALPHA / radiusA;
				if (alphaA > 100) {
					alphaA = 100;
				} else if (alphaA < 20) {
					alphaA = 20;
				}
				paintA.setAlpha(alphaA);
			}
			if (radiusB > 50) {
				alphaB = 50 * MAX_ALPHA / radiusB;
				if (alphaB > 100) {
					alphaB = 100;
				} else if (alphaB < 20) {
					alphaB = 20;
				}
				paintB.setAlpha(alphaB);
			}
			if (radiusC > 50) {
				alphaC = 50 * MAX_ALPHA / radiusC;
				if (alphaC > 100) {
					alphaC = 100;
				} else if (alphaC < 20) {
					alphaC = 20;
				}
				paintC.setAlpha(alphaC);
			}

			times++;
		}
	};

	void drawLarge(Canvas canvas, int r, Paint p) {
		if (r > 0) {
			drawArt(canvas, xDown, yDown, r, SECTOR_TOP_LEFT, p);
			drawArt(canvas, xDown, yDown, r, SECTOR_TOP_RIGHT, p);
			drawArt(canvas, xDown, yDown, r, SECTOR_BOTTOM_LEFT, p);
			drawArt(canvas, xDown, yDown, r, SECTOR_BOTTOM_RIGHT, p);

			drawRect(canvas, xDown, yDown, r, RECT_TOP, p);
			drawRect(canvas, xDown, yDown, r, RECT_MIDDLE, p);
			drawRect(canvas, xDown, yDown, r, RECT_BOTTOM, p);
		}
	}

	void drawSmall(Canvas canvas, int x, int y, int r, Paint p) {
		if (r > 0) {
			drawArt(canvas, x, y, r, SECTOR_TOP_LEFT, p);
			drawArt(canvas, x, y, r, SECTOR_TOP_RIGHT, p);
			drawArt(canvas, x, y, r, SECTOR_BOTTOM_LEFT, p);
			drawArt(canvas, x, y, r, SECTOR_BOTTOM_RIGHT, p);

			drawRect(canvas, x, y, r, RECT_TOP, p);
			drawRect(canvas, x, y, r, RECT_MIDDLE, p);
			drawRect(canvas, x, y, r, RECT_BOTTOM, p);
		}
	}

	void shrink(Canvas canvas) {
		drawLarge(canvas, radius1, paint1);
		drawLarge(canvas, radius2, paint2);
		drawLarge(canvas, radius3, paint3);
		drawLarge(canvas, radius4, paint4);
		
		Log.d(TAG, "0=" + radius + ",1=" + radius1 + ",2=" + radius2 + ",3=" + radius3 + ",4=" + radius4
				+ ",A=" + radiusA + ",B=" + radiusB + ",C=" + radiusC);

		if (radiusA > 50) {
			int r = radiusA / 40;
			int x = (int) (radiusA * SIN_A) + xDown;
			int y = yDown - (int) (radiusA * COS_A);
			
			// 小框圆角半径最小为3
			if (r < 3) {
				r = 3;
			}
			
			drawSmall(canvas, x, y, r, paintA);
		}
		
		if (radiusB > 50) {
			int r = radiusB / 40;
			int x = (int) (radiusB * SIN_B) + xDown;
			int y = yDown - (int) (radiusB * COS_B);
			if (r < 3) {
				r = 3;
			}
			drawSmall(canvas, x, y, r, paintB);
		}
		
		if (radiusC > 50) {
			int r = radiusC / 40;
			int x = (int) (radiusC * SIN_C) + xDown;
			int y = yDown - (int) (radiusC * COS_C);
			if (r < 3) {
				r = 3;
			}
			drawSmall(canvas, x, y, r, paintC);
		}

		// 画图标
		setCurrentProgress(canvas, mCurProgress);
	}

	float mProgress;

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
				radius = 0;
				alpha = MAX_ALPHA;
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
