package com.longluo.demo.adherent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.longluo.demo.R;


/**
 * Created by luolong on 2015/12/31.
 */
public class AdherentActivity extends Activity {
    AdherentLayout mAdherentLayout2;
    AdherentLayout mAdherentLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adherent_layout);

        mAdherentLayout1 = (AdherentLayout) findViewById(R.id.layout1);
        mAdherentLayout1.setColor(getResources().getColor(android.R.color.holo_blue_dark));

        mAdherentLayout2 = (AdherentLayout) findViewById(R.id.layout2);
        mAdherentLayout2.setOnAdherentListener(new AdherentLayout.OnAdherentListener() {
            @Override
            public void onDismiss() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                    }
                }, 1000);
            }
        });
    }
}

