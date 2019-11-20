package com.waterelephant.linkface.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.linkface.LinkfaceSDK;
import com.beadwallet.service.linkface.entity.Info;
import com.beadwallet.service.linkface.entity.LivenessIdnumberVerification;
import com.beadwallet.service.linkface.entity.OcrIdcard;
import com.beadwallet.service.linkface.entity.Validity;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.faceID.entity.IdentityCard;
import com.waterelephant.faceID.service.IdentityCardService;
import com.waterelephant.linkface.entity.LinkfaceVerify;
import com.waterelephant.linkface.entity.ResultInfo;
import com.waterelephant.linkface.service.LinkfaceService;
import com.waterelephant.linkface.service.LinkfaceVerifyService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwVerifySourceService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.zhengxin91.entity.ZxBlack;
import com.waterelephant.zhengxin91.service.ZxBlackService;

/**
 * 商汤人脸识别（code0088）
 * 
 * 
 * Module:
 * 
 * LinkfaceServiceImpl.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <商汤人脸识别>
 */
@Service
public class LinkfaceServiceImpl implements LinkfaceService {
	private Logger logger = LoggerFactory.getLogger(LinkfaceServiceImpl.class);

	@Autowired
	private IdentityCardService identityCardService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private LinkfaceVerifyService linkfaceVerifyService;
	@Autowired
	private ZxBlackService zxBlackService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private BwVerifySourceService bwVerifySourceService;

