package com.waterelephant.haoDai.controller;

import org.springframework.stereotype.Controller;// code0002

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.HaoDaiResponseData;
import com.beadwallet.service.serve.BeadWalletHaoDaiService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.waterelephant.constants.OrderStatusConstant;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOrderTem;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.entity.HaoDaiResponse;
import com.waterelephant.haoDai.service.HaoDaiService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOrderTemService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.AESUtil;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.MD5Util;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: HaoDaiController
 * @Description: TODO(提供给好贷网接口)
 * @author SongYaJun
 * @date 2016年11月18日 上午11:41:05
 *
 */
@Controller
@RequestMapping("/bewadwallet/loan")
public class HaoDaiController {

	private Logger logger = Logger.getLogger(HaoDaiController.class);

	@Autowired
	private BwRejectRecordService bwRejectRecordService;

	@Autowired
	private BwBorrowerService bwBorrowerService;

	@Autowired
	private IBwBankCardService iBwBankCardService;

	// @Autowired
	// private IBwOperateService bwOperateService;

	@Autowired
	private HaoDaiService haoDaiService;

	@Resource
	private BwOperateVoiceService bwOperateVoiceService;

	@Resource
	private IBwOrderService iBwOrderService;

	@Resource
	private BwOrderTemService bwOrderTemService;

	@Resource
	private BwOrderRongService bwOrderRongService;

	@Resource
	private IBwOrderService bwOrderService;

	@Resource
	private BwZmxyGradeService bwZmxyGradeService;

	@Resource
	private BwOrderAuthService bwOrderAuthService;

