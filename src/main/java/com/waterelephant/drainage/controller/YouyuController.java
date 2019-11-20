package com.waterelephant.drainage.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.drainage.entity.youyu.QueryOrderStatus;
import com.waterelephant.drainage.entity.youyu.SignOrder;
import com.waterelephant.drainage.entity.youyu.TalculationCharges;
import com.waterelephant.drainage.entity.youyu.YouyuRequest;
import com.waterelephant.drainage.entity.youyu.YouyuRequestCheckUser;
import com.waterelephant.drainage.entity.youyu.YouyuRequestPush;
import com.waterelephant.drainage.entity.youyu.YouyuResponse;
import com.waterelephant.drainage.service.YouyuService;
import com.waterelephant.drainage.util.youyu.DesService;
import com.waterelephant.drainage.util.youyu.DesServiceImpl;
import com.waterelephant.drainage.util.youyu.RsaService;
import com.waterelephant.drainage.util.youyu.RsaServiceImpl;
import com.waterelephant.drainage.util.youyu.YouyuConstant;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ObjectNotEmpty;

/**
 * 
 * @ClassName: YouyuController
 * @Description: 有鱼对外接口
 * @author He Longyun
 * @date 2017年12月8日 上午9:49:24
 *
 */
@Controller
public class YouyuController {

	private Logger logger = LoggerFactory.getLogger(YouyuController.class);

	@Autowired
	private YouyuService youyuService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private BwOrderRongService bwOrderRongService;

