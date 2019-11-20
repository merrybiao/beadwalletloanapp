package com.waterelephant.third.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.servcie.LianLianPayService;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.third.entity.ThirdRequest;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.entity.request.RequestSign;
import com.waterelephant.third.service.ThirdService;
import com.waterelephant.third.utils.ThirdConstant;
import com.waterelephant.utils.AESUtil;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * 统一对外接口（code0091）
 * 
 * Module:
 * 
 * ThirdController.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <统一对外接口>
 */
@Controller
public class ThirdController {
	private Logger logger = LoggerFactory.getLogger(ThirdController.class);
	private static String AES_KEY = "third.key.channel_";
	// 绑卡成功之后跳转到借钱吧页面的地址：
	// private static String bindCard_success_url = "http://p2pactive.ngrok.cc/dorong-openapi/callback/reback/page";

	@Autowired
	ThirdService thirdService;
	@Autowired
	IBwBankCardService iBwBankCardService;
	@Autowired
	IBwOrderChannelService bwOrderChannelService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwOrderRongService bwOrderRongService;

	// @RequestMapping("/test.do")
	// public String Test() {
	// return "test_bindcard";
	//
	// }

	/**
	 * 统一对外接口 - 检查用户（code0091）
	 * 
	 * @param thirdRequest
	 * @author zhangChong
	 * @return thirdResponse
	 */
	@ResponseBody
	@RequestMapping("/third/interface/checkUser.do")
	public ThirdResponse checkUser(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		ThirdResponse thirdResponse = new ThirdResponse();
		logger.info(sessionId + "：开始controller层检查用户接口");
		try {
			// 第一步：检查参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 第二步：处理业务
			thirdResponse = thirdService.checkUser(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层检查用户接口异常:", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("系统异常");
		}
		logger.info(sessionId + "结束controller层检查用户接口：" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口 - 订单推送（code0091）
	 * 
	 * @param request
	 * @param response
	 * @author liuDaodao
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/third/interface/pushOrder.do")
	public ThirdResponse pushOrder(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		ThirdResponse thirdResponse = new ThirdResponse();
		logger.info(sessionId + "：开始controller层订单推送接口");
		try {
			// 第一步：检查参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "：结束controller层订单推送接口：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 第二步：处理业务
			thirdResponse = thirdService.savePushOrder(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层订单推送接口异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("系统异常");
		}
		logger.info(sessionId + "结束controller层订单推送接口：" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口 - 绑卡（code0091）
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 */
	@RequestMapping("/third/interface/bindCard.do")
	public void bindCard(HttpServletRequest request, HttpServletResponse response) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始controller层绑卡接口");
		try {
			// 第一步：获取参数
			String channelCode = request.getParameter("appId");
			String requestParameter = request.getParameter("request");

			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requestParameter = AESUtil.Decrypt(requestParameter, AESKey); // AES解密请求参数
			RequestSign requestSign = JSON.parseObject(requestParameter, RequestSign.class);

			String thirdOrderNo = requestSign.getThirdOrderNo();
			logger.info("=======thirdOrderNo=======" + thirdOrderNo);
			// 根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			logger.info("=======bwOrderRong=======" + JSON.toJSONString(bwOrderRong));
			Long orderId = bwOrderRong.getOrderId();
			logger.info("==========orderId===========" + orderId);
			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			logger.info("=======bwOrder=======" + JSON.toJSONString(bwOrder));

			RedisUtils.hset("third:bindCard", requestSign.getUserId(), bwOrder.getOrderNo()); // 放入redis用于回调使用
			RedisUtils.hset("third:bindCard:successReturnUrl:" + orderChannel.getId(), bwOrder.getOrderNo(),
					requestSign.getUrl());

			// 第二步：连连签约
			String urlReturn = SystemConstant.NOTIRY_URL + "/third/other/interface/bindCardCallback.do";
			SignLess signLess = new SignLess();
			signLess.setUser_id(requestSign.getUserId());
			signLess.setId_no(requestSign.getIdCard());
			signLess.setAcct_name(requestSign.getAccountName());
			signLess.setCard_no(requestSign.getBankCardNo());
			signLess.setUrl_return(urlReturn);
			logger.info(sessionId + "开始调用连连绑卡接口,signLess=" + JSONObject.toJSONString(signLess));
			LianLianPayService.signAccreditPay(signLess, response);
			logger.info(sessionId + "结束调用连连绑卡接口");
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层绑卡接口异常", e);
		}
		logger.info(sessionId + "结束controller层绑卡接口");
	}

	/**
	 * 统一对外接口 - 绑卡 - 回调（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@RequestMapping("/third/other/interface/bindCardCallback.do")
	public String bindCardCallback(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始controller层绑卡回调接口");

		// 第一步：获取参数
		String status = request.getParameter("status"); // 状态
		String result = request.getParameter("result"); // 返回结果
		String userId = request.getParameter("user_id");// 用户ID
		logger.info("返回状态" + status);
		if (userId == null) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			userId = jsonObject.getString("user_id");
		}
		// JSONObject jsonObject = JSONObject.parseObject(result);
		// logger.info("status=" + status + ",,,result=" + result);
		// String userId = jsonObject.getString("user_id");
		String orderNo = RedisUtils.hget("third:bindCard", userId);
		BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNo);

		Integer channelId = bwOrder.getChannel();
		Long orderId = bwOrder.getId();
		Map<String, Object> map = new HashMap<>();
		map.put("channelId", channelId);
		map.put("orderId", orderId);
		map.put("result", result);
		String json = JSON.toJSONString(map);
		logger.info("存入redis的数据：" + json);

		try {
			// 第二步：验证参数
			if (CommUtils.isNull(status) || CommUtils.isNull(result)) {
				// 绑卡状态通知
				logger.info(sessionId + "：结束controller层绑卡回调接口，入参为空");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				modelMap.put("result", "系统异常");
				return "sign_fail_third";
			}

			// 判断是否重复绑卡
			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (!CommUtils.isNull(bwBankCard) && 2 == bwBankCard.getSignStatus()) {
				String url = RedisUtils.hget("third:bindCard:successReturnUrl:" + channelId, orderNo);
				RedisUtils.hdel("third:bindCard:successReturnUrl:" + channelId, orderNo);
				logger.info(sessionId + "结束controller层绑卡回调接口" + url);
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				if (!CommUtils.isNull(url)) {
					return "redirect:" + url;
				} else {
					return "sign_success_third";
				}
			}

			boolean isSuccess = thirdService.updateBindCardCallback(sessionId, status, result);
			String url = RedisUtils.hget("third:bindCard:successReturnUrl:" + channelId, orderNo);
			RedisUtils.hdel("third:bindCard:successReturnUrl:" + channelId, orderNo);
			if (isSuccess == true) {
				logger.info(sessionId + "结束controller层绑卡回调接口");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				if (!CommUtils.isNull(url)) {
					return "redirect:" + url;
				} else {
					return "sign_success_third";
				}
			} else {
				logger.info(sessionId + ".........结束controller层绑卡回调接口");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				modelMap.addAttribute("result", result);
				return "sign_fail_third";
			}
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层绑卡回调接口异常", e);
			RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
			return "sign_fail_third";
		}
	}

	/**
	 * 统一对外接口-修改银行卡信息（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@RequestMapping("/third/interface/updateBankCard.do")
	@ResponseBody
	public ThirdResponse updateBankCard(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.updateBankCard method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "-结束ThirdController.updateBankCard method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.updateBankCard(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.updateBankCard method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.updateBankCard method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口-获取合同地址（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@RequestMapping("/third/interface/getContract.do")
	@ResponseBody
	public ThirdResponse getContractUrl(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.getContractUrl method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "-结束ThirdController.getContractUrl method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.contractUrl(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.getContractUrl method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.getContractUrl method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 
	 * 统一对外接口-拉取订单状态（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/third/interface/pullOrderStatus.do")
	public ThirdResponse pullOrderStatus(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.pullOrderStatus method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(
						sessionId + "-结束ThirdController.pullOrderStatus method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.orderStatus(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.pullOrderStatus method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.pullOrderStatus method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	// /**
	// *
	// * 统一对外接口-拉取审批结论（code0091）
	// *
	// * @param thirdRequest
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping("/third/inteface/pullConclusion.do")
	// public ThirdResponse pullConclusion(ThirdRequest thirdRequest) {
	// long sessionId = System.currentTimeMillis();
	// logger.info(sessionId + "-开始ThirdController.pullConclusion method......");
	// ThirdResponse thirdResponse = new ThirdResponse();
	// try {
	// // 1.校验请求参数
	// if (thirdRequest == null) {
	// thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
	// thirdResponse.setMsg("请求参数为空");
	// logger.info(sessionId + "-结束ThirdController.pullConclusion method-" + JSON.toJSONString(thirdResponse));
	// return thirdResponse;
	// }
	// // 2.处理业务
	// thirdResponse = thirdService.conclusion(sessionId, thirdRequest);
	// } catch (Exception e) {
	// logger.error(sessionId + "-执行ThirdController.pullConclusion method 异常", e);
	// thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
	// thirdResponse.setMsg("运行异常");
	// }
	// logger.info(sessionId + "-结束ThirdController.pullConclusion method- " + JSON.toJSONString(thirdResponse));
	// return thirdResponse;
	// }

	/**
	 * 
	 * 统一对外接口-签约（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/third/interface/signContract.do")
	public ThirdResponse signContract(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.signContract method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "-结束ThirdController.signContract method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.updateSignContract(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.signContract method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.signContract method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 
	 * 统一对外接口-拉取还款计划（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/third/interface/pullRepaymentPlan.do")
	public ThirdResponse pullRepaymentPlan(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.pullRepaymentPlan method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(
						sessionId + "-结束ThirdController.pullRepaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.repaymentPlan(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.pullRepaymentPlan method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.pullRepaymentPlan method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 
	 * 统一对外接口-还款详情（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/third/interface/repayInfo.do")
	public ThirdResponse repayInfo(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.repayInfo method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "-结束ThirdController.repayInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.repayInfo(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.repayInfo method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.repayInfo method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 
	 * 统一对外接口-主动还款（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/third/interface/repayment.do")
	public ThirdResponse repayment(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.repayment method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "-结束ThirdController.repayment method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.repayment(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.repayment method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.repayment method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 
	 * 统一对外接口-展期详情（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/third/interface/deferInfo.do")
	public ThirdResponse deferInfo(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.deferInfo method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "-结束ThirdController.deferInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.deferInfo(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.deferInfo method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.deferInfo method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 
	 * 统一对外接口-展期（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/third/interface/defer.do")
	public ThirdResponse defer(ThirdRequest thirdRequest) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "-开始ThirdController.defer method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.校验请求参数
			if (thirdRequest == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "-结束ThirdController.defer method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.处理业务
			thirdResponse = thirdService.defer(sessionId, thirdRequest);
		} catch (Exception e) {
			logger.error(sessionId + "-执行ThirdController.defer method 异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdController.defer method- " + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}
}
