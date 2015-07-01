package com.longluo.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * DateUtils
 * 
 * @author luolong
 * @date 2015-7-1 下午8:05:35
 * @version
 */
public class DateUtil {
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * timeStamp2Date: Format Time Stamp to Date
     * 
     * @param @param timeStamp
     * @param @param formats
     * @return String Date
     * @author luolong
     * @date 2015-6-30 17:00:52
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeStamp2Date(int timeStamp, String formats) {
        long timeStampLong = (long) timeStamp * 1000;

        String date = new SimpleDateFormat(formats).format(new Date(timeStampLong));

        return date;
    }

    public static String getCurrentYear(String date) {
        return date.substring(0, 4);
    }

    public static String getCurrentMonth(String date) {
        return date.substring(5, 7);
    }

}
