package com.longluo.demo.filemanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.longluo.demo.R;

/**
 * Created by luolong on 2016/6/2.
 */
public class FileManagerDemoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filemanager_demo);

        initDatas();
        initViews();

    }

    private void initDatas() {

    }

    private void initViews() {

        mRecyclerView = (RecyclerView) findViewById(R.id.id_file_recyclerview);




    }


}
