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

import android.util.Log;

public class CrashReport extends LogReport {

	public static String CRASH_REPORT_FILENAME = "crash_report_321.log"; // Crashlog文件

	private static CrashReport instance = null;

	public final static String SEPARATOR = "$|$";// 分隔符

	public final static int SEPARATOR_SIZE = 3;// 分隔符的长度

	public static CrashReport getInstance() {
		if (instance == null) {
			instance = new CrashReport();
		}
		return instance;
	}

	public CrashReport() {
		super();
		Log.e("CrashReport", "CrashReportCrashReport");
		fileName = CRASH_REPORT_FILENAME;
		type = CRASH_TYPE;
	}

	@Override
	public String format(ReportObject data) {
		String str;
		// str = "123145678989";
		// StringBuffer bf = new StringBuffer();
		str = SEPARATOR + ((CrashReportObject) data).logTime + SEPARATOR
				+ ((CrashReportObject) data).traceId + SEPARATOR
				+ ((CrashReportObject) data).userId + SEPARATOR
				+ ((CrashReportObject) data).deviceId + SEPARATOR
				+ ((CrashReportObject) data).channelId + SEPARATOR
				+ ((CrashReportObject) data).subChannelId + SEPARATOR
				+ ((CrashReportObject) data).clientType + SEPARATOR
				+ ((CrashReportObject) data).systemVersion + SEPARATOR
				+ ((CrashReportObject) data).appVersion + SEPARATOR
				+ ((CrashReportObject) data).clientResponseCode + SEPARATOR
				+ ((CrashReportObject) data).exceptionMsg.replaceAll("\n", "#")
				+ SEPARATOR + ((CrashReportObject) data).clientIp + SEPARATOR
				+ "\n";
		// bf.append("{");
		// bf.append("logTime:"+((CrashReportObject)data).logTime+",");
		// bf.append("traceId:"+((CrashReportObject)data).traceId+",");
		// bf.append("userId:"+((CrashReportObject)data).userId+",");
		// bf.append("deviceId:"+((CrashReportObject)data).deviceId+",");
		// bf.append("channelId:"+((CrashReportObject)data).channelId+",");
		// bf.append("subChannelId:"+((CrashReportObject)data).subChannelId+",");
		// bf.append("clientType:"+((CrashReportObject)data).clientType+",");
		// bf.append("systemVersion:"+((CrashReportObject)data).systemVersion+",");
		// bf.append("appVersion:"+((CrashReportObject)data).appVersion+",");
		// bf.append("exceptionMsg:"+((CrashReportObject)data).exceptionMsg+",");
		// bf.append("clientIp:"+((CrashReportObject)data).clientIp);
		// bf.append("},");
		return str;
	}

	public String formatToJSON(ReportObject data) {
		StringBuffer bf = new StringBuffer();
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("logtime", ((CrashReportObject) data).logTime);
			jsonObj.put("traceId", ((CrashReportObject) data).traceId);
			jsonObj.put("userId", ((CrashReportObject) data).userId);
			jsonObj.put("deviceId", ((CrashReportObject) data).deviceId);
			jsonObj.put("channelId", ((CrashReportObject) data).channelId);
			jsonObj.put("subChannelId", ((CrashReportObject) data).subChannelId);
			jsonObj.put("clientType", ((CrashReportObject) data).clientType);
			jsonObj.put("systemVersion",
					((CrashReportObject) data).systemVersion);
			jsonObj.put("appVersion", ((CrashReportObject) data).appVersion);
			jsonObj.put("clientResponseCode",
					((CrashReportObject) data).clientResponseCode);
			jsonObj.put("exceptionMsg", ((CrashReportObject) data).exceptionMsg
					.replaceAll("\n", "#"));
			jsonObj.put("clientIp", ((CrashReportObject) data).clientIp);
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
						CrashReportObject CRObj = new CrashReportObject();
						String strtemp = tempString;
						String substring;

						strtemp = strtemp.substring(SEPARATOR_SIZE);

						int pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.logTime = Integer.parseInt(substring);

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.traceId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.userId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.deviceId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.channelId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.subChannelId = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.clientType = Integer.parseInt(substring);

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.systemVersion = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.appVersion = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.clientResponseCode = Integer.parseInt(substring);

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.exceptionMsg = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);
						pos_end = strtemp.indexOf(SEPARATOR);
						substring = strtemp.substring(0, pos_end);
						CRObj.clientIp = substring;

						strtemp = strtemp.substring(pos_end + SEPARATOR_SIZE);

						list.add(CRObj);
					}
					reader.close();
				} catch (IOException e) {
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
				CrashReportObject c = (CrashReportObject) (list.get(i));
				// bf.append("{");
				// bf.append("\"logTime\":\""+c.logTime+"\",");
				// bf.append("\"traceId\":\""+c.traceId+"\",");
				// bf.append("\"userId\":\""+c.userId+"\",");
				// bf.append("\"deviceId\":\""+c.deviceId+"\",");
				// bf.append("\"channelId\":\""+c.channelId+"\",");
				// bf.append("\"subChannelId\":\""+c.subChannelId+"\",");
				// bf.append("\"clientType\":\""+c.clientType+"\",");
				// bf.append("\"systemVersion\":\""+c.systemVersion+"\",");
				// bf.append("\"appVersion\":\""+c.appVersion+"\",");
				// bf.append("\"clientResponseCode\":\""+c.clientResponseCode+"\",");
				// bf.append("\"exceptionMsg\":\""+c.exceptionMsg+"\",");
				// bf.append("\"clientIp\":\""+c.clientIp+"\"");
				// bf.append("}");

				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("logtime", c.logTime);
					jsonObj.put("traceId", c.traceId);
					jsonObj.put("userId", c.userId);
					jsonObj.put("deviceId", c.deviceId);
					jsonObj.put("channelId", c.channelId);
					jsonObj.put("subChannelId", c.subChannelId);
					jsonObj.put("clientType", c.clientType);
					jsonObj.put("systemVersion", c.systemVersion);
					jsonObj.put("appVersion", c.appVersion);
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
