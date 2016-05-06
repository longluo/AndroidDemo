package com.longluo.demo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.longluo.demo.activitycard.MonthActivityCardActivity;
import com.longluo.demo.activitytrans.ActivityTransDemoActivity;
import com.longluo.demo.adherent.AdherentActivity;
import com.longluo.demo.advancedtextview.AdvancedTextViewActivity;
import com.longluo.demo.animation.AnimationActivity;
import com.longluo.demo.badgeview.BadgeViewDemoActivity;
import com.longluo.demo.calendarcard.CalendarCardDemoActivity;
import com.longluo.demo.cardview.CardViewDemoActivity;
import com.longluo.demo.contact.ContactDemoActivity;
import com.longluo.demo.contact.message.MessageDemoActivity;
import com.longluo.demo.contentprovider.ProviderDemoActivity;
import com.longluo.demo.crash.CrashDemoActivity;
import com.longluo.demo.creditsroll.CreditsRollDemoActivity;
import com.longluo.demo.dial.DialDemoActivity;
import com.longluo.demo.filemanager.FileManagerDemoActivity;
import com.longluo.demo.flowlayout.FlowLayoutDemoActivity;
import com.longluo.demo.forcetouch.ForceTouchDemoActivity;
import com.longluo.demo.fragment.FragmentDemoActivity;
import com.longluo.demo.friends.FriendsActivity;
import com.longluo.demo.gaussianblur.GaussianBlurActivity;
import com.longluo.demo.grahpics.GrahpicsDemoActivity;
import com.longluo.demo.grahpics.camera.CameraDemoActivity;
import com.longluo.demo.gyrorotate.GyroRotateActivity;
import com.longluo.demo.image.ImageCacheDemoActivity;
import com.longluo.demo.jpush.JPushDemoActivity;
import com.longluo.demo.json.JsonDemoActivity;
import com.longluo.demo.launcher.LauncherDemo2Activity;
import com.longluo.demo.launcher.LauncherDemoActivity;
import com.longluo.demo.lbs.LBSDemoActivity;
import com.longluo.demo.likebutton.LikeButtonDemoActivity;
import com.longluo.demo.listview.ListViewDemoActivity;
import com.longluo.demo.log.LogDemoActivity;
import com.longluo.demo.login.LoginDemoActivity;
import com.longluo.demo.media.MediaActivity;
import com.longluo.demo.ndk.NdkDemoActivity;
import com.longluo.demo.notifications.NotificationActivity;
import com.longluo.demo.numberprogressbar.NumberProgressBarActivity;
import com.longluo.demo.palette.PaletteDemoActivity;
import com.longluo.demo.progress.ProgressActivity;
import com.longluo.demo.ripple.RippleViewDemoActivity;
import com.longluo.demo.roundedimageview.RoundedImageViewActivity;
import com.longluo.demo.searchview.SearchViewActivity;
import com.longluo.demo.slideview.SlideViewDemoActivity;
import com.longluo.demo.swipelistview.SwipeListViewDemoActivity;
import com.longluo.demo.tagflowlayout.TagFlowLayoutCategoryActivity;
import com.longluo.demo.textview.TextViewDemoActivity;
import com.longluo.demo.toolbar.ToolBarDemoActivity;
import com.longluo.demo.touch.TouchEventDemoActivity;
import com.longluo.demo.utils.LinkUtils;
import com.longluo.demo.view.ViewDemoActivity;
import com.longluo.demo.viewgroup.ViewGroupDemoActivity;
import com.longluo.demo.viewpager.ViewPagerActivity;
import com.longluo.demo.viewpager.fragments.ViewPagerMultiFragmentActivity;
import com.longluo.demo.viewpager.tabpageindicator.miui.MIUITabPageIndicatorActivity;
import com.longluo.demo.vr.VRPlayerDemoActivity;
import com.longluo.demo.waterwave.WaterWaveDemoActivity;
import com.longluo.demo.webview.WebViewDemoActivity;
import com.longluo.demo.widget.imageprocess.ImageProcessDemoActivity;
import com.longluo.demo.xml.XmlDemoActivity;

