package com.longluo.demo.video;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Video;
import android.text.TextUtils;
import android.util.Log;

import com.longluo.demo.R;

/**
 * Created by luolong on 2016/6/6.
 */
public class VideoUtils {

    private static final String TAG = "test00";

    public static final String SORT_ORDER = null;

    public static final Uri EXTERNAL_VIDEO_URI = Video.Media.EXTERNAL_CONTENT_URI;

    public static final String[] PROJECTION = new String[]{
            Video.Media._ID,//
            Video.Media.DATA,//
            Video.Media.DATE_TAKEN,//
            Video.Media.TITLE,//
            Video.Media.DISPLAY_NAME,//
            Video.Media.DURATION,//
            Video.Media.BOOKMARK,//
            Video.Media.DATE_TAKEN,//
            Video.Media.MIME_TYPE,//
            Video.Media.SIZE,//
            Video.Media.HEIGHT,//
            Video.Media.WIDTH};

    public static final int ID_COL_INDEX = 0;
    public static final int DATA_COL_INDEX = 1;
    public static final int DATA_TAKEN_COL_INDEX = 2;
    public static final int TITLE_COL_INDEX = 3;
    public static final int DISPLAY_NAME_COL_INDEX = 4;
    public static final int DURATION_COL_INDEX = 5;
    public static final int BOOKMARK_COL_INDEX = 6;
    public static final int DATE_TAKEN_COL_INDEX = 7;
    public static final int MIME_TYPE_COL_INDEX = 8;
    public static final int SIZE_COL_INDEX = 9;
    public static final int HEIGHT_COL_INDEX = 10;
    public static final int WIDTH_COL_INDEX = 11;


    public static Cursor query(Context context, Uri specifyUri) {
        Log.d(TAG, "specifyUri=" + specifyUri);
        Cursor cursor = null;

        if (context != null && specifyUri != null) {
            StringBuilder where = new StringBuilder();

            where.append(Video.Media.DATA).append(" not null AND ").append(Video.Media.SIZE).append(" > '0'");

            cursor = context.getContentResolver().query(specifyUri, PROJECTION, where.toString(), null, SORT_ORDER);

            if (cursor != null) {
                cursor.moveToFirst();
                Log.d(TAG, "cursor cnt=" + cursor.getCount());
            }
        }

        return cursor;
    }

    public static Cursor query(Context context, Uri specifyUri, String selection, boolean isCameraVideo) {
        Log.d(TAG, "specifyUri=" + specifyUri + " selection=" + selection + "isCameraVideo=" + isCameraVideo);

        Cursor cursor = null;
        String SORT_BY_TIME_DESC = Video.Media.DATE_MODIFIED + " DESC";

        if (context != null && specifyUri != null) {
            StringBuilder where = new StringBuilder();

            if (selection != null) {
                where.append(selection).append(" AND ");
            }

            where.append(Video.Media.DATA).append(" not null AND ").append(Video.Media.SIZE).append(" > '0'");

            where.append(" AND ").append(Video.Media.BUCKET_DISPLAY_NAME).append(isCameraVideo ? " = " : " <> ")
                    .append("'Camera'");

            Log.d(TAG, "where=" + where.toString());

            cursor = context.getContentResolver().query(specifyUri, PROJECTION, where.toString(), null,
                    isCameraVideo ? SORT_BY_TIME_DESC : SORT_ORDER);

            if (cursor != null) {
                cursor.moveToFirst();
                Log.d(TAG, "cursor cnt=" + cursor.getCount());
            }
        }

        return cursor;
    }

    public static Cursor query(Context context, Uri specifyUri, boolean isCameraVideo) {
        return query(context, specifyUri, null, isCameraVideo);
    }

    /**
     * parse the file name from: firstly, TITLE_COL_INDEX; secondly,
     * DISPLAY_NAME_COL_INDEX; thirdly, DATA_COL_INDEX
     *
     * @return String
     */
    public static String getFileNameByAnyWay(Cursor cursor) {

        if (!checkCursorValid(cursor)) {
            Log.e(TAG, "getFileNameByAnyWay, cursor is null, return null.");
            return null;
        }

        String wholeFilePath = cursor.getString(VideoUtils.DATA_COL_INDEX);
        if (!TextUtils.isEmpty(wholeFilePath)) {
            return getFileNameByWholeFilePath(wholeFilePath);
        }

        return null;
    }


