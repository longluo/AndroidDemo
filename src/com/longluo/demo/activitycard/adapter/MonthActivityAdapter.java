package com.longluo.demo.activitycard.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;

import com.longluo.demo.activitycard.bean.ActivityInfo;
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
    private ArrayList<ActivityInfo> mActivityInfos = new ArrayList<ActivityInfo>();

    public MonthActivityAdapter() {

    }

    public MonthActivityAdapter(Context context) {
        mContext = context;
    }
    
    public void setActivityInfo(ArrayList<ActivityInfo> activityList) {
        mActivityInfos = activityList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mActivityInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mActivityInfos.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("luolong", TAG + " getView position=" + position);
        
        if(null == convertView) {
            convertView = new MonthActivityCard(mContext, null);
            
//            convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
//                    LayoutParams.MATCH_PARENT));
            
            convertView.setLayoutParams(new android.widget.AbsListView.LayoutParams(android.widget.AbsListView.LayoutParams.MATCH_PARENT, android.widget.AbsListView.LayoutParams.WRAP_CONTENT));
        }
        
        ((MonthActivityCard)convertView).init();
        
        return convertView;
    }

}
