package com.longluo.demo.webview;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.longluo.demo.R;

public class WebViewDemoActivity extends Activity {

	private WebView mWebView;
	private List<String> mList;
	private int mKeyCode;

	@SuppressLint("JavascriptInterface")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_demo);

		mWebView = (WebView) findViewById(R.id.htmlview);
		
		initData();

		WebSettings mWebSettings = mWebView.getSettings();

		// 是否允许在webview中执行javascript
		mWebSettings.setJavaScriptEnabled(true);
		
		mWebSettings.setSupportZoom(false);
		mWebSettings.setBuiltInZoomControls(false);
		mWebSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebSettings.setDefaultFontSize(18);
		
		mWebView.setWebChromeClient(new WebChromeClient());

		mWebView.addJavascriptInterface(this, "javatojs");

		// Load the web
		mWebView.loadUrl("file:///android_asset/index.html");
		
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		mKeyCode = keyCode;
		Log.i("AA", "keyCode=" + keyCode);
		mWebView.loadUrl("javascript: OnKeyUp()");
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public int getKeyCode() {
		return mKeyCode;
	}

	void initData() {
		mList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			mList.add("我是List中的第" + (i + 1) + "行");
		}
	}

	/**
	 * 该方法将在js脚本中，通过window.javatojs.....()进行调用
	 * 
	 * @return
	 */
	public Object getObject(int index) {
		Log.i("A", "getObject");
		return mList.get(index);
	}

	public int getSize() {
		Log.i("A", "getSize");
		return mList.size();
	}

	public void Callfunction() {
		Log.i("A", "Callfunction");
		mWebView.loadUrl("javascript: GetList()");
	}

	public void printStr(String str) {
		Log.i("A", "GetList:" + str);
	}

}
