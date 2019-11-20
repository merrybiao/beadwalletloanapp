///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.kaola;
//
//import org.apache.log4j.Logger;
//
//import com.waterelephant.sxyDrainage.entity.kaola.KaoLaRequest;
//import com.waterelephant.utils.CommUtils;
//
///**
// * 
// * 
// * Module:
// * 
// * KaoLaUtils.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class KaoLaUtils {
//	private static Logger logger = Logger.getLogger(KaoLaUtils.class);
//
//	//
//	private final static String SX_PRIVATEKEY = KaoLaConstant.get("sx_private_key");
//
//	private final static String KL_PUBLICKEY = KaoLaConstant.get("kl_public_key");
//
//	public static String checkFilter(KaoLaRequest kaoLaRequest) {
//
//		String check = null;
//		if (kaoLaRequest == null) {
//			logger.info("考拉请求参数为空");
//			check = "请求参数为空";
//		}
//		if (kaoLaRequest.getReqData() == null) {
//			logger.info("考拉请求数据为空");
//			check = "请求数据为空";
//		}
//		if (kaoLaRequest.getSign() == null) {
//			logger.info("考拉验证签名为空");
//			check = "签名为空";
//		}
//		// TODO 解密验签
//
//		return check;
//	}
//
//	/**
//	 * 获取收入
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public static String income(Integer key) {
//		// 0- 2000 以内;1- 2000-3000;2- 3000-5000;3- 5000-8000;4- 8000-12000;5- 12000 以上;
//		String income = null;
//		if (CommUtils.isNull(key)) {
//			income = "2000 以内";
//		} else {
//			switch (key) {
//			case 0:
//				income = "2000 以内";
//				break;
//			case 1:
//				income = "2000-3000";
//				break;
//			case 2:
//				income = "3000-5000";
//				break;
//			case 3:
//				income = "5000-8000";
//				break;
//			case 4:
//				income = "8000-12000";
//				break;
//			case 5:
//				income = "12000以上";
//				break;
//			default:
//				income = "2000以内";
//				break;
//			}
//
//		}
//		return income;
//	}
//
//	/**
//	 * 获取单位性质
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public static String companyNatrue(Integer key) {
//		// 1-机关、事业单位;2-国有企业;3-三资企业;4-上市公司;5-民营、乡镇企业;6-私营企业
//		String companyType = null;
//		if (CommUtils.isNull(key)) {
//			companyType = "其他";
//		} else {
//			switch (key) {
//			case 1:
//				companyType = "机关、事业单位";
//				break;
//			case 2:
//				companyType = "国有企业";
//				break;
//			case 3:
//				companyType = "三资企业";
//				break;
//			case 4:
//				companyType = "上市公司";
//				break;
//			case 5:
//				companyType = "民营、乡镇企业";
//				break;
//			case 6:
//				companyType = "私营企业";
//				break;
//
//			default:
//				companyType = "其他";
//				break;
//			}
//
//		}
//		return companyType;
//
//	}
//
//	/**
//	 * 获取工作年限
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public static String workYear(Integer key) {
//		// 0- 不足3 个月;1- 3-5 月;2- 6 个月-11 个月;3- 1年-3 年;4- 4 年-7 年;5- 7 年以上;
//		String workYear = null;
//		if (CommUtils.isNull(key)) {
//			workYear = "1年以内";
//		} else {
//			switch (key) {
//			case 1:
//				workYear = "3-5月";
//				break;
//			case 2:
//				workYear = "6个月-11个月";
//				break;
//			case 3:
//				workYear = "1年-3年";
//				break;
//			case 4:
//				workYear = "4年-7年";
//				break;
//			case 5:
//				workYear = "7年以上";
//				break;
//			case 0:
//				workYear = "不足3个月";
//				break;
//			default:
//				workYear = "1年以内";
//				break;
//			}
//		}
//
//		return workYear;
//	}
//
//	public static void main(String[] args) {
//		String workYear = workYear(2);
//		System.out.println(workYear);
//		String companyNatrue = companyNatrue(null);
//		System.out.println(companyNatrue);
//
//	}
//
//}
