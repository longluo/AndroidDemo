package com.longluo.demo.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.longluo.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luolong on 2016/6/7.
 */
public class RecyclerViewDemo2Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;

    private DemoAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo_2);

        initDatas();
        initViews();
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();

        for (int i = 'A'; i < 'K'; i++) {
            mDatas.add(" " + (char) i);
        }
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new DemoAdapter(this, mDatas);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
