package com.longluo.demo.ripple;

import com.longluo.demo.R;

import android.app.Activity;
import android.os.Bundle;

public class RippleViewDemoActivity extends Activity {

	private RippleView mRippleView = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rippleview_demo);

        mRippleView=(RippleView) findViewById(R.id.rippleview);
        mRippleView.startRipple();
    }
}
