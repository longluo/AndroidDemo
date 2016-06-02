package com.longluo.demo.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.longluo.demo.R;

/**
 * RecyclerViewActivity
 *
 * @author luolong
 * @version 0.1
 * @date 2015-6-10 上午10:48:11
 */
public class RecyclerViewDemoActivity extends Activity {
    private RecyclerView mHorizonRecyclerView;
    private RecyclerView mVerticalRecyclerView;

    private RecyclerViewAdapter mHorizonAdapter;
    private RecyclerViewAdapter mVerticalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initHorizontal();
        initVertical();

        initEvent();

    }

    private void initHorizontal() {
        mHorizonRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_horizontal);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        // 设置布局管理器
        mHorizonRecyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        String[] dataset = new String[10];
        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }

        // 创建Adapter，并指定数据集
        mHorizonAdapter = new RecyclerViewAdapter(dataset);
        // 设置Adapter
        mHorizonRecyclerView.setAdapter(mHorizonAdapter);
    }

    public void initVertical() {
        mVerticalRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_vertical);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        mVerticalRecyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        String[] dataset = new String[15];

        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }

        // 创建Adapter，并指定数据集
        mVerticalAdapter = new RecyclerViewAdapter(dataset);
        // 设置Adapter
        mVerticalRecyclerView.setAdapter(mVerticalAdapter);
    }

    private void initEvent() {
        mHorizonAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewDemoActivity.this, "Horizontal " + position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerViewDemoActivity.this, "Horizontal " + position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mVerticalAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewDemoActivity.this, "Vertical " + position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerViewDemoActivity.this, "Vertical " + position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


}
