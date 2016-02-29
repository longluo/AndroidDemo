package com.longluo.demo.textview;

import com.longluo.demo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class BubbleTextView extends TextView {
	
	private View mOutlineView;
	
	public BubbleTextView(Context context) {
		this(context, null);
	}
	
	public BubbleTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BubbleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
//		mOutlineView = LayoutInflater.from(context).inflate(R.drawable.bubble_outline, null);
		
		
		initView();
	}

	private void initView() {
		
	}

}
