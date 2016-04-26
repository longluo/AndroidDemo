package com.longluo.demo.viewgroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.longluo.demo.R;

import java.util.Arrays;
import java.util.List;

public class ViewGroupDemoActivity extends Activity {

    private List<String> mDatas = Arrays.asList("Android", "Java");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroup_demo);

        // setContentView(R.layout.activity_main);
        // setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main,
        // R.id.id_txt, mDatas));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
