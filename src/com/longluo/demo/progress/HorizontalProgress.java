package com.longluo.demo.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

public class HorizontalProgress extends View {
	// 静态圆个个数
	private int mStaticCicleCount = 5;
	private Circle[] mStaticCircles;
	/**
	 * 画笔
	 */
	private Paint mPaint = new Paint();

	/**
	 * 路径
	 */
	private Path mPath = new Path();
	/**
	 * 动态圆
	 */
	private Circle mDynamicCircle = new Circle();
	private float radius = 30;
	private float distance = 3 * radius;
	/**
	 * 宽度
	 */
	private int mWidth;
	/**
	 * 高度
	 */
	private int mHeight;
	/**
	 * 最大粘连长度
	 */
	private float mMaxAdherentLength = 3.5f * radius;
	/**
	 * 静态圆变化半径的最大比率
	 */
	private float mMaxStaticCircleRadiusScaleRate = 0.4f;
	/**
	 * 当前的静态圆半径
	 */
	private float mCurrentStaticCircleRadius = 10f;

	public HorizontalProgress(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public HorizontalProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HorizontalProgress(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		mWidth = (int) ((mStaticCicleCount + 2) * 2 * radius + (mStaticCicleCount + 1)
				* distance);
		mHeight = (int) (2 * radius * (1 + mMaxStaticCircleRadiusScaleRate));

		/* 动态圆 */
		mDynamicCircle.radius = radius * 3 / 4;
		;
		mDynamicCircle.x = radius;
		mDynamicCircle.y = mHeight / 2;

		/* 静态圆 */
		mStaticCircles = new Circle[mStaticCicleCount];
		for (int i = 0; i < mStaticCicleCount; i++) {
			mStaticCircles[i] = new Circle();
			mStaticCircles[i].x = radius + (i + 1) * (2 * radius + distance);
			mStaticCircles[i].y = mHeight / 2;
			mStaticCircles[i].radius = radius;
		}

		/* 开始动画 */
		startAnim();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawStaticCircle(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(
				resolveSizeAndState(mWidth, widthMeasureSpec,
						MeasureSpec.UNSPECIFIED),
				resolveSizeAndState(mHeight, heightMeasureSpec,
						MeasureSpec.UNSPECIFIED));
	}

	private void drawStaticCircle(Canvas canvas) {
		/* 动态圆 */
		canvas.drawCircle(mDynamicCircle.x, mDynamicCircle.y,
				mDynamicCircle.radius, mPaint);

		for (int i = 0; i < mStaticCicleCount; i++) {
			Circle circle = mStaticCircles[i];
			if (doAdhere(circle)) {
				canvas.drawCircle(circle.x, circle.y,
						mCurrentStaticCircleRadius, mPaint);
				Path path = drawAdherentBody(mDynamicCircle.x,
						mDynamicCircle.y, mDynamicCircle.radius, 45f, circle.x,
						circle.y, circle.radius, 45f);
				canvas.drawPath(path, mPaint);
			}
			canvas.drawCircle(circle.x, circle.y, circle.radius, mPaint);
		}
	}

	/**
	 * 判断粘连范围，动态改变静态圆大小
	 * 
	 * @param position
	 * @return
	 */
	private boolean doAdhere(Circle circle) {
		/* 半径变化 */
		float distance = (float) Math.sqrt(Math.pow(
				mDynamicCircle.x - circle.x, 2)
				+ Math.pow(mDynamicCircle.y - circle.y, 2));// Math.abs(mDynamicCircle.x
															// - circle.x);
		float scale = mMaxStaticCircleRadiusScaleRate
				- mMaxStaticCircleRadiusScaleRate
				* (distance / mMaxAdherentLength);
		mCurrentStaticCircleRadius = circle.radius * (1 + scale);
		/* 判断是否可以作贝塞尔曲线 */
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

	/**
	 * 开始动画
	 */
	private void startAnim() {
		/* 动态圆的x坐标动画 */
		ValueAnimator xValueAnimator = ValueAnimator.ofFloat(mDynamicCircle.x,
				mWidth - radius);
		xValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		xValueAnimator.setDuration(2500);
		xValueAnimator.setRepeatCount(Animation.INFINITE);
		xValueAnimator.setRepeatMode(Animation.REVERSE);
		xValueAnimator.start();
		xValueAnimator
				.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						mDynamicCircle.x = (float) (Float) animation
								.getAnimatedValue();
						invalidate();
					}
				});
	}

	private class Circle {
		public float x;
		public float y;
		public float radius;
	}
}
