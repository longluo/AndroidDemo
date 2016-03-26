package com.longluo.demo.contact.message;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.longluo.demo.R;

public class MessageDemoActivity extends Activity {
	private TextView mTvContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_demo);

		initViews();

		initData();
	}

	private void initViews() {
		mTvContent = (TextView) findViewById(R.id.tv_msg);

	}

	private void initData() {
		StringBuilder str = new StringBuilder(256);

		final Uri SMS_INBOX = Uri.parse("content://sms");
		Cursor cursor = getContentResolver().query(SMS_INBOX, null, null,
				null, "date desc limit 3");
		
//		final Uri SMS_INBOX = Uri.parse("content://sms/conversations");
//		Cursor cursor = mContext.getContentResolver().query(SMS_INBOX, null,
//				"type=0", null, "date desc limit 3");

		if (cursor == null) {
			return;
		}

		try {
			if (cursor.moveToFirst()) {
				do {
					String number = cursor.getString(cursor.getColumnIndex("address"));
					
					String name = cursor.getString(cursor.getColumnIndex("person"));
					
					int type = cursor.getInt(cursor.getColumnIndex("read"));

					str.append(name).append(", " + number).append("," + type).append("\n");

				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}

			mTvContent.setText(str);
		}
	}

}
