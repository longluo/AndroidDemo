package com.longluo.demo.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCache {
    private static final String TAG = "FileCache";
    public static final String CACHE_DIR = "Android/data/video/.local_video_thumb/";

    private File cacheDir;

    public FileCache(Context context) {

        // Find the dir to save cached images

        cacheDir = context.getCacheDir();

        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        Log.d(TAG, "cache dir: " + cacheDir.getAbsolutePath());
    }

    public File getFile(String key) {
        File f = new File(cacheDir, key);
        if (f.exists()) {
            Log.i(TAG, "the file you wanted exists " + f.getAbsolutePath());
            return f;
        } else {
            Log.w(TAG, "the file you wanted does not exists: " + f.getAbsolutePath());
        }

        return null;
    }

    public void put(String key, Bitmap value) {
        File f = createFile(key);

        if (saveBitmap(f, value)) {
            Log.d(TAG, "Save file to sdcard successfully!");
        } else {
            Log.w(TAG, "Save file to sdcard failed!");
        }

    }

    public File createFile(String key) {
        File f = new File(cacheDir, key);
        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return f;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        for (File f : files)
            f.delete();
    }

    public static boolean saveBitmap(File file, Bitmap bitmap) {
        if (file == null || bitmap == null) {
            return false;
        }
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            return bitmap.compress(CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
