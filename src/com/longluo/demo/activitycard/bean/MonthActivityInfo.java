package com.longluo.demo.activitycard.bean;

import java.util.ArrayList;

/**
 * MonthActivityInfo
 * 
 * @author luolong
 * @date 2015-7-1 下午7:59:04
 * @version
 */
public class MonthActivityInfo {
    public int mMonth;
    public ArrayList<DayActivityInfo> mDayActivityInfos = new ArrayList<DayActivityInfo>();

    public MonthActivityInfo() {

    }

    public MonthActivityInfo(int month) {
        mMonth = month;
    }

    public MonthActivityInfo(int month, ArrayList<DayActivityInfo> activityInfos) {
        mMonth = month;
        mDayActivityInfos = activityInfos;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public ArrayList<DayActivityInfo> getmMonthActivityInfos() {
        return mDayActivityInfos;
    }

    public void setmMonthActivityInfos(ArrayList<DayActivityInfo> mMonthActivityInfos) {
        this.mDayActivityInfos = mMonthActivityInfos;
    }
}
