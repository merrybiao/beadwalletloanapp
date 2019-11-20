package com.waterelephant.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.service.entity.request.RongOperateReqData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entity.response.RongOperateLogin;
import com.beadwallet.service.entity.response.RongOprerateMsgCodeRsqData;
import com.beadwallet.service.entiyt.middle.BizData;
import com.beadwallet.service.entiyt.middle.Next;
import com.beadwallet.service.entiyt.middle.Param;
import com.beadwallet.service.entiyt.middle.RefresParam;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOperateService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;

import net.sf.json.JSONObject;

/**
 * 运营商认证
 *
 * @author huwei
 *
 */
@Controller
@RequestMapping("/app/operate")
public class AppBwOperateController {

	private Logger logger = Logger.getLogger(AppBwOperateController.class);

	@Autowired
	private IBwOperateService bwOperateService;

	@Autowired
	private IBwBorrowerService bwBorrowerService;

	@Autowired
	private BwOrderAuthService bwOrderAuthService;

	/**
	 * 创建任务
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	// @ResponseBody
	// @RequestMapping("/appCheckLogin/createTask.do")
	// public AppResponseResult createTask(HttpServletRequest request, HttpServletResponse response) {
	// AppResponseResult result = new AppResponseResult();
	// String mobile = request.getParameter("mobile");
	// String password = request.getParameter("password");
	// String name = request.getParameter("name");
	// String idCard = request.getParameter("idCard");
	// String bwId = request.getParameter("bwId");
	// if (CommUtils.isNull(mobile)) {
	// result.setCode("102");
	// result.setMsg("手机号为空");
	// return result;
	// }
	// if (CommUtils.isNull(password)) {
	// result.setCode("103");
	// result.setMsg("服务密码为空");
	// return result;
	// }
	// if (CommUtils.isNull(name)) {
	// result.setCode("104");
	// result.setMsg("姓名为空");
	// return result;
	// }
	// if (CommUtils.isNull(idCard)) {
	// result.setCode("105");
	// result.setMsg("身份证号为空");
	// return result;
	// }
	// if (CommUtils.isNull(bwId)) {
	// result.setCode("106");
	// result.setMsg("借款人id为空");
	// return result;
	// }
	// logger.info("借款人id：" + bwId);
	// logger.info("手机号：" + mobile);
	// logger.info("服务密码：" + password);
	// logger.info("姓名：" + name);
	// logger.info("身份证号：" + idCard);
	// // 运营商认证
	// Response<Object> res = bwOperateService.operate(mobile, password, name, idCard, bwId);
	// System.out.println(res.toString());
	// if (res.getRequestCode().equals("200")) {
	// if (CommUtils.isNull(res.getObj())) {
	// // 不需要验证码，并且任务处理成功
	// BwBorrower borrower = new BwBorrower();
	// borrower.setId(Long.parseLong(bwId));
	// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
	// // 修改认证状态为5
	// logger.info("修改认证状态为5");
	// borrower.setAuthStep(5);
	// borrower.setUpdateTime(new Date());
	// int bNum = bwBorrowerService.updateBwBorrower(borrower);
	// logger.info("修改结果：" + bNum);
	// if (bNum < 0) {
	// result.setCode("101");
	// result.setMsg("操作失败");
	// return result;
	// }
	//
	// }
	// result.setCode("000");
	// result.setMsg(res.getRequestMsg());
	// result.setResult(res.getObj());
	// return result;
	// } else {
	// result.setCode(res.getRequestCode());
	// result.setMsg(res.getRequestMsg());
	// return result;
	// }
	//
	// }

	/**
	 * 输入图片验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	// @ResponseBody
	// @RequestMapping("/appCheckLogin/inputCode.do")
	// public AppResponseResult getTaskStatus(HttpServletRequest request, HttpServletResponse response) {
	// AppResponseResult result = new AppResponseResult();
	// String taskId = request.getParameter("taskId");
	// String code = request.getParameter("code");
	// String bwId = request.getParameter("bwId");
	// if (CommUtils.isNull(code)) {
	// result.setCode("102");
	// result.setMsg("手机号为空");
	// return result;
	// }
	// if (CommUtils.isNull(taskId)) {
	// result.setCode("103");
	// result.setMsg("服务密码为空");
	// return result;
	// }
	// if (CommUtils.isNull(bwId)) {
	// result.setCode("104");
	// result.setMsg("借款人id为空");
	// return result;
	// }
	// logger.info("借款人id：" + bwId);
	// logger.info("taskId：" + taskId);
	// logger.info("验证码：" + code);
	// // 运营商认证
	// Response<Object> res = bwOperateService.inputCode(taskId, code);
	// logger.info("魔蝎接口返回结果：" + res.getRequestCode());
	// logger.info("魔蝎接口返回结果：" + res.getRequestMsg());
	// logger.info("魔蝎接口返回结果：" + res);
	// if (res.getRequestCode().equals("200")) {
	// // 修改认证状态
	// BwBorrower borrower = new BwBorrower();
	// borrower.setId(Long.parseLong(bwId));
	// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
	// logger.info("根据借款人" + bwId + "查询结果：" + borrower.getId());
	// if (!CommUtils.isNull(borrower)) {
	// logger.info("修改认证状态为5");
	// borrower.setAuthStep(5);
	// borrower.setUpdateTime(new Date());
	// int bNum = bwBorrowerService.updateBwBorrower(borrower);
	// logger.info("修改结果：" + bNum);
	// if (bNum < 0) {
	// result.setCode("102");
	// result.setMsg("操作失败");
	// return result;
	// } else {
	// result.setCode("000");
	// result.setMsg("成功");
	// }
	//
	// } else {
	// logger.info("根据" + bwId + "查询借款人信息为空");
	// result.setCode("102");
	// result.setMsg("操作失败");
	// }
	//
	// } else {
	// result.setCode("101");
	// result.setMsg(res.getRequestMsg());
	// }
	// return result;
	// }

	/**
	 * 获取登录规则
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLoginRule.do")
	public AppResponseResult getLoginRule(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		if (CommUtils.isNull(phone)) {
			result.setCode("102");
			result.setMsg("手机号为空");
			return result;
		}
		logger.info("手机号：" + phone);
		// 运营商认证
		Response<Object> res = bwOperateService.getLoginRule(phone);
		logger.info("融360接口返回结果：" + res.getRequestCode());
		logger.info("融360接口返回结果：" + res.getRequestMsg());
		if (res.getRequestCode().equals("000")) {
			result.setCode("000");
			result.setMsg("成功");
			result.setResult(res.getObj());
		} else {
			result.setCode("101");
			result.setMsg(res.getRequestMsg());
		}
		return result;
	}

	/**
	 * 登录
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/login.do")
	public AppResponseResult login(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String phone = request.getParameter("phone");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String msgCode = request.getParameter("msgCode");
			String idCard = request.getParameter("idCard");
			String picCode = request.getParameter("picCode");
			String userId = request.getParameter("userId");
			String authChannel = request.getParameter("authChannel");
			String orderId = request.getParameter("orderId");

			if (CommUtils.isNull(phone)) {
				result.setCode("102");
				result.setMsg("手机号为空");
				return result;
			}
			if (CommUtils.isNull(pwd)) {
				result.setCode("103");
				result.setMsg("运营商服务密码为空");
				return result;
			}
			if (CommUtils.isNull(userId)) {
				result.setCode("104");
				result.setMsg("借款人id为空");
				return result;
			}
			logger.info("手机号：" + phone);
			logger.info("服务密码：" + pwd);
			logger.info("借款人id：" + userId);
			// phone+pwd 登录
			RongOperateReqData rongOperateReqData = new RongOperateReqData();
			rongOperateReqData.setIdCard(idCard);
			rongOperateReqData.setMsgCode(msgCode);
			rongOperateReqData.setName(name);
			rongOperateReqData.setPhone(phone);
			rongOperateReqData.setPicCode(picCode);
			rongOperateReqData.setPwd(pwd);
			rongOperateReqData.setUserId(userId);
			Response<Object> res = bwOperateService.login(rongOperateReqData);
			logger.info("融360接口返回结果：" + res.getRequestCode());
			logger.info("融360接口返回结果：" + res.getRequestMsg());
			if (res.getRequestCode().equals("000")) {
				result.setCode("000");
				result.setMsg("成功");
				if (!CommUtils.isNull(res.getObj())) {
					result.setResult(res.getObj());
				} else {
					// 修改认证状态为5
					BwBorrower borrower = bwBorrowerService.findBwBorrowerById(Long.parseLong(userId));
					borrower.setAuthStep(5);
					borrower.setUpdateTime(new Date());
					int num = bwBorrowerService.updateBwBorrower(borrower);
					if (num < 0) {
						result.setCode("102");
						// result.setMsg(res.getRequestMsg());
						result.setMsg("修改认证状态失败");
						return result;
					}
					logger.info("authChannel=====" + authChannel + "#####orderId=====" + orderId);
					if (!CommUtils.isNull(authChannel) && !CommUtils.isNull(orderId)) {
						Date now = new Date();
						Long order_id = Long.parseLong(orderId);
						// 工单认证状态
						BwOrderAuth orderAuth = bwOrderAuthService.findBwOrderAuth(order_id, 1);
						if (orderAuth == null) {
							orderAuth = new BwOrderAuth();
							orderAuth.setAuth_type(1);
							orderAuth.setCreateTime(now);
							orderAuth.setUpdateTime(now);
							orderAuth.setOrderId(order_id);
							orderAuth.setAuth_channel(Integer.parseInt(authChannel));
							bwOrderAuthService.saveBwOrderAuth(orderAuth);
						} else {
							orderAuth.setAuth_channel(Integer.parseInt(authChannel));
							orderAuth.setUpdateTime(now);
							bwOrderAuthService.updateBwOrderAuth(orderAuth);
						}
					}
				}
			} else {
				result.setCode("101");
				result.setMsg(res.getRequestMsg());
			}

		} catch (Exception e) {
			result.setCode("101");
			result.setMsg("系统异常");
			e.printStackTrace();
			logger.error(e);
		}
		return result;
	}

	/**
	 * 刷新短信验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/refreshMsgCode.do")
	public AppResponseResult refreshMsgCode(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		String userId = request.getParameter("userId");
		String messagecodeType = request.getParameter("messagecodeType");
		if (CommUtils.isNull(phone)) {
			result.setCode("102");
			result.setMsg("手机号为空");
			return result;
		}
		logger.info("手机号：" + phone);
		logger.info("短信类型：" + messagecodeType);
		Response<Object> res = bwOperateService.refreshMsgCode(phone, messagecodeType, userId);
		logger.info("融360接口返回结果：" + res.getRequestCode());
		logger.info("融360接口返回结果：" + res.getRequestMsg());
		if (res.getRequestCode().equals("000")) {
			result.setCode("000");
			result.setMsg("成功");
		} else {
			result.setCode("101");
			result.setMsg(res.getRequestMsg());
		}
		return result;
	}

	/**
	 * 获取短信验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/getMsgCode.do")
	public AppResponseResult getMsgCode(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String idCard = request.getParameter("idCard");
		String messagecodeType = request.getParameter("messagecodeType");
		if (CommUtils.isNull(userId)) {
			result.setCode("102");
			result.setMsg("用户id为空");
			return result;
		}
		if (CommUtils.isNull(phone)) {
			result.setCode("102");
			result.setMsg("手机号为空");
			return result;
		}
		logger.info("手机号：" + phone);
		Response<RongOprerateMsgCodeRsqData> res = bwOperateService.getMsgCode(phone, name, idCard, messagecodeType);
		RongOprerateMsgCodeRsqData rongOprerateMsgCodeRsqData = res.getObj();
		BizData bizData = rongOprerateMsgCodeRsqData.getBiz_data();
		map.put("messagecode_type", bizData.getMessagecode_type());

		logger.info("融360接口返回结果：" + res.getRequestCode());
		logger.info("融360接口返回结果：" + res.getRequestMsg());
		if (res.getRequestCode().equals("000")) {
			result.setCode("000");
			result.setMsg("成功");
			result.setResult(map);
		} else {
			result.setCode("101");
			result.setMsg(res.getRequestMsg());
		}
		return result;
	}

	/**
	 * 提交短信验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/submitMsgCode.do")
	public AppResponseResult submitMsgCode(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		String msgCode = request.getParameter("msgCode");
		String idCard = request.getParameter("idCard");
		String name = request.getParameter("name");
		String userId = request.getParameter("userId");
		String picCode = request.getParameter("picCode");
		String authChannel = request.getParameter("authChannel");
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(phone)) {
			result.setCode("102");
			result.setMsg("手机号为空");
			return result;
		}
		if (CommUtils.isNull(userId)) {
			result.setCode("103");
			result.setMsg("userId为空");
			return result;
		}
		if (CommUtils.isNull(msgCode)) {
			result.setCode("104");
			result.setMsg("短信验证码为空");
			return result;
		}
		logger.info("手机号：" + phone);
		logger.info("姓名：" + name);
		// phone+pwd 登录

		Response<Object> res = bwOperateService.submitMsgCode(phone, msgCode, name, idCard, userId, picCode);
		logger.info("融360接口返回结果：" + res.getRequestCode());
		logger.info("融360接口返回结果：" + res.getRequestMsg());
		if (res.getRequestCode().equals("000")) {
			result.setCode("000");
			result.setMsg("成功");
			result.setResult(res.getObj());
			// 修改认证状态为5
			BwBorrower borrower = bwBorrowerService.findBwBorrowerById(Long.parseLong(userId));
			borrower.setAuthStep(5);
			borrower.setUpdateTime(new Date());
			int num = bwBorrowerService.updateBwBorrower(borrower);
			if (num < 0) {
				result.setCode("102");
				// result.setMsg(res.getRequestMsg());
				result.setMsg("修改认证状态失败");
				return result;
			}
			// 拉取数据
			// bwOperateService.getData(userId);
			logger.info("authChannel=====" + authChannel + "#####orderId=====" + orderId);
			if (!CommUtils.isNull(authChannel) && !CommUtils.isNull(orderId)) {
				Date now = new Date();
				Long order_id = Long.parseLong(orderId);
				// 工单认证状态
				BwOrderAuth orderAuth = bwOrderAuthService.findBwOrderAuth(order_id, 1);
				if (orderAuth == null) {
					orderAuth = new BwOrderAuth();
					orderAuth.setAuth_type(1);
					orderAuth.setCreateTime(now);
					orderAuth.setUpdateTime(now);
					orderAuth.setOrderId(order_id);
					orderAuth.setAuth_channel(Integer.parseInt(authChannel));
					bwOrderAuthService.saveBwOrderAuth(orderAuth);
				} else {
					orderAuth.setAuth_channel(Integer.parseInt(authChannel));
					orderAuth.setUpdateTime(now);
					bwOrderAuthService.updateBwOrderAuth(orderAuth);
				}
			}
		} else {
			result.setCode("101");
			result.setMsg(res.getRequestMsg());
		}
		return result;
	}

	/**
	 * 请求图片验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/getPicCode.do")
	public AppResponseResult getPicCode(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> map = new HashMap<String, Object>();

		String phone = request.getParameter("phone");
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String idCard = request.getParameter("idCard");
		String pwd = request.getParameter("pwd");
		String msgCode = request.getParameter("msgCode");
		String piccodeType = request.getParameter("piccodeType");

		if (CommUtils.isNull(phone)) {
			result.setCode("102");
			result.setMsg("手机号为空");
			return result;
		}
		if (CommUtils.isNull(userId)) {
			result.setCode("104");
			result.setMsg("借款人id为空");
			return result;
		}
		logger.info("刷新图片验证码手机号：" + phone);
		// phone+pwd 登录
		RongOperateReqData rongOperateReqData = new RongOperateReqData();
		rongOperateReqData.setIdCard(idCard);
		rongOperateReqData.setMsgCode(msgCode);
		rongOperateReqData.setName(name);
		rongOperateReqData.setPhone(phone);
		rongOperateReqData.setPwd(pwd);
		Response<RongOperateLogin> res = bwOperateService.getPicCode(rongOperateReqData, piccodeType);
		logger.info("融360接口返回结果：" + res.getRequestCode());
		logger.info("融360接口返回结果：" + res.getRequestMsg());
		logger.info("融360接口返回结果：" + res.getObj());
		if (res.getRequestCode().equals("200")) {
			RongOperateLogin rongOperateLogin = res.getObj();
			Next next = rongOperateLogin.getNext();
			List<Param> params = next.getParam();
			for (Param param : params) {
				if (param.getKey().equals("message_code")) {
					map.put("param1", "msgCode");
					map.put("status", "1");
				}
				if (param.getKey().equals("pic_code")) {
					map.put("param2", "picCode");
					map.put("picCode", param.getValue());
					map.put("status", "2");
				}
				// refreshParam
				List<RefresParam> rParams = param.getRefresh_param();
				if (!CommUtils.isNull(rParams)) {
					for (RefresParam refresParam : rParams) {
						if (refresParam.getKey().equals("piccode_type")) {
							map.put("piccodeType", refresParam.getValue());
						}
						if (refresParam.getKey().equals("messagecode_type")) {
							map.put("messagecodeType", refresParam.getValue());
						}

					}
				}

			}
			result.setMsg("成功");
			result.setResult(map);
		} else {
			result.setCode("101");
			result.setMsg(res.getRequestMsg());
		}
		return result;
	}

	/**
	 * 刷新图片验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/refreshPicCode.do")
	public AppResponseResult refreshPicCode(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		String userId = request.getParameter("userId");
		String piccodeType = request.getParameter("piccodeType");
		if (CommUtils.isNull(phone)) {
			result.setCode("102");
			result.setMsg("手机号为空");
			return result;
		}
		if (CommUtils.isNull(userId)) {
			result.setCode("104");
			result.setMsg("借款人id为空");
			return result;
		}
		logger.info("刷新图片验证码手机号：" + phone);
		logger.info("图片类型：" + piccodeType);
		// phone+pwd 登录
		Response<Object> res = bwOperateService.refreshPicCode(phone, "1", userId);
		logger.info("融360接口返回结果：" + res.getRequestCode());
		logger.info("融360接口返回结果：" + res.getRequestMsg());
		logger.info("融360接口返回结果：" + res.getObj());
		if (res.getRequestCode().equals("200")) {
			result.setCode("000");
			result.setMsg("成功");
			JSONObject objJson = JSONObject.fromObject(res.getObj());
			logger.info("图片验证码：" + objJson.get("pic_code"));
			result.setResult(objJson.get("pic_code"));
		} else {
			result.setCode("101");
			result.setMsg(res.getRequestMsg());
		}
		return result;
	}

	/**
	 * 拉取数据
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getData.do")
	public AppResponseResult getData(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String userId = request.getParameter("userId");

		if (CommUtils.isNull(userId)) {
			result.setCode("102");
			result.setMsg("借款人id为空");
			return result;
		}
		logger.info("userId：" + userId);
		Response<Object> res;
		try {
			res = bwOperateService.saveData(userId, userId);
			logger.info("融360接口返回结果：" + res.getRequestCode());
			logger.info("融360接口返回结果：" + res.getRequestMsg());
			if (res.getRequestCode().equals("000")) {
				result.setCode("000");
				result.setMsg("成功");
				result.setResult(res.getObj());
			} else {
				result.setCode("101");
				result.setMsg(res.getRequestMsg());
			}
		} catch (Exception e) {
			result.setCode("102");
			result.setMsg("时间转化异常");
		}
		return result;
	}
}
