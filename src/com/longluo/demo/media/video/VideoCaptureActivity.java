package com.longluo.demo.media.video;

import android.app.Activity;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.view.SurfaceHolder;

public class VideoCaptureActivity extends Activity implements SurfaceHolder.Callback, OnInfoListener, OnErrorListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {

		
	}
	
	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	

	
}
