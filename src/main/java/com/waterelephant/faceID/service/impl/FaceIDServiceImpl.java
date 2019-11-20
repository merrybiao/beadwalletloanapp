package com.waterelephant.faceID.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.faceID.FaceIDSDK;
import com.beadwallet.service.faceID.entity.OCRIDCard;
import com.beadwallet.service.faceID.entity.Verify;
import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.entity.BwExternalIdentityCard;
import com.waterelephant.faceID.entity.FaceidVerify;
import com.waterelephant.faceID.entity.IdentityCard;
import com.waterelephant.faceID.service.FaceIDService;
import com.waterelephant.faceID.service.FaceidVerifyService;
import com.waterelephant.faceID.service.IdentityCardService;
import com.waterelephant.service.BwExternalIdentityCardService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.zhengxin91.entity.ZxBlack;
import com.waterelephant.zhengxin91.service.ZxBlackService;

/**
 * FaceID 事物处理层
 * 
 * @author dengyan
 *
 */
@Service
public class FaceIDServiceImpl implements FaceIDService {

	// 定义获取日志常量的常量
	private Logger logger = LoggerFactory.getLogger(FaceIDServiceImpl.class);

	@Autowired
	private ZxBlackService zxBlackService;

	@Autowired
	private IdentityCardService identityCardService;
	
	@Autowired 
	private FaceidVerifyService faceidVerifyService;
	
	@Autowired
	private BwExternalIdentityCardService bwExternalIdentityCardService;

	private static double score = 0.7;

	/**
	 * 身份证识别正面
	 */
	@Override
	public AppResponseResult ocrIDCardFront(Map<String, String> params) {
		AppResponseResult result = new AppResponseResult();
		// 获取sessionId
		String sessionId = params.get("sessionId");
		logger.info(sessionId + ":开始执行APP的FaceIDServiceImpl的ocrIDCardFront方法，入参");
		// 实例化身份证信息
		OCRIDCard ocrIDCard = new OCRIDCard();
		// 定义一个map集合
		Map<String, String> map = new HashMap<String, String>();
		// 将用户id放入到map中
		map.put("user_id", params.get("user_id"));
		try {
			// 将params信息给service系统
			Response<Object> response = FaceIDSDK.ocrIdCard(params);
			// 获取service系统返回的身份证识别信息
			ocrIDCard = (OCRIDCard) response.getObj();
			if (ocrIDCard == null) {
				result.setCode("400");
				result.setMsg("未获取到身份证信息，请上传正确的身份证正面照片！");
				logger.info(sessionId + "从Service的FaceID的ocrIDCard获取信息成功！出参：" + JSONObject.toJSONString(result));
				return result;
			}
			String errorMsg = ocrIDCard.getError();
			String errorInfo = OCRIDCard.getErrorInfo(errorMsg);
			if (StringUtils.isEmpty(errorMsg)) {// 如果errorMsg为空说明上传的图片成功返回了图片识别后的信息
				double idPhoto = ocrIDCard.getLegality().getID_Photo();
				if (idPhoto >= score && !CommUtils.isNull(ocrIDCard.getId_card_number())
						&& !CommUtils.isNull(ocrIDCard.getName())) {
					if (ocrIDCard.getSide().equals("front")) {// 如果返回side是front说明上传的是身份证正面
						String birthday = ocrIDCard.getBirthday().getYear() + "-" + ocrIDCard.getBirthday().getMonth()
								+ "-" + ocrIDCard.getBirthday().getDay();
						// 将身份证信息封装到map集合中
						result.setCode(response.getRequestCode());
						result.setMsg("身份证正面上传成功！");
						map.put("address", ocrIDCard.getAddress());
						map.put("birthday", birthday);
						map.put("gender", ocrIDCard.getGender());
						map.put("id_card_number", ocrIDCard.getId_card_number());
						map.put("name", ocrIDCard.getName());
						map.put("race", ocrIDCard.getRace());
						map.put("score", CommUtils.toString(idPhoto));
						result.setResult(map);
						logger.info(
								sessionId + "从Service的FaceID的ocrIDCard获取信息成功！出参：" + JSONObject.toJSONString(result));
						return result;
					} else {
						result.setCode("204");
						result.setMsg("请上传本人身份证正面照片！");
						logger.info(sessionId + "上传的非身份证正面照片！");
						return result;
					}
				} else {
					result.setCode("204");
					result.setMsg("身份校验失败，可能身份证模糊或者为非法身份证！");
					logger.info(sessionId + "上传的非身份证正面照片，或者不是真实的身份证正面照片！");
					return result;
				}
			}
			result.setCode(response.getRequestCode());
			result.setMsg(errorInfo);
			logger.info(sessionId + "从Service的FaceID的ocrIDCard获取信息失败！错误信息为：" + errorInfo);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(sessionId + "App的FaceIDService的ocrIDCard出现异常，异常信息：", e);
		}

		return result;
	}

