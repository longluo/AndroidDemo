package com.longluo.demo.activitycard;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.activitycard.adapter.MonthActivityAdapter;
import com.longluo.demo.activitycard.bean.DayActivityInfo;
import com.longluo.demo.activitycard.bean.MonthActivityInfo;
import com.longluo.demo.util.DateUtils;
import com.longluo.demo.util.FileUtils;
import com.longluo.demo.widget.imgcard.MonthActivityCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * MonthActivityCardActivity
 *
 * @author luolong
 * @Date 2015-6-26 下午4:23:14
 */
public class MonthActivityCardActivity extends Activity {
    private static final String TAG = MonthActivityCardActivity.class.getSimpleName();

    private TextView mTitleTV;
    private ListView mListView;
    private MonthActivityCard mCard;

    private MonthActivityAdapter mAdapter;

    private ArrayList<MonthActivityInfo> mMonthInfos = new ArrayList<MonthActivityInfo>();
    private ArrayList<DayActivityInfo> mDayInfos = new ArrayList<DayActivityInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_activity_card);

        initView();
        loadData();
        initData();
    }

    private void initView() {
        mTitleTV = (TextView) findViewById(R.id.tv_title);

        mListView = (ListView) findViewById(R.id.lv_activity_card);

//        mCard = (MonthActivityCard) findViewById(R.id.calendarCard1);
    }

    private void loadData() {
        JSONObject tempJsonObject = null;

        String str = FileUtils.getFromAssets(this, "gmvote2.json");
        try {
            tempJsonObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "tempJsonObject=" + tempJsonObject.toString());

        JSONArray candidateArray = tempJsonObject.optJSONArray("cand_info");

        JSONObject contentJsonObj = candidateArray.optJSONObject(0);

        JSONArray mActivityList = contentJsonObj.optJSONArray("active_list");

        if (mActivityList != null) {
            parseMonthInfos(mActivityList);
        }
    }

    private void parseMonthInfos(JSONArray monthArray) {
        int tempMonth = 0;
        tempMonth = getCurrentMonth(monthArray.optJSONArray(0));

//        Log.d(TAG, "parseMonthInfos, length=" + monthArray.length() + ",tempMonth=" + tempMonth);

        for (int i = 0; i < monthArray.length(); i++) {
//            Log.d(TAG, "i=" + i + ",tempMonth=" + tempMonth + ",month=" + getCurrentMonth(monthArray.optJSONArray(i)));

            if (getCurrentMonth(monthArray.optJSONArray(i)) > tempMonth) {
                MonthActivityInfo monthInfo = new MonthActivityInfo(tempMonth);

                for (int m = 0; m < i; m++) {
                    monthInfo.mDayActivityInfos.add(new DayActivityInfo(monthArray.optJSONArray(m)));
                }

                monthInfo.setmMonth(tempMonth);
                mMonthInfos.add(monthInfo);
//                Log.d(TAG, "neixunhuan  month size=" + mMonthInfos.size() + ",day size=" + mDayInfos.size());
                mDayInfos.clear();

                tempMonth = getCurrentMonth(monthArray.optJSONArray(i));
            }

//            Log.d(TAG, "After: i=" + i + ",tempMonth=" + tempMonth + ",month size=" + mMonthInfos.size());
        }
    }

    private int getCurrentMonth(JSONArray dateArray) {
        int timeStamp = dateArray.optInt(0);
        String month = DateUtils.getCurrentMonth(DateUtils.timeStamp2Date(timeStamp, DateUtils.FORMAT_SHORT));

        return Integer.parseInt(month);
    }

    private void initData() {
        mAdapter = new MonthActivityAdapter(this);
        mAdapter.setActivityInfo(mMonthInfos);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
