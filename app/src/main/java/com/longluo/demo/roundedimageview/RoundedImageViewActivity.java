package com.longluo.demo.roundedimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.longluo.demo.R;
import com.longluo.demo.widget.image.RoundedImageView;

public class RoundedImageViewActivity extends AppCompatActivity {
    private static final String TAG = RoundedImageViewActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private RoundedImageView mAvatarRIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounded_image);

        initViews();
        initDatas();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.rounded_image_view_demo_activity);
        setSupportActionBar(mToolbar);

        mAvatarRIV = (RoundedImageView) findViewById(R.id.ri_avatar);

    }

    private void initDatas() {
        mAvatarRIV.setImageResource(R.drawable.meinv01);

    }


}
