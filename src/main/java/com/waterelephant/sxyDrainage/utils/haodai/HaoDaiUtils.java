///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.haodai;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.codec.digest.HmacUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//
//import com.waterelephant.utils.CommUtils;
//
///**
// * 
// * 
// * Module:
// * 
// * HaoDaiUtils.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class HaoDaiUtils {
//
//	private final static String APP_ID = HaoDaiConstant.APP_ID;
//
//	private final static String APP_SECRET = HaoDaiConstant.APP_SERCRET;
//
//	private static Logger logger = Logger.getLogger(HaoDaiUtils.class);
//
//	// 订单状态转换
//	private static Map<Long, Integer> ORDER_STATUS = new HashMap<Long, Integer>();
//
//	static {
//		// 我方，1:草稿，2:初审，3:终审，4:待签约，5:待放款，6:结束，7:拒绝，8:撤回，9:还款中，11:待生成合同，12:待债匹，13:逾期，14:债匹中，15:待商城用户确认
//		// 好贷网， -12 放款失败, -11 用户取消，-10 逾期，-9 还款失败， 9 还款中 ，10 打款中 11 放款成功, 12 展期，13 还款结清
//		// ORDER_STATUS.put("1", "0");
//		// ORDER_STATUS.put("2", "0");
//		// ORDER_STATUS.put("3", "0");
//		// ORDER_STATUS.put("4", "4");
//		// ORDER_STATUS.put("5", "6");
//		ORDER_STATUS.put(6L, 13);
//		// ORDER_STATUS.put("7", "2");
//		// ORDER_STATUS.put("8", "2");
//		ORDER_STATUS.put(9L, 9);
//		// ORDER_STATUS.put("12", "6");
//		// ORDER_STATUS.put("14", "6");
//		ORDER_STATUS.put(13L, -10);
//
//	}
//
//	// 订单状态转换
//	public static Integer statusConvert(Long statusId) {
//		if (statusId == null) {
//			return null;
//		}
//		return ORDER_STATUS.get(statusId);
//	}
//
//	public static String checkFilter(String body, String sign, String appId, String version, String Timestamp) {
//		String check = null;
//
//		try {
//			if (CommUtils.isNull(body)) {
//				return "获取请求数据为空";
//			}
//			if (CommUtils.isNull(appId)) {
//				return "获取APP_ID为空";
//			}
//			if (!appId.equals(APP_ID)) {
//				return "传入的APP_ID非法";
//			}
//
//			if (CommUtils.isNull(sign)) {
//				return "获取签名值为空";
//			}
//			if (CommUtils.isNull(version)) {
//				return "获取版本号为空";
//			}
//			if (CommUtils.isNull(Timestamp)) {
//				return "获取接口时间戳为空";
//			}
//
//			String findSignData = findSignData(appId, version, Timestamp, body);
//			if (!findSignData.equalsIgnoreCase(sign)) {
//
//				return "验证签名失败";
//			}
//			return check;
//		} catch (Exception e) {
//			logger.info("验证签名异常", e);
//
//			return null;
//		}
//
//	}
//
//	// 获取用户的行业
//	public static String getWorkType(Integer number) {
//		String workType = null;
//		if (CommUtils.isNull(number)) {
//			return "其他类型";
//		}
//		if (number >= 1 && number <= 22) {
//			String[] workTypes = { "农、林、牧、渔业", "采掘业", "制造业", "电力、燃气及水的生产和供应业", "建筑业", "交通运输、仓储和邮政业", "信息传输、计算机服务和软件业",
//					"批发和零售业", "住宿和餐饮业", "金融业", "房地产业", "租赁和商务服务业", "科学研究、技术服务业", "水利环境和公共设施管理业", "居民服务", "教育",
//					"卫生、社会保障和社会福利业", "文化、体育和娱乐业", "公共管理和社会组织", "国际组织", "纺织服装", "服务业" };
//			workType = workTypes[number - 1];
//		} else {
//			workType = "其他";
//		}
//
//		return workType;
//
//	}
//
//	// 婚姻状况
//
//	public static int getMarryType(Integer type) {
//		// 1-未婚,2-已婚,3-离异,4-其他
//		// 1:已婚 0:未婚
//		int marry = 0;
//		if (type == null) {
//			return 0;
//		}
//		switch (type) {
//		case 2:
//			marry = 1;
//			break;
//
//		default:
//			marry = 0;
//			break;
//		}
//
//		return marry;
//	}
//
//	/**
//	 * 绑卡 转换富友银行编码为宝付
//	 * 
//	 * @param bankcode 富友银行编码
//	 * @return 对应宝付银行编码
//	 */
//	public static String convertHdToBaoFuCode(String bankcode) {
//		if (StringUtils.isBlank(bankcode)) {
//			return null;
//		}
//		String res = null;
//		switch (bankcode) {
//
//		case "ICBC":
//			res = "0102";
//			break;
//		case "ABC":
//			res = "0103";
//			break;
//		case "BOC":
//			res = "0104";
//			break;
//		case "CCB":
//			res = "0105";
//			break;
//		case "BCOM":
//			res = "0301";
//			break;
//		case "ECITIC":
//			res = "0302";
//			break;
//		case "CEB":
//			res = "0303";
//			break;
//		case "HXB":
//			res = "0304";
//			break;
//		case "CMBC":
//			res = "0305";
//			break;
//		case "GDB":
//			res = "0306";
//			break;
//		case "PAB":
//			res = "0307";
//			break;
//		case "CMB":
//			res = "0308";
//			break;
//		case "CIB":
//			res = "0309";
//			break;
//		case "SPDB":
//			res = "0310";
//			break;
//		case "PSBC":
//			res = "0403";
//			break;
//		}
//		return res;
//	}
//
//	/**
//	 * 宝富编码转银行编码 (0000-->ICBC)
//	 * 
//	 * @param baoFuCode 富友编码
//	 * @return 银行编码
//	 */
//	public static String convertHdBankCode(String baoFuCode) {
//		if (StringUtils.isBlank(baoFuCode)) {
//			return null;
//		}
//		String res = null;
//		switch (baoFuCode) {
//		case "0102":
//			res = "ICBC";
//			break;
//		case "0103":
//			res = "ABC";
//			break;
//		case "0104":
//			res = "BOC";
//			break;
//		case "0105":
//			res = "CCB";
//			break;
//		case "0301":
//			res = "BCOM";
//			break;
//		case "0302":
//			res = "ECITIC";
//			break;
//		case "0303":
//			res = "CEB";
//			break;
//		case "0304":
//			res = "HXB";
//			break;
//		case "0305":
//			res = "CMBC";
//			break;
//		case "0306":
//			res = "GDB";
//			break;
//		case "0307":
//			res = "PAB";
//			break;
//		case "0308":
//			res = "CMB";
//			break;
//		case "0309":
//			res = "CIB";
//			break;
//		case "0310":
//			res = "SPDB";
//			break;
//		case "0403":
//			res = "PSBC";
//			break;
//		}
//		return res;
//	}
//
//	// 签名Dome
//	public static String findSignData(String appID, String version, String Timestamp, String data) {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(appID + "\n");
//		buffer.append(version + "\n");
//		buffer.append(Timestamp + "\n");
//		// buffer.append(System.currentTimeMillis()+"\n");
//		buffer.append(data);
//		byte[] valueToDigest = buffer.toString().getBytes();
//		String hex = new HmacUtils().hmacSha1Hex(APP_SECRET.getBytes(), valueToDigest);
//		return hex.toLowerCase();
//	}
//
//	public static void main(String[] args) {
//		// String data = "{\"mobile\":\"1822222****\",\"user_name\":\"张三\"}";
//		// String data = "{ \"order_no\": \"1234wyn\"}";
//		// String data =
//		// "{\"orderInfo\":{\"order_no\":\"B12312351245451\",\"user_name\":\"王子杰\",\"user_id\":\"4211271995102121\",\"user_mobile\":\"15721571487\",\"apply_term\":\"4\",\"apply_amount\":\"2000.00\",\"address\":\"武汉\"},\"addInfo\":{}}";
//		// String data =
//		// "{\"orderInfo\":{\"order_no\":\"B12312351245451\"},\"contacts\":{\"13288837373\":\"张三\",\"13458745874\":\"李四\",\"13445412445\":\"张冲\"},\"applyDetail\":{\"marriage\":0,\"profession\":0,\"position_name\":\"\",\"type\":0,\"industry\":0,\"has_loan\":0,\"company_name\":\"武汉斗赞科技\",\"dorm_address\":\"\",\"graduation_time\":\"0\",\"to_company_time\":-28800,\"company_address\":\"\",\"company_type\":0,\"first_work_time\":0,\"grade\":0,\"salary_bank_public\":600,\"salary_bank_other\":0,\"house_loan\":0,\"child_status\":0,\"register_type\":0,\"sos_user\":{\"user_1\":{\"name\":\"王二明\",\"relation\":\"2\",\"sos_phone\":\"18516881523\"},\"user_2\":{\"name\":\"王小明\",\"relation\":\"4\",\"sos_phone\":\"18213691234\"},\"user_3\":{\"name\":\"王豆豆\",\"relation\":\"7\",\"sos_phone\":\"18513698523\"},\"user_4\":{\"name\":\"李宁\",\"relation\":\"6\",\"sos_phone\":\"18563214568\"},\"user_5\":{\"name\":\"李白\",\"relation\":\"6\",\"sos_phone\":\"18623568523\"}},\"iden_opposite_side\":\"http://openapi.api.test.haodai.com/Public/upload/shandai/profile/2017/01/18/587f08b47d0f8.jpg\",\"iden_correct_side\":\"http://openapi.api.test.haodai.com/Public/upload/shandai/profile/2017/01/18/587f084105f08.jpg\",\"iden_scene\":\"http://openapi.api.test.haodai.com/Public/upload/shandai/profile/2017/01/18/587f08d025c80.jpg\",\"iden_outdate\":0,\"qq\":\"765532145\",\"email\":\"765532145@qq.com\",\"otherloan\":0,\"department\":\"\",\"company_phone\":\"\",\"work_experience\":0,\"credit_no\":\"\",\"credit_limit\":0,\"credit_photo\":\"\",\"house_photo\":\"\",\"work_photo\":\"\",\"phone_time\":2,\"address_book\":0,\"loan_money\":0,\"school_name\":\"\",\"to_school_time\":0,\"source_income\":\"0\"}}";
//		// String data =
//		// "{\"order_no\":\"B12312351245451\",\"bank_card\":\"6215581818002985950\",\"bank_code\":\"ICBC\",\"user_name\":\"王飞\",\"iden_card\":\"421127199510211517\",\"user_mobile\":\"15727158711\"}";
//		// String data =
//		// "{\"order_no\":\"B12312351245451\",\"bank_card\":\"6215581818002985950\",\"bank_code\":\"ICBC\",\"user_name\":\"王飞\",\"iden_card\":\"421127199510211517\",\"user_mobile\":\"15727158711\",\"verify_code\":\"123456\"}";
//		// String data =
//		// "{\"orderNo\":\"B12312351245451\",\"loanAmt\":\"1000.00\",\"period\":\"1-28\",\"returnUrl\":\"http://www.baidu.com\"}";
//		// String data =
//		// "{\"order_no\":\"B12312351245451\",\"bank_card\":\"6227007200404946503\",\"bank_code\":\"CCB\",\"user_name\":\"肖建平\",\"iden_card\":\"431281198906074417\",\"user_mobile\":\"13028884271\",\"verify_code\":\"123456\"}";
//		// String data =
//		// "{\"orderInfo\":{\"order_no\":\"B12312351245451\"},\"contacts\":[{\"13288837373\":\"张三\"},{\"13458745874\":\"李四\"}],\"applyDetail\":{\"marriage\":0,\"profession\":0,\"position_name\":\"\",\"type\":0,\"industry\":0,\"has_loan\":0,\"company_name\":\"武汉斗赞科技\",\"dorm_address\":\"\",\"graduation_time\":\"0\",\"to_company_time\":-28800,\"company_address\":\"\",\"company_type\":0,\"first_work_time\":0,\"grade\":0,\"salary_bank_public\":600,\"salary_bank_other\":0,\"house_loan\":0,\"child_status\":0,\"register_type\":0,\"sos_user\":{\"user_1\":{\"name\":\"王二明\",\"relation\":\"2\",\"sos_phone\":\"18516881523\"},\"user_2\":{\"name\":\"王小明\",\"relation\":\"4\",\"sos_phone\":\"18213691234\"},\"user_3\":{\"name\":\"王豆豆\",\"relation\":\"7\",\"sos_phone\":\"18513698523\"},\"user_4\":{\"name\":\"李宁\",\"relation\":\"6\",\"sos_phone\":\"18563214568\"},\"user_5\":{\"name\":\"李白\",\"relation\":\"6\",\"sos_phone\":\"18623568523\"}},\"iden_opposite_side\":\"http://openapi.api.test.haodai.com/Public/upload/shandai/profile/2017/01/18/587f08b47d0f8.jpg\",\"iden_correct_side\":\"http://openapi.api.test.haodai.com/Public/upload/shandai/profile/2017/01/18/587f084105f08.jpg\",\"iden_scene\":\"http://openapi.api.test.haodai.com/Public/upload/shandai/profile/2017/01/18/587f08d025c80.jpg\",\"iden_outdate\":0,\"qq\":\"765532145\",\"email\":\"765532145@qq.com\",\"otherloan\":0,\"department\":\"\",\"company_phone\":\"\",\"work_experience\":0,\"credit_no\":\"\",\"credit_limit\":0,\"credit_photo\":\"\",\"house_photo\":\"\",\"work_photo\":\"\",\"phone_time\":2,\"address_book\":0,\"loan_money\":0,\"school_name\":\"\",\"to_school_time\":0,\"source_income\":\"0\"}}";
//		String data = "{\"orderInfo\":{\"order_no\":\"B12312351245451\",\"user_name\":\"王子杰\",\"user_id\":\"4211271995102121\",\"user_mobile\":\"15721571487\",\"apply_term\":\"4\",\"apply_amount\":\"2000.00\",\"address\":\"武汉\"},\"addInfo\":{\"mobile_src\":{\"available_balance\":5000,\"bills\":[{\"actualFee\":0,\"base_fee\":0,\"bill_end_date\":\"2018-02-28\",\"bill_month\":\"2018-02\",\"bill_start_date\":\"2018-02-01\",\"discount\":0,\"extra_discount\":0,\"extra_fee\":0,\"extra_service_fee\":0,\"last_point\":10735,\"notes\":null,\"paid_fee\":0,\"point\":10871,\"related_mobiles\":\"\",\"sms_fee\":0,\"total_fee\":0,\"unpaid_fee\":0,\"voice_fee\":0,\"web_fee\":0},{\"actualFee\":0,\"base_fee\":697,\"bill_end_date\":\"2018-03-31\",\"bill_month\":\"2018-03\",\"bill_start_date\":\"2018-03-01\",\"discount\":2817,\"extra_discount\":0,\"extra_fee\":0,\"extra_service_fee\":0,\"last_point\":0,\"notes\":null,\"paid_fee\":2817,\"point\":0,\"related_mobiles\":\"15321790296\",\"sms_fee\":20,\"total_fee\":2817,\"unpaid_fee\":0,\"voice_fee\":0,\"web_fee\":2100},{\"actualFee\":0,\"base_fee\":0,\"bill_end_date\":\"2017-11-30\",\"bill_month\":\"2017-11\",\"bill_start_date\":\"2017-11-01\",\"discount\":0,\"extra_discount\":0,\"extra_fee\":0,\"extra_service_fee\":0,\"last_point\":10003,\"notes\":null,\"paid_fee\":0,\"point\":10303,\"related_mobiles\":\"\",\"sms_fee\":0,\"total_fee\":0,\"unpaid_fee\":0,\"voice_fee\":0,\"web_fee\":0},{\"actualFee\":0,\"base_fee\":0,\"bill_end_date\":\"2017-12-31\",\"bill_month\":\"2017-12\",\"bill_start_date\":\"2017-12-01\",\"discount\":0,\"extra_discount\":0,\"extra_fee\":0,\"extra_service_fee\":0,\"last_point\":10303,\"notes\":null,\"paid_fee\":0,\"point\":10483,\"related_mobiles\":\"\",\"sms_fee\":0,\"total_fee\":0,\"unpaid_fee\":0,\"voice_fee\":0,\"web_fee\":0},{\"actualFee\":1300,\"base_fee\":900,\"bill_end_date\":\"2018-04-30\",\"bill_month\":\"2018-04\",\"bill_start_date\":\"2018-04-01\",\"discount\":0,\"extra_discount\":0,\"extra_fee\":0,\"extra_service_fee\":0,\"last_point\":null,\"notes\":null,\"paid_fee\":0,\"point\":null,\"related_mobiles\":\"\",\"sms_fee\":0,\"total_fee\":1300,\"unpaid_fee\":0,\"voice_fee\":0,\"web_fee\":400},{\"actualFee\":0,\"base_fee\":0,\"bill_end_date\":\"2018-01-31\",\"bill_month\":\"2018-01\",\"bill_start_date\":\"2018-01-01\",\"discount\":0,\"extra_discount\":0,\"extra_fee\":0,\"extra_service_fee\":0,\"last_point\":10483,\"notes\":null,\"paid_fee\":0,\"point\":10735,\"related_mobiles\":\"\",\"sms_fee\":0,\"total_fee\":0,\"unpaid_fee\":0,\"voice_fee\":0,\"web_fee\":0}],\"calls\":[{\"bill_month\":\"2018-04\",\"items\":[{\"details_id\":\"4fa845f76dc553dd71562b75e92514320\",\"dial_type\":\"DIALED\",\"duration\":32,\"fee\":0,\"location\":\"北京\",\"location_type\":\"国内通话\",\"peer_number\":\"15321398072\",\"time\":\"2018-04-02 11:38:51\"}],\"total_size\":1},{\"bill_month\":\"2018-03\",\"items\":[{\"details_id\":\"4be4f95c1bdcb714638005ee5b2529170\",\"dial_type\":\"DIALED\",\"duration\":69,\"fee\":0,\"location\":\"北京\",\"location_type\":\"国内通话\",\"peer_number\":\"17346559862\",\"time\":\"2018-03-13 19:44:14\"},{\"details_id\":\"f2654bc9319fa9ae9c49bcea86f561520\",\"dial_type\":\"DIALED\",\"duration\":22,\"fee\":0,\"location\":\"北京\",\"location_type\":\"国内通话\",\"peer_number\":\"17346559862\",\"time\":\"2018-03-12 19:53:16\"}],\"total_size\":2},{\"bill_month\":\"2018-02\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2018-01\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2017-12\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2017-11\",\"items\":[],\"total_size\":0}],\"carrier\":\"CHINA_TELECOM\",\"city\":\"北京\",\"code\":0,\"families\":[{\"family_num\":\"\",\"items\":[]}],\"idcard\":\"362526*51\",\"last_modify_time\":\"2018-04-13 11:32:02\",\"level\":\"无星\",\"message\":\"正常\",\"mobile\":\"15321790296\",\"name\":\"邹瑞平\",\"nets\":[{\"bill_month\":\"2018-04\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2018-03\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2018-02\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2018-01\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2017-12\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2017-11\",\"items\":[],\"total_size\":0}],\"open_time\":\"2018-03-06\",\"package_name\":null,\"packages\":[],\"province\":\"北京\",\"recharges\":[{\"amount\":5000,\"details_id\":\"b030f6014a1c52a6ebd4fc6c45df54930\",\"recharge_time\":\"2018-04-02 00:00:00\",\"type\":\"其他\"}],\"smses\":[{\"bill_month\":\"2018-04\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2018-03\",\"items\":[{\"details_id\":\"e7999ec36b74a4e5d2c976cd76eef3190\",\"fee\":10,\"location\":\"\",\"msg_type\":\"SMS\",\"peer_number\":\"13311501432\",\"send_type\":\"SEND\",\"service_name\":\"短信\",\"time\":\"2018-03-16 08:09:57\"},{\"details_id\":\"52d1b60f2283ec1da7734a9f0e92a4bd0\",\"fee\":10,\"location\":\"\",\"msg_type\":\"SMS\",\"peer_number\":\"13311501432\",\"send_type\":\"SEND\",\"service_name\":\"短信\",\"time\":\"2018-03-16 08:08:09\"},{\"details_id\":\"81650aeef13e85bfcfb3c79dc750fbf50\",\"fee\":0,\"location\":\"\",\"msg_type\":\"SMS\",\"peer_number\":\"10001\",\"send_type\":\"SEND\",\"service_name\":\"短信\",\"time\":\"2018-03-08 08:25:26\"}],\"total_size\":3},{\"bill_month\":\"2018-02\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2018-01\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2017-12\",\"items\":[],\"total_size\":0},{\"bill_month\":\"2017-11\",\"items\":[],\"total_size\":0}]}}}";
//		String findSignData = findSignData("IbTgkSaRIseCZcKq", "1.0.0", "1525860815398", data);// 1525860815398
//
//		System.out.println(findSignData);
//	}
//
//	// 读取请求,读流
//	public static String getBody(HttpServletRequest request) {
//		StringBuilder stringBuilder = new StringBuilder();
//		BufferedReader bufferedReader = null;
//		try {
//			request.setCharacterEncoding("UTF-8");
//			InputStream inputStream = request.getInputStream();
//			if (inputStream != null) {
//				bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//				char[] charBuffer = new char[128];
//				int bytesRead = -1;
//				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
//					stringBuilder.append(charBuffer, 0, bytesRead);
//					System.out.println(charBuffer);
//				}
//			} else {
//				stringBuilder.append("");
//			}
//		} catch (IOException ex) {
//			return null;
//		} finally {
//			if (bufferedReader != null) {
//				try {
//					bufferedReader.close();
//				} catch (IOException ex) {
//					return null;
//				}
//			}
//		}
//		return stringBuilder.toString();
//	}
//
//	/**
//	 * 银行卡掩码（后4位）
//	 * 
//	 * @param bankCard
//	 * @return
//	 */
//	public static String getBankCardCover(String bankCard) {
//		if (CommUtils.isNull(bankCard) || bankCard.length() < 4) {
//			return bankCard;
//		}
//		int length = bankCard.length();
//		String str = bankCard.substring(0, length - 4) + "****";
//		return str;
//	}
//
//}
