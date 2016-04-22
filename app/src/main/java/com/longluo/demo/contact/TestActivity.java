package com.longluo.demo.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 获取联系人、通话记录、短信
 */
public class TestActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);

        queryContacts();
    }

    private void queryContacts() {
        // 获取用来操作数据的类的对象，对联系人的基本操作都是使用这个对象
        ContentResolver cr = getContentResolver();
        // 查询contacts表的所有记录
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        // 如果记录不为空
        if (cursor.getCount() > 0) {
            // 游标初始指向查询结果的第一条记录的上方，执行moveToNext函数会判断
            // 下一条记录是否存在，如果存在，指向下一条记录。否则，返回false。
            while (cursor.moveToNext()) {
                String rawContactId = "";
                // 从Contacts表当中取得ContactId
                String id = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                Log.v("contactID", id);

                // 获取RawContacts表的游标
                Cursor rawContactCur = cr.query(RawContacts.CONTENT_URI, null,
                        RawContacts._ID + "=?", new String[]{id}, null);
                // 该查询结果一般只返回一条记录，所以我们直接让游标指向第一条记录
                if (rawContactCur.moveToFirst()) {
                    // 读取第一条记录的RawContacts._ID列的值
                    rawContactId = rawContactCur.getString(rawContactCur
                            .getColumnIndex(RawContacts._ID));
                    Log.v("rawContactID", rawContactId);

                }
                // 关闭游标
                rawContactCur.close();
                // 读取号码
                if (Integer
                        .parseInt(cursor.getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    // 根据查询RAW_CONTACT_ID查询该联系人的号码
                    Cursor phoneCur = cr
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID
                                            + "=?",
                                    new String[]{rawContactId}, null);
                    // 上面的ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    // 可以用下面的phoneUri代替
                    // Uri
                    // phoneUri=Uri.parse("content://com.android.contacts/data/phones");

                    // 一个联系人可能有多个号码，需要遍历
                    while (phoneCur.moveToNext()) {
                        // 获取号码
                        String number = phoneCur
                                .getString(phoneCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.v("number", number);
                        // 获取号码类型
                        String type = phoneCur
                                .getString(phoneCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        Log.v("type", type);

                    }
                    phoneCur.close();

                }
            }
            cursor.close();
        }
    }

    // 新建联系人
    public void addContact(String name, String phoneNum) {
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(
                RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        // 向data表插入数据
        if (name != "") {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
            values.put(StructuredName.GIVEN_NAME, name);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
        // 向data表插入电话号码
        if (phoneNum != "") {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
            values.put(Phone.NUMBER, phoneNum);
            values.put(Phone.TYPE, Phone.TYPE_MOBILE);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
    }

    // 删除联系人
    public void deleteContact(long rawContactId) {
        getContentResolver().delete(
                ContentUris.withAppendedId(RawContacts.CONTENT_URI,
                        rawContactId), null, null);
    }

    // 更新联系人
    public void updataCotact(long rawContactId) {
        ContentValues values = new ContentValues();
        values.put(Phone.NUMBER, "13800138000");
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        String where = ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                + ContactsContract.Data.MIMETYPE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(rawContactId),
                Phone.CONTENT_ITEM_TYPE};
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionArgs);
    }

    @Override
    public void onClick(View v) {

    }

}
