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

public class SwapProgress extends View {
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

	public SwapProgress(Context context) {
		super(context);
		init();
	}

	public SwapProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SwapProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		
		mWidth = (int) ((mWallCircleCount - 1) * mDistance + 2
				* mWallCircleCount * mWallCircleRadius);
		mHeight = (int) (mDistance + 4 * mWallCircleRadius);

		mWallCircles = new Circle[mWallCircleCount];
		
		for (int i = 0; i < mWallCircleCount; i++) {
			Circle circle = new Circle();
			circle.y = mHeight / 2;
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
				startSwapAnim(0);
			}
		}, 500);
	}

	private void startSwapAnim(final int pos) {
		if (pos == mWallCircleCount - 1) {
			startSwapAnim(0);
			return;
		}
		final Circle circle1 = mWallCircles[pos];
		final Circle circle2 = mWallCircles[pos + 1];
		final float centerX = (circle1.x + circle2.x) / 2;
		final float centerY = (circle1.y + circle2.y) / 2;
		final float radius = (circle2.x - circle1.x) / 2;
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(-180, 0);
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		valueAnimator.setDuration(1000);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float angle = (Float) animation.getAnimatedValue();
				circle1.x = (float) (centerX + radius
						* Math.cos(Math.toRadians(angle)));
				circle1.y = (float) (centerY + radius
						* Math.sin(Math.toRadians(angle)));
				circle2.x = (float) (centerX + radius
						* Math.cos(Math.toRadians(180 + angle)));
				circle2.y = (float) (centerY + radius
						* Math.sin(Math.toRadians(180 + angle)));
				invalidate();
			}
		});
		valueAnimator.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				// 真实交换两者在数组中的位置
				mWallCircles[pos] = circle2;
				mWallCircles[pos + 1] = circle1;
				startSwapAnim(pos + 1);
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
