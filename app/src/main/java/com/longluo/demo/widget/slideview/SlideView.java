package com.longluo.demo.widget.slideview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.longluo.demo.R;

public class SlideView extends LinearLayout {
    private static final String TAG = "SlideView";

    private Context mContext;
    private LinearLayout mViewContent;
    private RelativeLayout mHolder;
    private Scroller mScroller;

    private int mHolderWidth = 120;

    private int mLastX = 0;
    private int mLastY = 0;
    private static final int TAN = 2;

    public SlideView(Context context) {
        super(context);
        initView();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mContext = getContext();
        mScroller = new Scroller(mContext);

        setOrientation(LinearLayout.HORIZONTAL);
        View.inflate(mContext, R.layout.slideview_merge, this);
        mViewContent = (LinearLayout) findViewById(R.id.view_content);

        mHolderWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources()
                        .getDisplayMetrics()));

        // TypedValue.COMPLEX_UNIT_PX,时在不同手机上的数值都是一样的，还是原来数据120.
        //
        /*
         * mHolderWidth = Math.round(TypedValue.applyDimension(
		 * TypedValue.COMPLEX_UNIT_PX, mHolderWidth, getResources()
		 * .getDisplayMetrics()));
		 */
        // TypedValue.COMPLEX_UNIT_DIP在不同手机上显示的是不同的，是独立于设备的。
        // 在公司e6机器上显示的是180，在lg上显示240
        System.out.println("转化后的mHolderWidth is " + mHolderWidth);
    }

    public void setButtonText(CharSequence text) {
        ((TextView) findViewById(R.id.delete)).setText(text);
    }

    public void setContentView(View view) {
        mViewContent.addView(view);
    }

    public void onRequireTouchEvent(MotionEvent event) {
        // 注意执行顺序，每次不同的action都要执行int x=event.getX();和mLastX=x;
        // 注意此处的event。getX.越靠左的点x坐标越小，越靠右，x的坐标越大。
        int x = (int) event.getX();
        int y = (int) event.getY();
        System.out.println("event.getX is " + x + "event.getY is --" + y);
        // 该处方法是获得滑动了多少距离，根据打印的log。当滑动了180时就不再滑动了
        int scrollX = getScrollX();
        System.out.println("滑动了多少距离" + scrollX);
        Log.d(TAG, "x=" + x + "  y=" + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("----------down down down down ");

                break;

            case MotionEvent.ACTION_MOVE:
                System.out.println("-----------move move move ");
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                System.out.println("----move deltaX is" + deltaX);
                if (Math.abs(deltaX) < Math.abs(deltaY) * TAN) {
                    break;
                }
                // 注意这一步的处理，使用的减号
                int newScrollX = scrollX - deltaX;
                System.out.println("--------newScroll is" + newScrollX);
                if (deltaX != 0) {
                    // 此处为如果复原向右滑则滑动到初始状态，否则滑动到最左
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > mHolderWidth) {
                        newScrollX = mHolderWidth;
                    }
                    // scrollTo的参数x，若为负数则向右滚动，若为正数则向左滚动
                    this.scrollTo(newScrollX, 0);
                }
                break;

            case MotionEvent.ACTION_UP:
                System.out.println("------------up");
                newScrollX = 0;
                // 如果已经滑动le0.75倍的距离，则直接滑动到目的地，否则滑动回原来的位置
                if (scrollX - mHolderWidth * 0.75 > 0) {
                    newScrollX = mHolderWidth;
                }

                this.smoothScrollTo(newScrollX, 0);

                break;

            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        System.out.println("---------last x -----" + mLastX);
    }

    private void smoothScrollTo(int destX, int destY) {
        // 缓慢滚动到指定位置
        // 返回滚动的view的左边界
        int scrollX = getScrollX();
        // 在该例子中，当向左滑动时此处的scrollX是正数
        System.out.println("scrollX is" + scrollX);
        // getScrollX是得到总共滑动的距离，必定为正数
        int delta = destX - scrollX;
        // startScroll中的参数xofferset如果为正数则向左滑动，yofferset如果为正数则向上滑动
        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

}
