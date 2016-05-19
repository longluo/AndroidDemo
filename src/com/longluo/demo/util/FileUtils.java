package com.longluo.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;

import android.content.Context;
import android.util.Log;

/**
 * FileUtils
 * 
 * @author luolong
 * @Date 2015-6-29 下午8:13:47
 * @version
 */
public class FileUtils {

	public static String getFromAssets(Context context, String fileName) {
		String result = "";

		try {
			InputStreamReader inputReader = new InputStreamReader(context
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);

			String line = "";

			while ((line = bufReader.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.d("luolong", "getFromAssets,fileName=" + fileName + ",result="
				+ result);

		return result;
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}

		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		
		return bigInt.toString(16);
	}
}
