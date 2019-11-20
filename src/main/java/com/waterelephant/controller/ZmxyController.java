package com.waterelephant.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.serve.BeadWalletZmxyService;
import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.entity.BwZmxyRecord;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.BwZmxyRecordService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.utils.SystemConstant;

/**
 * 芝麻评分控制器
 * 
 * @author song
 *
 */
@Controller
@RequestMapping("/app/zmxy")
public class ZmxyController {

	private Logger logger = Logger.getLogger(ZmxyController.class);

	@Resource
	private IBwBorrowerService iBwBorrowerService;

	@Resource
	private BwOrderAuthService bwOrderAuthService;

	@Resource
	private BwZmxyGradeService bwZmxyGradeService;

	@Resource
	private BwZmxyRecordService bwZmxyRecordService;

	/**
	 * 跳转芝麻授权页面
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/zmmcportal.do")
	public void zmmcportal(HttpServletRequest request, HttpServletResponse response) {
		String orderId = request.getParameter("orderId");
		logger.info("===============工单id" + orderId);
		String borrowerId = request.getParameter("borrowerId");
		logger.info("===============借款人id" + borrowerId);
		String channel = request.getParameter("channel");
		logger.info("===============渠道来源为" + channel);
		String queryString = request.getParameter("queryString");
		logger.info("===============请求参数" + queryString);
		if (!CommUtils.isNull(orderId) || !CommUtils.isNull(borrowerId) || !CommUtils.isNull(channel)) {
			try {
				logger.info("=================查询借款人信息");
				BwBorrower bwBorrower = iBwBorrowerService.findNameAndIdCardById(Long.valueOf(borrowerId));
				logger.info("=================借款人姓名:" + bwBorrower.getName());
				// 传入商户数据格式,芝麻不允许用json格式。只允许用字符串
				String state = "orderId=" + orderId + "&borrowerId=" + borrowerId + "&channel=" + channel + "&name="
						+ bwBorrower.getName() + "&idCard=" + bwBorrower.getIdCard() + "";
				if (channel.equals("12")) {
					state += "&queryString=" + queryString + "";
				}
				logger.info("==================商户传入数据:" + state);
				Response<String> respon = BeadWalletZmxyService.zhimaAuthInfoAuthorize(bwBorrower.getName(),
						bwBorrower.getIdCard(), state);
				if (respon.getRequestCode().equals("200")) {
					logger.info("=================跳转麻授权页面:" + respon.getObj());
					// 跳转麻授权页面
					response.sendRedirect(respon.getObj());
				}
			} catch (IOException e) {
				logger.error("================跳转芝麻授权页面异常");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 芝麻评分回掉地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/zmxy_callback.do")
	public void zmxy_callback(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String params = request.getParameter("params");
		logger.info("===================芝麻回掉params:" + params);
		String sign = request.getParameter("sign");
		logger.info("====================芝麻回掉sign:" + sign);
		String pageProjectName = ResourceBundle.getBundle("config").getString("page_project_name");// 项目名
		String pageUrl = ResourceBundle.getBundle("config").getString("page_url");// 项目名
		if (!CommUtils.isNull(params) && !CommUtils.isNull(sign)) {
			try {
				String result = BeadWalletZmxyService.getResult(params, sign);
				logger.info("====================芝麻回掉响应结果:" + result);
				result = URLDecoder.decode(result, "utf-8");
				logger.info("====================编码之后的结果:" + result);
				Map<String, String> maps = this.analyticalResult(result);
				boolean success = false; // 查询芝麻分成功或者失败
				// 回掉成功
				if (maps.get("success").equals("true")) {
					// 查询芝麻评分
					Response<ZhimaCreditScoreGetResponse> respon = BeadWalletZmxyService
							.zhimaCreditScoreGet(this.randomTransactionId(), maps.get("open_id"));
					if (respon.getRequestCode().equals("200")) {
						logger.info("================查询芝麻分成功!");
						BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(maps.get("orderId")),
								4);
						Date now = new Date();
						if (CommUtils.isNull(bwOrderAuth)) {
							logger.info("=================添加认证信息");
							bwOrderAuth = new BwOrderAuth();
							bwOrderAuth.setOrderId(Long.valueOf(maps.get("orderId")));
							bwOrderAuth.setAuth_type(4);
							bwOrderAuth.setAuth_channel(Integer.valueOf(maps.get("channel"))); // 渠道
							bwOrderAuth.setCreateTime(now);
							bwOrderAuth.setUpdateTime(now);
							logger.info("================当前时间:" + now);
							bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
						}
						logger.info("======芝麻分:" + respon.getObj().getZmScore());
						logger.info("======流水号:" + respon.getObj().getBizNo());
						// 转换芝麻分
						int score = respon.getObj().getZmScore().equals("N/A") ? 0
								: Integer.valueOf(respon.getObj().getZmScore());
						// 保存芝麻分
						BwZmxyGrade bwZmxyGrade = bwZmxyGradeService
								.findZmxyGradeByBorrowerId(Long.valueOf(maps.get("borrowerId")));
						if (CommUtils.isNull(bwZmxyGrade)) {
							bwZmxyGrade = new BwZmxyGrade();
							bwZmxyGrade.setBorrowerId(Long.valueOf(maps.get("borrowerId")));
							bwZmxyGrade.setZmScore(score);
							bwZmxyGrade.setBizNo(respon.getObj().getBizNo());
							bwZmxyGrade.setCreateTime(now);
							bwZmxyGrade.setUpdateTime(now);
							bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
						} else {
							// 更新芝麻分
							bwZmxyGrade.setZmScore(score);
							bwZmxyGrade.setBizNo(respon.getObj().getBizNo());
							bwZmxyGrade.setUpdateTime(now);
							bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
						}
						// 添加流水
						BwZmxyRecord bwZmxyRecord = new BwZmxyRecord();
						bwZmxyRecord.setBorrowerId(Long.valueOf(maps.get("borrowerId")));
						bwZmxyRecord.setName(maps.get("name"));
						bwZmxyRecord.setIdCard(maps.get("idCard"));
						bwZmxyRecord.setZmScore(score);
						bwZmxyRecord.setBizNo(respon.getObj().getBizNo());
						bwZmxyRecord.setCreateTime(new Date());
						bwZmxyRecordService.saveBwZmxyRecord(bwZmxyRecord);
						success = true;
					}

				}
				logger.info("===============芝麻评分成功" + success);
				// 微信
				if (maps.get("channel").equals("4")) {
					// response.sendRedirect("https://www.beadwallet.com/loanpage/mashanglingqian.html");
					response.sendRedirect(pageUrl + pageProjectName + "/html/CreditCertification/index.html");
				}
				// 安卓和ios
				if (maps.get("channel").equals("1") || maps.get("channel").equals("2")) {
					// response.sendRedirect("https://www.beadwallet.com/loanpage/zhuangtai.html?name=zhimaxinyong&flag="
					// + success + "&channel=" + maps.get("channel"));
					response.sendRedirect(pageUrl + pageProjectName
							+ "/html/ZhimaCertification/zhuangtai.html?name=zhimaxinyong&flag=" + success + "&channel="
							+ maps.get("channel"));
				}
				// 12:好贷网
				if ((maps.get("channel").equals("12"))) {
					// String queryString = maps.get("queryString");
					// logger.info("=============好贷请求参数"+queryString);
					// response.sendRedirect("http://139.224.17.43:8092/beadwalletloanapp/bewadwallet/loan/haoDaiController.do?"+queryString+"");
					/**************************** 跳转运营商 *****************************/
					String token = CommUtils.getUUID();
					request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
					SystemConstant.SESSION_APP_TOKEN.put(maps.get("borrowerId"), token);
					logger.info("==========================借款app登录令牌:" + token);
					Cookie uidcookie = new Cookie("cookie_uuid", maps.get("borrowerId"));
					uidcookie.setPath("/");
					Cookie tokencookie = new Cookie("cookie_token", token);
					tokencookie.setPath("/");
					response.addCookie(uidcookie);
					response.addCookie(tokencookie);
					// modelMap.put("orderId", maps.get("orderId"));
					response.sendRedirect("bewadwallet/loan/yunyingshang.do?orderId=" + maps.get("orderId"));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析芝麻回掉的数据
	 * 
	 * @param result
	 * @return
	 */
	private Map<String, String> analyticalResult(String result) {
		Map<String, String> maps = new HashMap<>();
		if (CommUtils.isNull(result)) {
			return maps;
		}
		try {
			result = URLDecoder.decode(result, "utf-8");
			String[] results = result.split("&");
			String[] success = results[results.length - 1].split("=");
			String[] orderId = null;
			String[] borrowerId = null;
			String[] channel = null;
			String[] open_id = null;
			String[] name = null;
			String[] idCard = null;
			String[] queryString = null;
			for (int i = 0; i < results.length; i++) {
				if (results[i].indexOf("orderId") != -1) {
					orderId = results[i].split("=");
					continue;
				}
				if (results[i].indexOf("borrowerId") != -1) {
					borrowerId = results[i].split("=");
					continue;
				}
				if (results[i].indexOf("channel") != -1) {
					channel = results[i].split("=");
					continue;
				}
				if (results[i].indexOf("open_id") != -1) {
					open_id = results[i].split("=");
					continue;
				}
				if (results[i].indexOf("name") != -1) {
					name = results[i].split("=");
					continue;
				}
				if (results[i].indexOf("idCard") != -1) {
					idCard = results[i].split("=");
					continue;
				}
				if (results[i].indexOf("queryString") != -1) {
					queryString = results[i].split("=");
					continue;
				}
			}
			maps.put("orderId", orderId[orderId.length - 1]);
			maps.put("borrowerId", borrowerId[borrowerId.length - 1]);
			maps.put("channel", channel[channel.length - 1]);
			maps.put("success", success[success.length - 1]);
			maps.put("name", name[name.length - 1]);
			maps.put("idCard", idCard[idCard.length - 1]);
			if (!CommUtils.isNull(queryString)) {
				maps.put("queryString", queryString[queryString.length - 1]);
			}
			if (!CommUtils.isNull(open_id)) {
				maps.put("open_id", open_id[open_id.length - 1]);
			}
			logger.info("orderId=" + maps.get("orderId") + "=====borrowerId=" + maps.get("borrowerId"));
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			return maps;
		}
	}

	private static Long count = 0L;

	/**
	 * 随机生成业务流水号 生成规则: 固定30位数字串，前17位为精确到毫秒的时间yyyyMMddhhmmssSSS,后13位为自增数字。
	 */
	private String randomTransactionId() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String now = fmt.format(new Date());
		count++;
		String fullStr = null;
		if (count.toString().length() < 13) {
			for (int i = 0; i < 13 - count.toString().length(); i++) {
				if (i == 0) {
					fullStr = "0";
				} else {
					fullStr += "0";
				}

			}
		}
		if (CommUtils.isNull(fullStr)) {
			return now + count.toString();
		} else {
			return now + fullStr + count.toString();
		}
	}

	public static void main(String[] args) {

	}
}
