package com.longluo.demo.progress;

import com.longluo.demo.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by luolong on 2016/1/4.
 */
public class ProgressActivity extends Activity {
	private ChaseProgress mChaseProgress;
	private HorizontalProgress mHorizontalProgress;
	private MixProgress mMixProgress;
	private RoundProgress mRoundProgress;
	private ScatterProgress mScatterProgress;
	private ShuttleProgress mShuttleProgress;
	private SkipProgress mSkipProgress;
	private SwapProgress mSwapProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);

		initViews();
	}

	private void initViews() {
		mChaseProgress = (ChaseProgress) findViewById(R.id.chaseprogress);
		mHorizontalProgress = (HorizontalProgress) findViewById(R.id.horizontalprogress);
		mMixProgress = (MixProgress) findViewById(R.id.mixprogress);
		mRoundProgress = (RoundProgress) findViewById(R.id.roundprogress);
		mScatterProgress = (ScatterProgress) findViewById(R.id.scatterprogress);
		mShuttleProgress = (ShuttleProgress) findViewById(R.id.shuttleprogress);
		mSkipProgress = (SkipProgress) findViewById(R.id.skipprogress);
		mSwapProgress = (SwapProgress) findViewById(R.id.swapprogress);
	}

}
