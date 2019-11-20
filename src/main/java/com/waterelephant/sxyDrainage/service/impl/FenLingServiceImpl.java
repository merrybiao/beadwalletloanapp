//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.drainage.service.DrainageService;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingRequest;
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingResponse;
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingUserStatus;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.FenLingService;
//import com.waterelephant.sxyDrainage.utils.fenling.CodecUtils;
//import com.waterelephant.sxyDrainage.utils.fenling.FenLingConstant;
//import com.waterelephant.sxyDrainage.utils.fenling.ServerResponse;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.StringUtil;
//
//@Service
//public class FenLingServiceImpl implements FenLingService {
//	public static final String FENLING_CN = FenLingConstant.FENLING_CN;
//	public static final String SECRET = FenLingConstant.FENLING_SECRET;
//	private Logger logger = Logger.getLogger(FenLingServiceImpl.class);
//
//	/**
//	 * token失效时间,单位:分钟
//	 */
//	public final static int EXPIRED_TIME = 1 * 24 * 60 * 60;
//	public static final String TOKEN_PREFIX = "FenLingServiceImpl:";
//
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//
//	@Autowired
//	private CommonService commonService;
//
//	@Autowired
//	private DrainageService drainageService;
//	@Autowired
//	private BwBorrowerService bwborrowerService;
//
//	@Override
//	public FenLingResponse checkUser(Long sessionId, String user_name, String user_mobile, String id_card) {
//		logger.info(" 开始FenLingServiceImpl.checkUser()方法{user_name=" + user_name + ",user_mobile=" + user_mobile
//				+ ",id_card=" + id_card + "}");
//
//		FenLingResponse fenLingResponse = new FenLingResponse();
//		DrainageRsp checkUser = commonService.checkUser(sessionId, user_name, user_mobile, id_card);
//		// 获取公共接口返回的信息判断给三方返回的数据
//		String drainageRspCode = checkUser.getCode();
//		logger.info(JSONObject.toJSONString("checkUser检查用户接口返回code为：" + drainageRspCode));
//		// 返回数据
//		// 验证数据是否为空
//		if (drainageRspCode.equals("1002")) {
//			fenLingResponse.setErrorCode("111");
//			fenLingResponse.setErrMsg(checkUser.getMessage());
//			logger.info("系统异常,用户" + user_name + ":" + checkUser.getMessage());
//			return fenLingResponse;
//
//		}
//		// 验证是否符合申请要求
//		if (drainageRspCode.equals("2001") || drainageRspCode.equals("2002") || drainageRspCode.equals("2003")
//				|| drainageRspCode.equals("2004")) {
//			fenLingResponse.setErrorCode(drainageRspCode);
//			fenLingResponse.setErrMsg(checkUser.getMessage());
//			logger.info(user_name + "不可申请," + checkUser.getMessage());
//			return fenLingResponse;
//		}
//		fenLingResponse.setErrorCode("000");
//		fenLingResponse.setErrMsg("该用户允许通过");
//		// fenLingResponse.setBody(userCheckResp);
//		logger.info("检查用户接口返回数据为：" + JSONObject.toJSONString(fenLingResponse));
//
//		return fenLingResponse;
//	}
//
//	@Override
//	public FenLingResponse reportUserInfoStatus(Long sessionId, FenLingUserStatus fenLingUserStatus) {
//		logger.info(" 开始FenLingServiceImpl.UserInfoStatus()方法{userId=" + fenLingUserStatus.getUserId() + ",mblNo="
//				+ fenLingUserStatus.getMblNo() + ",orderNo=" + fenLingUserStatus.getOrderNo() + ",status="
//				+ fenLingUserStatus.getStatus() + "}");
//		FenLingResponse response = new FenLingResponse();
//		String requestTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		String jsonModel = JSONObject.toJSONString(fenLingUserStatus);
//		String aesData = CodecUtils.buildAESEncrypt(jsonModel, SECRET);
//        String dataStr = CodecUtils.sortParam(jsonModel);
//        
//		String signature = CodecUtils.getMD5(dataStr+requestTime).toLowerCase();
//		FenLingRequest fenLin = new FenLingRequest(FENLING_CN, requestTime, aesData, signature);
//		String data = HttpClientHelper.post(FenLingConstant.FENLING_URL + "/order/sxy", "utf-8",
//				JSONObject.toJSONString(fenLin));
//		FenLingResponse FenLingInfo = JSONObject.parseObject(data, FenLingResponse.class);
//		if (null != FenLingInfo) {
//			String errorCode = FenLingInfo.getErrorCode();
//			if (!StringUtils.isEmpty(errorCode) && "0".equals(errorCode)) {
//				response.setErrorCode("000");
//				response.setBody(FenLingInfo.getBody());
//				response.setErrMsg("给纷领发送回调接口成功");
//				logger.info("给纷领发送回调接口成功,返回code=" + errorCode);
//				return response;
//			}
//			response.setErrorCode(errorCode);
//			response.setErrMsg(FenLingInfo.getErrMsg());
//			logger.info("给纷领发送回调接口出现错误,返回code=" + errorCode + ",返回errmsg=" + FenLingInfo.getErrMsg());
//			return response;
//		}
//		response.setErrorCode("111");
//		response.setErrMsg("给纷领发送回调接口返回为空");
//		return response;
//	}
//
//	@Override
//	public FenLingResponse checkToken(Long sessionId, String visitToken) {
//		logger.info(" 开始FenLingServiceImpl.CheckToken()方法{visitToken=" + visitToken + "}");
//
//		FenLingResponse response = new FenLingResponse();
//
//		String requestTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());	
//		visitToken = CodecUtils.buildAESDecrypt(visitToken, SECRET);
//		Map<String,Object> tokenMap = new HashMap<String, Object>();
//		tokenMap.put("visitToken", visitToken);
//		String jsonToken = JSONObject.toJSONString(tokenMap);
//
//		String aesData = CodecUtils.buildAESEncrypt(jsonToken, SECRET);
//		
//	    String signature = CodecUtils.getMD5(visitToken+requestTime).toLowerCase();
//	    FenLingRequest fenLin = new FenLingRequest(FENLING_CN, requestTime, aesData,signature); 
//		
//	    String data = HttpClientHelper.post(FenLingConstant.FENLING_URL+"/sso/checkVToken", "utf-8", JSONObject.toJSONString(fenLin));
//        // 请求处理
//        if (com.beadwallet.service.utils.CommUtils.isNull(data) == true) {
//            response.setErrorCode("111");
//            response.setErrMsg("调用纷领效验Token接口返回为空");
//            logger.info("调用纷领效验Token接口返回为空,data="+data);
//            return response;
//        }
//        FenLingResponse FenLingInfo = JSONObject.parseObject(data, FenLingResponse.class);
//        if(null!=FenLingInfo){
//        	String errorCode = FenLingInfo.getErrorCode();
//        	if(!StringUtils.isEmpty(errorCode) && "0".equals(errorCode)){
//        		response.setErrorCode("000");
//        		response.setBody(FenLingInfo.getBody());
//                response.setErrMsg("调用纷领效验成功");
//                logger.info("调用纷领效验Token接口成功,返回code="+errorCode);
//                return response;
//        	}
//        	response.setErrorCode(errorCode);
//            response.setErrMsg(FenLingInfo.getErrMsg());
//            logger.info("调用纷领效验Token出现错误,返回code="+errorCode+",返回errmsg="+FenLingInfo.getErrMsg());
//        	return response;
//        }
//        response.setErrorCode("111");
//        response.setErrMsg("调用纷领效验Token接口返回为空");
//        return response;
//	}
//
//	
//	@Override
//	public boolean oldUserFilter(Long sessionId,String user_mobile) {
//		logger.info(" 开始FenLingServiceImpl.oldUserFilter()方法{user_mobile=" + user_mobile+"}");
//		// 判断新老用户,老用户true,新用户false
//		BwBorrower bw = bwborrowerService.oldUserFilter3(user_mobile);
//		if(null!=bw){
//			return true;
//		}
//		return false;
//
//	}
//
//	@Override
//	public ServerResponse<String> login(String phone) {
//		try {
//			BwBorrower bwBorrower = bwBorrowerService.selectLogin(phone, 1);
//			if (bwBorrower == null) {
//				return ServerResponse.error(-1, "用户不存在或被删除");
//			}
//			// state：1为启用，0为禁言
//			if (bwBorrower.getState() == 0) {
//				return ServerResponse.error(-2, "用户已禁用");
//			}
//			bwBorrower.setPassword(StringUtils.EMPTY);
//			return ServerResponse.success("登录成功", genToken("login", bwBorrower));
//		} catch (Exception e) {
//			logger.error("FenLingServiceImpl.login出现异常", e);
//			return ServerResponse.error(-3, "登录失败");
//		}
//	}
//
//	/**
//	 * 生成token返回对象
//	 *
//	 * @param bwBorrower
//	 *            需要缓存的对象
//	 * @return
//	 */
//	private String genToken(String midfix, BwBorrower bwBorrower) {
//		// redis的key
//		String key = TOKEN_PREFIX + midfix + ":" + bwBorrower.getPhone();
//
//		String token = RedisUtils.get(key);
//		if (StringUtil.isNotEmpty(token)) {
//			RedisUtils.del(key);
//		}
//		token = UUID.randomUUID().toString();
//		// 有效期7天
//		RedisUtils.setex(key, token, EXPIRED_TIME * 7);
//		return token;
//	}
//}
