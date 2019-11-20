/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.util.qihu360;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;

import com.waterelephant.drainage.test.TestQiHu360;

/**
 * 
 * 
 * Module:
 * 
 * Demo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Demo {

	// private String appId = "26";
	private String encoding = "UTF-8";
	// private String notifyUrl = "http://wlj.xdapi-pt.360.cn/openapi/proxy/hdo";
	// private String daiPublicKey =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCCUBWiOBM9/fQrVfmxmqqXuSnCb+rRCrJb5/n1JhJtKJy7WwcTF92qr+vvzVLTzjPPSO2bHX2jTGgm9Zbk+tybVLGaEpm+3P9mp/IXOg65h2HhpUa6och4tjHfMgNKXOMVlif9fef7M4DNw5h/Bs0VlcSHwpTIr2kkPkEo63gqwIDAQAB";
	// private String myPrivateKey =
	// "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIIJQFaI4Ez399CtV+bGaqpe5KcJv6tEKslvn+fUmEm0onLtbBxMX3aqv6+/NUtPOM89I7ZsdfaNMaCb1luT63JtUsZoSmb7c/2an8hc6DrmHYeGlRrqhyHi2Md8yA0pc4xWWJ/195/szgM3DmH8GzRWVxIfClMivaSQ+QSjreCrAgMBAAECgYBlVtRfG/jBwxXL+OtiHAuQjrdfPgezb0JxDZ7uyw2Lk0xuiH0SaKqVDmiojDj6YdkCGMG2LzyXUl30rr4VTGfV/SeW3TWKtY7vrq7Qdhk2OnobBMQU0uAgtIw5Z3r64xNcSO6ApvSfP5jFwNz+hdXyQg+3qUXPxLZDnSaD/6YkgQJBAMuiO9tzBaRqxVmvjylaPp0vr0ZVsKnV8zpGIgTEUlhYEXSON19br/KgCoWLdkqZmbmUKxZBx2g/nahGkKLMuqECQQCjeeL5SULKD5RFozAqIhF6hPqr22amKDUPlOmEiyGRxS5Kw9rAgYBXaK3e/+a8rnGfFlGzrTQM0yKCg9UpZgPLAkEAqU5MyCt9AHPJe8TsmawvrQ8NgsQSK1L4dHYFKMN113/5m1f3D/CyfF62AMywYFwPJO3dWRb9zV4/Hb712HWxoQJAHmeEBHNQvb4KCK2iPqPZdl0XNjF3NP4TaFN83w71bmQrCPLGU7mCQj+Q3wHfS0ZykXEYPv++x4fZSkb0tV9o4wJALLaYQWMWRI1UlTSUWEJvyTGGq+TLPX+41ePEf9GzhPMqea8C9XMjgLKi/epfoIf12sUatbsp+zCi7jAS0cJS/g==";

	/**
	 * 对des使用360贷款导航ras公钥加密
	 * 
	 * @param desKey
	 * @return
	 * @throws Exception
	 */
	public String encryptDesKey(String deskey) throws Exception {
		System.out.println("des-key===="
				+ Base64Utils.encode(RSAUtils.encryptByPublicKey(deskey.getBytes(), QiHu360Constant.get("publicKey"))));
		return Base64Utils.encode(RSAUtils.encryptByPublicKey(deskey.getBytes(), QiHu360Constant.get("publicKey")));
	}

	public String encryptBizData(String plainData, String deskey) throws Exception {
		System.out.println("data====" + DES.encrypt(plainData, deskey));
		return DES.encrypt(plainData, deskey);
	}

	String signStr = null;

	public String makeSign(TreeMap<String, String> data) throws UnsupportedEncodingException, Exception {

		StringBuffer strbuf = new StringBuffer();
		for (String key : data.keySet()) {
			if ("sign".equals(key))
				continue;
			strbuf.append("&");
			strbuf.append(key);
			strbuf.append("=");
			strbuf.append(data.get(key));
		}
		signStr = strbuf.toString();
		// System.out.println(signStr);
		if (!"".equals(signStr)) {
			signStr = signStr.substring(1);
		}
		System.out.println("signStr ==== " + signStr);

		/*
		 * 如果签名报以下错误：
		 * 
		 * java.security.spec.InvalidKeySpecException: java.security.InvalidKeyException: IOException : algid parse
		 * error, not a sequence
		 * 
		 * 则说明rsa私钥的格式不是pksc8格式，需要使用以下命令转换一下：
		 * 
		 * openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt
		 */
		return RSAUtils.sign(signStr.getBytes(), QiHu360Constant.get("qiHu360PrivateKey"));
	}

	public String testNotify() throws Exception {

		String EncFlag = "1"; // 是否加密
		// 通知的业务数据
		Map<String, Object> bizData = new HashMap<String, Object>();
		bizData.put("id_card", "421022199307063953");
		bizData.put("user_mobile", "15972182935");
		bizData.put("user_name", "张冲");
		bizData.put("product_id", "1");

		// 通知的扩展数据
		// Map<String, String> regist = new HashMap<String, String>();
		// regist.put("mobile", "18612345678");
		//
		// bizData.put("regist", regist);

		JSONObject bizJson = new JSONObject(bizData);
		String bizDataStr = bizJson.toString();
		bizDataStr = TestQiHu360.test3();
		// bizDataStr = "{\"order_no\":\"6323404357914963968\",\"family_live_type\":1,\"addr_detail\":\"北京 北京市
		// 东城区阿肯阿狸额\",\"user_marriage\":3,\"user_email\":\"fgffffgf@sina.com\",\"asset_auto_type\":2,\"emergency_contact_personA_relationship\":7,\"emergency_contact_personA_name\":\"第二个hh\",\"emergency_contact_personA_phone\":\"13233333333\",\"contact1A_number\":\"13810988214\",\"contact1A_name\":\"刘一一\",\"contact1A_relationship\":4,\"company_name\":\"360\",\"company_addr_detail\":\"北京市东城区\",\"company_number\":\"111-85635-222\",\"position\":1,\"ID_Positive\":[\"http://demo.t.360.cn/xdpt/resource/pic/fid/ui0_id_59b0de40fed297a837f82b80?t=1507600085&s=750da87021ae8500efea277c84e475f6\"],\"ID_Negative\":[\"http://demo.t.360.cn/xdpt/resource/pic/fid/ui0_id_59b0ded0fed297a534f82b85?t=1507600085&s=68419999dfac21964b9e51152200501f\"],\"photo_assay\":[\"http://demo.t.360.cn/xdpt/resource/pic/fid/ui0_id_59cc73fecd0ce8d74f7b23f1?a=1&t=1507600085&s=8e6eaf4a16f84c4cc7df5dbf3580280c\",\"http://demo.t.360.cn/xdpt/resource/pic/fid/ui0_id_59cc73fecd0ce8950e7b23f2?a=2&t=1507600085&s=5a6c7362df041ba1da2d75cc7710e872\",\"http://demo.t.360.cn/xdpt/resource/pic/fid/ui0_id_59cc73fecd0ce87b157b23f2?a=3&t=1507600085&s=0190ce38a0447ae7e841610c4286b5d7\",\"http://demo.t.360.cn/xdpt/resource/pic/fid/ui0_id_59cc73fecd0ce8e44f7b23f7?a=4&t=1507600085&s=9c59efc7e693bc5d3e4aefc6404485d6\",\"http://demo.t.360.cn/xdpt/resource/pic/fid/ui0_id_59cc73fecd0ce8de4f7b23fa?a=5&t=1507600085&s=ac94a0872d1b4bc18d55a29f68d8f278\"],\"photo_hand_ID\":[\"http://demo.t.360.cn/xdpt/resource/pic/fid/ui0_id_59a7e135fed297ce54f82b90?t=1507600085&s=2aa29e49727ecb3f4bb49f3446e97800\"],\"contacts\":{\"device_num\":\"868897026999623\",\"platform\":\"Android\",\"device_info\":\"LetvX501\",\"app_location\":{\"lat\":\"39.983063\",\"lon\":\"116.490601\",\"address\":\"北京市朝阳区酒仙桥路靠近电子城·国际电子总部\"},\"call_log\":null},\"device_info_all\":{\"platform\":\"Android\",\"imei\":\"868897026999623\",\"imsi\":\"460011950507758\",\"android_id\":\"c69927d63c4c6021\",\"is_root\":\"460011950507758\",\"android_ver\":\"6.0\",\"device_model\":\"Letv\",\"udid\":\"0fb353185bfbc7d70ee6979fd62e444e\",\"uuid\":null,\"tele_name\":null,\"mac\":\"84:73:03:ec:8e:df\",\"phone_brand\":\"Letv\",\"tele_num\":\"+8618501954487\",\"seria_no\":null,\"dns\":\"172.27.35.1,10.16.0.222\",\"mem_size\":\"2791583744\",\"storage_size\":\"12150579200\",\"ava_storage_size\":\"3395792896\"},\"is_simulator\":0}";
		// bizDataStr = TestQiHu360.test6();
		// bizDataStr = TestQiHu360.test8();
		// bizDataStr = TestQiHu360.test9();
		// bizDataStr = TestQiHu360.test10();
		// bizDataStr = TestQiHu360.test11();
		// bizDataStr = TestQiHu360.test12();
		// bizDataStr = TestQiHu360.test13();
		// bizDataStr = TestQiHu360.test15();
		System.out.println("biz-data====" + bizDataStr);

		// 通知参数，需要对key做字典序排序算签名
		TreeMap<String, String> requestParam = new TreeMap<String, String>();
		// requestParam.put("appId", this.appId);
		requestParam.put("merchant_id", "7UH413BFAI51A3C");
		// requestParam.put("version", "1.0");
		// requestParam.put("format", "json");
		// requestParam.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));

		if ("1".equals(EncFlag)) { // 需要加密
			String deskey = "12345678"; // 随机产生一个des密钥
			requestParam.put("biz_enc", "1");
			requestParam.put("des_key", encryptDesKey(deskey));
			requestParam.put("biz_data", encryptBizData(bizDataStr, deskey));
		} else {
			requestParam.put("biz_enc", "0");
			requestParam.put("biz_data", bizDataStr);
		}

		String sign = makeSign(requestParam);
		System.out.println("sign====" + sign);
		System.out.println(requestParam);

		boolean flag = RSAUtils.verify(signStr.getBytes(), QiHu360Constant.get(QiHu360Utils.QIHU360_PUBLIC_KEY), sign);
		System.out.println(flag);

		requestParam.put("sign", sign);
		JSONObject jsonObject = new JSONObject(requestParam);
		String postData = jsonObject.toString();
		System.out.println("postData ==== " + postData);

		// StringBuffer response = new StringBuffer();
		// try {
		// // 需要请求的restful地址
		// URL url = new URL(this.notifyUrl);
		//
		// // 打开restful链接
		// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//
		// // 提交模式
		// conn.setRequestMethod("POST");// POST GET PUT DELETE
		//
		// // 设置访问提交模式，
		// conn.setRequestProperty("Content-Type", "application/json");
		//
		// conn.setConnectTimeout(10000);// 连接超时 单位毫秒
		// conn.setReadTimeout(2000);// 读取超时 单位毫秒
		//
		// conn.setDoOutput(true);// 是否输入参数
		//
		// OutputStream outputStream = conn.getOutputStream();
		// conn.getOutputStream().write(postData.getBytes(this.encoding));
		// outputStream.flush();
		//
		// if (conn.getResponseCode() != 200) {
		// throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		// }
		// BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		//
		// String output;
		// while ((output = responseBuffer.readLine()) != null) {
		// System.out.println(output);
		// response.append(output);
		// }
		// conn.disconnect();
		// } catch (Exception e) {
		// System.out.println(e);
		// }
		return "";
	}

	public static void main(String[] args) throws Exception {

		Demo client = new Demo();
		String response = client.testNotify();
		System.out.println(response);
	}
}
