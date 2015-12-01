package com.longluo.demo.media.audio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.longluo.demo.R;

public class AudioRecordActivity extends Activity {
	private static final int RECORD_AUDIO = 0x01;
	private Button mRecordBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_record);
		init();
	}

	private void init() {
		mRecordBtn = (Button) findViewById(R.id.btn_audio_record);
		mRecordBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent audioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
				 startActivityForResult(audioIntent, RECORD_AUDIO);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == RECORD_AUDIO) {
				Uri audioUri = data.getData();
			}
		}
	}

}
