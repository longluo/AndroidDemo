package com.longluo.demo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Vibrator;

import com.longluo.demo.crash.CrashHandler;
import com.longluo.demo.task.TaskObserver;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

//import com.facebook.drawee.backends.pipeline.Fresco;

//import com.baidu.mapapi.SDKInitializer;
//import com.longluo.demo.lbs.service.LocationService;
//import com.longluo.demo.lbs.service.WriteLog;

/**
 * DemoApp
 *
 * @author luolong
 * @date 2015-5-27 12:16:55
 */
public class DemoApp extends Application {

    private static Context mContext;

    private CrashHandler mCrashHandler;

    // Baidu Location
//	public LocationService mLocationService;
    public Vibrator mVibrator;

    //
    private static RefWatcher sRefWatcher;
    /*
    use to watch the memory leak.
    MyApplication.getRefWatcher().watch(sLeaky);
     */

    private static TaskObserver mTaskObserver;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

/*		if (isMainProcessType()) {
            // initJPush();

			mCrashHandler = CrashHandler.getInstance();
			mCrashHandler.init(mContext);
		}*/

        initBaiduLBSSDK();

        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        //
        sRefWatcher = LeakCanary.install(this);

        initFresco();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * @return 异步任务管理器
     */
    public static TaskObserver getTaskObserver() {
        if (mTaskObserver == null) {
            mTaskObserver = new TaskObserver();
        }
        return mTaskObserver;
    }

    private void initJPush() {
        // Init JPush
        // JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        // JPushInterface.init(this); // 初始化 JPush
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

    private void initBaiduLBSSDK() {
        /***
         * 初始化定位sdk，建议在Application中创建
         */
//		mLocationService = new LocationService(getApplicationContext());
//		mVibrator = (Vibrator) getApplicationContext().getSystemService(
//				Service.VIBRATOR_SERVICE);
//		WriteLog.getInstance().init(); // 初始化日志
//		SDKInitializer.initialize(getApplicationContext());
    }

    public static RefWatcher getRefWatcher() {
        return sRefWatcher;
    }

    private void initFresco() {
//        Fresco.initialize(getApplicationContext());

    }

}
