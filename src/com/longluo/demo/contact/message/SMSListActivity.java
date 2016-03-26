package com.longluo.demo.contact.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.longluo.demo.R;
import com.longluo.demo.contact.utils.BaseIntentUtil;
import com.longluo.demo.contact.utils.RexseeSMS;

public class SMSListActivity extends Activity {

	private ListView smsListView;
	private SMSAdpter smsAdpter;
	private RexseeSMS rsms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_list_view);
		smsListView = (ListView) findViewById(R.id.sms_list);
		smsAdpter = new SMSAdpter(SMSListActivity.this);
		rsms = new RexseeSMS(SMSListActivity.this);
		
		List<SMSBean> list_mmt = rsms.getThreadsNum(rsms.getThreads(0));
		smsAdpter.assignment(list_mmt);
		smsListView.setAdapter(smsAdpter);
		smsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, String> map = new HashMap<String, String>();
				SMSBean sb = (SMSBean) smsAdpter.getItem(position);
				map.put("phoneNumber", sb.getAddress());
				map.put("threadId", sb.getThread_id());
				BaseIntentUtil.intentSysDefault(SMSListActivity.this,
						MessageBoxList.class, map);
			}
		});
	}
}