	/**
	 * <<<<<<< .mine <<<<<<< .mine
	 * 
	 * 1.14用户排重 =======
	 * 
	 * @Title: checkUser
	 * @Description:1.14用户排查
	 * @return: YouyuResponse >>>>>>> .r16071
	 * 
	 *          ======= 1.14用户排重 >>>>>>> .r15098
	 */
	@ResponseBody
	@RequestMapping("/youyu/interface/checkUser.do")
	public YouyuResponse checkUser(YouyuRequest youyuRequest) {
		long sessionId = System.currentTimeMillis();
		YouyuResponse youyuResponse = new YouyuResponse();
		logger.info(sessionId + "：开始controller层检查用户接口");

		try {
			// 第一步：检查参数是否为空
			boolean flag = ObjectNotEmpty.isAllFieldNull(youyuRequest);
			if (flag) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("参数属性存在空值");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第二步：将传过来的数据进行解密Integer.parseInt(channel)
			RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY,
					YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8);
			DesService desService = new DesServiceImpl();
			String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
			String checkuserdata = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据
			// 第三步：检查封装到的数据是否为空
			YouyuRequestCheckUser requestCheckUser = JSON.parseObject(checkuserdata, YouyuRequestCheckUser.class);
			boolean isOk = sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名
			if (!isOk) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("验签不成功");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			boolean flag1 = ObjectNotEmpty.isAllFieldNull(requestCheckUser);
			if (flag1) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("输入的参数不合法");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第四步：查看数据业务处理
			youyuResponse = youyuService.checkUser(sessionId, requestCheckUser);
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层检查用户接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		}
		return youyuResponse;
	}

	/**
	 * <<<<<<< .mine
	 * 
	 * 1.1订单推送
	 * 
	 * ======= 1.1订单推送 >>>>>>> .r15098
	 */
	@ResponseBody
	@RequestMapping("/youyu/interface/PushOrder.do")
	public YouyuResponse pushOrder(YouyuRequest youyuRequest) {
		long sessionId = System.currentTimeMillis();
		YouyuResponse youyuResponse = new YouyuResponse();
		logger.info(sessionId + "：开始controller层检查用户接口");
		try {
			// 第一步：检查参数是否为空
			boolean flag = ObjectNotEmpty.isAllFieldNull(youyuRequest);
			if (flag) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("参数属性存在空值");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;

			}
			// 第二步：将传过来的数据进行解密Integer.parseInt(channel)
			RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY,
					YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8);
			DesService desService = new DesServiceImpl();
			String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
			String PushOrderdata = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据
			boolean isOk = sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名
			if (!isOk) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("验签不成功");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第三步：检查封装到的数据是否为空
			YouyuRequestPush youyuRequestPush = JSON.parseObject(PushOrderdata, YouyuRequestPush.class);
			boolean flag1 = ObjectNotEmpty.isAllFieldNull(youyuRequestPush);
			if (flag1) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("输入的参数不合法");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第五步：处理业务
			youyuResponse = youyuService.savePushLoanBaseInfo(sessionId, youyuRequestPush);
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层检查用户接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		}
		return youyuResponse;
	}

	/**
	 * 
	 * <<<<<<< .mine <<<<<<< .mine
	 * 
	 * 1.11有鱼绑卡
	 * 
	 * ======= 1.11有鱼绑卡 >>>>>>> .r15098
	 */
	/*
	 * @RequestMapping("/youyu/bindCard.do")
	 * 
	 * @ResponseBody public YouyuResponse bindCard(YouyuRequest youyuRequest) { long sessionId =
	 * System.currentTimeMillis(); YouyuResponse youyuResponse = new YouyuResponse(); try { YouyuResponse response =
	 * nullJudgment(youyuRequest, sessionId); if (!CommUtils.isNull(response)) { return response; } // 验签 //
	 * 第二步：将传过来的数据进行解密Integer.parseInt(channel) RsaService sxfqRsaservice = new
	 * RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY, YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8); DesService
	 * desService = new DesServiceImpl(); String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
	 * String PushOrderdata = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据 boolean isOk =
	 * sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名 if (!isOk) {
	 * youyuResponse.setCode(YouyuResponse.CODE_FAIL); youyuResponse.setDesc("验签不成功"); logger.info(sessionId +
	 * "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse)); return youyuResponse; } // 第三步：检查封装到的数据是否为空
	 * YouyuBankCard youyuBankCard = JSON.parseObject(PushOrderdata, YouyuBankCard.class); if (youyuBankCard == null) {
	 * youyuResponse.setCode(YouyuResponse.CODE_FAIL); youyuResponse.setDesc("封装到的数据是否为空"); logger.info(sessionId +
	 * "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse)); return youyuResponse; }
	 * 
	 * // 第四步，处理业务 youyuResponse = youyuService.bindCard(sessionId, youyuBankCard);
	 * 
	 * } catch (Exception e) { youyuResponse.setCode(YouyuResponse.CODE_FAIL);// 1表示返回成功，其他表示异常
	 * youyuResponse.setDesc("发生异常"); logger.info(sessionId + "绑定银行卡接口发生异常"); return youyuResponse; } return
	 * youyuResponse; }
	 */

	/**
	 * 
	 * ======= >>>>>>> .r15423 <<<<<<< .mine
	 * 
	 * 1.7订单状态获取
	 * 
	 * ======= 1.7订单状态获取 >>>>>>> .r15098
	 */

	@ResponseBody
	@RequestMapping("/youyu/interface/queryOrderStatus.do")
	public YouyuResponse queryOrderStatus(YouyuRequest youyuRequest) {
		long sessionId = System.currentTimeMillis();
		YouyuResponse youyuResponse = new YouyuResponse();
		logger.info(sessionId + "：开始controller层检查用户接口");
		try {
			// 第一步：检查参数是否为空
			boolean flag = ObjectNotEmpty.isAllFieldNull(youyuRequest);
			if (flag) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("参数属性存在空值");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;

			}
			// 第二步：将传过来的数据进行解密Integer.parseInt(channel)
			RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY,
					YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8);
			DesService desService = new DesServiceImpl();
			String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
			String queryOrderData = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据

			boolean isOk = sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名
			if (!isOk) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("验签不成功");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第三步：检查封装到的数据是否为空
			QueryOrderStatus queryOrderStatus = JSON.parseObject(queryOrderData, QueryOrderStatus.class);
			boolean flag1 = ObjectNotEmpty.isAllFieldNull(queryOrderStatus);
			if (flag1) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("输入的参数不合法");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第四步：查看数据业务处理
			youyuResponse = youyuService.queryOrderStatus(sessionId, queryOrderStatus);
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层检查用户接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		}
		return youyuResponse;
	}

	/**
	 * 有鱼-签约
	 */
	@ResponseBody
	@RequestMapping("/youyu/interface/updateSignContract.do")
	public YouyuResponse updateSignContract(YouyuRequest youyuRequest) {
		long sessionId = System.currentTimeMillis();
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			// 第一步：检查参数是否为空
			boolean flag = ObjectNotEmpty.isAllFieldNull(youyuRequest);
			if (flag) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("参数属性存在空值");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;

			}
			// 第二步：将传过来的数据进行解密Integer.parseInt(channel)
			RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY,
					YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8);
			DesService desService = new DesServiceImpl();
			String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
			String signOrderData = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据

			boolean isOk = sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名
			if (!isOk) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("验签不成功");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}

			// 第三步：检查封装到的数据是否为空
			SignOrder signOrder = JSON.parseObject(signOrderData, SignOrder.class);
			boolean flag1 = ObjectNotEmpty.isAllFieldNull(signOrder);
			if (flag1) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("输入的参数不合法");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第四步：查看数据业务处理
			youyuResponse = youyuService.updateSignContract(sessionId, signOrder);
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层检查用户接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		}
		return youyuResponse;
	}

	/**
	 * 合同1.13
	 */
	@ResponseBody
	@RequestMapping("/youyu/interface/agreement.do")
	public YouyuResponse agreement(YouyuRequest youyuRequest) {
		long sessionId = System.currentTimeMillis();
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			// 第一步：检查参数是否为空
			boolean flag = ObjectNotEmpty.isAllFieldNull(youyuRequest);
			if (flag) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("参数属性存在空值");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;

			}
			// 第二步：将传过来的数据进行解密Integer.parseInt(channel)
			RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY,
					YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8);
			DesService desService = new DesServiceImpl();
			String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
			String agreementOrderData = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据
			boolean isOk = sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名
			if (!isOk) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("验签不成功");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第三步：检查封装到的数据是否为空
			SignOrder signOrder = JSON.parseObject(agreementOrderData, SignOrder.class);
			boolean flag1 = ObjectNotEmpty.isAllFieldNull(signOrder);
			if (flag1) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("输入的参数不合法");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第四步：查看数据业务处理
			youyuResponse = youyuService.agreement(sessionId, signOrder);
			return youyuResponse;
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层检查用户接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
			return youyuResponse;
		}
	}

	/**
	 * 还款接口1.19
	 * 
	 */
	@ResponseBody
	@RequestMapping("/youyu/interface/repayment.do")
	public YouyuResponse repayment(YouyuRequest youyuRequest) {
		long sessionId = System.currentTimeMillis();
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			// 第一步：检查参数是否为空
			boolean flag = ObjectNotEmpty.isAllFieldNull(youyuRequest);
			if (flag) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("参数属性存在空值");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;

			}
			// 第二步：将传过来的数据进行解密Integer.parseInt(channel)
			RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY,
					YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8);
			DesService desService = new DesServiceImpl();
			String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
			String repaymentOrderData = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据
			boolean isOk = sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名
			if (!isOk) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("验签不成功");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第三步：检查封装到的数据是否为空
			SignOrder signOrder = JSON.parseObject(repaymentOrderData, SignOrder.class);
			boolean flag1 = ObjectNotEmpty.isAllFieldNull(signOrder);
			if (flag1) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("输入的参数不合法");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第四步：查看数据业务处理
			youyuResponse = youyuService.repayment(sessionId, signOrder.getOrder_no());
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层检查用户接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		}
		return youyuResponse;
	}

	/**
	 * 
	 * <<<<<<< .mine
	 * 
	 * @Title: count @Description: 有鱼-费用试算1.18 @param youyuRequest @return @return YouyuResponse @throws =======
	 * @Title: count @Description: 有鱼-费用试算1.18 @param youyuRequest @return @return YouyuResponse @throws >>>>>>> .r15098
	 */
	@ResponseBody
	@RequestMapping("/youyu/interface/count.do")
	public YouyuResponse count(YouyuRequest youyuRequest) {
		long sessionId = System.currentTimeMillis();
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			// 第一步：检查参数是否为空
			boolean flag = ObjectNotEmpty.isAllFieldNull(youyuRequest);
			if (flag) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("参数属性存在空值");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第二步：将传过来的数据进行解密Integer.parseInt(channel)
			RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY,
					YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8);
			DesService desService = new DesServiceImpl();
			String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
			String agreementOrderData = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据
			boolean isOk = sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名
			if (!isOk) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("验签不成功");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第三步：检查封装到的数据是否为空
			TalculationCharges talculationCharges = JSON.parseObject(agreementOrderData, TalculationCharges.class);
			boolean flag1 = ObjectNotEmpty.isAllFieldNull(talculationCharges);
			if (flag1) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("输入的参数不合法");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			String loan_money = talculationCharges.getLoan_money();
			String loan_term = talculationCharges.getLoan_term();
			String money_unit = talculationCharges.getMoney_unit();
			String term_unit = talculationCharges.getTerm_unit();
			// 第四步：查看数据业务处理
			youyuResponse = youyuService.count(sessionId, loan_money, loan_term, money_unit, term_unit);
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层检查用户接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuResponse));
		}
		return youyuResponse;
	}

	/**
	 * 跳转页面(有鱼)
	 */
	@RequestMapping("/youyu/interface/montageURL.do")
	public String montageURL(HttpServletRequest request, HttpServletResponse response) {
		String failReturnUrl = YouyuConstant.YOUYU_SKIPURL + "#fail";
		try {
			// 取参
			String orderNo = request.getParameter("orderNo");
			String torder_no = request.getParameter("torder_no");
			if (orderNo == null || orderNo.equals("") || torder_no == null || torder_no.equals("")) {
				logger.info("montageURL>>>>>" + "订单号或三方订单号为null");
				return "redirect:" + failReturnUrl;
			}
			// 业务
			String successReturnUrl = youyuService.montageURL(orderNo, torder_no);
			return successReturnUrl;
		} catch (Exception e) {
			logger.error("有鱼绑卡跳转异常：", e);
			return "redirect:" + failReturnUrl;
		}
	}

	@RequestMapping("/youyu/interface/syntonyUrl.do")
	public String syntonyUrl(HttpServletRequest request, HttpServletResponse response) {
		String failReturnUrl = YouyuConstant.YOUYU_SKIPURL + "#fail";
		try {
			// 取参
			String orderNo = request.getParameter("orderNo");
			String torder_no = request.getParameter("torder_no");
			if (orderNo == null || orderNo.equals("") || torder_no == null || torder_no.equals("")) {
				logger.info("syntonyUrl>>>>>" + "订单号或三方订单号为null");
				return "redirect:" + failReturnUrl;
			}
			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNo);
			if (bwOrder == null) {
				logger.info("syntonyUrl>>>>>" + "订单为空，绑卡失败");
				return "redirect:" + failReturnUrl;
			}
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(torder_no);
			if (bwOrderRong == null) {
				logger.info("syntonyUrl>>>>>" + "三方订单为空，绑卡失败");
				return "redirect:" + failReturnUrl;
			}
			// 处理业务
			String url = youyuService.syntonyUrl(orderNo, torder_no);
			return url;
		} catch (Exception e) {
			logger.error("有鱼绑卡跳转异常：", e);
			return "redirect:" + failReturnUrl;
		}
	}

	/**
	 * 放弃签约
	 * 
	 * @Title: finish
	 * @Description:
	 * @return: void
	 */
	@RequestMapping("/youyu/interface/finish.do")
	@ResponseBody
	public YouyuResponse finish(YouyuRequest youyuRequest) {
		YouyuResponse youyuResponse = new YouyuResponse();
		long sessionId = System.currentTimeMillis();
		try {
			// 第一步：检查参数是否为空
			logger.info(sessionId + "：结束controller层检查用户接口：" + JSON.toJSONString(youyuRequest));

			boolean flag = ObjectNotEmpty.isAllFieldNull(youyuRequest);
			if (flag) {
				logger.info(sessionId + ">>>>>参数存在空值");
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("参数存在空值");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第二步：将传过来的数据进行解密Integer.parseInt(channel)
			RsaService sxfqRsaservice = new RsaServiceImpl(YouyuConstant.SHUIXIANG_PRIVATEKEY,
					YouyuConstant.YOUYU_PUBLICKEY, RsaService.PKCS8);
			DesService desService = new DesServiceImpl();
			String keys = sxfqRsaservice.decrypt(youyuRequest.getDes_key());// 原始key
			String agreementOrderData = desService.desDecrypt(youyuRequest.getBiz_data(), keys);// 解析数据
			boolean isOk = sxfqRsaservice.verifySign(youyuRequest.getSign(), youyuRequest.getBiz_data());// 验证签名
			if (!isOk) {
				logger.info(sessionId + ">>>>>验签不成功");
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("验签不成功");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第三步：检查封装到的数据是否为空
			SignOrder signOrder = JSON.parseObject(agreementOrderData, SignOrder.class);
			boolean flag1 = ObjectNotEmpty.isAllFieldNull(signOrder);
			if (flag1) {
				logger.info(sessionId + ">>>>>输入的参数不合法");
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("输入的参数不合法");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			String order_no = signOrder.getOrder_no();
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(order_no);
			if (CommUtils.isNull(bwOrderRong)) {
				logger.info(sessionId + ">>>>>第三方订单为空");
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("第三方订单为空");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			Long orderId = bwOrderRong.getOrderId();
			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				logger.info(sessionId + ">>>>>订单为空");
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("订单为空");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第四步：查看数据业务处理
			youyuResponse = youyuService.finish(sessionId, bwOrder);
			return youyuResponse;
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层放弃签约接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
			return youyuResponse;
		}
	}
}
