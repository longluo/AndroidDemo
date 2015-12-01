package com.longluo.demo.media;

import com.longluo.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MediaActivity extends Activity {
	
	private ListView mListView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media);

		initViews();
		init();
	}

	private void initViews() {
		
	}

	private void init() {

	}

}
