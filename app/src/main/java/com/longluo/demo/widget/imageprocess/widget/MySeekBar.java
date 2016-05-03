package com.longluo.demo.widget.imageprocess.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.longluo.demo.R;


/**
 * 自定义SeekBar控件
 *
 * @author luolong
 * @date 2015-05-21 01:20:03
 */
public class MySeekBar extends LinearLayout {
    private TextView mTvValue;
    private SeekBar mSeekBar;

    private String mProgressValue;
    private float mProgressTextSize;

    private Object mTagData;

    private LayoutParams tvParams, seekbarParams;

    protected onSeekBarProgressChangeListener mSeekBarChangeListener = null;

    public MySeekBar(Context context) {
        super(context);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelfSeekBar);

        mProgressValue = ta.getString(R.styleable.SelfSeekBar_progressValue);
        mProgressTextSize = ta.getDimension(R.styleable.SelfSeekBar_progressTextSize, 20);

        ta.recycle();

        mTvValue = new TextView(context);
        mSeekBar = new SeekBar(context);

        mTvValue.setText(mProgressValue);
        mTvValue.setTextSize(mProgressTextSize);

        mSeekBar.setMax(255);
        mSeekBar.setProgress(127);

        initLayout(context);
    }

    public interface onSeekBarProgressChangeListener {
        void onSeekBarProgressChange(int progess);
    }

    public void setOnProgressChangeListener(
            onSeekBarProgressChangeListener listener) {
        mSeekBarChangeListener = listener;
    }

    public void setTagData(Object data) {
        mTagData = data;
    }

    public Object getTagData() {
        return mTagData;
    }

    private void initLayout(Context context) {
        tvParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        seekbarParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout mLayout = new LinearLayout(context);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        mLayout.setGravity(Gravity.CENTER);
        mLayout.addView(mTvValue, tvParams);
        mLayout.addView(mSeekBar, seekbarParams);

        addView(mLayout);
    }
}
