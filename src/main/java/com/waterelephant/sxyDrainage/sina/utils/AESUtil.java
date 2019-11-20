//package com.waterelephant.sxyDrainage.sina.utils;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.commons.codec.binary.Base64;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.sina.entity.RequestCheckUser;
//
///**
// * 
// * @ClassName: AESUtil
// * @Description: TODO(AES-128-ECB 模式加密)
// * @author YANHUI
// * @date 
// *
// */
//public class AESUtil {
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
//		/*
//		 * 此处使用AES-128-ECB加密模式，key需要为16位。
//		 */
//		String cKey = "AmCILBEADCgkHhDK";
//		// 需要加密的字串
//		RequestCheckUser requestCheckUser = new RequestCheckUser();
//		requestCheckUser.setName("丁锋");
//		requestCheckUser.setPhone("13888882222");
//		requestCheckUser.setBankCardNo("6217211107001880725");
//		//requestCheckUser.setOrderNo("201806281000076177615");
//		requestCheckUser.setIdCard("445121198205080682");
//		requestCheckUser.setVerifyCode("123456");
//		requestCheckUser.setBankCode("ICBC");
//		requestCheckUser.setBankPhone("13888882222");
//		/*requestCheckUser.setLoanAmount(new Double(2000));
//		requestCheckUser.setSesameScore(600);
//		requestCheckUser.setDesc("生活费");
//		requestCheckUser.setHouseAddress("武汉");
//		requestCheckUser.setMarriage(0);
//		requestCheckUser.setEmail("1337866585@qq.com");
//		requestCheckUser.setHaveCar(0);
//		requestCheckUser.setHaveHouse(0);
//		requestCheckUser.setFirstName("王五");
//		requestCheckUser.setFirstPhone("110");
//		requestCheckUser.setSecondName("赵柳");
//		requestCheckUser.setSecondPhone("112");
//		requestCheckUser.setColleagueName("同事");
//		requestCheckUser.setColleaguePhone("150");
//		requestCheckUser.setFriend1Name("朋友1");
//		requestCheckUser.setFriend1Phone("1323");
//		requestCheckUser.setFriend2Name("朋友2");
//		requestCheckUser.setFriend2Phone("12345");
//		requestCheckUser.setQqchat("1337866585");
//		requestCheckUser.setWechat("CDK1337866585");
//		requestCheckUser.setOrderId("151005222270215");*/
//		
//		
//		String cSrc = JSON.toJSONString(requestCheckUser);
//		System.out.println(cSrc);
//		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("orderNo", "152905113836600011");
//		
//
//		String str = "";
//		// 加密
//		String enString = AESUtil.Encrypt(str, cKey);
//		System.out.println("加密后的字串是：" + enString);
//
//		// 解密
////		String DeString = AESUtil.Decrypt(enString, cKey);
////		System.out.println("解密后的字串是：" + DeString);
//
//	}
//}
