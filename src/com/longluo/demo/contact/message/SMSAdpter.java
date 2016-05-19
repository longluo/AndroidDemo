package com.longluo.demo.contact.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longluo.demo.R;

/**
 * 短信适配器
 */
public class SMSAdpter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<SMSBean> smsList;
	private Date date;
	private SimpleDateFormat sdf;

	public SMSAdpter(Context context) {
		mInflater = LayoutInflater.from(context);
		this.smsList = new ArrayList<SMSBean>();
		this.date = new Date();
		this.sdf = new SimpleDateFormat("MM/dd HH:mm");
	}

	public void assignment(List<SMSBean> smsList) {
		this.smsList = smsList;
	}

	public void add(SMSBean bean) {
		smsList.add(bean);
	}

	public void remove(int position) {
		smsList.remove(position);
	}

	@Override
	public int getCount() {
		return smsList.size();
	}

	@Override
	public Object getItem(int position) {
		return smsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.sms_list_item, parent,
					false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.content = (TextView) convertView.findViewById(R.id.content);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(smsList.get(position).getAddress());
		holder.count.setText("(" + smsList.get(position).getMsg_count() + ")");

		this.date.setTime(smsList.get(position).getDate());
		holder.date.setText(this.sdf.format(date));

		holder.content.setText(smsList.get(position).getMsg_snippet());

		convertView.setTag(holder);
		return convertView;
	}

	public final class ViewHolder {
		public TextView name;
		public TextView count;
		public TextView date;
		public TextView content;
	}
}
