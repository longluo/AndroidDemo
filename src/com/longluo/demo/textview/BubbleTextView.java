package com.longluo.demo.textview;

import com.longluo.demo.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class BubbleTextView extends TextView {

	private Context mContext;

	private View mView;
	private View mOutlineView;

	private Drawable mBackground;

	public BubbleTextView(Context context) {
		this(context, null);
		
	}

	public BubbleTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		
	}

	public BubbleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// mOutlineView =
		// LayoutInflater.from(context).inflate(R.drawable.bubble_outline,
		// null);

		mContext = context;

		initViews();

	}

	private void initViews() {
//		mBackground = getResources().getDrawable(R.drawable.bubble_outline);
//		mBackground.setBounds(0, 0, 250, 250);
//
//		setCompoundDrawables(null, mBackground, null, null);
//		setCompoundDrawablePadding(50);
		
//		Drawable topDrawable = getResources().getDrawable(R.drawable.icon);
//		topDrawable.setBounds(0, 0, 200, 200);
//		setCompoundDrawables(null, topDrawable, null, null);
//		setCompoundDrawablePadding(20);
//		
//		setText("Bubble Text");
//		setTextColor(mContext.getResources().getColor(R.color.blue));
		
		
		
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			break;

		case MotionEvent.ACTION_MOVE:

			break;

		case MotionEvent.ACTION_UP:

			break;

		default:
			break;

		}

		return super.onTouchEvent(event);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		canvas.translate(getScrollX() - 30, getScrollY() - 30);
		
		Paint paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		
		canvas.drawText("Just A  Test", 10, 10, paint);
		
		canvas.save();
		canvas.restore();
		
		super.draw(canvas);
		
		
	}

}
