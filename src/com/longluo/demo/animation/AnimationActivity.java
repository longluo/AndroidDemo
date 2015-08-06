package com.longluo.demo.animation;

import com.longluo.demo.R;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AnimationActivity extends Activity {
	private static final String TAG = AnimationActivity.class.getSimpleName();
	
	private Button mBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		
		initViews();
	}

	private void initViews() {
		mBtn = (Button) findViewById(R.id.btn_anim);
		
		mBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ValueAnimator animator = ValueAnimator.ofInt(0, 100);
				animator.setDuration(5000);
				animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						Integer value = (Integer) animation.getAnimatedValue();
						mBtn.setText(" " + value);	
					}
				});
				
				animator.start();
			}
		});
	}
	
	
}
