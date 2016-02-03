#include"jni.h"

/*
 * Class:     com_longluo_demo_ndk_Jni
 * Method:    getString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_longluo_demo_ndk_Jni_getString(JNIEnv* env, jobject obj)
{
	//返回一个字符串
	return (*env)->NewStringUTF(env, "Hello JNI NDK!");
}

/*
 * Class:     com_longluo_demo_ndk_Jni
 * Method:    plus
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_longluo_demo_ndk_Jni_plus(JNIEnv* env, jobject obj, jint a, jint b)
{
	//返回计算结果
	return a + b;
}
