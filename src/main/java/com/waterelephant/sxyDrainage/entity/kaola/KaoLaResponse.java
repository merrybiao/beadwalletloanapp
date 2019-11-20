///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.kaola;
//
///**
// * 
// * @ClassName: KaoLaResponse
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @Description: <考拉响应报文实体类>
// */
//
//public class KaoLaResponse {
//	private String retCode;// 错误代码
//	private String retMsg;// 错误信息
//	private Object retData;// 业务数据,失败不返回
//	private String sign;// 签名结果,失败不返回
//
//	public static final String CODE_SUCCESS = "000000";// 交易成功时 retCode 为 000000
//	public static final String CODE_FAIL = "111111";// ，非 000000 则是失败，失败包括验签失败、参数错误或无权限等
//	public static final String MSG = "SUCCESS";
//
//	public String getRetCode() {
//		return retCode;
//	}
//
//	public void setRetCode(String retCode) {
//		this.retCode = retCode;
//	}
//
//	public String getRetMsg() {
//		return retMsg;
//	}
//
//	public void setRetMsg(String retMsg) {
//		this.retMsg = retMsg;
//	}
//
//	public Object getRetData() {
//		return retData;
//	}
//
//	public void setRetData(Object retData) {
//		this.retData = retData;
//	}
//
//	public String getSign() {
//		return sign;
//	}
//
//	public void setSign(String sign) {
//		this.sign = sign;
//	}
//
//}
