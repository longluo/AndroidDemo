package com.longluo.demo.friends;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.longluo.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luolong
 * @date 2015-9-25 13:53:48
 */
public class FriendsActivity extends Activity {
    private static final String TAG = FriendsActivity.class.getSimpleName();

    private View mNoFriendsView;
    private View mExploreBtn;
    private ListView mFriendsLv;

    private FriendsAdapter mFriendsAdapter;
    private List<FriendData> mFriendDatas = new ArrayList<FriendData>();


    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {


                default:
                    break;
            }

            super.handleMessage(msg);
        }

    };

    private AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemClick position=" + position + ",id=" + id);


        }
    };

    private AdapterView.OnItemLongClickListener mItemLongClickListener = new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            Log.d(TAG, "onItemLongClick, position=" + position + ",id=" + id);


            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

    }

    private void initView() {
        mFriendsLv = (ListView) findViewById(R.id.id_lv_friends);

        mNoFriendsView = (View) findViewById(R.id.id_no_friends);

    }

    private void initData() {


    }

    private void initListener() {

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
