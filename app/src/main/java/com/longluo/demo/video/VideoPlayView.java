package com.longluo.demo.video;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.longluo.demo.R;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayView extends RelativeLayout implements SurfaceHolder.Callback {
    private static final String TAG = VideoPlayView.class.getSimpleName();
    private Context mContext;
    private FrameLayout mContentView;
    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;

    private int mCurPlayPositon = 0;
    private boolean mLooping = false;
    private boolean mAutoPlay = true;

    private String mVideoPtah = null;
    private int mVideoWidth;
    private int mVideoHeight;
    private boolean mHasError = false;

    public VideoPlayView(Context context) {
        this(context, null);
    }

    public VideoPlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * 设置是否自动播放
     *
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        mAutoPlay = autoPlay;
    }

    /**
     * @param videoPath      视频路径
     * @param looping        是否循环播放
     * @param playviewWidth  播放view的宽度
     * @param playviewHeight 播放view的高度
     */
    public void setParams(String videoPath, boolean looping, int playviewWidth, int playviewHeight) {
        mVideoPtah = videoPath;
        mLooping = looping;
        setPlayViewSize(playviewWidth, playviewHeight);
    }

    private String mCoverPath = null;

    /**
     * @param params
     */
    public void setParams(VideoPlayControllerParams params) {
        mVideoPtah = params.path;
        mLooping = params.loop;
        mCoverPath = params.coverPath;
        setPlayViewSize(params.width, params.height);
    }

    /**
     * 销毁，释放资源
     */
    public void destroy() {
        stop();
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_videoplay, this);
        mContentView = (FrameLayout) view.findViewById(R.id.content);
    }

    private void setPlayViewSize(int width, int height) {
        mHasError = false;
        LayoutParams layoutParams = (LayoutParams) mContentView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mContentView.setLayoutParams(layoutParams);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        this.mVideoWidth = width;
        this.mVideoHeight = height;
        try {
            if (mMediaPlayer != null) {
                stop();
            }
        } catch (Exception e) {

        }
        mSurfaceView = new SurfaceView(mContext) {
            @Override
            protected void onWindowVisibilityChanged(int visibility) {
                try {
                    super.onWindowVisibilityChanged(visibility);
                } catch (Throwable e) {

                }
            }
        };
        mSurfaceView.setLayoutParams(params);
        mSurfaceView.getHolder().addCallback(this);
        if (VERSION.SDK_INT < 11) {
            mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        mSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        mSurfaceView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnVideoPlayViewClickListener != null) {
                    mOnVideoPlayViewClickListener.onClick(VideoPlayView.this);
                }
            }
        });

        mContentView.addView(mSurfaceView);
    }

	/*
    public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
		this.drawCanvas(bitmap);
	}
	*/

    private void prepare() {
        if (TextUtils.isEmpty(mVideoPtah)) {
            return;
        }
        mCurPlayPositon = 0;
        try {
            if (mMediaPlayer != null) {
                try {
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                } catch (Exception e) {

                }
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(mVideoPtah);
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            mMediaPlayer.setLooping(false);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnInfoListener(mInfoListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seekTo(int position) {
        if (null != mMediaPlayer) {
            mMediaPlayer.seekTo(position);
        }
    }

    /**
     * 设置声音
     *
     * @param leftVolume
     * @param rightVolume
     */
    public void setVolume(float leftVolume, float rightVolume) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setVolume(leftVolume, rightVolume);
        }
    }

    /**
     * 设置为系统默认的声音
     */
    public void setDefaultVolume() {
        if (mMediaPlayer != null) {
            setVolume(1.0f, 1.0f);
        }
    }

    /**
     * 根据配置自己设置音量
     *
     * @param videoPlayView
     */
    public static void autoSetVolume(VideoPlayView videoPlayView) {
        if (videoPlayView == null) {
            return;
        }

        videoPlayView.setDefaultVolume();

/*        if (VideoLoadConfig.isSoundable()) {
            videoPlayView.setDefaultVolume();
        } else {
            videoPlayView.setVolume(0.0f, 0.0f);
        }*/
    }

    public void play() {
        play(mCurPlayPositon);
    }

    private void play(int position) {
        mMediaPlayer.seekTo(position);
        autoSetVolume(this);
        mMediaPlayer.start();
    }

    public void play(int start, final int end) {
        mMediaPlayer.seekTo(start);
        mMediaPlayer.start();

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (end <= mMediaPlayer.getCurrentPosition()) {
                    pause();
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask, 0, 10);

    }

    public void pause() {
        if (mMediaPlayer != null) {
            mCurPlayPositon = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.pause();
            if (null != mOnPauseListener) {
                mOnPauseListener.onPause();
            }
        }
    }

    private void hackRemove() {
        if (mSurfaceView != null && mContentView != null) {
            try {
                if (mContentView.indexOfChild(mSurfaceView) >= 0) {
                    //mContentView.removeView(mSurfaceView);
                    mContentView.removeAllViews();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        stop();
        mCurPlayPositon = 0;
    }

    public void stop() {
        mHasError = false;
        hackRemove();
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.setDisplay(null);
            }
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
            }
            mMediaPlayer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    private void drawCoverImage(SurfaceHolder holder) {
        if (!TextUtils.isEmpty(mCoverPath)) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
                File imageFile = diskCache.get(mCoverPath);
                if (imageFile != null && imageFile.exists()) {
                    Bitmap bitmap = ImageUtils.decodeBitmap(QsbkApp.mContext, mCoverPath, this.mVideoWidth, this.mVideoHeight, null);
                    if (bitmap != null) {
                        canvas.drawBitmap(bitmap, 0, 0, null);
                    }
                }
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }*/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prepare();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCurPlayPositon = 0;
        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(null);
        }

        //mSurfaceView=null;
        stop();
    }

    private OnPreparedListener mOnPreparedListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnVideoPlayViewClickListener mOnVideoPlayViewClickListener;
    private OnPauseListener mOnPauseListener;

    private OnPreparedListener mPreparedListener = new OnPreparedListener() {

        @Override
        public void onPrepared(final MediaPlayer mp) {
            mp.seekTo(0);
            if (mAutoPlay) {
                autoSetVolume(VideoPlayView.this);
                mp.start();
            }

            if (mVideoWidth != 0 && mVideoHeight != 0) {
                mSurfaceView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
            }
            if (VERSION.SDK_INT < 17) {
                postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (mOnPreparedListener != null) {
                            mOnPreparedListener.onPrepared(mp);
                        }
                    }
                }, 400l);
            }
        }
    };

    private OnCompletionListener mCompletionListener = new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mLooping && !mHasError) {
                mCurPlayPositon = 0;
                play(mCurPlayPositon);
            }
            if (mOnCompletionListener != null) {
                mOnCompletionListener.onCompletion(mp);
            }
        }
    };

    private OnErrorListener mErrorListener = new OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            boolean handle = false;
            if (extra == -2147483648 || 100 == what) {
                mHasError = true;
            }
            if (mOnErrorListener != null) {
                handle = mOnErrorListener.onError(mp, what, extra);
            }
            return handle;
        }
    };

    private OnInfoListener mInfoListener = new OnInfoListener() {

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            boolean handle = false;

            switch (what) {
                case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    if (VERSION.SDK_INT >= 17) {
                        if (mOnPreparedListener != null) {
                            mOnPreparedListener.onPrepared(mp);
                        }
                    }
                    break;

                case MediaPlayer.MEDIA_INFO_BUFFERING_END:

                    break;

                case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:

                    break;

                case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:

                    break;

                case MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:

                    break;

                case MediaPlayer.MEDIA_INFO_UNKNOWN:

                    break;

                case MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:

                    break;

                case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:

                    break;

                case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:

                    break;

                default:
                    break;
            }

            if (mOnInfoListener != null) {
                handle = mOnInfoListener.onInfo(mp, what, extra);
            }
            return handle;
        }
    };

    /**
     * If {@linkplain VERSION#SDK_INT} smaller than 17, invoked on prepared, else invoked on very first video frame for rendering.
     *
     * @param listener
     */
    public void setOnPreparedListener(OnPreparedListener listener) {
        this.mOnPreparedListener = listener;
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    public void setOnErrorListener(OnErrorListener listener) {
        this.mOnErrorListener = listener;
    }

    public void setOnVideoPlayViewClickListener(OnVideoPlayViewClickListener listener) {
        this.mOnVideoPlayViewClickListener = listener;
    }

    public void setOnPauseListener(OnPauseListener listentr) {
        mOnPauseListener = listentr;
    }

    public static interface OnVideoPlayViewClickListener {
        void onClick(VideoPlayView player);
    }

    public static interface OnPauseListener {
        void onPause();
    }


    /**
     * Video player params
     */
    public static class VideoPlayControllerParams implements Cloneable {
        public String path;
        public String coverPath;
        public boolean loop;
        public int width;
        public int height;

        public VideoPlayControllerParams() {
        }

        public VideoPlayControllerParams(String path, String coverPath, boolean loop, int width, int height) {
            this.path = path;
            this.loop = loop;
            this.width = width;
            this.height = height;
            this.coverPath = coverPath;
        }

        public VideoPlayControllerParams path(String path) {
            this.path = path;
            return this;
        }

        public VideoPlayControllerParams loop(boolean loop) {
            this.loop = loop;
            return this;
        }

        public VideoPlayControllerParams width(int width) {
            this.width = width;
            return this;
        }

        public VideoPlayControllerParams height(int height) {
            this.height = height;
            return this;
        }

        public Object clone() {
            VideoPlayControllerParams o = null;
            try {
                o = (VideoPlayControllerParams) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return o;
        }
    }

}
