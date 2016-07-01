package com.longluo.demo.coolcloud;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.coolcloud.uac.android.api.internal.Coolcloud2;
import com.longluo.demo.R;

/**
 * Created by luolong on 2016/6/22.
 */
public class CoolCloudDemoActivity extends AppCompatActivity {

    private Button mBtnLogin;

    public static final String APP_ID = "5000003602";

    private Coolcloud2 coolcloud = null;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cool_cloud_demo);

        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });

    }

    private void login() {
        coolcloud = Coolcloud2.get(this, APP_ID);


    }


}
