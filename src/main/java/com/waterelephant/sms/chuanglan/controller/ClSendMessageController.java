package com.waterelephant.sms.chuanglan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 创蓝短信发送控制层
 * 
 * @author dengyan
 *
 */
@Controller
public class ClSendMessageController {

	private Logger logger = LoggerFactory.getLogger(ClSendMessageController.class);

	// @Autowired
	// private MgMessageInfoService mgMessageInfoService;

	@ResponseBody
	@RequestMapping("/chuanglan/getreports.do")
	public void getVoiceReports(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始执行app的ClSendMessageController的getVoiceReports()方法。");
		// try {
		// DBCollection dbCollection = MgCollectionUtil.dbCollection();
		// // 取参
		// String seqid = request.getParameter("requestid");
		// String phone = request.getParameter("called");
		// String stateStr = request.getParameter("hungupreason");
		// String stateValue = "未获取到短信状态";
		// if (StringUtils.isEmpty(stateStr) == false) {
		// int state = Integer.parseInt(stateStr);
		// switch (state) {
		// case 0:
		// stateValue = "未规定";
		// break;
		// case 16:
		// stateValue = "正常挂机";
		// break;
		// case 17:
		// stateValue = "用户忙";
		// break;
		// case 18:
		// stateValue = "无用户响应";
		// break;
		// case 19:
		// stateValue = "无用户应答";
		// break;
		// case 20:
		// stateValue = "用户缺席";
		// break;
		// case 21:
		// stateValue = "用户拒绝";
		// break;
		// case 28:
		// stateValue = "无效号码";
		// break;
		// case 34:
		// stateValue = "无可用电路";
		// break;
		// case 38:
		// stateValue = "网络失序";
		// break;
		// default:
		// stateValue = "未知原因";
		// break;
		// }
		// }
		// if ("16".equals(stateStr)) {
		// mgMessageInfoService.update(dbCollection, seqid, 1, stateValue, phone);
		// } else {
		// mgMessageInfoService.update(dbCollection, seqid, 2, stateValue, phone);
		// }
		// // logger.info("更新数据成功");
		// } catch (Exception e) {
		// logger.error("执行app的ClSendMessageController的getVoiceReports()方法异常：", e.getMessage());
		// }
	}

	@ResponseBody
	@RequestMapping("/chuanglan/getmsgreports.do")
	public void getMsgReport(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始执行app的ClSendMessageController的getMsgReport()方法。");
		// try {
		// DBCollection dbCollection = MgCollectionUtil.dbCollection();
		// String phone = request.getParameter("mobile");
		// String seqid = request.getParameter("msgid");
		// String stateValue = request.getParameter("status");
		//
		// if ("DELIVRD".equals(stateValue)) {
		// mgMessageInfoService.update(dbCollection, seqid, 1, stateValue, phone);
		// }else {
		// mgMessageInfoService.update(dbCollection, seqid, 2, stateValue, phone);
		// }
		//// logger.info("更新数据成功");
		// }catch (Exception e) {
		// logger.error("执行app的ClSendMessageController的getMsgReport()方法异常：", e.getMessage());
		// }
	}
}
