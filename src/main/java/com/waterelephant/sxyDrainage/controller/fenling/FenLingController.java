///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller.fenling;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.gson.Gson;
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingResponse;
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingUserInfo;
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingUserStatus;
//import com.waterelephant.sxyDrainage.service.FenLingService;
//import com.waterelephant.sxyDrainage.utils.fenling.CodecUtils;
//import com.waterelephant.sxyDrainage.utils.fenling.FenLingConstant;
//import com.waterelephant.sxyDrainage.utils.fenling.ServerResponse;
//import com.waterelephant.third.service.ThirdCommonService;
//
///**
// * 
// * 
// * Module:
// * 
// * FenLingController.java
// * 
// * @author zy
// * @since JDK 1.8
// * @version 1.0
// * @description: <纷领>
// */
//@Controller
//public class FenLingController {
//
//	private Logger logger = Logger.getLogger(FenLingController.class);
//	public static final String CHANNEL_ID = FenLingConstant.CHANNEL_ID; 
//	public static final String LOGIN_URL = FenLingConstant.LOGIN_URL;
//	/**
//	 * 加载配置文件
//	 */
//	@Value("${REDIRECT_URL}")
//	private String REDIRECT_URL;
//
//	@Autowired
//	private FenLingService fenLingService;
//	/*@Autowired
//	private CommonService commonService;*/
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//
//	@RequestMapping("/sxy/fenling/checkuser.do")
//	// @ResponseBody
//	public String CheckUser(HttpServletRequest request, HttpServletResponse reponse) {
//		logger.info(" 开始FenLingController.checkUser()方法{visitToken=" + request.getParameter("visitToken")
//				+ ",redirectUrl=" + request.getParameter("redirectUrl") + "}");
//		String redirectUrl = null;
//		FenLingResponse fenLingResponse = null;
//		FenLingUserStatus fenLingUserStatus = null;
//		String strResult = null;
//		JSONObject json = new JSONObject();
//		try {
//			long sessionId = System.currentTimeMillis();
//			
//			String visitToken = request.getParameter("visitToken");
//			redirectUrl = request.getParameter("redirectUrl");
//			if(StringUtils.isEmpty(visitToken)){
//				logger.info(sessionId + " 调用FenLingController.checkUser()方法，返回结果：visitToken为空");
//				return "redirect:"+redirectUrl+"?message=纷领token为空";
//			}
//			if(StringUtils.isEmpty(redirectUrl)){
//				logger.info(sessionId + " 调用FenLingController.checkUser()方法，返回结果：redirectUrl为空");	
//				request.setAttribute("code", "111");
//				request.setAttribute("message", "纷领回调url为空");
//				return "fenling_error";
//			}
//			fenLingResponse = fenLingService.checkToken(sessionId,visitToken);
//			logger.info(sessionId + " 调用checkToken方法成功，返回结果code:"+fenLingResponse.getErrorCode());
//			String secret = FenLingConstant.FENLING_SECRET;
//			if("000".equals(fenLingResponse.getErrorCode())){	
//				String userJsonData = fenLingResponse.getBody();
//				userJsonData = CodecUtils.buildAESDecrypt(userJsonData, secret);
//				FenLingUserInfo fenLingUserInfo = new Gson().fromJson(userJsonData, FenLingUserInfo.class);
//				//FenLingUserInfo fenLingUserInfo = JSONObject.parseObject(userJsonData, FenLingUserInfo.class);
//			    String userName = fenLingUserInfo.getName();
//			    Long userId = fenLingUserInfo.getUserId();
//			    String mblNo = fenLingUserInfo.getMblNo();
//			    String idCard = fenLingUserInfo.getIdCard();
//			    Long orderNo = fenLingUserInfo.getOrderId();
//			    //判断新老用户
//				boolean oldUserBoolean = fenLingService.oldUserFilter(sessionId, mblNo);
//				if(oldUserBoolean){
//				//通知纷领老用户
//				fenLingUserStatus = new FenLingUserStatus(mblNo, orderNo,
//							Long.parseLong("1"), userId);
//				
//				fenLingService.reportUserInfoStatus(sessionId, fenLingUserStatus);
//				}
//				//用户验证是否可以贷款
//			    FenLingResponse checkUserRep = fenLingService.checkUser(sessionId, userName, mblNo,idCard);
//			    String checkCode = checkUserRep.getErrorCode();
//			    //0正常,1老用户,2年龄超限,3黑名单,4有订单用户,5被拒,如果都不是返回本身
//			    Long status = Long.parseLong(((((checkCode.equals("000")?"0":checkCode).equals("2001")?"3"
//			    		:checkCode).equals("2002")?"4":checkCode).equals("2003")?"5":checkCode).equals("2004")?"2":checkCode);
//				if("000".equals(checkCode)){	
//					if(!oldUserBoolean){
//						fenLingUserStatus = new FenLingUserStatus(mblNo, orderNo,
//							status, userId);
//							fenLingService.reportUserInfoStatus(sessionId, fenLingUserStatus);
//					}
//				//存储或跟新用户信息
//				//thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, mblNo, Integer.parseInt(CHANNEL_ID));
//				/*request.setAttribute("phone", mblNo);
//				request.setAttribute("code", CHANNEL_ID);
//				request.setAttribute("redirectUrl", redirectUrl);*/
//				logger.info("跳转3.0参数输出:LOGIN_URL="+LOGIN_URL+"Phone="+mblNo+"channelId="+CHANNEL_ID+"returnUrl="+redirectUrl);
//				return "redirect:"+LOGIN_URL+"?phone="+mblNo+"&channelId="+CHANNEL_ID+"&returnUrl="+redirectUrl;
//				}else if("111".equals(checkUserRep.getErrorCode())){
//					logger.info("checkUser接口异常:"+checkUserRep.getErrorCode()+",异常原因:"+checkUserRep.getErrMsg());
//				
//				return "redirect:"+LOGIN_URL+"?phone="+mblNo+"&channelId="+CHANNEL_ID+"&returnUrl="+redirectUrl;	
//				}else{
//					logger.info("调用checkUser用户返回code:"+checkUserRep.getErrorCode()+",不能贷款原因:"+checkUserRep.getErrMsg());
//					//年龄超限,通知纷领
//					if("2".equals(status+"")){
//					fenLingUserStatus = new FenLingUserStatus(mblNo, orderNo,
//							status, userId);
//							fenLingService.reportUserInfoStatus(sessionId, fenLingUserStatus);
//							json.put("code", "2");
//							json.put("msg", "年龄超限");
//							strResult = URLEncoder.encode(json.toString(), "UTF-8");
//							return "redirect:"+redirectUrl+"?result="+strResult;
//					}
//				}
//				}
//			
//			json.put("code", fenLingResponse.getErrorCode());
//			json.put("msg", fenLingResponse.getErrMsg());
//			strResult = URLEncoder.encode(json.toString(), "UTF-8");
//		
//			return "redirect:"+redirectUrl+"?result="+strResult;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;				
//		
//	}
//
//	/**
//	 * 登录。内部请求转发过来的。
//	 * @param request
//	 * @param response
//	 * @param mav
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping(value = "/fenling/login.do")
//	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
//		// 失败的页面
//		mav.setViewName("login_fail");
//		// 返回的地址
//		String redirectUrl = (String) request.getAttribute("redirectUrl");
//		mav.addObject("redirectUrl", redirectUrl);
//		Object rawCode = request.getAttribute("code");
//		String message = (String) request.getAttribute("message");
//
//		// 异常、非正常状态下
//		int code = 0;
//		try {
//			code = Integer.parseInt(rawCode.toString());
//			if (code == 0) {
//				String phone = (String) request.getAttribute("phone");
//				ServerResponse<String> serverResponse = fenLingService.login(phone);
//				// 登录成功重定向
//				if (serverResponse.isSuccess()) {
//					mav.setViewName("redirect:" + REDIRECT_URL + serverResponse.getData());
//					return mav;
//				} else {
//					mav.addObject("code", serverResponse.getCode());
//					mav.addObject("message", serverResponse.getMessage());
//				}
//			} else {
//				mav.addObject("code", rawCode);
//				mav.addObject("message", message);
//			}
//		} catch (NumberFormatException e) {
//			logger.error("FenLingController.login出现异常", e);
//			code = -10;
//			mav.addObject("code", code);
//			mav.addObject("message", "非法状态");
//		}
//		return mav;
//	}
//
//	/*@RequestMapping(value = "/fenling/login2.do")
//	public ModelAndView login2(ModelAndView mav) {
//		// 失败的页面
//		mav.setViewName("forward:/fenling/login.do");
//		mav.addObject("code", 0);
//		mav.addObject("message", "黑名单");
//		mav.addObject("phone", "18672935910");
//		mav.addObject("redirectUrl", REDIRECT_URL);
//		return mav;
//	}*/
//}
