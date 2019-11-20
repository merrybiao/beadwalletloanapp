//package com.waterelephant.sxyDrainage.sina.utils;
//
//import java.util.ResourceBundle;
//
//
///**
// * 
// * <p>Title: SinaConstant</p>  
// * <p>Description: 新浪常量</p>
// * @since JDK 1.8  
// * @author YANHUI
// */
//public class SinaConstant {
//	public static String APPID = "";
//	public static String CHANNELID = "";
//    public static String PRODUCTID = "";
//    public static String AESKEY = "";
//    public static String BQS_URL = "";
//
//	static {
//		ResourceBundle bundle = ResourceBundle.getBundle("sina");
//		if (bundle == null) {
//			throw new IllegalArgumentException("[sina.properties] is not found!");
//		}
//		SinaConstant.APPID = bundle.getString("APPId");
//		SinaConstant.CHANNELID = bundle.getString("CHANNELID");
//		SinaConstant.PRODUCTID = bundle.getString("PRODUCTID");
//		SinaConstant.AESKEY = bundle.getString("AESKEY");
//		SinaConstant.BQS_URL = bundle.getString("BQS_URL");
//	}
//	
//	
//	public static void main(String[] args) {
////		System.out.println(XinYongGuanJiaConstant.APPID);
////		System.out.println(XinYongGuanJiaConstant.CHANNELID);
////		System.out.println(XinYongGuanJiaConstant.PRODUCTID);
////		
////		String AESKey = XinYongGuanJiaConstant.xygjConfig.getString("RESKEY.CHANNEL_" + 777);
////		System.out.println(AESKey);
//	}
//}
