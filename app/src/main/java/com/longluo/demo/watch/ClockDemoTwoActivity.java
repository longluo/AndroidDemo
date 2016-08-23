package com.longluo.demo.watch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.longluo.demo.widget.clock.ClockTwo;


public class ClockDemoTwoActivity extends Activity {

    private Handler mHandler;

    private ClockTwo mClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClock = new ClockTwo(this);

        mHandler = new Handler();

        mHandler.post(updateTime);

        setContentView(mClock);
    }

    private Runnable updateTime = new Runnable() {
        public void run() {
            mClock.refreshClock();
            mHandler.postDelayed(updateTime, 1000);
        }
    };


}
