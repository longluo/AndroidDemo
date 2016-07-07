package com.longluo.demo.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.longluo.demo.R;
import com.longluo.demo.qrcode.zxing.client.android.CaptureActivity;


public class QRCodeDemoActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_demo);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.scanning_analytic:
                intent.setClass(QRCodeDemoActivity.this, CaptureActivity.class);
                startActivity(intent);
                break;

            case R.id.image_analytic:
                intent.setClass(QRCodeDemoActivity.this, ImageActivity.class);
                startActivity(intent);
                break;

/*            case R.id.help:
                intent.setClass(QRCodeDemoActivity.this, HelpActivity.class);
                startActivity(intent);
                break;*/

            default:
                break;
        }
    }

}

