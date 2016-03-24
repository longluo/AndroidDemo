package com.longluo.demo.log;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class LogUtilSetObject {

	public static synchronized void setNetworkReportObject(String url,
			String SessionID, int network, long elapsedTime,
			ArrayList<BasicNameValuePair> params, int clientResponseCode,
			String exceptionMsg) {
		Log.e("networkerror", "networkerrornetworkerror");

		StringBuffer strBf = new StringBuffer();
		// strBf.append("[");
		strBf.append("{");
		String paramsName;
		String paramsValue;
		for (int i = 0; i < params.size(); i++) {
			paramsName = params.get(i).getName();
			paramsValue = params.get(i).getValue();
			if (i < params.size() - 1) {
				strBf.append(paramsName + ":" + paramsValue + ",");
			} else {
				strBf.append(paramsName + ":" + paramsValue);
			}
		}
		strBf.append("}");
		// strBf.append("]");
		ReportObject object = NetworkErrorReport.getInstance()
				.getReportObject();
		if (object != null) {
			if (object instanceof NetworkReportObject) {
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
					((NetworkReportObject) object).requestBody = strBf
							.toString();
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
				((NetworkReportObject) object).exceptionMsg = exceptionMsg
						.replaceAll("\n", "#");
			}
			NetworkErrorReport.getInstance().appendOneLog(object);
		}
	}
}