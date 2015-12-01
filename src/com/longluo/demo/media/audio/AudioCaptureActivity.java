package com.longluo.demo.media.audio;

import java.io.File;

import com.longluo.demo.R;

import android.app.Activity;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AudioCaptureActivity extends Activity {
	private MediaRecorder mMediaRecorder = null;
	private File mAudioFile = null;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_capture);

		init();
	}

	private void init() {
		mTextView = (TextView) findViewById(R.id.tv_audio_desc);
		mAudioFile = new File(Environment.getExternalStorageDirectory(),
				"testaudio.3gp");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_audio_capture, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.start_recording_btn:
			startRecording(mAudioFile);
			break;

		case R.id.stop_recording:
			stopRecording();
			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	private void startRecording(File file) {
		if (mMediaRecorder != null) {
			mMediaRecorder.release();
		}

		mMediaRecorder = new MediaRecorder();

		mMediaRecorder.setAudioSource(AudioSource.MIC);
		mMediaRecorder.setOutputFormat(OutputFormat.THREE_GPP);
		mMediaRecorder.setAudioEncoder(AudioEncoder.AMR_WB);
		mMediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());

		try {
			mMediaRecorder.prepare();
			mMediaRecorder.start();
			mTextView
					.setText("Recording Started. Press Stop to Stop Recording.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopRecording() {
		if (mMediaRecorder != null) {
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder = null;
			mTextView.setText("Audio Recorded!");
		}
	}
}
