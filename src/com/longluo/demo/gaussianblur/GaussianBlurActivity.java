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
import android.os.Bundle;
import android.view.View;
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

	static {
		System.loadLibrary("ndkblur");
	}

	private native static void functionToBlur(Bitmap bitmapOut, int radius,
			int threadCount, int threadIndex, int round);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gaussianblur);

		initData();
		initViews();

		blurTest();
	}

	private static class NativeBlurTask implements Callable<Void> {
		private final Bitmap _bitmapOut;
		private final int _radius;
		private final int _totalCores;
		private final int _coreIndex;
		private final int _round;

		public NativeBlurTask(Bitmap bitmapOut, int radius, int totalCores,
				int coreIndex, int round) {
			_bitmapOut = bitmapOut;
			_radius = radius;
			_totalCores = totalCores;
			_coreIndex = coreIndex;
			_round = round;
		}

		@Override
		public Void call() throws Exception {
			functionToBlur(_bitmapOut, _radius, _totalCores, _coreIndex, _round);
			return null;
		}
	}

	public Bitmap blur(Bitmap original, float radius) {
		Bitmap bitmapOut = original.copy(Bitmap.Config.ARGB_8888, true);

		int cores = Runtime.getRuntime().availableProcessors();

		ArrayList<NativeBlurTask> horizontal = new ArrayList<NativeBlurTask>(
				cores);
		ArrayList<NativeBlurTask> vertical = new ArrayList<NativeBlurTask>(
				cores);
		for (int i = 0; i < cores; i++) {
			horizontal.add(new NativeBlurTask(bitmapOut, (int) radius, cores,
					i, 1));
			vertical.add(new NativeBlurTask(bitmapOut, (int) radius, cores, i,
					2));
		}

		try {
			Executors.newFixedThreadPool(cores).invokeAll(horizontal);
		} catch (InterruptedException e) {
			return bitmapOut;
		}

		try {
			Executors.newFixedThreadPool(cores).invokeAll(vertical);
		} catch (InterruptedException e) {
			return bitmapOut;
		}
		return bitmapOut;
	}

	private void blurTest() {
		mTestView = (ImageView) findViewById(R.id.iv_blur_test);

		final NativeBlur blur = new NativeBlur();
		mTestView.setImageBitmap(blur(
				getBitmapFromAsset(this, "android_platform_256.png"), 5));
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

}
