//package com.waterelephant.sxyDrainage.utils.haodai;
//
//import org.apache.commons.codec.digest.HmacUtils;
//import org.junit.Test;
//
///**
// * Created by youfu on 2018/4/17. 全量API接口签名/解密
// */
//
//public class Sign {
//	private static String appID = "IbTgkSaRIseCZcKq";
//	private static String appSecret = "1vZwJHfowizwICk1";
//	private static String version = "1.0.0";
//
//	public static String SignData(String data) {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(appID + "\n");
//		buffer.append(version + "\n");
//		buffer.append("1524131261766" + "\n");
//		// buffer.append(System.currentTimeMillis()+"\n");
//		buffer.append(data);
//		byte[] valueToDigest = buffer.toString().getBytes();
//		String hex = new HmacUtils().hmacSha1Hex(appSecret.getBytes(), valueToDigest);
//		return hex.toLowerCase();
//	}
//
//	public static void main(String[] args) {
//
//		String signData = SignData("8888");
//
//		System.out.println(signData);
//	}
//
//}
