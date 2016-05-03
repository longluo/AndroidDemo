package com.longluo.demo.widget.imageprocess.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.longluo.demo.R;

public class PickerBar extends ViewGroup {

    private Button btn1, btn2, btn3;

    private String btn1Desc, btn2Desc, btn3Desc;

    // 控件事件监听接口
    private buttonBarClickListener mListener;

    // 处理控件按钮点击事件
    public interface buttonBarClickListener {
        public void btn1Click();

        public void btn2Click();

        public void btn3Click();
    }

    public PickerBar(Context context) {
        this(context, null);
    }

    public PickerBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickerBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_picker_bar, this,
                true);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ButtonBar);

        btn1Desc = ta.getString(R.styleable.ButtonBar_btn1Desc);
        btn2Desc = ta.getString(R.styleable.ButtonBar_btn2Desc);
        btn3Desc = ta.getString(R.styleable.ButtonBar_btn3Desc);

        ta.recycle();

        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);

        btn1.setText(btn1Desc);
        btn2.setText(btn2Desc);
        btn3.setText(btn3Desc);

        setBackgroundColor(0xFFFFFF);

        initListener();
    }

    // 通过接口实现事件处理分离
    protected void initListener() {
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.btn1Click();
            }
        });

        btn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.btn2Click();
            }
        });

        btn3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.btn3Click();
            }
        });
    }

    public void setOnButtonBarClickListener(buttonBarClickListener listener) {
        mListener = listener;
    }

    // TODO
    // 可以通过按钮显示与否增加或者减少调节的项目
    public void setBtn1Enable(boolean flag) {
        if (flag) {
            btn1.setVisibility(View.VISIBLE);
        } else {
            btn1.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

}
