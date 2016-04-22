package com.longluo.demo;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;

import java.util.List;

public class DemoApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static Context getContext() {
        return mContext;
    }


    private boolean isMainProcessType() {
        String processName = getCurrentProcessName();
        if (getPackageName().equals(processName)) {
            return true;
        } else {
            return false;
        }
    }

    private String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        if (infos != null && infos.size() > 0) {
            for (RunningAppProcessInfo appProcess : infos) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }

        return "";
    }

}
