package com.longluo.demo.xml;

import java.util.List;

import android.content.Intent;

public class TouchAppInfoItem {
	private String name;
	private String className;

	private List<String> actions;
	private List<String> schemes;
	private List<String> datas;
	private List<String> icons;

	private Intent intent;

	public TouchAppInfoItem() {
		this(null, null, null, null, null);
	}

	public TouchAppInfoItem(String name, String className,
			List<String> actions, List<String> schemes, List<String> icons) {
		this.name = name;
		this.className = className;
		this.actions = actions;
		this.schemes = schemes;
		this.icons = icons;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return this.className;
	}

	public void setAction(List<String> actions) {
		this.actions = actions;
	}

	public List<String> getAction() {
		return this.actions;
	}

	public void setScheme(List<String> schemes) {
		this.schemes = schemes;
	}

	public List<String> getScheme() {
		return this.schemes;
	}

	public void setData(List<String> datas) {
		this.datas = datas;
	}

	public List<String> getData() {
		return this.datas;
	}

	public void setIcon(List<String> icons) {
		this.icons = icons;
	}

	public List<String> getIcon() {
		return this.icons;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public Intent getIntent() {
		return this.intent;
	}

	public boolean equals(TouchAppInfoItem item) {
		if (this.name == null || this.className == null || item.name == null
				|| item.className == null) {
			return false;
		}
		return this.name.equals(item.getName())
				&& this.className.equals(item.getClassName());
	}

	@Override
	public String toString() {
		return " " + name + "," + className + "," + actions + "," + schemes
				+ "," + datas + "," + icons + "," + intent;
	}

}
