///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.fenqiguanjia;
//
//import java.security.interfaces.RSAPublicKey;
//import java.text.DecimalFormat;
//import java.util.Date;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.waterelephant.drainage.util.fqgj.jkzj.Base64Utils;
//import com.waterelephant.drainage.util.fqgj.jkzj.KeyReader;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.FengQiGuanJiaRequest;
//import com.waterelephant.utils.CommUtils;
//
///**
// * 
// * 
// * Module:
// * 
// * FenQiGuanJiaUtils.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//
//public class FenQiGuanJiaUtils {
//	private static Logger logger = Logger.getLogger(FenQiGuanJiaUtils.class);
//
//	private static final String PRIKEY = "priKey";
//
//	private static final String PUBKEY = "pubKey";
//
//	private static final String appId = "14";
//
//	// private static final String PRODUCT_ID = "productId";
//
//	private static ObjectMapper mapper = new ObjectMapper();
//
//	public static String checkDataFilter(FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		String check = null;
//		if (fengQiGuanJiaRequest == null) {
//			return "检查用户请求参数为空";
//		}
//		if (fengQiGuanJiaRequest.getBiz_data() == null) {
//			return "检查用户请求数据为空";
//		}
//		if (fengQiGuanJiaRequest.getSign() == null) {
//			return "检查用户签名为空";
//		}
//		try {
//			String desString = JSONObject.toJSONString(fengQiGuanJiaRequest);
//			JSONObject parseObject = JSONObject.parseObject(desString);
//			String signNative = parseObject.getString("sign");
//			RSAPublicKey publicKey = KeyReader.loadPublicKey(FenQiGuanJiaConstant.get(PUBKEY));
//			String encryptStr = getSignSourceStr(parseObject);
//			boolean pass = KeyReader.doCheck(encryptStr, Base64Utils.decode(signNative), publicKey);
//			if (!pass) {
//				return "验证签名失败";
//			}
//
//		} catch (Exception e) {
//			logger.error("FenQiGuanJiaUtils.checkUserFilter()解析数据异常", e);
//		}
//
//		return check;
//	}
//
//	public static String getSignSourceStr(JSONObject jsonObject) throws Exception {
//		TreeMap<String, Object> sortedParams = new TreeMap<>();
//		Set<String> keySet = jsonObject.keySet();
//		for (String key : keySet) {
//			if (!"sign".equals(key)) {
//				sortedParams.put(key, jsonObject.get(key));
//			}
//		}
//		int size = sortedParams.size();
//		int count = 0;
//		StringBuffer buffer = new StringBuffer();
//		for (Map.Entry<String, Object> entry : sortedParams.entrySet()) {
//			count++;
//			String key = entry.getKey();
//			Object value = entry.getValue();
//			if (null == value) {
//				continue;
//			}
//			buffer.append(key).append("=").append(value).append("&");
//		}
//		int length = buffer.length();
//		return buffer.delete(length - 1, length).toString();
//	}
//
//	/**
//	 * 判断工作年限
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public static String getWorkPeriod(String key) {
//		// 1：0-5个月 2：6-11个月 3：1-3年 4：3-7年 5： 7年以上
//		String workPeriod = null;
//		if (StringUtils.isBlank(key)) {
//			workPeriod = "1年以内";
//		} else {
//			switch (key) {
//			case "1":
//				workPeriod = "0-5个月";
//				break;
//			case "2":
//				workPeriod = "6-11个月";
//				break;
//			case "3":
//				workPeriod = "1-3年";
//				break;
//			case "4":
//				workPeriod = "3-7年";
//				break;
//			case "5":
//				workPeriod = "7年以上";
//				break;
//			default:
//				workPeriod = "1年以内";
//				break;
//			}
//		}
//
//		return workPeriod;
//	}
//
//	/**
//	 * 判断职业类别
//	 * 
//	 * @param key
//	 * @return
//	 * 
//	 */
//	public static String getWorkType(String key) {
//
//		// 1.：企业主 2：个体户 4：工薪族 5：学生 10：自由职业
//		String workType = null;
//		switch (key) {
//		case "1":
//			workType = "企业主";
//			break;
//		case "2":
//			workType = "个体户";
//			break;
//		case "4":
//			workType = "工薪族";
//			break;
//		case "5":
//			workType = "学生";
//			break;
//
//		case "10":
//			workType = "自由职业";
//			break;
//
//		default:
//			workType = "未知";
//			break;
//		}
//
//		return workType;
//	}
//
//	/**
//	 * 生成水象云工单号
//	 * 
//	 * @return 工单号
//	 */
//	public static String generateOrderNo() {
//		StringBuffer orderNo = new StringBuffer("Y");
//		orderNo.append(CommUtils.convertDateToString(new Date(), "yyyyMMddhhmmssSSS"));
//		orderNo.append(CommUtils.getRandomNumber(3));
//		return orderNo.toString();
//	}
//
//	public static String convertOrderStatus(Long oriStatus) {
//		if (oriStatus == null) {
//			return null;
//		}
//
//		return FenQiGuanJiaConstant.ORDER_STATUS_MAP.get(String.valueOf(oriStatus));
//	}
//
//	public static String convertApproveStatus(Long oriStatus) {
//		if (oriStatus == null) {
//			return null;
//		}
//
//		return FenQiGuanJiaConstant.APPROVE_STATUS_MAP.get(String.valueOf(oriStatus));
//	}
//
//	public static String convertBillStatus(Long oriStatus) {
//		if (oriStatus == null) {
//			return null;
//		}
//
//		return FenQiGuanJiaConstant.BILL_STATUS_MAP.get(String.valueOf(oriStatus));
//	}
//
//	/**
//	 * 判断数据后续必备字段非空(同事（姓名），同事电话，朋友1（姓名），朋友1电话，朋友2（姓名），朋友2电话，本人qq号，本人微信号)
//	 */
//	public static String wordNotNull(String wechat_number, String qq, String workmate_name, String workmate_phone,
//			String friend_first_name, String friend_first_phone, String friend_second_name,
//			String friend_second_phone) {
//		String check = null;
//		if (wechat_number == null) {
//			return "微信参数为空";
//		}
//		if (qq == null) {
//			return "qq参数为空";
//		}
//		if (workmate_phone == null) {
//			return "同事电话参数为空";
//		}
//		if (workmate_name == null) {
//			return "同事姓名参数为空";
//		}
//
//		if (friend_first_name == null) {
//			return "朋友1姓名参数为空";
//		}
//		if (friend_first_phone == null) {
//			return "朋友1电话参数为空";
//		}
//		if (friend_second_name == null) {
//			return "朋友2姓名参数为空";
//		}
//		if (friend_second_phone == null) {
//			return "朋友2电话参数为空";
//		}
//
//		return check;
//	}
//
//	/**
//	 * 将Double类型数据保留四位小数
//	 */
//	private static String dataFormat(Double data) {
//		DecimalFormat df = new DecimalFormat("#.0000");
//
//		return df.format(data);
//	}
//
//	/**
//	 * 获取待签字符串
//	 * 
//	 * @param requestParams
//	 * @return
//	 */
//	public static String getSignStr(RequestParams requestParams) {
//		Map<String, String> sortedParams = requestParams.getParams();
//
//		StringBuffer buffer = new StringBuffer();
//		for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
//
//			String key = entry.getKey();
//			String value = entry.getValue();
//			if (null == value) {
//				continue;
//			}
//			buffer.append(key).append("=").append(value).append("&");
//		}
//		int length = buffer.length();
//		buffer.delete(length - 1, length);
//
//		return buffer.toString();
//	}
//
//	/**
//	 * 获取请求参数
//	 * 
//	 * @param param
//	 * @return
//	 */
//	public static String getRequestData(Map<String, Object> param) {
//		try {
//			RequestParams requestParams = new RequestParams(appId);
//			String bizDataStr = mapper.writeValueAsString(param);
//			requestParams.put("biz_data", bizDataStr);
//			String signStr = getSignStr(requestParams);
//			logger.info("待签名的字符串：{}" + signStr);
//			String sign = KeyReader.generateSHA1withRSASigature(signStr, FenQiGuanJiaConstant.get(PRIKEY));
//			logger.info("签名串：{}" + sign);
//			requestParams.put("sign", sign);
//
//			// boolean bool = false;
//			// try {
//			// bool = KeyReader.doCheck(signStr, Base64Utils.decode(sign), KeyReader.loadPublicKey(PRIKEY));
//			// } catch (Exception e) {
//			// e.printStackTrace();
//			// }
//			// System.out.println(bool);
//
//			String requestData = JSON.toJSONString(requestParams.getParamMap());
//			logger.info("请求参数：{}" + requestData);
//			return requestData;
//
//		} catch (JsonProcessingException e) {
//			logger.error("获取请求参数异常：", e);
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//
//	/**
//	 *
//	 * 绑卡 转换分期管家银行编码为富友编码
//	 *
//	 * @param bankcode
//	 * @return
//	 */
//	public static String convertFqgjBankCodeToFuiou(String bankcode) {
//		if (StringUtils.isBlank(bankcode)) {
//			return null;
//		}
//		String res = null;
//		switch (bankcode) {
//			case "ICBC":
//				res = "0102";
//				break;
//			case "CCB":
//				res = "0105";
//				break;
//			case "GDB":
//				res = "0306";
//				break;
//			case "HXB":
//				res = "0304";
//				break;
//			case "BOCOM":
//				res = "0301";
//				break;
//			case "CMBC":
//				res = "0305";
//				break;
//			case "ABC":
//				res = "0103";
//				break;
//			case "PINGAN":
//				res = "0307";
//				break;
//			case "SPDB":
//				res = "0310";
//				break;
//			case "CIB":
//				res = "0309";
//				break;
//			case "PSBC":
//				res = "0403";
//				break;
//			case "CMB":
//				res = "0308";
//				break;
//			case "BOC":
//				res = "0104";
//				break;
//			case "CITIC":
//				res = "0302";
//				break;
//			case "CEB":
//				res = "0303";
//				break;
//			default:
//				break;
//		}
//		return res;
//	}
//
//
//}
