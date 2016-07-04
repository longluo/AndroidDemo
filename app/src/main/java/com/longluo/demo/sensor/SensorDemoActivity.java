package com.longluo.demo.sensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.longluo.demo.R;

public class SensorDemoActivity extends Activity {

    private Button mBtnAcc;
    private Button mBtnGyro;
    private Button mBtnRotateVector;
    private Button mBtnGRV;
    private Button mBtnPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_demo);

        initViews();
    }

    private void initViews() {

        mBtnAcc = (Button) findViewById(R.id.btn_acc);
        mBtnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorDemoActivity.this, AccActivity.class);
                startActivity(intent);
            }
        });

        mBtnGyro = (Button) findViewById(R.id.btn_gyro);
        mBtnGyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorDemoActivity.this, GyroActivity.class);
                startActivity(intent);
            }
        });

        mBtnRotateVector = (Button) findViewById(R.id.btn_rotate_vector);
        mBtnRotateVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorDemoActivity.this, RotateVectorActivity.class);
                startActivity(intent);
            }
        });

        mBtnGRV = (Button) findViewById(R.id.btn_game_rotate_vector);
        mBtnGRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorDemoActivity.this, GameRotateVectorActivity.class);
                startActivity(intent);
            }
        });

        mBtnPressure = (Button) findViewById(R.id.btn_pressure);
        mBtnPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorDemoActivity.this, PressureDemoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
