package com.longluo.demo.dial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.longluo.demo.R;

public class DialDemoActivity extends Activity {
	private Button mDialPageBtn;
	private Button mDialPageNumberBtn;
	private Button mStartDialBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_dial_demo);

		initViews();
	}

	private void initViews() {
		mDialPageBtn = (Button) findViewById(R.id.btn_dial_page);
		mDialPageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL_BUTTON");
				startActivity(intent);
			}
		});

		mDialPageNumberBtn = (Button) findViewById(R.id.btn_dial_page_with_number);
		mDialPageNumberBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("tel:10086");
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
			}
		});

		mStartDialBtn = (Button) findViewById(R.id.btn_start_dial);
		mStartDialBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String num = "12580";
				Pattern p = Pattern.compile("\\d+?");
				Matcher match = p.matcher(num);
				// 正则验证输入的是否为数字
				if (match.matches()) {
					Intent intent = new Intent("android.intent.action.CALL",
							Uri.parse("tel:" + num));
					startActivity(intent);
				} else {
					Toast.makeText(DialDemoActivity.this, "号码不对",
							Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private void dial(String number) {

		// 只进入拨号界面，不拨打
		Uri uri = Uri.parse("tel:" + number);
		Intent intent = new Intent(Intent.ACTION_DIAL, uri);
		startActivity(intent);

		// 直接拨打
		Uri uri2 = Uri.parse("tel:" + number);
		Intent intent2 = new Intent(Intent.ACTION_CALL, uri);
		startActivity(intent);
	}

}
