package com.longluo.demo.contact;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.longluo.demo.R;

public class StarredContactsActivity extends Activity {

	private TextView mContactInfoTv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_demo);

		initViews();
		initData();
	}

	private void initViews() {
		mContactInfoTv = (TextView) findViewById(R.id.tv_contact);

	}

	private void initData() {
		getFavoriteContacts();

	}

	private void getFavoriteContacts() {
		Cursor cursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null,
				ContactsContract.Contacts.STARRED + " = 1 ", null, null);

		if (cursor == null) {
			return;
		}

		int num = cursor.getCount();

		Log.d("test00", "getFavoriteContacts, num=" + num);

		int count = 0;
		
		StringBuffer sb = new StringBuffer(128);

		try {
			if (cursor.moveToFirst()) {
				do {
					count++;

					long id = cursor.getLong(cursor.getColumnIndex("_id"));

					Cursor pCur = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=" + Long.toString(id), null, null);
					pCur.moveToFirst();
					String strPhoneNumber = "";
					strPhoneNumber = pCur
							.getString(pCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

					// multi numbers
/*					String phoneNumbers = "";
					while (pCur.moveToNext()) {
						String strPhoneNumber = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						phoneNumbers += strPhoneNumber + ":";
					}*/

//					phoneNumbers += "\n";
					
					pCur.close();

					String name = cursor.getString(cursor
							.getColumnIndex("display_name"));

					Log.d("test00", "getFavoriteContacts, name=" + name
							+ ",phoneNumbers=" + strPhoneNumber);
					
					sb.append(name).append(strPhoneNumber).append("\n");

				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			
			mContactInfoTv.setText(sb);
		}
	}

}
