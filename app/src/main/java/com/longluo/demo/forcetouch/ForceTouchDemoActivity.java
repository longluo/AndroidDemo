package com.longluo.demo.forcetouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.longluo.demo.R;

public class ForceTouchDemoActivity extends Activity {
    private static final String TAG = "ForceTouchDemoActivity";

    private Button mBtnIconPressDemo;
    private Button mBtnShortCutListPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcetouch_demo);

        initViews();
    }

    private void initViews() {
        mBtnIconPressDemo = (Button) findViewById(R.id.btn_app_icon_force_touch);
        mBtnIconPressDemo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForceTouchDemoActivity.this,
                        AppIconPressDemoActivity.class);
                startActivity(intent);
            }
        });

        mBtnShortCutListPop = (Button) findViewById(R.id.btn_shortcut_list_pop);
        mBtnShortCutListPop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForceTouchDemoActivity.this,
                        ShortcutListPopDemoActivity.class);
                startActivity(intent);
            }
        });
    }

}
