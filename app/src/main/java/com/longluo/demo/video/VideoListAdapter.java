package com.longluo.demo.video;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.MediaStore.Video;
import android.support.v4.widget.ResourceCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longluo.demo.DemoApp;
import com.longluo.demo.R;
import com.longluo.demo.threadpool.Future;
import com.longluo.demo.threadpool.FutureListener;
import com.longluo.demo.threadpool.ThreadPool;


/**
 * Created by luolong on 2016/6/8.
 */
public class VideoListAdapter extends ResourceCursorAdapter {
    private static final String TAG = "test00";

    private Context mContext;
    private Handler mHandler;
    private ThreadPool mThreadPool;

    public VideoListAdapter(Context context, int layout, Cursor cursor) {
        super(context, layout, cursor, true);

        mContext = context;
        mHandler = new Handler();
        mThreadPool = ((DemoApp) mContext.getApplicationContext()).getThreadPool();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        ViewHolder holder = new ViewHolder();
        holder.videoImage = (ImageView) view.findViewById(R.id.thumb_icon);
        holder.videoName = (TextView) view.findViewById(R.id.videoname);
        holder.bookmark = (TextView) view.findViewById(R.id.bookmark);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (null == viewHolder) {
            return;
        }

        final long videoId = cursor.getLong(VideoUtils.ID_COL_INDEX);
        viewHolder.videoId = videoId;

        // get title
        viewHolder.videoName.setText(VideoUtils.getFileNameByAnyWay(cursor));

        // get bookmark
        String time = null;
        int bookmark = cursor.getInt(VideoUtils.BOOKMARK_COL_INDEX);
        if (bookmark > 0) {
            time = VideoUtils.formatDuration(context, bookmark);
        }

        // get duration and generate the time string
        int duration = cursor.getInt(VideoUtils.DURATION_COL_INDEX);
        if (duration > 0) {
            if (time != null) {
                time += "/";
            } else {
                time = "";
            }
            time += VideoUtils.formatDuration(context, duration);
            viewHolder.bookmark.setText(time);
        } else {
            viewHolder.bookmark.setText("");
        }

        final int kind = Video.Thumbnails.MINI_KIND;

        Bitmap bitmap = VideoThumbUtils.getCachedVideoThumb(videoId, kind);

        Log.d(TAG, " bitmap=" + bitmap);

        if (null == bitmap) {
            mThreadPool.submit(new ThreadPool.Job<Bitmap>() {
                public Bitmap run(ThreadPool.JobContext jc) {
                    return VideoThumbUtils.getVideoThumbById(mContext, videoId, kind);
                }
            }, new VideoThumbListener(videoId, viewHolder));
        }

        if (null != bitmap) {
            viewHolder.videoImage.setImageBitmap(bitmap);
        } else {

//            viewHolder.videoImage.setImageDrawable(mContext.getResources().getDrawable(
//                    ImgUtils.getDefaultDrawable(ImgUtils.LOCAL_SMALL_LAND)));

        }

    }


    class ViewHolder {
        ImageView videoImage;
        TextView videoName;
        TextView bookmark;
        long videoId;
    }

    private class VideoThumbListener implements Runnable, FutureListener<Bitmap> {
        Future<Bitmap> mFuture;
        ViewHolder mViewHolder;
        long mVideoId;

        public VideoThumbListener(long videoId, ViewHolder viewHolder) {
            mViewHolder = viewHolder;
            mVideoId = videoId;
        }

        public void run() {
            Bitmap bitmap = mFuture.get();
            if (null != bitmap) {
                mViewHolder.videoImage.setImageBitmap(bitmap);
            }
        }

        public void onFutureDone(Future<Bitmap> future) {
            mFuture = future;
            Log.d(TAG, "onFutureDone id=" + mVideoId + " ViewHolder Id=" + mViewHolder.videoId);
            if (mVideoId == mViewHolder.videoId) {
                mHandler.post(this);
            } else {
                Log.d(TAG, "videoId has changed, do not setImage");
            }
        }
    }


}
