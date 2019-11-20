package com.waterelephant.haoDai.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.entity.lianlian.SignResponse;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.base.BaseController;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: SurfaceSignController
 * @Description: TODO(签约生成合同控制器)
 * @author SongYajun
 * @date 2016年9月3日 上午9:47:10
 *
 */
@Controller
@RequestMapping("/loan/signing")
public class SurfaceSignController extends BaseController {

	private Logger logger = Logger.getLogger(SurfaceSignController.class);

	@Resource
	private BwOrderService bwOrderService;

	@Resource
	private BwBorrowerService bwBorrowerService;

	@Resource
	private IBwBankCardService iBwBankCardService;
	
	@Resource
	private BwZmxyGradeService bwZmxyGradeService;

	/**
	 * 签约操作
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "signContract.do")
	public void signContract(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		logger.info("========================签约用户:" + userId);
		BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(Long.valueOf(userId));
		BwBankCard bwBankCard = iBwBankCardService.findBwBankCardByBoorwerId(Long.valueOf(userId));
		SignLess signLess = new SignLess();
		signLess.setUser_id(userId);
		signLess.setId_no(bwBorrower.getIdCard());
		signLess.setAcct_name(bwBorrower.getName());
		signLess.setCard_no(bwBankCard.getCardNo());
		signLess.setUrl_return(SystemConstant.CALLBACK_URL + "/loan/signing/syntony.do"); // 回掉地址
		SignResponse signResponse = LianLianPayService.signAccreditPay(signLess, response);
		logger.info("=========================签约操作");
	}

	/**
	 * 签约回调
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "syntony.do")
	public String syntony(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String status = request.getParameter("status");
		logger.info("==========================签约回掉状态:" + status);
		String result = request.getParameter("result");
		logger.info("=========================签约回掉结果:" + result);
		if (CommUtils.isNull(status)) {
			logger.info("====================================签约异常返回首页");
			return "index";
		}
		// 签约成功
		if (status.equals("0000")) {
			try {
				logger.info("========================================签约成功");
				JSONObject jsonObject = JSONObject.fromObject(result);
				String userId = jsonObject.getString("user_id");
				// 更新为已签约
				iBwBankCardService.updateSignStatusByBorrowerId(Long.valueOf(userId));
				Long orderId = bwOrderService.findBorrwerIdByOrderId(Long.valueOf(userId));
				logger.info("========================签约成功的工单id:" + orderId + "========签约成功的用户:" + userId);
				
				/**********************查看一个月内是否调用芝麻评分,没有则调用*******************/
				BwZmxyGrade bwZmxyGrade = bwZmxyGradeService
						.findZmxyGradeByBorrowerId(Long.valueOf(userId));
				if (CommUtils.isNull(bwZmxyGrade)) {
					// 查询芝麻评分
					return "redirect:/app/zmxy/zmmcportal.do?orderId=" + orderId + "&borrowerId="
							+ userId + "&channel=" + 12 ;
				} else {
					int daySpace = MyDateUtils.getDaySpace(bwZmxyGrade.getUpdateTime(), new Date());
					if (daySpace >= 30) {
						// 查询芝麻评分
						return "redirect:/app/zmxy/zmmcportal.do?orderId=" + orderId + "&borrowerId="
						+ userId + "&channel=" + 12 ;
					}
				}
				
				/****************************跳转运营商*****************************/
				String token = CommUtils.getUUID();
				request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
				SystemConstant.SESSION_APP_TOKEN.put(userId, token);
				logger.info("==========================借款app登录令牌:" + token);
				Cookie uidcookie = new Cookie("cookie_uuid", userId);
				uidcookie.setPath("/");
				Cookie tokencookie = new Cookie("cookie_token", token);
				tokencookie.setPath("/");
				response.addCookie(uidcookie);
				response.addCookie(tokencookie);
				modelMap.put("orderId", orderId);
				return "yunyingshang";
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				return "index";
			}
		} else {
			// 签约失败
			map.put("result", result);
			return "sign_fail_haodai";
		}
	}

}
