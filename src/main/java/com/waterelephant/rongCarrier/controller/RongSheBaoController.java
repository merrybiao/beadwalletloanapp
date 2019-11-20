package com.waterelephant.rongCarrier.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.rong360.entity.response.SheBaoData1;
import com.beadwallet.service.rong360.entity.response.SheBaoData2;
import com.beadwallet.service.rong360.entity.response.SheBaoDataList;
import com.beadwallet.service.rong360.entity.response.SheBaoFlow;
import com.beadwallet.service.rong360.entity.response.SheBaoResponse;
import com.beadwallet.service.rong360.entity.response.SheBaoUser;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwInsureInfo;
import com.waterelephant.entity.BwInsureRecord;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.rongCarrier.entity.RongValidCity;
import com.waterelephant.rongCarrier.service.RongValidCityService;
import com.waterelephant.service.BwInsureInfoService;
import com.waterelephant.service.BwInsureRecordService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;

/**
 * 社保获取接口
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/27 16:02
 */
@Controller
@RequestMapping("/app/rongshebao")
public class RongSheBaoController {
	private Logger logger = Logger.getLogger(RongSheBaoController.class);
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
	private RongValidCityService rongValidCityService;
	@Autowired
	private BwInsureInfoService bwInsureInfoService;
	@Autowired
	private BwInsureRecordService bwInsureRecordService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * 融360 - 社保(code0084)
	 * 
	 * @param
	 * @return
	 * @author 郭坤
	 * @date 2017年3月25日
	 * @description 认证成功通知 H5
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/collectuser.do")
	public String collectuserShebao(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String url = "auth_fail_common";
		try {
			// rongValidCityService.saveBatchOfSheBao(); // 批量更新
			// boolean isExist =
			// rongValidCityService.findByCityNameOfLike("武汉市",
			// RongValidCity.TYPE_SHEBAO);
			// System.out.println(isExist);// 判断该城市是否存在

			// 取参数(code0084)
			String outUniqueId = request.getParameter("orderId") + "_SHEBAO";
			String userId = request.getParameter("userId");
			String authChannel = request.getParameter("authChannel");
			String phone = request.getParameter("phone");

			if (CommUtils.isNull(userId)) {
				request.setAttribute("msg", "用户编号为空，请重新认证");
				return "auth_fail_common";
			}
			if (CommUtils.isNull(outUniqueId)) {
				request.setAttribute("msg", "工单编号为空，请重新认证");
				return "auth_fail_common";
			}
			if (CommUtils.isNull(authChannel)) {
				request.setAttribute("msg", "渠道为空，请重新认证");
				return "auth_fail_common";
			}

			// 请求入参
			BwBorrower q = new BwBorrower();
			q.setId(Long.valueOf(userId));
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByAttr(q);
			String name = bwBorrower.getName();
			String idCard = bwBorrower.getIdCard();
			logger.info("社保认证入参：authChannel：" + authChannel + ",userId:" + userId + ",outUniqueId:" + outUniqueId
					+ ",phone:" + phone + ",idCard:" + idCard);
			Map<String, String> map = new HashMap<>();
			map.put("userId", userId);
			map.put("outUniqueId", outUniqueId);
			map.put("authChannel", authChannel);
			map.put("name", name);
			map.put("idCard", idCard);
			map.put("phone", phone);
			String returnStr = BeadWalletRongCarrierService.collectuserSheBao(map);

			// 处理结果
			// logger.info("认证出参：" + returnStr);
			if (CommUtils.isNull(returnStr)) {
				request.setAttribute("msg", "社保认证失败，请重新认证");
				logger.info("社保认证：第三方接口返回参数为空");
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
					request.setAttribute("msg", "社保认证失败，请重新认证");
					logger.info("社保认证：未获取到跳转URL");
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
	 * 融360 - 社保 - 二级联动（省份）（code0084）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProvinceListOfShebao.do")
	public AppResponseResult getProvinceListOfShebao(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			List<Map<String, Object>> mapList = rongValidCityService.findProvinceList(RongValidCity.TYPE_SHEBAO);
			if (mapList != null && mapList.size() > 0) {
				appResponseResult.setCode("200");
				appResponseResult.setMsg("获取成功");
				appResponseResult.setResult(mapList);
			} else {
				appResponseResult.setCode("100");
				appResponseResult.setMsg("未查询到数据");
			}
		} catch (Exception e) {
			logger.error("系统异常", e);
			appResponseResult.setCode("000");
			appResponseResult.setMsg("异常：" + e.getMessage());
		}
		return appResponseResult;
	}

	/**
	 * 融360 - 社保 - 二级联动（城市）（code0084）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCityListOfShebao.do")
	public AppResponseResult getCityListOfShebao(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			// 取参数
			String provinceName = request.getParameter("provinceName");
			if (StringUtils.isEmpty(provinceName) == true) {
				appResponseResult.setCode("100");
				appResponseResult.setMsg("省份为空");
			}

			// 取数据
			List<Map<String, Object>> mapList = rongValidCityService.findCityList(RongValidCity.TYPE_SHEBAO,
					provinceName);
			if (mapList != null && mapList.size() > 0) {
				appResponseResult.setCode("200");
				appResponseResult.setMsg("获取成功");
				appResponseResult.setResult(mapList);
			} else {
				appResponseResult.setCode("100");
				appResponseResult.setMsg("未查询到数据");
			}
		} catch (Exception e) {
			logger.error("系统异常", e);
			appResponseResult.setCode("000");
			appResponseResult.setMsg("异常：" + e.getMessage());
		}
		return appResponseResult;
	}

	/**
	 * 融360 - 社保 - 同步通知页面（code0084）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/returnURLOfSheBao.do")
	public String returnURLOfSheBao(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getCurrentDateString("yyyyMMddHHmmssSSS");
		logger.info(sessionId + "：开始社保同步通知");
		try {
			// 第零步：准备工作
			String pageUrl = ResourceBundle.getBundle("config").getString("page_url"); // 页面路径
			String pageProjectName = ResourceBundle.getBundle("config").getString("page_project_name");// 项目名

			// 第一步：取参数
			// String userId = request.getParameter("userId"); // 用户ID
			String outUniqueId = request.getParameter("outUniqueId"); // 唯一标识ID
			String state = request.getParameter("state"); // 状态
			String authChannel = request.getParameter("authChannel"); // 渠道
			logger.info(sessionId + "：执行社保同步通知，入参：" + JSON.toJSONString(request.getParameterMap()));

			// 第二步：
			if ("login".equals(state) || "crawl".equals(state) || "report".equals(state)) {
				// 添加社保认证记录
				Thread task = new Thread(new Runnable() {
					@Override
					public void run() {
						BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(outUniqueId), 5);
						if (CommUtils.isNull(bwOrderAuth)) {
							bwOrderAuth = new BwOrderAuth();
							bwOrderAuth.setCreateTime(new Date());
							bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
							bwOrderAuth.setOrderId(Long.parseLong(outUniqueId));
							bwOrderAuth.setAuth_type(5); // 5表示社保
							bwOrderAuth.setUpdateTime(new Date());
							bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
						} else {
							bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
							bwOrderAuth.setOrderId(Long.parseLong(outUniqueId));
							bwOrderAuth.setAuth_type(5); // 5表示社保
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
					request.setAttribute("msg", "您的社保认证成功");
					return "auth_success_common";
				}

			} else {
				if ("4".equals(authChannel)) {
					return "redirect:" + pageUrl + pageProjectName
							+ "/html/CreditCertification/failure.html?category=1";
				} else {
					request.setAttribute("msg", "您的社保认证失败，请重新认证");
					return "auth_fail_common";
				}
			}
		} catch (Exception e) {
			logger.info("系统异常", e);
		}
		logger.info(sessionId + "：成功结束社保同步通知");
		request.setAttribute("msg", "您的社保认证成功");
		return "auth_success_common";
	}

	/**
	 * 融360 - 社保 - 后台回调（code0084）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/notifyURLOfSheBao.do")
	public void notifyURLOfSheBao(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getCurrentDateString("yyyyMMddHHmmssSSS");
		logger.info(sessionId + "：开始社保后台回调");
		try {
			// 第一步：取参数
			// String userId = request.getParameter("userId"); // 用户ID
			String outUniqueId = request.getParameter("outUniqueId"); // 唯一标识ID
			String state = request.getParameter("state"); // 状态
			// String account = request.getParameter("account"); //
			// 查询的账户名，运营商对应手机号，电商对应京东账号
			// String accountType = request.getParameter("accountType"); //
			// 账户类型，可能为mobile,jd,ec等
			String search_id = request.getParameter("search_id"); // 查询ID
			logger.info(sessionId + "：执行社保后台回调，入参：" + JSON.toJSONString(request.getParameterMap()));

			if ("report".equals(state) == false) { // 未生成报告时，不执行后续操作
				logger.info(sessionId + "：结束社保后台回调，报告还未生成【" + state + "】，等待第三方再次回调");
				return;
			}

			// 第二步：开始抓取数据（报告已生成）
			Map<String, String> sheBaoMap = new HashMap<>();
			sheBaoMap.put("search_id", search_id);

			SheBaoResponse sheBaoResponse = BeadWalletRongCarrierService.rongSheBaoSource(sheBaoMap);
			if (sheBaoResponse == null) {
				logger.info(sessionId + "：结束社保后台回调，报告获取返回为空");
				return;
			}
			if (sheBaoResponse.getError() != 200) {
				logger.info(sessionId + "：结束社保后台回调失败，告获取返回{error=" + sheBaoResponse.getError() + ",msg="
						+ sheBaoResponse.getMsg() + "}");
				return;
			}

			// // 第三步：删除旧数据（根据工单ID）
			// try {
			// BwInsureInfo bwInsureInfo = new BwInsureInfo();
			// bwInsureInfo.setOrderId(Long.parseLong(outUniqueId));
			// bwInsureInfoService.deleteBwInsureInfo(bwInsureInfo); // 删除社保账户信息
			//
			// BwInsureRecord bwInsureRecord = new BwInsureRecord();
			// bwInsureRecord.setOrderId(Long.parseLong(outUniqueId));
			// bwInsureRecordService.deleteBwInsureRecord(bwInsureRecord);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }

			Thread task = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// 第三步：删除旧数据（根据工单ID）
						BwInsureInfo bwInsureInfo = new BwInsureInfo();
						bwInsureInfo.setOrderId(Long.parseLong(outUniqueId));
						bwInsureInfoService.deleteBwInsureInfo(bwInsureInfo); // 删除社保账户信息

						BwInsureRecord bwInsureRecord = new BwInsureRecord();
						bwInsureRecord.setOrderId(Long.parseLong(outUniqueId));
						bwInsureRecordService.deleteBwInsureRecord(bwInsureRecord);
						// 第四步：存入数据库
						SheBaoData1 sheBaoData1 = sheBaoResponse.getWd_api_insure_getData_response();
						SheBaoData2 sheBaoData2 = sheBaoData1.getData();
						List<SheBaoDataList> sheBaoDataLists = sheBaoData2.getData_list();
						for (SheBaoDataList sheBaoDataList : sheBaoDataLists) {
							// begin of 社保对应的账号信息
							SheBaoUser sheBaoUser = sheBaoDataList.getUser(); // 社保对应的账户信息
							bwInsureInfo = new BwInsureInfo();
							bwInsureInfo.setRealName(sheBaoUser.getReal_name());
							bwInsureInfo.setIdCard(sheBaoUser.getId_card());
							bwInsureInfo.setSex(sheBaoUser.getSex());
							bwInsureInfo.setBirthday(sheBaoUser.getBirthday());
							bwInsureInfo.setWorkStartDay(sheBaoUser.getWork_start_day());
							bwInsureInfo.setAccProp(sheBaoUser.getAcc_prop());
							bwInsureInfo.setAccAddr(sheBaoUser.getAcc_addr());
							bwInsureInfo.setDegree(sheBaoUser.getDegree());
							bwInsureInfo.setCellphone(sheBaoUser.getCellphone());
							bwInsureInfo.setPhone(sheBaoUser.getPhone());
							bwInsureInfo.setEmail(sheBaoUser.getEmail());
							bwInsureInfo.setInsureCode(sheBaoUser.getInsure_code());
							bwInsureInfo.setInsureCity(sheBaoUser.getInsure_city());
							bwInsureInfo.setInsureStatus(sheBaoUser.getInsure_status());
							bwInsureInfo.setInsureMonthMoney(sheBaoUser.getInsure_month_money());
							bwInsureInfo.setComName(sheBaoUser.getCom_name());
							bwInsureInfo.setComCode(sheBaoUser.getCom_code());
							bwInsureInfo.setNation(sheBaoUser.getNation());
							bwInsureInfo.setLiveAddr(sheBaoUser.getLive_addr());
							bwInsureInfo.setLivePostcode(sheBaoUser.getLive_postcode());
							bwInsureInfo.setMaritalStatus(sheBaoUser.getMarital_status());
							bwInsureInfo.setWorkerNation(sheBaoUser.getWorker_nation());
							bwInsureInfo.setStartInsureDay(sheBaoUser.getStart_insure_day());
							bwInsureInfo.setFormatAccProp(sheBaoUser.getFormat_acc_prop());
							bwInsureInfo.setFormatDegree(sheBaoUser.getFormat_degree());
							bwInsureInfo.setOrderId(Long.parseLong(outUniqueId));
							bwInsureInfo.setCreateTime(new Date());
							bwInsureInfoService.save(bwInsureInfo);
							// end of 社保对应的账号信息

							// begin of 社保账户对应的社保流水
							List<SheBaoFlow> flowList = sheBaoDataList.getFlow(); // 社保账户对应的社保流水
							bwInsureRecordService.saveList(flowList, bwInsureInfo.getId(), Long.parseLong(outUniqueId));
							// 以下插入速度过于底下，已使用上面的批量插入方法
							// for (SheBaoFlow sheBaoFlow : flowList) {
							// BwInsureRecord bwInsureRecord = new BwInsureRecord();
							// bwInsureRecord.setIdCard(sheBaoFlow.getId_card());
							// bwInsureRecord.setPayDate(sheBaoFlow.getPay_date());
							// bwInsureRecord.setStartDate(sheBaoFlow.getStart_date());
							// bwInsureRecord.setEndDate(sheBaoFlow.getEnd_date());
							// bwInsureRecord.setBaseRmb(sheBaoFlow.getBase_rmb());
							// bwInsureRecord.setComRmb(sheBaoFlow.getCom_rmb());
							// bwInsureRecord.setPerRmb(sheBaoFlow.getPer_rmb());
							// bwInsureRecord.setBalanceRmb(sheBaoFlow.getBalance_rmb());
							// bwInsureRecord.setMonthRmb(sheBaoFlow.getMonth_rmb());
							// bwInsureRecord.setComName(sheBaoFlow.getCom_name());
							// bwInsureRecord.setPayType(sheBaoFlow.getPay_type());
							// bwInsureRecord.setFlowType(Integer.parseInt(sheBaoFlow.getFlow_type()));
							// bwInsureRecord.setInsureInfoId(bwInsureInfo.getId());// 社保信息
							// bwInsureRecord.setOrderId(Long.parseLong(outUniqueId));
							// bwInsureRecordService.save(bwInsureRecord);
							// }
							// end of 社保账户对应的社保流水
						}
					} catch (Exception e) {
						logger.info("保存社保数据异常：", e);
					}
				}
			});
			// 关闭线程
			taskExecutor.execute(task);

		} catch (Exception e) {
			logger.error(" 执行社保后台回调，系统异常", e);
			// logger.info(sessionId + "执行社保后台回调，系统异常：" + e.getMessage());
		}
		logger.info(sessionId + "：结束社保后台回调");
	}

	// sheBao_returnURL=http://139.224.17.43:8092/beadwalletloanapp/app/rongshebao/returnURLOfSheBao.do
	// sheBao_notifyURL=http://139.224.17.43:8092/beadwalletloanapp/app/rongshebao/notifyURLOfSheBao.do
}
