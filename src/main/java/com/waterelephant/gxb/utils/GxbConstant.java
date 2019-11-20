package com.waterelephant.gxb.utils;

import java.util.HashSet;
import java.util.Set;

public class GxbConstant {
	
	public static Set<String> AUTH_ITEMS = new HashSet<>();
	
	static {
		AUTH_ITEMS.add(GxbAuthItem.SHEBAO);
		AUTH_ITEMS.add(GxbAuthItem.HOUSEFUND);
		AUTH_ITEMS.add(GxbAuthItem.OPERATOR_PRO);
		AUTH_ITEMS.add(GxbAuthItem.CHSI);
		AUTH_ITEMS.add(GxbAuthItem.JD);
		AUTH_ITEMS.add(GxbAuthItem.ECOMMERCE);
		AUTH_ITEMS.add(GxbAuthItem.WECHAT);
		AUTH_ITEMS.add(GxbAuthItem.KYC);
		AUTH_ITEMS.add(GxbAuthItem.MAIL);
		AUTH_ITEMS.add(GxbAuthItem.WECHAT_PHONE);
		AUTH_ITEMS.add(GxbAuthItem.DIDITAXI);
		AUTH_ITEMS.add(GxbAuthItem.DIDIZM);
	}

	public interface GxbAuthItem{
		
		String SHEBAO = "shebao";//	社保
		String HOUSEFUND = "housefund";//	公积金
		String OPERATOR_PRO = "operator_pro";//	运营商
		String CHSI = "chsi";//	学信
		String JD = "jd";//	京东
		@Deprecated
		String CREDIT = "credit";//	人行征信（已下线）
		@Deprecated
		String SESAME = "sesame";//	芝麻信用分（已下线）
		String ECOMMERCE = "ecommerce";//	电商（支付宝、淘宝）
		String WECHAT = "wechat";//	微信、微粒贷
		String KYC = "kyc";//	身份验证
		String MAIL = "mail";//	邮箱账单
		String WECHAT_PHONE = "wechat_phone";//	微信(基本信息)
		String DIDITAXI = "diditaxi";//	滴滴
		String DIDIZM = "didizm";//滴滴芝麻
	}
	
}
