package com.longluo.demo.media.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.longluo.demo.R;
import com.longluo.demo.util.ToastUtils;

public class CaptureImageActivity extends Activity implements
		SurfaceHolder.Callback {
	private SurfaceHolder mSurfaceHolder;
	private SurfaceView mSurfaceView;
	private Camera mCamera;

	PictureCallback mPicHandler = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			File picFile = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/sampleimage.jpg");

			try {
				FileOutputStream fos = new FileOutputStream(picFile.toString());
				fos.write(data);
				fos.close();
				ToastUtils
						.showShort(CaptureImageActivity.this, "Picture Saved");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_image);

		init();
	}

	private void init() {
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_capture_image, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.capture_button:
			mCamera.takePicture(null, null, mPicHandler);
			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(mSurfaceHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		try {
			mCamera.setPreviewDisplay(mSurfaceHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

}