	/**
	 * 用户信息预提交接口 获取好贷工单号
	 * 
	 * @param request
	 * @param response
	 * 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uerInfo.do")
	public HaoDaiResponse uerInfo(HttpServletRequest request, HttpServletResponse response) {
		String appid = request.getParameter("appid");
		String time = request.getParameter("time");
		String sign = request.getParameter("sign");
		String ver = request.getParameter("ver");
		HaoDaiResponse daiResponse = new HaoDaiResponse();
		daiResponse.setVersion("2.1"); // 返回接口版本
		if (CommUtils.isNull(time) || CommUtils.isNull(sign) || CommUtils.isNull(ver) || CommUtils.isNull(appid)) {
			daiResponse.setCode(4001);
			logger.info("接口2.1code" + daiResponse.getCode());
			daiResponse.setMessage("请求数据为空");
			return daiResponse;
		}
		String secret = ResourceBundle.getBundle("haodai").getString("secret");
		logger.info("secret的值为---------------------" + secret);
		try {
			String encodeSign = MD5Util.md5(time + secret);
			if (!encodeSign.substring(8, 24).equals(sign)) {
				daiResponse.setCode(4003);
				daiResponse.setMessage("签名错误");
				logger.info("接口2.1code" + daiResponse.getCode());
				return daiResponse;
			}
		} catch (Exception e) {
			daiResponse.setCode(1102); // 加密错误
			daiResponse.setMessage("数据加密错误");
			logger.error("接口2.1code" + daiResponse.getCode(), e);
			return daiResponse;
		}
		String content = request.getParameter("content");
		String jsonContent = null;
		String key = ResourceBundle.getBundle("haodai").getString("privateKey");
		logger.info("-------------------好贷网key" + key);
		try {
			jsonContent = AESUtil.Decrypt(content, key); // 传过来的数据进行解密
			logger.info("------------接口2.1解密数据" + jsonContent);
		} catch (Exception e) {
			daiResponse.setCode(1101);
			daiResponse.setMessage("数据解密错误");
			logger.info("接口2.1code" + daiResponse.getCode());
			return daiResponse;
		}
		if (CommUtils.isNull(jsonContent)) {
			daiResponse.setCode(4001);
			daiResponse.setMessage("合作机构 source 错误");
			return daiResponse;
		}
		try {
			Type type = new TypeToken<Map<String, String>>() {
				private static final long serialVersionUID = 1L;
			}.getType();
			Map<String, String> result = new Gson().fromJson(jsonContent, type);
			logger.info("接口2.1返回工单编号：" + result.get("orderId"));
			int count = 0;
			// 根据手机查询该用户是否为黑名单用户
			count = bwRejectRecordService.findRejectRecordByPhone(result.get("phone"));
			logger.info("---------------接口2.1用户是否为黑名单:" + count);
			// 返回值大于零则说明为我们平台黑名单用户不给予借款
			if (count > 0) {
				daiResponse.setCode(4025); // 合作机构推送的数据为我方黑名单用户
				daiResponse.setMessage("合作机构推送的数据为我方黑名单用户");
				return daiResponse;
			}
			// 调用接口2.2获取用户信息
			Response<HaoDaiResponseData> respData = BeadWalletHaoDaiService.getUserInfo(result.get("orderId"));
//			logger.info("---------------调用接口2.2获取用户信息:" + respData.getObj());
			if (respData.getRequestCode().equals("1000")) {
				logger.info("---------------调用接口2.2获取用户信息成功phone：" + result.get("phone"));
				if (!CommUtils.isNull(respData.getObj())) {
					// 第三方返回的所有数据入数据库
					Map<String, Long> maps = haoDaiService.addLoanUserInfo(respData, result.get("phone"));
					if (!CommUtils.isNull(maps)) {
						String josnStr = "{\"userId\":" + maps.get("userId") + ",\"orderId\":" + maps.get("orderId")
								+ "}";
						String enString = AESUtil.Encrypt(josnStr, key);
						daiResponse.setCode(1000);
						daiResponse.setMessage(enString);
						logger.info("------------接口调用成功返回数据：" + enString);
						return daiResponse;

					} else {
						daiResponse.setCode(4001);
						daiResponse.setMessage("合作机构 source 错误");
						return daiResponse;
					}
				} else {
					daiResponse.setCode(4001); // 接口2.2返回数据为null
					daiResponse.setMessage("合作机构 source 错误");
					return daiResponse;
				}
			} else {
				daiResponse.setCode(Integer.parseInt(respData.getRequestCode()));
				daiResponse.setMessage(respData.getRequestMsg());
//				logger.info("===========================接口2.2请求失败");
				return daiResponse;
			}
		} catch (Exception e) {
			daiResponse.setCode(4001);
			daiResponse.setMessage("接口2.2请求失败");
			e.printStackTrace();
		}
		return daiResponse;
	}

	@ResponseBody
	@RequestMapping("/uerInfoH5.do")
	public HaoDaiResponse uerInfoH5(HttpServletRequest request, HttpServletResponse response) {
		String appid = request.getParameter("appid");
		String time = request.getParameter("time");
		String sign = request.getParameter("sign");
		String ver = request.getParameter("ver");
		HaoDaiResponse daiResponse = new HaoDaiResponse();
		daiResponse.setVersion("2.1"); // 返回接口版本
		if (CommUtils.isNull(time) || CommUtils.isNull(sign) || CommUtils.isNull(ver) || CommUtils.isNull(appid)) {
			daiResponse.setCode(4001);
			logger.info("接口2.1code" + daiResponse.getCode());
			daiResponse.setMessage("请求数据为空");
			return daiResponse;
		}
		String secret = ResourceBundle.getBundle("haodai").getString("secret");
		logger.info("secret的值为---------------------" + secret);
		try {
			String encodeSign = MD5Util.md5(time + secret);
			if (!encodeSign.substring(8, 24).equals(sign)) {
				daiResponse.setCode(4003);
				daiResponse.setMessage("签名错误");
				logger.info("接口2.1code" + daiResponse.getCode());
				return daiResponse;
			}
		} catch (Exception e) {
			daiResponse.setCode(1102); // 加密错误
			daiResponse.setMessage("数据加密错误");
			logger.error("接口2.1code" + daiResponse.getCode(), e);
			return daiResponse;
		}
		String content = request.getParameter("content");
		String jsonContent = null;
		String key = ResourceBundle.getBundle("haodai").getString("privateKey");
		logger.info("-------------------好贷网key" + key);
		try {
			jsonContent = AESUtil.Decrypt(content, key); // 传过来的数据进行解密
			logger.info("------------接口2.1解密数据" + jsonContent);
		} catch (Exception e) {
			daiResponse.setCode(1101);
			daiResponse.setMessage("数据解密错误");
			logger.info("接口2.1code" + daiResponse.getCode());
			return daiResponse;
		}
		if (CommUtils.isNull(jsonContent)) {
			daiResponse.setCode(4001);
			daiResponse.setMessage("合作机构 source 错误");
			return daiResponse;
		}
		try {
			Type type = new TypeToken<Map<String, String>>() {
				private static final long serialVersionUID = 1L;
			}.getType();
			Map<String, String> result = new Gson().fromJson(jsonContent, type);
			logger.info("接口2.1返回工单编号：" + result.get("orderId"));
			int count = 0;
			// 根据手机查询该用户是否为黑名单用户
			count = bwRejectRecordService.findRejectRecordByPhone(result.get("phone"));
			logger.info("---------------接口2.1用户是否为黑名单:" + count);
			// 返回值大于零则说明为我们平台黑名单用户不给予借款
			if (count > 0) {
				daiResponse.setCode(4025); // 合作机构推送的数据为我方黑名单用户
				daiResponse.setMessage("合作机构推送的数据为我方黑名单用户");
				return daiResponse;
			}
			BwOrder bwOrder = iBwOrderService.findCheckOrder(result.get("phone"));
			if (!CommUtils.isNull(bwOrder)) {
				// 草稿状态时 变更
				if ("1".equals(CommUtils.toString(bwOrder.getStatusId()))) {
					iBwOrderService.updateChannel(bwOrder.getId(), "81");
					BwOrderRong bwOrderRong = bwOrderRongService.findBwOrderRongByOrderId(bwOrder.getId());
					if (CommUtils.isNull(bwOrderRong)) {
						bwOrderRong = new BwOrderRong();
						bwOrderRong.setOrderId(bwOrder.getId());
						bwOrderRong.setThirdOrderNo(result.get("orderId"));
						bwOrderRong.setChannelId(81L);
						bwOrderRong.setCreateTime(new Date());
						bwOrderRongService.save(bwOrderRong);
					} else {
						bwOrderRong.setThirdOrderNo(result.get("orderId"));
						bwOrderRong.setChannelId(81L);
						bwOrderRong.setCreateTime(new Date());
						bwOrderRongService.updateBwOrderRongNo(bwOrderRong);
					}
					daiResponse.setCode(1000);
					daiResponse.setMessage("操纵成功");
				} else {

					daiResponse.setCode(4035);
					daiResponse.setMessage("已存在未完成的订单。");
					// if ("12".equals(CommUtils.toString(bwOrder.getChannel()))
					// || "81".equals(CommUtils.toString(bwOrder.getChannel()))) {
					//
					// } else {
					// daiResponse.setCode(4035);
					// daiResponse.setMessage("已存在未完成的订单。");
					// }
					return daiResponse;
				}

			} else {
				// {"orderId":"201703091808119006","channelKey":"haodai","thirdId":2663,"phone":"18825220731","thirdVersion":1.1}

				String phone = result.get("phone");
				String orderId = result.get("orderId");
				String channelKey = result.get("channelKey");

				if (bwOrderTemService.getByPhonne(phone, channelKey) == null) {
					BwOrderTem bwOrderTem = new BwOrderTem();
					bwOrderTem.setThirdOrderNo(orderId);
					bwOrderTem.setChannelKey(channelKey);
					bwOrderTem.setPhone(phone);
					bwOrderTem.setCreateTime(new Date());
					bwOrderTemService.save(bwOrderTem);
				}
				daiResponse.setCode(1000);
				daiResponse.setMessage("操纵成功");
			}

		} catch (Exception e) {
			daiResponse.setCode(4001);
			daiResponse.setMessage("接口2.1请求失败");
			e.printStackTrace();
		}
		return daiResponse;
	}

	/**
	 * 对好贷提供统一访问接口: 包括运营商H5访问、签约
	 * 
	 * @return
	 */
	@RequestMapping("/haoDaiController.do")
	public String haoDaiController(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String requestURI = request.getRequestURI();
		logger.info("=============接口2.6请求地址:" + requestURI);
		String queryString = request.getQueryString();
//		logger.info("=======================借款2.6请求参数" + queryString);
		String appid = request.getParameter("appid");
		String time = request.getParameter("time");
		String sign = request.getParameter("sign");
		String ver = request.getParameter("ver");
		String content = request.getParameter("content");
		HaoDaiResponse daiResponse = new HaoDaiResponse();
		daiResponse.setVersion("2.6"); // 返回接口版本
		if (CommUtils.isNull(time) || CommUtils.isNull(sign) || CommUtils.isNull(ver) || CommUtils.isNull(appid)
				|| CommUtils.isNull(content)) {
			return "index";
		}
		String secret = ResourceBundle.getBundle("haodai").getString("secret");
		logger.info("secret的值为---------------------" + secret);
		try {
			String encodeSign = MD5Util.md5(time + secret);
			if (!encodeSign.substring(8, 24).equals(sign)) {
				return "index";
			}
			String jsonContent = null;
			String key = ResourceBundle.getBundle("haodai").getString("privateKey");
			logger.info("-------------------好贷网key" + key);
			jsonContent = AESUtil.Decrypt(content, key); // 传过来的数据进行解密
			logger.info("------------接口2.6解密数据" + jsonContent);
			if (CommUtils.isNull(jsonContent)) {
				return "index";
			}
			Type type = new TypeToken<Map<String, String>>() {
				private static final long serialVersionUID = 1L;
			}.getType();
			Map<String, String> result = new Gson().fromJson(jsonContent, type);
			logger.info("==========================接口2.6返回工单编号：" + result.get("orderId"));
			logger.info("==========================接口2.6返回用户id：" + result.get("userId"));
			Integer orderStatus = iBwOrderService.findOrderStatusByOrderId(Long.valueOf(result.get("orderId")));
			if (CommUtils.isNull(orderStatus)) {
				return "index";
			}
			if (orderStatus == 8 || orderStatus == 6) { // 拒绝和撤回和已还款
				return "index";
			}
			if (orderStatus == 1) {
				/************************ 查询是否签约,如果签约修工单改状态 ********************/
				Integer signStatus = iBwBankCardService.findSignStatusByBorrowerId(Long.valueOf(result.get("userId")));
				if (CommUtils.isNull(signStatus) || signStatus != 2) {
					// 重定向签约操作
					return "redirect:/loan/signing/signContract.do?userId=" + result.get("userId");
				}

				/********************** 查看一个月内是否调用芝麻评分,没有则调用 *******************/
				BwZmxyGrade bwZmxyGrade = bwZmxyGradeService
						.findZmxyGradeByBorrowerId(Long.valueOf(result.get("userId")));
				if (CommUtils.isNull(bwZmxyGrade)) {
					// 查询芝麻评分
					return "redirect:/app/zmxy/zmmcportal.do?orderId=" + result.get("orderId") + "&borrowerId="
							+ result.get("userId") + "&channel=" + 12 + "&queryString=" + queryString;
				} else {
					int daySpace = MyDateUtils.getDaySpace(bwZmxyGrade.getUpdateTime(), new Date());
					if (daySpace >= 30) {
						// 查询芝麻评分
						return "redirect:/app/zmxy/zmmcportal.do?orderId=" + result.get("orderId") + "&borrowerId="
								+ result.get("userId") + "&channel=" + 12 + "&queryString=" + queryString;
					}
				}

				/**************************** 跳转运营商 *****************************/
				String token = CommUtils.getUUID();
				request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
				SystemConstant.SESSION_APP_TOKEN.put(result.get("userId"), token);
				logger.info("==========================借款app登录令牌:" + token);
				Cookie uidcookie = new Cookie("cookie_uuid", result.get("userId"));
				uidcookie.setPath("/");
				Cookie tokencookie = new Cookie("cookie_token", token);
				tokencookie.setPath("/");
				response.addCookie(uidcookie);
				response.addCookie(tokencookie);
				modelMap.put("orderId", result.get("orderId"));
				return "yunyingshang";
			}
			String token = CommUtils.getUUID();
			request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
			SystemConstant.SESSION_APP_TOKEN.put(result.get("userId"), token);
			logger.info("==========================借款app登录令牌:" + token);
			Cookie uidcookie = new Cookie("cookie_uuid", result.get("userId"));
			uidcookie.setPath("/");
			Cookie tokencookie = new Cookie("cookie_token", token);
			tokencookie.setPath("/");
			response.addCookie(uidcookie);
			response.addCookie(tokencookie);
			// return "review";
			BwOrder order = bwOrderService.findBwOrderById(result.get("orderId"));
			if (order == null || order.getStatusId() == 9) {
				// 还款中状态 直接跳转首页
				// String pageUrl = ResourceBundle.getBundle("config").getString("page_url");
				// logger.info("好贷=======================重定向至:" + pageUrl + "loanpage/index.html");
				return "index";
			}
			String pageUrl = ResourceBundle.getBundle("config").getString("page_url");
			logger.info("好贷=======================重定向至:" + pageUrl + "loanpage/review.html");
			return "redirect:" + pageUrl + "loanpage/review.html";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return "index";
		}
	}

