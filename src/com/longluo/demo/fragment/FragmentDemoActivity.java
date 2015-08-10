package com.longluo.demo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.longluo.demo.R;

public class FragmentDemoActivity extends Activity {
	private static final String TAG = FragmentDemoActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fragment);
		
		
	}

	
}
