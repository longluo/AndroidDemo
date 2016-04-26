package com.longluo.demo.adherent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;

import com.longluo.demo.R;

/**
 * Created by luolong on 2015/12/31.
 */
public class AdherentLayout extends RelativeLayout {
    private Circle mHeaderCircle = new Circle();
    private Circle mFooterCircle = new Circle();

    // 画笔
    private Paint mPaint = new Paint();
    // 画贝塞尔曲线的Path对象
    private Path mPath = new Path();
    // 粘连的颜色
    private int mColor = Color.rgb(247, 82, 49);
    // 是否粘连着
    private boolean isAdherent = true;
    // 本View初始宽度、高度
    private int mOriginalWidth;
    private int mOriginalHeight;
    // 是否第一次onSizeChanged
    private boolean isFirst = true;
    // 用户添加的视图（可以不添加）
    private View mView;
    // 是否正在进行动画中
    private boolean isAnim = false;
    // 记录按下的x、y
    float mDownX;
    float mDownY;
    // 本View的左上角x、y
    private float mX;
    private float mY;
    // 父控件左、上内边距
    private float mParentPaddingLeft;
    private float mParentPaddingTop;
    // 默认粘连的最大长度
    private float mMaxAdherentLength = 1000;
    // 头部圆缩小时不能小于这个最小半径
    private float mMinHeaderCircleRadius = 4;
    // 是否允许可以扯断
    private boolean isDismissed = true;
    // 是否按下
    boolean isDown = false;

