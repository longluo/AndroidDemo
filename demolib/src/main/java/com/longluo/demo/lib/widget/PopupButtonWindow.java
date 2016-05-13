package com.longluo.demo.lib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longluo.demo.lib.R;
import com.longluo.demo.lib.utils.DensityUtils;


public class PopupButtonWindow extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "PopupButtonWindow";

    private Context context;
    private View rootView;
    private int row;
    private int redLine;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setVisiblity(int viewId, int visible) {
        if (null != rootView) {
            rootView.findViewById(viewId).setVisibility(visible);
        }
    }

    public PopupButtonWindow(Context context, String title, String msg, int[] buttonIds, String[] buttonTexts, View.OnClickListener clickListener) {
        super(context);
        init(context, title, msg, buttonIds, buttonTexts, clickListener);
    }

    private void init(Context context, String title, String msg, int[] buttonIds, String[] buttonTexts, View.OnClickListener clickListener) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.popup_button_window, null);
        // 设置背景透明
        ColorDrawable cd = new ColorDrawable(this.context.getResources().getColor(R.color.black_alpha_80));
        this.setBackgroundDrawable(cd);

        setFocusable(true);
        setContentView(rootView);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
//        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
//        setHeight(DensityUtils.getScreenHeight(context));
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);

        setAnimationStyle(android.R.style.Animation_Toast);

        initView(title, msg, buttonIds, buttonTexts, clickListener);
    }

    private void initView(String title, String msg, int[] buttonIds, String[] buttonTexts, View.OnClickListener clickListener) {
        final RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.popup_window_root);
        relativeLayout.setOnClickListener(this);

        TextView titleView = (TextView) rootView.findViewById(R.id.popup_window_title);
        TextView msgView = (TextView) rootView.findViewById(R.id.popup_window_msg);
        LinearLayout buttonsLayout = (LinearLayout) rootView.findViewById(R.id.popup_window_buttons);
        View divider = rootView.findViewById(R.id.popup_window_divider);

        if (TextUtils.isEmpty(title)) {
            titleView.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        } else {
            titleView.setText(title);
        }
        if (TextUtils.isEmpty(msg)) {
            msgView.setVisibility(View.GONE);
        } else {
            msgView.setText(msg);
        }

        if (buttonTexts == null || buttonTexts.length == 0) {
            buttonsLayout.setVisibility(View.GONE);
        } else {
            View line;
            Button btn;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < buttonTexts.length; i++) {
                btn = new Button(context);
                btn.setLayoutParams(params);

                line = new View(context);
                line.setBackgroundColor(context.getResources().getColor(R.color.divider_color));
                line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
                boolean isAddLine = false;

                btn.setText(buttonTexts[i]);

                ColorStateList csl = context.getResources().getColorStateList(R.color.popup_window_text_color);
                btn.setTextColor(csl);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                btn.setPadding(0, DensityUtils.dp2px(context, 9), 0, DensityUtils.dp2px(context, 9));

                //按钮背景样式
                if (titleView.getVisibility() == View.GONE && i == 0 && buttonTexts.length > 1) {
                    btn.setBackgroundResource(R.drawable.popup_window_bg_top_selector);
                    isAddLine = true;
                } else if (titleView.getVisibility() == View.GONE && buttonTexts.length == 1) {
                    btn.setBackgroundResource(R.drawable.popup_window_bg_single_selector);
                } else if (i == (buttonTexts.length - 1) && buttonTexts.length > 1) {
                    btn.setBackgroundResource(R.drawable.popup_window_bg_bottom_selector);
                    // 取消按钮字体颜色
                    csl = this.context.getResources().getColorStateList(R.color.popup_window_text_color);
                    btn.setTextColor(csl);
                } else {
                    btn.setBackgroundResource(R.drawable.popup_window_bg_mid_selector);
                    isAddLine = true;
                }

                if (buttonIds != null && i < buttonIds.length) {
                    btn.setId(buttonIds[i]);
                }

                btn.setOnClickListener(clickListener);
                buttonsLayout.addView(btn);

                if (isAddLine) {
                    buttonsLayout.addView(line);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.popup_window_root) {
            this.dismiss();
        }
    }
}
