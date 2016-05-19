package com.longluo.demo.tagflowlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TableLayout;

import com.longluo.demo.R;

public class TagFlowLayoutCategoryActivity extends Activity {

	private ViewPager mViewPager;

	private String[] mTabTitles = new String[] { "Muli Selected", "Limit 3",
			"Event Test", "ScrollView Test", "Single Choose", "Gravity" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tagflowlayout_category);

		initViews();
	}
	
	private void initViews() {
		
	}

}
