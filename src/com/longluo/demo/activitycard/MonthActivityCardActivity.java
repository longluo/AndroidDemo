package com.longluo.demo.activitycard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.widgets.imgcard.MonthActivityCard;

/**
 * MonthActivityCardActivity
 * 
 * @author luolong
 * @version
 * @Date 2015-6-26 下午4:23:14
 */
public class MonthActivityCardActivity extends Activity {
    private MonthActivityCard mCard;
    private TextView mTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_activity_card);

        initView();
    }

    private void initView() {
        mCard = (MonthActivityCard) findViewById(R.id.calendarCard1);
        mTitleTV = (TextView) findViewById(R.id.tv_title);
    }

}
