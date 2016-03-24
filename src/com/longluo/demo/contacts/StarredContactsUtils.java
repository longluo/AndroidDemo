package com.longluo.demo.contacts;

import com.longluo.demo.R;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.widget.Toast;

public class StarredContactsUtils {

	/**
	 * 获得收藏夹的联系人
	 */
	private void getKeepedContacts(Context context) {
		Cursor cur = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null,
				ContactsContract.Contacts.STARRED + " =  1 ", null, null);

		// startManagingCursor(cur);

		int num = cur.getCount();
		System.out.println(num + "");
		int count = 0;
		while (cur.moveToNext()) {
			count++;

			long id = cur.getLong(cur.getColumnIndex("_id"));
			Cursor pcur = context.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ Long.toString(id), null, null);

			// 处理多个号码的情况
			String phoneNumbers = "";
			while (pcur.moveToNext()) {
				String strPhoneNumber = pcur
						.getString(pcur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				phoneNumbers += strPhoneNumber + ":";
			}
			phoneNumbers += "\n";
			pcur.close();
			String name = cur.getString(cur.getColumnIndex("display_name"));

			// contactNameList.add(name);
			// contactNumList.add(phoneNumbers);
		}
		cur.close();
	}

	/**
	 * 添加到收藏夹
	 */
	private void addKeepedContacts(Context context, long _id) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cusor = null;
		
		@SuppressWarnings("deprecation")
		String[] projection = new String[] { Contacts.People._ID,
				Contacts.People.NAME, Contacts.People.NUMBER };
		cusor = contentResolver.query(Contacts.People.CONTENT_URI, projection,
				Contacts.People._ID + "= ", new String[] { _id + "" },
				Contacts.People.NAME + " ASC");
		cusor.moveToFirst();
		
		ContentValues values = new ContentValues();
		
		Uri uri = Uri.withAppendedPath(Contacts.People.CONTENT_URI,
				cusor.getString(cusor.getColumnIndex(Contacts.People._ID)));
		
		// values.put(Contacts.People.NAME, newName);
		values.put(Contacts.People.STARRED, 1);
		// values.put(Contacts.Phones.NUMBER, newPhoneNum);
		contentResolver.update(uri, values, null, null);
		
		Toast.makeText(context,
				context.getResources().getString(R.string.add_succeed),
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 从收藏夹中移出
	 */
	@SuppressWarnings("deprecation")
	private void removeKeepedContacts(Context context, long _id) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cusor = null;
		
		@SuppressWarnings("deprecation")
		String[] projection = new String[] { Contacts.People._ID,
				Contacts.People.NAME, Contacts.People.NUMBER };
		cusor = contentResolver.query(Contacts.People.CONTENT_URI, projection,
				Contacts.People._ID + "= ", new String[] { _id + "" },
				Contacts.People.NAME + " ASC");
		cusor.moveToFirst();
		
		ContentValues values = new ContentValues();
		
		Uri uri = Uri.withAppendedPath(Contacts.People.CONTENT_URI,
				cusor.getString(cusor.getColumnIndex(Contacts.People._ID)));
		
		// values.put(Contacts.People.NAME, newName);
		values.put(Contacts.People.STARRED, 0);
		// values.put(Contacts.Phones.NUMBER, newPhoneNum);
		contentResolver.update(uri, values, null, null);

		// new getKeepedContactsTask().execute((Void) null);
		Toast.makeText(context,
				context.getResources().getString(R.string.remove_succeed),
				Toast.LENGTH_SHORT).show();
	}
}
