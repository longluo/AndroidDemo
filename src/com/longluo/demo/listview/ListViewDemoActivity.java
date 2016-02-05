package com.longluo.demo.listview;

import java.util.ArrayList;
import java.util.List;

import com.longluo.demo.R;
import com.longluo.demo.util.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewDemoActivity extends Activity {
	private static final String TAG = "ListViewDemoActivity";

	private ListView mListView;
	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		initViews();
	}

	private void initViews() {
		mAdapter = new MyAdapter(ListViewDemoActivity.this);

		mListView = (ListView) findViewById(R.id.id_lv);

		mListView.setAdapter(mAdapter);

		mListView.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// Log.d(TAG, "onTouch, view=" + view);

				float xDown = 0, xMove = 0, xDistance = 0;

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					xDown = event.getRawY();
					Log.d(TAG, "onTouch, ACTION_DOWN, xDown=" + xDown);
					break;

				case MotionEvent.ACTION_MOVE:
					xMove = event.getRawY();
					xDistance = xMove - xDown;
					int pos = (int) ((xDistance - 200) / 160);

					Log.d(TAG, "onTouch, ACTION_MOVE, xMove=" + xMove
							+ ",xDistance=" + xDistance + ",pos=" + pos);

					mAdapter.setItemSelected(pos);

					break;

				case MotionEvent.ACTION_UP:
					Log.d(TAG, "onTouch, ACTION_UP");

					break;

				default:
					break;
				}

				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				ToastUtils.show(ListViewDemoActivity.this, "pos=" + position
						+ ",id=" + id, Toast.LENGTH_SHORT);

				// view.setBackgroundColor(getResources().getColor(R.color.red));
			}
		});

		mListView.setOnScrollListener(new ListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				switch (scrollState) {
				// 没有滚动的时候
				case OnScrollListener.SCROLL_STATE_IDLE:
					Log.d(TAG,
							"最后显示的listview的位置是--->>"
									+ mListView.getLastVisiblePosition());

					if (mListView.getLastVisiblePosition() == (mListView
							.getCount() - 1)) {
						Toast.makeText(ListViewDemoActivity.this,
								"滑到listView底了", Toast.LENGTH_SHORT).show();
					}

					if (mListView.getFirstVisiblePosition() == 0) {
						Toast.makeText(ListViewDemoActivity.this,
								"listView顶头了", Toast.LENGTH_SHORT).show();
					}
					break;

				case OnScrollListener.SCROLL_STATE_FLING:
					Log.d(TAG, "SCROLL_STATE_FLING");
					break;

				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					Log.d(TAG,
							"SCROLL_STATE_TOUCH_SCROLL" + "能看到的最后一个显示的位置--->>"
									+ mListView.getLastVisiblePosition());
					break;

				default:
					break;
				}
			}

			/**
			 * view The view whose scroll state is being reported
			 * firstVisibleItem the index of the first visible cell (ignore if
			 * visibleItemCount == 0) visibleItemCount the number of visible
			 * cells totalItemCount the number of items in the list adaptor
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				Log.d(TAG, "onScroll, firstVisibleItem=" + firstVisibleItem);

			}
		});
	}

}
