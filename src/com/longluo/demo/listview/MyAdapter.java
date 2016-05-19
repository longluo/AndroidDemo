package com.longluo.demo.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longluo.demo.R;

public class MyAdapter extends BaseAdapter {

	private Context mContext;

	private List<String> mStrings = new ArrayList<String>();

	private int mSelectedItem = 0;

	class ViewHolder {
		TextView contentTextView;
	}

	public MyAdapter(Context context) {
		mContext = context;

		initData();
	}

	private void initData() {
		for (int i = 0; i < 6; i++) {
			mStrings.add("这是第" + i + "个");
		}
	}

	public void setItemSelected(int selected) {
		mSelectedItem = selected;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mStrings.size();
	}

	@Override
	public Object getItem(int position) {
		return mStrings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.layout_list_view_demo_adpter, null);

			holder = new ViewHolder();
			holder.contentTextView = (TextView) convertView
					.findViewById(R.id.tv_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.contentTextView.setText(mStrings.get(position));
		
		if (mSelectedItem == position) {
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
		} else {
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
		}

		return convertView;
	}
}
