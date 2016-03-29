package com.longluo.demo.widget.imgcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * ImageCellLayout
 * 
 * @author luolong
 * @Date 2015-6-26 下午4:51:54
 */
public class ImageCellLayout extends RelativeLayout {
    private static final String TAG = ImageCellLayout.class.getSimpleName();
    

    public ImageCellLayout(Context context) {
        super(context);
    }

    public ImageCellLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    public ImageCellLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
