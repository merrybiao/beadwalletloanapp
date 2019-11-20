package com.waterelephant.linkface.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.faceID.utils.DateUtils;
import com.waterelephant.faceID.utils.FaceIDConstant;
import com.waterelephant.faceID.utils.GetImageUtils;
import com.waterelephant.faceID.utils.YunOSSUtil;
import com.waterelephant.linkface.entity.ResultInfo;
import com.waterelephant.linkface.service.LinkfaceService;
import com.waterelephant.linkface.utils.LinkfaceConstant;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.DateUtil;

/**
 * 商汤人脸识别（code0088）
 * 
 * 
 * Module:
 * 
 * LinkfaceController.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <商汤人脸识别>
 */
@Controller
public class LinkfaceController {
	private Logger logger = LoggerFactory.getLogger(LinkfaceController.class);
	private String fileUrl = LinkfaceConstant.FILEURL; // 本地临时文件储存路径

	@Autowired
	LinkfaceService linkfaceService;

	/**
	 * 商汤人脸识别 - 身份证 - 正面 - App
	 * 
	 * @param request
	 * @param response
	 * @return AppResponseResult
	 */
	@RequestMapping("/app/linkface/appCheckLogin/ocrIdcardFrontApp.do")
	@ResponseBody
	public AppResponseResult ocrIdcardFrontApp(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始LinkfaceController的ocrIdcardFrontApp()方法");
		try {
			// 第一步：获取参数
			String ocrIDCard_front_image_path = request.getParameter("ocrIDCard_front_image_path"); // 身份证正面
			String verifyIDCard_image_path = request.getParameter("verifyIDCard_image_path"); // 身份证正面头像（待定）
			String user_id = request.getParameter("user_id"); // 用户ID
			String orderId = request.getParameter("orderId"); // 工单ID
			String verifySource = request.getParameter("verifySource"); // 认证来源

			// 第二步：验证参数
			if (StringUtils.isEmpty(ocrIDCard_front_image_path) == true) {
				result.setCode("103");
				result.setMsg("身份证正面图片为空");
				logger.info(sessionId + "：结束app的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(user_id) == true) {
				result.setCode("103");
				result.setMsg("用户编号为空");
				logger.info(sessionId + "：结束app的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(orderId) == true) {
				result.setCode("103");
				result.setMsg("工单编号为空");
				logger.info(sessionId + "：结束app的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(verifySource) == true) {
				result.setCode("103");
				result.setMsg("认证来源为空");
				logger.info(sessionId + "：结束app的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (LinkfaceConstant.VERIFYSOURCE_LINKFACE.equals(LinkfaceConstant.VERIFYSOURCE_CURRENT) == false) {
				result.setCode(LinkfaceConstant.getCurrentVerifyCode());
				result.setMsg("网络运营商故障，导致通信失败，请重新提交");
				logger.info(sessionId + "：结束app的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			// 第三步：执行请求
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("ocrIDCard_front_image_path", ocrIDCard_front_image_path);
			paramMap.put("verifyIDCard_image_path", verifyIDCard_image_path);
			paramMap.put("user_id", user_id);
			paramMap.put("orderId", orderId);
			result = linkfaceService.saveOcrIDCardFront(sessionId, paramMap);
		} catch (Exception e) {
			logger.error(sessionId + "：执行app的ocrIdcardFrontApp()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束app的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商汤人脸识别 - 身份证 - 反面
	 * 
	 * @param request
	 * @param response
	 * @return AppResponseResult
	 */
	@RequestMapping("/app/linkface/appCheckLogin/ocrIDCardBackApp.do")
	@ResponseBody
	public AppResponseResult ocrIDCardBackApp(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始LinkfaceController的ocrIDCardBackApp()方法");
		try {
			// 第一步：获取参数
			String ocrIDCardBackPath = request.getParameter("ocrIDCard_back_image_path"); // 身份证反面
			String user_id = request.getParameter("user_id"); // 用户ID
			String orderId = request.getParameter("orderId"); // 工单ID
			String verifySource = request.getParameter("verifySource"); // 认证来源

			// 第二步：验证参数
			if (StringUtils.isEmpty(ocrIDCardBackPath) == true) {
				result.setCode("103");
				result.setMsg("身份证反面图片为空");
				logger.info(sessionId + "：结束app的ocrIDCardBackApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(user_id) == true) {
				result.setCode("103");
				result.setMsg("用户编号为空");
				logger.info(sessionId + "：结束app的ocrIDCardBackApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(orderId) == true) {
				result.setCode("103");
				result.setMsg("工单编号为空");
				logger.info(sessionId + "：结束app的ocrIDCardBackApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(verifySource) == true) {
				result.setCode("103");
				result.setMsg("认证来源为空");
				logger.info(sessionId + "：结束app的ocrIDCardBackApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (LinkfaceConstant.VERIFYSOURCE_LINKFACE.equals(LinkfaceConstant.VERIFYSOURCE_CURRENT) == false) {
				result.setCode(LinkfaceConstant.getCurrentVerifyCode());
				result.setMsg("网络运营商故障，导致通信失败，请重新提交");
				logger.info(sessionId + "：结束app的ocrIDCardBackApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			// 第三步：执行请求
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("ocrIDCardBackPath", ocrIDCardBackPath);
			paramMap.put("user_id", user_id);
			paramMap.put("orderId", orderId);
			result = linkfaceService.saveOcrIDCardBack(sessionId, paramMap);
		} catch (Exception e) {
			logger.error(sessionId + "：执行app的ocrIDCardBackApp()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束app的ocrIDCardBackApp()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商汤人脸识别 - 身份证 - 保存身份证信息 - APP
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/linkface/appCheckLogin/saveIDCardInfoApp.do")
	@ResponseBody
	public AppResponseResult saveIDCardInfoApp(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始app的saveIDCardInfoApp()方法");
		try {
			// 第一步：获取参数
			String orderId = request.getParameter("orderId"); // 工单ID
			String borrowId = request.getParameter("bId"); // 用户ID
			String authChannel = request.getParameter("authChannel"); // 渠道来源
			String sfzzmUrl = request.getParameter("sfzzmUrl"); // 身份证正面地址
			String sfzfmUrl = request.getParameter("sfzfmUrl"); // 身份证反面地址
			String czzUrl = request.getParameter("czzUrl"); // 活体图片地址
			String verifySource = request.getParameter("verifySource"); // 认证来源(0:人工审核，1：face++,2:商汤)
			String adjunctDesc = request.getParameter("adjunctDesc"); // 身份证信息
			String photoState = request.getParameter("photoState"); // 照片类型，活体还是手持
			String delta = request.getParameter("delta"); // 活体校验所需的校验码

			// 第二步：验证参数
			if (StringUtils.isEmpty(orderId) == true) {
				result.setCode("103");
				result.setMsg("工单编号为空");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(borrowId) == true) {
				result.setCode("103");
				result.setMsg("用户编号为空");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(authChannel) == true) {
				result.setCode("103");
				result.setMsg("渠道来源为空");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(sfzzmUrl) == true) {
				result.setCode("103");
				result.setMsg("身份证正面地址为空");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(sfzfmUrl) == true) {
				result.setCode("103");
				result.setMsg("身份证反面地址为空");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(czzUrl) == true) {
				result.setCode("103");
				result.setMsg("活体图片地址为空");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (LinkfaceConstant.VERIFYSOURCE_LINKFACE.equals(LinkfaceConstant.VERIFYSOURCE_CURRENT) == false) {
				result.setCode(LinkfaceConstant.getCurrentVerifyCode());
				result.setMsg("网络运营商故障，导致通信失败，请重新提交");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(adjunctDesc) == true || adjunctDesc.split("/") == null || adjunctDesc.split("/").length != 2) {
				result.setCode("103");
				result.setMsg("身份证信息为空");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(photoState) == true) {
				photoState = "1";
			}
			if (StringUtils.isEmpty(delta) == true) {
				result.setCode("103");
				result.setMsg("活体校验码为空");
				logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			// 第三步：执行请求
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("orderId", orderId); // 工单ID
			paramMap.put("borrowId", borrowId); // 用户ID
			paramMap.put("authChannel", authChannel); // 渠道来源
			paramMap.put("sfzzmUrl", sfzzmUrl); // 身份证正面地址
			paramMap.put("sfzfmUrl", sfzfmUrl); // 身份证反面地址
			paramMap.put("czzUrl", czzUrl); // 活体图片地址
			paramMap.put("verifySource", verifySource); // 认证来源
			paramMap.put("adjunctDesc", adjunctDesc); // 身份证信息
			paramMap.put("photoState", photoState); // 照片类型，活体还是手持
			paramMap.put("delta", delta); // 活体校验所需的校验码

			result = linkfaceService.saveIDCardInfoApp(sessionId, paramMap);
		} catch (Exception e) {
			logger.error(sessionId + "：执行app的saveIDCardInfoApp()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info(sessionId + "：结束app的saveIDCardInfoApp()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商汤人脸识别 - 身份证 - 正面 - H5
	 * 
	 * @param request
	 * @param response
	 * @return AppResponseResult
	 */
	@RequestMapping("/app/linkface/appCheckLogin/ocrIdcardFrontH5.do")
	@ResponseBody
	public AppResponseResult ocrIdcardFrontH5(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始H5的ocrIdcardFrontApp()方法");
		String imagePath = null;
		try {
			// 第一步：获取参数
			String ocrIDCard_front_image_path = request.getParameter("ocrIDCard_front_image_path"); // 身份证正面
			// String verifyIDCard_image_path =
			// request.getParameter("verifyIDCard_image_path"); // 身份证正面头像（待定）
			String user_id = request.getParameter("borrowerId"); // 用户ID
			String orderId = request.getParameter("orderId"); // 工单ID
			String verifySource = request.getParameter("verifySource"); // 认证来源
			String orderNo = request.getParameter("orderNo"); // 工单编号

			// 第二步：验证参数
			if (StringUtils.isEmpty(ocrIDCard_front_image_path) == true) {
				result.setCode("103");
				result.setMsg("身份证正面图片为空");
				logger.info(sessionId + "：结束H5的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(user_id) == true) {
				result.setCode("103");
				result.setMsg("用户编号为空");
				logger.info(sessionId + "：结束H5的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(orderId) == true) {
				result.setCode("103");
				result.setMsg("工单编号为空");
				logger.info(sessionId + "：结束H5的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(verifySource) == true) {
				result.setCode("103");
				result.setMsg("认证来源为空");
				logger.info(sessionId + "：结束H5的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (LinkfaceConstant.VERIFYSOURCE_LINKFACE.equals(LinkfaceConstant.VERIFYSOURCE_CURRENT) == false) {
				result.setCode(LinkfaceConstant.getCurrentVerifyCode());
				result.setMsg("网络运营商故障，导致通信失败，请重新提交");
				logger.info(sessionId + "：结束H5的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			// 第三步：base64图片保存
			imagePath = fileUrl + "/" + orderNo + "_front.jpg";
			String fileNameFront = orderNo + "_front.jpg";
			boolean front1 = GetImageUtils.base64SaveImage(ocrIDCard_front_image_path, fileUrl, fileNameFront);
			if (front1 == false) {
				result.setCode("104");
				result.setMsg("身份证正面照片保存失败");
				logger.info(sessionId + "：结束H5的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			String fileName = orderNo + "_01.jpg";
			String diskName = "upload/backend/" + DateUtil.getCurrentDateString(DateUtil.yyyy_MM_dd) + "/" + fileName;
			boolean front2 = YunOSSUtil.uploadObject2OSS(imagePath, FaceIDConstant.BACKETNAME, diskName);
			if (front2 == false) {
				result.setCode("104");
				result.setMsg("身份证正面照片保存失败");
				logger.info(sessionId + "：结束H5的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			// 第四步：执行请求
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("ocrIDCard_front_image_path", diskName);
			// paramMap.put("verifyIDCard_image_path", verifyIDCard_image_path);
			paramMap.put("user_id", user_id);
			paramMap.put("orderId", orderId);

			// 第五步：返回
			result = linkfaceService.saveOcrIDCardFront(sessionId, paramMap);
			Object object = result.getResult();
			if (object != null) {
				ResultInfo resultInfo = (ResultInfo) object;
				resultInfo.setSfzzm(diskName);
				result.setResult(resultInfo);
			}
		} catch (Exception e) {
			logger.error(sessionId + "：执行H5的ocrIdcardFrontApp()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		} finally {
			if (imagePath != null) {
				GetImageUtils.delImage(imagePath); // 删除缓存的图片
			}
		}
		logger.info(sessionId + "：结束H5的ocrIdcardFrontApp()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商汤人脸识别 - 身份证 - 反面 - H5
	 * 
	 * @param request
	 * @param response
	 * @return AppResponseResult
	 */
	@RequestMapping("/app/linkface/appCheckLogin/ocrIDCardBackH5.do")
	@ResponseBody
	public AppResponseResult ocrIDCardBackH5(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始H5的ocrIDCardBackH5()方法");
		String imagePath = null;
		try {
			// 第一步：获取参数
			String ocrIDCardBackPath = request.getParameter("ocrIDCard_back_image_path"); // 身份证反面
			String user_id = request.getParameter("borrowerId"); // 用户ID
			String orderId = request.getParameter("orderId"); // 工单ID
			String verifySource = request.getParameter("verifySource"); // 认证来源
			String orderNo = request.getParameter("orderNo"); // 工单编号

			// 第二步：验证参数
			if (StringUtils.isEmpty(ocrIDCardBackPath) == true) {
				result.setCode("103");
				result.setMsg("身份证反面图片为空");
				logger.info(sessionId + "：结束H5的ocrIDCardBackH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(user_id) == true) {
				result.setCode("103");
				result.setMsg("用户编号为空");
				logger.info(sessionId + "：结束H5的ocrIDCardBackH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(orderId) == true) {
				result.setCode("103");
				result.setMsg("工单编号为空");
				logger.info(sessionId + "：结束H5的ocrIDCardBackH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(verifySource) == true) {
				result.setCode("103");
				result.setMsg("认证来源为空");
				logger.info(sessionId + "：结束H5的ocrIDCardBackH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (LinkfaceConstant.VERIFYSOURCE_LINKFACE.equals(LinkfaceConstant.VERIFYSOURCE_CURRENT) == false) {
				result.setCode(LinkfaceConstant.getCurrentVerifyCode());
				result.setMsg("网络运营商故障，导致通信失败，请重新提交");
				logger.info(sessionId + "：结束H5的ocrIDCardBackH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			// 第三步：base64图片保存
			imagePath = fileUrl + "/" + orderNo + "_back.jpg";
			String fileNameFront = orderNo + "_back.jpg";
			boolean front1 = GetImageUtils.base64SaveImage(ocrIDCardBackPath, fileUrl, fileNameFront);

			if (front1 == false) {
				result.setCode("104");
				result.setMsg("身份证反面照片保存失败");
				logger.info(sessionId + "：结束H5的ocrIDCardBackH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			String fileName = orderNo + "_02.jpg";
			String diskName = "upload/backend/" + DateUtil.getCurrentDateString(DateUtil.yyyy_MM_dd) + "/" + fileName;
			boolean front2 = YunOSSUtil.uploadObject2OSS(imagePath, FaceIDConstant.BACKETNAME, diskName);
			if (front2 == false) {
				result.setCode("104");
				result.setMsg("身份证反面照片保存失败");
				logger.info(sessionId + "：结束H5的ocrIDCardBackH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			// 第四步：执行请求
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("ocrIDCardBackPath", diskName);
			paramMap.put("user_id", user_id);
			paramMap.put("orderId", orderId);

			// 第五步：返回
			result = linkfaceService.saveOcrIDCardBack(sessionId, paramMap);
			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setSfzfm(diskName);
			result.setResult(resultInfo);
		} catch (Exception e) {
			logger.error(sessionId + "：执行H5的ocrIDCardBackH5()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		} finally {
			if (imagePath != null) {
				GetImageUtils.delImage(imagePath); // 删除缓存的图片
			}
		}
		logger.info(sessionId + "：结束H5的ocrIDCardBackH5()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商汤人脸识别 - 身份证 - 保存身份证信息 - H5
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/linkface/appCheckLogin/saveIDCardInfoH5.do")
	@ResponseBody
	public AppResponseResult saveIDCardInfoH5(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始H5的saveIDCardInfoH5()方法");
		String imagePath = null;
		try {
			// 第一步：获取参数
			String orderId = request.getParameter("orderId"); // 工单ID
			// String orderNumber = request.getParameter("orderNo"); // 工单编号
			String borrowerId = request.getParameter("borrowerId"); // 用户ID
			String authChannel = request.getParameter("authChannel"); // 渠道来源
			String czzUrl = request.getParameter("imageStr"); // 活体图片地址
			String verifySource = request.getParameter("verifySource"); // 认证来源(0:人工审核，1：face++,2:商汤)
			String adjunctDesc = request.getParameter("adjunctDesc"); // 身份证信息
			String photoState = request.getParameter("photoState"); // 照片类型，活体还是手持(0:活体，1：手持)
			String orderNo = request.getParameter("orderNo"); // 工单编号

			// 第二步：验证参数
			if (StringUtils.isEmpty(orderId) == true) {
				result.setCode("103");
				result.setMsg("工单编号为空");
				logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(borrowerId) == true) {
				result.setCode("103");
				result.setMsg("用户编号为空");
				logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(authChannel) == true) {
				result.setCode("103");
				result.setMsg("渠道来源为空");
				logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(czzUrl) == true) {
				result.setCode("103");
				result.setMsg("活体图片地址为空");
				logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (LinkfaceConstant.VERIFYSOURCE_LINKFACE.equals(LinkfaceConstant.VERIFYSOURCE_CURRENT) == false) {
				result.setCode(LinkfaceConstant.getCurrentVerifyCode());
				result.setMsg("网络运营商故障，导致通信失败，请重新提交");
				logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(adjunctDesc) == true || adjunctDesc.split("/") == null || adjunctDesc.split("/").length != 2) {
				result.setCode("103");
				result.setMsg("身份证信息为空");
				logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(photoState) == true) {
				photoState = "0";
			}

			// 第三步：保存图片到阿里云服务器
			imagePath = fileUrl + "/" + orderNo + "_hander.jpg";
			String fileNameFront = orderNo + "_hander.jpg";
			boolean front1 = GetImageUtils.base64SaveImage(czzUrl, fileUrl, fileNameFront);
			if (front1 == false) {
				result.setCode("104");
				result.setMsg("手持身份证照片保存失败");
				logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}
			String fileName = orderNo + "_03.jpg";
			String diskName = "upload/backend/" + DateUtil.getCurrentDateString(DateUtil.yyyy_MM_dd) + "/" + fileName;
			boolean front2 = YunOSSUtil.uploadObject2OSS(imagePath, FaceIDConstant.BACKETNAME, diskName);
			if (front2 == false) {
				result.setCode("104");
				result.setMsg("手持身份证照片保存失败");
				logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
				return result;
			}

			// 第三步：执行请求
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("orderId", orderId); // 工单ID
			paramMap.put("borrowId", borrowerId); // 用户ID
			paramMap.put("authChannel", authChannel); // 渠道来源
			// paramMap.put("sfzzmUrl", ocrIDCard_front_image_path); // 身份证正面地址
			// paramMap.put("sfzfmUrl", sfzfmUrl); // 身份证反面地址
			paramMap.put("czzUrl", diskName); // 手持身份证图片地址
			paramMap.put("verifySource", verifySource); // 认证来源
			paramMap.put("adjunctDesc", adjunctDesc); // 身份证信息
			paramMap.put("photoState", photoState); // 照片类型，活体还是手持

			result = linkfaceService.saveIDCardInfoH5(sessionId, paramMap);
		} catch (Exception e) {
			logger.error(sessionId + "：执行H5的saveIDCardInfoH5()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		} finally {
			if (imagePath != null) {
				GetImageUtils.delImage(imagePath); // 删除缓存的图片
			}
		}
		logger.info(sessionId + "：结束H5的saveIDCardInfoH5()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 渠道切换
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/linkface/switchVerifySource.do")
	@ResponseBody
	public AppResponseResult switchChannel(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + "：开始switchVerifySource方法");
		try {
			// 第一步：取参
			String beforeSwitch = request.getParameter("beforeSwitch");
			String afterSwitch = request.getParameter("afterSwitch");

			List<String> sourceList = new ArrayList<>();
			sourceList.add("0");
			sourceList.add("1");
			sourceList.add("2");
			// 第二步：验证参数
			if (sourceList.contains(beforeSwitch) == false) {
				result.setCode("000");
				result.setMsg("beforeSwitch参数值有误，取值{0,1,2}");
				return result;
			}
			if (sourceList.contains(afterSwitch) == false) {
				result.setCode("000");
				result.setMsg("afterSwitch参数值有误，取值{0,1,2}");
				return result;
			}
			if (LinkfaceConstant.VERIFYSOURCE_CURRENT.equals(beforeSwitch) == false) {
				result.setCode("000");
				result.setMsg("当前使用的是" + LinkfaceConstant.VERIFYSOURCE_CURRENT + "，并非" + beforeSwitch + "，请核查");
				return result;
			}

			// 第三步：修改配置文件
			System.out.println("开始");
			String path = request.getSession().getServletContext().getRealPath("");
			String file = path + "WEB-INF/classes/linkface.properties";
			Properties prop = new Properties();
			prop.load(new FileInputStream(file));
			prop.setProperty("linkface.verifySource_current", afterSwitch);

			FileOutputStream fos = new FileOutputStream(file);
			prop.store(fos, null);
			fos.close();

			System.out.println(prop.getProperty("linkface.verifySource_current"));
			System.out.println("结束");

			// 第四步：修改当前认证来源
			String frontSource = LinkfaceConstant.VERIFYSOURCE_CURRENT;
			LinkfaceConstant.VERIFYSOURCE_CURRENT = afterSwitch;
			String backSource = LinkfaceConstant.VERIFYSOURCE_CURRENT;

			// 第五步：返回
			result.setCode("000");
			result.setMsg("切换前" + frontSource + "，切换后" + backSource);
		} catch (Exception e) {
			logger.error(sessionId + "：执行switchVerifySource()方法异常", e);
			result.setCode("100");
			result.setMsg("系统异常，请稍后再试");
		}
		// logger.info(sessionId + "：结束switchVerifySource()方法，返回结果：" + JSON.toJSONString(result));
		return result;
	}
}
