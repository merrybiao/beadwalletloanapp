package com.waterelephant.constants;

import java.util.ResourceBundle;

public class KJTConstant {
	private static ResourceBundle configBundle = ResourceBundle.getBundle("kuaijietong");
	public static String SERVICE_TRADE_BANK_WITHOLDING;//单笔代扣服务
	public static String SERVICE_TRADE_QUERY;//交易查询服务
	public static String VERSION;//调用的接口版本
	public static String PARTNER_ID;//签约后的平台（商户）ID
	public static String CHARSET;//编码集
	public static String SIGN_TYPE;//签名方式
	public static String FORMAT;//业务请求参数集合支持的格式
	public static String BIZ_PRODUCT_CODE;//业务产品码：银行卡代扣20204
	public static String PAY_PRODUCT_CODE;//支付产品码：61，银行卡代扣-借记卡
	
	
	public static String PAYEE_IDENTITY;//商户的入款账号，这里是ID
	
	
	public static String KJTPUBLICKEY;//公钥
	public static String WEPRIVATEKEY;//私钥
	
	static {
		SERVICE_TRADE_BANK_WITHOLDING = configBundle.getString("trade_bank_witholding");
		SERVICE_TRADE_QUERY = configBundle.getString("trade_query");
		VERSION = configBundle.getString("version");
		PARTNER_ID = configBundle.getString("partner_id");
		CHARSET = configBundle.getString("charset");
		SIGN_TYPE = configBundle.getString("sign_type");
		FORMAT = configBundle.getString("format");
		BIZ_PRODUCT_CODE = configBundle.getString("biz_product_code");
		PAY_PRODUCT_CODE = configBundle.getString("pay_product_code");
		
		KJTPUBLICKEY = configBundle.getString("KJT_publicKey");
		WEPRIVATEKEY = configBundle.getString("WE_privateKey");
		
		
		PAYEE_IDENTITY = configBundle.getString("payee_identity");
	}
	
}
