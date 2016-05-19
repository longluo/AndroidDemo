package com.longluo.demo.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 
 * ScreenUtils
 *
 * @author    luolong
 * @date      2015-5-27    下午3:31:05
 *
 * @version
 */
public class ScreenUtils {

    /**
     * @方法说明:获取DisplayMetrics对象
     * @方法名称:getDisPlayMetrics
     * @param context
     * @return
     * @返回值:DisplayMetrics
     */
    public static DisplayMetrics getDisPlayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        if (null != context) {
            ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getMetrics(metric);
        }
        return metric;
    }

    /**
     * @方法说明:获取屏幕的宽度（像素）
     * @方法名称:getScreenWidth
     * @param context
     * @return
     * @返回值:int
     */
    public static int getScreenWidth(Context context) {
        int width = getDisPlayMetrics(context).widthPixels;
        return width;
    }

    /**
     * @方法说明:获取屏幕的高（像素）
     * @方法名称:getScreenHeight
     * @param context
     * @return
     * @返回值:int
     */
    public static int getScreenHeight(Context context) {
        int height = getDisPlayMetrics(context).heightPixels;
        return height;
    }

    /**
     * @方法说明:屏幕密度(0.75 / 1.0 / 1.5)
     * @方法名称:getDensity
     * @param context
     * @return
     * @返回 float
     */
    public static float getDensity(Context context) {
        float density = getDisPlayMetrics(context).density;
        return density;
    }

    /**
     * @方法说明:屏幕密度DPI(120 / 160 / 240)
     * @方法名称:getDensityDpi
     * @param context
     * @return
     * @返回 int
     */
    public static int getDensityDpi(Context context) {
        int densityDpi = getDisPlayMetrics(context).densityDpi;
        return densityDpi;
    }

}