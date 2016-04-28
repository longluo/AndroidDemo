package com.longluo.demo.filemanager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.longluo.demo.R;

/**
 * Created by luolong on 2016/4/28.
 */
public class FileManagerDemoActivity extends Activity {
    private Button mBtnOpenFileManager, mBtnChooseImage, mBtnChooseVideo;

    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filemanager_demo);

        initViews();

    }

    private void initViews() {
        mBtnOpenFileManager = (Button) findViewById(R.id.btn_open_filemanager);
        mBtnOpenFileManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnChooseImage = (Button) findViewById(R.id.btn_choose_image);
        mBtnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnChooseVideo = (Button) findViewById(R.id.btn_choose_video);
        mBtnChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mTvContent = (TextView) findViewById(R.id.tv_desc);

    }

    private void updateTextContent(String content) {
        Log.d("Demo", "updateTextContent, content=" + content);

        mTvContent.setText(content);

    }


}
