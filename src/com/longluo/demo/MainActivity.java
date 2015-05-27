package com.longluo.demo;

import com.longluo.demo.guide.GuideActivity;
import com.longluo.demo.guide.GuideUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mBtnStart;

    private GuideUtils mGuideUtils = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
//        initGuide();
    }

    private void init() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initGuide() {
        /** 获取引导界面工具类的实例 **/
        mGuideUtils = GuideUtils.getInstance();
        
        /** 调用引导界面 **/
        mGuideUtils.initGuide(this, R.drawable.guide_tap_to_refresh);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /** 按钮的方式点击显示引导界面 **/
                mGuideUtils.initGuide(MainActivity.this, R.drawable.guide_tap_to_refresh);
            }
        });

/*        findViewById(R.id.btn_).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                *//**
                 * 实际程序中，如果没有第一次了，那不会显示引导界面了。
                 * 这这时候，我们在setFirst中设置false，当我们点击的时候，
                 * 就没有效果了！不会再弹出了
                 *//*
                mGuideUtils.setFirst(false);
                mGuideUtils.initGuide(MainActivity.this, R.drawable.guide_tap_to_refresh);
            }
        });*/
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
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
