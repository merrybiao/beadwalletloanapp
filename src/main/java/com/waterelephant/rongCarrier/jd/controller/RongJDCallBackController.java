package com.waterelephant.rongCarrier.jd.controller;

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
import com.waterelephant.rongCarrier.jd.service.RongJDService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.CommUtils;

/**
 * rong360 - 京东回调控制器
 * @author dengyan
 *
 */
@Controller
public class RongJDCallBackController {

	private Logger logger = LoggerFactory.getLogger(RongJDCallBackController.class);
	
	@Autowired
	private RongJDService rongJDService;
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
	@RequestMapping("/app/rongCarrier/jdcallback.do")
	public void collectJDCallBack(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始执行app的RongJDCallBackController的collectJDCallBack（）方法！");
		// 第一步：获取回调参数
		String userId = request.getParameter("userId");
		String state = request.getParameter("state");
		String searchId = request.getParameter("search_id");
		if ("login".equals(state)) {
			logger.info("登录成功，rong360开始抓取数据！");
		}
		if("crawl".equals(state)) {
			logger.info("数据抓取中...");
			Thread task = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 抓取京东数据
                        rongJDService.getData(userId,searchId);
                    }catch (Exception e) {
                        logger.error("保存京东数据线程异常：", e);
                    }
                }
            });
			// 关闭线程池
            taskExecutor.execute(task);
		}
		if("report".equals(state)) {
			logger.info("生成报告成功，请获取报告详情");
		}
	}
	
	/**
	 * 页面重定向
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/app/rongCarrier/operateJDReturnH5.do")
    public String operateReturnH5(HttpServletRequest request, HttpServletResponse response) {
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
                request.setAttribute("msg", "您的京东认证失败");
				return "auth_fail_common";
            }

            if ("login".equals(state)) {
                // 修改认证状态
                BwBorrower borrower = new BwBorrower();
                borrower.setId(Long.parseLong(userId));
                borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
                borrower.setAuthStep(5);
                borrower.setUpdateTime(new Date());
                int bNum = bwBorrowerService.updateBwBorrower(borrower);
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 修改结果：" + bNum);
                if (bNum < 0) {
                	request.setAttribute("msg", "您的京东认证失败");
					return "auth_fail_common";
                }

                // 添加运营商认证记录
                BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(order_id), 9);
                if (CommUtils.isNull(bwOrderAuth)) {
                    bwOrderAuth = new BwOrderAuth();
                    bwOrderAuth.setCreateTime(new Date());
                    bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
                    bwOrderAuth.setOrderId(Long.parseLong(order_id));
                    bwOrderAuth.setAuth_type(9);
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
                	request.setAttribute("msg", "您的京东认证成功");
					return "auth_success_common";
                }

            } else {
                if ("4".equals(authChannel)) {
                    return "redirect:" + pageUrl + pageProjectName + "/html/CreditCertification/failure.html?category=2";
                } else {
                	request.setAttribute("msg", "您的京东认证失败");
					return "auth_fail_common";
                }
            }
        } catch (Exception e) {
            logger.error("异常:", e);
            request.setAttribute("msg", "您的京东认证失败");
			return "auth_fail_common";
        }

    }
}
