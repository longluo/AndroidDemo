package com.longluo.demo.widget.imageprocess.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.longluo.demo.R;


/**
 * @author luolong
 * @date 2015-05-21 01:36:27
 */
public class ButtonBar extends RelativeLayout {
    private Button btn1, btn2, btn3;

    private String btn1Desc, btn2Desc, btn3Desc;

    private float btnDescTextSize;

    private LayoutParams btn1Params, btn2Params, btn3Params;

    // 控件事件监听接口
    private buttonBarClickListener mListener;


    // 处理控件按钮点击事件
    public interface buttonBarClickListener {
        void btn1Click();

        void btn2Click();

        void btn3Click();
    }

    public ButtonBar(Context context) {
        super(context);
    }

    public ButtonBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ButtonBar);

        btnDescTextSize = ta.getDimension(R.styleable.ButtonBar_descTextSize, 10);

        btn1Desc = ta.getString(R.styleable.ButtonBar_btn1Desc);

        btn2Desc = ta.getString(R.styleable.ButtonBar_btn2Desc);

        btn3Desc = ta.getString(R.styleable.ButtonBar_btn3Desc);

        ta.recycle();

        btn1 = new Button(context);
        btn2 = new Button(context);
        btn3 = new Button(context);

        btn1.setText(btn1Desc);
        btn2.setText(btn2Desc);
        btn3.setText(btn3Desc);

        btn1.setTextSize(btnDescTextSize);
        btn2.setTextSize(btnDescTextSize);
        btn3.setTextSize(btnDescTextSize);

        setBackgroundColor(0xFFFFFF);

        initLayout();

        initListener();
    }

    public ButtonBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void initLayout() {
        btn1Params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn1Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
//        btn1.setLayoutParams(btn1Params);
        addView(btn1, btn1Params);
//        addView(btn1);

        btn2Params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn2Params.addRule(RelativeLayout.CENTER_HORIZONTAL, TRUE);
//        btn2Params.addRule(RelativeLayout.RIGHT_OF, btn1.getId());
//        btn2.setLayoutParams(btn2Params);
        addView(btn2, btn2Params);
//        addView(btn2);

        btn3Params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn3Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
//        btn3.setLayoutParams(btn3Params);
//        addView(btn3);
        addView(btn3, btn3Params);
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

}
