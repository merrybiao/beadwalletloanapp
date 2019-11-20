package com.waterelephant.rongCarrier.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.rong360.entity.response.FundData2;
import com.beadwallet.service.rong360.entity.response.FundDataList;
import com.beadwallet.service.rong360.entity.response.FundFlow;
import com.beadwallet.service.rong360.entity.response.FundResponse;
import com.beadwallet.service.rong360.entity.response.FundUser;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.rongCarrier.entity.BwFundInfo;
import com.waterelephant.rongCarrier.entity.BwFundRecord;
import com.waterelephant.rongCarrier.service.BwFundInfoService;
import com.waterelephant.rongCarrier.service.BwFundRecordService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;

/**
 * 融360 - 公积金
 * 
 * @author liuDaodao
 *
 */
@Controller
@RequestMapping("/app/rongFund")
public class RongFundController {
	private Logger logger = Logger.getLogger(RongFundController.class);
	private static Map<String, String> STATE = new HashMap<>(); // 回调state字段释义
	static {
		STATE.put("init", "初始化");
		STATE.put("login", "登录成功");
		STATE.put("login_fail", "登录失败");
		STATE.put("crawl", "抓取成功");
		STATE.put("crawl_fail", "抓取失败");
		STATE.put("report", "生成报告成功");
		STATE.put("report_fail", "生成报告失败");
	}

	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private BwFundInfoService bwFundInfoService;
	@Autowired
	private BwFundRecordService bwFundRecordService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * 融360 - 公积金(code0085)
	 * 
	 * @param
	 * @return
	 * @author liuDaodao
	 * @date 2017年6月6日
	 * @description 融360 - 公积金
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/collectuser.do")
	public String collectuser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String url = "auth_fail_common";
		try {
			// 第一步：取参数
			String outUniqueId = request.getParameter("orderId");
			String userId = request.getParameter("userId");
			String authChannel = request.getParameter("authChannel");
			String phone = request.getParameter("phone");

			// 第二步：验证参数
			if (CommUtils.isNull(userId)) {
				request.setAttribute("msg", "用户编号为空，请核查后重新认证");
				return "auth_fail_common";
			}
			if (CommUtils.isNull(outUniqueId)) {
				request.setAttribute("msg", "工单编号为空，请核查后重新认证");
				return "auth_fail_common";
			}
			if (CommUtils.isNull(authChannel)) {
				request.setAttribute("msg", "渠道为空，请核查后重新认证");
				return "auth_fail_common";
			}

			// 第三步：请求入参
			BwBorrower q = new BwBorrower();
			q.setId(Long.valueOf(userId));
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByAttr(q);
			String name = bwBorrower.getName();
			String idCard = bwBorrower.getIdCard();
			logger.info("公积金认证入参：authChannel：" + authChannel + ",userId:" + userId + ",outUniqueId:" + outUniqueId
					+ ",phone:" + phone + ",idCard:" + idCard);
			Map<String, String> map = new HashMap<>();
			map.put("userId", userId);
			map.put("outUniqueId", outUniqueId);
			map.put("authChannel", authChannel);
			map.put("name", name);
			map.put("idCard", idCard);
			map.put("phone", phone);
			String returnStr = BeadWalletRongCarrierService.collectuserFund(map);

			// 处理结果
			// logger.info("认证出参：" + returnStr);
			if (CommUtils.isNull(returnStr)) {
				request.setAttribute("msg", "公积金认证失败，请重新认证");
				logger.info("公积金认证：调用第三方接口返回为空");
				return "auth_fail_common";
			} else {
				Map<String, Object> returnMap = JSON.parseObject(returnStr);
				Map<String, String> mapTem = (Map<String, String>) returnMap
						.get("tianji_api_tianjireport_collectuser_response");
				url = CommUtils.toString(mapTem.get("redirectUrl"));
				if (!CommUtils.isNull(url)) {
					response.setStatus(201);
					return "redirect:" + url;
				} else {
					request.setAttribute("msg", "公积金认证失败，请重新认证");
					logger.info("公积金认证：未去获取到跳转URL");
					return "auth_fail_common";
				}
			}
		} catch (Exception e) {
			response.setStatus(101);
			logger.error("系统异常", e);
		}
		return url;
	}

	/**
	 * 融360 - 公积金 - 同步通知页面（code0085）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/returnURLOfFund.do")
	public String returnURLOfFund(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getCurrentDateString("yyyyMMddHHmmssSSS");
		logger.info(sessionId + "：开始公积金同步通知");
		try {
			// 第零步：准备工作
			String pageUrl = ResourceBundle.getBundle("config").getString("page_url"); // 页面路径
			String pageProjectName = ResourceBundle.getBundle("config").getString("page_project_name");// 项目名

			// 第一步：取参数
			// String userId = request.getParameter("userId"); // 用户ID
			String outUniqueId = request.getParameter("outUniqueId"); // 唯一标识ID
			String state = request.getParameter("state"); // 状态
			String authChannel = request.getParameter("authChannel"); // 渠道
			logger.info(sessionId + "：执行公积金同步通知，入参：" + JSON.toJSONString(request.getParameterMap()));

			// 第二步：
			if ("login".equals(state) || "crawl".equals(state) || "report".equals(state)) {
				Thread task = new Thread(new Runnable() {
					@Override
					public void run() {
						// 添加公积金认证记录
						BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(outUniqueId), 6);
						if (CommUtils.isNull(bwOrderAuth)) {
							bwOrderAuth = new BwOrderAuth();
							bwOrderAuth.setCreateTime(new Date());
							bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
							bwOrderAuth.setOrderId(Long.parseLong(outUniqueId));
							bwOrderAuth.setAuth_type(6); // 6表示公积金
							bwOrderAuth.setUpdateTime(new Date());
							bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
						} else {
							bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
							bwOrderAuth.setOrderId(Long.parseLong(outUniqueId));
							bwOrderAuth.setAuth_type(6); // 6表示公积金
							bwOrderAuth.setUpdateTime(new Date());
							bwOrderAuthService.updateBwOrderAuth(bwOrderAuth);
						}
					}
				});
				// 关闭线程
				taskExecutor.execute(task);

				if ("4".equals(authChannel)) {
					return "redirect:" + pageUrl + pageProjectName + "/html/CreditCertification/index.html";
				} else {
					request.setAttribute("msg", "您的公积金认证成功");
					return "auth_success_common";
				}

			} else {
				if ("4".equals(authChannel)) {
					return "redirect:" + pageUrl + pageProjectName
							+ "/html/CreditCertification/failure.html?category=4";
				} else {
					request.setAttribute("msg", "您的公积金认证失败，请重新认证");
					return "auth_fail_common";
				}
			}
		} catch (Exception e) {
			logger.info("系统异常", e);
		}
		logger.info(sessionId + "：成功结束公积金同步通知");
		request.setAttribute("msg", "您的公积金认证成功");
		return "auth_success_common";
	}

	/**
	 * 融360 - 公积金 - 后台回调（code0085）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/notifyURLOfFund.do")
	public void notifyURLOfFund(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getCurrentDateString("yyyyMMddHHmmssSSS");
		logger.info(sessionId + "：开始公积金后台回调");
		try {
			// 第一步：取参数
			// String userId = request.getParameter("userId"); // 用户ID
			String outUniqueId = request.getParameter("outUniqueId"); // 唯一标识ID
			String state = request.getParameter("state"); // 状态
			// 查询的账户名，运营商对应手机号，电商对应京东账号
			// String account = request.getParameter("account");
			// 账户类型，可能为mobile,jd,ec等
			// String accountType = request.getParameter("accountType");
			String search_id = request.getParameter("search_id"); // 查询ID

			logger.info(sessionId + "：执行公积金后台回调，入参outUniqueId=" + outUniqueId + "&state=" + state + "&search_id="
					+ search_id);

			if ("report".equals(state) == false) { // 未生成报告时，不执行后续操作
				logger.info(sessionId + "：结束公积金后台回调，报告还未生成【" + state + "】，等待第三方再次回调");
				return;
			}

			// 第二步：开始抓取数据（报告已生成）
			Map<String, String> fundMap = new HashMap<>();
			fundMap.put("search_id", search_id);

			FundResponse fundResponse = BeadWalletRongCarrierService.rongFundSource(fundMap);
			if (fundResponse == null) {
				logger.info(sessionId + "：结束公积金后台回调，报告获取返回为空");
				return;
			}
			if (fundResponse.getError() != 200) {
				logger.info(sessionId + "：结束公积金后台回调失败，告获取返回{error=" + fundResponse.getError() + ",msg="
						+ fundResponse.getMsg() + "}");
				return;
			}

			// 第三步：删除旧数据（根据工单ID）
			Thread task = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						BwFundInfo bwFundInfo = new BwFundInfo();
						bwFundInfo.setOrderId(Long.parseLong(outUniqueId));
						bwFundInfoService.deleteBwFundInfo(bwFundInfo); // 删除公积金账户信息

						BwFundRecord bwFundRecord = new BwFundRecord();
						bwFundRecord.setOrderId(Long.parseLong(outUniqueId));
						bwFundRecordService.deleteBwFundRecord(bwFundRecord); // 删除公积金缴费记录
						logger.info(sessionId + "：公积金后台回调，旧数据删除成功");

						// 第四步：存入数据库
						FundData2 fundData2 = fundResponse.getWd_api_fund_getData_response().getData();
						List<FundDataList> fundDataLists = fundData2.getData_list();
						for (FundDataList fundDataList : fundDataLists) {
							// begin of 公积金对应的账号信息
							FundUser fundUser = fundDataList.getUser(); // 公积金对应的账户信息
							bwFundInfo = new BwFundInfo();
							bwFundInfo.setAccAddr(fundUser.getAcc_addr());
							bwFundInfo.setAccProp(fundUser.getAcc_prop());
							bwFundInfo.setBalance(fundUser.getBalance());
							bwFundInfo.setBirthday(fundUser.getBirthday());
							bwFundInfo.setCellphone(fundUser.getCellphone());
							bwFundInfo.setComCode(fundUser.getCom_code());
							bwFundInfo.setComName(fundUser.getCom_name());
							bwFundInfo.setCreateTime(new Date());
							bwFundInfo.setDegree(fundUser.getDegree());
							bwFundInfo.setEmail(fundUser.getEmail());
							bwFundInfo.setFormatAccProp(fundUser.getFormat_acc_prop());
							bwFundInfo.setFormatDegree(fundUser.getFormat_degree());
							bwFundInfo.setFromatFundStatus(fundUser.getFormat_fund_status());
							bwFundInfo.setFundBaseMoney(fundUser.getFund_base_money());
							bwFundInfo.setFundCity(fundUser.getFund_city());
							bwFundInfo.setFundCode(fundUser.getFund_code());
							bwFundInfo.setFundMonthMoney(fundUser.getFund_month_money());
							bwFundInfo.setFundStatus(fundUser.getFund_status());
							bwFundInfo.setIdCard(fundUser.getId_card());
							bwFundInfo.setLiveAddr(fundUser.getLive_addr());
							bwFundInfo.setLivePostcode(fundUser.getLive_postcode());
							bwFundInfo.setMaritalStatus(fundUser.getMarital_status());
							bwFundInfo.setNation(fundUser.getNation());
							bwFundInfo.setOrderId(Long.parseLong(outUniqueId));
							bwFundInfo.setPhone(fundUser.getPhone());
							bwFundInfo.setRealName(fundUser.getReal_name());
							bwFundInfo.setSex(fundUser.getSex());
							bwFundInfo.setWorkerNation(fundUser.getWorker_nation());
							bwFundInfo.setWorkStartDay(fundUser.getWork_start_day());
							bwFundInfoService.save(bwFundInfo);
							logger.info(sessionId + "：公积金后台回调，账户信息保存成功");
							// end of 公积金对应的账号信息

							// begin of 公积金缴费流水
							List<FundFlow> flowList = fundDataList.getFlow();
							bwFundRecordService.saveList(flowList, bwFundInfo, Long.parseLong(outUniqueId)); // 公积金账户对应的公积金流水
							logger.info(sessionId + "：公积金后台回调，公积金缴费流水保存成功");
							// end of 公积金缴费流水
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("融360 - 公积金 - 回台回调，根据工单【" + outUniqueId + "】删除就数据异常：", e);
					}


				}
			});

			taskExecutor.execute(task);

		} catch (Exception e) {
			logger.info(" 执行公积金后台回调，系统异常", e);
		}
		logger.info(sessionId + "：结束公积金后台回调");
	}

	// fund_returnURL=http://106.14.238.126:8092/beadwalletloanapp/app/rongFund/returnURLOfFund.do
	// fund_notifyURL=http://106.14.238.126:8092/beadwalletloanapp/app/rongFund/notifyURLOfFund.do
}
