package com.longluo.demo.ripple;

import android.app.Activity;
import android.os.Bundle;

import com.longluo.demo.R;

public class RippleViewDemoActivity extends Activity {

    private RippleView mRippleView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rippleview_demo);

        mRippleView = (RippleView) findViewById(R.id.rippleview);
        mRippleView.startRipple();
    }
}
