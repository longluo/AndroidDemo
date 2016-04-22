package com.longluo.demo.log;

import android.util.Log;

public class LogUtil {

	public static synchronized void setNetworkReportObject(String url,
			String SessionID, int network, long elapsedTime,
			String requestBody, int clientResponseCode, String exceptionMsg) {
		Log.e("networknotfound", "networkerror");
		ReportObject object = NetworkErrorReport.getInstance()
				.getReportObject();
		if (object != null) {
			if (object instanceof NetworkReportObject) {
				// ((NetworkReportObject)object).httpUrl = url;
				if (SessionID == null) {
					((NetworkReportObject) object).sessionId = "";
				} else {
					((NetworkReportObject) object).sessionId = SessionID;
				}
				((NetworkReportObject) object).network = network;
				((NetworkReportObject) object).elapsedTime = elapsedTime;
				if (url == null) {
					((NetworkReportObject) object).httpUrl = "";
					((NetworkReportObject) object).requestBody = "";
				} else if (!url.contains("?")) {
					((NetworkReportObject) object).httpUrl = url;
					((NetworkReportObject) object).requestBody = requestBody;
				} else {
					String tempstr = url;
					String[] strArray = tempstr.split("\\?");
					((NetworkReportObject) object).httpUrl = strArray[0];
					if (strArray.length >= 2) {
						int pos = tempstr.indexOf("?");
						String ts = tempstr.substring(pos + 1);
						String requestBodyStr = "{\"params\":\"" + ts + "\"}";
						((NetworkReportObject) object).requestBody = requestBodyStr;
					} else {
						((NetworkReportObject) object).requestBody = "";
					}
				}
				((NetworkReportObject) object).clientResponseCode = clientResponseCode;
				((NetworkReportObject) object).exceptionMsg = exceptionMsg;
			}
			NetworkErrorReport.getInstance().appendOneLog(object);
		}
	}
}
