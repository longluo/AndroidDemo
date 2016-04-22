package com.longluo.demo.contact;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.longluo.demo.R;

public class ContactDetailActivity extends Activity {
    private Button mBtnOpenContactDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail_demo);

        initViews();
    }

    private void initViews() {
        mBtnOpenContactDetail = (Button) findViewById(R.id.btn_open_contact_detail);
        mBtnOpenContactDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    private void start() {
        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null,
                ContactsContract.Contacts.STARRED + " = 1 ", null, null);

        if (cursor == null) {
            return;
        }

        int count = 0;

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

                    String phoneNumber = pCur
                            .getString(pCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    pCur.close();

                    String name = cursor.getString(cursor
                            .getColumnIndex("display_name"));

                    Log.d("test00", "getFavoriteContacts, name=" + name
                            + ",phoneNumbers=" + phoneNumber);

                    if (count >= 3) {
                        break;
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
