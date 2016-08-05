package com.longluo.demo.analogclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Clock extends View {
    private final static String TAG = "mytest";

    private int mHeight, mWidth;

    private Time mTime;

    public Clock(Context context) {
        this(context, null);
    }

    public Clock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTime = new Time();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mTime.setToNow();

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
        float secRot = mTime.second * 6;
        float minRot = mTime.minute * 6 + 6 * mTime.second / 60F;
        float hrRot = (mTime.hour % 12) * 30 + 30 * minRot / 360F;

        Log.d(TAG, "hrRot=" + hrRot + ",minRot=" + minRot + ",secRot=" + secRot);

        // 画指针
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
        Paint paintSecond = new Paint();
        paintSecond.setStrokeWidth(5);

        canvas.save();

        canvas.rotate(hrRot, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 320,
                paintHour);
        canvas.rotate(-hrRot, mWidth / 2, mHeight / 2);

        canvas.rotate(minRot, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 230,
                paintMinute);
        canvas.rotate(-minRot, mWidth / 2, mHeight / 2);

        canvas.rotate(secRot, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 150,
                paintSecond);
        canvas.rotate(-secRot, mWidth / 2, mHeight / 2);

        canvas.restore();
    }


    public void refreshClock() {
        postInvalidate();
    }

}