	/**
	 * 身份证识别反面
	 */
	@Override
	public AppResponseResult ocrIDCardBack(Map<String, String> params) {
		AppResponseResult result = new AppResponseResult();
		// 获取sessionId
		String sessionId = params.get("sessionId");
		logger.info(sessionId + ":开始执行APP的FaceIDServiceImpl的ocrIDCardBack方法");
		// 实例化身份证信息
		OCRIDCard ocrIDCard = new OCRIDCard();
		// 定义一个map集合
		Map<String, String> map = new HashMap<String, String>();
		// 将用户id放入到map中
		map.put("user_id", params.get("user_id"));
		try {
			// 将params存储的信息传递给service系统，并获取返回的response值
			Response<Object> response = FaceIDSDK.ocrIdCard(params);
			// 获取身份证信息
			ocrIDCard = (OCRIDCard) response.getObj();
			if (ocrIDCard == null) {
				result.setCode("400");
				result.setMsg("未获取到身份证信息，请上传正确的身份证正面照片！");
				logger.info(sessionId + "从Service的FaceID的ocrIDCardBack获取信息成功！出参：" + JSONObject.toJSONString(result));
				return result;
			}
			String errorMsg = ocrIDCard.getError();
			if (StringUtils.isEmpty(errorMsg)) {// 如果errorMsg为空说明上传的图片成功返回了图片识别后的信息
				double idPhoto = ocrIDCard.getLegality().getID_Photo();
				if (idPhoto >= score && !CommUtils.isNull(ocrIDCard.getValid_date())) {
					if ("back".equals(ocrIDCard.getSide())) {// 如果返回的side值是back说明上传的是身份证反面
						boolean bo = StringUtils.isEmpty(ocrIDCard.getIssued_by())
								|| StringUtils.isEmpty(ocrIDCard.getValid_date());
						if (bo == false) {
							// 将身份证反面信息封装到map中
							result.setCode(response.getRequestCode());
							result.setMsg("身份证反面上传成功！");
							map.put("user_id", params.get("user_id"));
							map.put("issued_by", ocrIDCard.getIssued_by());
							map.put("valid_date", ocrIDCard.getValid_date());
							map.put("score", StringUtil.toString(idPhoto));
							result.setResult(map);
							logger.info(sessionId + "从Service的FaceID的ocrIDCardBack获取信息成功！出参："
									+ JSONObject.toJSONString(result));
							return result;
						} else {
							result.setCode("204");
							result.setMsg("请上传本人身份证反面照片！");
							logger.info(sessionId + "上传的非身份证反面照片！");
							return result;
						}
					} else {
						result.setCode("204");
						result.setMsg("请上传本人身份证反面照片！");
						logger.info(sessionId + "上传的非身份证反面照片！");
						return result;
					}
				} else {
					result.setCode("204");
					result.setMsg("身份校验失败，可能身份证模糊或者为非法身份证！");
					logger.info(sessionId + "上传的非身份证反面照片！");
					return result;
				}
			} else {
				String errorInfo = OCRIDCard.getErrorInfo(errorMsg);
				result.setCode(response.getRequestCode());
				result.setMsg(errorInfo);
				logger.info(sessionId + "从Service的FaceID的ocrIDCardBack获取信息失败！错误信息为：" + errorInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(sessionId + "App的FaceIDService的ocrIDCardBack出现异常，异常信息：", e);
		}

		return result;
	}

	/**
	 * 身份核验
	 */

	@Override
	public Map<String, String> saveVerifyIDCard(Map<String, String> params) {
		// 获取sessionId
		String sessionId = params.get("sessionId");
		logger.info(sessionId + ":开始执行APP的FaceIDServiceImpl的verifyIDCard方法");
		// 实例化活体信息
		Verify verify = new Verify();
		// 定义一个map集合
		Map<String, String> map = new HashMap<String, String>();
		// 将用户id存进map集合中
		map.put("user_id", params.get("user_id"));
		try {
			// 将params集合中的信息传递给service系统，并获取返回的response信息
			Response<Object> response = FaceIDSDK.verifyIDCard(params);
			logger.info(sessionId + "从Service的FaceID的verifyIDCard获取信息成功！");
			// 将response中的活体信息存入到实例化的verfiy实体中
			verify = (Verify) response.getObj();
			if (verify == null) {
				map.put("code", "400");
				map.put("msg", "未获取到身份证信息，请上传正确的身份证正面照片！");
				logger.info(sessionId + "从Service的FaceID的verifyIDCard获取信息成功！出参：" + JSONObject.toJSONString(map));
				return map;
			}
			String errorMsg = verify.getError_message();
			if (StringUtils.isEmpty(errorMsg)) {// 如果errorMsg为空说明上传的图片成功返回了图片识别后的信息
				float confidence = verify.getResult_faceid().getConfidence();
				map.put("score", StringUtil.toString(confidence));
				float E_3 = verify.getResult_faceid().getThresholds().getE_3();
				if (confidence >= E_3) {
					map.put("code", response.getRequestCode());
					map.put("msg", "身份核验成功！");
				} else {
					map.put("code", "203");
					map.put("msg", "身份校验失败，可能身份证上的图像模糊或者为非法身份证！");
				}
				FaceidVerify faceidVerify = new FaceidVerify();
				faceidVerify.setBorrowerId(Long.parseLong(params.get("user_id")));
				faceidVerify.setOrderId(Long.parseLong(params.get("orderId")));
				faceidVerify.setCreateTime(new Date());
				// 保存活体检测记录
				faceidVerifyService.saveFaceidVerify(faceidVerify);
				logger.info(sessionId + "从Service的FaceID的verifyIDCard获取信息成功！出参：" + JSONObject.toJSONString(map));
				return map;
			}
			String errorInfo = Verify.getErrorInfo(errorMsg);
			map.put("code", response.getRequestCode());
			map.put("msg", errorInfo);

			logger.info(sessionId + "从Service的FaceID的verifyIDCard获取信息失败！错误信息为：" + errorInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(sessionId + "App的FaceIDService的verifyIDCard出现异常，异常信息：", e);
		}

		return map;
	}

	/**
	 * 活体检测
	 */
	@Override
	public AppResponseResult saveVerifyFace(Map<String, String> params, String delta) {
		AppResponseResult result = new AppResponseResult();
		// 获取sessionId
		String sessionId = params.get("sessionId");
		logger.info(sessionId + ":开始执行APP的FaceIDServiceImpl的verifyFace方法");
		// 实例化活体信息
		Verify verify = new Verify();
		// 定义一个map集合
		Map<String, String> map = new HashMap<String, String>();
		// 将用户id存入到map集合中
		map.put("user_id", params.get("user_id"));
		try {
			// 将params存储的信息传递给service系统，service系统通过faceID服务器判断后传回response信息
			params.put("delta", delta);
			Response<Object> response = FaceIDSDK.verifyFace(params);
			logger.info(sessionId + "从Service的FaceID的verifyFace获取信息成功！");
			// 将response中的object数据存储到verify实体中
			verify = (Verify) response.getObj();
			if (verify == null) {
				result.setCode("400");
				result.setMsg("未获取到人像信息，请重新上传活体图像！");
				logger.info(sessionId + "从Service的FaceID的verifyFace获取信息成功！出参：" + JSONObject.toJSONString(result));
				return result;
			}
			String errorMsg = verify.getError_message();
			if (StringUtils.isEmpty(errorMsg)) {// 如果errorMsg为空说明上传的图片成功返回了图片识别后的信息
				float confidence = verify.getResult_faceid().getConfidence();
				float E_3 = verify.getResult_faceid().getThresholds().getE_3();
				result.setResult(confidence);
				// 进行活体检测
				if (confidence >= E_3) {
					result.setCode(response.getRequestCode());
					result.setMsg("活体检验成功！");
				} else {
					result.setCode("203");
					result.setMsg("身份校验失败，可能图片模糊或者非本人图片！");
				}
				FaceidVerify faceidVerify = new FaceidVerify();
				faceidVerify.setBorrowerId(Long.parseLong(params.get("user_id")));
				faceidVerify.setOrderId(Long.parseLong(params.get("orderId")));
				faceidVerify.setCreateTime(new Date());
				// 保存活体检测记录
				faceidVerifyService.saveFaceidVerify(faceidVerify);
				logger.info(sessionId + "从Service的FaceID的verifyFace获取信息成功！出参：" + JSONObject.toJSONString(result));
				return result;
			}
			String errorInfo = Verify.getErrorInfo(errorMsg);
			result.setCode(response.getRequestCode());
			result.setMsg(errorInfo);
			logger.info(sessionId + "从Service的FaceID的verifyFace获取信息失败！错误信息为：" + errorInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(sessionId + "App的FaceIDService的verifyFace出现异常，异常信息：", e);
		}

		return result;
	}

	// /**
	// * faceID - H5 - 获取朗读随机字符
	// *
	// * @param params
	// * @return
	// */
	// @Override
	// public AppResponseResult getRandomNumber(Map<String, String> params) {
	// AppResponseResult result = new AppResponseResult();
	// String sessionId = params.get("sessionId");
	// logger.info(sessionId
	// + ":开始执行APP的FaceIDServiceImpl的getRandomNumber方法，入参："
	// + JSONObject.toJSONString(params));
	// try {
	// Map<String, String> map = new HashMap<String, String>();
	// Response<Object> response = FaceIDSDK.getRandomNumber(params);
	// Random_number_h5 random_number = new Random_number_h5();
	// random_number = (Random_number_h5) response.getObj();
	// if (!CommUtils.isNull(random_number)) {
	// String errorMsg = random_number.getError_message();
	// if (StringUtils.isNullOrEmpty(errorMsg)) {
	// map.put("biz_no", random_number.getBiz_no());
	// map.put("token_random_number",
	// random_number.getToken_random_number());
	// map.put("random_number", random_number.getRandom_number());
	// result.setCode(response.getRequestCode());
	// result.setMsg("获取朗读随机字符成功！");
	// result.setResult(map);
	// } else {
	// result.setCode(response.getRequestCode());
	// String errorInfo = random_number.getErrorInfo(errorMsg);
	// result.setMsg(errorInfo);
	// }
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// result.setCode("900");
	// result.setMsg("系统异常！");
	// logger.info(sessionId
	// + "App的FaceIDService的getRandomNumber()方法出现异常，异常信息："
	// + e.getMessage());
	// }
	// logger.info(sessionId + ":结束Service的FaceID的getRandomNumber()方法！出参："
	// + JSONObject.toJSONString(result));
	// return result;
	// }

	// /**
	// * faceID - H5 - 活体验证
	// *
	// * @param params
	// * @return
	// */
	//
	// @Override
	// public Map<String, String> validateVideo(Map<String, String> paramMap) {
	// Map<String, String> map = new HashMap<String, String>();
	// String sessionId = paramMap.get("sessionId");
	// logger.info(sessionId
	// + ":开始执行APP的FaceIDServiceImpl的validateVideo()方法，入参："
	// + JSONObject.toJSONString(paramMap));
	// try {
	// map.put("borrowerId", paramMap.get("borrowId"));
	// Response<Object> response = FaceIDSDK.validateVideo(paramMap);
	// Video_h5 video = (Video_h5) response.getObj();
	// map.put("code", response.getRequestCode());
	// map.put("msg", response.getRequestMsg());
	// if (!CommUtils.isNull(video)) {
	// String errorMsg = video.getError_message();
	// if (StringUtils.isNullOrEmpty(errorMsg)) {
	// map.put("token_video", video.getToken_video());
	// map.put("biz_no", video.getBiz_no());
	// } else {
	// map.put("code", response.getRequestCode());
	// String errorInfo = video.getErrorInfo(errorMsg);
	// map.put("msg", errorInfo);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// map.put("code", "900");
	// map.put("msg", "系统异常！");
	// logger.info(sessionId
	// + "App的FaceIDService的validateVideo()方法出现异常，异常信息："
	// + e.getMessage());
	// }
	// logger.info(sessionId + ":结束Service的FaceID的validateVideo()方法！出参："
	// + JSONObject.toJSONString(map));
	// return map;
	// }

	// /**
	// * faceID - H5 - 活体结果比对
	// *
	// * @param params
	// * @return
	// */
	//
	// @Override
	// public AppResponseResult verify(Map<String, String> paramMap) {
	// AppResponseResult result = new AppResponseResult();
	// String sessionId = paramMap.get("sessionId");
	// logger.info(sessionId + ":开始执行APP的FaceIDServiceImpl的verify()方法，入参："
	// + JSONObject.toJSONString(paramMap));
	// try {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("borrowerId", paramMap.get("borrowerId"));
	// Response<Object> response = FaceIDSDK.verify(paramMap);
	// Verify_h5 verify = (Verify_h5) response.getObj();
	// if (!CommUtils.isNull(verify)) {
	// String errorMsg = verify.getError_message();
	// if (StringUtils.isEmpty(errorMsg)) {
	// boolean bo1 = false;
	// boolean bo2 = false;
	// boolean bo3 = false;
	// if (verify.getLiveness() != null) {
	// String procedure_validation = verify.getLiveness()
	// .getProcedure_validation();
	// String face_genuineness = verify.getLiveness()
	// .getFace_genuineness();
	// bo1 = "PASSED".equals(procedure_validation);
	// bo2 = "PASSED".equals(face_genuineness);
	// }
	// if (!bo1) {
	// result.setCode("203");
	// result.setMsg("验证流程不通过，正侧脸照不一致");
	// logger.info(sessionId
	// + ":结束Service的FaceID的verify()方法！出参："
	// + JSONObject.toJSONString(result));
	// return result;
	// }
	// if (!bo2) {
	// result.setCode("203");
	// result.setMsg("照片中的脸不是真实人脸！");
	// logger.info(sessionId
	// + ":结束Service的FaceID的verify()方法！出参："
	// + JSONObject.toJSONString(result));
	// return result;
	// }
	// if(verify.getResult_faceid() != null){
	// Float confidence = verify.getResult_faceid()
	// .getConfidence();
	// Float E_3 = verify.getResult_faceid().getThresholds()
	// .getE_3();
	// bo3 = confidence < E_3;
	// }
	// if (bo3) {
	// result.setCode("203");
	// result.setMsg("活体验证失败，数据源比对不通过！");
	// logger.info(sessionId
	// + ":结束Service的FaceID的verify()方法！出参："
	// + JSONObject.toJSONString(result));
	// return result;
	// }
	//
	// result.setCode(response.getRequestCode());
	// result.setMsg("活体比对完成！");
	// result.setResult(verify);
	// } else {
	// result.setCode(response.getRequestCode());
	// String errorInfo = verify.getErrorInfo(errorMsg);
	// result.setMsg(errorInfo);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// result.setCode("900");
	// result.setMsg("系统异常！");
	// logger.error(sessionId + "App的FaceIDService的verify()方法出现异常，异常信息："
	// + e.getMessage());
	// }
	// logger.info(sessionId + ":结束Service的FaceID的verify()方法！出参："
	// + JSONObject.toJSONString(result));
	// return result;
	// }
	//
	// /**
	// * FaceID - H5 - 正脸验证
	// */
	// public AppResponseResult validateFrontFace(Map<String, String> paramMap){
	// AppResponseResult result = new AppResponseResult();
	// // 第一步：取参
	// String sessionId = paramMap.get("sessionId");
	// logger.info(sessionId
	// + ":开始执行APP的FaceIDServiceImpl的validateFrontFace方法，入参："
	// + JSONObject.toJSONString(paramMap));
	// try{
	//
	// // 第二步：获取Face++返回信息
	// Response<Object> response = FaceIDSDK.validateFrontFace(paramMap);
	// ValidateFrontFace frontFace = (ValidateFrontFace)response.getObj();
	// if (frontFace == null) {
	// result.setCode("400");
	// result.setMsg("未获取到身份证信息，请上传正确的身份证正面照片！");
	// logger.info(sessionId + "从Service的FaceID的validateFrontFace获取信息成功！出参："
	// + JSONObject.toJSONString(result));
	// return result;
	// }
	// String errorMsg = frontFace.getError_message();
	// String errorInfo = ValidateFrontFace.getErrorInfo(errorMsg);
	//
	// // 第三步：验证是否存在错误信息
	// if (StringUtils.isEmpty(errorMsg)) {// 如果errorMsg为空说明上传的图片成功返回了图片识别后的信息
	// result.setCode(response.getRequestCode());
	// result.setMsg(response.getRequestMsg());
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("biz_no", frontFace.getBiz_no());
	// params.put("token_front_face", frontFace.getToken_front_face());
	// result.setResult(params);
	// logger.info(sessionId
	// + "从Service的FaceID的validateFrontFace获取信息成功！出参："
	// + JSONObject.toJSONString(result));
	// }else{ //errorMsg不为空，说明存在错误信息
	// result.setCode(response.getRequestCode());
	// result.setMsg(errorInfo);
	// logger.info(sessionId + "从Service的FaceID的validateFrontFace获取信息失败！错误信息为："
	// + errorInfo);
	// }
	// }catch (Exception e) {
	// e.printStackTrace();
	// result.setCode("900");
	// result.setMsg(e.getMessage());
	// logger.info(sessionId + "App的FaceIDService的validateFrontFace出现异常，异常信息："
	// + e.getMessage());
	// }
	//
	// return result;
	// }
	//
	// /**
	// * faceID - H5 - 侧脸验证
	// */
	// public AppResponseResult validateSideFace(Map<String, String> paramMap){
	// AppResponseResult result = new AppResponseResult();
	// // 第一步：取参
	// String sessionId = paramMap.get("sessionId");
	// logger.info(sessionId
	// + ":开始执行APP的FaceIDServiceImpl的validateSideFace方法，入参："
	// + JSONObject.toJSONString(paramMap));
	// try{
	//
	// // 第二步：获取Face++返回信息
	// Response<Object> response = FaceIDSDK.validateSideFace(paramMap);
	// ValidateSideFace sideFace = (ValidateSideFace)response.getObj();
	// if (sideFace == null) {
	// result.setCode("400");
	// result.setMsg("未获取到侧脸信息！");
	// logger.info(sessionId + "从Service的FaceID的validateSideFace获取信息成功！出参："
	// + JSONObject.toJSONString(result));
	// return result;
	// }
	// String errorMsg = sideFace.getError_message();
	// String errorInfo = ValidateSideFace.getErrorInfo(errorMsg);
	//
	// // 第三步：验证是否存在错误信息
	// if (StringUtils.isEmpty(errorMsg)) {// 如果errorMsg为空说明上传的图片成功返回了图片识别后的信息
	// result.setCode(response.getRequestCode());
	// result.setMsg(response.getRequestMsg());
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("biz_no", sideFace.getBiz_no());
	// params.put("token_side_face", sideFace.getToken_side_face());
	// result.setResult(params);
	// logger.info(sessionId
	// + "从Service的FaceID的validateSideFace获取信息成功！出参："
	// + JSONObject.toJSONString(result));
	// }else{ //errorMsg不为空，说明存在错误信息
	// result.setCode(response.getRequestCode());
	// result.setMsg(errorInfo);
	// logger.info(sessionId + "从Service的FaceID的validateSideFace获取信息失败！错误信息为："
	// + errorInfo);
	// }
	// }catch (Exception e) {
	// e.printStackTrace();
	// result.setCode("900");
	// result.setMsg(e.getMessage());
	// logger.info(sessionId + "App的FaceIDService的validateSideFace出现异常，异常信息："
	// + e.getMessage());
	// }
	//
	// return result;
	// }

	/**
	 * FaceID 征信黑名单
	 */
	@Override
	public void saveZxBlack(Map<String, String> params) {
		String sessionId = params.get("sessionId");
		logger.info(sessionId + ":开始执行APP的FaceIDServiceImpl的saveZxBlack()方法，入参：" + JSONObject.toJSONString(params));
		try {
			// 根据borrowerId和orderId查询表中是否存在审核信息
			long borrowerId = Long.parseLong(params.get("borrowerId"));
			long orderId = Long.parseLong(params.get("orderId"));
			// logger.info(":开始执行APP的FaceIDServiceImpl的saveZxBlack()方法，入参：0000000000");
			ZxBlack zxBlack = zxBlackService.queryZxBlack(borrowerId, orderId, 21,
					StringUtil.toInteger(params.get("sourceItem")));
			// logger.info(":开始执行APP的FaceIDServiceImpl的saveZxBlack()方法，入参：11111111111111111");
			if (CommUtils.isNull(zxBlack)) {
				// logger.info(":开始执行APP的FaceIDServiceImpl的saveZxBlack()方法，入参：22222222222222");
				zxBlack = new ZxBlack();
				zxBlack.setBorrowId(Long.parseLong(params.get("borrowerId")));
				zxBlack.setOrderId(Long.parseLong(params.get("orderId")));
				zxBlack.setName(params.get("idcard_name"));
				zxBlack.setIdCard(params.get("idcard_number"));
				zxBlack.setSourceItem(StringUtil.toInteger(params.get("sourceItem")));
				zxBlack.setRemark("需要人工审核的身份认证");
				zxBlack.setCreateTime(new Date());
				zxBlack.setUpdateTime(new Date());
				zxBlack.setSource(21);
				zxBlack.setType(1);
				zxBlack.setRejectType(2);
				zxBlack.setScore(new BigDecimal(params.get("score")).doubleValue());
				zxBlack.setRejectInfo(params.get("msg"));
				boolean bo2 = zxBlackService.saveZxBlack(zxBlack);
				if (!bo2) {
					logger.info(sessionId + ":将face++认证信息保存ZxBlack失败！");
				}
			} else {
				// logger.info(":开始执行APP的FaceIDServiceImpl的saveZxBlack()方法，入参：333333333333333333333");
				zxBlack.setBorrowId(Long.parseLong(params.get("borrowerId")));
				zxBlack.setOrderId(Long.parseLong(params.get("orderId")));
				zxBlack.setName(params.get("idcard_name"));
				zxBlack.setIdCard(params.get("idcard_number"));
				zxBlack.setSourceItem(StringUtil.toInteger(params.get("sourceItem")));
				zxBlack.setRemark("需要人工审核的身份认证");
				zxBlack.setUpdateTime(new Date());
				zxBlack.setSource(21);
				zxBlack.setType(1);
				zxBlack.setRejectType(2);
				zxBlack.setScore(new BigDecimal(params.get("score")).doubleValue());
				zxBlack.setRejectInfo(params.get("msg"));
				boolean bo3 = zxBlackService.updateZxBlackh(zxBlack);
				if (!bo3) {
					logger.info(sessionId + ":将face++认证信息保存ZxBlack失败！");
				}
			}
		} catch (Exception e) {
			logger.error("执行APP的FaceIDServiceImpl的saveZxBlack()方法异常:", e);
		}
		// logger.info(":开始执行APP的FaceIDServiceImpl的saveZxBlack()方法，入参：4444444444444444");
		logger.info(sessionId + ":结束执行APP的FaceIDServiceImpl的saveZxBlack()方法");
	}

	/**
	 * 保存身份证信息
	 */
	@Override
	public void saveIdCard(Map<String, String> paramMap) {
		String sessionId = paramMap.get("sessionId");
		logger.info(sessionId + ":开始执行APP的FaceIDServiceImpl的saveIdCard()方法，入参：" + JSONObject.toJSONString(paramMap));
		try {
			// 第一步：判断用户是否存在
			String borrowerId = paramMap.get("user_id");
			IdentityCard identityCard = identityCardService.queryIdentityCard(Integer.parseInt(borrowerId));
			if (identityCard == null) {
				// 第二步：实例化实体
				identityCard = new IdentityCard();
				identityCard.setBorrowerId(Integer.parseInt(borrowerId));
				identityCard.setAddress(paramMap.get("address"));
				identityCard.setBirthday(paramMap.get("birthday"));
				identityCard.setCreateTime(new Date());
				identityCard.setGender(paramMap.get("gender"));
				identityCard.setIdCardNumber(paramMap.get("id_card_number"));
				identityCard.setIssuedBy(paramMap.get("issued_by"));
				identityCard.setName(paramMap.get("name"));
				identityCard.setRace(paramMap.get("race"));
				identityCard.setValidDate(paramMap.get("valid_date"));
				identityCard.setUpdateTime(new Date());
				// 第三步：保存数据
				identityCardService.saveIdentityCard(identityCard);
			} else {
				// 第二步：实例化实体
				identityCard.setUpdateTime(new Date());
				if (StringUtils.isEmpty(paramMap.get("id_card_number")) == false) {
					identityCard.setAddress(paramMap.get("address"));
					identityCard.setBirthday(paramMap.get("birthday"));
					identityCard.setGender(paramMap.get("gender"));
					identityCard.setIdCardNumber(paramMap.get("id_card_number"));
					identityCard.setName(paramMap.get("name"));
					identityCard.setRace(paramMap.get("race"));
					identityCardService.updateIdentityCard(identityCard);
				}
				if (StringUtils.isEmpty(paramMap.get("issued_by")) == false) {
					identityCard.setIssuedBy(paramMap.get("issued_by"));
					identityCard.setValidDate(paramMap.get("valid_date"));
					identityCardService.updateIdentityCard(identityCard);
				}
				// 第三步：更新数据
			}
			logger.info(sessionId + ":结束执行APP的FaceIDServiceImpl的saveIdCard()方法");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(sessionId + ":执行APP的FaceIDServiceImpl的saveIdCard()方法异常，异常结果：", e);
		}

	}
	
	/**
	 * 身份证OCR识别
	 * @param sessionId 会话Id 根据当前时间生成
	 * @param userId 用户唯一Id
	 * @param imgBase64 身份证照Base64字符串
	 * @param side 身份证正面/反面
	 * @return OCR识别结果
	 * @throws Exception
	 * @author dinglinhao
	 * @date 2018年7月13日14:38:21
	 */
	public OCRIDCard ocrIdcard(String sessionId,String userId,String imgBase64,String side) throws Exception {
		
		Assert.hasText(sessionId, "缺少参数[sessionId]不能为空~");
		Assert.hasText(imgBase64, "缺少参数[imgBase64]不能为空~");
		Assert.hasText(side, "缺少参数[side]不能为空~");
		
		Map<String,String> paramMap = new HashMap<>();
		paramMap.put("sessionId", sessionId);
		paramMap.put("imagePath", imgBase64);
		paramMap.put("user_id", StringUtils.isEmpty(userId)?UUID.randomUUID().toString():userId);
		//paramMap.put("", value);
		Response<Object> ocrResult = null;
		try {
			ocrResult = FaceIDSDK.ocrIdCard(paramMap);
			logger.info("{}调用FaceIDSDK.ocrIdCard()方法返回结果：{}",sessionId,JSON.toJSONString(ocrResult));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}调用FaceIDSDK.ocrIdCard()方法异常{}",sessionId,e.getMessage());
			throw new Exception("接口异常~");
		}
		//返回结果或对象为空
		if(null == ocrResult || null == ocrResult.getObj()) {
			throw new IllegalArgumentException("未获取到身份证信息，请上传正确的身份证"+(side.equals("front")?"正面":"反面")+"照片！");
		}
		
		OCRIDCard ocrIdcard = (OCRIDCard) ocrResult.getObj();
		//有错误信息返回
		if(StringUtils.isNotEmpty(ocrIdcard.getError())) {
			String errorInfo = OCRIDCard.getErrorInfo(ocrIdcard.getError());
			throw new IllegalArgumentException(errorInfo);
		}
		
		//解析side和上传的side不匹配
		if(!side.equals(ocrIdcard.getSide())) {
			logger.info("{}上传的非身份证{}照片！",sessionId,side.equals("front")?"正面":"反面");
			throw new IllegalArgumentException("请上传本人身份证"+(side.equals("front")?"正面":"反面")+"照片！");
		}
		
		//idPhoto小于0.7 或身份证号或姓名为空
		if (null == ocrIdcard.getLegality() 
				|| ocrIdcard.getLegality().getID_Photo() < score ) {
			throw new IllegalArgumentException("身份校验失败，可能身份证模糊或者为非法身份证！");
		}
		
		//解析
		if("front".equals(ocrIdcard.getSide())) {
			
			if (CommUtils.isNull(ocrIdcard.getId_card_number())
					|| CommUtils.isNull(ocrIdcard.getName())) {
				logger.info("{}上传的非身份证正面照片，或者不是真实的身份证正面照片！",sessionId);
				throw new IllegalArgumentException("身份校验失败，识别身份证姓名或身份证号为空~");
			}
			
//			Birthday ocrBirthday = ocrIdcard.getBirthday();
//			String birthday = null;
//			if(null != ocrBirthday) {
//				birthday = ocrBirthday.getYear() + "-" + ocrBirthday.getMonth() + "-" + ocrBirthday.getDay();
//			}
//			resultMap.put("address", ocrIdcard.getAddress());
//			resultMap.put("birthday", birthday);
//			resultMap.put("gender", ocrIdcard.getGender());
//			resultMap.put("id_card_number", ocrIdcard.getId_card_number());
//			resultMap.put("name", ocrIdcard.getName());
//			resultMap.put("race", ocrIdcard.getRace());
//			resultMap.put("score", String.valueOf(ocrIdcard.getLegality().getID_Photo()));
		} else if("back".equals(ocrIdcard.getSide())) {

			if (CommUtils.isNull(ocrIdcard.getIssued_by())
					|| CommUtils.isNull(ocrIdcard.getValid_date())) {
				logger.info("{}上传的非身份证反面照片，或者不是真实的身份证反面照片！",sessionId);
				throw new IllegalArgumentException("身份校验失败，识别身份证签发机关或有效期为空~");
			}
		}
		
		return ocrIdcard;
	}
	
	/**
	 * 活体检测/人脸识别
	 * @param sessionId 会话Id 根据当前时间生成
	 * @param userId 用户唯一Id
	 * @param idcardName 姓名
	 * @param idcardNum 身份证号
	 * @param imgBase64 照片Base64字符串
	 * @param delta 活体校验码
	 * @return Map<String,Object> resultMap
	 * @throws Exception
	 * @author dinglinhao
	 * @date 2018年7月13日14:42:49
	 */
	public Verify verifyFace(String sessionId,String userId,String idcardName,String idcardNum,String imgBase64,String delta) throws Exception {
		
		Assert.hasText("sessionId", "缺少参数[sessionId]不能为空~");
		Assert.hasText("idCardName", "缺少参数[idCardName]不能为空~");
		Assert.hasText("idcardNum", "缺少参数[idcardNum]不能为空~");
		Assert.hasText("imgBase64", "缺少参数[imgBase64]不能为空~");
		Assert.hasText("delta", "缺少参数[delta]不能为空~");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user_id", StringUtils.isEmpty(userId)? UUID.randomUUID().toString() : userId);
		paramMap.put("sessionId", sessionId);
		paramMap.put("idcard_name", idcardName);
		paramMap.put("idcard_number", idcardNum);
		paramMap.put("imagePath", imgBase64);
		paramMap.put("delta", delta);
		
//		Map<String,String> resultMap = new HashMap<>();
		Response<Object> faceResult = null;
		
		try {
			faceResult = FaceIDSDK.verifyFace(paramMap);
			logger.info("{}调用FaceIDSDK.verifyFace()方法返回结果：{}",sessionId,JSON.toJSONString(faceResult));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}调用FaceIDSDK.verifyFace()方法异常{}",sessionId,e.getMessage());
			throw new Exception("接口异常~");
		}
		//返回结果或对象为空
		if(null == faceResult || null == faceResult.getObj()) {
			throw new Exception("未检测到活体信息，请重试~");
		}
		Verify verify = (Verify) faceResult.getObj();
		//有错误信息返回
		if(StringUtils.isNotEmpty(verify.getError_message())) {
			String errorInfo = Verify.getErrorInfo(verify.getError_message());
			throw new Exception(errorInfo);
		}
		
//		Result_faceid resultFaceId = verify.getResult_faceid();
//		if(null == resultFaceId) {
//			throw new Exception("活体检测失败~返回结果为空~");
//		}
//		float confidence = resultFaceId.getConfidence();
//		float e3 = resultFaceId.getThresholds().getE_3();
//			
//		// 进行活体检测
//		if (confidence < e3) {
//			throw new Exception("身份校验失败，可能图片模糊或者非本人图片！");
//		} 
//		resultMap.put("confidence", String.valueOf(confidence));
//		
//		return resultMap;
		return verify;
	}
	
	/**
	 * 验证身份证
	 * @param userId	用户唯一Id
	 * @param sessionId 会话Id
	 * @param imgBase64 图片base4字符串
	 * @param idcardName 姓名
	 * @param idcardNum身份证号
	 * @return Map<String,Objct> resultMap
	 * @throws Exception
	 * @author dinglinhao
	 * @date 2018年7月13日15:46:54
	 */
	public Map<String,String> verifyIdcard(String userId,String sessionId,String imgBase64,String delta,String idcardName,String idcardNum) throws Exception {
		
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user_id", userId);
		paramMap.put("sessionId", sessionId);
		paramMap.put("imagePath", imgBase64);
		paramMap.put("delta", delta);
		paramMap.put("idcard_name", idcardName);
		paramMap.put("idcard_number", idcardNum);
		
		Response<Object> verifyResult = null;
		
		try {
			verifyResult = FaceIDSDK.verifyIDCard(paramMap);
			logger.info("{}调用FaceIDSDK.verifyIDCard()方法返回结果：{}",sessionId,JSON.toJSONString(verifyResult));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}调用FaceIDSDK.verifyIDCard()方法异常{}",sessionId,e.getMessage());
			throw new Exception("接口异常~");
		}
		//返回结果或对象为空
		if(null == verifyResult || null == verifyResult.getObj()) {
			throw new Exception("未获取到身份证信息，请上传正确的身份证照片！");
		}
		Verify verify = (Verify) verifyResult.getObj();
		//有错误信息返回
		if(StringUtils.isNotEmpty(verify.getError_message())) {
			String errorInfo = Verify.getErrorInfo(verify.getError_message());
			throw new Exception(errorInfo);
		}
		
		Map<String,String> resultMap = new HashMap<>();
		
		return resultMap;
	}
	/**
	 * 保存身份证信息
	 */
	@Override
	public boolean saveIdcardInfo(Map<String, String> paramMap) throws Exception {
		
		String idcardNumber = paramMap.get("id_card_number");
		Assert.hasText(idcardNumber, "身份证号码不能为空~");
		
		boolean flag = false;
		try {
			// 第一步：判断用户是否存在（根据身份证号）
			BwExternalIdentityCard identityCard = bwExternalIdentityCardService.queryIdentityCardByIdcardNo(idcardNumber);
			if (null == identityCard && "front".equals(paramMap.get("side"))) {
				// 第二步：实例化身份证正面信息
				identityCard = new BwExternalIdentityCard();
				identityCard.setIdCardNumber(idcardNumber);
				identityCard.setAddress(paramMap.get("address"));
				identityCard.setBirthday(paramMap.get("birthday"));
				identityCard.setGender(paramMap.get("gender"));
				identityCard.setName(paramMap.get("name"));
				identityCard.setRace(paramMap.get("race"));
//				identityCard.setIssuedBy(paramMap.get("issued_by"));
//				identityCard.setValidDate(paramMap.get("valid_date"));
				identityCard.setCreateTime(new Date());
				identityCard.setUpdateTime(new Date());
				identityCard.setVerifySource(1);//faceid 1
				
				// 第三步：保存身份证正面信息
				flag = bwExternalIdentityCardService.saveBwIdentityCard(identityCard)>0;
			} else if(null != identityCard) {
				// 第二步：更新身份证正面信息
				if ("front".equals(paramMap.get("side"))) {
					identityCard.setIdCardNumber(paramMap.get("id_card_number"));//身份证号
					identityCard.setAddress(paramMap.get("address"));//地址
					identityCard.setBirthday(paramMap.get("birthday"));//生日
					identityCard.setGender(paramMap.get("gender"));//性别
					identityCard.setName(paramMap.get("name"));//姓名
					identityCard.setRace(paramMap.get("race"));//名族
					identityCard.setUpdateTime(new Date());
					identityCard.setVerifySource(1);//faceid 1
					flag = bwExternalIdentityCardService.updateBwIdentityCard(identityCard)>0;
				}else if("back".equals(paramMap.get("side"))) {
					// 第三步：更新身份证反面信息
					identityCard.setIssuedBy(paramMap.get("issued_by"));
					identityCard.setValidDate(paramMap.get("valid_date"));
					identityCard.setUpdateTime(new Date());
					flag = bwExternalIdentityCardService.updateBwIdentityCard(identityCard)>0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存身份证信息异常,入参信息：{}，异常信息：{}",JSON.toJSONString(paramMap),e.getMessage());
		}
		return flag;
	}

	@Override
	public boolean saveVerifyFaceInfo(String idcardName, String idcardNum, float confidence) {
		// TODO 对外接口调用活体检测不做保存
		return true;
	}
	
}
