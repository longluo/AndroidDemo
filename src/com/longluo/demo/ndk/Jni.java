package com.longluo.demo.ndk;

public class Jni {
	// 获取字符串
	public native String getString();

	// 进行加法操作
	public native int plus(int a, int b);
}
