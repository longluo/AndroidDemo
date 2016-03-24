package com.longluo.demo.log;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import org.apache.http.conn.util.InetAddressUtils;

import com.longluo.demo.DemoApp;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public abstract class LogReport {

	public final static int CRASH_TYPE = 1;

	public final static int NETWORK_TYPE = 2;

	public int type = 1;

	public static String SDPath = Environment.getExternalStorageDirectory()
			.getPath();

	public static String logPath = SDPath + "/AndroidDemo/"; // log文件路径

	public String fileName = ""; // log文件名字

	public String appVersion;

	public LogReport() {
		try {
			appVersion = DemoApp.getContext().getPackageManager()
					.getPackageInfo(DemoApp.getContext().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// ignore
		}
	}

	/**
	 * 读取日志文件 ，并返回 如果没有日志文件，返回空
	 * 
	 * @return
	 */
	public abstract List<ReportObject> getReportLogList();

	/**
	 * @return
	 */
	public abstract String getReportLogJson();

	/**
	 * sdcard是否可用 true为可用
	 * 
	 * @return
	 */
	public boolean isSdcardCanUse() {
		boolean flag = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		return flag;
	}

	/**
	 * 删除日志文件
	 * 
	 * @return
	 */
	public boolean deleteLogFile() {
		File file = new File(logPath, fileName);
		if (file.exists())
			file.delete();
		return true;
	}

	public abstract String format(ReportObject data);

	public abstract String formatToJSON(ReportObject data);

	public boolean appendOneLog(ReportObject data) {
		// String str = format(data);
		String str = formatToJSON(data);
		File file = new File(logPath, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			fos.write(str.getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
		}
		return true;
	}

	private String getIMEI() {
		// 获取IMEI码
		TelephonyManager telephonyManager = (TelephonyManager) DemoApp
				.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		String IMEI = telephonyManager.getDeviceId();
		return IMEI;
	}

	public String getLocalHostIp() {
		String ipaddress = "";
		try {
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) {
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(ip
									.getHostAddress())) {
						return ipaddress = ip.getHostAddress();
					}
				}

			}
		} catch (SocketException e) {
			Log.e("feige", "获取本地ip地址失败");
			e.printStackTrace();
		}
		return ipaddress;

	}

	/**
	 * 
	 * @param retReportObject
	 *            public long logTime;//日志记录时间（时间戳，单位为秒 ） public String
	 *            traceId;//跟踪ID（用于跟踪用户的一次请求唯一标示） public String
	 *            userId;//用户id(可用于分析用户行为使用) public String deviceId;//客户端设备id
	 *            public String channelId;//来源渠道号 public String
	 *            subChannelId;//子渠道号 public int
	 *            clientType;//客户端类型（传入对应的数值）WEB(1,"web"), WAP(2,"wap"),
	 *            WAP_SIMPLE(3,"wap_simple"), WAP_COLOR(4,"wap_color"),
	 *            WAP_HTML5(5,"wap_html5"),
	 *            MOBILE(6,"mobile"),ANDROID(7,"android"
	 *            ),IOS(8,"ios"),MATCH_MOBILE
	 *            (9,"match_mobile"),MATCH_ANDROID(10,
	 *            "match_android"),MATCH_IOS(
	 *            11,"match_ios"),PAD_ANDROID(12,"pad_android"
	 *            ),PAD_IOS(13,"pad_ios"
	 *            ),IOS_PAY(51,"ios_pay"),MATCHHL_MOBILE(52
	 *            ,"matchhl_mobile"),MATCHHL_ANDROID
	 *            (53,"matchhl_android"),MATCHHL_IOS
	 *            (54,"matchhl_ios"),CITY_MOBILE
	 *            (61,"city_mobile"),CITY_ANDROID(62
	 *            ,"city_android"),CITY_IOS(63,"city_ios"),UNKNOW(0,"unknow");
	 *            public String systemVersion;//客户端系统版本号 public String
	 *            appVersion;//app版本号 public String clientIp;//客户端ip
	 */
	public void setBaseObject(ReportObject retReportObject) {

		retReportObject.logTime = System.currentTimeMillis() / 1000;
		retReportObject.traceId = UUID.randomUUID() + "";
//		if (DemoApp.getAccount() == null) {
//			retReportObject.userId = "";
//		} else {
//			retReportObject.userId = DemoApp.getAccount().memberId;
//		}
		retReportObject.deviceId = getIMEI();
//		retReportObject.channelId = DeviceUtils.getMainChannel();
//		retReportObject.subChannelId = DeviceUtils.getSubChannel();
		retReportObject.clientType = 7;
		retReportObject.systemVersion = android.os.Build.VERSION.RELEASE;
		retReportObject.appVersion = appVersion;
		retReportObject.clientIp = getLocalHostIp();
	}

	/**
	 * 
	 * @param type
	 *            =
	 * @return
	 */
	public ReportObject getReportObject() {
		ReportObject retReportObject = null;
		switch (type) {
		case CRASH_TYPE:
			retReportObject = new CrashReportObject();
			break;

		case NETWORK_TYPE:
			retReportObject = new NetworkReportObject();
			break;

		default:
			break;
		}
		if (retReportObject != null) {
			setBaseObject(retReportObject);
		}
		return retReportObject;
	}
}
