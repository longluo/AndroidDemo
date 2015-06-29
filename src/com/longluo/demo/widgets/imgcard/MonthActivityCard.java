package com.longluo.demo.widgets.imgcard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longluo.demo.R;

/**
 * MonthActivityCard
 * 
 * @author luolong
 * @version
 * @Date 2015-6-26 下午4:32:55
 */
public class MonthActivityCard extends RelativeLayout {
    private static final String TAG = MonthActivityCard.class.getSimpleName();
    
    private LayoutInflater mLayoutInflater;

    private TextView mCardTitle;
    private LinearLayout mCardGrid;

    private int itemLayout = R.layout.image_card_item;
    private Calendar dateDisplay;
    private ArrayList<ImageCellLayout> cells = new ArrayList<ImageCellLayout>();

    public MonthActivityCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MonthActivityCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MonthActivityCard(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode()) {
            return;
        }
        
        mLayoutInflater = LayoutInflater.from(context);

        View layout = LayoutInflater.from(context).inflate(R.layout.image_card_view, null, false);

        mCardTitle = (TextView) layout.findViewById(R.id.cardTitle);
        mCardGrid = (LinearLayout) layout.findViewById(R.id.cardGrid);

        for (int y = 0; y < mCardGrid.getChildCount(); y++) {
            LinearLayout row = (LinearLayout) mCardGrid.getChildAt(y);

            for (int x = 0; x < row.getChildCount(); x++) {
                ImageCellLayout cell = (ImageCellLayout) row.getChildAt(x);
                
                View cellContent = mLayoutInflater.inflate(itemLayout, cell, false);
                cell.addView(cellContent);
                cells.add(cell);
            }
        }

        addView(layout);
        updateCells();
    }

    private void updateCells() {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        Log.d("luolong", TAG + " l=" + l + ",t=" + t + ",r=" + r + ",b=" + b);

        if (changed && cells.size() > 0) {
            int size = (r - l) / 16;
            for (ImageCellLayout cell : cells) {
                cell.getLayoutParams().height = size;
            }
        }
    }

    public int getItemLayout() {
        return itemLayout;
    }

    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }

    public Calendar getDateDisplay() {
        return dateDisplay;
    }

    public void setDateDisplay(Calendar dateDisplay) {
        this.dateDisplay = dateDisplay;
        mCardTitle.setText(new SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(dateDisplay.getTime()));
    }

    /**
     * call after change any input data - to refresh view
     */
    public void notifyChanges() {
        updateCells();
    }

}
