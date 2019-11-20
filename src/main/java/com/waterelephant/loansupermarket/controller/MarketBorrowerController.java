package com.waterelephant.loansupermarket.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.loansupermarket.service.MarketBorrowerService;
import com.waterelephant.utils.AESUtil;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.RedisUtils;

/**
 * 贷款超市controller
 * 
 * @author maoenqi
 *
 */
@Controller
@RequestMapping("/market/borrower")
public class MarketBorrowerController {

	@Autowired
	private MarketBorrowerService marketBorrowerService;

	private Logger logger = Logger.getLogger(MarketBorrowerController.class);

	private String requestAesKey = "mriogyw/prvhwpvj";

	/**
	 * 提供给贷款超市接口，登录或注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginOrRegister.do")
	@ResponseBody
	public AppResponseResult loginOrRegister(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String receiveDecryptStr = request.getParameter("request_data");
		String lockKey = null;
		String phone = null;
		try {
			String receiveJsonStr = AESUtil.Decrypt(receiveDecryptStr, requestAesKey);
			logger.info("【AppBwBorrowerController.loginOrRegister】receiveJsonStr=" + receiveJsonStr);
			JSONObject receiveJson = JSON.parseObject(receiveJsonStr);
			if (receiveJson == null || receiveJson.size() == 0) {
				result.setCode("101");
				result.setMsg("参数错误");
				return result;
			}
			phone = receiveJson.getString("phone");
			Integer loginType = receiveJson.getInteger("loginType");
			Integer channel = receiveJson.getInteger("channel");
			String password = receiveJson.getString("password");
			logger.info("【AppBwBorrowerController.loginOrRegister】phone=" + phone + "," + receiveJsonStr);
			if (StringUtils.isEmpty(phone) || !phone.matches("^1[1-9]\\d{9}$") || channel == null || loginType == null
					|| (loginType != 1 && loginType != 2) || (loginType == 1 && StringUtils.isEmpty(password))) {
				logger.info("【AppBwBorrowerController.loginOrRegister】参数错误,phone=" + phone + "," + receiveJsonStr);
				result.setCode("101");
				result.setMsg("参数错误");
				return result;
			}

			lockKey = RedisKeyConstant.LOCK_KEY_PRE + phone;
			if (!ControllerUtil.lockRequest(lockKey, 20)) {
				result.setCode("300");
				result.setMsg("请稍后再试");
				logger.info("【AppBwBorrowerController.loginOrRegister】重复提交：phone=" + phone + "," + receiveJsonStr);
				return result;
			}
			result = marketBorrowerService.updateLoginOrRegister(phone, password, loginType, channel);
		} catch (Exception e) {
			logger.error("【AppBwBorrowerController.loginOrRegister】系统异常phone=" + phone, e);
			result.setCode("111");
			result.setMsg("系统异常");
			return result;
		} finally {
			if (StringUtils.isNotEmpty(lockKey)) {
				RedisUtils.del(lockKey);
			}
		}
		return result;
	}

	/**
	 * 提供给贷款超市接口，登录或注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/resetPwd.do")
	@ResponseBody
	public AppResponseResult resetPwd(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String receiveDecryptStr = request.getParameter("request_data");
		String lockKey = null;
		String phone = null;
		try {
			String receiveJsonStr = AESUtil.Decrypt(receiveDecryptStr, requestAesKey);
			JSONObject receiveJson = JSON.parseObject(receiveJsonStr);
			if (receiveJson == null || receiveJson.size() == 0) {
				result.setCode("101");
				result.setMsg("参数错误");
				return result;
			}
			phone = receiveJson.getString("phone");
			String password = receiveJson.getString("password");
			logger.info("【AppBwBorrowerController.resetPwd】phone=" + phone + "," + receiveJsonStr);
			if (StringUtils.isEmpty(phone) || !phone.matches("^1[1-9]\\d{9}$") || StringUtils.isEmpty(password)) {
				logger.info("【AppBwBorrowerController.resetPwd】参数错误,phone=" + phone + "," + receiveJsonStr);
				result.setCode("101");
				result.setMsg("参数错误");
				return result;
			}

			lockKey = RedisKeyConstant.LOCK_KEY_PRE + phone;
			if (!ControllerUtil.lockRequest(lockKey, 20)) {
				result.setCode("300");
				result.setMsg("请稍后再试");
				logger.info("【AppBwBorrowerController.resetPwd】重复提交：phone=" + phone + "," + receiveJsonStr);
				return result;
			}
			// 重置密码
			BwBorrower queryBorrower = marketBorrowerService.findByPhone(phone);
			if (queryBorrower == null) {
				result.setCode("102");
				result.setMsg("没有此用户");
				return result;
			}
			BwBorrower bwBorrower = new BwBorrower();
			bwBorrower.setId(queryBorrower.getId());
			bwBorrower.setPhone(phone);
			String pwdMd5 = CommUtils.getMD5(password.getBytes());
			bwBorrower.setPassword(pwdMd5);
			marketBorrowerService.updateByPrimaryKeySelective(bwBorrower);
			result.setCode("000");
			result.setMsg("SUCCESS");
		} catch (Exception e) {
			logger.error("【AppBwBorrowerController.resetPwd】系统异常phone=" + phone, e);
			result.setCode("111");
			result.setMsg("系统异常");
			return result;
		} finally {
			if (StringUtils.isNotEmpty(lockKey)) {
				RedisUtils.del(lockKey);
			}
		}
		return result;
	}

	/**
	 * 是否有设置密码,是否有设置密码，true:有设置，修改密码页面，false：设置密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/isSetPwd.do")
	@ResponseBody
	public AppResponseResult isSetPwd(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String receiveDecryptStr = request.getParameter("request_data");
		String phone = null;
		try {
			String receiveJsonStr = AESUtil.Decrypt(receiveDecryptStr, requestAesKey);
			JSONObject receiveJson = JSON.parseObject(receiveJsonStr);
			if (receiveJson == null || receiveJson.size() == 0) {
				result.setCode("101");
				result.setMsg("参数错误");
				return result;
			}
			phone = receiveJson.getString("phone");
			logger.info("【AppBwBorrowerController.isSetPwd】phone=" + phone + "," + receiveJsonStr);
			if (StringUtils.isEmpty(phone) || !phone.matches("^1[1-9]\\d{9}$")) {
				logger.info("【AppBwBorrowerController.isSetPwd】参数错误,phone=" + phone + "," + receiveJsonStr);
				result.setCode("101");
				result.setMsg("参数错误");
				return result;
			}

			BwBorrower queryBorrower = marketBorrowerService.findByPhone(phone);
			if (queryBorrower == null) {
				result.setCode("102");
				result.setMsg("没有此用户");
				return result;
			}
			String password = queryBorrower.getPassword();
			JSONObject resultJson = new JSONObject();
			if (StringUtils.isNotEmpty(password)) {
				resultJson.put("isSetPwd", true);// 是否有设置密码，true:有设置，修改密码页面，false：设置密码页面
			} else {
				resultJson.put("isSetPwd", false);
			}
			result.setCode("000");
			result.setMsg("SUCCESS");
			result.setResult(AESUtil.Encrypt(resultJson.toJSONString(), requestAesKey));
		} catch (Exception e) {
			logger.error("【AppBwBorrowerController.isSetPwd】系统异常phone=" + phone, e);
			result.setCode("111");
			result.setMsg("系统异常");
			return result;
		}
		return result;
	}

	/**
	 * 设置密码或修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePwd.do")
	@ResponseBody
	public AppResponseResult updatePwd(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String receiveDecryptStr = request.getParameter("request_data");
		String phone = null;
		try {
			String receiveJsonStr = AESUtil.Decrypt(receiveDecryptStr, requestAesKey);
			JSONObject receiveJson = JSON.parseObject(receiveJsonStr);
			if (receiveJson == null || receiveJson.size() == 0) {
				result.setCode("101");
				result.setMsg("参数错误");
				return result;
			}
			phone = receiveJson.getString("phone");
			String oldPassword = receiveJson.getString("oldPassword");
			String newPassword = receiveJson.getString("password");
			String oldPasswordMd5 = null;
			String newPasswordMd5 = null;
			if (StringUtils.isNotEmpty(oldPassword)) {
				oldPasswordMd5 = CommUtils.getMD5(oldPassword.getBytes());
			}
			if (StringUtils.isNotEmpty(newPassword)) {
				newPasswordMd5 = CommUtils.getMD5(newPassword.getBytes());
			}
			logger.info("【AppBwBorrowerController.updatePwd】phone=" + phone + "," + receiveJsonStr);
			if (StringUtils.isEmpty(phone) || !phone.matches("^1[1-9]\\d{9}$") || StringUtils.isEmpty(newPassword)) {
				logger.info("【AppBwBorrowerController.updatePwd】参数错误,phone=" + phone + "," + receiveJsonStr);
				result.setCode("101");
				result.setMsg("参数错误");
				return result;
			}

			BwBorrower queryBorrower = marketBorrowerService.findByPhone(phone);
			if (queryBorrower == null) {
				result.setCode("102");
				result.setMsg("没有此用户");
				return result;
			}
			String queryPassword = queryBorrower.getPassword();
			boolean isSetPwd = true;// 是否设置密码，true:修改密码，false：设置密码
			if (StringUtils.isEmpty(queryPassword)) {
				isSetPwd = false;
			}
			if (isSetPwd && (StringUtils.isEmpty(oldPasswordMd5) || !oldPasswordMd5.equalsIgnoreCase(queryPassword))) {
				result.setCode("103");
				result.setMsg("密码不匹配");
				return result;
			}
			BwBorrower updateBorrower = new BwBorrower();
			updateBorrower.setId(queryBorrower.getId());
			updateBorrower.setPassword(newPasswordMd5);
			updateBorrower.setUpdateTime(new Date());
			marketBorrowerService.updateByPrimaryKeySelective(updateBorrower);
			result.setCode("000");
			result.setMsg("SUCCESS");
		} catch (Exception e) {
			logger.error("【AppBwBorrowerController.updatePwd】系统异常phone=" + phone, e);
			result.setCode("111");
			result.setMsg("系统异常");
			return result;
		}
		return result;
	}
}