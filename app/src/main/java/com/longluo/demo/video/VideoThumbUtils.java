package com.longluo.demo.video;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;
import android.util.Log;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

public class VideoThumbUtils {
    private static String TAG = "VideoThumbUtils";
    public static HashMap<ThumbVideoKey, SoftReference<Bitmap>> sVideoThumbCache = new HashMap<ThumbVideoKey, SoftReference<Bitmap>>();
    public static FileCache sFileCache;

    public static Bitmap getVideoThumbById(Context context, long videoId, int kind) {
        Log.d(TAG, "getVideoThumbById videoId=" + videoId + " kind=" + kind);

        if (null == sFileCache) {
            sFileCache = new FileCache(context.getApplicationContext());
        }
        Bitmap bitmap = null;
        ThumbVideoKey videoKey = new ThumbVideoKey(videoId, kind == Video.Thumbnails.MINI_KIND);
        String hashcode = String.valueOf(videoKey.hashCode());
        Uri uri = ContentUris.withAppendedId(VideoUtils.EXTERNAL_VIDEO_URI, videoId);
        Cursor cursor = null;
        String videoPath = null;
        try {
            cursor = VideoUtils.query(context, uri);
            if (VideoUtils.checkCursorValid(cursor)) {
                videoPath = cursor.getString(VideoUtils.DATA_COL_INDEX);
            } else {
                return null;
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        Log.d(TAG, "hashcode=" + hashcode);
        bitmap = getThumbFromFileCache(hashcode);
        Log.d(TAG, "bitmap=" + bitmap);

        if (null == bitmap) {
            bitmap = createVideoThumbnail(videoPath, kind, -1);
            sFileCache.put(hashcode, bitmap);
        }

        if (null != bitmap) {
            synchronized (sVideoThumbCache) {
                addBitmapToCache(videoKey, bitmap);
            }
        }
        return bitmap;
    }

    public static void addBitmapToCache(ThumbVideoKey videoKey, Bitmap bitmap) {
        SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(bitmap);
        sVideoThumbCache.put(videoKey, softBitmap);
    }

    public static Bitmap getCachedVideoThumb(long videoId, int kind) {
        Log.d(TAG, "getCachedVideoThumb videoId=" + videoId + " kind=" + kind);
        SoftReference<Bitmap> softBitmap = null;
        Bitmap bitmap = null;
        synchronized (sVideoThumbCache) {
            ThumbVideoKey videoKey = new ThumbVideoKey(videoId, kind == Video.Thumbnails.MINI_KIND);
            softBitmap = sVideoThumbCache.get(videoKey);
            if (softBitmap != null) {
                bitmap = (Bitmap) softBitmap.get();
            }
        }
        return bitmap;
    }

    public static void saveThumbnail(Context ctx, long timeUs, long videoId) {
        Log.d(TAG, "saveThumbnail videoId=" + videoId + " timeUs=" + timeUs);
        try {
            Uri videoUri = ContentUris.withAppendedId(VideoUtils.EXTERNAL_VIDEO_URI, videoId);
            Cursor cursor = null;
            String path = null;
            try {
                cursor = VideoUtils.query(ctx, videoUri);
                path = cursor.getString(VideoUtils.DATA_COL_INDEX);
            } finally {
                if (null != cursor) {
                    cursor.close();
                }
            }
            Bitmap bitmap = null;

            Log.d(TAG, "path=" + path);
            if (path != null) {
                bitmap = createVideoThumbnail(path, Video.Thumbnails.MINI_KIND, timeUs);
            }

            if (bitmap != null) {
                synchronized (sVideoThumbCache) {
                    ThumbVideoKey videoKey = new ThumbVideoKey(videoId, true);
                    addBitmapToCache(videoKey, bitmap);
                    String hashcode = String.valueOf(videoKey.hashCode());
                    sFileCache.put(hashcode, bitmap);
                    Log.d(TAG, "hashcode =" + hashcode + " kind mini");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap createVideoThumbnail(String filePath, int kind, long timeUs) {
        Log.d(TAG, "createVideoThumbnail filePath=" + filePath + " kind=" + kind);
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(timeUs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != retriever) {
                retriever.release();
            }
        }

        Log.d(TAG, "bitmap=" + bitmap);

        if (bitmap == null) {
            return null;
        }

        if (kind == Images.Thumbnails.MINI_KIND) {
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }
        }

        return bitmap;
    }

    public static Bitmap getThumbFromFileCache(String key) {
        Bitmap bitmap = null;
        // search sdcard
        if (bitmap == null) {
            File file = sFileCache.getFile(key);
            if (file != null) {
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), null);
            }
        }

        return bitmap;
    }
}
