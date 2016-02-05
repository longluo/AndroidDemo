package com.longluo.demo;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.longluo.demo.activitycard.MonthActivityCardActivity;
import com.longluo.demo.adherent.AdherentActivity;
import com.longluo.demo.animation.AnimationActivity;
import com.longluo.demo.badgeview.BadgeViewDemoActivity;
import com.longluo.demo.calendarcard.CalendarCardDemoActivity;
import com.longluo.demo.fragment.FragmentDemoActivity;
import com.longluo.demo.friends.FriendsActivity;
import com.longluo.demo.gaussianblur.GaussianBlurActivity;
import com.longluo.demo.jpush.JPushDemoActivity;
import com.longluo.demo.launcher.LauncherActivity;
import com.longluo.demo.listview.ListViewDemoActivity;
import com.longluo.demo.login.LoginActivity;
import com.longluo.demo.media.MediaActivity;
import com.longluo.demo.ndk.NdkDemoActivity;
import com.longluo.demo.notifications.NotificationActivity;
import com.longluo.demo.numberprogressbar.NumberProgressBarActivity;
import com.longluo.demo.progress.ProgressActivity;
import com.longluo.demo.provider.ProviderActivity;
import com.longluo.demo.roundedimageview.RoundedImageViewActivity;
import com.longluo.demo.searchview.SearchViewActivity;
import com.longluo.demo.slideview.SlideViewDemoActivity;
import com.longluo.demo.swipelistview.SwipeListViewDemoActivity;
import com.longluo.demo.touch.TouchEventDemoActivity;
import com.longluo.demo.util.LinkUtils;
import com.longluo.demo.util.UIUtils;
import com.longluo.demo.viewgroup.ViewGroupDemoActivity;
import com.longluo.demo.viewpager.ViewPagerActivity;
import com.longluo.demo.viewpager.fragments.ViewPagerMultiFragmentActivity;
import com.longluo.demo.viewpager.tabpageindicator.miui.MIUITabPageIndicatorActivity;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	private static final String[] mDemoStrings = { "BadgeView Demo",
			"MonthActivityCard Demo", "CalendarCard Demo",
			"NumberProgressBar Demo", "Animation Demo", "ViewPager Demo",
			"ViewPager Multi Fragment Demo", "SearchView Demo",
			"RoundedImageView Demo", "MIUI TabPage Indicator Demo",
			"Fragment Demo", "SwipeListView Demo", "SlideView Demo",
			"JPush Demo", "LoginActivity Demo", "FriendsActivity Demo",
			"Notifications Demo", "Media Demo", "Adherent Demo",
			"Progress Demo", "ListView Demo", "ProviderActivity Demo", "LauncherActivity Demo",
			"GaussianBlur Activity", "NDK JNI Demo",
			"Touch Event Demo Activity", "ViewGroup Demo Activity"};

	private static final int mTotal = mDemoStrings.length - 1;

	private ListView mDemoListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(TAG, "onCreate ");

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

				case 10:
					startActivity(FragmentDemoActivity.class);
					break;

				case 11:
					startActivity(SwipeListViewDemoActivity.class);
					break;

				case 12:
					startActivity(SlideViewDemoActivity.class);
					break;

				case 13:
					startActivity(JPushDemoActivity.class);
					break;

				case 14:
					startActivity(LoginActivity.class);
					break;

				case 15:
					startActivity(FriendsActivity.class);
					break;

				case 16:
					startActivity(NotificationActivity.class);
					break;

				case 17:
					startActivity(MediaActivity.class);
					break;

				case 18:
					startActivity(AdherentActivity.class);
					break;
					
				case 19:
					startActivity(ProgressActivity.class);
					break;
					
				case 20:
					startActivity(ListViewDemoActivity.class);
					break;
					
				case 21:
					startActivity(LauncherActivity.class);
					break;
					
				case 22:
					startActivity(ProviderActivity.class);
					break;
					
				case 23:
					startActivity(GaussianBlurActivity.class);
					break;
					
				case 24:
					startActivity(NdkDemoActivity.class);
					break;
					
				case 25:
					startActivity(TouchEventDemoActivity.class);
					break;
					
				case 26:
					startActivity(ViewGroupDemoActivity.class);
					break;

				default:
					break;
				}
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
