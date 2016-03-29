package com.longluo.demo.widget.imgcard;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.activitycard.bean.DayActivityInfo;
import com.longluo.demo.activitycard.bean.MonthActivityInfo;

/**
 * MonthActivityCard
 * 
 * @author luolong
 * @version
 * @Date 2015-6-26 4:32:55 
 */
public class MonthActivityCard extends RelativeLayout {
    private static final String TAG = MonthActivityCard.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private TextView mCardTitle;
    private LinearLayout mCardGrid;

    private int mItemLayout = R.layout.image_card_item;
    private ArrayList<ImageCellLayout> mCells = new ArrayList<ImageCellLayout>();
//    private ArrayList<RelativeLayout> mCells = new ArrayList<RelativeLayout>();
    private MonthActivityInfo mMonthActivityInfo = new MonthActivityInfo();
    private ArrayList<DayActivityInfo> mActivityInfos = new ArrayList<DayActivityInfo>();

    public MonthActivityCard(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MonthActivityCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
//        init();
    }

    public MonthActivityCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
//        init();
    }

    public void init() {
        if (isInEditMode()) {
            return;
        }

        mLayoutInflater = LayoutInflater.from(mContext);

        View layout = LayoutInflater.from(mContext).inflate(R.layout.image_card_view, null, false);

        mCardTitle = (TextView) layout.findViewById(R.id.cardTitle);
        mCardGrid = (LinearLayout) layout.findViewById(R.id.cardGrid);
        
        addView(layout);
        updateCells();
    }
    
    public void initData(MonthActivityInfo monthInfo) {
        mMonthActivityInfo = monthInfo;
        mActivityInfos = monthInfo.mDayActivityInfos;
    }

    public void updateCells() {
        Log.d("luolong", TAG + ",updateCells, " + mActivityInfos.size());
        
        int index = 0;
        
        for (int y = 0; y < mCardGrid.getChildCount(); y++) {
            LinearLayout row = (LinearLayout) mCardGrid.getChildAt(y);

            for (int x = 0; x < row.getChildCount() && index < mActivityInfos.size(); x++) {
                ImageCellLayout cell = (ImageCellLayout) row.getChildAt(x);
//                RelativeLayout cell = (RelativeLayout) row.getChildAt(x);

                ImageView cellContent = (ImageView) mLayoutInflater.inflate(mItemLayout, cell, false);
                cellContent.setImageLevel(mActivityInfos.get(index).mActivityLevel);
                index++;
                cell.addView(cellContent);
                mCells.add(cell);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        Log.d("luolong", TAG + ",changed=" + changed + " l=" + l + ",t=" + t + ",r=" + r + ",b=" + b);

        if (changed && mCells.size() > 0) {
            int size = (r - l) / 16;
            for (ImageCellLayout cell : mCells) {
                cell.getLayoutParams().height = size;
            }

            Log.d("luolong", TAG + "," + mCells.size() + ",size=" + size);
        }
    }
    
    public void setCardTitle() {
        mCardTitle.setText(String.valueOf(mMonthActivityInfo.mMonth) + getResources().getString(R.string.month));
    }

    public int getItemLayout() {
        return mItemLayout;
    }

    public void setItemLayout(int itemLayout) {
        this.mItemLayout = itemLayout;
    }

    /**
     * call after change any input data - to refresh view
     */
    public void notifyChanges() {
        updateCells();
    }

}
