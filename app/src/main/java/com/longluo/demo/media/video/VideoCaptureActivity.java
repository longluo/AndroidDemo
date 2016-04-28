package com.longluo.demo.media.video;

import android.app.Activity;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.longluo.demo.R;
import com.longluo.demo.utils.ToastUtils;

import java.io.IOException;

public class VideoCaptureActivity extends Activity implements
		SurfaceHolder.Callback, OnInfoListener, OnErrorListener {
	private MediaRecorder mMediaRecorder;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private String mOutputFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_capture);

		init();
	}

	private void init() {
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setOnInfoListener(this);
		mMediaRecorder.setOnErrorListener(this);
		initMediaRecorder();

		mSurfaceView = (SurfaceView) findViewById(R.id.videoview_video_capture);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
	}

	private void initMediaRecorder() {
		mOutputFile = Environment.getExternalStorageDirectory().getPath()
				+ "/myvideo.mp4";

		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mMediaRecorder.setVideoFrameRate(20);

		mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mMediaRecorder.setOutputFile(mOutputFile);
		mMediaRecorder.setMaxDuration(10000);
		mMediaRecorder.setMaxFileSize(5000000);
	}

	private void releaseMediaRecorder() {
		if (mMediaRecorder != null) {
			mMediaRecorder.reset();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}

	private void prepareMediaRecorder() {
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

		try {
			mMediaRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			finish();
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaRecorder();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
			try {
				mMediaRecorder.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			releaseMediaRecorder();
			ToastUtils.showShort(VideoCaptureActivity.this, "Exceeded the Recording limit. Stopping the recording");
			finish();
		}
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		try {
			mMediaRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		releaseMediaRecorder();
		ToastUtils.showShort(VideoCaptureActivity.this, "Error occurred in Recording. Stopping the recording");
		finish();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		prepareMediaRecorder();
		mMediaRecorder.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

}
