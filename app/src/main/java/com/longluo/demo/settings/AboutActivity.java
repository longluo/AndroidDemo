package com.longluo.demo.settings;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.longluo.demo.R;


/**
 * Created by luolong on 2016/5/31.
 */
public class AboutActivity extends AppCompatActivity {

    private TextView mApkVersionTv;
    private TextView mServiceTv;
    private PackageManager mPkgMngr;
    private PackageInfo mPkgInfo;
    private String mVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        mPkgMngr = getPackageManager();

        mServiceTv = (TextView) findViewById(R.id.service_textview);

        mApkVersionTv = (TextView) findViewById(R.id.apk_version);

        mVersion = getResources().getString(R.string.version);

        try {
            mPkgInfo = mPkgMngr.getPackageInfo(getPackageName(), 0);
            mApkVersionTv.setText(String.format(mVersion, mPkgInfo.versionName));
        } catch (Exception e) {
            e.printStackTrace();
            mApkVersionTv.setText(String.format(mVersion, "2.0.0"));
        }

    }

}
