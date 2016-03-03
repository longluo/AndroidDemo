package com.longluo.demo.xml;

import java.util.List;

import com.longluo.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class XmlParserDemoActivity extends Activity {
	private List<TouchAppInfo> mAppInfos;
	private TextView mTvAppInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xml_demo);
		
		try {
			mAppInfos = TouchShortcutListManager.getInstance(XmlParserDemoActivity.this).initData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		initViews();
	}

	private void initViews() {
		mTvAppInfo = (TextView) findViewById(R.id.tv_app_info);
		
		mTvAppInfo.setText(" " + mAppInfos.get(1).getItem().get(1).toString());
	}

}
