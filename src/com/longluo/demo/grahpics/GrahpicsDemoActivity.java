package com.longluo.demo.grahpics;

import com.longluo.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
//		root.addView(new MyTestView(GrahpicsDemoActivity.this, null, 0, root));
		
	}

}
