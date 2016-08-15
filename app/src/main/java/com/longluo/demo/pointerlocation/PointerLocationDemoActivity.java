package com.longluo.demo.pointerlocation;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by luolong on 2016/8/15.
 */
public class PointerLocationDemoActivity extends Activity {

    private static final int MSG_FAIL = 1;

    private static final int MSG_PASS = 2;

    private static final int MSG_DELAY = 1000;

    private static final String TAG = "mytest";

    private int numberOfLine = 1;

    /**
     * 中间线XY坐标误差范围
     */
    private static final int XY_RANGE_MIDDLE = 40;

    /**
     * 边缘线XY坐标误差范围
     */
    private static final int XY_RANGE_MARGIN = 20;

    WindowManager.LayoutParams params;

    Window window;


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FAIL:
                    Toast.makeText(PointerLocationDemoActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    break;

                case MSG_PASS:
                    Toast.makeText(PointerLocationDemoActivity.this, "Pass", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        window = getWindow();
        params = window.getAttributes();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        params.screenBrightness = 1.0f;
        Log.d(TAG, "params.systemUiVisibility = " + params.systemUiVisibility);
        window.setAttributes(params);

        setContentView(new MyView(this));

        showHintDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    public static class PointerState {
        private final ArrayList<Float> mXs = new ArrayList<Float>();

        private final ArrayList<Float> mYs = new ArrayList<Float>();

        private boolean mCurDown;

        private int mCurX;

        private int mCurY;

        private float mCurPressure;

        private float mCurSize;

        private int mCurWidth;

        private VelocityTracker mVelocity;
    }

    public class MyView extends View {
        private final ViewConfiguration mVC;

        private final Paint mTextPaint;

        private final Paint mTextBackgroundPaint;

        private final Paint mTextLevelPaint;

        private final Paint mPaint;

        private final Paint mTargetPaint;

        private final Paint mPathPaint;

        private final Paint.FontMetricsInt mTextMetrics = new Paint.FontMetricsInt();

        private int mHeaderBottom;

        private boolean mCurDown;

        private int mCurNumPointers;

        private int mMaxNumPointers;

        private final ArrayList<PointerState> mPointers = new ArrayList<PointerState>();

        /**
         * 测试线
         */
        private final Paint mTestLine;

        /**
         * 合格范围阴影
         */
        private final Paint mQualifiedShadow;

        /**
         * 合理范围阴影
         */
        private final Paint mQualifiedShadowRed;

        /**
         * 竖线时y的最大值
         */
        private int yMax;

        /**
         * 横线时x的最大值
         */
        private int xMax;

        /**
         * x、y的最小值
         */
        private final int xyMin;

        /**
         * 画同一条线计数
         */
        private int time = 1;

        public MyView(Context c) {
            super(c);

            mVC = ViewConfiguration.get(c);

            mTextPaint = new Paint();
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextSize(10 * getResources().getDisplayMetrics().density);
            mTextPaint.setARGB(255, 0, 0, 0);
            mTextBackgroundPaint = new Paint();
            mTextBackgroundPaint.setAntiAlias(false);
            mTextBackgroundPaint.setARGB(128, 255, 255, 255);

            mTextLevelPaint = new Paint();
            mTextLevelPaint.setAntiAlias(false);
            mTextLevelPaint.setARGB(192, 255, 0, 0);

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setARGB(255, 255, 255, 255);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(2);

            mTargetPaint = new Paint();
            mTargetPaint.setAntiAlias(false);
            mTargetPaint.setARGB(255, 0, 0, 192);

            mPathPaint = new Paint();
            mPathPaint.setStrokeWidth(8);
            mPathPaint.setAntiAlias(false);
            mPathPaint.setARGB(255, 0, 96, 255);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(1);

            mTestLine = new Paint();
            mTestLine.setStrokeWidth(3);
            mTestLine.setARGB(255, 255, 255, 255);

            mQualifiedShadow = new Paint();
            mQualifiedShadow.setARGB(50, 0, 255, 0);
            mQualifiedShadowRed = new Paint();
            mQualifiedShadowRed.setARGB(100, 255, 0, 0);

            xyMin = 0;

            PointerState ps = new PointerState();
            ps.mVelocity = VelocityTracker.obtain();
            mPointers.add(ps);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mTextPaint.getFontMetricsInt(mTextMetrics);
            mHeaderBottom = -mTextMetrics.ascent + mTextMetrics.descent + 2;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            final int NP = mPointers.size();

            //增加测试线及合格阴影(add by heping1)
            Paint shadow;
            if (1 == time) {
                shadow = mQualifiedShadow;
            } else {
                shadow = mQualifiedShadowRed;
            }

            switch (numberOfLine) {
                case 1:
                    // 中间竖线
                    yMax = getHeight() - xyMin;
                    int range = (getWidth() - XY_RANGE_MARGIN * 4) / 10;
                    int xPoint = 200;

                    canvas.drawLine(200, 0, 200, yMax, mTestLine);
                    canvas.drawRect(200 - range, 0, 200 + range, 400, shadow);
                    break;

                case 2:
                    // 中间横线
                    final int y0 = getHeight() / 2;
                    final int range0 = 32;

                    Log.d(TAG, "y0=" + y0 + ",range0=" + range0);

                    canvas.drawLine(0, y0, getWidth(), y0, mTestLine);
                    canvas.drawRect(0, y0 - range0, getWidth(), y0 + range0, shadow);
                    break;

                case 3:
                    Log.i(TAG, "It must quit");
                    break;

                default:
                    Log.e(TAG, "numberOfLine error!");
                    break;
            }

            for (int p = 0; p < NP; p++) {
                final PointerState ps = mPointers.get(p);

                if (mCurDown && ps.mCurDown) {

                    int pressureLevel = (int) (ps.mCurPressure * 255);
                    mPaint.setARGB(255, pressureLevel, 128, 255 - pressureLevel);
                    canvas.drawPoint(ps.mCurX, ps.mCurY, mPaint);

                }
            }

            for (int p = 0; p < NP; p++) {
                final PointerState ps = mPointers.get(p);

                final int N = ps.mXs.size();
                float lastX = 0;
                float lastY = 0;
                boolean haveLast = false;
                boolean drawn = false;
                mPaint.setARGB(255, 128, 255, 255);

                for (int i = 0; i < N; i++) {
                    float x = ps.mXs.get(i);
                    float y = ps.mYs.get(i);

                    if (Float.isNaN(x)) {
                        haveLast = false;

                        continue;
                    }

                    if (haveLast) {
                        canvas.drawLine(lastX, lastY, x, y, mPathPaint);
                        canvas.drawPoint(lastX, lastY, mPaint);
                        drawn = true;
                    }

                    lastX = x;
                    lastY = y;
                    haveLast = true;
                }

                if (drawn) {
                    if (ps.mVelocity != null) {
                        mPaint.setARGB(255, 255, 64, 128);
                    } else {
                        canvas.drawPoint(lastX, lastY, mPaint);
                    }
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            int NP = mPointers.size();

            if (action == MotionEvent.ACTION_DOWN) {
                for (int p = 0; p < NP; p++) {
                    final PointerState ps = mPointers.get(p);
                    ps.mXs.clear();
                    ps.mYs.clear();
                    ps.mVelocity = VelocityTracker.obtain();
                    ps.mCurDown = false;
                }

                mPointers.get(0).mCurDown = true;
                mMaxNumPointers = 0;
            }

            if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) {
                final int id = (action & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;

                while (NP <= id) {
                    PointerState ps = new PointerState();
                    ps.mVelocity = VelocityTracker.obtain();
                    mPointers.add(ps);
                    NP++;
                }

                final PointerState ps = mPointers.get(id);
                ps.mVelocity = VelocityTracker.obtain();
                ps.mCurDown = true;
            }

            final int NI = event.getPointerCount();

            mCurDown = (action != MotionEvent.ACTION_UP)
                    && (action != MotionEvent.ACTION_CANCEL);
            mCurNumPointers = mCurDown ? NI : 0;

            if (mMaxNumPointers < mCurNumPointers) {
                mMaxNumPointers = mCurNumPointers;
            }

            for (int i = 0; i < NI; i++) {
                int mPointerId = event.getPointerId(i);
                if (mPointerId < mPointers.size() && mPointerId >= 0) {
                    final PointerState ps = mPointers.get(mPointerId);
                    ps.mVelocity.addMovement(event);
                    ps.mVelocity.computeCurrentVelocity(1);

                    final int N = event.getHistorySize();

                    for (int j = 0; j < N; j++) {
                        ps.mXs.add(event.getHistoricalX(i, j));
                        ps.mYs.add(event.getHistoricalY(i, j));
                    }

                    ps.mXs.add(event.getX(i));
                    ps.mYs.add(event.getY(i));
                    ps.mCurX = (int) event.getX(i);
                    ps.mCurY = (int) event.getY(i);
                    ps.mCurPressure = event.getPressure(i);
                    ps.mCurSize = event.getSize(i);
                    ps.mCurWidth = (int) (ps.mCurSize * (getWidth() / 3));
                } else {
                    Log.e(TAG, "PointerLocation: IndexOutOfBoundsException : ");
                    Log.e(TAG, "PointerLocation: Count : " + event.getPointerCount());
                    Log.e(TAG, "PointerLocation: Index : " + i);
                    Log.e(TAG, "PointerLocation: PointId : " + event.getPointerId(i));
                }
            }

            if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
                final int id = (action & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
                if (id < mPointers.size() && id >= 0) {
                    final PointerState ps = mPointers.get(id);
                    ps.mXs.add(Float.NaN);
                    ps.mYs.add(Float.NaN);
                    ps.mCurDown = false;
                }
            }

            if (action == MotionEvent.ACTION_UP) {
                for (int i = 0; i < NI; i++) {
                    int mPointerId = event.getPointerId(i);
                    if (mPointerId < mPointers.size() && mPointerId >= 0) {
                        final PointerState ps = mPointers.get(mPointerId);

                        if (ps.mCurDown) {
                            ps.mCurDown = false;
                        }
                    }
                }

                boolean result = true;

                for (int i = 0; i < mPointers.size(); i++) {
                    ArrayList<Float> xList = mPointers.get(i).mXs;
                    ArrayList<Float> yList = mPointers.get(i).mYs;

                    if (xList.size() > 0) {
                        if (!checkPointRange(xList.get(xList.size() - 1), xList.get(0),
                                yList.get(yList.size() - 1), yList.get(0))) {
                            result = false;
                            break;
                        } else {
                            for (int j = 0; j < xList.size(); j++) {
                                if (!checkPointInShadow(xList.get(j), yList.get(j))) {
                                    result = false;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (result) {
                    for (int p = 0; p < mPointers.size(); p++) {
                        final PointerState ps = mPointers.get(p);
                        ps.mXs.clear();
                        ps.mYs.clear();
                    }

                    numberOfLine++;

                    time = 1;

                    Log.d(TAG, "result=" + result + ",numberOfLine=" + numberOfLine);

                    if (numberOfLine == 3) {
                        Toast.makeText(PointerLocationDemoActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_PASS), 0/*MSG_DELAY*/);
                    }

                } else {
                    time++;
                    if (time > 3) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_FAIL), MSG_DELAY);
                    } else {
                        for (int p = 0; p < mPointers.size(); p++) {
                            final PointerState ps = mPointers.get(p);
                            ps.mXs.clear();
                            ps.mYs.clear();
                        }
                    }
                }
            }

            invalidate();

            return true;
        }

        /**
         * 判断点的移动范围是否足够大
         */
        boolean checkPointRange(float xmax, float xmin, float ymax, float ymin) {
            boolean result = false;

            Log.d(TAG, "checkPointRange, xmax=" + xmax + ",xmin=" + xmin + ",ymax=" + ymax + ",ymin=" + ymin);

            switch (numberOfLine) {
                case 1:
                    // 中间竖线
                    Log.d(TAG, "ymin=" + ymin + ",ymax=" + ymax);

                    if ((ymin < XY_RANGE_MARGIN * 2 + XY_RANGE_MIDDLE
                            && ymax > getHeight() - (XY_RANGE_MARGIN * 2 + XY_RANGE_MIDDLE))
                            || (ymax < XY_RANGE_MARGIN * 2 + XY_RANGE_MIDDLE
                            && ymin > getHeight() - (XY_RANGE_MARGIN * 2 + XY_RANGE_MIDDLE))) {
                        result = true;
                    }
                    break;

                case 2:
                    // 中间横线
                    Log.d(TAG, "xmin=" + xmin + ",xmax=" + xmax);

                    if ((xmin < XY_RANGE_MARGIN * 2 && xmax > getWidth() - XY_RANGE_MARGIN * 2)
                            || (xmax < XY_RANGE_MARGIN * 2 && xmin > getWidth() - XY_RANGE_MARGIN * 2)) {
                        result = true;
                    }
                    break;

                default:
                    Log.d(TAG, "checkPointRange: " + numberOfLine);
                    break;
            }

            if (!result) {
                Log.i(TAG, "Range small(" + numberOfLine + "): "
                        + xmax + "," + xmin + "," + ymax + "," + ymax);
            }

            return result;
        }

        /**
         * 判断点是否在阴影中(add by heping1)
         */
        boolean checkPointInShadow(float x, float y) {
            boolean result = false;

            Log.d(TAG, "checkPointInShadow, x=" + x + ",y=" + y + ",numberOfLine=" + numberOfLine);

            switch (numberOfLine) {
                case 1:
                    int range = (getWidth() - XY_RANGE_MARGIN * 4) / 10;
                    int xStandard = 200;

                    Log.d(TAG, "range=" + range + ",xStandard=" + xStandard);

                    if (Math.abs(x - xStandard) < range) {
                        result = true;
                    }
                    break;

                case 2:
                    final int y0 = getHeight() / 2;
                    final int range0 = 32;

                    Log.d(TAG, "y=" + y + ",y0=" + y0 + ",range0=" + range0);

                    if (Math.abs(y - y0) < range0) {
                        result = true;
                    }
                    break;

                default:
                    Log.d(TAG, "checkPointInShadow: " + numberOfLine);
                    break;
            }

            if (!result) {
                Log.i(TAG, "out of shadow(" + numberOfLine + "): " + x + "," + y);
            }

            return result;
        }
    }

    @Override
    protected void onPause() {
        if (null != mHandler) {
            mHandler.removeMessages(MSG_FAIL);
            mHandler.removeMessages(MSG_PASS);
        }

        if (Build.MODEL.contains("8692")) {
            Intent recoverPullInterfaceintent = new Intent("yulong.intent.action.PULLINTERFACE_FORBID");
            recoverPullInterfaceintent.putExtra("FORBID", false);
            sendBroadcast(recoverPullInterfaceintent);
            Log.d(TAG, "recoverPullInterfaceintent, FORBID = false.");
        }

        super.onPause();
    }

    public void showHintDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PointerLocationDemoActivity.this);
        builder.setMessage("竖线测试时，请从下向上滑动测试");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();

            }
        });

        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
