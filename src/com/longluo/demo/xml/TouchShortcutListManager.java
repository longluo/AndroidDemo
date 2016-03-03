package com.longluo.demo.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class TouchShortcutListManager {
	private static Context mContext;

	private static volatile TouchShortcutListManager mInstance;

	public static TouchShortcutListManager getInstance(Context context) {
		if (mInstance == null) {
			synchronized (TouchShortcutListManager.class) {
				if (mInstance == null) {
					mInstance = new TouchShortcutListManager();
				}
			}
		}

		mContext = context;
		return mInstance;
	}

	public List<TouchAppInfo> initData() throws Exception {
		Log.d("test", "TouchAppInfo, initData");

		List<TouchAppInfo> items = null;
		InputStream is = mContext.getAssets().open(
				"category/force_touch_shortcut.xml");
		items = parse(is);

		return items;
	}

	private List<TouchAppInfo> parse(InputStream is) throws Exception {
		List<TouchAppInfo> apps = null;
		TouchAppInfo app = null;
		List<TouchAppInfoItem> items = null;

		List<String> actions = null;
		List<String> schemes = null;
		List<String> datas = null;

		List<String> icons = null;

		TouchAppInfoItem infoItem = null;
		XmlPullParser parser = Xml.newPullParser(); 
		parser.setInput(is, "UTF-8");

		int eventType = parser.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				apps = new ArrayList<TouchAppInfo>();
				break;

			case XmlPullParser.START_TAG:
				if (parser.getName().equals("app")) {
					app = new TouchAppInfo();
					items = new ArrayList<TouchAppInfoItem>();
					app.setPackageName(parser.getAttributeValue(0));
					eventType = parser.next();
				} else if (parser.getName().equals("item")) {
					infoItem = new TouchAppInfoItem();
					int count = parser.getAttributeCount();

					if (count > 0) {
						infoItem.setName(parser.getAttributeValue(0));
						if (count >= 2) {
							infoItem.setClassName(parser.getAttributeValue(1));
						}
					}
					actions = new ArrayList<String>();
					schemes = new ArrayList<String>();
					datas = new ArrayList<String>();

					icons = new ArrayList<String>();

					eventType = parser.next();
				} else if (parser.getName().equals("scheme")) {
					datas.add(parser.nextText());
					eventType = parser.next();

				} else if (parser.getName().equals("data")) {

					schemes.add(parser.nextText());
					eventType = parser.next();

				} else if (parser.getName().equals("action")) {

					actions.add(parser.nextText());
					eventType = parser.next();

				} else if (parser.getName().equals("icon")) {
					icons.add(parser.nextText());
					eventType = parser.next();
				}

				break;

			case XmlPullParser.END_TAG:
				if (parser.getName().equals("app")) {
					app.setItem(items);
					apps.add(app);
					app = null;
					items = null;
				} else if (parser.getName().equals("item")) {
					items.add(infoItem);
					infoItem.setAction(actions);
					infoItem.setScheme(schemes);
					infoItem.setData(datas);

					infoItem.setIcon(icons);

					infoItem = null;
					actions = null;
					schemes = null;
					datas = null;

					icons = null;

				}
				break;
			}

			eventType = parser.next();
		}

		return apps;
	}
}
