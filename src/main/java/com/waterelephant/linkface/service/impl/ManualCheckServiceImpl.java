package com.waterelephant.linkface.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.faceID.entity.IdentityCard;
import com.waterelephant.faceID.service.IdentityCardService;
import com.waterelephant.linkface.entity.ResultInfo;
import com.waterelephant.linkface.service.ManualCheckService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwVerifySourceService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.zhengxin91.entity.ZxBlack;
import com.waterelephant.zhengxin91.service.ZxBlackService;

@Service
public class ManualCheckServiceImpl implements ManualCheckService {
	private Logger logger = LoggerFactory.getLogger(ManualCheckServiceImpl.class);

	@Autowired
	private IdentityCardService identityCardService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private ZxBlackService zxBlackService;
	@Autowired
	private BwVerifySourceService bwVerifySourceService;

	/**
	 * 活体验证 - 人工审核 - 正面（code0088）
	 * 
	 * @see com.waterelephant.linkface.service.ManualCheckService#saveOcrIDCardFront(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public AppResponseResult saveOcrIDCardFront(String sessionId, Map<String, String> params) {
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始saveOcrIDCardFront()方法");
		try {
			// 第一步：插入身份证正面信息
			int borrowId = Integer.parseInt(params.get("user_id"));
			IdentityCard identityCard = identityCardService.queryIdentityCard(borrowId);
			if (identityCard == null) { // 不存在，新增
				identityCard = new IdentityCard();
				identityCard.setBorrowerId(borrowId);
				identityCard.setAddress("");
				identityCard.setBirthday("");
				identityCard.setCreateTime(new Date());
				identityCard.setGender("");
				identityCard.setIdCardNumber("");
				identityCard.setName("");
				identityCard.setRace("");
				identityCard.setUpdateTime(new Date());

				identityCardService.saveIdentityCard(identityCard);
			} else { // 存在，修改
				identityCard.setBorrowerId(borrowId);
				identityCard.setAddress("");
				identityCard.setBirthday("");
				identityCard.setGender("");
				identityCard.setIdCardNumber("");
				identityCard.setName("");
				identityCard.setRace("");
				identityCard.setUpdateTime(new Date());

				identityCardService.updateIdentityCard(identityCard);
			}
			logger.info(sessionId + "：第一步：插入身份证正面信息");

			// 第二步：插入正面认证信息
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
			bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
			bwAdjunct.setAdjunctType(1);
			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
			if (bwAdjunct == null) {
				bwAdjunct = new BwAdjunct();
				bwAdjunct.setAdjunctType(1);
				bwAdjunct.setAdjunctPath(params.get("ocrIDCard_front_image_path"));
				bwAdjunct.setAdjunctDesc("");
				bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
				bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(0);

				bwAdjunctService.add(bwAdjunct);
			} else {
				bwAdjunct.setAdjunctType(1);
				bwAdjunct.setAdjunctPath(params.get("ocrIDCard_front_image_path"));
				bwAdjunct.setAdjunctDesc("");
				bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
				bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(0);

				bwAdjunctService.update(bwAdjunct);
			}
			bwVerifySourceService.saveOrUpdateVerfiySource(bwAdjunct.getId(),Long.valueOf(params.get("user_id")), Long.valueOf(params.get("orderId")), 0L);
			logger.info(sessionId + "：第二步：插入正面认证信息");

			// 第三步：返回
			result.setCode("000");
			result.setMsg("认证成功");

			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setName("");
			resultInfo.setId_card_number("");
			result.setResult(resultInfo);
			logger.info(sessionId + "：第三步：返回");
		} catch (Exception e) {
			logger.error(sessionId + "：执行saveOcrIDCardFront()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束saveOcrIDCardFront()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 活体验证 - 人工审核 - 反面（code0088）
	 * 
	 * @see com.waterelephant.linkface.service.ManualCheckService#saveOcrIDCardBack(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public AppResponseResult saveOcrIDCardBack(String sessionId, Map<String, String> params) {
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始saveOcrIDCardBack()方法");
		try {
			// 第一步：插入身份证反面信息
			int borrowId = Integer.parseInt(params.get("user_id"));
			IdentityCard identityCard = identityCardService.queryIdentityCard(borrowId);
			if (identityCard == null) { // 不存在，新增
				identityCard = new IdentityCard();
				identityCard.setBorrowerId(borrowId);
				identityCard.setCreateTime(new Date());
				identityCard.setIssuedBy("");
				identityCard.setValidDate("");
				identityCard.setUpdateTime(new Date());

				identityCardService.saveIdentityCard(identityCard);
			} else { // 存在，修改
				identityCard.setBorrowerId(borrowId);
				identityCard.setIssuedBy("");
				identityCard.setValidDate("");
				identityCard.setUpdateTime(new Date());

				identityCardService.updateIdentityCard(identityCard);
			}
			logger.info(sessionId + "：第一步：插入身份证反面信息");

			// 第二步：插入身份证反面认证信息
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
			bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
			bwAdjunct.setAdjunctType(2);
			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
			if (bwAdjunct == null) {
				bwAdjunct = new BwAdjunct();
				bwAdjunct.setAdjunctType(2);
				bwAdjunct.setAdjunctPath(params.get("ocrIDCardBackPath"));
				bwAdjunct.setAdjunctDesc(null);
				bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
				bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(0);

				bwAdjunctService.add(bwAdjunct);
			} else {
				bwAdjunct.setAdjunctType(2);
				bwAdjunct.setAdjunctPath(params.get("ocrIDCardBackPath"));
				bwAdjunct.setAdjunctDesc(null);
				bwAdjunct.setBorrowerId(Long.valueOf(params.get("user_id")));
				bwAdjunct.setOrderId(Long.valueOf(params.get("orderId")));
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(0);

				bwAdjunctService.update(bwAdjunct);
			}
			bwVerifySourceService.saveOrUpdateVerfiySource(bwAdjunct.getId(),Long.valueOf(params.get("user_id")), Long.valueOf(params.get("orderId")), 0L);
			logger.info(sessionId + "：第二步：插入身份证反面认证信息");

			// 第三步：返回
			result.setCode("000");
			result.setMsg("认证成功");
			logger.info(sessionId + "：第三步：返回");
		} catch (Exception e) {
			logger.error(sessionId + "：执行saveOcrIDCardBack()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束saveOcrIDCardBack()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 活体验证 - 人工审核 - APP - 保存身份证信息（code0088）
	 * 
	 * @see com.waterelephant.linkface.service.ManualCheckService#saveIDCardInfoApp(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public AppResponseResult saveIDCardInfoApp(String sessionId, Map<String, String> paramMap) {
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始saveIDCardInfoApp()方法");
		try {
			// 第一步：获取参数
			long borrowerId = Long.valueOf(paramMap.get("borrowId"));
			long orderId = Long.valueOf(paramMap.get("orderId"));
			// int photoState = Integer.parseInt(paramMap.get("photoState"));
			int authChannel = Integer.parseInt(paramMap.get("authChannel"));
			logger.info(sessionId + "：第一步：获取参数");

			Map<String, String> faceMap = new HashMap<>();
			faceMap.put("code", "000");
			faceMap.put("msg", "请人工审核");
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
				bwAdjunct.setPhotoState(0);

				bwAdjunctService.add(bwAdjunct);
			} else {
				bwAdjunct.setAdjunctType(3);
				bwAdjunct.setAdjunctPath(paramMap.get("czzUrl"));
				bwAdjunct.setAdjunctDesc(JSON.toJSONString(faceMap));
				bwAdjunct.setBorrowerId(borrowerId);
				bwAdjunct.setOrderId(orderId);
				bwAdjunct.setCreateTime(new Date());
				bwAdjunct.setUpdateTime(new Date());
				bwAdjunct.setPhotoState(0);

				bwAdjunctService.update(bwAdjunct);
			}
			bwVerifySourceService.saveOrUpdateVerfiySource(bwAdjunct.getId(),borrowerId, orderId, 0L);
			logger.info(sessionId + "：第四步：插入活体认证信息");

			// 第五步：添加或保存工单认证信息
			saveOrderAuth(sessionId, orderId, authChannel, 0);
			logger.info(sessionId + "：第五步：添加或保存工单认证信息");

			// 第六步：保存征信黑名单
			saveZxBlack(sessionId, borrowerId, orderId, "", "", 0, 3);
			logger.info(sessionId + "：第六步：保存征信黑名单");

			// 第七步：返回
			result.setCode("000");
			result.setMsg("认证成功");
			logger.info(sessionId + "： 第七步：返回");
		} catch (Exception e) {
			logger.error(sessionId + "：执行saveIDCardInfoApp()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 活体认证 - 身份证 - 保存身份证信息 - H5
	 * 
	 * @see com.waterelephant.linkface.service.ManualCheckService#saveIDCardInfoH5(java.lang.String,
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
			bwVerifySourceService.saveOrUpdateVerfiySource(bwAdjunct.getId(),borrowerId, orderId, 0L);
			logger.info(sessionId + "：第一步：插入手持身份证照信息");

			// 第二步：插入工单认证
			saveOrderAuth(sessionId, orderId, authChannel, 0);
			logger.info(sessionId + "：第二步：插入工单认证");

			// 第三步：保存征信黑名单
			saveZxBlack(sessionId, borrowerId, orderId, "", "", 0, 3);
			logger.info(sessionId + "：第三步：保存征信黑名单");

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

		long source = 23; // 来源：人工审核

		ZxBlack zxBlack = zxBlackService.queryZxBlack(borrowerId, orderId, source, sourceItem);
		if (zxBlack == null) {
			zxBlack = new ZxBlack();
			zxBlack.setCreateTime(new Date());
			zxBlack.setBorrowId(borrowerId);
			zxBlack.setOrderId(orderId);
			zxBlack.setName(idcard_name);
			zxBlack.setIdCard(idcard_number);
			zxBlack.setUpdateTime(new Date());
			zxBlack.setSource(23);
			zxBlack.setSourceItem(3);
			zxBlack.setScore(verifyScore);
			zxBlack.setType(1);
			zxBlack.setRemark("需要人工审核的身份认证");
			zxBlack.setRejectType(2);
			zxBlack.setRejectInfo("手持身份证照片，请人工审核");
			zxBlackService.saveZxBlack(zxBlack);
		} else {
			zxBlack.setBorrowId(borrowerId);
			zxBlack.setOrderId(orderId);
			zxBlack.setName(idcard_name);
			zxBlack.setIdCard(idcard_number);
			zxBlack.setUpdateTime(new Date());
			zxBlack.setSource(23);
			zxBlack.setSourceItem(3);
			zxBlack.setScore(verifyScore);
			zxBlack.setType(1);
			zxBlack.setRemark("需要人工审核的身份认证");
			zxBlack.setRejectType(2);
			zxBlack.setRejectInfo("手持身份证照片，请人工审核");
			zxBlackService.updateZxBlackh(zxBlack);
		}
		logger.info(sessionId + "结束插入黑名单borrowerId=" + borrowerId + ",name=" + idcard_name);
	}

}
