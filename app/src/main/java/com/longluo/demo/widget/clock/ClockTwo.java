package com.longluo.demo.widget.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;


public class ClockTwo extends View {

    private final static String TAG = "clock2";

    private int mHeight, mWidth;

    private Calendar mCalendar;

    public ClockTwo(Context context) {
        this(context, null);
    }

    public ClockTwo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ClockTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCalendar = Calendar.getInstance();

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
        // 画指针
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
        Paint paintSecond = new Paint();
        paintSecond.setStrokeWidth(5);

        canvas.save();

        float secRot = mCalendar.get(Calendar.SECOND) / 30f * (float) Math.PI;
        float minRot = mCalendar.get(Calendar.MINUTE) / 30f * (float) Math.PI;
        float hrRot = 360.0f * (mCalendar.get(Calendar.HOUR_OF_DAY) + mCalendar.get(Calendar.MINUTE) / 60.0f) / 12.0f;

        Log.d(TAG, "hrRot=" + hrRot + ",minRot=" + minRot + ",secRot=" + secRot);

        float centerX = mWidth / 2f;
        float centerY = mHeight / 2f;

        float secLength = centerX - 60;
        float minLength = centerX - 80;
        float hrLength = centerX - 100;

        float hrX = (float) Math.sin(hrRot) * hrLength;
        float hrY = (float) -Math.cos(hrRot) * hrLength;
        canvas.drawLine(centerX, centerY, centerX + hrX, centerY + hrY, paintHour);

        float minX = (float) Math.sin(minRot) * minLength;
        float minY = (float) -Math.cos(minRot) * minLength;
        canvas.drawLine(centerX, centerY, centerX + minX, centerY + minY, paintMinute);

        float secX = (float) Math.sin(secRot) * secLength;
        float secY = (float) -Math.cos(secRot) * secLength;
        canvas.drawLine(centerX, centerY, centerX + secX, centerY + secY, paintSecond);

        canvas.restore();
    }


    public void refreshClock() {
        postInvalidate();
    }
}
