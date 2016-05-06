package com.longluo.demo.gyrorotate;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.longluo.demo.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by luolong on 2016/4/22.
 */
public class GyroRotateActivity extends Activity implements SensorEventListener {
    private static boolean DEBUG = false;

    private TextView mAxisX;
    private TextView mAxisY;
    private TextView mAxisZ;

    private TouchSurfaceView mGLSurfaceView;
    private SensorManager mSensorManager;

    private float x;
    private float y;
    private float z;

    private int i = 0;

    private long lastTime = System.currentTimeMillis();
    private long nowTime = 0l;
    private long lastTimeStamp;
    private long nowTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));

        setContentView(R.layout.activity_gyroscope_demo);
        initViews();

//        mGLSurfaceView = new TouchSurfaceView(this);
//        setContentView(mGLSurfaceView);

//        mGLSurfaceView.requestFocus();
//        mGLSurfaceView.setFocusableInTouchMode(true);

        Log.d("mytest", "" + System.currentTimeMillis() + "," + new Date().getTime() + ","
                + Calendar.getInstance().getTimeInMillis() + "," + SystemClock.currentThreadTimeMillis()
                + "," + SystemClock.elapsedRealtime() + "," + SystemClock.uptimeMillis());

    }

    private void initViews() {

        mAxisX = (TextView) findViewById(R.id.tv_gyro_axis_x);
        mAxisY = (TextView) findViewById(R.id.tv_gyro_axis_y);
        mAxisZ = (TextView) findViewById(R.id.tv_gyro_axis_z);

    }

    @Override
    protected void onPause() {
        super.onPause();

//        mGLSurfaceView.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        mGLSurfaceView.onResume();

        if (mSensorManager != null) {
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                    SensorManager.SENSOR_DELAY_FASTEST);
        }

//        SensorManager.SENSOR_DELAY_GAME
//        SensorManager.SENSOR_DELAY_NORMAL
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
        nowTimeStamp = event.timestamp;

        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

//        Log.d("gyro", "time gap=" + (nowTime - lastTime) + ",last=" + lastTime + ",nowTime=" + nowTime);

        Log.d("gyro", "time gap=" + (nowTimeStamp - lastTimeStamp) + ",last=" + lastTimeStamp
                + ",nowTime=" + nowTimeStamp);

        lastTime = nowTime;
        lastTimeStamp = nowTimeStamp;

        String xVal = " " + x;
        String yVal = " " + y;
        String zVal = " " + z;

//        Log.d("gyro", "x=" + x + " y=" + y + " z=" + z);
        Log.d("gyro", "x=" + xVal + " y=" + yVal + " z=" + zVal);

//        mGLSurfaceView.updateGyro(x, y, z);

        mAxisX.setText("X =" + x);
        mAxisY.setText("Y =" + y);
        mAxisZ.setText("Z =" + z);

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
