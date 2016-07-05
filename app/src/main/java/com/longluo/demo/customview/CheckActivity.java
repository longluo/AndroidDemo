package com.longluo.demo.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.longluo.demo.R;


public class CheckActivity extends AppCompatActivity {

    private CheckView mCheckView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        mCheckView = (CheckView) findViewById(R.id.custom_check);
    }

    public void check(View view) {
        mCheckView.check();
    }

    public void unCheck(View view) {
        mCheckView.unCheck();
    }
}
