package com.longluo.demo.widget.imageprocess;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.widget.imageprocess.view.ToneLayer;
import com.longluo.demo.widget.imageprocess.widget.ButtonBar;
import com.longluo.demo.widget.imageprocess.widget.PickerBar;


/**
 * <p>
 * 1. 在界面下方有3个按钮，分别是亮度、色彩度、饱和度 2. 点击按钮，选中一个，上面用一个滚动条显示亮度、色彩度、饱和度的值，三个用同一个滚动条。 3.
 * 选中后，滑动滚动条，调节当前选择的亮度、色彩度、饱和度。（不用真调节相机，假实现即可，但需要考虑将来可能会实现，要预留实现接口） 4.
 * 考虑一下，上面的需求可能会变化的情况，比如可能会增加减少要调节的项目，每个调节项目调节时触发的动作会修改，代码要能够很方便扩展
 * </p>
 * <p/>
 * 主界面
 *
 * @author luolong
 * @date 2015-05-21 00:20:16
 */
public class ImageProcessDemoActivity extends Activity {
    private static final String TAG = ImageProcessDemoActivity.class.getSimpleName();

    private ToneLayer mToneLayer;
    private Bitmap mBitmap;

    private ImageView mImageView;

    /**
     * 后续自定义UI控件再使用，此处不用
     */
    // private MySeekBar mSeekBar;

    private TextView mTvProgressValue;
    private SeekBar mSeekBar;

    private ButtonBar mButtonBar;
    private PickerBar mPickerBar;

    /**
     * SeekBar Middle Value
     */
    public static final int MIDDLE_VALUE = 127;

    /**
     * SeekBar Max Value
     */
    public static final int MAX_VALUE = 255;


    public static int mColorFlag = 0;

    public static void setFlag(int flag) {
        mColorFlag = flag;
    }

    private SeekBar.OnSeekBarChangeListener mChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_imageprocess_demo);

        initDatas();
        initViews();
        initListeners();
    }

    private void initDatas() {
        mToneLayer = new ToneLayer(this);
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.octocat);
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.iv_picture);
        mImageView.setImageBitmap(mBitmap);

        // mSeekBar = (MySeekBar) findViewById(R.id.sb_ctrl);

        mTvProgressValue = (TextView) findViewById(R.id.tv_progressValue);

        mSeekBar = (SeekBar) findViewById(R.id.sb_ctrl);
        mSeekBar.setMax(MAX_VALUE);
        mSeekBar.setProgress(MIDDLE_VALUE);

        mButtonBar = (ButtonBar) findViewById(R.id.color_btns);

//		mPickerBar = (PickerBar) findViewById(R.id.pb_process);
    }

    private void initListeners() {
        // mSeekBar.setOnProgressChangeListener(mOnSeekBarChangeListener);
        // mSeekBar.setOnSeekBarChangeListener(mChangeListener);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int flag = (Integer) mSeekBar.getTag();
                mTvProgressValue.setText(String.valueOf(progress));

                Log.d(TAG, "onProgressChanged, progress=" + progress + ",flag=" + flag);

                handleImage(progress, flag);
            }
        });

        mSeekBar.setTag(ToneLayer.FLAG_LUM);

        // 在这里控制每个按钮触发的动作
        mButtonBar
                .setOnButtonBarClickListener(new ButtonBar.buttonBarClickListener() {

                    @Override
                    public void btn3Click() {
                        Log.d(TAG, "btn3 Click 饱和度");
                        mSeekBar.setTag(ToneLayer.FLAG_SATURATION);
                    }

                    @Override
                    public void btn2Click() {
                        Log.d(TAG, "btn2 Click 色彩度");
                        mSeekBar.setTag(ToneLayer.FLAG_HUE);
                    }

                    @Override
                    public void btn1Click() {
                        Log.d(TAG, "btn1 Click 亮度");
                        mSeekBar.setTag(ToneLayer.FLAG_LUM);
                    }
                });

		
		/*
        mPickerBar.
				setOnButtonBarClickListener(new PickerBar.buttonBarClickListener() {

					@Override
					public void btn3Click() {
						Log.d(Utils.TAG, "btn3 Click 饱和度");
						mSeekBar.setTag(ToneLayer.FLAG_SATURATION);
					}

					@Override
					public void btn2Click() {
						Log.d(Utils.TAG, "btn2 Click 色彩度");
						mSeekBar.setTag(ToneLayer.FLAG_HUE);
					}

					@Override
					public void btn1Click() {
						Log.d(Utils.TAG, "btn1 Click 亮度");
						mSeekBar.setTag(ToneLayer.FLAG_LUM);
					}
				});
		*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected void handleImage(int progress, int flag) {
        Log.d(TAG, "handleImage, progress=" + progress + ",flag=" + flag);

        switch (flag) {
            case ToneLayer.FLAG_SATURATION:
                mToneLayer.setSaturation(progress);
                break;

            case ToneLayer.FLAG_LUM:
                mToneLayer.setLum(progress);
                break;

            case ToneLayer.FLAG_HUE:
                mToneLayer.setHue(progress);
                break;

            default:
                break;
        }

        mImageView.setImageBitmap(mToneLayer.handleImage(mBitmap, mColorFlag));
    }

//	private onSeekBarProgressChangeListener mOnSeekBarChangeListener = new onSeekBarProgressChangeListener() {
//		@Override
//		public void onSeekBarProgressChange(int progress) {
//			Log.d(Utils.TAG, "onSeekBarProgressChange, progrss=" + progress);
//
//		}
//	};

}
