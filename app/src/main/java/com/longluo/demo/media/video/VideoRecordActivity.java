package com.longluo.demo.media.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

import com.longluo.demo.R;

public class VideoRecordActivity extends Activity {
	private Button mVideoCaptureBtn;
	private VideoView mVideoView;
	private final int RECORD_VIDEO = 0x01;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_record);

		initViews();
	}

	private void initViews() {
		mVideoCaptureBtn = (Button) findViewById(R.id.btn_video_record);
		mVideoCaptureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent videoIntent = new Intent(
							MediaStore.ACTION_VIDEO_CAPTURE);
					startActivityForResult(videoIntent, RECORD_VIDEO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == RECORD_VIDEO) {
				Uri videoUri = data.getData();
				mVideoView.setVideoURI(videoUri);
				mVideoView.start();
			}
		}
	}

}
