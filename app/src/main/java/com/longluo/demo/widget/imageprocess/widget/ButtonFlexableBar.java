package com.longluo.demo.widget.imageprocess.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ButtonFlexableBar extends RelativeLayout {
    private static final String TAG = ButtonFlexableBar.class.getSimpleName();

    private LayoutInflater mLayoutInflater;

    private ArrayList<View> mViews = new ArrayList<View>();

    public ButtonFlexableBar(Context context) {
        this(context, null);
    }

    public ButtonFlexableBar(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ButtonFlexableBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs) {

    }

}
