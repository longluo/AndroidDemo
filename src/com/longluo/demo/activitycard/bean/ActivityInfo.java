package com.longluo.demo.activitycard.bean;

import org.json.JSONArray;

import android.util.Log;

/**
 * ActivityInfo
 *
 * @author   luolong 
 * @Date	 2015-6-29		下午7:26:32
 * @version 
 */
public class ActivityInfo {
    public long mDate;
    public int mActivityLevel;
    
    public ActivityInfo() {
        
    }
    
    public ActivityInfo(long date, int level) {
        mDate = date;
        mActivityLevel = level;
    }
    
    public ActivityInfo(JSONArray contentJsonArray) {
        parseData(contentJsonArray);
    }
    
    public void parseData(JSONArray contentJsonArray) {
        if (contentJsonArray == null) {
            return;
        }
        
        mDate = contentJsonArray.optLong(0);
        mActivityLevel = contentJsonArray.optInt(1);
        
        Log.d("luolong", "ActivityInfo, " + ",mDate=" + mDate + ",mActivityLevel=" + mActivityLevel);
    }

    public long getmDate() {
        return mDate;
    }

    public void setmDate(long mDate) {
        this.mDate = mDate;
    }

    public int getmActivityLevel() {
        return mActivityLevel;
    }

    public void setmActivityLevel(int mActivityLevel) {
        this.mActivityLevel = mActivityLevel;
    }
}

