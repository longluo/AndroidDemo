package com.longluo.demo.media.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class VideoRecordActivity extends Activity {
	VideoView mVideoView;
	final int RECORD_VIDEO = 0x01;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
