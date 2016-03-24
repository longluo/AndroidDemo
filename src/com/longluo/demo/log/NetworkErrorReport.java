package com.longluo.demo.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkErrorReport extends LogReport {

	public static String NETWORKERROR_REPORT_FILENAME = "networkerror_report_321.log"; // networklog文件

	private static NetworkErrorReport instance = null;

	public final static String SEPARATOR = "$|$";// 分隔符

	public final static int SEPARATOR_SIZE = 3;// 分隔符的长度

	public static NetworkErrorReport getInstance() {
		if (instance == null) {
			instance = new NetworkErrorReport();
		}
		return instance;
	}

	public NetworkErrorReport() {
		super();
		fileName = NETWORKERROR_REPORT_FILENAME;
		type = NETWORK_TYPE;
	}

	@Override
	public String format(ReportObject data) {
		String str;
		str = SEPARATOR
				+ ((NetworkReportObject) data).logTime
				+ SEPARATOR
				+ ((NetworkReportObject) data).traceId
				+ SEPARATOR
				+ ((NetworkReportObject) data).httpUrl
				+ SEPARATOR
				+ ((NetworkReportObject) data).sessionId
				+ SEPARATOR
				+ ((NetworkReportObject) data).userId
				+ SEPARATOR
				+ ((NetworkReportObject) data).deviceId
				+ SEPARATOR
				+ ((NetworkReportObject) data).channelId
				+ SEPARATOR
				+ ((NetworkReportObject) data).subChannelId
				+ SEPARATOR
				+ ((NetworkReportObject) data).clientType
				+ SEPARATOR
				+ ((NetworkReportObject) data).systemVersion
				+ SEPARATOR
				+ ((NetworkReportObject) data).appVersion
				+ SEPARATOR
				+ ((NetworkReportObject) data).network
				+ SEPARATOR
				+ ((NetworkReportObject) data).elapsedTime
				+ SEPARATOR
				+ ((NetworkReportObject) data).requestBody
				+ SEPARATOR
				+ ((NetworkReportObject) data).clientResponseCode
				+ SEPARATOR
				+ ((NetworkReportObject) data).exceptionMsg.replaceAll("\n",
						"#") + SEPARATOR
				+ ((NetworkReportObject) data).clientIp + SEPARATOR + "\n";
		return str;

	}

	public String formatToJSON(ReportObject data) {
		StringBuffer bf = new StringBuffer();
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("logtime", ((NetworkReportObject) data).logTime);
			jsonObj.put("traceId", ((NetworkReportObject) data).traceId);
			jsonObj.put("httpUrl", ((NetworkReportObject) data).httpUrl);
			jsonObj.put("sessionId", ((NetworkReportObject) data).sessionId);
			jsonObj.put("userId", ((NetworkReportObject) data).userId);
			jsonObj.put("deviceId", ((NetworkReportObject) data).deviceId);
			jsonObj.put("channelId", ((NetworkReportObject) data).channelId);
			jsonObj.put("subChannelId",
					((NetworkReportObject) data).subChannelId);
			jsonObj.put("clientType", ((NetworkReportObject) data).clientType);
			jsonObj.put("systemVersion",
					((NetworkReportObject) data).systemVersion);
			jsonObj.put("appVersion", ((NetworkReportObject) data).appVersion);
			jsonObj.put("network", ((NetworkReportObject) data).network);
			jsonObj.put("elapsedTime", ((NetworkReportObject) data).elapsedTime);
			jsonObj.put("requestBody", ((NetworkReportObject) data).requestBody);
			jsonObj.put("clientResponseCode",
					((NetworkReportObject) data).clientResponseCode);
			jsonObj.put("exceptionMsg",
					((NetworkReportObject) data).exceptionMsg.replaceAll("\n",
							"#"));
			jsonObj.put("clientIp", ((NetworkReportObject) data).clientIp);
			bf.append(jsonObj);
			bf.append("\n");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bf.toString();
	}

	/**
	 * 读取日志文件 ，并返回 如果没有日志文件，返回空
	 * 
	 * @return
	 */
	public List<ReportObject> getReportLogList() {

		File file = new File(logPath, fileName);

		List<ReportObject> list = new ArrayList<ReportObject>();
		list.clear();
		if (file.exists()) {
			BufferedReader reader = null;
			String tempString = null;
			try {
				reader = new BufferedReader(new FileReader(file));

				try {
					while ((tempString = reader.readLine()) != null) {
						NetworkReportObject NWRObj = new NetworkReportObject();
						String strtemp = tempString;
						String substring;

						strtemp = strtemp.substring(SEPARATOR_SIZE);

						int pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.logTime = Integer.parseInt(substring);

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.traceId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.httpUrl = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.sessionId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.userId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.deviceId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.channelId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.subChannelId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.clientType = Integer.parseInt(substring);

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.systemVersion = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.appVersion = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.network = Integer.parseInt(substring);

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.elapsedTime = Integer.parseInt(substring);

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.requestBody = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.clientResponseCode = Integer.parseInt(substring);

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.exceptionMsg = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						NWRObj.clientIp = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);

						list.add(NWRObj);
					}
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	/**
	 * @return
	 */
	public String getReportLogJson() {
		StringBuffer bf = new StringBuffer();
		List list = getReportLogList();
		if (list != null && list.size() > 0) {
			bf.append("[");
			for (int i = 0; i < list.size(); i++) {
				NetworkReportObject c = (NetworkReportObject) (list.get(i));
				// bf.append("{");
				// bf.append("\"logTime\":\""+c.logTime+"\",");
				// bf.append("\"traceId\":\""+c.traceId+"\",");
				// bf.append("\"httpUrl\":\""+c.httpUrl+"\",");
				// bf.append("\"sessionId\":\""+c.sessionId+"\",");
				// bf.append("\"userId\":\""+c.userId+"\",");
				// bf.append("\"deviceId\":\""+c.deviceId+"\",");
				// bf.append("\"channelId\":\""+c.channelId+"\",");
				// bf.append("\"subChannelId\":\""+c.subChannelId+"\",");
				// bf.append("\"clientType\":\""+c.clientType+"\",");
				// bf.append("\"systemVersion\":\""+c.systemVersion+"\",");
				// bf.append("\"appVersion\":\""+c.appVersion+"\",");
				// bf.append("\"network\":\""+c.network+"\",");
				// bf.append("\"elapsedTime\":\""+c.elapsedTime+"\",");
				// bf.append("\"requestBody\":\""+c.requestBody+"\",");
				// bf.append("\"clientResponseCode\":\""+c.clientResponseCode+"\",");
				// //bf.append("exceptionMsg:"+c.exceptionMsg+",");
				// bf.append("\"clientIp\":\""+c.clientIp+"\"");
				// bf.append("}");

				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("logtime", c.logTime);
					jsonObj.put("traceId", c.traceId);
					jsonObj.put("httpUrl", c.httpUrl);
					jsonObj.put("sessionId", c.sessionId);
					jsonObj.put("userId", c.userId);
					jsonObj.put("deviceId", c.deviceId);
					jsonObj.put("channelId", c.channelId);
					jsonObj.put("subChannelId", c.subChannelId);
					jsonObj.put("clientType", c.clientType);
					jsonObj.put("systemVersion", c.systemVersion);
					jsonObj.put("appVersion", c.appVersion);
					jsonObj.put("network", c.network);
					jsonObj.put("elapsedTime", c.elapsedTime);
					jsonObj.put("requestBody", c.requestBody);
					jsonObj.put("clientResponseCode", c.clientResponseCode);
					jsonObj.put("exceptionMsg", c.exceptionMsg);
					jsonObj.put("clientIp", c.clientIp);
					bf.append(jsonObj);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (i < list.size() - 1) {
					bf.append(",");
				}
			}
			bf.append("]");
		}
		return bf.toString();
	}

	public String getReportLogJsonFromJsonLog() {
		StringBuffer bf = new StringBuffer();
		bf.append("[");
		File file = new File(logPath, fileName);
		if (file.exists()) {
			BufferedReader reader = null;
			String tempString = null;
			try {
				reader = new BufferedReader(new FileReader(file));

				try {
					if ((tempString = reader.readLine()) != null) {
						bf.append(tempString);
					}
					while ((tempString = reader.readLine()) != null) {
						bf.append(",");
						bf.append(tempString);
					}
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		bf.append("]");
		return bf.toString();
	}
}
