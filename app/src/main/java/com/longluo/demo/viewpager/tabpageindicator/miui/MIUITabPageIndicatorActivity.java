package com.longluo.demo.viewpager.tabpageindicator.miui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.longluo.demo.R;
import com.longluo.demo.viewpager.tabpageindicator.miui.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MIUITabPageIndicatorActivity extends FragmentActivity {
    private static final String TAG = MIUITabPageIndicatorActivity.class
            .getSimpleName();

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private List<String> mDatas = Arrays.asList("短信1", "短信2", "短信3", "短信4",
            "短信5", "短信6", "短信7", "短信8", "短信9");
    // private List<String> mDatas = Arrays.asList("短信", "收藏", "推荐");

    private ViewPagerIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_miui_tabpageindicator);

        initView();
        initDatas();
        // 设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        // 设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);

    }

    private void initDatas() {

        for (String data : mDatas) {
            ViewPagerSimpleFragment fragment = ViewPagerSimpleFragment
                    .newInstance(data);
            mTabContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
    }

}
