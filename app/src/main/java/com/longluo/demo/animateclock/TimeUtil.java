package com.longluo.demo.animateclock;


import android.content.Context;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by luolong on 2016/8/12.
 */
public class TimeUtil {

    /**
     * 根据时间计算时针的角度
     *
     * @param calendar
     * @return
     */
    public static float calculateHourDegree(Calendar calendar) {
        return calculateHourDegree(calendar, true);
    }

    /**
     * 根据时间计算时针的角度
     *
     * @param calendar
     * @param delta    是否计算分针和秒针
     * @return
     */
    public static float calculateHourDegree(Calendar calendar, boolean delta) {
        if (delta) {
            return 360.0f * (calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) / 60.0f) / 12.0f;
        }
        return 360.0f * calendar.get(Calendar.HOUR_OF_DAY) / 12.0f;
    }

    /**
     * 根据时间计算分针的角度
     *
     * @param calendar
     * @return
     */
    public static float calculateMinuteDegree(Calendar calendar) {
        return 360.0f * (calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) / 60.0f) / 60.0f;
    }

    public static float calculateMinuteDegree(Calendar calendar, boolean delta) {
        if (!delta)
            return 360.0f * calendar.get(Calendar.MINUTE) / 60.0f;
        else
            return calculateMinuteDegree(calendar);
    }

    /**
     * 根据时间计算秒针的角度
     */
    public static float calculateSecondDegree(Calendar calendar) {
        return 360.0f * calendar.get(Calendar.SECOND) / 60.0f;
    }

    /**
     * 根据时间计算秒针的角度
     */
    public static float calculateSecondDegree(Calendar calendar, boolean delta) {
        long milliseconds = calendar.getTimeInMillis() % 1000;
        if (delta) {
            return 360.0f * (calendar.get(Calendar.SECOND) + milliseconds / 1000f) / 60f;
        }
        return 360.0f * calendar.get(Calendar.SECOND) / 60.0f;
    }

    public static float calculateWeekDegree(Calendar calendar, float startDegree, float endDegree) {
        return startDegree + (endDegree - startDegree) * (calendar.get(Calendar.DAY_OF_WEEK) - 1) / 6.0f;
    }

    public static float calculateBatteryDegree(float start, float end, float batteryLevel) {
        return (end - start) * batteryLevel / 100 + start;
    }

    public static float calculateTemperatureDegree(float start, float end, float startDegree,
                                                   float endDegree, float temperature) {
        float factor = (endDegree - startDegree) / (end - start);
        return factor * temperature + startDegree - factor * start;
    }

    private static boolean is24HourModeEnabled(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    public static int getHour(Calendar calendar, boolean hasAmPm) {
        int hour;
        if (hasAmPm) {
            hour = calendar.get(Calendar.HOUR);
            if (hour == 0) {
                hour = 12;
            }
        } else {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        }
        return hour;
    }

    public static String getTimeString(Calendar calendar, String connector, boolean hasAmPm) {
        if (connector == null) {
            connector = "";
        }
        return paddingSingleNumber(getHour(calendar, hasAmPm)) + connector
                + paddingSingleNumber(calendar.get(Calendar.MINUTE));
    }

    public static String formatDigitalHour(Context context, int time) {
        if (!is24HourModeEnabled(context)) {
            time = time > 12 ? time - 12 : time;
        }
        return paddingSingleNumber(time);
    }

    public static boolean isAm(Calendar calendar) {
        //noinspection WrongConstant
        return calendar.get(Calendar.AM_PM) == Calendar.AM;
    }

    /**
     * 1位数字前面补0
     */
    public static String paddingSingleNumber(int number) {
        return String.format(Locale.getDefault(), "%02d", number);
    }

    public static String getDistance(int distanceInMeter, boolean isUnitMetric) {
        if (isUnitMetric) {
            return String.format(Locale.getDefault(), "%.2fKM", distanceInMeter / (float) 1000);
        } else {
            return String.format(Locale.getDefault(), "%.2fMI", distanceInMeter * 0.6214 / (float) 1000);
        }
    }

    /**
     * 获取当前星期
     *
     * @param prefix 前缀，例如星期、周等
     */
    public static String getWeek(int weekDay, String prefix) {
        String weekStr = "日";
        switch (weekDay) {
            case 1:
                weekStr = "一";
                break;
            case 2:
                weekStr = "二";
                break;
            case 3:
                weekStr = "三";
                break;
            case 4:
                weekStr = "四";
                break;
            case 5:
                weekStr = "五";
                break;
            case 6:
                weekStr = "六";
                break;
            case 7:
                weekStr = "日";
                break;
        }
        if (prefix == null) {
            prefix = "星期";
        }
        return prefix + weekStr;
    }

    /**
     * 获取当前星期英文简写
     */
    public static String getWeekEN(Context context, int weekDay) {
        int res;
        switch (weekDay) {
            case 1:
//                res = R.string.monday_en;
                break;
            case 2:
//                res = R.string.thuesday_en;
                break;
            case 3:
//                res = R.string.wednesday_en;
                break;
            case 4:
//                res = R.string.thursday_en;
                break;
            case 5:
//                res = R.string.friday_en;
                break;
            case 6:
//                res = R.string.saturday_en;
                break;
            default:
//                res = R.string.sunday_en;
                break;
        }

        return " ";
//        return context.getResources().getString(res);
    }

    /**
     * 获取当前月份的英文简称
     */
/*    public static String getMonthEN(Calendar calendar) {
        String[] months = WatchFacesApp.getInstance().getResources().getStringArray(R.array.month_en);
        return months[calendar.get(Calendar.MONTH)];
    }*/

/*    public static boolean isUnitMetric(String unit) {
        return TextUtils.isEmpty(unit) || Constants.UNIT_METRIC.equals(unit);
    }*/
}
