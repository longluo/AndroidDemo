package com.longluo.demo.guide;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.longluo.demo.R;
import com.longluo.demo.utils.ScreenUtils;

/**
 * GuideUtils
 *
 * @author luolong
 * @date 2015-5-27    下午3:27:59
 */
public class GuideUtils {
    private Context mContext;
    private ImageView imgView;
    private WindowManager windowManager;

    private static GuideUtils instance = null;

    /**
     * 是否第一次进入该程序
     **/
    private boolean isFirst = true;

    /**
     * 采用私有的方式，只保证这种通过单例来引用，同时保证这个对象不会存在多个
     **/
    private GuideUtils() {

    }

    /**
     * 采用单例的设计模式，同时用了同步锁
     **/
    public static GuideUtils getInstance() {
        synchronized (GuideUtils.class) {
            if (null == instance) {
                instance = new GuideUtils();
            }
        }

        return instance;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置LayoutParams参数
                    final LayoutParams params = new WindowManager.LayoutParams();
                    // 设置显示的类型，TYPE_PHONE指的是来电话的时候会被覆盖，其他时候会在最前端，显示位置在stateBar下面，其他更多的值请查阅文档
                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
                    // 设置显示格式
                    params.format = PixelFormat.RGBA_8888;
                    // 设置对齐方式
                    params.gravity = Gravity.LEFT | Gravity.TOP;
                    // 设置宽高
                    params.width = ScreenUtils.getScreenWidth(mContext);
                    params.height = ScreenUtils.getScreenHeight(mContext);

                    // 设置动画
                    params.windowAnimations = R.style.view_anim;

                    // 添加到当前的窗口上
                    windowManager.addView(imgView, params);
                    break;
            }
        }

        ;
    };

    /**
     * @param context
     * @param drawableRourcesId ：引导图片的资源Id
     * @方法说明:初始化
     * @方法名称:initGuide
     * @返回值:void
     */
    public void initGuide(Activity context, int drawableRourcesId) {
        /** 如果不是第一次进入该界面 **/
        if (!isFirst) {
            return;
        }
        this.mContext = context;
        windowManager = context.getWindowManager();

        /** 动态初始化图层 **/
        imgView = new ImageView(context);
        imgView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        imgView.setScaleType(ScaleType.FIT_XY);
        imgView.setImageResource(drawableRourcesId);
        /** 这里我特意用了一个handler延迟显示界面，主要是为了进入界面后，你能看到它淡入得动画效果，不然的话，引导界面就直接显示出来 **/
        handler.sendEmptyMessageDelayed(1, 1000);

        imgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /** 点击图层之后，将图层移除 **/
                windowManager.removeView(imgView);
            }
        });
    }

    public boolean isFirst() {
        return isFirst;
    }

    /**
     * @param isFirst
     * @方法说明:设置是否第一次进入该程序
     * @方法名称:setFirst
     * @返回值:void
     */
    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }
}
