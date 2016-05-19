package com.longluo.demo.progress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class SkipProgress extends View {
	/**
	 * 宽度
	 */
	private int mWidth;

	/**
	 * 高度
	 */
	private int mHeight;
	/**
	 * 画笔
	 */
	private Paint mPaint = new Paint();
	/**
	 * 小圆半径
	 */
	private float mWallCircleRadius = 40;
	// 小圆
	private Circle[] mWallCircles;
	// 小圆数目
	private int mWallCircleCount = 4;
	// 小圆间距
	private int mDistance = 20;
	// 上升高度
	private int mHigh = 200;

	public SkipProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public SkipProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SkipProgress(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		mWidth = (int) ((mWallCircleCount - 1) * mDistance + 2
				* mWallCircleCount * mWallCircleRadius);
		mHeight = (int) (mHigh + 2 * mWallCircleRadius);

		mWallCircles = new Circle[mWallCircleCount];
		for (int i = 0; i < mWallCircleCount; i++) {
			Circle circle = new Circle();
			circle.y = mHeight - mWallCircleRadius;
			circle.x = mWallCircleRadius + i
					* (2 * mWallCircleRadius + mDistance);
			circle.radius = mWallCircleRadius;
			mWallCircles[i] = circle;
		}

		/* 开始动画 */
		startAnim();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(
				resolveSizeAndState(mWidth, widthMeasureSpec,
						MeasureSpec.UNSPECIFIED),
				resolveSizeAndState(mHeight, heightMeasureSpec,
						MeasureSpec.UNSPECIFIED));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < mWallCircleCount; i++) {
			canvas.drawCircle(mWallCircles[i].x, mWallCircles[i].y,
					mWallCircles[i].radius, mPaint);
		}
	}

	private void startAnim() {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				startUpAnim(0);
			}
		}, 500);
	}

	private void startUpAnim(final int pos) {
		final Circle circle = mWallCircles[pos];
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(mHeight
				- mWallCircleRadius, mWallCircleRadius);
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		valueAnimator.setDuration(500);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float y = (Float) animation.getAnimatedValue();
				circle.y = y;
				invalidate();
			}
		});
		valueAnimator.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				startDownAnim(pos);
			}
		});
		valueAnimator.start();
	}

	private void startDownAnim(final int pos) {
		if (pos + 1 < mWallCircleCount)
			startUpAnim(pos + 1);
		final Circle circle = mWallCircles[pos];
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(mWallCircleRadius,
				mHeight - mWallCircleRadius);
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		valueAnimator.setDuration(500);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float y = (Float) animation.getAnimatedValue();
				circle.y = y;
				invalidate();
			}
		});
		valueAnimator.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				if (pos + 1 == mWallCircleCount)
					startUpAnim(0);
			}
		});
		valueAnimator.start();
	}

	private class Circle {
		public float x;
		public float y;
		public float radius;
	}
}
