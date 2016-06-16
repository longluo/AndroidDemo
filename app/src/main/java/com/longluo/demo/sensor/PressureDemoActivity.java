package com.longluo.demo.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.longluo.demo.R;

import java.text.DecimalFormat;

/**
 * Created by luolong on 2016/6/16.
 */
public class PressureDemoActivity extends Activity {

    private TextView mTvPressureData;
    private TextView mTvElevationData;

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_demo);

        initViews();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mSensorManager.registerListener(pressureListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSensorManager.unregisterListener(pressureListener);

    }

    private void initViews() {
        mTvPressureData = (TextView) findViewById(R.id.tv_pressure);

        mTvElevationData = (TextView) findViewById(R.id.tv_elevation);
    }


    SensorEventListener pressureListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            float sPV = event.values[0];

            mTvPressureData.setText(String.valueOf(sPV));

            DecimalFormat df = new DecimalFormat("0.00");
            df.getRoundingMode();// 计算海拔
            double height = 44330000 * (1 - (Math.pow((Double.parseDouble(df.format(sPV)) / 1013.25),
                    (float) 1.0 / 5255.0)));

            mTvElevationData.setText(df.format(height));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {


        }
    };


}
