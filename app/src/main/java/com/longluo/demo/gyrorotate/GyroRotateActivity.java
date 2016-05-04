package com.longluo.demo.gyrorotate;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by luolong on 2016/4/22.
 */
public class GyroRotateActivity extends Activity implements SensorEventListener {
    private static boolean DEBUG = false;

    private TouchSurfaceView mGLSurfaceView;
    private SensorManager mSensorManager;

    private float x;
    private float y;
    private float z;

    private int i = 0;

    private long lastTime = System.currentTimeMillis();
    private long nowTime = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        mGLSurfaceView = new TouchSurfaceView(this);
        setContentView(mGLSurfaceView);

        mGLSurfaceView.requestFocus();
        mGLSurfaceView.setFocusableInTouchMode(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mGLSurfaceView.onResume();
        if (mSensorManager != null) {
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        nowTime = System.currentTimeMillis();
        Log.d("gyro", "time gap=" + (nowTime - lastTime) + ",last=" + lastTime + ",nowTime=" + nowTime);
        lastTime = nowTime;

        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        mGLSurfaceView.updateGyro(x, y, z);

        if (DEBUG) {
            i++;
            if (i % 50 == 0) {
                Log.i("gyro", "x=" + x + " y=" + y + " z=" + z);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
