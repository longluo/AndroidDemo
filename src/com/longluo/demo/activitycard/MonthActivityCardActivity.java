package com.longluo.demo.activitycard;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.activitycard.adapter.MonthActivityAdapter;
import com.longluo.demo.activitycard.bean.ActivityInfo;
import com.longluo.demo.utils.FileUtils;
import com.longluo.demo.widgets.imgcard.MonthActivityCard;

/**
 * MonthActivityCardActivity
 * 
 * @author luolong
 * @version
 * @Date 2015-6-26 下午4:23:14
 */
public class MonthActivityCardActivity extends Activity {
    private static final String TAG = MonthActivityCardActivity.class.getSimpleName();
    
    private ListView mListView;
    private MonthActivityCard mCard;
    private TextView mTitleTV;
    
    private MonthActivityAdapter mAdapter;
    
    private ArrayList<ActivityInfo> mInfos = new ArrayList<ActivityInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_activity_card);

        initView();
        loadData();
        initData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_activity_card);
        mCard = (MonthActivityCard) findViewById(R.id.calendarCard1);
        mTitleTV = (TextView) findViewById(R.id.tv_title);
    }
    
    private void loadData() {
        JSONObject tempJsonObject = null;
        
        String str = FileUtils.getFromAssets(this, "gmvote2.json");
        try {
            tempJsonObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        Log.d("luolong", TAG + ",str=" + str + ",tempJsonObject=" + tempJsonObject.toString());
    
        JSONArray candidateArray = tempJsonObject.optJSONArray("cand_info");
        
        JSONObject contentJsonObj = candidateArray.optJSONObject(0);

        JSONArray mActivityList = contentJsonObj.optJSONArray("active_list");
        if (mActivityList != null) {
            for (int i = 0; i < mActivityList.length(); i++) {
                mInfos.add(new ActivityInfo(mActivityList.optJSONArray(i)));
            }
        }
    }
    
    private void initData() {
        mAdapter = new MonthActivityAdapter(this);
        mAdapter.setActivityInfo(mInfos);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
