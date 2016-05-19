package com.longluo.demo.ndk;

import com.longluo.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NdkDemoActivity extends Activity {

	// 使用静态代码块加载类库
	static {
		System.loadLibrary("first_jni"); //一定要注意名称没有“lib"
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ndkdemo);

		initViews();
	}

	private void initViews() {
		Button btn1 = (Button) findViewById(R.id.show_btn);

		final EditText oneEdit = (EditText) findViewById(R.id.one_number);
		final EditText twoEdit = (EditText) findViewById(R.id.two_number);
		final Button btn2 = (Button) findViewById(R.id.calculate_btn);

		// 定义本地类
		final Jni jni = new Jni();

		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 显示从C代码中返回的字符串
				Toast.makeText(getApplicationContext(), jni.getString(), 1500)
						.show();
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 调用C代码中的加法操作
				String oneNumber = oneEdit.getText().toString();
				String twoNumber = twoEdit.getText().toString();
				int oneNumbers = Integer.valueOf(oneNumber);
				int twoNumbers = Integer.valueOf(twoNumber);
				btn2.setText(jni.plus(oneNumbers, twoNumbers) + "");
			}
		});
	}

}
