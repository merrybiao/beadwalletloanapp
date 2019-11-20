package com.waterelephant.rongCarrier.email.controller;

import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.rongCarrier.email.service.RongEmailService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.CommUtils;

@Controller
public class RongEmailCallBackController {

	private Logger logger = LoggerFactory.getLogger(RongEmailCallBackController.class);
	
	@Autowired
	private RongEmailService rongEmailService;
	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	
	/**
	 * 融360回调数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/app/rongCarrier/emailcallback.do")
	public void collectEmailCallBack(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始执行app的RongEmailCallBackController的collectEmailCallBack（）方法！");
		// 第一步：获取回调参数
		String userId = request.getParameter("userId");
		String state = request.getParameter("state");
		String searchId = request.getParameter("search_id");
		if ("login".equals(state)) {
			logger.info("登录成功，rong360开始抓取数据！");
		}
		if("report".equals(state)) {
		    Thread task = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 保存邮箱数据
                    try{
                        // 保存邮箱数据
                        rongEmailService.getData(userId,searchId);
                    }catch (Exception e) {
                        logger.error("保存邮箱数据异常：", e);
                    }
                }
            });
            taskExecutor.execute(task);
		}
	}
	
	/**
	 * 页面重定向
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/app/rongCarrier/operateEmailReturnH5.do")
    public String operateEmailReturnH5(HttpServletRequest request, HttpServletResponse response) {
        try {
            String state = request.getParameter("state");
            String authChannel = request.getParameter("authChannel");
            String pageUrl = ResourceBundle.getBundle("config").getString("page_url");
            String pageProjectName = ResourceBundle.getBundle("config").getString("page_project_name");// 项目名
            String userId = request.getParameter("userId");
            String order_id = request.getParameter("outUniqueId");
            logger.info(" 出参~~~~~~~" + state + "  authChannel: " + authChannel + "  pageUrl: " + pageUrl
                    + " pageProjectName:" + pageProjectName + " userId: " + userId + " order_id: " + order_id);
            if (CommUtils.isNull(userId) || CommUtils.isNull(order_id) || "null".equals(order_id)) {
                logger.info("operateReturnH5===>>userId:" + userId + ",order_id:" + order_id);
                request.setAttribute("msg", "您的邮箱认证失败");
				return "auth_fail_common";
            }
            if ("login".equals(state) || "report".equals(state)) {
                // 修改认证状态
                BwBorrower borrower = new BwBorrower();
                borrower.setId(Long.parseLong(userId));
                borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
                borrower.setAuthStep(5);
                borrower.setUpdateTime(new Date());
                int bNum = bwBorrowerService.updateBwBorrower(borrower);
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 修改结果：" + bNum);
                if (bNum < 0) {
                	request.setAttribute("msg", "您的邮箱认证失败");
    				return "auth_fail_common";
                }

                // 添加运营商认证记录
                BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(order_id), 7);
                if (CommUtils.isNull(bwOrderAuth)) {
                    bwOrderAuth = new BwOrderAuth();
                    bwOrderAuth.setCreateTime(new Date());
                    bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
                    bwOrderAuth.setOrderId(Long.parseLong(order_id));
                    bwOrderAuth.setAuth_type(7);
                    bwOrderAuth.setUpdateTime(new Date());
                    bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
                } else {
                    // logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~融360修改认证运营商认证记录：order_id：" + order_id);
                    bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
                    bwOrderAuth.setUpdateTime(new Date());
                    bwOrderAuthService.updateBwOrderAuth(bwOrderAuth);
                }

                if ("4".equals(authChannel)) {
                    return "redirect:" + pageUrl + pageProjectName + "/html/CreditCertification/index.html";
                } else {
                	request.setAttribute("msg", "您的邮箱认证成功");
    				return "auth_success_common";
                }

            } else {
                if ("4".equals(authChannel)) {
                    return "redirect:" + pageUrl + pageProjectName + "/html/CreditCertification/failure.html?category=3";
                } else {
                	request.setAttribute("msg", "您的邮箱认证失败");
    				return "auth_fail_common";
                }
            }
        } catch (Exception e) {
            logger.error("异常:", e);
            request.setAttribute("msg", "您的邮箱认证失败");
			return "auth_fail_common";
        }

    }
}
