package com.longluo.demo.widget;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longluo.demo.R;


public class ListItemLinearLayout extends LinearLayout {
    private RelativeLayout mIconLayout;
    private TextView mTextViewTitle, mTextViewContent;
    private ImageView mImageViewArrow, mImageViewLeftIcon, mImageview, mRedDot;
    private ImageView mNewIcon;
    private Button mButtonRightIcon;
    private Context mContext;
    private float mScreendensity = 1.5f;
    private boolean isNormalSelected = false;

    public ListItemLinearLayout(Context context) {
        super(context);
        mContext = context;
    }

    public ListItemLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        WindowManager mWm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mWm.getDefaultDisplay().getMetrics(metrics);
        mScreendensity = metrics.density;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item, this);

        mIconLayout = (RelativeLayout) this
                .findViewById(R.id.list_item_redpointicon);
        mImageview = (ImageView) this.findViewById(R.id.imageview);
        mRedDot = (ImageView) this.findViewById(R.id.reddot);
        mTextViewTitle = (TextView) this.findViewById(R.id.list_item_title);
        mTextViewContent = (TextView) this.findViewById(R.id.list_item_content);
        mImageViewArrow = (ImageView) this.findViewById(R.id.list_item_arrow);
        mImageViewLeftIcon = (ImageView) this
                .findViewById(R.id.list_item_lefticon);
        mNewIcon = (ImageView) this.findViewById(R.id.new_icon);
        mButtonRightIcon = (Button) this.findViewById(R.id.list_item_righticon);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ListItemLinearlayout);

        // 设置标题字体大小
        float titleSize = a.getDimension(
                R.styleable.ListItemLinearlayout_titleSize, getResources()
                        .getDimension(R.dimen.text_size_24px));
        titleSize = titleSize / mScreendensity;
        mTextViewTitle.setTextSize(titleSize);

        // 设置标题字体颜色
        int titleColor = a
                .getColor(R.styleable.ListItemLinearlayout_titleColor, context
                        .getResources().getColor(R.color.list_item_title_color));
        mTextViewTitle.setTextColor(titleColor);

        // 设置内容字体大小
        float textSize = a.getDimension(
                R.styleable.ListItemLinearlayout_contentSize, getResources()
                        .getDimension(R.dimen.text_size_small));
        textSize = textSize / mScreendensity;
        mTextViewContent.setTextSize(textSize);

        // 设置内容字体颜色
        int textColor = a.getColor(
                R.styleable.ListItemLinearlayout_contentColor, context
                        .getResources().getColor(R.color.send_verify_gray));
        mTextViewContent.setTextColor(textColor);

        // 设置标题
        CharSequence mTitleText = a
                .getText(R.styleable.ListItemLinearlayout_titleText);
        if (mTitleText != null)
            mTextViewTitle.setText(mTitleText);

        // 设置内容
        CharSequence mContentText = a
                .getText(R.styleable.ListItemLinearlayout_contentText);
        if (mContentText != null)
            setTextContent(mContentText);

        // 设置箭头图标显示隐藏
        boolean isArrowVisibility = a.getBoolean(
                R.styleable.ListItemLinearlayout_arrowIconVisibility, true);
        if (!isArrowVisibility)
            this.mImageViewArrow.setVisibility(View.GONE);

        // 设置箭头图标
        Drawable mArrowIcon = a
                .getDrawable(R.styleable.ListItemLinearlayout_arrowIcon);
        if (mArrowIcon != null)
            this.mImageViewArrow.setBackgroundDrawable(mArrowIcon);
        mArrowIcon = null;

        // 设置左边其它图标
        Drawable mLeftIcon = a
                .getDrawable(R.styleable.ListItemLinearlayout_leftIcon);
        if (mLeftIcon != null) {
            this.mImageViewLeftIcon.setImageDrawable(mLeftIcon);
            this.mImageViewLeftIcon.setVisibility(View.VISIBLE);
        }
        mLeftIcon = null;

        // 设置右边图标显示隐藏
        boolean isRightIconVisibility = a.getBoolean(
                R.styleable.ListItemLinearlayout_rightIconVisibility, false);
        if (isRightIconVisibility)
            this.mButtonRightIcon.setVisibility(View.VISIBLE);

        // 设置右边其它图标
        Drawable mRightIcon = a
                .getDrawable(R.styleable.ListItemLinearlayout_rightForIcon);
        if (mRightIcon != null) {
            this.mButtonRightIcon.setBackgroundDrawable(mRightIcon);
            this.mButtonRightIcon.setVisibility(View.VISIBLE);
        }

        isNormalSelected = a.getBoolean(
                R.styleable.ListItemLinearlayout_normalSelected, false);

        mRightIcon = null;
        a.recycle();
    }

    /**
     * 返回标题控件
     *
     * @return
     */
    public TextView getTextTitleView() {
        return mTextViewTitle;
    }

    /**
     * 返回内容控件
     *
     * @return
     */
    public TextView getTextContentView() {
        return mTextViewContent;
    }

    /**
     * 设置文本内容
     *
     * @param resourceId 内容ID
     */
    public void setTextContent(int resourceId) {
        setTextContent(mContext.getResources().getString(resourceId));
    }

    /**
     * 设置文本内容
     *
     * @param strContent 内容字符串
     */
    public void setTextContent(String strContent) {
        setTextContent((CharSequence) strContent);
    }

    /**
     * 设置文本内容
     *
     * @param strContent 内容字符串
     */
    public void setTextContent(CharSequence strContent) {
        if (strContent != null) {
            if (strContent.length() > 13)
                strContent = strContent.subSequence(0, 10) + "...";
            mTextViewContent.setText(strContent);
            if (isNormalSelected && !TextUtils.isEmpty(strContent.toString())
                    && !strContent.equals("未选择") && !strContent.equals("未填写")) {
                mTextViewContent.setTextColor(mContext.getResources().getColor(
                        R.color.listitem_content_text_color));
            }
        }
    }

    /**
     * 返回文本内容
     *
     * @param strContent 内容字符串
     */
    public CharSequence getTextContent() {
        return mTextViewContent.getText();
    }

    /**
     * 设置文本标题
     *
     * @param resourceId 标题内容ID
     */
    public void setTextTitle(int resourceId) {
        setTextTitle(mContext.getResources().getString(resourceId));
    }

    /**
     * 设置文本标题
     *
     * @param strContent 标题字符串
     */
    public void setTextTitle(String strContent) {
        mTextViewTitle.setText(strContent);
    }

    /**
     * 设置文本标题
     *
     * @param strContent 标题字符串
     */
    public void setTextTitle(CharSequence strContent) {
        mTextViewTitle.setText(strContent);
    }

    /**
     * 设置左边图标
     *
     * @param resourceId 图标ID
     */
    public void setLeftIcon(int resourceId) {
        mImageViewLeftIcon.setBackgroundResource(resourceId);
    }

    /**
     * 设置左边图标
     *
     * @param drwObject 图标Drawable
     */
    public void setLeftIcon(Drawable drwObject) {
        mImageViewLeftIcon.setBackgroundDrawable(drwObject);
    }

    /**
     * 显示隐藏左边图标
     *
     * @param visibility View.GONE-View.INVISIBLE-View.VISIBLE
     */
    public void setLeftIconVisible(int visibility) {
        mImageViewLeftIcon.setVisibility(visibility);
    }

    /**
     * 取得右边按钮
     *
     * @return
     */
    public Button getRightIcon() {
        return mButtonRightIcon;
    }

    /**
     * 设置右边图标
     *
     * @param resourceId 图标ID
     */
    public void setRightIcon(int resourceId) {
        mButtonRightIcon.setBackgroundResource(resourceId);
    }

    /**
     * 设置右边图标
     *
     * @param drwObject 图标Drawable
     */
    public void setRightIcon(Drawable drwObject) {
        mButtonRightIcon.setBackgroundDrawable(drwObject);
    }

    /**
     * 显示隐藏右边图标
     *
     * @param visibility View.GONE-View.INVISIBLE-View.VISIBLE
     */
    public void setRightIconVisible(int visibility) {
        mButtonRightIcon.setVisibility(visibility);
    }

    /**
     * 设置右边带红点头像图标
     *
     * @param resourceId 图标ID
     */
    public void setRedRightIcon(Activity activity, String mGifeAvatarURL,
                                int redPointId) {

//        ZhenaiImageLoader.displayImage(
//                PhotoUrlUtils.format(mGifeAvatarURL, PhotoUrlUtils.TYPE_3),
//                mImageview, true);

        mRedDot.setBackgroundResource(redPointId);
    }

    /**
     * 显示隐藏右边带红点头像图标
     *
     * @param visibility View.GONE-View.INVISIBLE-View.VISIBLE
     */
    public void setRedRightIconVisible(int visibility) {
        mIconLayout.setVisibility(visibility);
    }

    /**
     * 设置消息个数
     *
     * @param visibility View.GONE-View.INVISIBLE-View.VISIBLE
     */
    public void setRightIconNum(String strNum) {
        mButtonRightIcon.setText(strNum);
    }

    /**
     * 显示隐藏箭头图标
     *
     * @param visibility View.GONE-View.INVISIBLE-View.VISIBLE
     */
    public void setArrowIconVisible(int visibility) {
        mImageViewArrow.setVisibility(visibility);
    }

    /**
     * 设置右边图标
     */
    public void setNewIconBackground(int resourceId) {
        mNewIcon.setBackgroundResource(resourceId);
    }

    /**
     * 设置右边图标
     *
     * @param drwObject 图标Drawable
     */
    public void setNewIconBackground(Drawable drwObject) {
        mNewIcon.setBackgroundDrawable(drwObject);
    }

    /**
     * 显示隐藏右边图标图标
     *
     * @param visibility View.GONE-View.INVISIBLE-View.VISIBLE
     */
    public void setNewIconVisible(int visibility) {
        mNewIcon.setVisibility(visibility);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 设置内容字体颜色
     */
    public void setContentTextColor(int colorId) {
        mTextViewContent
                .setTextColor(mContext.getResources().getColor(colorId));
    }

    @Override
    protected void finalize() throws Throwable {
        mContext = null;
        super.finalize();
    }

    /**
     * 设置背景色
     *
     * @param mBackground
     */
    public void setBackgroup(Drawable mBackground, int textColor) {
        if (mBackground != null)
            this.setBackgroundDrawable(mBackground);
        mTextViewTitle
                .setTextColor(mContext.getResources().getColor(textColor));
        mTextViewContent.setTextColor(mContext.getResources().getColor(
                textColor));
    }

    public void setTextType(int type) {
        mTextViewContent.setInputType(type);
    }

}
