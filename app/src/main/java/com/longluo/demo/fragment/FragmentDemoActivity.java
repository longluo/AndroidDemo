package com.longluo.demo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.longluo.demo.R;

public class FragmentDemoActivity extends Activity {
    private static final String TAG = FragmentDemoActivity.class.getSimpleName();

    private Button mBtnListTitle;
    private Button mBtnContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_demo);

        initViews();


    }

    private void initViews() {
        mBtnListTitle = (Button) findViewById(R.id.btn_list_title);
        mBtnListTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentDemoActivity.this, ListTitleFragment.class);
                startActivity(intent);
            }
        });

        mBtnContent = (Button) findViewById(R.id.btn_content);
        mBtnContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentDemoActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });

    }


}
