///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingResponse;
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingUserStatus;
//import com.waterelephant.sxyDrainage.utils.fenling.ServerResponse;
//
///**
// * 
// * 
// * Module:
// * 
// * FenLingService.java
// * 
// * @author zy
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//
//public interface FenLingService {
//	/**
//	 * 3.1用户过滤接口
//	 * 
//	 * @param sessionId
//	 * @param user_name
//	 * @param user_mobile
//	 * @param id_card
//	 * @return
//	 */
//	public FenLingResponse checkUser(Long sessionId, String user_name, String user_mobile, String id_card);
//
//	/**
//	 * 3.2返回给纷领的用户状态回调
//	 * 
//	 * @param sessionId
//	 * @param fenLingUserStatus
//	 * @return
//	 */
//	public FenLingResponse reportUserInfoStatus(Long sessionId, FenLingUserStatus fenLingUserStatus);
//
//	/**
//	 * 3.3去纷领效验token是否正确
//	 * 
//	 * @param sessionId
//	 * @param visitToken
//	 * @return
//	 */
//	public FenLingResponse checkToken(Long sessionId, String visitToken);
//	
//	/**
//	 * 3.4判断新老用户
//	 * @return
//	 * @param user_name
//	 * @param user_mobile
//	 * @param id_card
//	 */
//	public boolean oldUserFilter(Long sessionId,String user_mobile);
//
//	/**
//	 * 登录
//	 * @param phone	手机号
//	 * @return
//	 */
//	ServerResponse<String> login(String phone);
//}