	/**
	 * 跳转首页
	 * 
	 * @return
	 */
	@RequestMapping("/yunyingshang.do")
	public String yunyingshang() {
		return "yunyingshang";
	}

	/**
	 * 跳转运营商页面
	 */
	@RequestMapping("/index.do")
	public String index() {
		return "index";
	}

	/**
	 * 跳转还款中页面
	 * 
	 * @return
	 */
	@RequestMapping("/repayNormal.do")
	public String repayNormal(HttpServletRequest request, ModelMap map) {
		String orderId = request.getParameter("orderId");
		map.put("orderId", orderId);
		return "repay_normal";
	}

	/**
	 * 跳转逾期页面
	 * 
	 * @return
	 */
	@RequestMapping("/repayLate.do")
	public String repayLate(HttpServletRequest request, ModelMap map) {
		String orderId = request.getParameter("orderId");
		map.put("orderId", orderId);
		return "repay_late";
	}

	/**
	 * 运营商结果处理controller
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/yunYingShangResult.do")
	public String yunYingShangResult(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String code = request.getParameter("code");
		logger.info("============================运营商处理结果code" + code);
		String orderId = request.getParameter("orderId");
		logger.info("============================工单id" + orderId);
		String userId = request.getParameter("userId");
		logger.info("============================用户id" + userId);
		if (CommUtils.isNull(code) || CommUtils.isNull(orderId) || CommUtils.isNull(userId)) {
			return "index";
		}
		if (code.equals("000")) {
			try {
				// 修改借款人认证状态
				BwBorrower borrower = bwBorrowerService.findBwBorrowerById(Long.parseLong(userId));
				borrower.setAuthStep(4);
				// 更新工单状态
				iBwOrderService.updateStatusByOrderId(Long.valueOf(orderId), 2);
				// 添加运营商认证
				BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(orderId), 1);
				Date now = new Date();
				if (CommUtils.isNull(bwOrderAuth)) {
					logger.info("=================添加认证信息");
					bwOrderAuth = new BwOrderAuth();
					bwOrderAuth.setOrderId(Long.valueOf(orderId));
					bwOrderAuth.setAuth_type(1);
					bwOrderAuth.setAuth_channel(Integer.valueOf(12)); // 渠道
					bwOrderAuth.setCreateTime(now);
					bwOrderAuth.setUpdateTime(now);
					logger.info("================当前时间:" + now);
					bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
				}
				logger.info("========================================抓取运营商成功更改工单状态");
				SystemAuditDto systemAuditDto = new SystemAuditDto();
				systemAuditDto.setOrderId(Long.valueOf(orderId));
				systemAuditDto.setBorrowerId(Long.valueOf(userId));
				systemAuditDto.setCreateTime(new Date());
				systemAuditDto.setName(borrower.getName());
				systemAuditDto.setIdCard(borrower.getIdCard());
				systemAuditDto.setPhone(borrower.getPhone());
				systemAuditDto.setChannel(12);
				String systemAudit = JsonUtils.toJson(systemAuditDto);
				logger.info("========================================抓取运营商成功放入系统审核缓存" + systemAudit);
				RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId, systemAudit);
				// 获取设备信息
				haoDaiService.addBwUserEquipment(Long.valueOf(orderId));
				logger.info("============================获取设备信息");
				// String str = haoDaiService.getDataBack(Long.valueOf(userId));
				// // 回传运营商数据给好贷
				// BeadWalletHaoDaiService.sendDateBack(str);
				// logger.info("============================回传运营商数据:" + str);
				return "review";
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				return "index";
			}
		}
		return "index";
	}

	/**
	 * 根据借款人id获取到最近一期工单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findBwOrderByBwId.do")
	public AppResponseResult findBwOrderByBwId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("601");
			result.setMsg("借款人id为空");
			return result;
		}
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", bwId).andEqualTo("productType",
				OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//		example.setOrderByClause(" createTime desc ");
		example.orderBy("createTime").desc();
		List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
		if (list.size() > 0) {
			result.setCode("000");
			result.setMsg("根据借款人id获取到最近一期工单信息");
			result.setResult(list.get(0));
			return result;
		} else {
			result.setCode("000");
			result.setMsg("根据借款人id获取到最近一期工单信息");
			result.setResult(null);
			return result;
		}
	}

	/**
	 * 提供分期调用
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderStatus.do")
	public Map<String, String> getOrderStatus(HttpServletRequest request, HttpServletResponse response) {
		String orderId = request.getParameter("orderId");
		Map<String, String> content = new HashMap<>();
		if (CommUtils.isNull(orderId)) {
			content.put("code", "1001");
			return content;
		}
		try {
			content = haoDaiService.getLoanStatusData(Long.valueOf(orderId));
		} catch (Exception e) {
			e.printStackTrace();
			content.put("code", "1001");
			return content;
		}
		return content;
	}

}