    /**
     * 监听粘连是否断掉的监听器
     */
    private OnAdherentListener mOnAdherentListener = new OnAdherentListener() {

        @Override
        public void onDismiss() {
            if (mView == null) {
                return;
            }

            final Drawable old = mView.getBackground();
            mView.setBackgroundResource(R.drawable.tip_anim);
            // 烟雾动画
            AnimationDrawable animationDrawable = ((AnimationDrawable) mView.getBackground());
            animationDrawable.stop();
            animationDrawable.start();

            int duration = 0;
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                duration += animationDrawable.getDuration(i);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    reset();
//                    mView.setBackground(old);
                    mView.setBackgroundDrawable(old);
                }
            }, duration);
        }
    };

    public AdherentLayout(Context context) {
        super(context);
        init();
    }

    public AdherentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdherentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 透明背景
        setBackgroundColor(Color.TRANSPARENT);
        // 设置画笔
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isFirst && w > 0 && h > 0) {
            mView = getChildAt(0);
            // 记录初始宽高，用于复原
            mOriginalWidth = w;
            mOriginalHeight = h;
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
            mX = getX() - lp.leftMargin;
            mY = getY() - lp.topMargin;
            ViewGroup mViewGroup = (ViewGroup) getParent();
            if (mViewGroup != null) {
                mParentPaddingLeft = mViewGroup.getPaddingLeft();
                mParentPaddingTop = mViewGroup.getPaddingTop();
            }
            reset();
            isFirst = false;
        }
    }

    /**
     * 重置所有参数
     */
    public void reset() {
        setWidthAndHeight(mOriginalWidth, mOriginalHeight);
        mHeaderCircle.curRadius = mFooterCircle.curRadius = mHeaderCircle.originalRadius = mFooterCircle.originalRadius = getRadius();
        mFooterCircle.ox = mFooterCircle.curx = mHeaderCircle.ox = mHeaderCircle.curx = mOriginalWidth / 2;
        mFooterCircle.oy = mFooterCircle.cury = mHeaderCircle.oy = mHeaderCircle.cury = mOriginalHeight / 2;
        if (mView != null) {
            if (isFirst) {
                mView.setX(0);
                mView.setY(0);
            } else {
                mView.setX(getPaddingLeft());
                mView.setY(getPaddingTop());
            }
        }
        isAnim = false;
    }

    /**
     * 根据内边距返回圆的半径
     *
     * @return
     */
    private float getRadius() {
        return (float) (Math.min(
                Math.min(mOriginalWidth / 2 - getPaddingLeft(), mOriginalWidth
                        / 2 - getPaddingRight()),
                Math.min(mOriginalHeight / 2 - getPaddingTop(), mOriginalHeight
                        / 2 - getPaddingBottom())) - 2);
    }

    /**
     * 设置宽和高
     *
     * @param width
     * @param height
     */
    private void setWidthAndHeight(int width, int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        setLayoutParams(layoutParams);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawCircle(mHeaderCircle.curx, mHeaderCircle.cury,
                mHeaderCircle.curRadius, mPaint);
        canvas.drawCircle(mFooterCircle.curx, mFooterCircle.cury,
                mFooterCircle.curRadius, mPaint);
        if (isAdherent) {
            drawBezier(canvas);
        }
    }

    /**
     * 画贝塞尔曲线
     *
     * @param canvas
     */
    private void drawBezier(Canvas canvas) {
        /* 求三角函数 */
        float atan = (float) Math
                .atan((mFooterCircle.cury - mHeaderCircle.cury)
                        / (mFooterCircle.curx - mHeaderCircle.curx));
        float sin = (float) Math.sin(atan);
        float cos = (float) Math.cos(atan);

		/* 四个点 */
        float headerX1 = mHeaderCircle.curx - mHeaderCircle.curRadius * sin;
        float headerY1 = mHeaderCircle.cury + mHeaderCircle.curRadius * cos;

        float headerX2 = mHeaderCircle.curx + mHeaderCircle.curRadius * sin;
        float headerY2 = mHeaderCircle.cury - mHeaderCircle.curRadius * cos;

        float footerX1 = mFooterCircle.curx - mFooterCircle.curRadius * sin;
        float footerY1 = mFooterCircle.cury + mFooterCircle.curRadius * cos;

        float footerX2 = mFooterCircle.curx + mFooterCircle.curRadius * sin;
        float footerY2 = mFooterCircle.cury - mFooterCircle.curRadius * cos;

        float anchorX = (mHeaderCircle.curx + mFooterCircle.curx) / 2;
        float anchorY = (mHeaderCircle.cury + mFooterCircle.cury) / 2;

		/* 画贝塞尔曲线 */
        mPath.reset();
        mPath.moveTo(headerX1, headerY1);
        mPath.quadTo(anchorX, anchorY, footerX1, footerY1);
        mPath.lineTo(footerX2, footerY2);
        mPath.quadTo(anchorX, anchorY, headerX2, headerY2);
        mPath.lineTo(headerX1, headerY1);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (isAnim) {
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setWidthAndHeight(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                // 设置成MATCH_PARENT后，会重复计算一次父控件padding，所以在这里要减去
                mFooterCircle.ox = mFooterCircle.curx = mHeaderCircle.ox = mHeaderCircle.curx = mX
                        + mOriginalWidth / 2 - mParentPaddingLeft;
                mFooterCircle.oy = mFooterCircle.cury = mHeaderCircle.oy = mHeaderCircle.cury = mY
                        + mOriginalHeight / 2 - mParentPaddingTop;
                if (mView != null) {
                    mView.setX(mX + getPaddingLeft() - mParentPaddingLeft);
                    mView.setY(mY + getPaddingTop() - mParentPaddingTop);
                }
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                // 标记按下
                isDown = true;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isDown) {
                    break;
                }

                // 偏移
                float detalX = event.getRawX() - mDownX;
                float detalY = event.getRawY() - mDownY;

                mFooterCircle.curx = mFooterCircle.ox + detalX;
                mFooterCircle.cury = mFooterCircle.oy + detalY;
                if (mView != null) {
                    mView.setX(mX + getPaddingLeft() + detalX - mParentPaddingLeft);
                    mView.setY(mY + getPaddingTop() + detalY - mParentPaddingTop);
                }
                doAdhere();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 如果没有按下，直接返回，这是为了防止多指触控
                if (!isDown) {
                    break;
                }
                isDown = false;
                isAnim = true;
                if (isAdherent) {
                    startAnim();
                } else if (mOnAdherentListener != null) {
                    mFooterCircle.curRadius = 0;
                    mOnAdherentListener.onDismiss();
                }
                break;
        }

        invalidate();
        return true;
    }

    /**
     * 处理粘连效果逻辑
     */
    private void doAdhere() {
        // 两圆心的距离
        float distance = (float) Math.sqrt(Math.pow(mFooterCircle.curx
                - mHeaderCircle.ox, 2)
                + Math.pow(mFooterCircle.cury - mHeaderCircle.oy, 2));

        // 缩放比例
        float scale = 1 - distance / mMaxAdherentLength;
        mHeaderCircle.curRadius = Math.max(
                mHeaderCircle.originalRadius * scale, mMinHeaderCircleRadius);
        if (distance > mMaxAdherentLength && isDismissed) {
            isAdherent = false;
            mHeaderCircle.curRadius = 0;
        } else {
            isAdherent = true;
        }
    }

    /**
     * 开始粘连动画
     */
    private void startAnim() {

		/* x方向 */
        ValueAnimator xValueAnimator = ValueAnimator.ofFloat(
                mFooterCircle.curx, mFooterCircle.ox);
        xValueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFooterCircle.curx = (float) (Float) animation
                                .getAnimatedValue();
                        invalidate();
                    }
                });

		/* y方向 */
        ValueAnimator yValueAnimator = ValueAnimator.ofFloat(
                mFooterCircle.cury, mFooterCircle.oy);
        yValueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFooterCircle.cury = (float) (Float) animation
                                .getAnimatedValue();
                        invalidate();
                    }
                });

		/* 用户添加的视图x、y方向 */
        ObjectAnimator objectAnimator = null;
        if (mView != null) {
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("X",
                    mFooterCircle.curx - mFooterCircle.curRadius
                            - getPaddingLeft(), mX + getPaddingLeft()
                            - mParentPaddingLeft);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("Y",
                    mFooterCircle.cury - mFooterCircle.curRadius
                            - getPaddingTop(), mY + getPaddingTop()
                            - mParentPaddingTop);
            objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mView, pvhX,
                    pvhY);
        }

		/* 动画集合 */
        AnimatorSet animSet = new AnimatorSet();
        if (mView != null) {
            animSet.playTogether(xValueAnimator, yValueAnimator, objectAnimator);
        } else {
            animSet.playTogether(xValueAnimator, yValueAnimator);
        }

        animSet.setInterpolator(new BounceInterpolator());
        animSet.setDuration(400);
        animSet.start();
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reset();
            }
        });
    }

    /**
     * 圆点类
     *
     * @author Administrator
     */
    private class Circle {
        /**
         * 初始坐标x,y
         */
        float ox;
        float oy;
        /**
         * 当前坐标x,y
         */
        float curx;
        float cury;
        // 初始半径
        float originalRadius;
        // 当前半径
        float curRadius;
    }

    /**
     * 对外接口：设置监听器
     *
     * @param onAdherentListener
     */
    public void setOnAdherentListener(OnAdherentListener onAdherentListener) {
        mOnAdherentListener = onAdherentListener;
    }

    /**
     * 监听器
     */
    public interface OnAdherentListener {
        public void onDismiss();
    }

    /**
     * 对外接口：设置颜色
     *
     * @param color
     */
    public void setColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
    }

    /**
     * 对外接口：设置是否可以扯断
     */
    public void setDismissedEnable(boolean isDismissed) {
        this.isDismissed = isDismissed;
    }

    /**
     * 对外接口：设置粘连的最大长度
     *
     * @param maxAdherentLength
     */
    public void setMaxAdherentLength(int maxAdherentLength) {
        mMaxAdherentLength = maxAdherentLength;
    }

    /**
     * 对外接口：设置头部的最小半径
     *
     * @param minHeaderCircleRadius
     */
    public void setMinHeaderCircleRadius(int minHeaderCircleRadius) {
        mMinHeaderCircleRadius = minHeaderCircleRadius;
    }

}
