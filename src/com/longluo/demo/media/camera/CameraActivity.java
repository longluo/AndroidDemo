package com.longluo.demo.media.camera;

import com.longluo.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by luolong on 15/11/26.
 */
public class CameraActivity extends Activity {
	final int CAPTURE_PHOTO = 0x01;

	private Button mCaptureBtn;
	private Button mSelfieBtn;
	private ImageView mPicView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		initViews();
		init();
	}

	private void initViews() {
		mCaptureBtn = (Button) findViewById(R.id.btn_launch);
		mSelfieBtn = (Button) findViewById(R.id.btn_selfie_launch);
		mPicView = (ImageView) findViewById(R.id.iv_photo);
	}

	private void init() {
		mCaptureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent captureIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(captureIntent, CAPTURE_PHOTO);
			}
		});
		
		mSelfieBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent captureIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				
				startActivityForResult(captureIntent, CAPTURE_PHOTO);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (RESULT_OK == resultCode) {
			if (requestCode == CAPTURE_PHOTO) {
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				mPicView.setImageBitmap(photo);
			}
		}
	}

}
