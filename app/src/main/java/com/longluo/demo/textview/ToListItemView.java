package com.longluo.demo.textview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.longluo.demo.R;

public class ToListItemView extends TextView {
    // 绘制页面的背景
    private Paint marginPaint;
    // 绘制页面的边缘
    private Paint linePaint;
    // 存储页面的颜色值
    private int paperColor;
    // 存储页面的边缘宽度值
    private float margin;

    public ToListItemView(Context context) {
        super(context);
        init();
    }

    public ToListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        // 获得最资源表的引用
        Resources resources = getResources();

        // 创建在onDraw方法中使用的画刷
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(resources.getColor(R.color.notepad_margin));

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(resources.getColor(R.color.notepad_lines));

        // 获得页面背景颜色和边缘宽度
        paperColor = resources.getColor(R.color.notepad_paper);
        margin = resources.getDimension(R.dimen.notepad_margin);
    }

    // 重新绘制样式
    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制页面的颜色
        canvas.drawColor(paperColor);

        // 绘制边缘
        canvas.drawLine(margin, 0, margin, getMeasuredHeight(), linePaint);
        canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(),
                getMeasuredHeight(), linePaint);

        // 绘制margin
        canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);

        // 移动文本，让它跨过边缘
        canvas.save();
        canvas.translate(margin, 0);

        // 使用TextView渲染文本
        super.onDraw(canvas);
        canvas.restore();
    }
}
