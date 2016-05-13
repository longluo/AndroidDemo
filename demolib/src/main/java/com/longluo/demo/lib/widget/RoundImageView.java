package com.longluo.demo.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.longluo.demo.lib.R;


/**
 * 圆角图片，可对四个角分别设置弧度（radius_left_up、radius_left_down、radius_right_up、radius_right_down）
 */
public class RoundImageView extends ImageView {

    private Paint paint;
    private Paint paint2;

    private int radius_left_up;
    private int radius_left_down;
    private int radius_right_up;
    private int radius_right_down;

    /**
     * @param context
     */
    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);

            radius_left_up = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_radius_left_up, 0);
            radius_left_down = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_radius_left_down, 0);
            radius_right_up = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_radius_right_up, 0);
            radius_right_down = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_radius_right_down, 0);

            typedArray.recycle();
        }

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);

        super.draw(canvas2);
        drawLeftUp(canvas2);
        drawRightUp(canvas2);
        drawLeftDown(canvas2);
        drawRightDown(canvas2);
        canvas.drawBitmap(bitmap, 0, 0, paint2);
        bitmap.recycle();
    }

    private void drawLeftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, radius_left_up);
        path.lineTo(0, 0);
        path.lineTo(radius_left_up, 0);
        path.arcTo(new RectF(0, 0, radius_left_up * 2, radius_left_up * 2), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLeftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - radius_left_down);
        path.lineTo(0, getHeight());
        path.lineTo(radius_left_down, getHeight());
        path.arcTo(new RectF(0, getHeight() - radius_left_down * 2, radius_left_down * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - radius_right_down, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - radius_right_down);
        path.arcTo(new RectF(getWidth() - radius_right_down * 2, getHeight() - radius_right_down * 2, getWidth(), getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), radius_right_up);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - radius_right_up, 0);
        path.arcTo(new RectF(getWidth() - radius_right_up * 2, 0, getWidth(), radius_right_up * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }
}

