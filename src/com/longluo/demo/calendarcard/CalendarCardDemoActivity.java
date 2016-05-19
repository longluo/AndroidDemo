package com.longluo.demo.calendarcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.longluo.demo.R;

public class CalendarCardDemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_display_demo);
	}
	
	public void handleSample1(View v) {
		startActivity(new Intent(this, CardSample1Activity.class));
	}

	public void handleSample2(View v) {
		startActivity(new Intent(this, CardSample2Activity.class));
	}
	
}
