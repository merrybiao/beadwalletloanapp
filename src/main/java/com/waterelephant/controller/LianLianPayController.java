package com.waterelephant.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waterelephant.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.entity.lianlian.SignResponse;
import com.beadwallet.servcie.LianLianPayService;
import com.waterelephant.dto.BackCardAndUserInfo;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.service.BwBankCardChangeService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;

import net.sf.json.JSONObject;

/**
 * 连连支付相关控制器 Created by liven on 2017/1/14.
 */
@Controller
@RequestMapping("/lianlian/")
public class LianLianPayController {
	private Logger logger = Logger.getLogger(LianLianPayController.class);

	@Autowired
	private IBwBankCardService bwBankCardService;

	@Autowired
	private IBwBorrowerService bwBorrowerService;

	@Autowired
	private BwBankCardChangeService bwBankCardChangeService;

	/**
	 * 判断是否连连签约接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkSignLianLianPay.do")
	public AppResponseResult checkSignLianLianPay(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String borrower_id = request.getParameter("borrower_id");
		logger.info("============判断是否连连签约接口===借款人id:" + borrower_id);
		if (CommUtils.isNull(borrower_id)) {
			result.setCode("101");
			result.setMsg("借款人id为空");
			logger.info("============================借款人id为空");
			return result;
		}
		try {
			BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(borrower_id));
			result.setCode("000");
			result.setMsg("成功");
			result.setResult(card);
			return result;
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("失败");
			logger.error(e, e);
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 获取提现卡信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTiXianCard.do")
	public AppResponseResult getTiXianCard(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		logger.info("============获取提现卡===借款人id:" + bwId);
		try {
			if (CommUtils.isNull(bwId)) {
				result.setCode("101");
				result.setMsg("借款人id为空");
				return result;
			}
			BwBorrower bwBorrower = new BwBorrower();
			bwBorrower.setId(Long.valueOf(bwId));
			BwBorrower bw = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
			if (CommUtils.isNull(bw)) {
				result.setCode("100");
				result.setMsg("借款人不存在");
				return result;
			}
			BackCardAndUserInfo backCardAndUserInfo = new BackCardAndUserInfo();
			backCardAndUserInfo.setBwId(String.valueOf(bw.getId()));
			backCardAndUserInfo.setName(bw.getName());
			backCardAndUserInfo.setIdCard(bw.getIdCard());
			backCardAndUserInfo.setPhone(bw.getPhone());

			BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(bwId));
			if (card != null) {
				backCardAndUserInfo.setCardNo(card.getCardNo());
				backCardAndUserInfo.setBankCode(card.getBankCode());
				backCardAndUserInfo.setBankName(RedisUtils.hget("fuiou:bank", card.getBankCode()));
			}
			logger.info("============获取提现卡信息====:" + backCardAndUserInfo.toString());
			result.setCode("000");
			result.setMsg("成功");
			result.setResult(backCardAndUserInfo);
			return result;
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("失败");
			logger.error(e, e);
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 设置提现卡
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setUpBackCard.do")
	public AppResponseResult setUpBackCard(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");// 借款人id
		String name = request.getParameter("name");// 真实姓名
		String bankCode = request.getParameter("bankCode");// 银行卡编码
		String idCard = request.getParameter("idCard");// 身份证
		String cardNo = request.getParameter("cardNo");// 银行卡号
		String isBankCardChange = request.getParameter("isBankCardChange");// 是否是银行卡重绑
		logger.info("====页面传入========借款人id:" + bwId);
		logger.info("====页面传入========真实姓名:" + name);
		logger.info("====页面传入========银行卡编码:" + bankCode);
		logger.info("====页面传入========身份证:" + idCard);
		logger.info("====页面传入========银行卡号:" + cardNo);
		logger.info("====页面传入========是否是银行卡重绑:" + isBankCardChange);
		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		logger.info("【LianLianPayController.setUpBackCard】borrowerId：" + bwId + "，paramMap=" + paramMap);
		try {
			if (CommUtils.isNull(bwId)) {
				result.setCode("105");
				result.setMsg("借款人id为空");
				return result;
			}
			BwBorrower bwBorrower = new BwBorrower();
			bwBorrower.setId(Long.valueOf(bwId));
			// logger.info("============查询借款人==");
			BwBorrower bw = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
			if (CommUtils.isNull(bw)) {
				result.setCode("100");
				result.setMsg("借款人不存在");
				return result;
			}
			// logger.info("============验证身份证==");
			if (!this.checkIdentityCard(idCard)) {
				result.setCode("101");
				result.setMsg("身份证不合法");
				return result;
			}
			if (!this.checkBankCard(cardNo)) {
				result.setCode("102");
				result.setMsg("银行卡不合法");
				return result;
			}
			if (!this.checkTrueName(name)) {
				result.setCode("103");
				result.setMsg("名字不合法");
				return result;
			}

			// 根据bankCode 从redis获取数据
			// String bankName = RedisUtils.hget("fuiou:bank", bankCode);
			// if (CommUtils.isNull(bankName)) {
			// result.setCode("104");
			// result.setMsg("请选择开户行");
			// return result;
			// }

			BwBankCard oriBank = bwBankCardService.findBwBankCardByBoorwerId(Long.parseLong(bwId));
			if (oriBank != null && StringUtil.isNotEmpty(oriBank.getCardNo())
					&& "2".equals(StringUtil.toString(oriBank.getSignStatus())) && cardNo.equals(oriBank.getCardNo())) {
				result.setCode("105");
				result.setMsg("请绑定不同的银行卡");
				return result;
			}

			// 验证银行卡和名称是否一致
			// Boolean is_exist = checkBankNameAndCode(bankCode, cardNo);
			// if (!is_exist) {
			// result.setCode("106");
			// result.setMsg("银行卡号和名称不一致");
			// return result;
			// }

			// 验证身份证是否被使用
			BwBorrower checkBorrower = new BwBorrower();
			checkBorrower.setIdCard(idCard);
			List<BwBorrower> borrowers = bwBorrowerService.findBwBorrowerListByIdCard(checkBorrower);
			if (!CommUtils.isNull(borrowers)) {
				boolean check = false;
				for (BwBorrower cBorrower : borrowers) {
					if (bw.getPhone().equals(cBorrower.getPhone())) {
						check = true;
					}
				}

				if (!check) {
					result.setCode("107");
					result.setMsg("该身份证已经申请借款！");
					return result;
				}
			}

			Date now = new Date();
			int bronDate = Integer.parseInt(idCard.substring(6, 10));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			bw.setAge(Integer.parseInt(sdf.format(now)) - bronDate);

			int sex = Integer.parseInt(idCard.substring(idCard.length() - 2, idCard.length() - 1)) % 2 == 0 ? 0 : 1;
			bw.setSex(sex);
			bw.setName(name);
			bw.setIdCard(idCard);
			logger.info("============性别===:" + sex);
			String bankNameUtil = BankInfoUtils.getNameOfBank(cardNo.substring(0, 6));
			logger.info("============工具类获取银行卡名称===:" + bankNameUtil);
			String bankName = null;
			if (!CommUtils.isNull(bankNameUtil)) {
				bankCode = getBankCode(bankNameUtil);
				logger.info("============工具类获取银行卡编码===:" + bankCode);
				// 只对应到银行大分类
				String[] split = bankNameUtil.split("·");
				bankName = split[0];
				// 判断银行卡是否支持
				if ("".equals(bankCode)) {
					result.setCode("105");
					result.setMsg("该银行卡不支持");
					return result;
				}
			} else {
				result.setCode("110");
				result.setMsg("该银行此类型的卡暂不支持");
				return result;
			}

			// start
			if (CommUtils.isNull(isBankCardChange) || "false".equals(isBankCardChange)) {
				bwBankCardService.saveOrUpdBorrowerAndBankCard(bw, bankCode, bankName, cardNo);
			} else {
				BwBankCard bankCard = new BwBankCard();
				bankCard.setBorrowerId(bw.getBorrowerId());
				bankCard.setBankCode(bankCode);
				bankCard.setBankName(bankName);
				bankCard.setCardNo(cardNo);
				String bankJson = JSONObject.fromObject(bankCard).toString();
				// String logintoken =
				// request.getSession().getAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN).toString();
				RedisUtils.hset("BANK_CARD_REBINDING", bwId, bankJson);
				logger.info("================重新绑定银行卡，存入redis,BANK_CARD_REBINDING,bwId=" + bwId + "====");
			}
			// end

			// if (!CommUtils.isNull(isBankCardChange) && "true".equals(isBankCardChange)) {
			// bwBankCardChangeService.saveOrUpdBorrowerAndBankCardAndBankCardChange(bw, bankCode, bankName, cardNo);
			// } else {
			// bwBankCardService.saveOrUpdBorrowerAndBankCard(bw, bankCode, bankName, cardNo);
			// }
			result.setMsg("设置成功");
			result.setCode("000");
			return result;
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统异常");
			logger.error(e, e);
			e.printStackTrace();
			return result;
		}

	}

	/**
	 * 跳转连连签约
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("appCheckToken/gotoLianLianSign.do")
	public AppResponseResult gotoLianLianSign(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String user_id = request.getParameter("bwId");// 借款人id
		String id_no = request.getParameter("idCard");// 证件号码 18位
		String acct_name = request.getParameter("name");// 姓名
		String card_no = request.getParameter("cardNo");// 银行卡号
		String app_request = request.getParameter("signType");// 请求应用标识
																// 1-Android
																// 2-ios 3-WAP
		logger.info("连连签约:借款人id====" + user_id);
		logger.info("连连签约:证件号码 18位====" + id_no);
		logger.info("连连签约:姓名===" + acct_name);
		logger.info("连连签约:银行卡号===  " + card_no);
		logger.info("连连签约 请求应用标识   1-Android 2-ios 3-WAP:====" + app_request);

		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		logger.info("【LianLianPayController.gotoLianLianSign】borrowerId：" + user_id + "，paramMap=" + paramMap);

		SignLess signLess = new SignLess();
		signLess.setUser_id(user_id);
		signLess.setId_no(id_no);
		signLess.setAcct_name(acct_name);
		signLess.setCard_no(card_no);
		signLess.setApp_request(app_request);
		if (app_request.equals("3")) {// 微信回调
			signLess.setUrl_return(SystemConstant.NOTIRY_URL + "/lianlian/signCallBackWeixin.do");
		} else {// app回调
			signLess.setUrl_return(SystemConstant.NOTIRY_URL + "/lianlian/signCallBackApp.do");
		}
		SignResponse signResponse = LianLianPayService.signAccreditPay(signLess, response);

		logger.info("交易结构代码:" + signResponse.getStatus());
		logger.info("交易结果描述:" + signResponse.getResult());
		result.setCode(signResponse.getSign_type());
		result.setMsg(signResponse.getResult());
		return result;
	}

	/**
	 * 新 跳连连签约
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("appCheckToken/gotoLianLianSign2.do")
	public AppResponseResult gotoLianLianSign2(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String user_id = request.getParameter("bwId");// 借款人id
		String id_no = request.getParameter("idCard");// 证件号码 18位
		String acct_name = request.getParameter("name");// 姓名
		String card_no = request.getParameter("cardNo");// 银行卡号
		String app_request = request.getParameter("signType");// 请求应用标识
																// 1-Android
																// 2-ios 3-WAP
		logger.info("连连签约:借款人id====" + user_id);
		logger.info("连连签约:证件号码 18位====" + id_no);
		logger.info("连连签约:姓名===" + acct_name);
		logger.info("连连签约:银行卡号===  " + card_no);
		logger.info("连连签约 请求应用标识   1-Android 2-ios 3-WAP:====" + app_request);

		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		logger.info("【LianLianPayController.gotoLianLianSign2】borrowerId：" + user_id + "，paramMap=" + paramMap);

		SignLess signLess = new SignLess();
		signLess.setUser_id(user_id);
		signLess.setId_no(id_no);
		signLess.setAcct_name(acct_name);
		signLess.setCard_no(card_no);
		signLess.setApp_request(app_request);
		if (app_request.equals("3")) {// 微信回调
			signLess.setUrl_return(SystemConstant.NOTIRY_URL + "/lianlian/signCallBackWeixin2.do");
		} else {// app回调
			signLess.setUrl_return(SystemConstant.NOTIRY_URL + "/lianlian/signCallBackApp2.do");
		}
		SignResponse signResponse = LianLianPayService.signAccreditPay(signLess, response);

		logger.info("交易结构代码:" + signResponse.getStatus());
		logger.info("交易结果描述:" + signResponse.getResult());
		result.setCode(signResponse.getSign_type());
		result.setMsg(signResponse.getResult());
		return result;
	}

	/**
	 * 连连签约微信回调
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("signCallBackWeixin.do")
	public String signCallBackWeixin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String status = request.getParameter("status");
		String result = request.getParameter("result");
		logger.info("免登陆签约回调获取  交易代码:===" + status);
		logger.info("免登陆签约回调获取 交易结果描述:===" + result);
		if (!CommUtils.isNull(status) && (status.equals("0000"))) {
			JSONObject object = JSONObject.fromObject(result);
			String user_id = object.getString("user_id");
			logger.info("签约成功后返回的借款人id:" + user_id);
			BwBankCard bb = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(user_id));
			logger.info("免登陆签约从数据库获取的银行卡id:" + bb.getId());

			bb.setSignStatus(2);// 连连支付签约
			bb.setUpdateTime(new Date());
			bwBankCardService.update(bb);

			return "sign_success_weixin";
		} else {
			modelMap.put("respDesc", status);
			modelMap.put("result", result);
			return "sign_fail_weixin";
		}
	}

	/**
	 * 新 绑定银行卡微信回调
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("signCallBackWeixin2.do")
	public String signCallBackWeixin2(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String status = request.getParameter("status");
		String result = request.getParameter("result");
		logger.info("免登陆签约回调获取  交易代码:===" + status);

		// 判断是否是重新绑定 start
		int isBankCardChange = 0;// 0 初次绑卡；1重新绑卡
		// String logintoken = request.getSession().getAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN).toString();
		// if (RedisUtils.hexists("BANK_CARD_REBINDING", logintoken)) {// 重新绑定
		// isBankCardChange = 1;
		// }
		// end

		logger.info("免登陆签约回调获取 交易结果描述:===" + result);
		if (!CommUtils.isNull(status) && (status.equals("0000"))) {
			JSONObject object = JSONObject.fromObject(result);
			String user_id = object.getString("user_id");
			logger.info("签约成功后返回的借款人id:" + user_id);
			BwBankCard bb = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(user_id));
			logger.info("免登陆签约从数据库获取的银行卡id:" + bb.getId());

			// start
			if (RedisUtils.hexists("BANK_CARD_REBINDING", user_id)) {// 重新绑定
				String bankJson = RedisUtils.hget("BANK_CARD_REBINDING", user_id);
				if (!StringUtil.isEmpty(bankJson)) {
					BwBankCard redisBank = JSON.toJavaObject(JSON.parseObject(bankJson), BwBankCard.class);
					BwBorrower bw = bwBorrowerService.findBwBorrowerById(Long.valueOf(user_id));
					bwBankCardChangeService.saveOrUpdBorrowerAndBankCardAndBankCardChange(bw, redisBank.getBankCode(),
							redisBank.getBankName(), redisBank.getCardNo());
					logger.info("绑定银行卡回调，将redis中银行卡信息存入数据库中");
				}
				isBankCardChange = 1;
			} else {
				bb.setSignStatus(2);// 连连支付签约
				bb.setUpdateTime(new Date());
				bwBankCardService.update(bb);
			}
			request.setAttribute("isBankCardChange", isBankCardChange);
			// end

			return "sign_success_weixin2";
		} else {
			if (getUrlUserId(request)) {
				isBankCardChange = 1;
			}
			request.setAttribute("isBankCardChange", isBankCardChange);
			modelMap.put("respDesc", status);
			modelMap.put("result", result);
			return "sign_fail_weixin2";
		}
	}

	// 获取连连回调地址中的user_id, true为重新绑卡
	public boolean getUrlUserId(HttpServletRequest request) {
		HttpServletRequest httpRequest = request;
		String param = httpRequest.getQueryString();
		String[] params = param.split("&");
		String bwIdStr = "";
		for (int i = 0; i < params.length; i++) {
			if (params[i].indexOf("user_id") != -1) {
				bwIdStr = params[i];
			}
		}
		String bwId = bwIdStr.replace("user_id", "");
		if (bwId.indexOf("=") != -1) {
			bwId = bwId.replace("=", "");
		}
		if (RedisUtils.hexists("BANK_CARD_REBINDING", bwId)) {// 重新绑定
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 连连签约App回调
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("signCallBackApp.do")
	public String signCallBackApp(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String status = request.getParameter("status");
		String result = request.getParameter("result");
		logger.info("免登陆签约回调获取  交易代码:===" + status);
		logger.info("免登陆签约回调获取 交易结果描述:===" + result);
		if (!CommUtils.isNull(status) && (status.equals("0000"))) {
			JSONObject object = JSONObject.fromObject(result);
			String user_id = object.getString("user_id");
			logger.info("签约成功后返回的借款人id:" + user_id);
			BwBankCard bb = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(user_id));
			logger.info("免登陆签约从数据库获取的银行卡id:" + bb.getId());

			bb.setSignStatus(2);// 连连支付签约
			bb.setUpdateTime(new Date());
			bwBankCardService.update(bb);
			return "sign_success";
		} else {
			modelMap.put("respDesc", status);
			modelMap.put("result", result);
			return "sign_fail";
		}
	}

	/**
	 * 新 绑定银行卡app回调
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("signCallBackApp2.do")
	public String signCallBackApp2(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String status = request.getParameter("status");
		String result = request.getParameter("result");
		logger.info("免登陆签约回调获取  交易代码:===" + status);
		logger.info("免登陆签约回调获取 交易结果描述:===" + result);

		// 判断是否是重新绑定 start
		int isBankCardChange = 0;// 0 初次绑卡；1重新绑卡
		// String logintoken = request.getSession().getAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN).toString();
		// if (RedisUtils.hexists("BANK_CARD_REBINDING", logintoken)) {// 重新绑定
		// isBankCardChange = 1;
		// }
		// end

		if (!CommUtils.isNull(status) && (status.equals("0000"))) {
			JSONObject object = JSONObject.fromObject(result);
			String user_id = object.getString("user_id");
			logger.info("签约成功后返回的借款人id:" + user_id);
			BwBankCard bb = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(user_id));
			logger.info("免登陆签约从数据库获取的银行卡id:" + bb.getId());

			// start
			if (RedisUtils.hexists("BANK_CARD_REBINDING", user_id)) {// 重新绑定
				String bankJson = RedisUtils.hget("BANK_CARD_REBINDING", user_id);
				if (!StringUtil.isEmpty(bankJson)) {
					BwBankCard redisBank = JSON.toJavaObject(JSON.parseObject(bankJson), BwBankCard.class);
					BwBorrower bw = bwBorrowerService.findBwBorrowerById(Long.valueOf(user_id));
					bwBankCardChangeService.saveOrUpdBorrowerAndBankCardAndBankCardChange(bw, redisBank.getBankCode(),
							redisBank.getBankName(), redisBank.getCardNo());
					logger.info("======绑定银行卡回调，将redis中银行卡信息存入数据库中,bwId=" + user_id + "======");
				}
				isBankCardChange = 1;
			} else {
				bb.setSignStatus(2);// 连连支付签约
				bb.setUpdateTime(new Date());
				bwBankCardService.update(bb);
			}
			request.setAttribute("isBankCardChange", isBankCardChange);
			// end

			// bb.setSignStatus(2);// 连连支付签约
			// bb.setUpdateTime(new Date());
			// bwBankCardService.update(bb);
			return "sign_success2";
		} else {
			if (getUrlUserId(request)) {
				isBankCardChange = 1;
			}
			request.setAttribute("isBankCardChange", isBankCardChange);
			modelMap.put("respDesc", status);
			modelMap.put("result", result);
			return "sign_fail2";
		}
	}

	/**
	 * 根据卡号获取银行卡信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBankByCardNo.do")
	public AppResponseResult getBankByCardNo(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String cardNo = request.getParameter("cardNo");
		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		logger.info("【LianLianPayController.getBankByCardNo】cardNo：" + cardNo + "，paramMap=" + paramMap);
		if (StringUtils.isEmpty(cardNo) || cardNo.length() < 8) {
			result.setCode("101");
			result.setMsg("银行卡格式不对");
			return result;
		}
		String bankNameUtil = BankInfoUtils.getNameOfBank(cardNo.substring(0, 6));
		logger.info("============cardNo：" + cardNo + "工具类获取银行卡名称===:" + bankNameUtil);
		String bankCode = null;
		String bankName = null;
		if (!StringUtils.isEmpty(bankNameUtil)) {
			bankCode = ValidateUtil.getBankCode(bankNameUtil);
			logger.info("============工具类获取银行卡编码===:" + bankCode);
			bankName = RedisUtils.hget("fuiou:bank", bankCode);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 判断银行卡是否支持
		if (StringUtils.isEmpty(bankCode) || StringUtils.isEmpty(bankName)) {
			resultMap.put("supportBank", false);
		} else {
			resultMap.put("supportBank", true);
		}
		result.setCode("000");
		result.setMsg("SUCCESS");
		resultMap.put("bankName", bankName);
		resultMap.put("bankCode", bankCode);
		result.setResult(resultMap);
		return result;
	}

	/**
	 * 判断是否是银行卡号
	 * 
	 * @param cardId
	 * @return
	 */
	private boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;

	}

	private char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/***
	 * 真实姓名验证
	 *
	 * @param trueName
	 * @return
	 */
	private boolean checkTrueName(String trueName) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String check = "^[\u4E00-\u9FA5]{2,8}(?:·[\u4E00-\u9FA5]{2,8})*$";
		p = Pattern.compile(check); // 验证真实姓名
		m = p.matcher(trueName);
		b = m.matches();
		return b;
	}

	/**
	 * 身份证号码验证
	 *
	 * @param card
	 * @return
	 */
	private boolean checkIdentityCard(String card) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String check = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		p = Pattern.compile(check);
		m = p.matcher(card);
		b = m.matches();
		return b;
	}

	/**
	 * 验证银行卡名字和卡号是否一致
	 * 
	 * @param bankCode
	 * @param cardNo
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkBankNameAndCode(String bankCode, String cardNo) {
		String bankName = BankInfoUtils.getNameOfBank(cardNo.substring(0, 6));
		logger.info("银行卡名称==========" + bankName);
		if (bankCode.equals("0105")) {// 中国建设银行
			if (!bankName.contains("建设")) {
				return false;
			}
		}
		if (bankCode.equals("0303")) {// 中国光大银行
			if (!bankName.contains("光大")) {
				return false;
			}
		}
		if (bankCode.equals("0308")) {// 招商银行
			if (!bankName.contains("招商")) {
				return false;
			}
		}
		if (bankCode.equals("0103")) {// 中国农业银行
			if (!bankName.contains("农业")) {
				return false;
			}
		}
		if (bankCode.equals("0301")) {// 交通银行
			if (!bankName.contains("交通")) {
				return false;
			}
		}
		if (bankCode.equals("0307")) {// 平安银行股份有限公司
			if (!bankName.contains("平安")) {
				return false;
			}
		}
		if (bankCode.equals("0310")) {// 上海浦东发展银行
			if (!bankName.contains("浦东")) {
				return false;
			}
		}
		if (bankCode.equals("0403")) {// 中国邮政储蓄银行有限公司
			if (!bankName.contains("邮政储蓄")) {
				return false;
			}
		}
		if (bankCode.equals("0304")) {// 华夏银行
			if (!bankName.contains("华夏银行")) {
				return false;
			}
		}
		if (bankCode.equals("0305")) {// 中国民生银行
			if (!bankName.contains("民生银行")) {
				return false;
			}
		}
		if (bankCode.equals("0104")) {// 中国银行
			if (!bankName.contains("中国银行")) {
				return false;
			}
		}
		if (bankCode.equals("0102")) {// 中国工商银行
			if (!bankName.contains("工商银行")) {
				return false;
			}
		}
		if (bankCode.equals("0306")) {// 广东发展银行
			if (!bankName.contains("广发银行")) {
				return false;
			}
		}
		if (bankCode.equals("0309")) {// 兴业银行
			if (!bankName.contains("兴业")) {
				return false;
			}
		}
		if (bankCode.equals("0302")) {// 中信实业银行
			if (!bankName.contains("中信银行")) {
				return false;
			}
		}

		return true;
	}

	private String getBankCode(String bankName) {
		String bankCode = "";

		// 中国建设银行
		if (bankName.contains("建设")) {
			bankCode = "0105";
		}

		// 中国光大银行
		if (bankName.contains("光大")) {
			bankCode = "0303";
		}

		// 招商银行
		if (bankName.contains("招商")) {
			bankCode = "0308";
		}

		// 中国农业银行
		if (bankName.contains("农业")) {
			bankCode = "0103";
		}

		// 交通银行
		if (bankName.contains("交通")) {
			bankCode = "0301";
		}

		// 平安银行股份有限公司
		if (bankName.contains("平安")) {
			bankCode = "0307";
		}

		// 上海浦东发展银行
		if (bankName.contains("浦东")) {
			bankCode = "0310";
		}

		// 中国邮政储蓄银行有限公司
		if (bankName.contains("邮政")) {
			bankCode = "0403";
		}

		// 华夏银行
		if (bankName.contains("华夏")) {
			bankCode = "0304";
		}

		// 中国民生银行
		if (bankName.contains("民生")) {
			bankCode = "0305";
		}

		// 中国银行
		if (bankName.contains("中国银行")) {
			bankCode = "0104";
		}

		// 中国工商银行
		if (bankName.contains("工商")) {
			bankCode = "0102";
		}

		// 广东发展银行
		if (bankName.contains("广发")) {
			bankCode = "0306";
		}

		// 兴业银行
		if (bankName.contains("兴业")) {
			bankCode = "0309";
		}

		// 中信实业银行
		if (bankName.contains("中信")) {
			bankCode = "0302";
		}

		return bankCode;
	}
}
