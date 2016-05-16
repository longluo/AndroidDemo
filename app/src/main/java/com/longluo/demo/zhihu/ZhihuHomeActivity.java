package com.longluo.demo.zhihu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.longluo.demo.R;
import com.longluo.demo.zhihu.fragment.NavigationDrawerFragment;

/**
 * Created by luolong on 2016/5/13.
 */
public class ZhihuHomeActivity extends Activity {
    private NavigationDrawerFragment mDrawerFragment;
    private DrawerLayout mDrawLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);

        initViews();
    }

    private void initViews() {
        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }


}
