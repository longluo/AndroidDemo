package com.longluo.demo.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.longluo.demo.R;

/**
 * Created by luolong on 2016/6/12.
 */
public class SensorDemoActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private TextView mAxisA;
    private TextView mAxisB;
    private TextView mAxisC;
    private TextView mAxisD;

    private float x;
    private float y;
    private float z;
    private float w;

    private long lastTime = System.currentTimeMillis();
    private long nowTime = 0l;
    private long lastTimeStamp;
    private long nowTimeStamp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_demo);

        initViews();

        init();
    }

    private void initViews() {

        mAxisA = (TextView) findViewById(R.id.tv_axis_a);
        mAxisB = (TextView) findViewById(R.id.tv_axis_b);
        mAxisC = (TextView) findViewById(R.id.tv_axis_c);
        mAxisD = (TextView) findViewById(R.id.tv_axis_d);

    }

    private void init() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSensorManager != null) {
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR),
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        nowTime = System.currentTimeMillis();
        nowTimeStamp = event.timestamp;

        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        w = event.values[3];

        Log.d("sensor", "time gap=" + (nowTimeStamp - lastTimeStamp) + ",last=" + lastTimeStamp
                + ",nowTime=" + nowTimeStamp);

        lastTime = nowTime;
        lastTimeStamp = nowTimeStamp;

        String xVal = " " + x;
        String yVal = " " + y;
        String zVal = " " + z;
        String wVal = " " + w;

        Log.d("sensor", "x=" + xVal + " y=" + yVal + " z=" + zVal + " w=" + w);

        mAxisA.setText("X =" + x);
        mAxisB.setText("Y =" + y);
        mAxisC.setText("Z =" + z);
        mAxisD.setText("W =" + w);

    }
}
