package com.longluo.demo.watch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.longluo.demo.R;

/**
 * Created by luolong on 2016/8/23.
 */
public class WatchDemoActivity extends Activity {

    private Button mClockBtn1;
    private Button mClockBtn2;
    private Button mClockBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_demo);

        initViews();
    }

    private void initViews() {
        mClockBtn1 = (Button) findViewById(R.id.id_clock_one);
        mClockBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WatchDemoActivity.this, ClockDemoActivity.class));
            }
        });

        mClockBtn2 = (Button) findViewById(R.id.id_clock_two);
        mClockBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WatchDemoActivity.this, ClockDemoTwoActivity.class));
            }
        });


        mClockBtn3 = (Button) findViewById(R.id.id_clock_three);
        mClockBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WatchDemoActivity.this, WatchBoardDemoActivity.class));
            }
        });

    }


}
