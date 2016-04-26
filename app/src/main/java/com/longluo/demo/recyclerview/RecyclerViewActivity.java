package com.longluo.demo.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.longluo.demo.R;

/**
 * RecyclerViewActivity
 *
 * @author luolong
 * @version 0.1
 * @date 2015-6-10 上午10:48:11
 */
public class RecyclerViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initHorizontal();
        initVertical();
    }

    private void initHorizontal() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_horizontal);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        String[] dataset = new String[10];
        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }

        // 创建Adapter，并指定数据集
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataset);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
    }

    public void initVertical() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_vertical);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        String[] dataset = new String[15];
        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }
        // 创建Adapter，并指定数据集
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataset);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
    }

}
