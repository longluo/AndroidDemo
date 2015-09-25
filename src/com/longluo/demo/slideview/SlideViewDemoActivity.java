package com.longluo.demo.slideview;

import java.util.ArrayList;
import java.util.List;

import com.longluo.demo.R;
import com.longluo.demo.widgets.slideview.ListViewCompat;
import com.longluo.demo.widgets.slideview.SlideView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author luolong
 * @date 2015-9-25 14:06:37
 * 
 */
public class SlideViewDemoActivity extends Activity implements
		OnItemClickListener, OnClickListener {

	private static final String TAG = "MainActivity";

	private ListViewCompat mListView;

	private List<MessageItem> mMessageItems = new ArrayList<SlideViewDemoActivity.MessageItem>();

	private SlideView mLastSlideViewWithStatusOn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slideview);
		initView();
	}

	private void initView() {
		mListView = (ListViewCompat) findViewById(R.id.list);

		for (int i = 0; i < 20; i++) {
			MessageItem item = new MessageItem();
			if (i % 3 == 0) {
				item.iconRes = R.drawable.default_qq_avatar;
				item.title = "腾讯新闻";
				item.msg = "青岛爆炸满月：大量鱼虾死亡";
				item.time = "晚上18:18";
			} else {
				item.iconRes = R.drawable.wechat_icon;
				item.title = "微信团队";
				item.msg = "欢迎你使用微信";
				item.time = "12月18日";
			}
			mMessageItems.add(item);
		}
		mListView.setAdapter(new SlideAdapter());
		mListView.setOnItemClickListener(this);
	}

	private class SlideAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		SlideAdapter() {
			super();
			mInflater = getLayoutInflater();
		}

		@Override
		public int getCount() {
			return mMessageItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mMessageItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			SlideView slideView = (SlideView) convertView;
			if (slideView == null) {
				View itemView = mInflater.inflate(R.layout.slideview_list_item, null);

				slideView = new SlideView(SlideViewDemoActivity.this);
				slideView.setContentView(itemView);

				holder = new ViewHolder(slideView);
				// slideView.setOnSlideListener(MainActivity.this);
				slideView.setTag(holder);
			} else {
				holder = (ViewHolder) slideView.getTag();
			}
			MessageItem item = mMessageItems.get(position);
			item.slideView = slideView;
			holder.icon.setImageResource(item.iconRes);
			holder.title.setText(item.title);
			holder.msg.setText(item.msg);
			holder.time.setText(item.time);
			holder.deleteHolder.setOnClickListener(SlideViewDemoActivity.this);

			return slideView;
		}

	}

	public class MessageItem {
		public int iconRes;
		public String title;
		public String msg;
		public String time;
		public SlideView slideView;
	}

	private static class ViewHolder {
		public ImageView icon;
		public TextView title;
		public TextView msg;
		public TextView time;
		public ViewGroup deleteHolder;

		ViewHolder(View view) {
			icon = (ImageView) view.findViewById(R.id.icon);
			title = (TextView) view.findViewById(R.id.title);
			msg = (TextView) view.findViewById(R.id.msg);
			time = (TextView) view.findViewById(R.id.time);
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.e(TAG, "onItemClick position=" + position);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.holder) {
			Log.e(TAG, "onClick v=" + v);
		}
	}
}