import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String[] mDemoStrings = {"BadgeView Demo",
            "MonthActivityCard Demo",
            "CalendarCard Demo",
            "NumberProgressBar Demo",
            "Animation Demo",
            "ViewPager Demo",
            "ViewPager Multi Fragment Demo",
            "SearchView Demo",
            "RoundedImageView Demo",
            "MIUI TabPage Indicator Demo",
            "Fragment Demo",
            "SwipeListView Demo",
            "SlideView Demo",
            "JPush Demo",
            "LoginDemoActivity Demo",
            "FriendsActivity Demo",
            "Notifications Demo",
            "Media Demo",
            "Adherent Demo",
            "Progress Demo",
            "ListView Demo",
            "ProviderActivity Demo",
            "LauncherActivity Demo 1",
            "LauncherActivity Demo 2",
            "GaussianBlur Activity",
            "NDK JNI Demo",
            "Touch Event Demo Activity",
            "ViewGroup Demo Activity",
            "Log Demo Activity",
            "FlowLayout Demo Activity",
            "TagFlowLayout Demo Activity",
            "Graphics Demo Activity",
            "TextView Demo Activity",
            "Advanced Text View Demo Activity",
            "WaterWave Demo ",
            "ForceTouch Demo",
            "Ripple View Demo",
            "XML Parser Demo",
            "JSON Demo",
            "LBS Demo",
            "Message Demo",
            "Contacts Demo",
            "Dial Demo",
            "View Demo",
            "Crash Demo",
            "ImageCache Demo",
            "WebView Demo",
            "Gyrorotate Demo",
            "VR Player Demo",
            "FileManager Demo",
            "ToolBar Demo",
            "CardView Demo",
            "ActivityTranslation Demo",
            "Palette Demo",
            "ImageProcess Demo",
            "CreditsRoll Demo",
            "LikeButton Demo",
            "Camera Demo",
    };

    private static final int mTotal = mDemoStrings.length - 1;

    private Toolbar mToolbar;

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

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mDemoListView = (ListView) findViewById(R.id.lv_demos);

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);

    }

    private void init(Activity activity) {
        LinkUtils.initAuthorInfo(activity);

//		UIUtils.initActionBar(activity);
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
                        startActivity(LoginDemoActivity.class);
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
                        startActivity(ProviderDemoActivity.class);
                        break;

                    case 22:
                        startActivity(LauncherDemoActivity.class);
                        break;

                    case 23:
                        startActivity(LauncherDemo2Activity.class);
                        break;

                    case 24:
                        startActivity(GaussianBlurActivity.class);
                        break;

                    case 25:
                        startActivity(NdkDemoActivity.class);
                        break;

                    case 26:
                        startActivity(TouchEventDemoActivity.class);
                        break;

                    case 27:
                        startActivity(ViewGroupDemoActivity.class);
                        break;

                    case 28:
                        startActivity(LogDemoActivity.class);
                        break;

                    case 29:
                        startActivity(FlowLayoutDemoActivity.class);
                        break;

                    case 30:
                        startActivity(TagFlowLayoutCategoryActivity.class);
                        break;

                    case 31:
                        startActivity(GrahpicsDemoActivity.class);
                        break;

                    case 32:
                        startActivity(TextViewDemoActivity.class);
                        break;

                    case 33:
                        startActivity(AdvancedTextViewActivity.class);
                        break;

                    case 34:
                        startActivity(WaterWaveDemoActivity.class);
                        break;

                    case 35:
                        startActivity(ForceTouchDemoActivity.class);
                        break;

                    case 36:
                        startActivity(RippleViewDemoActivity.class);
                        break;

                    case 37:
                        startActivity(XmlDemoActivity.class);
                        break;

                    case 38:
                        startActivity(JsonDemoActivity.class);
                        break;

                    case 39:
                        startActivity(LBSDemoActivity.class);
                        break;

                    case 40:
                        startActivity(MessageDemoActivity.class);
                        break;

                    case 41:
                        startActivity(ContactDemoActivity.class);
                        break;

                    case 42:
                        startActivity(DialDemoActivity.class);
                        break;

                    case 43:
                        startActivity(ViewDemoActivity.class);
                        break;

                    case 44:
                        startActivity(CrashDemoActivity.class);
                        break;

                    case 45:
                        startActivity(ImageCacheDemoActivity.class);
                        break;

                    case 46:
                        startActivity(WebViewDemoActivity.class);
                        break;

                    case 47:
                        startActivity(GyroRotateActivity.class);
                        break;

                    case 48:
                        startActivity(VRPlayerDemoActivity.class);
                        break;

                    case 49:
                        startActivity(FileManagerDemoActivity.class);
                        break;

                    case 50:
                        startActivity(ToolBarDemoActivity.class);
                        break;

                    case 51:
                        startActivity(CardViewDemoActivity.class);
                        break;

                    case 52:
                        startActivity(ActivityTransDemoActivity.class);
                        break;

                    case 53:
                        startActivity(PaletteDemoActivity.class);
                        break;

                    case 54:
                        startActivity(ImageProcessDemoActivity.class);
                        break;

                    case 55:
                        startActivity(CreditsRollDemoActivity.class);
                        break;

                    case 56:
                        startActivity(LikeButtonDemoActivity.class);
                        break;

                    case 57:
                        startActivity(CameraDemoActivity.class);
                        break;

                    default:
                        break;
                }
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void initActionBar(final Activity activity) {
        if (activity == null) {
            return;
        }

        ActionBar bar = activity.getActionBar();

        if (activity instanceof MainActivity) {
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
                    | ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_SHOW_HOME);
        } else {
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
                    | ActionBar.DISPLAY_HOME_AS_UP
                    | ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }
}
