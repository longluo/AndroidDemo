package com.longluo.demo.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.longluo.demo.R;


public class CustomViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_demo);
    }

    public void CheckView(View view) {
        startActivity(new Intent(this, CheckActivity.class));
    }

    public void Zombie(View view) {
        startActivity(new Intent(this, ZombieActivity.class));
    }

    public void MyQQSport(View view) {
        startActivity(new Intent(this, MyQQSportActivity.class));
    }
}
