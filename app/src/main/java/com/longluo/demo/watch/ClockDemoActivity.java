package com.longluo.demo.watch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.longluo.demo.widget.clock.Clock;


/**
 * Created by luolong on 2016/8/5.
 */
public class ClockDemoActivity extends Activity {

    private Handler mHandler;

    private Clock mClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClock = new Clock(this);

//        mHandler = new Handler();

//        mHandler.post(updateTime);

        setContentView(mClock);
    }

/*    private Runnable updateTime = new Runnable() {
        public void run() {
            mClock.refreshClock();
            mHandler.postDelayed(updateTime, 1000);
        }
    };*/


}