    public static void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
    }

    public static Uri getUriFromCursor(Cursor cursor) {

        Uri uri = null;
        if (checkCursorValid(cursor) && checkCursorPosition(cursor)) {
            long videoId = cursor.getLong(VideoUtils.ID_COL_INDEX);
            uri = ContentUris.withAppendedId(EXTERNAL_VIDEO_URI, videoId);
        }

        return uri;
    }

    public static Uri getUriById(final long id) {
        return ContentUris.withAppendedId(EXTERNAL_VIDEO_URI, id);
    }

    /**
     * check cursor is valid
     *
     * @param cursor
     * @return boolean
     */
    public static boolean checkCursorValid(Cursor cursor) {

        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }

        return false;
    }

    /**
     * check cursor position is valid
     *
     * @param cursor
     * @return boolean
     */
    public static boolean checkCursorPosition(Cursor cursor) {

        if (cursor != null && cursor.getPosition() >= 0 && cursor.getPosition() < cursor.getCount()) {
            return true;
        }

        return false;
    }

    /**
     * parse the file name from whole file path
     *
     * @param wholeFilePath
     * @return String
     */
    private static String getFileNameByWholeFilePath(String wholeFilePath) {

        if (null != wholeFilePath) {
            int start = wholeFilePath.lastIndexOf('/');
            int end = wholeFilePath.lastIndexOf('.');
            if (start != -1 && end != -1) {
                return wholeFilePath.substring(start + 1, end);
            }
        }

        return null;
    }

    public static String formatDuration(final Context context, int durationMs) {
        int duration = durationMs / 1000;
        int h = duration / 3600;
        int m = (duration - h * 3600) / 60;
        int s = duration - (h * 3600 + m * 60);
        String durationValue;
        if (h == 0) {
            // set seconds to be 1s at least if durationMs > 0
            if (durationMs > 0 && duration <= 0) {
                s = 1;
            }
            // set time to be m:s if hours == 0
            durationValue = String.format(context.getString(R.string.details_ms), m, s);
        } else {
            // set time to be h:m:s if hours > 0
            durationValue = String.format(context.getString(R.string.details_hms), h, m, s);
        }
        return durationValue;
    }

    public static String parseString(String text) {
        try {
            boolean isGBK = false;
            byte[] data = text.getBytes("iso8859-1");
            int firsthalf = 0;
            int secondhalf = 0;
            int thirdhalf = 0;
            int fourthhalf = 0;
            for (int i = 0; i < data.length; i++) {
                firsthalf = data[i] & 0xF0;
                secondhalf = 0;
                thirdhalf = 0;
                fourthhalf = 0;
                if (firsthalf == 0xA0 || firsthalf == 0xB0) {
                    isGBK = true;
                    break;
                } else if (firsthalf == 0xC0 || firsthalf == 0xD0) {
                    if (i + 1 > data.length - 1) {
                        isGBK = true;
                        break;
                    } else {
                        secondhalf = data[i + 1] & 0xC0;
                        if (secondhalf != 0x80) {
                            isGBK = true;
                            break;
                        }
                    }
                } else if (firsthalf == 0xE0) {
                    if (i + 2 > data.length - 1) {
                        isGBK = true;
                        break;
                    } else {
                        secondhalf = data[i + 1] & 0xC0;
                        thirdhalf = data[i + 2] & 0xC0;
                        if (secondhalf != 0x80 || thirdhalf != 0x80) {
                            isGBK = true;
                            break;
                        }
                    }
                } else if (firsthalf == 0xF0) {
                    if (i + 3 > data.length - 1) {
                        isGBK = true;
                        break;
                    } else {
                        secondhalf = data[i + 1] & 0xC0;
                        thirdhalf = data[i + 2] & 0xC0;
                        fourthhalf = data[i + 3] & 0xC0;
                        if (secondhalf != 0x80 || thirdhalf != 0x80 || fourthhalf != 0x80) {
                            isGBK = true;
                            break;
                        }
                    }
                }
            }
            if (isGBK) {
                text = new String(data, "GBK");
            }
        } catch (Exception e) {
        }
        return text;
    }

}
