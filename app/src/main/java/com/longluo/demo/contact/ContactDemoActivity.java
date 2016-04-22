package com.longluo.demo.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.longluo.demo.R;
import com.longluo.demo.contact.dial.ContactRecordListActivity;
import com.longluo.demo.contact.message.SMSListActivity;

public class ContactDemoActivity extends Activity implements OnClickListener {
    private Button btnLoadContacts;
    private Button btnGetStarredContacts;
    private Button btnGetRecentContacts;
    private Button btnGetSMS;
    private Button btnOpenTheContactDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_demo);

        initViews();
    }

    private void initViews() {
        btnLoadContacts = (Button) findViewById(R.id.btn_load_contacts);
        btnGetStarredContacts = (Button) findViewById(R.id.btn_get_starred_contacts);
        btnGetRecentContacts = (Button) findViewById(R.id.btn_get_recent_contact);
        btnGetSMS = (Button) findViewById(R.id.btn_get_sms);
        btnOpenTheContactDetail = (Button) findViewById(R.id.btn_open_contact_detail);

        btnLoadContacts.setOnClickListener(this);
        btnGetStarredContacts.setOnClickListener(this);
        btnGetRecentContacts.setOnClickListener(this);
        btnGetSMS.setOnClickListener(this);
        btnOpenTheContactDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_load_contacts: // 获取联系人
                intent = new Intent(this, ContactListActivity.class);
                break;

            case R.id.btn_get_starred_contacts:
                intent = new Intent(this, StarredContactsActivity.class);
                break;

            case R.id.btn_get_recent_contact: // 获取通话记录
                intent = new Intent(this, ContactRecordListActivity.class);
                break;

            case R.id.btn_get_sms: // 获取短信记录
                intent = new Intent(this, SMSListActivity.class);
                break;

            case R.id.btn_open_contact_detail:
                intent = new Intent(this, ContactDetailActivity.class);
                break;
        }

        startActivity(intent);
    }
}
