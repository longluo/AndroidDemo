package com.longluo.demo.pendulum;

import android.app.Activity;
import android.os.Bundle;

import com.longluo.uilib.pendulum.Pendulum;

/**
 * Created by luolong on 2016/9/7.
 */
public class PendulumDemoActivity extends Activity {

    private Pendulum mPendulumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPendulumView = new Pendulum(PendulumDemoActivity.this);

        setContentView(mPendulumView);
    }


}
