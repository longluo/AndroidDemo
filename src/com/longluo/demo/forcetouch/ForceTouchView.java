package com.longluo.demo.forcetouch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.longluo.demo.R;

public class ForceTouchView extends View {

	private Context mContext;
	
	private int mScreenWidth;
	private int mScreenHeight;

	private Bitmap mRippleBitmap;
	
	private Paint mRipplePaint = new Paint();

	private int mBitmapWidth;
	private int mBitmapHeight;

	private boolean isStartRipple;

	private Rect mRect;

	private int rippleRadius = 0;

	private Paint mTextPaint = new Paint();
	private String mText = "Click Me";

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			invalidate();

			if (isStartRipple) {
				rippleRadius++;
				if (rippleRadius > 100) {
					rippleRadius = 100;
				}
				
				sendEmptyMessageDelayed(0, 50);
			}
		}
	};

	public ForceTouchView(Context context) {
		super(context);

		init();
	}

	public ForceTouchView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	public ForceTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		init();
	}

	private void init() {
		mRipplePaint.setColor(Color.RED);
		mRipplePaint.setAntiAlias(true);
		mRipplePaint.setStyle(Paint.Style.FILL);

		mTextPaint.setTextSize(30);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setColor(Color.WHITE);

		mRippleBitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.clear_background));
		
		mBitmapWidth = mRippleBitmap.getWidth();
		mBitmapHeight = mRippleBitmap.getHeight();
		
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		setMeasuredDimension(720, 1280);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (isStartRipple) {
			float f = 3 * mBitmapHeight / 10;
			
			mRipplePaint.setAlpha(255);
			
			int i = (int) (220.0F - (220.0F - 0.0F) / 100.0F
					* rippleRadius);
			
			mRipplePaint.setAlpha(i);
			
			mRipplePaint.setColor(Color.GREEN);
			
			canvas.drawRoundRect(100, 100, 100 + mBitmapWidth, 200 + mBitmapHeight, 7
					* mBitmapHeight / 10 + f * rippleRadius / 100.0F, 7
					* mBitmapHeight / 10 + f * rippleRadius / 100.0F, mRipplePaint);
		}
		
		mRipplePaint.setColor(Color.BLUE);
		mRipplePaint.setAlpha(255);
		
		mRect = new Rect(100, 100, 100 + mRippleBitmap.getWidth(), 100 + mRippleBitmap.getHeight());
		canvas.drawBitmap(mRippleBitmap, mRect, mRect, mRipplePaint);
		
		float length = mTextPaint.measureText(mText);
		canvas.drawText(mText, 50, 70, mTextPaint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		invalidate();
	}

	public void startRipple() {
		isStartRipple = true;
		mHandler.sendEmptyMessage(0);
	}

	public void stopRipple() {
		isStartRipple = false;
		mHandler.removeMessages(0);
	}

}
