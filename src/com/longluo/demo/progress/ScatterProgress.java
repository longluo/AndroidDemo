package com.longluo.demo.progress;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class ScatterProgress extends View {
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
	 * 中间圆和抽象大圆变化半径的最大比率
	 */
	private float mMaxCircleRadiusScaleRate = 0.4f;
	/**
	 * 小圆半径
	 */
	private float mWallCircleRadius = 30;
	/**
	 * 中圆半径
	 */
	private float mCenterCircleRadius = 150;
	/**
	 * 中间大圆
	 */
	private Circle mCenterCircle;

	private int mWallCircleCount = 8;

	private Circle[] mWallCircles;

	private float mMaxAdherentLength;
	/**
	 * 中间球动画
	 */
	private ValueAnimator mCenterCircleAnimator;

	public ScatterProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public ScatterProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ScatterProgress(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		mWidth = mHeight = (int) (2 * (mWallCircleRadius + mCenterCircleRadius
				* (1 + mMaxCircleRadiusScaleRate)
				* (1 + mMaxCircleRadiusScaleRate)));
		mMaxAdherentLength = mWallCircleRadius + mCenterCircleRadius
				* (1 + mMaxCircleRadiusScaleRate)
				* (1 + mMaxCircleRadiusScaleRate / 2);

		mCenterCircle = new Circle();
		mCenterCircle.radius = mCenterCircleRadius
				* (1 + mMaxCircleRadiusScaleRate);
		mCenterCircle.x = mWidth / 2;
		mCenterCircle.y = mHeight / 2;

		mWallCircles = new Circle[mWallCircleCount];
		for (int i = 0; i < mWallCircleCount; i++) {
			Circle circle = new Circle();
			circle.angle = 360 / mWallCircleCount * i;
			circle.x = mWidth / 2;
			circle.y = mHeight / 2;
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
		// 中间圆
		canvas.drawCircle(mCenterCircle.x, mCenterCircle.y,
				mCenterCircle.radius, mPaint);
		// 小圆
		for (int i = 0; i < mWallCircleCount; i++) {
			Circle circle = mWallCircles[i];
			if (doAdhere(circle)) {
				Path path = drawAdherentBody(mCenterCircle.x, mCenterCircle.y,
						mCenterCircle.radius, 20f, circle.x, circle.y,
						circle.radius, 45f);
				canvas.drawPath(path, mPaint);
			}
			canvas.drawCircle(circle.x, circle.y, circle.radius, mPaint);
		}
	}

	private void startAnim() {
		startCenterCirclesAnim();
		startWallCirclesAnim();
	}

	ArrayList<Animator> animators1 = new ArrayList<Animator>();
	ArrayList<Animator> animators2 = new ArrayList<Animator>();

	public void startWallCirclesAnim() {
		AnimatorSet set = new AnimatorSet();
		AnimatorSet set1 = new AnimatorSet();
		AnimatorSet set2 = new AnimatorSet();
		animators1.clear();
		animators2.clear();
		/* 小圆坐标动画 */
		for (int i = 0; i < mWallCircleCount; i++) {
			// 半径
			final Circle wallCircle = mWallCircles[i];
			ValueAnimator valueAnimator = ValueAnimator.ofFloat(
					mWallCircleRadius, mWidth / 2 - mWallCircleRadius);
			valueAnimator
					.setInterpolator(new AccelerateDecelerateInterpolator());
			valueAnimator.setDuration(400);
			valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float radius = (Float) animation.getAnimatedValue();
					wallCircle.bigRadius = radius;
				}
			});
			animators1.add(valueAnimator);
			// 位置
			ValueAnimator XYAnimator = ValueAnimator.ofFloat(wallCircle.angle,
					wallCircle.angle + 360);
			XYAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
			XYAnimator.setDuration(3600);
			XYAnimator.start();
			XYAnimator
					.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float angle = (Float) animation.getAnimatedValue();
							wallCircle.x = (float) (mCenterCircle.x + wallCircle.bigRadius
									* Math.cos(Math.toRadians(angle)));
							wallCircle.y = (float) (mCenterCircle.y + wallCircle.bigRadius
									* Math.sin(Math.toRadians(angle)));
							invalidate();
						}
					});
			animators2.add(XYAnimator);
		}
		set1.playSequentially(animators1);
		set2.playTogether(animators2);

		set.playTogether(set1, set2);
		set.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				startWallCirclesAnimRecovery();
				startCenterCirclesAnimRecovery();
			}
		});
		set.start();
	}

	public void startWallCirclesAnimRecovery() {
		AnimatorSet set = new AnimatorSet();
		AnimatorSet set1 = new AnimatorSet();
		AnimatorSet set2 = new AnimatorSet();
		animators1.clear();
		animators2.clear();
		/* 小圆坐标动画 */
		for (int i = 0; i < mWallCircleCount; i++) {
			// 半径
			final Circle wallCircle = mWallCircles[i];
			ValueAnimator valueAnimator = ValueAnimator.ofFloat(mWidth / 2
					- mWallCircleRadius, mWallCircleRadius);
			valueAnimator
					.setInterpolator(new AccelerateDecelerateInterpolator());
			valueAnimator.setDuration(400);
			valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float radius = (Float) animation.getAnimatedValue();
					wallCircle.bigRadius = radius;
				}
			});
			animators1.add(valueAnimator);
			// 位置
			ValueAnimator XYAnimator = ValueAnimator.ofFloat(
					wallCircle.angle + 360, wallCircle.angle);
			XYAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
			XYAnimator.setDuration(3600);
			XYAnimator.start();
			XYAnimator
					.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float angle = (Float) animation.getAnimatedValue();
							wallCircle.x = (float) (mCenterCircle.x + wallCircle.bigRadius
									* Math.cos(Math.toRadians(angle)));
							wallCircle.y = (float) (mCenterCircle.y + wallCircle.bigRadius
									* Math.sin(Math.toRadians(angle)));
							invalidate();
						}
					});
			animators2.add(XYAnimator);
		}
		set1.playSequentially(animators1);
		set2.playTogether(animators2);

		set.playTogether(set1, set2);
		set.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				startWallCirclesAnim();
				startCenterCirclesAnim();
			}
		});
		set.start();
	}

	public void startCenterCirclesAnimRecovery() {
		mCenterCircleAnimator = ValueAnimator.ofFloat(mCenterCircleRadius,
				mCenterCircleRadius * (1 + mMaxCircleRadiusScaleRate));
		mCenterCircleAnimator
				.setInterpolator(new AccelerateDecelerateInterpolator());
		mCenterCircleAnimator.setDuration(3600);
		mCenterCircleAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float radius = (Float) animation.getAnimatedValue();
				mCenterCircle.radius = radius;
				invalidate();
			}
		});
		mCenterCircleAnimator.start();
	}

	public void startCenterCirclesAnim() {
		mCenterCircleAnimator = ValueAnimator.ofFloat(mCenterCircleRadius
				* (1 + mMaxCircleRadiusScaleRate), mCenterCircleRadius);
		mCenterCircleAnimator
				.setInterpolator(new AccelerateDecelerateInterpolator());
		mCenterCircleAnimator.setDuration(3600);
		mCenterCircleAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float radius = (Float) animation.getAnimatedValue();
				mCenterCircle.radius = radius;
				invalidate();
			}
		});
		mCenterCircleAnimator.start();
	}

	private class Circle {
		public float x;
		public float y;
		public float radius;
		public float angle;
		public float bigRadius;
	}

	/**
	 * 判断粘连范围，动态改变静态圆大小
	 * 
	 * @param position
	 * @return
	 */
	private boolean doAdhere(Circle circle) {
		float distance = (float) Math.sqrt(Math.pow(mCenterCircle.x - circle.x,
				2) + Math.pow(mCenterCircle.y - circle.y, 2));// Math.abs(mDynamicCircle.x
																// - circle.x);
		if (distance < mMaxAdherentLength)
			return true;
		else
			return false;
	}

	/**
	 * 画粘连体（通用方法）
	 * 
	 * @param cx1
	 *            圆心x1
	 * @param cy1
	 *            圆心y1
	 * @param r1
	 *            圆半径r1
	 * @param offset1
	 *            贝塞尔曲线偏移角度offset1
	 * @param cx2
	 *            圆心x2
	 * @param cy2
	 *            圆心y2
	 * @param r2
	 *            圆半径r2
	 * @param offset2
	 *            贝塞尔曲线偏移角度offset2
	 * @return
	 */
	private Path drawAdherentBody(float cx1, float cy1, float r1,
			float offset1, float cx2, float cy2, float r2, float offset2) {

		/* 求三角函数 */
		float degrees = (float) Math.toDegrees(Math.atan(Math.abs(cy2 - cy1)
				/ Math.abs(cx2 - cx1)));

		/* 根据圆1与圆2的相对位置求四个点 */
		float differenceX = cx1 - cx2;
		float differenceY = cy1 - cy2;

		/* 两条贝塞尔曲线的四个端点 */
		float x1, y1, x2, y2, x3, y3, x4, y4;

		/* 圆1在圆2的下边 */
		if (differenceX == 0 && differenceY > 0) {
			x2 = cx2 - r2 * (float) Math.sin(Math.toRadians(offset2));
			y2 = cy2 + r2 * (float) Math.cos(Math.toRadians(offset2));
			x4 = cx2 + r2 * (float) Math.sin(Math.toRadians(offset2));
			y4 = cy2 + r2 * (float) Math.cos(Math.toRadians(offset2));
			x1 = cx1 - r1 * (float) Math.sin(Math.toRadians(offset1));
			y1 = cy1 - r1 * (float) Math.cos(Math.toRadians(offset1));
			x3 = cx1 + r1 * (float) Math.sin(Math.toRadians(offset1));
			y3 = cy1 - r1 * (float) Math.cos(Math.toRadians(offset1));

		}
		/* 圆1在圆2的上边 */
		else if (differenceX == 0 && differenceY < 0) {
			x2 = cx2 - r2 * (float) Math.sin(Math.toRadians(offset2));
			y2 = cy2 - r2 * (float) Math.cos(Math.toRadians(offset2));
			x4 = cx2 + r2 * (float) Math.sin(Math.toRadians(offset2));
			y4 = cy2 - r2 * (float) Math.cos(Math.toRadians(offset2));
			x1 = cx1 - r1 * (float) Math.sin(Math.toRadians(offset1));
			y1 = cy1 + r1 * (float) Math.cos(Math.toRadians(offset1));
			x3 = cx1 + r1 * (float) Math.sin(Math.toRadians(offset1));
			y3 = cy1 + r1 * (float) Math.cos(Math.toRadians(offset1));

		}
		/* 圆1在圆2的右边 */
		else if (differenceX > 0 && differenceY == 0) {
			x2 = cx2 + r2 * (float) Math.cos(Math.toRadians(offset2));
			y2 = cy2 + r2 * (float) Math.sin(Math.toRadians(offset2));
			x4 = cx2 + r2 * (float) Math.cos(Math.toRadians(offset2));
			y4 = cy2 - r2 * (float) Math.sin(Math.toRadians(offset2));
			x1 = cx1 - r1 * (float) Math.cos(Math.toRadians(offset1));
			y1 = cy1 + r1 * (float) Math.sin(Math.toRadians(offset1));
			x3 = cx1 - r1 * (float) Math.cos(Math.toRadians(offset1));
			y3 = cy1 - r1 * (float) Math.sin(Math.toRadians(offset1));
		}
		/* 圆1在圆2的左边 */
		else if (differenceX < 0 && differenceY == 0) {
			x2 = cx2 - r2 * (float) Math.cos(Math.toRadians(offset2));
			y2 = cy2 + r2 * (float) Math.sin(Math.toRadians(offset2));
			x4 = cx2 - r2 * (float) Math.cos(Math.toRadians(offset2));
			y4 = cy2 - r2 * (float) Math.sin(Math.toRadians(offset2));
			x1 = cx1 + r1 * (float) Math.cos(Math.toRadians(offset1));
			y1 = cy1 + r1 * (float) Math.sin(Math.toRadians(offset1));
			x3 = cx1 + r1 * (float) Math.cos(Math.toRadians(offset1));
			y3 = cy1 - r1 * (float) Math.sin(Math.toRadians(offset1));
		}
		/* 圆1在圆2的右下角 */
		else if (differenceX > 0 && differenceY > 0) {
			x2 = cx2 - r2
					* (float) Math.cos(Math.toRadians(180 - offset2 - degrees));
			y2 = cy2 + r2
					* (float) Math.sin(Math.toRadians(180 - offset2 - degrees));
			x4 = cx2 + r2 * (float) Math.cos(Math.toRadians(degrees - offset2));
			y4 = cy2 + r2 * (float) Math.sin(Math.toRadians(degrees - offset2));
			x1 = cx1 - r1 * (float) Math.cos(Math.toRadians(degrees - offset1));
			y1 = cy1 - r1 * (float) Math.sin(Math.toRadians(degrees - offset1));
			x3 = cx1 + r1
					* (float) Math.cos(Math.toRadians(180 - offset1 - degrees));
			y3 = cy1 - r1
					* (float) Math.sin(Math.toRadians(180 - offset1 - degrees));
		}
		/* 圆1在圆2的左上角 */
		else if (differenceX < 0 && differenceY < 0) {
			x2 = cx2 - r2 * (float) Math.cos(Math.toRadians(degrees - offset2));
			y2 = cy2 - r2 * (float) Math.sin(Math.toRadians(degrees - offset2));
			x4 = cx2 + r2
					* (float) Math.cos(Math.toRadians(180 - offset2 - degrees));
			y4 = cy2 - r2
					* (float) Math.sin(Math.toRadians(180 - offset2 - degrees));
			x1 = cx1 - r1
					* (float) Math.cos(Math.toRadians(180 - offset1 - degrees));
			y1 = cy1 + r1
					* (float) Math.sin(Math.toRadians(180 - offset1 - degrees));
			x3 = cx1 + r1 * (float) Math.cos(Math.toRadians(degrees - offset1));
			y3 = cy1 + r1 * (float) Math.sin(Math.toRadians(degrees - offset1));
		}
		/* 圆1在圆2的左下角 */
		else if (differenceX < 0 && differenceY > 0) {
			x2 = cx2 - r2 * (float) Math.cos(Math.toRadians(degrees - offset2));
			y2 = cy2 + r2 * (float) Math.sin(Math.toRadians(degrees - offset2));
			x4 = cx2 + r2
					* (float) Math.cos(Math.toRadians(180 - offset2 - degrees));
			y4 = cy2 + r2
					* (float) Math.sin(Math.toRadians(180 - offset2 - degrees));
			x1 = cx1 - r1
					* (float) Math.cos(Math.toRadians(180 - offset1 - degrees));
			y1 = cy1 - r1
					* (float) Math.sin(Math.toRadians(180 - offset1 - degrees));
			x3 = cx1 + r1 * (float) Math.cos(Math.toRadians(degrees - offset1));
			y3 = cy1 - r1 * (float) Math.sin(Math.toRadians(degrees - offset1));
		}
		/* 圆1在圆2的右上角 */
		else {
			x2 = cx2 - r2
					* (float) Math.cos(Math.toRadians(180 - offset2 - degrees));
			y2 = cy2 - r2
					* (float) Math.sin(Math.toRadians(180 - offset2 - degrees));
			x4 = cx2 + r2 * (float) Math.cos(Math.toRadians(degrees - offset2));
			y4 = cy2 - r2 * (float) Math.sin(Math.toRadians(degrees - offset2));
			x1 = cx1 - r1 * (float) Math.cos(Math.toRadians(degrees - offset1));
			y1 = cy1 + r1 * (float) Math.sin(Math.toRadians(degrees - offset1));
			x3 = cx1 + r1
					* (float) Math.cos(Math.toRadians(180 - offset1 - degrees));
			y3 = cy1 + r1
					* (float) Math.sin(Math.toRadians(180 - offset1 - degrees));
		}

		/* 贝塞尔曲线的控制点 */
		float anchorX1, anchorY1, anchorX2, anchorY2;

		/* 圆1大于圆2 */
		if (r1 > r2) {
			anchorX1 = (x2 + x3) / 2;
			anchorY1 = (y2 + y3) / 2;
			anchorX2 = (x1 + x4) / 2;
			anchorY2 = (y1 + y4) / 2;
		}
		/* 圆1小于或等于圆2 */
		else {
			anchorX1 = (x1 + x4) / 2;
			anchorY1 = (y1 + y4) / 2;
			anchorX2 = (x2 + x3) / 2;
			anchorY2 = (y2 + y3) / 2;
		}

		/* 画粘连体 */
		Path path = new Path();
		path.reset();
		path.moveTo(x1, y1);
		path.quadTo(anchorX1, anchorY1, x2, y2);
		path.lineTo(x4, y4);
		path.quadTo(anchorX2, anchorY2, x3, y3);
		path.lineTo(x1, y1);

		return path;
	}

}
