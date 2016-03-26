package com.longluo.demo.contentprovider;

import com.longluo.demo.R;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProviderDemoActivity extends Activity implements
		View.OnClickListener {
	private EditText et_name, et_sex;
	private Button btn_add, btn_select, btn_delete, btn_update;
	private TextView tv_show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_provider_demo);

		initViews();
	}

	private void initViews() {
		et_name = (EditText) findViewById(R.id.et_name);
		et_sex = (EditText) findViewById(R.id.et_sex);

		btn_add = (Button) findViewById(R.id.btn_add);
		btn_delete = (Button) findViewById(R.id.btn_delete);
		btn_update = (Button) findViewById(R.id.btn_update);
		btn_select = (Button) findViewById(R.id.btn_select);

		tv_show = (TextView) findViewById(R.id.tv_show);

		btn_add.setOnClickListener(this);
		btn_delete.setOnClickListener(this);
		btn_update.setOnClickListener(this);
		btn_select.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			addDB();
			break;

		case R.id.btn_delete:
			deleteDB();
			break;

		case R.id.btn_update:
			updateDB();
			break;

		case R.id.btn_select:
			selectDB();
			break;
		}
	}

	public void addDB() {
		ContentValues cv = new ContentValues();
		cv.put(ProviderConstants.CP_NAME, et_name.getText().toString());
		cv.put(ProviderConstants.CP_SEX, et_sex.getText().toString());
		getContentResolver().insert(ProviderConstants.URI, cv);
	}

	public void deleteDB() {
		getContentResolver().delete(ProviderConstants.URI, null, null);
	}

	public void updateDB() {
		ContentValues cv = new ContentValues();
		cv.put(ProviderConstants.CP_NAME, et_name.getText().toString());
		cv.put(ProviderConstants.CP_SEX, et_sex.getText().toString());
		getContentResolver().update(ProviderConstants.URI, null, null, null);
	}

	public Cursor selectDB() {
		Cursor c = getContentResolver().query(ProviderConstants.URI, null,
				null, null, null);
		while (c.moveToNext()) {
			tv_show.setText(c.getString(c
					.getColumnIndex(ProviderConstants.CP_NAME))
					+ " "
					+ c.getString(c.getColumnIndex(ProviderConstants.CP_SEX)));
		}
		return c;
	}

}
