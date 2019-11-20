package com.waterelephant.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.entity.BwAuthCodeRecord;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOfficialAccounts;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.service.ActivityInfoService;
import com.waterelephant.service.BwAuthCodeRecordService;
import com.waterelephant.service.BwBorrowerDetailService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOfficialAccountsService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.EncryptionUtil;
import com.waterelephant.utils.NewVerifyCodeUtils;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.utils.VerifyCodeUtils;

/**
 * app借款人控制器
 * 
 * @author lujilong
 *
 */
@Controller
@RequestMapping("/app/borrower")
public class AppBwBorrowerController {

	private Logger logger = Logger.getLogger(AppBwBorrowerController.class);

	@Autowired
	private IBwBorrowerService bwBorrowerService;

	@Autowired
	private IBwOfficialAccountsService bwOfficialAccountsService;

	@Autowired
	private IBwOrderChannelService bwOrderChannelService;

	@Autowired
	private BwAuthCodeRecordService bwAuthCodeRecordService;

	@Autowired
	private BwBorrowerDetailService bwBorrowerDetailService;

	@Autowired
	private ActivityInfoService activityInfoService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login.do")
	public AppResponseResult login(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String mobileSeq = request.getParameter("mobileSeq");

		result.setCode("401");
		result.setMsg("请更新最新版本");
		return result;

//		if (CommUtils.isNull(phone)) {
//			result.setCode("401");
//			result.setMsg("手机号为空");
//			return result;
//		}
//		if (CommUtils.isNull(password)) {
//			result.setCode("402");
//			result.setMsg("密码为空");
//			return result;
//		}
//		if (CommUtils.isNull(mobileSeq)) {
//			result.setCode("404");
//			result.setMsg("手机设备号为空");
//			return result;
//		}
//		boolean checkUpdate = false;
//		BwBorrower bo = new BwBorrower();
//		bo.setMobileSeq(mobileSeq);
//		bo = bwBorrowerService.findBwBorrowerByAttr(bo);
//		if (!CommUtils.isNull(bo)) {
//			// 判断设备号是否对应登录的手机号
//			if (!phone.equals(bo.getPhone())) {
//				result.setCode("406");
//				result.setMsg("该设备已绑定用户");
//				logger.info("登录的手机号为:" + phone);
//				logger.info("改设备绑定的用户手机号为:" + bo.getPhone());
//				return result;
//			}
//		} else {
//			checkUpdate = true;
//			logger.info("根据传入的设备号没有找到对应的借款人,需要绑定");
//		}
//
//		BwBorrower bw = new BwBorrower();
//		bw.setPhone(phone);
//		bw.setPassword(CommUtils.getMD5(password.getBytes()));
//		bw.setFlag(1);// 未删除的
//		bw = bwBorrowerService.findBwBorrowerByAttr(bw);
//		if (!CommUtils.isNull(bw)) {
//			// 判断用户是否禁用
//			if (1 == bw.getState()) {
//				if (checkUpdate) {
//					bw.setMobileSeq(mobileSeq);
//					bwBorrowerService.updateBwBorrower(bw);
//					logger.info("更新手机号设备成功");
//					logger.info("更新的手机号为:" + bw.getPhone());
//					logger.info("更新的设备号为:" + bw.getMobileSeq());
//				}
//				String token = CommUtils.getUUID();
//				request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
//				request.getSession().setAttribute(token, bw.getId());
//				SystemConstant.SESSION_APP_TOKEN.put(bw.getId().toString(), token);
//				logger.info("借款app登录令牌:" + token);
//				result.setCode("000");
//				result.setMsg("登录成功");
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("loginToken", token);
//				map.put("loginUser", bw);
//				// 是否支持分期
//				Integer channel = StringUtil.toInteger(bw.getChannel());
//				if (11 == channel || 12 == channel || 81 == channel || 261 == channel || 272 == channel
//						|| 288 == channel || 289 == channel) {
//					map.put("canInstallment", "0");// 不支持
//				} else {
//					map.put("canInstallment", "1");// 支持
//				}
//				result.setResult(map);
//				return result;
//			} else {
//				result.setCode("405");
//				result.setMsg("用户已被禁用");
//				return result;
//			}
//		} else {
//			result.setCode("403");
//			result.setMsg("用户名或密码错误");
//			return result;
//		}
	}

