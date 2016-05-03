package com.longluo.demo.widget.imageprocess.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 图像处理层
 *
 * @author luolong
 * @date 2015-05-21 01:48:05
 */
public class ToneLayer {
    /**
     * 饱和度标识
     */
    public static final int FLAG_SATURATION = 0x0;

    /**
     * 亮度标识
     */
    public static final int FLAG_LUM = 0x1;

    /**
     * 色相标识
     */
    public static final int FLAG_HUE = 0x2;

    private ColorMatrix mLightnessMatrix;
    private ColorMatrix mSaturationMatrix;
    private ColorMatrix mHueMatrix;
    private ColorMatrix mAllMatrix;

    /**
     * 亮度
     */
    private float mLumValue = 1F;

    /**
     * 饱和度
     */
    private float mSaturationValue = 0F;

    /**
     * 色相
     */
    private float mHueValue = 0F;

    public ToneLayer(Context context) {

    }

    /**
     * 设置饱和度值
     *
     * @param saturation
     */
    public void setSaturation(int saturation) {
        mSaturationValue = saturation * 1.0F / 127;
    }

    /**
     * 设置色相值
     *
     * @param hue
     */
    public void setHue(int hue) {
        mHueValue = hue * 1.0F / 127;
    }

    /**
     * 设置亮度值
     *
     * @param lum
     */
    public void setLum(int lum) {
        mLumValue = (lum - 127) * 1.0F / 127 * 180;
    }

    /**
     * 对图片进行处理
     *
     * @param flag 比特位0 表示是否改变色相，比位1表示是否改变饱和度,比特位2表示是否改变明亮度
     */
    public Bitmap handleImage(Bitmap bm, int flag) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (null == mAllMatrix) {
            mAllMatrix = new ColorMatrix();
        }

        if (null == mLightnessMatrix) {
            mLightnessMatrix = new ColorMatrix();
        }

        if (null == mSaturationMatrix) {
            mSaturationMatrix = new ColorMatrix();
        }

        if (null == mHueMatrix) {
            mHueMatrix = new ColorMatrix();
        }

        switch (flag) {
            case FLAG_HUE:
                mHueMatrix.reset();
                mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1);
                break;

            case FLAG_SATURATION:
                mSaturationMatrix.reset();
                mSaturationMatrix.setSaturation(mSaturationValue);
                break;

            case FLAG_LUM:
                mLightnessMatrix.reset();
                mLightnessMatrix.setRotate(0, mLumValue);
                mLightnessMatrix.setRotate(1, mLumValue);
                mLightnessMatrix.setRotate(2, mLumValue);
                break;

            default:
                break;
        }

        mAllMatrix.reset();
        mAllMatrix.postConcat(mHueMatrix);
        mAllMatrix.postConcat(mSaturationMatrix);
        mAllMatrix.postConcat(mLightnessMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);
        return bmp;
    }

}
