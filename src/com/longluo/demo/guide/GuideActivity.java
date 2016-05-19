package com.longluo.demo.guide;

import com.longluo.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * GuideActivity
 * 
 * @author luolong
 * @date 2015-5-27 下午12:18:31
 * @version
 */
public class GuideActivity extends Activity {
    private static final String TAG = GuideActivity.class.getSimpleName();

    private ImageView mIvRefreshTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }
    
    private void initViews() {
        mIvRefreshTip = (ImageView) findViewById(R.id.iv_guide);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
