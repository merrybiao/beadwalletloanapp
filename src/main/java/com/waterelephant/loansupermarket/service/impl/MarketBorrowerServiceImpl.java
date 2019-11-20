/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.loansupermarket.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.loansupermarket.service.MarketBorrowerService;
import com.waterelephant.service.impl.BaseCommonServiceImpl;
import com.waterelephant.utils.AESUtil;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * 
 * 
 * Module:
 * 
 * MarketBorrowerServiceImpl.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class MarketBorrowerServiceImpl extends BaseCommonServiceImpl<BwBorrower, Long>
		implements MarketBorrowerService {

	@Override
	public BwBorrower findByPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		BwBorrower param = new BwBorrower();
		param.setPhone(phone);
		List<BwBorrower> list = mapper.select(param);
		if (list != null && !list.isEmpty()) {
			return list.get(list.size() - 1);
		}
		return null;
	}

	@Override
	public AppResponseResult updateLoginOrRegister(String phone, String password, Integer loginType, Integer channel)
			throws Exception {
		AppResponseResult result = new AppResponseResult();
		BwBorrower queryBorrower = null;
		BwBorrower param = new BwBorrower();
		param.setPhone(phone);
		List<BwBorrower> list = mapper.select(param);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("hasBorrower", false);// 是否有借款人
		if (list != null && !list.isEmpty()) {
			queryBorrower = list.get(list.size() - 1);
			resultMap.put("hasBorrower", true);
		}
		if (loginType == 1) {// 密码登录
			String pwdMd5 = CommUtils.getMD5(password.getBytes());
			if (queryBorrower != null) {
				String queryPassword = queryBorrower.getPassword();
				if (queryPassword != null && !queryPassword.equalsIgnoreCase(pwdMd5)) {
					result.setCode("202");
					result.setMsg("密码错误");
					return result;
				}
			} else {
				result.setCode("201");
				result.setMsg("账号不存在");
				return result;
			}
		} else if (loginType == 2) {// 验证码登录或注册
			if (queryBorrower == null) {// 创建用户
				Date nowDate = new Date();
				queryBorrower = new BwBorrower();
				queryBorrower.setPhone(phone);
				queryBorrower.setChannel(channel);
				queryBorrower.setFlag(1);
				queryBorrower.setCreateTime(nowDate);
				queryBorrower.setUpdateTime(nowDate);
				queryBorrower.setChannel(channel);
				mapper.insertSelective(queryBorrower);
			} else {
				resultMap.put("hasBorrower", true);
			}
		}
		resultMap.put("loginType", loginType);
		result.setCode("000");
		result.setMsg("SUSSCSS");
		if (queryBorrower == null) {
			resultMap.put("isLogin", false);
		} else {
			resultMap.put("isLogin", true);
			resultMap.put("borrowerId", queryBorrower.getId());
			resultMap.put("idCard", queryBorrower.getIdCard());
			resultMap.put("name", queryBorrower.getName());
			resultMap.put("createTime",
					CommUtils.convertDateToString(queryBorrower.getCreateTime(), SystemConstant.YMD_HMS));
		}

		result.setResult(AESUtil.Encrypt(JSON.toJSONString(resultMap), "mriogyw/prvhwpvj"));
		return result;
	}
}