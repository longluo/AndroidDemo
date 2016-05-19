package com.longluo.demo.gaussianblur;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.longluo.demo.R;
import com.longluo.demo.grahpic.StackBlurManager;

public class GaussianBlurActivity extends Activity {

	private ImageView _imageView;
	private SeekBar _seekBar;
	private ToggleButton _toggleButton;
	private Spinner _typeSelectSpinner;

	private StackBlurManager _stackBlurManager;

	private String IMAGE_TO_ANALYZE = "android_platform_256.png";

	private int blurMode;

	private ImageView mTestView;

	private ImageView mWhiteImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gaussianblur);

		initData();
		initViews();

		blurTest();
		whiteImageTest();
	}

	private void initData() {
		_stackBlurManager = new StackBlurManager(getBitmapFromAsset(this,
				"android_platform_256.png"));
	}

	private void initViews() {
		_imageView = (ImageView) findViewById(R.id.iv_gaussianblur);

		_seekBar = (SeekBar) findViewById(R.id.blur_amount);

		_seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				onBlur();
			}
		});

		_toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		_toggleButton
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							IMAGE_TO_ANALYZE = "image_transparency.png";
							_stackBlurManager = new StackBlurManager(
									getBitmapFromAsset(getApplicationContext(),
											IMAGE_TO_ANALYZE));
							onBlur();
						} else {
							IMAGE_TO_ANALYZE = "android_platform_256.png";
							_stackBlurManager = new StackBlurManager(
									getBitmapFromAsset(getApplicationContext(),
											IMAGE_TO_ANALYZE));
							onBlur();
						}
					}
				});

		_typeSelectSpinner = (Spinner) findViewById(R.id.typeSelectSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.blur_modes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_typeSelectSpinner.setAdapter(adapter);
		_typeSelectSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						setBlurMode(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
	}

	private Bitmap getBitmapFromAsset(Context context, String strName) {
		AssetManager assetManager = context.getAssets();
		InputStream istr;
		Bitmap bitmap = null;
		try {
			istr = assetManager.open(strName);
			bitmap = BitmapFactory.decodeStream(istr);
		} catch (IOException e) {
			return null;
		}
		return bitmap;
	}

	public void setBlurMode(int mode) {
		this.blurMode = mode;
		onBlur();
	}

	private void onBlur() {
		int radius = _seekBar.getProgress() * 5;
		switch (blurMode) {
		case 0:
			_imageView.setImageBitmap(_stackBlurManager.process(radius));
			break;

		case 1:
			_imageView
					.setImageBitmap(_stackBlurManager.processNatively(radius));
			break;

		case 2:
			// _imageView.setImageBitmap(_stackBlurManager.processRenderScript(
			// this, radius));
			break;

		default:
			break;
		}
	}

	private void blurTest() {
		mTestView = (ImageView) findViewById(R.id.iv_blur_test);

		final NativeBlur nativeBlur = new NativeBlur();
		mTestView.setImageBitmap(nativeBlur.blur(
				getBitmapFromAsset(this, "android_platform_256.png"), 40));
	}

	private void whiteImageTest() {
		mWhiteImageView = (ImageView) findViewById(R.id.iv_white_blur_test);

		mWhiteImageView.setDrawingCacheEnabled(true);
		
		mWhiteImageView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		mWhiteImageView.layout(0, 0, mWhiteImageView.getMeasuredWidth(), mWhiteImageView.getMeasuredHeight());
		
		mWhiteImageView.buildDrawingCache(true);
		
		Bitmap bmp = Bitmap.createBitmap(mWhiteImageView.getDrawingCache());
		
		mWhiteImageView.setDrawingCacheEnabled(false);
		
		final NativeBlur nativeBlur = new NativeBlur();
		mWhiteImageView.setImageBitmap(nativeBlur.blur(bmp, 40));
	}

}
