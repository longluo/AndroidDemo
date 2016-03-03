package com.longluo.demo.xml;

import java.util.List;

public class TouchAppInfo {
	private String packageName;
	private List<TouchAppInfoItem> item;

	public TouchAppInfo() {
		this(null, null);
	}

	public TouchAppInfo(String packageName, List<TouchAppInfoItem> item) {
		this.packageName = packageName;
		this.item = item;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setItem(List<TouchAppInfoItem> item) {
		this.item = item;
	}

	public List<TouchAppInfoItem> getItem() {
		return this.item;
	}

	public boolean equals(TouchAppInfo info) {
		if (this.getPackageName() == null || info.getPackageName() == null) {
			return false;
		}
		return this.getPackageName().equals(info.getPackageName());
	}
}
