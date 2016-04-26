package com.longluo.demo.grahpics;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.longluo.demo.R;
import com.longluo.demo.util.BitmapUtils;

public class GrahpicsDemoActivity extends Activity {
    private FrameLayout mRoot;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grahpics_demo);

        initViews();
    }

    private void initViews() {
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
//		root.addView(new MyGrahpicsView(GrahpicsDemoActivity.this));

        mImageView = (ImageView) findViewById(R.id.iv_content);

        MyTestView testView = new MyTestView(GrahpicsDemoActivity.this, BitmapUtils.getViewBitmap(mImageView));
        root.addView(testView);
    }

    public View getRootView() {
        if (mRoot != null) {
            return mRoot;
        }

        return null;
    }

    public ImageView getImageView() {
        if (mImageView != null) {
            return mImageView;
        }

        return null;
    }
}
