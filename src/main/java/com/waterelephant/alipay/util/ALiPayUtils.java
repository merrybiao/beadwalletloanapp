package com.waterelephant.alipay.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ALiPayUtils {

	public static Map convertData(String data) {
		data = data.replace("\"", "");
		String[] paramArray = data.split("&");

		Map<String, String> map = new HashMap<String, String>();

		for (String param : paramArray) {
			map.put(param.split("=")[0], param.split("=")[1]);
		}

		return map;
	}

	@Test
	public void test01() {
		String data = "method=mbupay.alipay.jswap&version=2.0.1&appid=a20160923000000641&out_trade_no=B201711101510311452&body=test&mch_id=m20160923000000641&total_fee=10000";
		@SuppressWarnings("rawtypes")
		Map dataMap = convertData(data);
		String method = (String) dataMap.get("method");
		String appid = (String) dataMap.get("appid");
		String mch_id = (String) dataMap.get("mch_id");
		String body = (String) dataMap.get("body");
		String out_trade_no = (String) dataMap.get("out_trade_no");
		String total_fee = (String) dataMap.get("total_fee");
		System.out.println(method);
		System.out.println(appid);
		System.out.println(mch_id);
		System.out.println(body);
		System.out.println(out_trade_no);
		System.out.println(total_fee);
	}
}
