package com.longluo.demo.qrcode.zxing.client.android.share;

import android.graphics.drawable.Drawable;

final class AppInfo implements Comparable<AppInfo> {

    private final String packageName;
    private final String label;
    private final Drawable icon;

    AppInfo(String packageName, String label, Drawable icon) {
        this.packageName = packageName;
        this.label = label;
        this.icon = icon;
    }

    String getPackageName() {
        return packageName;
    }

    Drawable getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public int compareTo(AppInfo another) {
        return label.compareTo(another.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AppInfo)) {
            return false;
        }
        AppInfo another = (AppInfo) other;
        return label.equals(another.label);
    }

}
