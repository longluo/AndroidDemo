package com.longluo.demo.activitycard.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.longluo.demo.activitycard.bean.MonthActivityInfo;
import com.longluo.demo.widgets.imgcard.MonthActivityCard;

/**
 * MonthActivityAdapter
 * 
 * @author luolong
 * @Date 2015-6-29 下午7:13:58
 * @version
 */
public class MonthActivityAdapter extends BaseAdapter {
    private static final String TAG = MonthActivityAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<MonthActivityInfo> mMonthActivityInfos = new ArrayList<MonthActivityInfo>();

    public MonthActivityAdapter() {

    }

    public MonthActivityAdapter(Context context) {
        mContext = context;
    }

    public void setActivityInfo(ArrayList<MonthActivityInfo> monthInfos) {
        Log.d("luolong", "setActivityInfo, size=" + monthInfos.size());
        mMonthActivityInfos = monthInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMonthActivityInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mMonthActivityInfos.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("luolong", TAG + " getView position=" + position + ",month=" + mMonthActivityInfos.get(position).mMonth
                + "");

        if (null == convertView) {
            convertView = new MonthActivityCard(mContext, null);

            convertView.setLayoutParams(new android.widget.AbsListView.LayoutParams(
                    android.widget.AbsListView.LayoutParams.MATCH_PARENT,
                    android.widget.AbsListView.LayoutParams.WRAP_CONTENT));
        }

        ((MonthActivityCard) convertView).initData(mMonthActivityInfos.get(position));
        ((MonthActivityCard) convertView).init();
        ((MonthActivityCard) convertView).setCardTitle();

        return convertView;
    }

}
