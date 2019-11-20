///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.jdq;
//
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.sxyDrainage.entity.jdq.Address_book;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqOrderInfoRequest;
//import com.waterelephant.sxyDrainage.entity.jdq.Loan_info;
//import com.waterelephant.sxyDrainage.entity.jdq.User_contact;
//import com.waterelephant.sxyDrainage.entity.jdq.User_info;
//import com.waterelephant.utils.CommUtils;
//
//import org.apache.log4j.Logger;
//
//import java.text.DecimalFormat;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 
// * 
// * Module:
// * 
// * JdqUtils.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class JdqUtils {
//
//	private static Logger logger = Logger.getLogger(JdqUtils.class);
//	// 机构方公钥
//	public final static String partnerPublicKey = JdqConstant.get("sxPublicKey");
//	// 机构方私钥
//	public final static String partnerPrivateKey = JdqConstant.get("sxPrivateKey");
//
//	// 借点钱公钥
//	public final static String jdqPublicKey = JdqConstant.get("jdqPublicKey");
//	// 借点钱私钥
//	public final static String jdqPrivateKey = JdqConstant.get("jdqPrivateKey");
//
//	//
//	public final static String sx_channel_code = JdqConstant.get("sx_channel_code");
//
//	public static void getRequest() {
//
//	}
//
//	public static String convertOrderStatus(Long oriStatus) {
//		if (oriStatus == null) {
//			return null;
//		}
//
//		return JdqConstant.ORDER_STATUS_MAP.get(String.valueOf(oriStatus));
//	}
//
//	public static String convertApproveStatus(Long oriStatus) {
//		if (oriStatus == null) {
//			return null;
//		}
//
//		return JdqConstant.REPAY_STATUS_MAP.get(String.valueOf(oriStatus));
//	}
//
//	/**
//	 * 借点钱调用机构方接口时，按此方式加密加签
//	 * 
//	 * @param param
//	 * @return
//	 */
//	public static String jdqEncode(String param) {
//		String data = null;
//		try {
//			System.out.println("水象科技公钥："+partnerPublicKey);
//			//水象公钥加密
//			data = RSA.encryptByPublicKey(param, partnerPublicKey, "utf-8");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("加密失败");
//		}
//		//借点钱私钥签名
//		String sign = RSAUtil.buildRSASignByPrivateKey(param, jdqPrivateKey);
//		JSONObject paramJson = new JSONObject();
//		paramJson.put("data", data);
//		paramJson.put("sign", sign);
//		//paramJson.put("channel_code", sx_channel_code);
//		return paramJson.toJSONString();
//	}
//
//	/**
//	 * 机构方推送数据至借点钱时，按此方式加密加签
//	 * 
//	 * @param param
//	 * @return
//	 */
//	public static String partnerEncode(String param) {
//		String data = null;
//		try {
//			data = RSA.encryptByPublicKey(param, jdqPublicKey, "utf-8");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("加密失败");
//		}
//		String sign = RSAUtil.buildRSASignByPrivateKey(param, partnerPrivateKey);
//		JSONObject paramJson = new JSONObject();
//		paramJson.put("data", data);
//		paramJson.put("sign", sign);
//		paramJson.put("channel_code", sx_channel_code);
//		return paramJson.toJSONString();
//	}
//
//	/**
//	 * 借点钱获取机构方的加密参数后，按此方式解密验签
//	 * 
//	 * @param paramJson
//	 * @return
//	 */
//	public static String jdqDecode(JSONObject paramJson) {
//		String data = RSAUtil.buildRSADecryptByPrivateKey(paramJson.getString("data"), jdqPrivateKey);// 解密
//		boolean flag = RSAUtil.buildRSAverifyByPublicKey(data, partnerPublicKey, paramJson.getString("sign"));// 验签
//		if (!flag) {
//			System.out.println("验签失败");
//		}
//		return data;
//	}
//
//	/**
//	 * 机构方获取借点钱的加密参数后，按此方式解密验签
//	 * 
//	 * @param paramJson
//	 * @return
//	 */
//	public static String partnerDecode(JSONObject paramJson) {
//		String data = RSAUtil.buildRSADecryptByPrivateKey(paramJson.getString("data"), partnerPrivateKey);// 解密
//		boolean flag = RSAUtil.buildRSAverifyByPublicKey(data, jdqPublicKey, paramJson.getString("sign"));// 验签
//		if (!flag) {
//			System.out.println("验签失败");
//		}
//		return data;
//	}
//
//	/**
//	 * 将Double数据转化为string类型
//	 */
//	public static String dataFormat(Double data) {
//		DecimalFormat df = new DecimalFormat("#0.00");
//		return df.format(data);
//	}
//
//	public static String checkSaveOrder(JdqOrderInfoRequest jdqOrderInfoRequest) {
//		String check = null;
//		String jdqOrderId = jdqOrderInfoRequest.getJdq_order_id();
//		Loan_info loanInfo = jdqOrderInfoRequest.getLoan_info();
//		User_info userInfo = jdqOrderInfoRequest.getUser_info();
//		// 获取姓名、手机号、身份证信息
//
//		List<Address_book> addressBooks = jdqOrderInfoRequest.getAddress_book();
//		User_contact userContact = jdqOrderInfoRequest.getUser_contact();
//		// String operator = jdqOrderInfoRequest.getOperator();
//		// 验证参数
//		if (userInfo == null) {
//			return "用户基本信息为空";
//		}
//		String phone = userInfo.getPhone();
//		String name = userInfo.getName();
//		String idCard = userInfo.getId_card();
//		if (phone == null || name == null || idCard == null) {
//			logger.info("用户基本信息：phone=" + phone + ",idCard=" + idCard + ",name=" + name);
//
//			return "检测用户基本信息三要素为空";
//
//		}
//		if (jdqOrderId == null) {
//
//			return "第三方订单为空";
//		}
//		if (addressBooks == null) {
//
//			return "用户通讯录信息为空";
//		}
//		if (userContact == null) {
//
//			return "用户联系人信息为空";
//		}
//		// if (operator == null) {
//		// jdqResponse.setCode(JdqResponse.CODE_FAIL);
//		// jdqResponse.setDesc("用户运营商数据为空");
//		// return jdqResponse;
//		// }
//		return check;
//	}
//
//	/**
//	 * 检查是否为中文
//	 */
//	public static boolean checkChinese(String text) {
//		if (CommUtils.isNull(text)) {
//			return false;
//		}
//		String patternString = "[\u4e00-\u9fa5]";
//		Pattern pattern = Pattern.compile(patternString);
//		Matcher matcher = pattern.matcher(text);
//		if (matcher.find()) {
//			return true;
//		}
//		return false;
//	}
//
//	public static void main(String[] args) {
//		// 加密解密数据
//		Map<String, Object> map = new HashMap<>();
//		String jdq_order_id = "BE64647564545";
//		map.put("jdq_order_id", jdq_order_id);
//		String channel_order_id = "GR564646464";
//		map.put("channel_order_id", channel_order_id);
//		String param = JSONObject.toJSONString(map);
//		System.out.println(param);
//		String data = partnerEncode(param);
//		System.out.println(data);
//
//		JSONObject parseObject = JSONObject.parseObject(data);
//		String result = jdqDecode(parseObject);
//		System.out.println(result);
//
//	}
//
//}
