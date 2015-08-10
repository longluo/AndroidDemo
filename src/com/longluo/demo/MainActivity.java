package com.longluo.demo;

import java.util.Arrays;
import java.util.LinkedList;

import com.longluo.demo.activitycard.MonthActivityCardActivity;
import com.longluo.demo.animation.AnimationActivity;
import com.longluo.demo.badgeview.BadgeViewDemoActivity;
import com.longluo.demo.calendarcard.CalendarCardDemoActivity;
import com.longluo.demo.numberprogressbar.NumberProgressBarActivity;
import com.longluo.demo.roundedimageview.RoundedImageViewActivity;
import com.longluo.demo.searchview.SearchViewActivity;
import com.longluo.demo.utils.AppUtils;
import com.longluo.demo.utils.LinkUtils;
import com.longluo.demo.utils.UIUtils;
import com.longluo.demo.viewpager.ViewPagerActivity;
import com.longluo.demo.viewpager.fragments.ViewPagerMultiFragmentActivity;
import com.longluo.demo.viewpager.tabpageindicator.miui.MIUITabPageIndicatorActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	private static final String[] mDemoStrings = { "BadgeView Demo",
			"MonthActivityCard Demo", "CalendarCard Demo",
			"NumberProgressBar Demo", "Animation Demo", "ViewPager Demo",
			"ViewPager Multi Fragment Demo", "SearchView Demo",
			"RoundedImageView Demo", "MIUI TabPage Indicator Demo" };

	private static final int mTotal = mDemoStrings.length - 1;

	private ListView mDemoListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		init(this);
		initData();
	}

	private void initData() {
		LinkedList<String> mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mDemoStrings));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mListItems);

		mDemoListView.setAdapter(adapter);
		mDemoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "position=" + position);

				switch (position) {
				case 0:
					startActivity(BadgeViewDemoActivity.class);
					break;

				case 1:
					startActivity(MonthActivityCardActivity.class);
					break;

				case 2:
					startActivity(CalendarCardDemoActivity.class);
					break;

				case 3:
					startActivity(NumberProgressBarActivity.class);
					break;

				case 4:
					startActivity(AnimationActivity.class);
					break;

				case 5:
					startActivity(ViewPagerActivity.class);
					break;

				case 6:
					startActivity(ViewPagerMultiFragmentActivity.class);
					break;

				case 7:
					startActivity(SearchViewActivity.class);
					break;

				case 8:
					startActivity(RoundedImageViewActivity.class);
					break;
					
				case 9:
					startActivity(MIUITabPageIndicatorActivity.class);
					break;

				default:
					break;
				}

				// if (position == mTotal - 7) {
				// startActivity(BadgeViewDemoActivity.class);
				// } else if (position == mTotal - 6) {
				// startActivity(MonthActivityCardActivity.class);
				// } else if (position == mTotal - 5) {
				// startActivity(CalendarCardDemoActivity.class);
				// } else if (position == mTotal - 4) {
				// startActivity(NumberProgressBarActivity.class);
				// } else if (position == mTotal - 3) {
				// startActivity(AnimationActivity.class);
				// } else if (position == mTotal - 2) {
				// startActivity(ViewPagerActivity.class);
				// } else if (position == mTotal - 1) {
				// startActivity(ViewPagerMultiFragmentActivity.class);
				// } else {
				// startActivity(SearchViewActivity.class);
				// }
			}
		});
	}

	private void init(Activity activity) {
		LinkUtils.initAuthorInfo(activity);
		UIUtils.initActionBar(activity);
	}

	private void initViews() {
		mDemoListView = (ListView) findViewById(R.id.lv_demos);
	}

	private void startActivity(Class<?> cls) {
		Intent intent = new Intent(MainActivity.this, cls);
		startActivity(intent);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
