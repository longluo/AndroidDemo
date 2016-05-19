package com.longluo.demo.log;

public class NetworkReportObject extends ReportObject {

	public String httpUrl;// 调用服务接口的名称（可以使地址，http请求路径（别带入请求具体参数））
	public String sessionId;// sessionId
	// public int clientType;//客户端类型（传入对应的数值）WEB(1,"web"), WAP(2,"wap"),
	// WAP_SIMPLE(3,"wap_simple"), WAP_COLOR(4,"wap_color"),
	// WAP_HTML5(5,"wap_html5"),
	// MOBILE(6,"mobile"),ANDROID(7,"android"),IOS(8,"ios"),MATCH_MOBILE(9,"match_mobile"),MATCH_ANDROID(10,"match_android"),MATCH_IOS(11,"match_ios"),PAD_ANDROID(12,"pad_android"),PAD_IOS(13,"pad_ios"),IOS_PAY(51,"ios_pay"),MATCHHL_MOBILE(52,"matchhl_mobile"),MATCHHL_ANDROID(53,"matchhl_android"),MATCHHL_IOS(54,"matchhl_ios"),CITY_MOBILE(61,"city_mobile"),CITY_ANDROID(62,"city_android"),CITY_IOS(63,"city_ios"),UNKNOW(0,"unknow");
	public int network;// 客户端网络（传入对应的数值）NETWORK_2G(0,"2g"),
						// NETWORK_3G(1,"3g"),NETWORK_4G(2,"4g"),
						// NETWORK_WIFI(3,"wifi"), UNKNOW(-1,"unknow");
	public long elapsedTime;// 耗时、毫秒数
	public String requestBody;// 用户http请求的请求内容（GET、POST格式一致）（必须json格式）（可以用于记录调用方法的参数）
	public int clientResponseCode;// 接口响应结果:-1是域名解释错误，
									// -2网络连接超时，-3是crash，-4是其他，-5是找不到网络，4XX
									// 5XX（http错误返回码）是http error
	public String exceptionMsg;// 异常的说明,堆栈信息
	// public String clientIp;//客户端ip

}
