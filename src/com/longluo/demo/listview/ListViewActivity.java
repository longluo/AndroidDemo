package com.longluo.demo.listview;

import java.util.ArrayList;
import java.util.List;

import com.longluo.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewActivity extends Activity {
	private static final String TAG = "ListViewActivity";

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		initViews();
	}

	private void initViews() {
		mListView = (ListView) findViewById(R.id.id_lv);

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("这是第" + i + "个");
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, list);

		mListView.setAdapter(adapter);

		mListView.setOnScrollListener(new ListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				// 没有滚动的时候
				case OnScrollListener.SCROLL_STATE_IDLE:
					System.out.println("最后显示的listview的位置是--->>"
							+ mListView.getLastVisiblePosition());
					if (mListView.getLastVisiblePosition() == (mListView
							.getCount() - 1)) {
						Toast.makeText(ListViewActivity.this, "滑到listView底了",
								Toast.LENGTH_SHORT).show();
					}

					if (mListView.getFirstVisiblePosition() == 0) {
						Toast.makeText(ListViewActivity.this, "listView顶头了",
								Toast.LENGTH_SHORT).show();
					}
					break;

				case OnScrollListener.SCROLL_STATE_FLING:
					System.out.println("SCROLL_STATE_FLING");
					break;

				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					System.out.println("SCROLL_STATE_TOUCH_SCROLL"
							+ "能看到的最后一个显示的位置--->>"
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

			}
		});
	}

}
