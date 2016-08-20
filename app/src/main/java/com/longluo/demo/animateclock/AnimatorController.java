package com.longluo.demo.animateclock;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;


/**
 * Created by luolong on 2016/8/12.
 */
public class AnimatorController {
    public static final String TAG = "AnimatorController";

    private static final int[] TIME_NUMBER = new int[60];

    private ObjectAnimator mAnimator;
    private float mAnimProgress;
    private boolean mIsAnimating = false;
    private AnimUpdateListener mListener;

    static {
        for (int i = 0; i < 60; i++) {
            TIME_NUMBER[i] = i;
        }
    }

    @SuppressWarnings("unused")
    public void setProgress(float progress) {
        mAnimProgress = progress;
        if (mListener != null) {
            mListener.onAnimUpdate();
        }
    }

    public boolean getIsAnimating() {
        return mIsAnimating;
    }

    public void start() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(this, "progress", 0f, 1f);
            mAnimator.setDuration(1000);
            mAnimator.setInterpolator(new EaseCubicInterpolator(0.56f, 0.01f, 0.09f, 1.0f));
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (mListener != null) {
                        mListener.onAnimStart();
                    }
                    mIsAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mIsAnimating = false;
                            if (mListener != null) {
                                mListener.onAnimEnd();
                            }
                        }
                    }, 500);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        if (!mAnimator.isRunning()) {
            mAnimator.start();
        }
    }

    public int calculateAnimNumber(int nowNumber, int preNumber) {
        int range;
        if (nowNumber >= preNumber) {
            range = nowNumber - preNumber;
            return TIME_NUMBER[preNumber + (int) (range * mAnimProgress)];
        } else {
            range = 24 - preNumber + nowNumber;
            return TIME_NUMBER[(preNumber + (int) (range * mAnimProgress)) % 24];
        }
    }

    public void setAnimUpdateListener(AnimUpdateListener animUpdateListener) {
        this.mListener = animUpdateListener;
    }

    public interface AnimUpdateListener {
        void onAnimStart();

        void onAnimUpdate();

        void onAnimEnd();
    }
}
