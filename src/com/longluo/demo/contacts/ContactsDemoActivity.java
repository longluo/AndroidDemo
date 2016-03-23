package com.longluo.demo.contacts;

import com.longluo.demo.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

public class ContactsDemoActivity extends Activity {
	
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
		
		while (cursor.moveToNext()) {
			count++;

			long id = cursor.getLong(cursor.getColumnIndex("_id"));
			
			Cursor pCur = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ Long.toString(id), null, null);

			// multi numbers
			String phoneNumbers = "";
			while (pCur.moveToNext()) {
				String strPhoneNumber = pCur
						.getString(pCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				phoneNumbers += strPhoneNumber + ":";
			}
			
			phoneNumbers += "\n";
			pCur.close();
			
			String name = cursor.getString(cursor.getColumnIndex("display_name"));
			
			Log.d("test00", "getFavoriteContacts, name=" + name + ",phoneNumbers=" + phoneNumbers);

			mContactInfoTv.setText(" " + name + "," + phoneNumbers);
			
		}
		
		cursor.close();
	}
	

}
