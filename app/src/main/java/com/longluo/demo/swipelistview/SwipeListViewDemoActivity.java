package com.longluo.demo.swipelistview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.longluo.demo.R;
import com.longluo.demo.widget.swipelistview.BaseSwipeListViewListener;
import com.longluo.demo.widget.swipelistview.SwipeListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luolong
 * @date 2015-9-25 10:37:30
 */
public class SwipeListViewDemoActivity extends Activity {
    protected static final String TAG = "SwipeListViewDemoActivity";

    private SwipeListView mSwipeListView;
    private DataAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_swipelistview);

        initDatas();

        mSwipeListView = (SwipeListView) findViewById(R.id.id_swipelistview);
        mAdapter = new DataAdapter(this, mDatas, mSwipeListView);
        mSwipeListView.setAdapter(mAdapter);

        mSwipeListView
                .setSwipeListViewListener(new BaseSwipeListViewListener() {
                    @Override
                    public void onChoiceChanged(int position, boolean selected) {
                        Log.d(TAG, "onChoiceChanged:" + position + ", "
                                + selected);
                    }

                    @Override
                    public void onChoiceEnded() {
                        Log.d(TAG, "onChoiceEnded");
                    }

                    @Override
                    public void onChoiceStarted() {
                        Log.d(TAG, "onChoiceStarted");
                    }

                    @Override
                    public void onClickBackView(int position) {
                        Log.d(TAG, "onClickBackView:" + position);
                    }

                    @Override
                    public void onClickFrontView(int position) {
                        Log.d(TAG, "onClickFrontView:" + position);
                    }

                    @Override
                    public void onClosed(int position, boolean fromRight) {
                        Log.d(TAG, "onClosed:" + position + "," + fromRight);
                    }

                    @Override
                    public void onDismiss(int[] reverseSortedPositions) {
                        Log.d(TAG, "onDismiss");

                    }

                    @Override
                    public void onFirstListItem() {
                        Log.d(TAG, "onFirstListItem");
                    }

                    @Override
                    public void onLastListItem() {
                        Log.d(TAG, "onLastListItem");
                    }

                    @Override
                    public void onListChanged() {
                        Log.d(TAG, "onListChanged");

                        mSwipeListView.closeOpenedItems();

                    }

                    @Override
                    public void onMove(int position, float x) {
                        Log.d(TAG, "onMove:" + position + "," + x);
                    }

                    @Override
                    public void onOpened(int position, boolean toRight) {
                        Log.d(TAG, "onOpened:" + position + "," + toRight);
                    }

                    @Override
                    public void onStartClose(int position, boolean right) {
                        Log.d(TAG, "onStartClose:" + position + "," + right);
                    }

                    @Override
                    public void onStartOpen(int position, int action,
                                            boolean right) {
                        Log.d(TAG, "onStartOpen:" + position + "," + action
                                + "," + right);
                    }
                });
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i <= 'Z'; i++)
            mDatas.add((char) i + "");
    }

}