	/**
	 * 商汤人脸识别 - 身份证 - 正面
	 * 
	 * @see com.waterelephant.linkface.service.LinkfaceService#ocrIDCardFront(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public AppResponseResult saveOcrIDCardFront(String sessionId, Map<String, String> params) {
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始saveOcrIDCardFront()方法");
		try {
			// 第一步：请求SDK
			Map<String, String> sdkMap = new HashMap<>();
			sdkMap.put("sessionId", sessionId);
			sdkMap.put("imageUrl", params.get("ocrIDCard_front_image_path"));
			sdkMap.put("side", "front");
			Response<Object> response = LinkfaceSDK.ocrIdcard(sdkMap);
			logger.info(sessionId + "：第一步：请求SDK结束：" + JSON.toJSONString(response));

			// 第二步：验证结果
			if ("200".equals(response.getRequestCode()) == false) {
				result.setCode(response.getRequestCode());
				result.setMsg(response.getRequestMsg());
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			OcrIdcard ocrIdcard = (OcrIdcard) response.getObj();
			if (ocrIdcard == null) {
				result.setCode("400");
				result.setMsg("未获取到身份证正面信息");
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if ("OK".equalsIgnoreCase(ocrIdcard.getStatus()) == false) {
				result.setCode("104");
				result.setMsg(ocrIdcard.getStatusMsg());
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if ("front".equalsIgnoreCase(ocrIdcard.getSide()) == false) {
				result.setCode("104");
				result.setMsg("请上传本人身份证正面照片");
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			logger.info(sessionId + "：第二步：验证结果结束");

			// 第三步：验证结果正确与否
			Validity validity = ocrIdcard.getValidity();
			if (validity.isName() == false) {
				result.setCode("105");
				result.setMsg("姓名识别有误，请重新上传身份证正面照");
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (validity.isSex() == false) {
				result.setCode("105");
				result.setMsg("性别识别有误，请重新上传身份证正面照");
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (validity.isBirthday() == false) {
				result.setCode("105");
				result.setMsg("出生日期识别有误，请重新上传身份证正面照");
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (validity.isAddress() == false) {
				result.setCode("105");
				result.setMsg("身份证号识别有误，请重新上传身份证正面照");
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (validity.isNumber() == false) {
				result.setCode("105");
				result.setMsg("姓名识别有误，请重新上传身份证正面照");
				logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			logger.info(sessionId + "：第三步：验证结果正确与否结束");

			// 第四步：插入身份证正面信息
			Info info = ocrIdcard.getInfo();
			int borrowId = Integer.parseInt(params.get("user_id"));
			IdentityCard identityCard = identityCardService.queryIdentityCard(borrowId);
			if (identityCard == null) { // 不存在，新增
				identityCard = new IdentityCard();
				identityCard.setBorrowerId(borrowId);
				identityCard.setAddress(info.getAddress());
				identityCard.setBirthday(info.getYear() + "-" + info.getMonth() + "-" + info.getDay());
				identityCard.setCreateTime(new Date());
				identityCard.setGender(info.getSex());
				identityCard.setIdCardNumber(info.getNumber());
				identityCard.setName(info.getName());
				identityCard.setRace(info.getNation());
				identityCard.setUpdateTime(new Date());

				identityCardService.saveIdentityCard(identityCard);
			} else { // 存在，修改
				identityCard.setBorrowerId(borrowId);
				identityCard.setAddress(info.getAddress());
				identityCard.setBirthday(info.getYear() + "-" + info.getMonth() + "-" + info.getDay());
				identityCard.setGender(info.getSex());
				identityCard.setIdCardNumber(info.getNumber());
				identityCard.setName(info.getName());
				identityCard.setRace(info.getNation());
				identityCard.setUpdateTime(new Date());

				identityCardService.updateIdentityCard(identityCard);
			}
			logger.info(sessionId + "：第四步：插入身份证正面信息结束");

			// 第五步：插入正面认证信息
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
			bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
			bwAdjunct.setAdjunctType(1);
			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
			if (bwAdjunct == null) {
				bwAdjunct = new BwAdjunct();
				bwAdjunct.setAdjunctType(1);
				bwAdjunct.setAdjunctPath(params.get("ocrIDCard_front_image_path"));
				bwAdjunct.setAdjunctDesc(info.getName() + "/" + info.getNumber());
				bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
				bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(1);
				
				bwAdjunctService.add(bwAdjunct);
			} else {
				bwAdjunct.setAdjunctType(1);
				bwAdjunct.setAdjunctPath(params.get("ocrIDCard_front_image_path"));
				bwAdjunct.setAdjunctDesc(info.getName() + "/" + info.getNumber());
				bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
				bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(1);

				bwAdjunctService.update(bwAdjunct);
			}
			bwVerifySourceService.saveOrUpdateVerfiySource(bwAdjunct.getId(),Long.valueOf(params.get("user_id")), Long.valueOf(params.get("orderId")), 2L);
			logger.info(sessionId + "：第五步：插入正面认证信息结束");

			// 第六步：返回
			result.setCode("000");
			result.setMsg("认证成功");

			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setName(info.getName());
			resultInfo.setId_card_number(info.getNumber());
			result.setResult(resultInfo);
			logger.info(sessionId + "：第六步：成功返回结束");
		} catch (Exception e) {
			logger.error(sessionId + "：执行saveOcrIDCardFront()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商汤人脸识别 - 身份证 - 反面
	 * 
	 * @see com.waterelephant.linkface.service.LinkfaceService#ocrIDCardFront(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public AppResponseResult saveOcrIDCardBack(String sessionId, Map<String, String> params) {
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始saveOcrIDCardBack()方法");
		try {
			// 第一步：请求SDK
			Map<String, String> sdkMap = new HashMap<>();
			sdkMap.put("sessionId", sessionId);
			sdkMap.put("imageUrl", params.get("ocrIDCardBackPath"));
			sdkMap.put("side", "back");
			Response<Object> response = LinkfaceSDK.ocrIdcard(sdkMap);
			logger.info(sessionId + "：第一步：请求SDK结束");

			// 第二步：验证结果
			if ("200".equals(response.getRequestCode()) == false) {
				result.setCode(response.getRequestCode());
				result.setMsg(response.getRequestMsg());
				logger.info(sessionId + "：结束saveOcrIDCardBack()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			OcrIdcard ocrIdcard = (OcrIdcard) response.getObj();
			if (ocrIdcard == null) {
				result.setCode("400");
				result.setMsg("未获取到身份证反面信息");
				logger.info(sessionId + "：结束saveOcrIDCardBack()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if ("OK".equalsIgnoreCase(ocrIdcard.getStatus()) == false) {
				result.setCode("104");
				result.setMsg(ocrIdcard.getStatusMsg());
				logger.info(sessionId + "：结束saveOcrIDCardBack()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if ("back".equalsIgnoreCase(ocrIdcard.getSide()) == false) {
				result.setCode("104");
				result.setMsg("请上传本人身份证反面照片");
				logger.info(sessionId + "：结束saveOcrIDCardBack()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			logger.info(sessionId + "：第二步：验证结果结束");

			// 第三步：验证结果正确与否
			Validity validity = ocrIdcard.getValidity();
			if (validity.isAuthority() == false) {
				result.setCode("105");
				result.setMsg("签发机构识别有误，请重新上传身份证反面照");
				logger.info(sessionId + "：结束saveOcrIDCardBack()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (validity.isTimelimit() == false) {
				result.setCode("105");
				result.setMsg("身份证有效期识别有误，请重新上传身份证反面照");
				logger.info(sessionId + "：结束saveOcrIDCardBack()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			logger.info(sessionId + "：第三步：验证结果正确与否结束");

			// 第四步：插入身份证反面信息
			Info info = ocrIdcard.getInfo();
			int borrowId = Integer.parseInt(params.get("user_id"));
			IdentityCard identityCard = identityCardService.queryIdentityCard(borrowId);
			if (identityCard == null) { // 不存在，新增
				identityCard = new IdentityCard();
				identityCard.setBorrowerId(borrowId);
				identityCard.setCreateTime(new Date());
				identityCard.setIssuedBy(info.getAuthority());
				identityCard.setValidDate(info.getTimelimit());
				identityCard.setUpdateTime(new Date());

				identityCardService.saveIdentityCard(identityCard);
			} else { // 存在，修改
				identityCard.setBorrowerId(borrowId);
				identityCard.setIssuedBy(info.getAuthority());
				identityCard.setValidDate(info.getTimelimit());
				identityCard.setUpdateTime(new Date());

				identityCardService.updateIdentityCard(identityCard);
			}
			logger.info(sessionId + "：第四步：插入身份证反面信息结束");

			// 第五步：插入身份证反面认证信息
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
			bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
			bwAdjunct.setAdjunctType(2);
			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
			if (bwAdjunct == null) {
				bwAdjunct = new BwAdjunct();
				bwAdjunct.setAdjunctType(2);
				bwAdjunct.setAdjunctPath(params.get("ocrIDCardBackPath"));
				bwAdjunct.setAdjunctDesc("身份证反面");
				bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
				bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(1);

				bwAdjunctService.add(bwAdjunct);
			} else {
				bwAdjunct.setAdjunctType(2);
				bwAdjunct.setAdjunctPath(params.get("ocrIDCardBackPath"));
				bwAdjunct.setAdjunctDesc("身份证反面");
				bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
				bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(1);

				bwAdjunctService.update(bwAdjunct);
			}
			bwVerifySourceService.saveOrUpdateVerfiySource(bwAdjunct.getId(),Long.valueOf(params.get("user_id")), Long.valueOf(params.get("orderId")), 2L);
			logger.info(sessionId + "：第五步：插入反面认证信息结束");

			// 第六步：返回
			result.setCode("000");
			result.setMsg("认证成功");
			logger.info(sessionId + "：第六步：成功返回结束");
		} catch (Exception e) {
			logger.error(sessionId + "：执行saveOcrIDCardBack()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束saveOcrIDCardBack()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商汤人脸识别 - 身份证 - 保存身份证信息 - APP
	 * 
	 * @see com.waterelephant.linkface.service.LinkfaceService#saveIDCardInfo(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public AppResponseResult saveIDCardInfoApp(String sessionId, Map<String, String> paramMap) {
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始saveIDCardInfoApp()方法");
		try {
			// 第一步：获取参数
			String[] str = paramMap.get("adjunctDesc").split("/");
			String idcard_name = str[0];
			String idcard_number = str[1];
			long borrowerId = Long.valueOf(paramMap.get("borrowId"));
			long orderId = Long.valueOf(paramMap.get("orderId"));
			// int photoState = Integer.parseInt(paramMap.get("photoState"));
			int authChannel = Integer.parseInt(paramMap.get("authChannel"));
			logger.info(sessionId + "：第一步：获取参数");

			// 第二步：请求SDK(活体检测)
			Map<String, String> sdkMap = new HashMap<>();
			sdkMap.put("name", idcard_name);
			sdkMap.put("idCard", idcard_number);
			sdkMap.put("dataUrl", paramMap.get("delta"));
			sdkMap.put("sessionId", sessionId);
			Response<Object> sdkResponse = LinkfaceSDK.livenessIdnumberVerification(sdkMap);
			logger.info(sessionId + "：第二步：请求SDK(活体检测)");

			// 第三步：验证SDK结果
			Map<String, String> faceMap = new HashMap<>();
			LivenessIdnumberVerification liveness = (LivenessIdnumberVerification) sdkResponse.getObj();
			if (liveness != null && "OK".equalsIgnoreCase(liveness.getStatus())) {
				// 用于checkJob
				if (liveness.getVerify_score() >= 0.7) {
					faceMap.put("code", "000");
					faceMap.put("msg", "认证成功");
					faceMap.put("status", liveness.getStatus());
					faceMap.put("statusMsg", liveness.getStatusMsg());
				} else {
					faceMap.put("code", "204");
					faceMap.put("msg", "分数过低");
					faceMap.put("status", liveness.getStatus());
					faceMap.put("statusMsg", liveness.getStatusMsg());
				}

				// 征信黑名单
				saveZxBlack(sessionId, borrowerId, orderId, idcard_name, idcard_number, liveness.getVerify_score(), 3);

				// 因商汤接口未返回Identity对象，因此直接计数调用次数
				LinkfaceVerify linkfaceVerify = new LinkfaceVerify();
				linkfaceVerify.setBorrowerId(borrowerId);
				linkfaceVerify.setOrderId(orderId);
				linkfaceVerify.setCreateTime(new Date());
				linkfaceVerifyService.saveLinkfaceVerify(linkfaceVerify);
			} else {
				faceMap.put("code", "204");
				faceMap.put("msg", "认证失败");
				if(liveness != null){
					faceMap.put("status", liveness.getStatus());
					faceMap.put("statusMsg", liveness.getStatusMsg());
				}
				// 征信黑名单
				saveZxBlack(sessionId, borrowerId, orderId, idcard_name, idcard_number, 0, 3);
			}
			logger.info(sessionId + "：第三步：验证SDK结果");

			// 第四步：插入活体认证信息
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setBorrowerId(Long.valueOf(paramMap.get("borrowId")));
			bwAdjunct.setOrderId(Long.valueOf(paramMap.get("orderId")));
			bwAdjunct.setAdjunctType(3);
			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
			if (bwAdjunct == null) {
				bwAdjunct = new BwAdjunct();
				bwAdjunct.setAdjunctType(3);
				bwAdjunct.setAdjunctPath(paramMap.get("czzUrl"));
				bwAdjunct.setAdjunctDesc(JSON.toJSONString(faceMap));
				bwAdjunct.setBorrowerId(borrowerId);
				bwAdjunct.setOrderId(orderId);
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(1);

				bwAdjunctService.add(bwAdjunct);
			} else {
				bwAdjunct.setAdjunctType(3);
				bwAdjunct.setAdjunctPath(paramMap.get("czzUrl"));
				bwAdjunct.setAdjunctDesc(JSON.toJSONString(faceMap));
				bwAdjunct.setBorrowerId(borrowerId);
				bwAdjunct.setOrderId(orderId);
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(1);

				bwAdjunctService.update(bwAdjunct);
			}
			bwVerifySourceService.saveOrUpdateVerfiySource(bwAdjunct.getId(),borrowerId, orderId, 2L);
			logger.info(sessionId + "：第四步：插入活体认证信息");

			// 第五步：添加或保存工单认证信息
			saveOrderAuth(sessionId, orderId, authChannel, 1);
			logger.info(sessionId + "：第五步：添加或保存工单认证信息");

			// 第六步：返回
			result.setCode("000");
			result.setMsg("认证成功");
			logger.info(sessionId + "：第六步：返回");
		} catch (Exception e) {
			logger.error(sessionId + "：执行saveIDCardInfoApp()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商汤人脸识别 - 身份证 - 保存身份证信息 - H5
	 * 
	 * @see com.waterelephant.linkface.service.LinkfaceService#saveIDCardInfo(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public AppResponseResult saveIDCardInfoH5(String sessionId, Map<String, String> paramMap) {
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始saveIDCardInfoH5()方法");
		try {
			// 第零步：获取参数
			long borrowerId = Long.valueOf(paramMap.get("borrowId"));
			long orderId = Long.valueOf(paramMap.get("orderId"));
			// int photoState = Integer.parseInt(paramMap.get("photoState"));
			int authChannel = Integer.parseInt(paramMap.get("authChannel"));

			// 第一步：插入手持身份证照信息
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setBorrowerId(Long.valueOf(paramMap.get("borrowId")));
			bwAdjunct.setOrderId(Long.valueOf(paramMap.get("orderId")));
			bwAdjunct.setAdjunctType(3);
			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
			if (bwAdjunct == null) {
				bwAdjunct = new BwAdjunct();
				bwAdjunct.setAdjunctType(3);
				bwAdjunct.setAdjunctPath(paramMap.get("czzUrl"));
				bwAdjunct.setAdjunctDesc("手持身份证照");
				bwAdjunct.setBorrowerId(borrowerId);
				bwAdjunct.setOrderId(orderId);
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(0);

				bwAdjunctService.add(bwAdjunct);
			} else {
				bwAdjunct.setAdjunctType(3);
				bwAdjunct.setAdjunctPath(paramMap.get("czzUrl"));
				bwAdjunct.setAdjunctDesc("手持身份证照");
				bwAdjunct.setBorrowerId(borrowerId);
				bwAdjunct.setOrderId(orderId);
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(0);

				bwAdjunctService.update(bwAdjunct);
			}
			bwVerifySourceService.saveOrUpdateVerfiySource(bwAdjunct.getId(),borrowerId, orderId, 2L);
			logger.info(sessionId + "：第一步：插入手持身份证照信息");

			// 第二步：插入工单认证
			saveOrderAuth(sessionId, orderId, authChannel, 0);
			logger.info(sessionId + "：第二步：插入工单认证");

			// 第三步：征信黑名单
			saveZxBlack(sessionId, borrowerId, orderId, "", "", 0, 3);
			logger.info(sessionId + "：第三步：征信黑名单");

			// 第四步：返回
			result.setCode("000");
			result.setMsg("认证成功");
			logger.info(sessionId + "： 第四步：返回");
		} catch (Exception e) {
			logger.error(sessionId + "：执行saveIDCardInfoH5()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 公共方法 - 保存黑名单（code0088）
	 * 
	 * @param sessionId
	 * @param borrowerId
	 * @param orderId
	 * @param idcard_name
	 * @param idcard_number
	 * @param verifyScore
	 */
	private void saveZxBlack(String sessionId, long borrowerId, long orderId, String idcard_name, String idcard_number,
			double verifyScore, long sourceItem) {
		logger.info(sessionId + "开始插入黑名单borrowerId=" + borrowerId + ",name=" + idcard_name);

		long source = 22; // 来源：商汤

		ZxBlack zxBlack = zxBlackService.queryZxBlack(borrowerId, orderId, source, sourceItem);
		if (zxBlack == null) {
			zxBlack = new ZxBlack();
			zxBlack.setCreateTime(new Date());
			zxBlack.setBorrowId(borrowerId);
			zxBlack.setOrderId(orderId);
			zxBlack.setName(idcard_name);
			zxBlack.setIdCard(idcard_number);
			zxBlack.setUpdateTime(new Date());
			zxBlack.setSource(22);
			zxBlack.setSourceItem(3);
			zxBlack.setScore(verifyScore);
			zxBlack.setType(1);
			zxBlack.setRemark("需要人工审核的身份认证");
			if (verifyScore >= 0.7) {
				zxBlack.setRejectType(2);
				zxBlack.setRejectInfo("商汤认证通过");
			} else {
				zxBlack.setRejectType(2);
				zxBlack.setRejectInfo("商汤认证分数过低，请人工审核");
			}
			zxBlackService.saveZxBlack(zxBlack);
		} else {
			zxBlack.setBorrowId(borrowerId);
			zxBlack.setOrderId(orderId);
			zxBlack.setName(idcard_name);
			zxBlack.setIdCard(idcard_number);
			zxBlack.setUpdateTime(new Date());
			zxBlack.setSource(22);
			zxBlack.setSourceItem(3);
			zxBlack.setScore(verifyScore);
			zxBlack.setType(1);
			zxBlack.setRemark("需要人工审核的身份认证");
			if (verifyScore >= 0.7) {
				zxBlack.setRejectType(2);
				zxBlack.setRejectInfo("商汤认证通过");
			} else {
				zxBlack.setRejectType(2);
				zxBlack.setRejectInfo("商汤认证分数过低，请人工审核");
			}
			zxBlackService.updateZxBlackh(zxBlack);
		}
		logger.info(sessionId + "结束插入黑名单borrowerId=" + borrowerId + ",name=" + idcard_name);
	}

	/**
	 * 公共方法 - 保存订单认证（code0088）
	 * 
	 * @param sessionId
	 * @param order_id
	 * @param authChannel
	 * @param photoState
	 */
	private void saveOrderAuth(String sessionId, long order_id, int authChannel, int photoState) {
		logger.info(sessionId + "开始插入订单认证（OrderAuth）信息orderId=" + order_id);
		BwOrderAuth orderAuth = bwOrderAuthService.findBwOrderAuth(order_id, 3);
		if (orderAuth == null) {
			orderAuth = new BwOrderAuth();
			orderAuth.setAuth_type(3);
			orderAuth.setCreateTime(new Date());
			orderAuth.setUpdateTime(new Date());
			orderAuth.setOrderId(order_id);
			orderAuth.setAuth_channel(authChannel);
			orderAuth.setPhotoState(photoState);
			bwOrderAuthService.saveBwOrderAuth(orderAuth);
		} else {
			orderAuth.setAuth_channel(authChannel);
			orderAuth.setUpdateTime(new Date());
			orderAuth.setPhotoState(photoState);
			bwOrderAuthService.updateBwOrderAuth(orderAuth);
		}
		logger.info(sessionId + "结束插入订单认证（OrderAuth）信息orderId=" + order_id);
	}

}
