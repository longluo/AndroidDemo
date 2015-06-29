package com.longluo.demo.widgets.imgcard;

import com.longluo.demo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.util.DebugUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * ImageCellLayout
 * 
 * @author luolong
 * @Date 2015-6-26 下午4:51:54
 */
public class ImageCellLayout extends RelativeLayout {
    private static final String TAG = ImageCellLayout.class.getSimpleName();
    
    private ImageView mImageView;

    private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked };

    public ImageCellLayout(Context context) {
        super(context);
        init(context);
    }

    public ImageCellLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("NewApi")
    public ImageCellLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.image_card_cell, null, false);
//        mImageView = (ImageView) layout.findViewById(R.id.iv_image);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        
        Log.d("luolong", TAG + ", onCreateDrawableState, extraSpace=" + extraSpace
                + ",drawableState=" + drawableState);
        
        mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        
        return drawableState;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        
    }
}
