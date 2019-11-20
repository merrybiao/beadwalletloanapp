/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.util.qihu360;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.waterelephant.drainage.entity.qihu360.QiHu360Request;
import com.waterelephant.utils.CommUtils;

/**
 * 
 * 
 * Module:奇虎360（code360）
 * 
 * QiHu360Utils.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class QiHu360Utils {

	public static final String QIHU360_PUBLIC_KEY = "qiHu360PublicKey";
	public static final String PRIVATE_KEY = "privateKey";

	// 行业类别
	public static final Map<String, String> INDUSTRY_TYPE = new HashMap<>();
	// 是否已婚映射
	public static final Map<String, String> HAVE_MARRY = new HashMap<>();
	// 是否有房映射
	public static final Map<String, String> HAVE_HOUSE = new HashMap<>();
	// 是否有车映射
	public static final Map<String, String> HAVE_CAR = new HashMap<>();
	// 审核状态映射
	private static Map<String, String> APPROVE_STATUS = new HashMap<>();
	// 账单状态映射
	private static Map<String, String> BILL_STATUS = new HashMap<String, String>();
	// 连连状态码
	private static Map<String, String> LIANLIAN_CODE_MAP = new HashMap<String, String>();
	// 订单状态映射
	private static Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();
	static {
		INDUSTRY_TYPE.put("1", "批发/零售业");
		INDUSTRY_TYPE.put("2", "制造业");
		INDUSTRY_TYPE.put("3", "金融业/保险/证券");
		INDUSTRY_TYPE.put("4", "商业服务业/娱乐/艺术/体育");
		INDUSTRY_TYPE.put("5", "计算机/互联网");
		INDUSTRY_TYPE.put("6", "通讯/电子");

		HAVE_MARRY.put("1", "0");
		HAVE_MARRY.put("2", "1");
		HAVE_MARRY.put("3", "1");
		HAVE_MARRY.put("4", "0");
		HAVE_MARRY.put("5", "1");
		HAVE_MARRY.put("6", "1");

		HAVE_HOUSE.put("1", "1");
		HAVE_HOUSE.put("2", "1");
		HAVE_HOUSE.put("3", "0");
		HAVE_HOUSE.put("4", "0");
		HAVE_HOUSE.put("5", "0");
		HAVE_HOUSE.put("6", "1");
		HAVE_HOUSE.put("7", "0");

		HAVE_CAR.put("1", "0");
		HAVE_CAR.put("2", "1");
		HAVE_CAR.put("3", "1");
		HAVE_CAR.put("4", "1");
		HAVE_CAR.put("5", "0");

		APPROVE_STATUS.put("7", "40");
		APPROVE_STATUS.put("8", "40");
		APPROVE_STATUS.put("4", "10");
		APPROVE_STATUS.put("5", "10");
		APPROVE_STATUS.put("6", "10");
		APPROVE_STATUS.put("9", "10");
		APPROVE_STATUS.put("11", "10");
		APPROVE_STATUS.put("12", "10");
		APPROVE_STATUS.put("13", "10");
		APPROVE_STATUS.put("14", "10");

		BILL_STATUS.put("9", "1");
		BILL_STATUS.put("6", "2");
		BILL_STATUS.put("13", "3");

		LIANLIAN_CODE_MAP.put("0000", "交易成功");

		LIANLIAN_CODE_MAP.put("1000", "支付授权令牌失效或错误");
		LIANLIAN_CODE_MAP.put("1001", "商户请求签名错误");
		LIANLIAN_CODE_MAP.put("1002", "支付服务超时，请重新支付");
		LIANLIAN_CODE_MAP.put("1003", "正在支付中,请稍后");
		LIANLIAN_CODE_MAP.put("1004", "商户请求参数校验错误");
		LIANLIAN_CODE_MAP.put("1005", "支付处理失败");
		LIANLIAN_CODE_MAP.put("1006", "用户中途取消支付操作");
		LIANLIAN_CODE_MAP.put("1007", "网络链接繁忙");
		LIANLIAN_CODE_MAP.put("1008", "银行卡号无效");
		LIANLIAN_CODE_MAP.put("1009", "暂停商户支付服务，请联系连连银通客服");
		LIANLIAN_CODE_MAP.put("1014", "日累计金额或笔数超限");
		LIANLIAN_CODE_MAP.put("1016", "余额不足");
		LIANLIAN_CODE_MAP.put("1019", "单笔金额超限");
		LIANLIAN_CODE_MAP.put("1100", "无效卡号");
		LIANLIAN_CODE_MAP.put("1101", "卡已挂失");
		LIANLIAN_CODE_MAP.put("1102", "无此发卡方");
		LIANLIAN_CODE_MAP.put("1103", "您的卡已过期或者您输入的有效期不正确");
		LIANLIAN_CODE_MAP.put("1104", "卡密码错误  ");
		LIANLIAN_CODE_MAP.put("1105", "卡号在黑名单中");
		LIANLIAN_CODE_MAP.put("1106", "允许的输入 PIN 次数超限");
		LIANLIAN_CODE_MAP.put("1107", "交易成功");
		LIANLIAN_CODE_MAP.put("1108", "您输入的证件号、姓名或手机号有误");
		LIANLIAN_CODE_MAP.put("1109", "卡号和证件号不符");
		LIANLIAN_CODE_MAP.put("1110", "卡状态异常");
		LIANLIAN_CODE_MAP.put("1111", "交易异常，支付失败");

		LIANLIAN_CODE_MAP.put("1112", "证件号有误");
		LIANLIAN_CODE_MAP.put("1113", "持卡人姓名有误");
		LIANLIAN_CODE_MAP.put("1114", "手机号有误");
		LIANLIAN_CODE_MAP.put("1115", "该卡未预留手机号");
		LIANLIAN_CODE_MAP.put("1200", "用户选择的银行暂不支持，请重新选择其他银行进行支付/签约");
		LIANLIAN_CODE_MAP.put("1900", "短信码校验错误");
		LIANLIAN_CODE_MAP.put("1901", "短信码已失效");
		LIANLIAN_CODE_MAP.put("2004", "签约处理中");
		LIANLIAN_CODE_MAP.put("2005", "原交易已在进行处理，请勿重复提交");
		LIANLIAN_CODE_MAP.put("2006", "交易已过期");
		LIANLIAN_CODE_MAP.put("2007", "交易已支付成功");
		LIANLIAN_CODE_MAP.put("2008", "交易处理中");
		LIANLIAN_CODE_MAP.put("3001", "非法商户");
		LIANLIAN_CODE_MAP.put("3002", "商户无此业务权限");
		LIANLIAN_CODE_MAP.put("3003", "用户签约失败");
		LIANLIAN_CODE_MAP.put("3004", "用户解约失败");
		LIANLIAN_CODE_MAP.put("3005", "暂时不支持该银行卡支付 ");
		LIANLIAN_CODE_MAP.put("3006", "无效的银行卡信息");
		LIANLIAN_CODE_MAP.put("3007", "用户信息查询失败");
		LIANLIAN_CODE_MAP.put("4000", "解约失败，请联系发卡行");
		LIANLIAN_CODE_MAP.put("5001", "卡 bin 校验失败");
		LIANLIAN_CODE_MAP.put("5002", "原始交易不存在");
		LIANLIAN_CODE_MAP.put("5003", "退款金额错误");
		LIANLIAN_CODE_MAP.put("5004", "商户状态异常，无法退款");
		LIANLIAN_CODE_MAP.put("5005", "退款失败，请重试");

		LIANLIAN_CODE_MAP.put("5006", "商户账户余额不足");
		LIANLIAN_CODE_MAP.put("5007", "累计退款金额大于原交易金额");
		LIANLIAN_CODE_MAP.put("5008", "原交易未成功");
		LIANLIAN_CODE_MAP.put("5501", "大额行号查询失败");
		LIANLIAN_CODE_MAP.put("5502", "信用卡不支持提现");
		LIANLIAN_CODE_MAP.put("6001", "卡余额不足");
		LIANLIAN_CODE_MAP.put("6002", "该卡号未成功进行首次验证");
		LIANLIAN_CODE_MAP.put("8000", "用户信息不存在");
		LIANLIAN_CODE_MAP.put("8001", "用户状态异常");
		LIANLIAN_CODE_MAP.put("8888", "交易申请成功,需要再次进行验证");
		LIANLIAN_CODE_MAP.put("8901", "没有签约记录 ");
		LIANLIAN_CODE_MAP.put("8911", "没有风控记录");
		LIANLIAN_CODE_MAP.put("9901", "请求报文非法");
		LIANLIAN_CODE_MAP.put("9902", "请求参数错误");
		LIANLIAN_CODE_MAP.put("9903", "请求参数错误");
		LIANLIAN_CODE_MAP.put("9904", "支付参数和原创建支付单参数不一致");
		LIANLIAN_CODE_MAP.put("9091", "创建支付失败");
		LIANLIAN_CODE_MAP.put("9092", "业务信息非法");
		LIANLIAN_CODE_MAP.put("9093", "无对应的支付单信息");
		LIANLIAN_CODE_MAP.put("9094", "请求银行扣款失败");
		LIANLIAN_CODE_MAP.put("9700", "短信验证码错误");
		LIANLIAN_CODE_MAP.put("9701", "短信验证码和手机不匹配");
		LIANLIAN_CODE_MAP.put("9702", "验证码错误次数超过最大次数,请重新获取进行验证");
		LIANLIAN_CODE_MAP.put("9703", "短信验证码失效,请重新获取");
		LIANLIAN_CODE_MAP.put("9704", "短信发送异常,请稍后重试");
		LIANLIAN_CODE_MAP.put("9902", "接口调用异常");

		LIANLIAN_CODE_MAP.put("9910", "风险等级过高");
		LIANLIAN_CODE_MAP.put("9911", "金额超过指定额度");
		LIANLIAN_CODE_MAP.put("9912", "该卡不支持");
		LIANLIAN_CODE_MAP.put("9913", "该卡已签约成功");
		LIANLIAN_CODE_MAP.put("9970", "银行系统日切处理中");
		LIANLIAN_CODE_MAP.put("9999", "系统错误");
		LIANLIAN_CODE_MAP.put("9990", "银行交易出错");
		LIANLIAN_CODE_MAP.put("9907", "银行编码与卡不一致");
		LIANLIAN_CODE_MAP.put("9000", "银行维护中，请稍后再试");

		ORDER_STATUS_MAP.put("9", "170");
		ORDER_STATUS_MAP.put("13", "180");
		ORDER_STATUS_MAP.put("6", "200");
		ORDER_STATUS_MAP.put("7", "110");
		ORDER_STATUS_MAP.put("8", "110");
		ORDER_STATUS_MAP.put("4", "100");
		ORDER_STATUS_MAP.put("11", "100");
		ORDER_STATUS_MAP.put("12", "100");
		ORDER_STATUS_MAP.put("14", "100");
		ORDER_STATUS_MAP.put("5", "100");
	}

	public static String makeDESKey() {
		Random random = new Random();
		int num = random.nextInt(Integer.MAX_VALUE);
		return String.valueOf(num);
	}

	// 校验参数
	public static String checkParam(QiHu360Request qiHu360Request) {
		try {
			// 第一步：检查参数
			if (CommUtils.isNull(qiHu360Request)) {
				return "请求参数为空";
			}

			String sign = qiHu360Request.getSign();// 签名值
			if (CommUtils.isNull(sign)) {
				return "sign为空";
			}

			String merchant_id = qiHu360Request.getMerchant_id();// 合作机构给 360 分配的商户号
			if (CommUtils.isNull(merchant_id)) {
				return "merchant_id为空";
			}

			String biz_enc = qiHu360Request.getBiz_enc();// biz_data 加密方式（0 不加密，1 加密:采用 DES 加密算法）

			String des_key = null;// des密钥
			String rsa_des_key = null;// RSA 加密后的密钥（biz_enc 为 1 时为必传）
			String biz_data = null;// 业务参数
			String des_biz_data = null;// des加密后的业务参数
			if ("1".equals(biz_enc)) {
				rsa_des_key = qiHu360Request.getDes_key();
				if (CommUtils.isNull(rsa_des_key)) {
					return "des_key为空";
				}

				byte[] bs = RSAUtils.decryptByPrivateKey(Base64Utils.decode(rsa_des_key),
						QiHu360Constant.get(PRIVATE_KEY));
				des_key = new String(bs);

				des_biz_data = qiHu360Request.getBiz_data();
				if (CommUtils.isNull(des_biz_data)) {
					return "biz_data为空";
				}
				// 解密业务参数
				biz_data = DES.decrypt(des_biz_data, des_key);
			} else {
				biz_data = qiHu360Request.getBiz_data();
				if (CommUtils.isNull(biz_data)) {
					return "biz_data为空";
				}
			}

			// 通知参数，需要对key做字典序排序算签名
			TreeMap<String, String> requestParam = new TreeMap<String, String>();
			requestParam.put("merchant_id", merchant_id);

			if ("1".equals(biz_enc)) {
				requestParam.put("biz_enc", biz_enc);
				requestParam.put("des_key", rsa_des_key);
				requestParam.put("biz_data", des_biz_data);
			} else if ("0".equals(biz_enc)) {
				requestParam.put("biz_enc", biz_enc);
				requestParam.put("biz_data", biz_data);
			} else {
				requestParam.put("biz_data", biz_data);
			}
			String signStr = makeSignStr(requestParam);

			// 第二步：验证签名
			boolean flag = RSAUtils.verify(signStr.getBytes(), QiHu360Constant.get(QIHU360_PUBLIC_KEY), sign);
			if (!flag) {
				return "验证签名失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 生成签名串
	public static String makeSignStr(TreeMap<String, String> data) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String key : data.keySet()) {
			if ("sign".equals(key)) {
				continue;
			}
			stringBuffer.append("&");
			stringBuffer.append(key);
			stringBuffer.append("=");
			stringBuffer.append(data.get(key));
		}
		String signStr = stringBuffer.toString();
		// System.out.println(signStr);
		if (!"".equals(signStr)) {
			signStr = signStr.substring(1);
		}
		// System.out.println("signStr ==== " + signStr);
		return signStr;
	}

	// 解密biz_data数据
	public static String decodeBizData(QiHu360Request qiHu360Request) {
		String des_key = null;// RSA 加密后的密钥（biz_enc 为 1 时为必传）
		String biz_data = null;// 业务参数
		try {
			String biz_enc = qiHu360Request.getBiz_enc();// biz_data 加密方式（0 不加密，1 加密:采用 DES 加密算法）

			if ("1".equals(biz_enc)) {
				des_key = qiHu360Request.getDes_key();

				byte[] desKey = RSAUtils.decryptByPrivateKey(Base64Utils.decode(des_key),
						QiHu360Constant.get(QiHu360Utils.PRIVATE_KEY));// 解密
				des_key = new String(desKey);

				biz_data = qiHu360Request.getBiz_data();

				biz_data = DES.decrypt(biz_data, des_key);// 解密
			} else {
				biz_data = qiHu360Request.getBiz_data();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("biz_data ====" + biz_data);
		return biz_data;
	}

	public static String getIndustry(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return INDUSTRY_TYPE.get(key);
	}

	public static String getMarry(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return HAVE_MARRY.get(key);
	}

	public static String getHouse(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return HAVE_HOUSE.get(key);
	}

	public static String getCar(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return HAVE_CAR.get(key);
	}

	public static String getApprovalStatus(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return APPROVE_STATUS.get(key);
	}

	public static String getBillStatus(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return BILL_STATUS.get(key);
	}

	public static String getMsgByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return "系统异常";
		}
		return LIANLIAN_CODE_MAP.get(code);
	}

	public static String getOrderStatus(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return ORDER_STATUS_MAP.get(key);
	}

	public static void main(String[] args) {
		System.out.println(makeDESKey());
	}
}
