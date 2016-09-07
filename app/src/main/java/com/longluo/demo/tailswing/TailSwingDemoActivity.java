package com.longluo.demo.tailswing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.longluo.demo.R;

/**
 * Created by luolong on 2016/8/29.
 */
public class TailSwingDemoActivity extends Activity {

    private ImageView mWatchFaceBg;
    private ImageView mCenterIv;
    private ImageView mTailIv;

    private Button mBtnPlay;

    private SwingAnimation mSwingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tail_swing_demo);

        //参数取值说明：中间度数、摆到左侧的度数、摆到右侧的度数、圆心X坐标类型、圆心X坐标相对比例、圆心Y坐标类型、圆心Y坐标相对比例
        //坐标类型有三种：ABSOLUTE 绝对坐标，RELATIVE_TO_SELF 相对自身的坐标，RELATIVE_TO_PARENT 相对上级视图的坐标
        //X坐标相对比例，为0时表示左边顶点，为1表示右边顶点，为0.5表示水平中心点
        //Y坐标相对比例，为0时表示上边顶点，为1表示下边顶点，为0.5表示垂直中心点

        mSwingAnimation = new SwingAnimation(
                0f, 20f, -20f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mSwingAnimation.setDuration(3000);     //动画持续时间
        mSwingAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mSwingAnimation.setRepeatCount(Animation.INFINITE);  //动画重播次数
        mSwingAnimation.setFillAfter(false);  //是否保持动画结束画面
        mSwingAnimation.setStartOffset(2000);   //动画播放延迟

        initViews();

    }

    private void initViews() {
        mTailIv = (ImageView) findViewById(R.id.iv_tail_swing);

        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTailIv.startAnimation(mSwingAnimation);
            }
        });

    }


}
