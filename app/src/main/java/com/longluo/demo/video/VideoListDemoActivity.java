package com.longluo.demo.video;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.longluo.demo.R;
import com.longluo.demo.utils.ToastUtils;

/**
 * Created by luolong on 2016/6/12.
 */
public class VideoListDemoActivity extends AppCompatActivity {

    private ListView mListView;
    private VideoListAdapter mAdapter;

    private Cursor mCursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list_demo);

        initViews();
        initDatas();

    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.video_list_view);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.show(VideoListDemoActivity.this, "pos=" + position + ",id=" + id, Toast.LENGTH_LONG);

            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.show(VideoListDemoActivity.this, "pos=" + position + ",id=" + id, Toast.LENGTH_LONG);

                return false;
            }
        });


    }

    private void initDatas() {
        VideoUtils.closeCursor(mCursor);
        mCursor = VideoUtils.query(this, VideoUtils.EXTERNAL_VIDEO_URI, false);

        mAdapter = new VideoListAdapter(this, R.layout.layout_video_list_item, mCursor);

        mListView.setAdapter(mAdapter);

    }


}
