package com.longluo.demo;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

/**
 * 
 * DemoApp
 *
 * @author    luolong
 * @date      2015-5-27    12:16:55
 *
 * @version
 */
public class DemoApp extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Init JPush
//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        
    }

}

