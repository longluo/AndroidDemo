package com.longluo.demo.roundedimageview;

import com.longluo.demo.R;
import com.longluo.demo.widgets.image.RoundedImageView;

import android.app.Activity;
import android.os.Bundle;

public class RoundedImageViewActivity extends Activity {
	private static final String TAG = RoundedImageViewActivity.class.getSimpleName();
	
	private RoundedImageView mAvatarRIV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rounded_image);
		
		initViews();
		initDatas();
	}

	private void initDatas() {
		mAvatarRIV.setImageResource(R.drawable.meinv_wear_glass);
	}

	private void initViews() {
		mAvatarRIV = (RoundedImageView) findViewById(R.id.ri_avatar);
		
		
	}

}
