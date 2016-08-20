package com.longluo.demo.animateclock;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class AnimateClock extends View {
    private final static String TAG = "mytest";

    private int mHeight, mWidth;

    private Time mTime;

    protected static final float DEFAULT_HOUR_DEGREE;
    protected static final float DEFAULT_MINUTE_DEGREE;
    protected static final float DEFAULT_SECOND_DEGREE;

    protected Calendar mCalendar;

    protected static final int MSG_UPDATE_TIME = 0x101;

    Handler mHandler = new Handler() {

    };

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 9);
        calendar.set(Calendar.SECOND, 25);
        DEFAULT_HOUR_DEGREE = TimeUtil.calculateHourDegree(calendar);
        DEFAULT_MINUTE_DEGREE = TimeUtil.calculateMinuteDegree(calendar);
        DEFAULT_SECOND_DEGREE = TimeUtil.calculateSecondDegree(calendar);
    }

    private boolean mIsAnimating;
    private float mAnimDegree;

    private ObjectAnimator mPointerAnimator;
    private AnimatorController mController;


    public AnimateClock(Context context) {
        this(context, null);
    }

    public AnimateClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public AnimateClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTime = new Time();

        mCalendar = Calendar.getInstance();


        mPointerAnimator = ObjectAnimator.ofFloat(this, "degree", 0.0f, 360.0f);
        mPointerAnimator.setInterpolator(new DegreeInterpolator());
        mPointerAnimator.setDuration(3000);
        mPointerAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, "onAnimationStart");
                }
                mHandler.removeMessages(MSG_UPDATE_TIME);
                mCalendar.setTimeInMillis(System.currentTimeMillis());
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, "onAnimationEnd");
                }
                mHandler.sendEmptyMessage(MSG_UPDATE_TIME);
                mIsAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, "onAnimationCancel");
                }
                mIsAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mController = new AnimatorController();
        mController.setAnimUpdateListener(new AnimatorController.AnimUpdateListener() {
            @Override
            public void onAnimStart() {
                mHandler.removeMessages(MSG_UPDATE_TIME);

            }

            @Override
            public void onAnimUpdate() {
                invalidate();

            }

            @Override
            public void onAnimEnd() {
                mHandler.sendEmptyMessage(MSG_UPDATE_TIME);

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 获取宽高参数
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        // 画外圆
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(5);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paintCircle);

        // 画刻度
        Paint painDegree = new Paint();
        paintCircle.setStrokeWidth(3);

        for (int i = 0; i < 24; i++) {
            // 区分整点与非整点
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                painDegree.setStrokeWidth(5);
                painDegree.setTextSize(30);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 60,
                        painDegree);
                String degree = String.valueOf(i);
                canvas.drawText(degree,
                        mWidth / 2 - painDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 90,
                        painDegree);
            } else {
                painDegree.setStrokeWidth(3);
                painDegree.setTextSize(15);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 30,
                        painDegree);

                String degree = String.valueOf(i);

                canvas.drawText(degree,
                        mWidth / 2 - painDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 60,
                        painDegree);
            }

            // 通过旋转画布简化坐标运算
            canvas.rotate(15, mWidth / 2, mHeight / 2);
        }

        // 画圆心
        Paint paintPointer = new Paint();
        paintPointer.setStrokeWidth(30);
        canvas.drawPoint(mWidth / 2, mHeight / 2, paintPointer);

        drawTime(canvas);
    }

    private void drawTime(Canvas canvas) {
        // 当前时间
        mTime.setToNow();

        // 当前时间对应的角度
        float secRot = mTime.second * 6;
        float minRot = mTime.minute * 6 + 6 * mTime.second / 60F;
        float hrRot = (mTime.hour % 12) * 30 + 30 * minRot / 360F;

        Log.d(TAG, "hr=" + mTime.hour + ",min=" + mTime.minute + ",sec=" + mTime.second);
        Log.d(TAG, "hrRot=" + hrRot + ",minRot=" + minRot + ",secRot=" + secRot);

        // 画指针
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
        Paint paintSecond = new Paint();
        paintSecond.setStrokeWidth(5);

        canvas.save();

        // 时针
        canvas.rotate(hrRot, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 320,
                paintHour);
        canvas.rotate(-hrRot, mWidth / 2, mHeight / 2);

        // 分针
        canvas.rotate(minRot, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 230,
                paintMinute);
        canvas.rotate(-minRot, mWidth / 2, mHeight / 2);

        // 秒针
        canvas.rotate(secRot, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 150,
                paintSecond);
        canvas.rotate(-secRot, mWidth / 2, mHeight / 2);

        canvas.restore();
    }


}
