/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.rongCarrier.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.IBwOperateService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.CommUtils;

/**
 * @author 崔雄健
 * @date 2017年3月25日
 * @description 融360 运营商
 */
@Controller
@RequestMapping("/app/rongCarrier")
public class RongCarrierController {

	private Logger logger = Logger.getLogger(RongCarrierController.class);

	@Resource
	private BwOrderAuthService bwOrderAuthService;
	@Resource
	private IBwOperateService bwOperateService;
	@Resource
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;

	/**
	 * @param
	 * @return
	 * @author 崔雄健
	 * @date 2017年3月25日
	 * @description 认证成功通知 H5
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/collectuser.do")
	public String collectuser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String url = "auth_fail";
		try {
			String outUniqueId = request.getParameter("orderId");
			String userId = request.getParameter("userId");
			String authChannel = request.getParameter("authChannel");
			String phone = request.getParameter("phone");

			if (CommUtils.isNull(userId)) {
				return "auth_fail";
			}
			if (CommUtils.isNull(outUniqueId)) {
				return "auth_fail";
			}
			if (CommUtils.isNull(authChannel)) {
				return "auth_fail";
			}

			Map<String, Object> map = new HashMap<>();
			map.put("userId", userId);
			map.put("outUniqueId", outUniqueId);
			map.put("authChannel", authChannel);
			BwBorrower q = new BwBorrower();
			q.setId(Long.valueOf(userId));
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByAttr(q);
			String name = bwBorrower.getName();
			String idCard = bwBorrower.getIdCard();

			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderIdNew(outUniqueId);
			String emergencyName1 = null;
			String emergencyPhone1 = null;
			String emergencyName2 = null;
			String emergencyPhone2 = null;
			if (!CommUtils.isNull(bwPersonInfo) && !CommUtils.isNull(bwPersonInfo.getRelationPhone())) {
				emergencyName1 = bwPersonInfo.getRelationName();
				emergencyPhone1 = bwPersonInfo.getRelationPhone();

				emergencyName2 = bwPersonInfo.getUnrelationName();
				emergencyPhone2 = bwPersonInfo.getUnrelationPhone();
			}

			logger.info("认证入参：authChannel：" + authChannel + ",userId:" + userId + ",outUniqueId:" + outUniqueId
					+ ",phone:" + phone + ",idCard:" + idCard + ",emergencyName1:" + emergencyName1
					+ ",emergencyPhone1:" + emergencyPhone1 + ",emergencyName2:" + emergencyName2 + ",emergencyPhone2:"
					+ emergencyPhone2);
			// 5 月13 号以前添加运营商认证记录
			// String returnStr = BeadWalletRongCarrierService.collectuser(userId, outUniqueId, authChannel, phone);

			// 5 月18 号以前 获取西瓜分用
			// String returnStr = BeadWalletRongCarrierService.xiGuaSubmitinfo(userId, outUniqueId, authChannel,
			// phone,name, idCard);

			// 5月 18号以后 添加运营商认证记录配合西瓜分
			String returnStr = BeadWalletRongCarrierService.collectuserPhxg(userId, outUniqueId, authChannel, phone,
					name, idCard, emergencyName1, emergencyName2, emergencyPhone1, emergencyPhone2);

			logger.info("认证出参：" + returnStr);
			if (CommUtils.isNull(returnStr)) {
				return "auth_fail";
			} else {
				Map<String, Object> returnMap = JSON.parseObject(returnStr);
				Map<String, String> mapTem = (Map<String, String>) returnMap
						.get("tianji_api_tianjireport_collectuser_response");
				url = CommUtils.toString(mapTem.get("redirectUrl"));
				if (!CommUtils.isNull(url)) {
					response.setStatus(201);
					return "redirect:" + url;
				} else {
					return "auth_fail";
				}
			}

		} catch (Exception e) {
			response.setStatus(101);
			logger.error("系统异常", e);
		}

		return url;
	}

}