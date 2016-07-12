package com.longluo.demo.fragment;

import android.support.v4.app.Fragment;

public class ContentActivity extends SingleFragmentActivity {
    private ContentFragment mContentFragment;

    @Override
    protected Fragment createFragment() {
        String title = getIntent().getStringExtra(ContentFragment.ARGUMENT);

        mContentFragment = ContentFragment.newInstance(title);
        return mContentFragment;
    }
}
