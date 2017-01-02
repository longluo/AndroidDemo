package com.longluo.network.get;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by longl on 2017/1/2.
 */
public class ImageService {
    /**
     * 获取图片
     *
     * @param path 图片路径
     * @return
     */
    public static Bitmap getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream inStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inStream);
            return bitmap;
            /*byte[] data = StreamTool.read(inStream);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			return bitmap;*/
        }
        return null;
    }

}

