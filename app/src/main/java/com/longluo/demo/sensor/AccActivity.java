package com.longluo.demo.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.longluo.demo.R;

/**
 * Created by luolong on 2016/6/15.
 */
public class AccActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;

    private TextView mAxisA;
    private TextView mAxisB;
    private TextView mAxisC;

    private float x;
    private float y;
    private float z;

    private long lastTime = System.currentTimeMillis();
    private long nowTime = 0l;
    private long lastTimeStamp;
    private long nowTimeStamp;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1001) {
                updateSensorData();
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_acc);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        initViews();
    }

    private void initViews() {
        mAxisA = (TextView) findViewById(R.id.tv_axis_a);
        mAxisB = (TextView) findViewById(R.id.tv_axis_b);
        mAxisC = (TextView) findViewById(R.id.tv_axis_c);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        nowTime = System.currentTimeMillis();
        nowTimeStamp = event.timestamp;

        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        Log.d("acc", "time gap=" + (nowTimeStamp - lastTimeStamp) + ",last=" + lastTimeStamp
                + ",nowTime=" + nowTimeStamp);

        lastTime = nowTime;
        lastTimeStamp = nowTimeStamp;

        String xVal = " " + x;
        String yVal = " " + y;
        String zVal = " " + z;

//        Log.d("gyro", "x=" + xVal + " y=" + yVal + " z=" + zVal + " w=" + w);

//        mHandler.sendEmptyMessage(1001);

        mAxisA.setText("X =" + x);
        mAxisB.setText("Y =" + y);
        mAxisC.setText("Z =" + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void updateSensorData() {
        mAxisA.setText("X =" + x);
        mAxisB.setText("Y =" + y);
        mAxisC.setText("Z =" + z);
    }
}