package com.longluo.demo.calendarcard;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.widgets.calendarcard.CalendarCard;
import com.longluo.demo.widgets.calendarcard.OnCellItemClick;
import com.longluo.demo.widgets.calendarcard.CardGridItem;


public class CardSample1Activity extends Activity {
	
	private CalendarCard mCalendarCard;
	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_sample1);
		
		mCalendarCard = (CalendarCard)findViewById(R.id.calendarCard1);
		mCalendarCard.setOnCellItemClick(new OnCellItemClick() {
			@Override
			public void onCellClick(View v, CardGridItem item) {
				mTextView.setText(getResources().getString(R.string.sel_date, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.getDate().getTime())));
			}
		});
		
		mTextView = (TextView)findViewById(R.id.textView1);
	}

}
