package com.longluo.demo.log;

public class ReportObject {
	public long logTime;// 日志记录时间（时间戳，单位为秒 ）
	public String traceId;// 跟踪ID（用于跟踪用户的一次请求唯一标示）
	public String userId;// 用户id(可用于分析用户行为使用)
	public String deviceId;// 客户端设备id
	public String channelId;// 来源渠道号
	public String subChannelId;// 子渠道号
	public int clientType;// 客户端类型（传入对应的数值）WEB(1,"web"), WAP(2,"wap"),
							// WAP_SIMPLE(3,"wap_simple"),
							// WAP_COLOR(4,"wap_color"),
							// WAP_HTML5(5,"wap_html5"),
							// MOBILE(6,"mobile"),ANDROID(7,"android"),IOS(8,"ios"),MATCH_MOBILE(9,"match_mobile"),MATCH_ANDROID(10,"match_android"),MATCH_IOS(11,"match_ios"),PAD_ANDROID(12,"pad_android"),PAD_IOS(13,"pad_ios"),IOS_PAY(51,"ios_pay"),MATCHHL_MOBILE(52,"matchhl_mobile"),MATCHHL_ANDROID(53,"matchhl_android"),MATCHHL_IOS(54,"matchhl_ios"),CITY_MOBILE(61,"city_mobile"),CITY_ANDROID(62,"city_android"),CITY_IOS(63,"city_ios"),UNKNOW(0,"unknow");
	public String systemVersion;// 客户端系统版本号
	public String appVersion;// app版本号
	public String clientIp;// 客户端ip
}
