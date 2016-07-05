package com.longluo.demo.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.longluo.demo.R;

public class ZombieActivity extends AppCompatActivity {

    private ZombieView mZombieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zombie);
        mZombieView = (ZombieView) findViewById(R.id.custom_zombie);
    }

    public void start(View view) {
        mZombieView.start();
    }

    public void sendBack(View view) {
        mZombieView.sendBack();
    }
}