	/**
	 * 微信登录
	 * 
	 * @author buzhongshan
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/weiXinLogin.do")
	public AppResponseResult weiXinLogin(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		// String openId = request.getParameter("openId");
		String openId = (String) request.getSession().getAttribute(SystemConstant.WEIXIN_OPENID_TOKEN);

		result.setCode("401");
		result.setMsg("请更新最新版本");
		return result;

//		if (CommUtils.isNull(phone)) {
//			result.setCode("401");
//			result.setMsg("手机号为空");
//			return result;
//		}
//		if (CommUtils.isNull(password)) {
//			result.setCode("402");
//			result.setMsg("密码为空");
//			return result;
//		}
//		BwBorrower bw = new BwBorrower();
//		bw.setPhone(phone);
//		bw.setPassword(CommUtils.getMD5(password.getBytes()));
//		bw.setFlag(1);// 未删除的
//		bw = bwBorrowerService.findBwBorrowerByAttr(bw);
//		if (!CommUtils.isNull(bw)) {
//			// 判断用户是否禁用
//			if (1 == bw.getState()) {
//				// openId不为空时才做绑定操作
//				if (!CommUtils.isNull(openId)) {
//					BwOfficialAccounts accounts = new BwOfficialAccounts();
//					accounts.setOpenId(openId);
//					BwOfficialAccounts officialAccounts = bwOfficialAccountsService
//							.findBwOfficialAccountsByAttr(accounts);
//					// openId是否存在记录
//					if (CommUtils.isNull(officialAccounts)) {
//						accounts.setUserId(bw.getId());
//						// 添加公众号与用户关联信息
//						bwOfficialAccountsService.addOfficialAccounts(accounts);
//						logger.info("添加公众号记录:" + openId);
//					} else {
//						// 当前用户ID是否与openId关联用户ID相同
//						if (!bw.getId().equals(officialAccounts.getUserId())) {
//							result.setCode("406");
//							result.setMsg("登录失败");
//							return result;
//						}
//					}
//				}
//
//				String token = CommUtils.getUUID();
//				request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
//				request.getSession().setAttribute(token, bw.getId());
//				SystemConstant.SESSION_APP_TOKEN.put(bw.getId().toString(), token);
//				logger.info("借款app登录令牌:" + token);
//				result.setCode("000");
//				result.setMsg("登录成功");
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("loginToken", token);
//				map.put("loginUser", bw);
//				// 是否支持分期
//				Integer channel = StringUtil.toInteger(bw.getChannel());
//				if (11 == channel || 12 == channel || 81 == channel || 261 == channel || 272 == channel
//						|| 288 == channel || 289 == channel) {
//					map.put("canInstallment", "0");// 不支持
//				} else {
//					map.put("canInstallment", "1");// 支持
//				}
//				Cookie uidcookie = new Cookie("cookie_uuid", bw.getId().toString());
//				uidcookie.setPath("/");
//				Cookie tokencookie = new Cookie("cookie_token", token);
//				tokencookie.setPath("/");
//				response.addCookie(uidcookie);
//				response.addCookie(tokencookie);
//
//				result.setResult(map);
//				return result;
//			} else {
//				result.setCode("405");
//				result.setMsg("用户已被禁用");
//				return result;
//			}
//		} else {
//			result.setCode("403");
//			result.setMsg("用户名或密码错误");
//			return result;
//		}
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register.do")
	public AppResponseResult register(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String phoneCode = request.getParameter("phoneCode");
		String invitation = request.getParameter("invitation");
		String mobileSeq = request.getParameter("mobileSeq");

		result.setCode("401");
		result.setMsg("请更新最新版本");
		return result;

//		if (CommUtils.isNull(phoneCode)) {
//			result.setCode("406");
//			result.setMsg("验证码为空");
//			return result;
//		}
//		String sessionCode = (String) request.getSession().getAttribute(SystemConstant.APP_PHONE_CODE);
//		if (CommUtils.isNull(sessionCode) || !phoneCode.equals(sessionCode)) {
//			result.setCode("407");
//			result.setMsg("验证码错误");
//			return result;
//		}
//		if (CommUtils.isNull(phone)) {
//			result.setCode("401");
//			result.setMsg("手机号为空");
//			return result;
//		}
//		if (CommUtils.isNull(password)) {
//			result.setCode("402");
//			result.setMsg("密码为空");
//			return result;
//		}
//		if (CommUtils.isNull(mobileSeq)) {
//			result.setCode("408");
//			result.setMsg("手机设备号为空");
//			return result;
//		}
//		// 根据手机序列号获取注册账号信息
//		BwBorrower mobileSeqBw = new BwBorrower();
//		mobileSeqBw.setMobileSeq(mobileSeq);
//		mobileSeqBw.setFlag(1);// 未删除的
//		mobileSeqBw = bwBorrowerService.findBwBorrowerByAttr(mobileSeqBw);
//		if (!CommUtils.isNull(mobileSeqBw)) {
//			result.setCode("413");
//			result.setMsg("该设备已经绑定手机了");
//			return result;
//		}
//
//		BwBorrower valiBw = new BwBorrower();
//		valiBw.setPhone(phone);
//		valiBw.setFlag(1);// 未删除的
//		valiBw = bwBorrowerService.findBwBorrowerByAttr(valiBw);
//		if (!CommUtils.isNull(valiBw)) {
//			result.setCode("404");
//			result.setMsg("此手机号已经抢先一步注册了");
//			return result;
//		}
//
//		BwBorrower bw = new BwBorrower();
//		// 验证邀请码
//		if (StringUtils.isNotBlank(invitation)) {
//			BwBorrower tmp = new BwBorrower();
//			tmp.setInviteCode(invitation);
//			tmp = bwBorrowerService.findBwBorrowerByAttr(tmp);
//			// 没有找到对应的借款人
//			if (tmp == null) {
//				result.setCode("412");
//				result.setMsg("邀请码错误");
//				return result;
//			} else {
//				logger.info("借款人邀请人标识：" + tmp.getId());
//				bw.setBorrowerId(tmp.getId());
//			}
//		}
//		bw.setPhone(phone);
//		bw.setPassword(CommUtils.getMD5(password.getBytes()));
//		bw.setCreateTime(new Date());
//		bw.setAuthStep(1);
//		bw.setFlag(1);
//		bw.setState(1);
//		bw.setMobileSeq(mobileSeq);
//		int resultNum = bwBorrowerService.addBwBorrower(bw);
//		logger.info("借款人标识：" + bw.getId());
//		String inviteCode = RecommendCodeUtil.toSerialCode(bw.getId());
//		logger.info("借款人邀请码：" + inviteCode);
//		bw.setInviteCode(inviteCode);
//		bwBorrowerService.updateBwBorrower(bw);
//		if (resultNum > 0) {
//			String token = CommUtils.getUUID();
//			request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
//			request.getSession().setAttribute(token, bw.getId());
//			SystemConstant.SESSION_APP_TOKEN.put(bw.getId().toString(), token);
//			logger.info("借款app注册令牌:" + token);
//			result.setCode("000");
//			result.setMsg("注册成功");
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("loginToken", token);
//			map.put("loginUser", bw);
//			result.setResult(map);
//			request.getSession().removeAttribute(SystemConstant.APP_PHONE_CODE);
//			return result;
//		} else {
//			result.setCode("405");
//			result.setMsg("注册失败");
//			return result;
//		}
	}

	/**
	 * 注册(2016-10-13)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/newRegister.do")
	public AppResponseResult newRegister(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String phoneCode = request.getParameter("phoneCode");
		String invitation = request.getParameter("invitation");
		String mobileSeq = request.getParameter("mobileSeq");
		String city = request.getParameter("city");
		String appStoreCode = request.getParameter("appStoreCode");// 应用市场编码（安卓）
		String apptab = request.getParameter("apptab"); // 来源

		result.setCode("401");
		result.setMsg("请更新最新版本");
		return result;

//		if (CommUtils.isNull(phoneCode)) {
//			result.setCode("406");
//			result.setMsg("验证码为空");
//			return result;
//		}
//		String sessionCode = (String) request.getSession().getAttribute(SystemConstant.APP_PHONE_CODE);
//		logger.info("app发送的验证码：" + phoneCode + ",session中验证码：" + sessionCode);
//		if (CommUtils.isNull(sessionCode) || !phoneCode.equals(sessionCode)) {
//			result.setCode("407");
//			result.setMsg("验证码错误");
//			return result;
//		}
//		if (CommUtils.isNull(phone)) {
//			result.setCode("401");
//			result.setMsg("手机号为空");
//			return result;
//		}
//		if (CommUtils.isNull(password)) {
//			result.setCode("402");
//			result.setMsg("密码为空");
//			return result;
//		}
//		if (CommUtils.isNull(mobileSeq)) {
//			result.setCode("408");
//			result.setMsg("手机设备号为空");
//			return result;
//		}
//		// 根据手机序列号获取注册账号信息
//		BwBorrower mobileSeqBw = new BwBorrower();
//		mobileSeqBw.setMobileSeq(mobileSeq);
//		mobileSeqBw.setFlag(1);// 未删除的
//		mobileSeqBw = bwBorrowerService.findBwBorrowerByAttr(mobileSeqBw);
//		if (!CommUtils.isNull(mobileSeqBw)) {
//			result.setCode("413");
//			result.setMsg("该设备已经绑定手机了");
//			return result;
//		}
//
//		BwBorrower valiBw = new BwBorrower();
//		valiBw.setPhone(phone);
//		valiBw.setFlag(1);// 未删除的
//		valiBw = bwBorrowerService.findBwBorrowerByAttr(valiBw);
//		if (!CommUtils.isNull(valiBw)) {
//			result.setCode("404");
//			result.setMsg("此手机号已经抢先一步注册了");
//			return result;
//		}
//
//		BwBorrower bw = new BwBorrower();
//		// 验证邀请码
//		if (StringUtils.isNotBlank(invitation)) {
//			BwBorrower tmp = new BwBorrower();
//			tmp.setInviteCode(invitation);
//			tmp = bwBorrowerService.findBwBorrowerByAttr(tmp);
//			// 没有找到对应的借款人
//			if (tmp == null) {
//				result.setCode("412");
//				result.setMsg("邀请码错误");
//				return result;
//			} else {
//				logger.info("借款人邀请人标识：" + tmp.getId());
//				bw.setBorrowerId(tmp.getId());
//			}
//		}
//		bw.setPhone(phone);
//		bw.setPassword(CommUtils.getMD5(password.getBytes()));
//		bw.setCreateTime(new Date());
//		bw.setAuthStep(1);
//		bw.setFlag(1);
//		bw.setState(1);
//		if (!CommUtils.isNull(apptab)) {
//			bw.setChannel(Integer.valueOf(apptab));
//		}
//
//		bw.setMobileSeq(mobileSeq);
//		if (!CommUtils.isNull(city)) {
//			logger.info("注册地址为：" + city.replaceAll("您当前所在城市：", ""));
//			bw.setRegisterAddr(city.replaceAll("您当前所在城市：", ""));
//		}
//		int resultNum = bwBorrowerService.addBwBorrower(bw);
//		logger.info("借款人标识：" + bw.getId());
//		String inviteCode = RecommendCodeUtil.toSerialCode(bw.getId());
//		logger.info("借款人邀请码：" + inviteCode);
//		bw.setInviteCode(inviteCode);
//		bwBorrowerService.updateBwBorrower(bw);
//
//		// 保存借款人详情
//		Date nowDate = new Date();
//		BwBorrowerDetail bwBorrowerDetail = new BwBorrowerDetail();
//		bwBorrowerDetail.setBorrowerId(bw.getId());
//		bwBorrowerDetail.setAppStoreCode(appStoreCode);
//		bwBorrowerDetail.setCreateTime(nowDate);
//		bwBorrowerDetail.setUpdateTime(nowDate);
//		bwBorrowerDetailService.insertSelective(bwBorrowerDetail);
//		if (resultNum > 0) {
//			String token = CommUtils.getUUID();
//			request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
//			request.getSession().setAttribute(token, bw.getId());
//			SystemConstant.SESSION_APP_TOKEN.put(bw.getId().toString(), token);
//			logger.info("借款app注册令牌:" + token);
//			result.setCode("000");
//			result.setMsg("注册成功");
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("loginToken", token);
//			map.put("loginUser", bw);
//			result.setResult(map);
//			request.getSession().removeAttribute(SystemConstant.APP_PHONE_CODE);
//			return result;
//		} else {
//			result.setCode("405");
//			result.setMsg("注册失败");
//			return result;
//		}
	}

	/**
	 * 微信 注册 wrh
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/weiXinRegister.do", method = RequestMethod.POST)
	public AppResponseResult webRegister(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		result.setCode("401");
		result.setMsg("请更新最新版本");
		return result;
//		try {
//			String phone = request.getParameter("phone");
//			String password = request.getParameter("password");
//			String phoneCode = request.getParameter("phoneCode");
//			String invitation = request.getParameter("invitation");
//
//			// 新增加两个参数，borrower_id 和 activity_type,用户注册加抽奖机会====start
//			String borrower_id = request.getParameter("borrower_id");
//			String activity_type = request.getParameter("activity_type");
//			// ====end
//
//			String openId = (String) request.getSession().getAttribute(SystemConstant.WEIXIN_OPENID_TOKEN);
//			String city = (String) request.getSession().getAttribute(SystemConstant.WEIXIN_LOCATION_TOKEN);
//			if (CommUtils.isNull(phoneCode)) {
//				result.setCode("406");
//				result.setMsg("验证码为空");
//				return result;
//			}
//			String sessionCode = (String) request.getSession().getAttribute(SystemConstant.APP_PHONE_CODE);
//			if (CommUtils.isNull(sessionCode) || !phoneCode.equals(sessionCode)) {
//				result.setCode("407");
//				result.setMsg("验证码错误");
//				return result;
//			}
//			if (CommUtils.isNull(phone)) {
//				result.setCode("401");
//				result.setMsg("手机号为空");
//				return result;
//			}
//			if (CommUtils.isNull(password)) {
//				result.setCode("402");
//				result.setMsg("密码为空");
//				return result;
//			}
//			BwBorrower valiBw = new BwBorrower();
//			valiBw.setPhone(phone);
//			valiBw.setFlag(1);// 未删除的
//			valiBw = bwBorrowerService.findBwBorrowerByAttr(valiBw);
//			if (!CommUtils.isNull(valiBw)) {
//				result.setCode("404");
//				result.setMsg("此手机号已经抢先一步注册了");
//				return result;
//			}
//			//////////////////////////////////////
//			// openId不为空时判断是否注册过
//			if (!CommUtils.isNull(openId)) {
//				BwOfficialAccounts accounts = new BwOfficialAccounts();
//				accounts.setOpenId(openId);
//				BwOfficialAccounts officialAccounts = bwOfficialAccountsService.findBwOfficialAccountsByAttr(accounts);
//				// openId是否存在记录
//				if (!CommUtils.isNull(officialAccounts)) {
//					valiBw.setId(officialAccounts.getUserId());
//					valiBw.setFlag(1);// 未删除的
//					valiBw = bwBorrowerService.findBwBorrowerByAttr(valiBw);
//					if (!CommUtils.isNull(valiBw)) {
//						logger.info("公众号已存在:" + openId);
//						result.setCode("408");
//						result.setMsg("注册失败");
//						return result;
//					}
//				}
//			}
//			/////////////////////////////////
//			BwBorrower bw = new BwBorrower();
//			// 验证邀请码
//			if (StringUtils.isNotBlank(invitation)) {
//				BwBorrower tmp = new BwBorrower();
//				tmp.setInviteCode(invitation);
//				tmp = bwBorrowerService.findBwBorrowerByAttr(tmp);
//				// 没有找到对应的借款人
//				if (tmp == null) {
//					result.setCode("412");
//					result.setMsg("邀请码错误");
//					return result;
//				} else {
//					logger.info("借款人邀请人标识：" + tmp.getId());
//					bw.setBorrowerId(tmp.getId());
//				}
//			}
//			bw.setPhone(phone);
//			bw.setPassword(CommUtils.getMD5(password.getBytes()));
//			bw.setCreateTime(new Date());
//			bw.setAuthStep(1);
//			bw.setFlag(1);
//			bw.setState(1);
//			if (!CommUtils.isNull(city)) {
//				logger.info("注册地址为：" + city);
//				bw.setRegisterAddr(city);
//			}
//			bw.setMobileSeq(openId);
//			// 设置注册渠道
//			String channelId = request.getParameter("channelId");
//			if (!CommUtils.isNull(channelId)) {
//				bw.setChannel(Integer.valueOf(channelId));
//			} else {
//				bw.setChannel(4); // 微信
//			}
//
//			int resultNum = bwBorrowerService.addBwBorrower(bw);
//			logger.info("借款人标识：" + bw.getId());
//			String inviteCode = RecommendCodeUtil.toSerialCode(bw.getId());
//			logger.info("借款人邀请码：" + inviteCode);
//			bw.setInviteCode(inviteCode);
//			bwBorrowerService.updateBwBorrower(bw);
//			////////////////////////////////////////////////////////
//			// openId不为空，做绑定系统用户操作
//			if (!CommUtils.isNull(openId)) {
//				BwOfficialAccounts accounts = new BwOfficialAccounts();
//				accounts.setUserId(bw.getId());
//				accounts.setOpenId(openId);
//				// 添加公众号与用户关联信息
//				bwOfficialAccountsService.addOfficialAccounts(accounts);
//				logger.info("添加公众号记录:" + openId);
//			}
//
//			////////////////////////////////////////////////////////
//			if (resultNum > 0) {
//				String token = CommUtils.getUUID();
//				request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
//				request.getSession().setAttribute(token, bw.getId());
//				SystemConstant.SESSION_APP_TOKEN.put(bw.getId().toString(), token);
//				logger.info("借款app注册令牌:" + token);
//				result.setCode("000");
//				result.setMsg("注册成功");
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("loginToken", token);
//				map.put("loginUser", bw);
//
//				Cookie uidcookie = new Cookie("cookie_uuid", bw.getId().toString());
//				uidcookie.setPath("/");
//				Cookie tokencookie = new Cookie("cookie_token", token);
//				tokencookie.setPath("/");
//				response.addCookie(uidcookie);
//				response.addCookie(tokencookie);
//
//				result.setResult(map);
//				request.getSession().removeAttribute(SystemConstant.APP_PHONE_CODE);
//
//				// 抽奖活动新增====start
//				// 如果是通过活动抽奖注册，给邀请人加抽奖机会，并给邀请总数加1
//				if (!CommUtils.isNull(activity_type)) {
//					if (activity_type.equals("3") && !CommUtils.isNull(borrower_id)) {
//						ActivityInfo activity = new ActivityInfo();
//						activity.setActivityType("3");// 抽奖
//						activity.setStatus(1);// 是否启用活动（0、否，1、是）
//						activity = activityInfoService.queryActivityInfo(activity);
//						if (!CommUtils.isNull(activity)) {
//							Long start_time = activity.getStartTime().getTime();
//							Long end_time = activity.getEndTime().getTime();
//							Long cur_time = new Date().getTime();
//							if (start_time <= cur_time && cur_time <= end_time) {// 判断是否在活动时间内
//								// 活动新增注册人数加1
//								if (RedisUtils.exists("activity:register:count")) {
//									int activity_register_count = Integer
//											.parseInt(RedisUtils.get("activity:register:count"));
//
//									RedisUtils.set("activity:register:count",
//											String.valueOf(activity_register_count + 1));
//								} else {
//									RedisUtils.set("activity:register:count", "1");
//								}
//								int invite_count = 0;
//								if (RedisUtils.hexists("activity:invite:count", borrower_id)) {
//									invite_count = Integer
//											.parseInt(RedisUtils.hget("activity:invite:count", borrower_id));
//								}
//								if (invite_count < 20) {
//									int chance_count = 0;
//									if (RedisUtils.hexists("activity:chance:count", borrower_id)) {
//										chance_count = Integer
//												.parseInt(RedisUtils.hget("activity:chance:count", borrower_id));
//									}
//									RedisUtils.hset("activity:chance:count", borrower_id,
//											String.valueOf(chance_count + 1));
//								}
//								RedisUtils.hset("activity:invite:count", borrower_id, String.valueOf(invite_count + 1));
//							}
//						}
//					}
//				}
//				// ====end
//
//				return result;
//			} else {
//				result.setCode("405");
//				result.setMsg("注册失败");
//				return result;
//			}
//		} catch (Exception e) {
//			logger.error(e, e);
//			e.printStackTrace();
//			result.setCode("405");
//			result.setMsg("注册失败");
//			return result;
//		}
	}

	/**
	 * 首页 wrh
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/weiXinIndex.do")
	public AppResponseResult weiXinIndex(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();

		String openId = (String) request.getSession().getAttribute(SystemConstant.WEIXIN_OPENID_TOKEN);

		if (!CommUtils.isNull(openId)) {// openId有值
			BwOfficialAccounts bwOfficialAccounts = new BwOfficialAccounts();
			bwOfficialAccounts.setOpenId(openId);
			BwOfficialAccounts bws = bwOfficialAccountsService.findBwOfficialAccountsByAttr(bwOfficialAccounts);
			if (!CommUtils.isNull(bws)) {// 有关联
				BwBorrower bw = new BwBorrower();
				bw.setId(bws.getUserId());
				bw = bwBorrowerService.findBwBorrowerByAttr(bw);
				String token = CommUtils.getUUID();
				request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
				request.getSession().setAttribute(token, bw.getId());
				SystemConstant.SESSION_APP_TOKEN.put(bw.getId().toString(), token);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("loginToken", token);
				map.put("loginUser", bw);

				Cookie uidcookie = new Cookie("cookie_uuid", bw.getId().toString());
				uidcookie.setPath("/");
				Cookie tokencookie = new Cookie("cookie_token", token);
				tokencookie.setPath("/");
				response.addCookie(uidcookie);
				response.addCookie(tokencookie);

				result.setResult(map);
				result.setCode("000");
				result.setMsg("登录成功");
				return result;
			} else {// 无关联
				result.setCode("450");
				result.setMsg("登录失败");
				return result;
			}
		} else {
			result.setCode("450");
			result.setMsg("登录失败");
			return result;
		}
	}

	/**
	 * 检查手机号是否已经存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPhone.do")
	public AppResponseResult checkPhone(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		if (StringUtil.isEmpty(phone)) {
			result.setCode("-111");
			result.setMsg("手机号不能为空");
			logger.warn("checkPhone手机号为空");
			return result;
		}
		BwBorrower bw = new BwBorrower();
		bw.setPhone(phone);
		bw.setFlag(1);// 未删除的
		List<BwBorrower> list = bwBorrowerService.findBwBorrowerListByIdCard(bw);
		if (null == list || list.isEmpty()) {
			result.setCode("000");
			result.setMsg("此手机号未被使用");
		} else {
			result.setCode("-1111");
			result.setMsg("此手机号已被使用");
		}
		return result;
	}

	// /**
	// * 发送短信
	// *
	// * @param request
	// * @param response
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping("/sendMessage.do")
	// public AppResponseResult sendMessage(HttpServletRequest request, HttpServletResponse response) {
	// AppResponseResult result = new AppResponseResult();
	// String phone = request.getParameter("phone");
	// String pageToken = request.getParameter("pageToken");// app传入pagetoken
	// if (CommUtils.isNull(pageToken)) {
	// result.setCode("501");
	// result.setMsg("参数页面令牌为空");
	// return result;
	// }
	// String appPageToken = (String) request.getSession().getAttribute(SystemConstant.APP_PAGE_TOKEN);//
	// 获取当前会话中的pagetoken
	// if (CommUtils.isNull(pageToken) || CommUtils.isNull(appPageToken) || !appPageToken.equals(pageToken)) {
	// logger.info("发送短信请求接口页面令牌:" + pageToken);
	// logger.info("发送短信请求接口会话令牌:" + appPageToken);
	// result.setCode("502");
	// result.setMsg("令牌参数不匹配");
	// return result;
	// }
	// String phoneCode = CommUtils.getRandomNumber(6);
	// logger.info("短信验证码:" + phoneCode);
	// MsgReqData msgReqData = new MsgReqData();
	// msgReqData.setPhone(phone);
	// msgReqData.setMsg("【水象借点花】尊敬的水象借点花用户，您的手机验证码为：" + phoneCode + "，如非本人操作，请忽略此短信。为了您的账号安全，请勿泄露验证码！");
	// // 短信的环境 0 真是环境 1 测试环境
	// msgReqData.setType(SystemConstant.MESSAGE_CIRCUMSTANCE);
	//
	// Response<Object> rsp = BeadWalletSendMsgService.sendMsg(msgReqData);
	// logger.info("发送短信请求接口返回码:" + rsp.getRequestCode());
	// logger.info("发送短信请求接口返回消息:" + rsp.getRequestMsg());
	// if (rsp.getRequestCode().equals("200")) {
	// request.getSession().setAttribute(SystemConstant.APP_PHONE_CODE, phoneCode);
	// result.setCode("000");
	// result.setMsg("短信发送成功");
	// return result;
	// } else {
	// result.setCode(rsp.getRequestCode());
	// result.setMsg(rsp.getRequestMsg());
	// return result;
	// }
	// }

	/**
	 * App发送短信（加密防止被攻击）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendMessageEncryption.do")
	public AppResponseResult sendMessageEncryption(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		logger.info("加密的手机号码：" + phone);
		// 电话号码解密
		phone = EncryptionUtil.getPhone(phone);
		logger.info("解密的手机号码：" + phone);
		if (StringUtil.isEmpty(phone)) {

			// 往redis里面记录
			BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 0);
			bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);

			result.setCode("603");
			result.setMsg("手机号解密错误");
			return result;
		}
		// 频繁发送校验
		if (RedisUtils.exists("phone:" + phone)) {
			result.setCode("502");
			result.setMsg("发送验证码请求过于频繁，请于1分钟后再次发送");

			// 往redis里面记录
			BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 0);
			bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);

			return result;
		} else {
			RedisUtils.setex("phone:" + phone, phone, 60);// 设置当前号码发送标记
		}
		String phoneCountStr = RedisUtils.hget("phone:count", phone);
		if (!CommUtils.isNull(phoneCountStr)) {
			int phoneCount = Integer.parseInt(phoneCountStr);
			if (phoneCount >= 5) {
				result.setCode("502");
				result.setMsg("今天发送验证码次数已使用完，请您明天再试！");

				// 往redis里面记录
				BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 0);
				bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);
				return result;
			}
		} else {
			RedisUtils.hset("phone:count", phone, "0");
		}
		String pageToken = request.getParameter("pageToken");// app传入pagetoken
		if (CommUtils.isNull(pageToken)) {
			result.setCode("501");
			result.setMsg("参数页面令牌为空");

			// 往redis里面记录
			BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 0);
			bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);

			return result;
		}
		String appPageToken = (String) request.getSession().getAttribute(SystemConstant.APP_PAGE_TOKEN);// 获取当前会话中的pagetoken
		if (CommUtils.isNull(pageToken) || CommUtils.isNull(appPageToken) || !appPageToken.equals(pageToken)) {
			logger.info("发送短信请求接口页面令牌:" + pageToken);
			logger.info("发送短信请求接口会话令牌:" + appPageToken);

			// 往redis里面记录
			BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 0);
			bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);

			result.setCode("502");
			result.setMsg("令牌参数不匹配");
			return result;
		}
		String phoneCode = CommUtils.getRandomNumber(6);
		logger.info("短信验证码:" + phoneCode);
		// MsgReqData msgReqData = new MsgReqData();
		// msgReqData.setPhone(phone);
		// msgReqData.setMsg("【水象借点花】尊敬的水象借点花用户，您的手机验证码为：" + phoneCode + "，如非本人操作，请忽略此短信。为了您的账号安全，请勿泄露验证码！");
		// // 短信的环境 0 真是环境 1 测试环境
		// msgReqData.setType(SystemConstant.MESSAGE_CIRCUMSTANCE);
		//
		// Response<Object> rsp = BeadWalletSendMsgService.sendMsg(msgReqData);
		// logger.info("发送短信请求接口返回码:" + rsp.getRequestCode());
		// logger.info("发送短信请求接口返回消息:" + rsp.getRequestMsg());
		String message = "尊敬的水象借点花用户，您的手机验证码为：" + phoneCode + "，如非本人操作，请忽略此短信。为了您的账号安全，请勿泄露验证码！";
		// boolean bo = sendMessageCommonService.commonSendMessage(phone, message);
		MessageDto messageDto = new MessageDto();
		messageDto.setBusinessScenario("1");
		messageDto.setPhone(phone);
		messageDto.setMsg(message);
		messageDto.setType("1");
		RedisUtils.lpush("system:sendMessage", JSON.toJSONString(messageDto));
		boolean bo = true;
		logger.info("【AppBwBorrowerController.sendMessageEncryption】发送短信返回：" + bo);
		if (bo) {
			// if (rsp.getRequestCode().equals("200")) {
			RedisUtils.setex("phone:" + phone, phone, 60);// 设置当前号码发送标记
			String countStr = RedisUtils.hget("phone:count", phone);
			if (!CommUtils.isNull(countStr)) {
				int count = Integer.parseInt(countStr);
				count = count + 1;
				RedisUtils.hset("phone:count", phone, String.valueOf(count));
			} else {
				RedisUtils.hset("phone:count", phone, "1");
			}

			// 往redis里面记录
			BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 1, 0, 0);
			bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);

			request.getSession().setAttribute(SystemConstant.APP_PHONE_CODE, phoneCode);
			result.setCode("000");
			result.setMsg("短信发送成功");
			return result;
		} else {
			// result.setCode(rsp.getRequestCode());
			// result.setMsg(rsp.getRequestMsg());
			result.setCode("101");
			result.setMsg("短信发送失败！");
			return result;
		}
	}

	/**
	 * 获取页面token
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryPageToken.do")
	public AppResponseResult queryPageToken(HttpServletRequest request, HttpServletResponse response) {
		String pageToken = CommUtils.getUUID();
		AppResponseResult result = new AppResponseResult();
		result.setCode("000");
		result.setMsg("获取页面token成功");
		result.setResult(pageToken);
		logger.info("获取页面token:" + pageToken);
		request.getSession().setAttribute(SystemConstant.APP_PAGE_TOKEN, pageToken);
		return result;
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/updateBwBorrowerPwd.do")
	public AppResponseResult updateBwBorrowerPwd(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(oldPwd)) {
			result.setCode("401");
			result.setMsg("原密码为空");
			return result;
		}
		if (CommUtils.isNull(newPwd)) {
			result.setCode("402");
			result.setMsg("新密码为空");
			return result;
		}
		if (oldPwd.equals(newPwd)) {
			result.setCode("413");
			result.setMsg("原密码不能与新密码相同");
			return result;
		}
		if (CommUtils.isNull(bwId)) {
			result.setCode("408");
			result.setMsg("借款人id为空");
			return result;
		}
		BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bwId));
		// 判断原密码是否正确
		if (!bb.getPassword().equals(CommUtils.getMD5(oldPwd.getBytes()))) {
			result.setCode("411");
			result.setMsg("原密码不正确");
			return result;
		}
		bb.setPassword(CommUtils.getMD5(newPwd.getBytes()));
		bb.setUpdateTime(new Date());
		int num = bwBorrowerService.updateBwBorrower(bb);
		if (num > 0) {
			result.setCode("000");
			result.setMsg("密码修改成功");
			request.getSession().removeAttribute(SystemConstant.APP_PHONE_CODE);
			return result;
		} else {
			result.setCode("409");
			result.setMsg("密码修改失败");
			return result;
		}
	}

	/**
	 * 忘记密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetBwBorrowerPwd.do")
	public AppResponseResult resetBwBorrowerPwd(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		result.setCode("9999");
		result.setMsg("Please download the new App~");//dinglinhao 2019-01-22
		return result;
		
		
//		String phoneCode = request.getParameter("phoneCode");
//		String phone = request.getParameter("phone");
//		String pwd = request.getParameter("pwd");
//		if (CommUtils.isNull(phoneCode)) {
//			result.setCode("406");
//			result.setMsg("验证码为空");
//			return result;
//		}
//		String sessionCode = (String) request.getSession().getAttribute(SystemConstant.APP_PHONE_CODE);
//		if (CommUtils.isNull(sessionCode) || !sessionCode.equals(phoneCode)) {
//			result.setCode("407");
//			result.setMsg("验证码错误");
//			return result;
//		}
//		if (CommUtils.isNull(phone)) {
//			result.setCode("401");
//			result.setMsg("手机号为空");
//			return result;
//		}
//		if (CommUtils.isNull(pwd)) {
//			result.setCode("402");
//			result.setMsg("密码为空");
//			return result;
//		}
//		BwBorrower bb = new BwBorrower();
//		bb.setPhone(phone);
//		bb = bwBorrowerService.findBwBorrowerByAttr(bb);
//		List<BwBorrower> list = bwBorrowerService.findBwBorrowerListByIdCard(bb);
//		if (null == list || list.isEmpty()) {
//			result.setCode("410");
//			result.setMsg("账户不存在");
//			return result;
//		}
//		bb.setPassword(CommUtils.getMD5(pwd.getBytes()));
//		bb.setUpdateTime(new Date());
//		int num = bwBorrowerService.updateBwBorrower(bb);
//		if (num > 0) {
//			result.setCode("000");
//			result.setMsg("密码重置成功");
//			request.getSession().removeAttribute(SystemConstant.APP_PHONE_CODE);
//			return result;
//		} else {
//			result.setCode("409");
//			result.setMsg("密码重置失败");
//			return result;
//		}
	}

	@ResponseBody
	@RequestMapping("/appCheckLogin/loginOut.do")
	public AppResponseResult loginOut(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		request.getSession().removeAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN);
		result.setCode("000");
		result.setMsg("退出登录成功");
		return result;
	}

	/**
	 * 登录的双重验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loginCheck.do")
	public AppResponseResult loginCheck(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		// loginToken
		String loginToken = request.getParameter("loginToken");
		HttpSession session = request.getSession();
		String appToken = (String) session.getAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN);
		logger.info("loginToken===" + loginToken);
		logger.info("appToken===" + appToken);
		if (!CommUtils.isNull(loginToken) && !CommUtils.isNull(appToken) && appToken.equals(loginToken)) {
			appResponseResult.setCode("000");
			appResponseResult.setMsg("登录成功");
		} else {
			appResponseResult.setCode("-1111");
			appResponseResult.setMsg("请登录");
		}
		logger.info("跳过登录验证");
		return appResponseResult;
	}

	/**
	 * 获取验证码图片
	 */
	@RequestMapping(value = "/getVerifyCode.do")
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		request.getSession().setAttribute(SystemConstant.APP_VERIFY_CODE, verifyCode);
		ServletOutputStream os = response.getOutputStream();
		NewVerifyCodeUtils.outputImage(116, 43, os, verifyCode);
		os.flush();
		os.close();
	}

	/**
	 * 获得渠道号
	 */
	@ResponseBody
	@RequestMapping("/getChannel.do")
	public AppResponseResult getChannel(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String channelCode = request.getParameter("channelCode");
		if (CommUtils.isNull(channelCode)) {
			result.setCode("501");
			result.setMsg("渠道编码为空");
			return result;
		}
		try {
			BwOrderChannel channel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			if (CommUtils.isNull(channel)) {
				result.setCode("502");
				result.setMsg("渠道编码不存在");
				return result;
			}
			request.getSession().setAttribute(SystemConstant.ORDER_CHENNEL_TOKEN, channel.getId());
			result.setCode("000");
			result.setMsg("获得渠道号成功");
			result.setResult(channel.getId());
			return result;
		} catch (Exception e) {
			logger.error("获得渠道号接口异常，传入参数[channelCode=" + channelCode + "]，异常信息：" + e.getMessage());
			e.printStackTrace();
			result.setCode("500");
			result.setMsg("系统错误");
			return result;
		}
	}

	/**
	 * 发送短信验证码（图片验证码防止被攻击）
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendMessageImage.do")
	public AppResponseResult sendMessageImage(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		if (CommUtils.isNull(phone)) {

			// 往redis里面记录
			BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 1);
			bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);

			result.setCode("501");
			result.setMsg("参数手机号码为空");
			return result;
		}
		String verifyCode = request.getParameter("verifyCode");// 验证码
		logger.info("接收到的图片验证码：" + verifyCode);
		String oriVerifyCode = (String) request.getSession().getAttribute(SystemConstant.APP_VERIFY_CODE);
		logger.info("session中的图片验证码：" + oriVerifyCode);
		if (StringUtil.isEmpty(verifyCode)) {
			result.setCode("701");
			result.setMsg("图片验证码不能为空");

			// 往redis里面记录
			BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 1);
			bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);
			return result;
		}
		if (!verifyCode.equalsIgnoreCase(oriVerifyCode)) {
			result.setCode("702");
			result.setMsg("图片验证码输入错误");

			// 往redis里面记录
			BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 1);
			bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);

			return result;
		}
		try {
			// 频繁发送校验
			if (RedisUtils.exists("phone:" + phone)) {
				result.setCode("502");
				result.setMsg("发送验证码请求过于频繁，请于1分钟后再次发送");

				// 往redis里面记录
				BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 1);
				bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);
				return result;
			} else {
				RedisUtils.setex("phone:" + phone, phone, 60);// 设置当前号码发送标记
			}
			String phoneCountStr = RedisUtils.hget("phone:count", phone);
			if (!CommUtils.isNull(phoneCountStr)) {
				int phoneCount = Integer.parseInt(phoneCountStr);
				if (phoneCount >= 5) {
					result.setCode("502");
					result.setMsg("今天发送验证码次数已使用完，请您明天再试！");

					// 往redis里面记录
					BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 0, 1, 1);
					bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);
					return result;
				}
			} else {
				RedisUtils.hset("phone:count", phone, "0");
			}
			String phoneCode = CommUtils.getRandomNumber(6);
			logger.info("短信验证码:" + phoneCode);

			String msg = "尊敬的水象借点花用户，您的手机验证码为：" + phoneCode + "，如非本人操作，请忽略此短信。为了您的账号安全，请勿泄露验证码！";
			MessageDto messageDto = new MessageDto();
			messageDto.setBusinessScenario("1");
			messageDto.setPhone(phone);
			messageDto.setMsg(msg);
			messageDto.setType("1");
			RedisUtils.lpush("system:sendMessage", JSON.toJSONString(messageDto));
			boolean bo = true;
			if (bo) {
				RedisUtils.setex("phone:" + phone, phone, 60);// 设置当前号码发送标记
				String countStr = RedisUtils.hget("phone:count", phone);
				if (!CommUtils.isNull(countStr)) {
					int count = Integer.parseInt(countStr);
					count = count + 1;
					RedisUtils.hset("phone:count", phone, String.valueOf(count));
				} else {
					RedisUtils.hset("phone:count", phone, "1");
				}
				request.getSession().setAttribute(SystemConstant.APP_PHONE_CODE, phoneCode);
				result.setCode("000");
				result.setMsg("短信发送成功");

				// 往redis里面记录
				BwAuthCodeRecord bwAuthCodeRecord = new BwAuthCodeRecord(1, 1, 0, 1);
				bwAuthCodeRecordService.addDataToBwAuthCodeRecord(bwAuthCodeRecord);
				return result;
			} else {
				result.setCode("101");
				result.setMsg("短信发送失败");
				return result;
			}
		} catch (Exception e) {
			logger.error("发送短信验证码接口出错：" + e.getMessage());
			e.printStackTrace();
			result.setCode("500");
			result.setMsg("发送失败");
			return result;
		}
	}

	// /**
	// * 发送短信验证码
	// *
	// * @param request
	// * @param response
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping("/sendMessageGoodBoyForH5.do")
	// public AppResponseResult sendMessageGoodBoyForH5(HttpServletRequest request, HttpServletResponse response) {
	// AppResponseResult result = new AppResponseResult();
	// String phone = request.getParameter("phone");
	// if (CommUtils.isNull(phone)) {
	// result.setCode("501");
	// result.setMsg("参数手机号码为空");
	// return result;
	// }
	// logger.info("h5传的手机号码：" + phone);
	// // 电话号码解密
	// phone = EncryptionUtil.getPhoneForH5(phone);
	// logger.info("解密的手机号码：" + phone);
	// if (StringUtil.isEmpty(phone)) {
	// result.setCode("603");
	// result.setMsg("手机号解密错误");
	// return result;
	// }
	// try {
	// // 频繁发送校验
	// if (RedisUtils.exists("phone:" + phone)) {
	// result.setCode("502");
	// result.setMsg("发送验证码请求过于频繁，请于1分钟后再次发送");
	// return result;
	// } else {
	// RedisUtils.setex("phone:" + phone, phone, 60);// 设置当前号码发送标记
	// }
	// String phoneCountStr = RedisUtils.hget("phone:count", phone);
	// if (!CommUtils.isNull(phoneCountStr)) {
	// int phoneCount = Integer.parseInt(phoneCountStr);
	// if (phoneCount >= 5) {
	// result.setCode("502");
	// result.setMsg("今天发送验证码次数已使用完，请您明天再试！");
	// return result;
	// }
	// } else {
	// RedisUtils.hset("phone:count", phone, "0");
	// }
	// String phoneCode = CommUtils.getRandomNumber(6);
	// logger.info("短信验证码:" + phoneCode);
	// MsgReqData msgReqData = new MsgReqData();
	// msgReqData.setPhone(phone);
	// msgReqData.setMsg("【水象借点花】尊敬的水象借点花用户，您的手机验证码为：" + phoneCode + "，如非本人操作，请忽略此短信。为了您的账号安全，请勿泄露验证码！");
	// msgReqData.setType(SystemConstant.MESSAGE_CIRCUMSTANCE);
	//
	// Response<Object> rsp = BeadWalletSendMsgService.sendMsg(msgReqData);
	// logger.info("发送短信请求接口返回码:" + rsp.getRequestCode());
	// logger.info("发送短信请求接口返回消息:" + rsp.getRequestMsg());
	// if (rsp.getRequestCode().equals("200")) {
	// String countStr = RedisUtils.hget("phone:count", phone);
	// if (!CommUtils.isNull(countStr)) {
	// int count = Integer.parseInt(countStr);
	// count = count + 1;
	// RedisUtils.hset("phone:count", phone, String.valueOf(count));
	// } else {
	// RedisUtils.hset("phone:count", phone, "1");
	// }
	// request.getSession().setAttribute(SystemConstant.APP_PHONE_CODE, phoneCode);
	// result.setCode("000");
	// result.setMsg("短信发送成功");
	// return result;
	// } else {
	// result.setCode(rsp.getRequestCode());
	// result.setMsg(rsp.getRequestMsg());
	// return result;
	// }
	// } catch (Exception e) {
	// logger.error("发送短信验证码接口出错：" + e.getMessage());
	// e.printStackTrace();
	// result.setCode("500");
	// result.setMsg("发送失败");
	// return result;
	// }
	// }

	/**
	 * 发送语音验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/sendVoiceVerify.do")
	public AppResponseResult sendVoiceVerify(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		AppResponseResult result = new AppResponseResult();
		String appToken = (String) session.getAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN);
		Long borrowerId = (Long) session.getAttribute(appToken);
		logger.info("【AppBwBorrowerController.sendVoiceVerify】appToken：" + appToken
				+ "，从session中根据appToken获取borrowerId：" + borrowerId);
		Long requestBorrowerId = NumberUtil.parseLong(request.getParameter("borrowerId"), null);
		logger.info("【AppBwBorrowerController.sendVoiceVerify】appToken：" + appToken + "，从request中获取borrowerId："
				+ requestBorrowerId);
		if (borrowerId != null && requestBorrowerId != null && !borrowerId.equals(requestBorrowerId)) {// 防止攻击
			result.setCode("109");
			result.setMsg("用户未登录");
			logger.error("【AppBwBorrowerController.sendVoiceVerify】appToken：" + appToken + "，从request中获取borrowerId："
					+ requestBorrowerId + "和session中borrowerId：" + borrowerId + "不匹配");
			return result;
		}
		if (borrowerId == null) {
			borrowerId = requestBorrowerId;
		}
		BwBorrower queryBorrower = null;
		if (borrowerId != null && borrowerId > 0L) {
			queryBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
		}
		if (queryBorrower == null) {
			result.setCode("101");
			result.setMsg("用户未登录");
			return result;
		}
		String phone = queryBorrower.getPhone();
		logger.info("手机号码：" + phone);
		// 频繁发送校验
		if (RedisUtils.exists(RedisKeyConstant.VOICE_PHONE_TIME_PRE + phone)) {
			result.setCode("102");
			result.setMsg("您的操作过于频繁，请于1分钟后再次发送");
			return result;
		} else {
			RedisUtils.setex(RedisKeyConstant.VOICE_PHONE_TIME_PRE + phone, phone, 60);// 设置当前号码发送标记
		}

		String phoneCountStr = RedisUtils.hget(RedisKeyConstant.VOICE_PHONE_COUNT, borrowerId.toString());
		if (!CommUtils.isNull(phoneCountStr)) {
			int phoneCount = Integer.parseInt(phoneCountStr);
			if (phoneCount >= 5) {
				result.setCode("103");
				result.setMsg("您的操作过于频繁，请您明天再试！");
				return result;
			}
		} else {
			RedisUtils.hset(RedisKeyConstant.VOICE_PHONE_COUNT, borrowerId.toString(), "0");
		}

		// 生成验证码
		String voicePhoneCode = CommUtils.getRandomNumber(4);
		logger.info("borrowerId：" + borrowerId + "，语音验证码:" + voicePhoneCode);
		// boolean sendBool = sendMessageCommonService.commonSendMessageVoice(phone, voicePhoneCode);
		MessageDto messageDto = new MessageDto();
		messageDto.setBusinessScenario("1");
		messageDto.setPhone(phone);
		messageDto.setMsg(voicePhoneCode);
		messageDto.setType("2");
		RedisUtils.lpush("system:sendMessage", JSON.toJSONString(messageDto));
		boolean sendBool = true;
		if (sendBool) {
			RedisUtils.setex(RedisKeyConstant.VOICE_PHONE_TIME_PRE + phone, phone, 60);// 设置当前号码发送标记
			String countStr = RedisUtils.hget(RedisKeyConstant.VOICE_PHONE_COUNT, borrowerId.toString());
			if (!CommUtils.isNull(countStr)) {
				int count = Integer.parseInt(countStr);
				count = count + 1;
				RedisUtils.hset(RedisKeyConstant.VOICE_PHONE_COUNT, borrowerId.toString(), String.valueOf(count));
			} else {
				RedisUtils.hset(RedisKeyConstant.VOICE_PHONE_COUNT, borrowerId.toString(), "1");
			}

			result.setCode("000");
			result.setMsg("语音验证码发送成功");
			result.setResult(true);
			// 存入redis10分钟
			RedisUtils.setex(RedisKeyConstant.VOICE_CODE_PRE + borrowerId, voicePhoneCode, 10 * 60);
			return result;
		} else {
			result.setCode("108");
			result.setMsg("语音验证码发送失败");
			result.setResult(false);
			return result;
		}
	}
}