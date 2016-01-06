package com.longluo.demo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

/**
 * FileUtils
 * 
 * @author luolong
 * @Date 2015-6-29 下午8:13:47
 * @version
 */
public class FileUtils {

    public static String getFromAssets(Context context, String fileName) {
        String result = "";

        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("luolong", "getFromAssets,fileName=" + fileName + ",result=" + result);

        return result;
    }
}
