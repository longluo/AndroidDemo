package com.longluo.demo.video;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;

import com.longluo.demo.R;

/**
 * Created by luolong on 2016/6/1.
 */
public class VideoFeedDemoActivity extends Activity {

    public static String VIDEO_FEED_URL = "http://m2.qiushibaike.com/article/list/video";

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_video_feed);

        initViews();
        initDatas();

    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_rv_video_feed);


    }

    private void initDatas() {


    }


/*    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//        super.onScrollStateChanged(recyclerView, newState);


    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);


    }*/


}
