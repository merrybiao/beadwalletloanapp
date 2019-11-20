///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.sxy2saas;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.utils.ElasticSearchUtils;
//import com.waterelephant.utils.SystemConstant;
//import org.apache.commons.codec.binary.Base64;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.utils.AESUtil;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.sort.SortOrder;
//
///**
// * 
// * 
// * Module: (code:s2s)
// * 
// * Sxy2SaasUtil.java
// * 
// * @author zhangchong
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class Sxy2SaasUtil {
//	/*
//	 * 此处使用AES-128-ECB加密模式，key需要为16位。
//	 */
//	public static final String cKey = "J2xFzm8TqfLoHOl1";
//
//	// 加密
//	public static String Encrypt(String sSrc, String sKey) throws Exception {
//		if (sKey == null) {
//			System.out.print("Key为空null");
//			return null;
//		}
//		// 判断Key是否为16位
//		if (sKey.length() != 16) {
//			System.out.print("Key长度不是16位");
//			return null;
//		}
//		byte[] raw = sKey.getBytes("utf-8");
//		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
//		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
//
//		return new Base64().encodeToString(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
//	}
//
//	// 解密
//	public static String Decrypt(String sSrc, String sKey) throws Exception {
//		try {
//			// 判断Key是否正确
//			if (sKey == null) {
//				System.out.print("Key为空null");
//				return null;
//			}
//			// 判断Key是否为16位
//			if (sKey.length() != 16) {
//				System.out.print("Key长度不是16位");
//				return null;
//			}
//			byte[] raw = sKey.getBytes("utf-8");
//			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//			byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
//			try {
//				byte[] original = cipher.doFinal(encrypted1);
//				String originalString = new String(original, "utf-8");
//				return originalString;
//			} catch (Exception e) {
//				System.out.println(e.toString());
//				return null;
//			}
//		} catch (Exception ex) {
//			System.out.println(ex.toString());
//			return null;
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//
//		// 需要加密的字串
//		// RequestParams requestParams = new RequestParams();
//		// requestParams.setPhone("15972182935");
//		// requestParams.setChannelId(0);
//		// requestParams.setMerchantId(0L);
//		// String cSrc = JSON.toJSONString(requestParams);
//		//
//		// System.out.println(cSrc);
//
//		// Map<String, Object> map = new HashMap<>();
//		// map.put("channelId", 793);
//		// map.put("merchantId", 106);
//		// map.put("phone", "18566789940");
//		// String cSrc = JSON.toJSONString(map);
//        //
//		// // AES加密生成sign
//		// String enString = AESUtil.Encrypt(cSrc, cKey);
//		// System.out.println("加密后的字串是：" + enString);
//        //
//		// // 验签
//		// String DeString = AESUtil.Decrypt(enString, cKey);
//		// System.out.println("解密后的字串是：" + DeString);
//		// boolean flag = cSrc.equals(DeString);
//		// System.out.println(flag);
//		// // 106.14.238.126:8092
//		// String url = "http://localhost:8080/beadwalletloanapp/sxyDrainage/getBasicInfo.do";
//		// // String url = "http://106.14.45.32:8092/beadwalletloanapp/sxyDrainage/getOperatorInfo.do";
//		// Map<String, String> params = new HashMap<>();
//		// params.put("sign", enString);
//		// params.put("request", cSrc);
//		// String json = HttpClientHelper.post(url, "utf-8", params);
//		// System.out.println(json);
//
//		List<BwOperateVoice> bwOperateVoices = new ArrayList<>();
//		Client client = ElasticSearchUtils.getInstance().getClient();
//		SearchResponse searchResponse = client.prepareSearch("we-loan_alias").setTypes("BwOperateVoice")
//		.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("borrower_id", 2436226)))
//		.addSort("call_time", SortOrder.DESC).execute().actionGet();
//		System.out.println(searchResponse);
//		SearchHits searchHits = searchResponse.getHits();
//		long totalHits = searchHits.getTotalHits();
//		System.out.println(totalHits);
//
//		for (SearchHit searchHit : searchHits) {
//		BwOperateVoice bwOper = new BwOperateVoice();
//		Map<String, Object> map1 = searchHit.getSource();
//
//		bwOper.setTrade_time(map1.get("trade_time") == null ? "" : map1.get("trade_time").toString());
//		bwOper.setCall_time(map1.get("call_time") == null ? "" : map1.get("call_time").toString());
//		bwOper.setTrade_addr(map1.get("trade_addr") == null ? "" : map1.get("trade_addr").toString());
//			bwOper.setCall_type(map1.get("call_type") == null ? null : Integer.valueOf(map1.get("call_type")
//				.toString()));
//			bwOper.setReceive_phone(map1.get("receive_phone") == null ? "" : map1.get("receive_phone").toString());
//			bwOper.setTrade_type(map1.get("trade_type") == null ? null : Integer.valueOf(map1.get("trade_type")
//				.toString()));
//
//		bwOperateVoices.add(bwOper);
//		}
//		System.out.println(bwOperateVoices);
//
//		SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//		String string = sdf_hms.format(1524207309000L);
//		Date jsonCallData = sdf_hms.parse(string);
//		System.out.println(jsonCallData);
//	}
//
//}
