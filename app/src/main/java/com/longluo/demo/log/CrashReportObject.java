package com.longluo.demo.log;

public class CrashReportObject extends ReportObject {

	public int clientResponseCode;// 接口响应结果:-1是域名解释错误，
									// -2网络连接超时，-3是crash，-4是其他，-5是找不到网络，4XX
									// 5XX（http错误返回码）是http error
	public String exceptionMsg;// 异常的说明,堆栈信息

}
