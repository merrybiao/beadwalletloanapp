package com.waterelephant.faceID.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.entity.request.MsgReqData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.serve.BeadWalletSendMsgService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.faceID.service.FaceIDService;
import com.waterelephant.faceID.utils.DateUtils;
import com.waterelephant.faceID.utils.FaceIDConstant;
import com.waterelephant.faceID.utils.GetImageUtils;
import com.waterelephant.faceID.utils.RandomNumber;
import com.waterelephant.faceID.utils.YunOSSUtil;
import com.waterelephant.linkface.utils.LinkfaceConstant;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.RedisUtils;

/**
 * faceID控制层
 * 
 * @author dengyan
 *
 */
@Controller
public class FaceIDController {

	// 日志管理
	private Logger logger = LoggerFactory.getLogger(FaceIDController.class);
	// 图片存储目录
	private String fileUrl = FaceIDConstant.FILEURL;

	private String imageUrl = FaceIDConstant.IMAGERUL;

	@Autowired
	private FaceIDService faceIDService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private SendMessageCommonService sendMessageCommonService;

	/**
	 * 身份证正面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/app/faceID/appCheckLogin/ocrIDCardFront.do")
	public AppResponseResult ocrIDCardFront(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();

		// 获取app传过来的二进制图片
		// String ocrIDCard_front_image_path =
		// request.getParameter("ocrIDCard_front_image_path");
		// String verifyIDCard_image_path =
		// request.getParameter("verifyIDCard_image_path");
		String ocrIDCard_front_image_path = request.getParameter("ocrIDCard_front_image_path");
		String verifyIDCard_image_path = request.getParameter("verifyIDCard_image_path");
		String user_id = request.getParameter("user_id");
		String orderId = request.getParameter("orderId");
		logger.info(sessionId + ":开始FaceIDController的ocrIDCardFront方法...user_id:" + user_id + "。orderId:" + orderId);
		// 通过app传来的图片路径将图片下载下来保存到本地
		String imagePath = fileUrl + "/" + user_id + "_front.jpg";
		String fileNameFront = user_id + "_front.jpg";
		String imagePath2 = fileUrl + "/" + user_id + "_verify.jpg";
		String fileNameVerify = user_id + "_verify.jpg";
		try {
			if (StringUtils.isEmpty(ocrIDCard_front_image_path)) {
				result.setCode("103");
				result.setMsg("图片信息不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCard方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(user_id)) {
				result.setCode("103");
				result.setMsg("用户id不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCard方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			boolean bo1 = GetImageUtils.saveUrlAs(imageUrl + ocrIDCard_front_image_path, fileUrl, fileNameFront);
			boolean bo2 = GetImageUtils.saveUrlAs(imageUrl + verifyIDCard_image_path, fileUrl, fileNameVerify);
			if (!bo1 || !bo2) {
				result.setCode("208");
				result.setMsg("身份证图片本地存储失败，图片路径错误，请重新上传图片!");
				logger.info(sessionId + ":存储图片路径：" + imagePath);
				logger.info(sessionId + ":存储图片路径：" + imagePath2);
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCard方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			Map<String, String> params = new HashMap<String, String>();
			String imageStr = GetImageUtils.encodeBase64File(imagePath);
			params.put("sessionId", sessionId);
			params.put("user_id", user_id);
			params.put("imagePath", imageStr);
			result = faceIDService.ocrIDCardFront(params);
			if ("200".equals(result.getCode())) {
				Map<String, String> map = (Map<String, String>) result.getResult();
				String idcard_name = map.get("name");
				String idcard_number = map.get("id_card_number");
				Map<String, String> verifyMap = new HashMap<String, String>();
				verifyMap = verifyIDCard(sessionId, user_id, imagePath2, idcard_name, idcard_number, orderId);
				result.setCode(verifyMap.get("code"));
				if ("200".equals(result.getCode())) {
					String nameAndNumber = idcard_name + "/" + idcard_number;
					result.setCode("000");
					// 保存身份证信息
					faceIDService.saveIdCard(map);
					// 保存图片路径
					bwOrderAuthService.saveOcrIDFront(orderId, user_id, ocrIDCard_front_image_path, nameAndNumber, "1");

					Map<String, String> zxparams = new HashMap<String, String>();
					// String msg = "{\"code\" : " + result.getCode() + ",\"msg\" : " + result.getMsg() + "}"; //
					// 定义一个json类型的msg
					zxparams.put("sessionId", sessionId);
					zxparams.put("borrowerId", user_id);
					zxparams.put("orderId", orderId);
					zxparams.put("idcard_name", idcard_name);
					zxparams.put("idcard_number", idcard_number);
					zxparams.put("msg", result.getMsg());
					zxparams.put("score", CommUtils.isNull(map.get("score")) ? "0" : map.get("score"));
					zxparams.put("sourceItem", "1");
					faceIDService.saveZxBlack(zxparams);

				} else {
					result = new AppResponseResult();
					result.setCode(verifyMap.get("code"));
					result.setMsg(verifyMap.get("msg"));
				}
			}
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
		} catch (Exception e) {
			result.setCode("900");
			result.setMsg(e.getMessage());
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
			logger.error("执行APP的FaceIDController的ocrIDCard方法，异常", e);

		}

		// 返回数据给app
		logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCard方法，出参" + JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 身份证反面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/faceID/appCheckLogin/ocrIDCardBack.do")
	public AppResponseResult ocrIDCardBack(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String sessionId = DateUtils.getDateHMToString();
		logger.info(sessionId + ":开始FaceIDController的ocrIDCardBack方法...");
		// 获取app传过来的二进制图片
		// String ocrIDCardBackPath =
		// request.getParameter("ocrIDCard_back_image_path");
		String ocrIDCardBackPath = request.getParameter("ocrIDCard_back_image_path");
		String user_id = request.getParameter("user_id");
		String orderId = request.getParameter("orderId");
		String imagePath = fileUrl + "/" + user_id + "_back.jpg";
		String fileNameBack = user_id + "_back.jpg";
		try {
			// 判断身份证路径是否为空
			if (StringUtils.isEmpty(ocrIDCardBackPath)) {
				result.setCode("103");
				result.setMsg("图片信息不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardBack方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			// boolean bo = Base64ImageConverter.saveUrlAs(sessionId,
			// ocrIDCardBackPath, fileUrl, fileNameBack);
			boolean bo = GetImageUtils.saveUrlAs(imageUrl + ocrIDCardBackPath, fileUrl, fileNameBack);
			// 判断图片是否存储成功
			if (bo == false) {
				result.setCode("208");
				result.setMsg("身份证图片本地存储失败，图片路径错误，请重新上传图片!");
				logger.info(sessionId + ":存储图片路径：" + imagePath);
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardBack方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			// 将图片转成base64
			String imageStr = GetImageUtils.encodeBase64File(imagePath);
			Map<String, String> params = new HashMap<String, String>();
			params.put("sessionId", sessionId);
			params.put("imagePath", imageStr);
			params.put("user_id", user_id);
			result = faceIDService.ocrIDCardBack(params);
			// 如果code为200则返回前台code为000并保存身份证信息到数据库
			if ("200".equals(result.getCode())) {
				result.setCode("000");
				Map<String, String> resultMap = (Map<String, String>) result.getResult();
				// 保存身份证信息
				faceIDService.saveIdCard(resultMap);
				// 保存图片信息
				bwOrderAuthService.saveOcrIDBack(orderId, user_id, ocrIDCardBackPath, "1");

				// TODO
				Map<String, String> zxparams = new HashMap<String, String>();
				// String msg = "{\"code\" : " + result.getCode() + ",\"msg\" : " + result.getMsg() + "}"; //
				// 定义一个json类型的msg
				zxparams.put("sessionId", sessionId);
				zxparams.put("borrowerId", user_id);
				zxparams.put("orderId", orderId);
				// zxparams.put("idcard_name", idcard_name);
				// zxparams.put("idcard_number", idcard_number);
				zxparams.put("msg", result.getMsg());
				zxparams.put("score", CommUtils.isNull(resultMap.get("score")) ? "0" : resultMap.get("score"));
				zxparams.put("sourceItem", "2");
				faceIDService.saveZxBlack(zxparams);
			}
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
			// 将接收到的参数保存到AppResult中
		} catch (Exception e) {
			result.setCode("900");
			result.setMsg(e.getMessage());
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
			logger.error(sessionId + ":执行APP的FaceIDController的ocrIDCard方法，异常", e);

		}

		// 返回数据给app
		logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardBack方法，出参" + JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 身份核验
	 * @param sessionId
	 * @param user_id
	 * @param verifyIDCard_image_path
	 * @param idcard_name
	 * @param idcard_number
	 * @param orderId
	 * @return
	 */
	private Map<String, String> verifyIDCard(String sessionId, String user_id, String verifyIDCard_image_path,
			String idcard_name, String idcard_number, String orderId) {
		logger.info(sessionId + ":开始FaceIDController的verifyIDCard方法...");

		Map<String, String> map = new HashMap<String, String>();
		try {
			// 判断用户id是否为空
			if (StringUtils.isEmpty(user_id)) {
				map.put("code", "103");
				map.put("msg", "获取信息失败,用户id不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的verifyIDCard方法，出参" + JSONObject.toJSONString(map));

				return map;
			}
			// 判断用户姓名是否为空
			if (StringUtils.isEmpty(idcard_name)) {
				map.put("code", "103");
				map.put("msg", "获取图片信息失败,姓名信息不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的verifyIDCard方法，出参" + JSONObject.toJSONString(map));

				return map;
			}
			// 判断用户身份证号码是否为空
			if (StringUtils.isEmpty(idcard_number)) {
				map.put("code", "103");
				map.put("msg", "获取图片信息失败,身份证号码不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的verifyIDCard方法，出参" + JSONObject.toJSONString(map));

				return map;
			}
			logger.info(sessionId + ":获取图片成功！");
			String imageStr = GetImageUtils.encodeBase64File(verifyIDCard_image_path);
			// 将请求参数存入到Map<String, String>中
			Map<String, String> params = new HashMap<String, String>();
			params.put("user_id", user_id);
			params.put("orderId", orderId);
			params.put("sessionId", sessionId);
			params.put("imagePath", imageStr);
			params.put("idcard_name", idcard_name);
			params.put("idcard_number", idcard_number);
			// 请求Service服务器
			map = faceIDService.saveVerifyIDCard(params);
			GetImageUtils.delImage(verifyIDCard_image_path);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "900");
			map.put("msg", e.getMessage());
			// 删除缓存的图片
			GetImageUtils.delImage(verifyIDCard_image_path);
			logger.error(sessionId + ":执行APP的FaceIDController的verifyIDCard（）方法，异常", e);
		}
		// 返回数据给app
		logger.info(sessionId + ":结束APP的FaceIDControlle的verifyIDCard方法，出参" + JSONObject.toJSONString(map));
		return map;
	}

	// /**
	// * 活体认证
	// * @param request
	// * @param response
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping("/app/faceID/appCheckLogin/verifyFace.do")
	// public AppResponseResult verifyFace(HttpServletRequest request,
	// HttpServletResponse response) {
	// AppResponseResult result = new AppResponseResult();
	// String sessionId = DateUtils.getDateHMToString();
	// logger.info(sessionId + ":开始FaceIDController的verifyFace方法...");
	// logger.info(sessionId + ":开始获取app传过来的信息...");
	// //获取app传过来参数
	// String user_id = request.getParameter("user_id");
	// String verifyFacePath = request.getParameter("image_path");//图片二进制字符串
	// String idcard_name = request.getParameter("idcard_name");// 身份证姓名
	// String idcard_number = request.getParameter("idcard_number"); // 身份证号
	// String delta = request.getParameter("delta");
	// String imagePath = fileUrl + "/" + user_id + "_verifyFace.jpg";
	// String fileNameVerifyFace = user_id + "_verifyFace.jpg";
	// try {
	// if (StringUtils.isNullOrEmpty(imagePath)) {
	// result.setCode("103");
	// result.setMsg("获取图片信息失败,姓名信息不能为空！");
	// logger.info(sessionId + ":结束APP的FaceIDControlle的verifyFace方法，出参" +
	// JSONObject.toJSONString(result));
	//
	// return result;
	// }
	// if (StringUtils.isNullOrEmpty(idcard_name)) {
	// result.setCode("103");
	// result.setMsg("获取图片信息失败,姓名不能为空！");
	// logger.info(sessionId + ":结束APP的FaceIDControlle的verifyFace方法，出参" +
	// JSONObject.toJSONString(result));
	//
	// return result;
	// }
	// if (StringUtils.isNullOrEmpty(idcard_number)) {
	// result.setCode("103");
	// result.setMsg("获取图片信息失败,身份证号码不能为空！");
	// logger.info(sessionId + ":结束APP的FaceIDControlle的verifyFace方法，出参" +
	// JSONObject.toJSONString(result));
	//
	// return result;
	// }
	// if (StringUtils.isNullOrEmpty(delta)) {
	// result.setCode("103");
	// result.setMsg("获取图片信息失败,校验字符串不能为空！");
	// logger.info(sessionId + ":结束APP的FaceIDControlle的verifyIDCard方法，出参" +
	// JSONObject.toJSONString(result));
	//
	// return result;
	// }
	// boolean bo = Base64ImageConverter.saveUrlAs(sessionId, verifyFacePath,
	// fileUrl, fileNameVerifyFace);
	// if (bo == false) {
	// result.setCode("208");
	// result.setMsg("图片获取失败，请重新上传！");
	// logger.info(sessionId + ":存储图片路径：" + imagePath);
	// logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCard方法，出参" +
	// JSONObject.toJSONString(result));
	// return result;
	// }
	//
	// logger.info(sessionId + ":获取活体信息成功！");
	// //将请求参数存入Map<String,String>集合中
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("user_id", user_id);
	// params.put("sessionId", sessionId);
	// params.put("imagePath", imagePath);
	// params.put("idcard_name", idcard_name);
	// params.put("idcard_number", idcard_number);
	// //请求Service系统
	// result = faceIDService.verifyFace(params,delta);
	// if ("200".equals(result.getCode())) {
	// result.setCode("000");
	// }
	// GetImageUtils.delImage(imagePath);
	// //将返回信息保存大AppResult中
	// } catch (Exception e) {
	// e.printStackTrace();
	// result.setCode("900");
	// result.setMsg(e.getMessage());
	// //删除缓存的图片
	// GetImageUtils.delImage(imagePath);
	// logger.info(sessionId + ":执行APP的FaceIDController的verifyFace方法，异常：" +
	// e.getMessage());
	// }
	// //返回数据给app
	// logger.info(sessionId + ":结束APP的FaceIDControlle的verifyFace方法，出参" +
	// JSONObject.toJSONString(result));
	// return result;
	// }

	/**
	 * 活体验证
	 * 
	 * @param borrowId
	 * @param czzUrl
	 * @param adjunctDesc
	 * @param delta
	 * @return
	 */
	public String verifyFace(String borrowId, String orderId, String czzUrl, String adjunctDesc, String delta) {
		AppResponseResult result = new AppResponseResult();
		String sessionId = DateUtils.getDateHMToString();
		logger.info(sessionId + ":开始FaceIDController的verifyFace方法...");
		String imagePath = fileUrl + "/" + borrowId + "_verifyFace.jpg";
		try {
			String[] str = adjunctDesc.split("/");
			String idcard_name = str[0];
			String idcard_number = str[1];
			String verifyFacePath = imageUrl + czzUrl;
			String fileName = borrowId + "_verifyFace.jpg";
			boolean bo = GetImageUtils.saveUrlAs(verifyFacePath, fileUrl, fileName);
			if (bo == false) {
				result.setCode("208");
				result.setMsg("图片获取失败，请重新上传！");
				logger.info(sessionId + ":存储图片路径：" + imagePath);
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCard方法，出参" + JSONObject.toJSONString(result));
				return JsonUtils.toJson(result);
			}

			logger.info(sessionId + ":获取活体信息成功！");
			// 图片转成base64
			String imageStr = GetImageUtils.encodeBase64File(imagePath);
			// 将请求参数存入Map<String,String>集合中
			Map<String, String> params = new HashMap<String, String>();
			params.put("user_id", borrowId);
			params.put("orderId", orderId);
			params.put("idcard_name", idcard_name);
			params.put("idcard_number", idcard_number);
			params.put("imagePath", imageStr);
			params.put("sessionId", sessionId);
			// 请求Service系统
			result = faceIDService.saveVerifyFace(params, delta);
			if ("200".equals(result.getCode())) {
				result.setCode("000");
				result.setMsg(result.getMsg());
			}
			if ("数据源中没有此身份证号码的记录".equals(result.getMsg())) {
				result.setCode("204");
			}
			if ("数据源中存在此身份证号码，但与提供的姓名不匹配".equals(result.getMsg())) {
				result.setCode("205");
			}
			if ("身份证号格式错误".equals(result.getMsg())) {
				result.setCode("206");
			}
			boolean bo1 = !"203".equals(result.getCode()) && !"204".equals(result.getCode())
					&& !"205".equals(result.getCode()) && !"206".equals(result.getCode())
					&& !"000".equals(result.getCode());
			if (bo1 || true) {
				Map<String, String> zxparams = new HashMap<String, String>();
				// String msg = "{\"code\" : " + result.getCode() + ",\"msg\" : " + result.getMsg() + "}"; //
				// 定义一个json类型的msg
				zxparams.put("sessionId", sessionId);
				zxparams.put("borrowerId", borrowId);
				zxparams.put("orderId", orderId);
				zxparams.put("idcard_name", idcard_name);
				zxparams.put("idcard_number", idcard_number);
				zxparams.put("msg", result.getMsg());
				zxparams.put("score",
						CommUtils.isNull(result.getResult()) ? "0" : CommUtils.toString(result.getResult()));
				zxparams.put("sourceItem", "3");
				faceIDService.saveZxBlack(zxparams);
			}
			if ("verify方法调用数据源发生错误".equals(result.getMsg())) {
//				MsgReqData msgReqData = new MsgReqData();
//				msgReqData.setMsg("Face++预警：数据源异常，请立即联系faceID客服，并通知审核相关负责人，暂停审核业务。");
//				msgReqData.setPhone("15623060179,17702708862");
//				msgReqData.setType("0");
//				Response<Object> response = BeadWalletSendMsgService.senMarketingMsg(msgReqData);
				
//				logger.info(response.getRequestMsg());
				String phones = "15623060179,17702708862";
				String msg = "Face++预警：数据源异常，请立即联系faceID客服，并通知审核相关负责人，暂停审核业务。";
//				boolean flag = sendMessageCommonService.commonSendMessage(phones, msg);
//				if (flag) {
//					logger.info("短信发送成功！");
//				}else {
//					logger.info("短信发送失败！");
//				}
				MessageDto messageDto = new MessageDto();
				messageDto.setBusinessScenario("1");
				messageDto.setPhone(phones);
				messageDto.setMsg(msg);
				messageDto.setType("1");
				RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
			}

			if ("系统检验出delta已被使用过".equals(result.getMsg())) {
//				MsgReqData msgReqData = new MsgReqData();
//				msgReqData.setMsg("Face++预警：配合MegLive SDK使用时，系统检验出delta已被使用过。请及时处理。");
//				msgReqData.setPhone("15623060179,17702708862");
//				msgReqData.setType("0");
//				Response<Object> response = BeadWalletSendMsgService.senMarketingMsg(msgReqData);
//				logger.info(response.getRequestMsg());
				String phones = "15623060179,17702708862";
				String msg = "Face++预警：配合MegLive SDK使用时，系统检验出delta已被使用过。请及时处理。";
//				boolean flag = sendMessageCommonService.commonSendMessage(phones, msg);
//				if (flag) {
//					logger.info("短信发送成功！");
//				}else {
//					logger.info("短信发送失败！");
//				}
				MessageDto messageDto = new MessageDto();
				messageDto.setBusinessScenario("1");
				messageDto.setPhone(phones);
				messageDto.setMsg(msg);
				messageDto.setType("1");
				RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
			}
			GetImageUtils.delImage(imagePath);
			// 将返回信息保存大AppResult中
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("900");
			result.setMsg("执行face++活体检测异常，请人工审核！");
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
			logger.error(sessionId + ":执行APP的FaceIDController的verifyFace方法，异常", e);
		}
		// 返回数据给app
		logger.info(sessionId + ":结束APP的FaceIDControlle的verifyFace方法，出参" + JSONObject.toJSONString(result));
		return JSONObject.toJSONString(result);
	}

	/**
	 * 保存拍照信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/faceID/appCheckLogin/saveIDCar.do")
	public AppResponseResult saveIDCarInfo(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		logger.info(sessionId + "开始执行开始FaceIDController的saveIDCarInfo方法");
		AppResponseResult respResult = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");
			String borrowId = request.getParameter("bId");
			if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(borrowId)) {
				respResult.setCode("1001");
				respResult.setMsg("工单ID或借款人ID不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法...");
				return respResult;
			}
			String authChannel = request.getParameter("authChannel");
			if (StringUtils.isEmpty(authChannel)) {
				respResult.setCode("1001");
				respResult.setMsg("来源渠道不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法...");
				return respResult;
			}
			// 身份证正面地址
			String sfzzmUrl = request.getParameter("sfzzmUrl");
			// 身份证反面地址
			String sfzfmUrl = request.getParameter("sfzfmUrl");
			// 活体图片地址
			String czzUrl = request.getParameter("czzUrl");
			// 定义判断条件
			boolean flag1 = StringUtils.isEmpty(sfzzmUrl) || StringUtils.isEmpty(sfzfmUrl)
					|| StringUtils.isEmpty(czzUrl);
			if (flag1) {
				respResult.setCode("1001");
				respResult.setMsg("图片路径不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法...");
				return respResult;
			}
			// 身份证信息
			String adjunctDesc = request.getParameter("adjunctDesc");
			if (StringUtils.isEmpty(adjunctDesc) || adjunctDesc.split("/") == null || adjunctDesc.split("/").length != 2) {
				respResult.setCode("1001");
				respResult.setMsg("用姓名和身份证号码不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法...");
				return respResult;
			}

			// 照片类型，活体还是手持
			String photoState = request.getParameter("photoState");
			if (StringUtils.isEmpty(photoState)) {
				photoState = "0";
			}
			logger.info(sessionId + "入参，身份证正面图片地址：" + sfzzmUrl + ",身份证反面图片地址：" + sfzfmUrl + ",活体图像地址：" + czzUrl
					+ ",身份证信息：" + adjunctDesc + ",phtoState:" + photoState);
			// 活体校验所需的校验码
			String delta = request.getParameter("delta");
			if (StringUtils.isEmpty(delta)) {
				respResult.setCode("1001");
				respResult.setMsg("校验信息不能为空！");
				logger.info(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法...");
				return respResult;
			}
			// 定义存储错误信息的字符串
			String verifyFaceResult = null;
			verifyFaceResult = verifyFace(borrowId, orderId, czzUrl, adjunctDesc, delta);
			AppResponseResult result = new AppResponseResult();
			result = JSONObject.parseObject(verifyFaceResult, AppResponseResult.class);
			// 定义判断条件，该条件为前端活体验证需要前端处理的错误信息
			boolean flage3 = "上传的图像过大".equals(result.getMsg()) || "图像质量过低".equals(result.getMsg())
					|| "图像上传不完整".equals(result.getMsg());
			if (flage3) {
				respResult.setCode("1001");
				respResult.setMsg(result.getMsg());
				logger.info(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法...");
				return respResult;
			}
			respResult.setCode("0000");
			respResult.setMsg("操作成功");
			logger.info(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法...");
			bwOrderAuthService.savePicInfo(orderId, borrowId, sfzzmUrl, sfzfmUrl, czzUrl, authChannel, adjunctDesc,
					photoState, verifyFaceResult, "1");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			respResult.setCode("1001");
			respResult.setMsg("系统异常");
			logger.error(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法,系统异常，异常原因：", e);
		}
		return respResult;
	}

	/**
	 * 查询图片信息(faceID专用)
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/faceID/appCheckLogin/findAdjunct.do")
	public Map<String, Object> findFaceIdAdjunct(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		logger.info(sessionId + "开始执行开始FaceIDController的findFaceIdAdjunct方法...");
		Map<String, Object> result = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		String bwId = request.getParameter("bwId");
		if (StringUtils.isEmpty(orderId)) {
			result.put("code", "301");
			result.put("msg", "工单id为空");
			return result;
		}
		if (StringUtils.isEmpty(bwId)) {
			result.put("code", "302");
			result.put("msg", "借款人id为空");
			return result;
		}
		List<BwAdjunct> list = null;
		// 判断当前工单是否有对应附件信息
		list = bwAdjunctService.findBwAdjunctPhotoByOrderId(Long.parseLong(orderId));
		List<BwAdjunct> newlist = new ArrayList<BwAdjunct>();
		if (list != null && list.size() > 0) {
			logger.info("根据当前工单" + orderId + "查找到图片附件信息。");
			result.put("code", "000");
			result.put("msg", "查询附件信息成功");
			for (int i = 0; i < list.size(); i++) {
				BwAdjunct bwAdjunct = list.get(i);
				if (bwAdjunct.getAdjunctType() == 1) {
					result.put("adjunctDesc", bwAdjunct.getAdjunctDesc());
				}
				if (bwAdjunct != null) {
					newlist.add(bwAdjunct);
				}
			}
			if (newlist != null && newlist.size() > 0) {
				result.put("result", newlist);
			} else {
				result.put("result", null);
			}
		} else {
			result.put("code", "303");
			result.put("msg", "当前工单没有附件信息");
		}
		logger.info(sessionId + ":结束执行FaceIDController的findFaceIdAdjunct方法,出参：" + JsonUtils.toJson(result));
		return result;
	}

	/**
	 * faceID - H5 - 身份证正面验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/app/faceID/appCheckLogin/ocrIDCardFrontH5.do")
	public AppResponseResult ocrIDCardFrontH5(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + ":开始FaceIDController的ocrIDCardFrontH5方法...");
		// 获取app传过来的二进制图片
		// String ocrIDCard_front_image_path =
		// request.getParameter("ocrIDCard_front_image_path");
		String ocrIDCard_front_image_path = request.getParameter("ocrIDCard_front_image_path");
		String borrowerId = request.getParameter("borrowerId");
		String orderId = request.getParameter("orderId");
		String orderNumber = request.getParameter("orderNo");
		// 通过app传来的图片路径将图片下载下来保存到本地
		String imagePath = fileUrl + "/" + borrowerId + "_front.jpg";
		String fileNameFront = borrowerId + "_front.jpg";
		try {
			if (StringUtils.isEmpty(ocrIDCard_front_image_path)) {
				result.setCode("103");
				result.setMsg("图片信息不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardH5方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(borrowerId)) {
				result.setCode("103");
				result.setMsg("用户id不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardH5方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(orderNumber)) {
				result.setCode("103");
				result.setMsg("orderNumber不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardH5方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			// boolean bo1 =
			// Base64ImageConverter.saveUrlAs(sessionId,ocrIDCard_front_image_path,
			// fileUrl,fileNameFront);
			boolean bo1 = GetImageUtils.base64SaveImage(ocrIDCard_front_image_path, fileUrl, fileNameFront);
			if (!bo1) {
				result.setCode("208");
				result.setMsg("身份证图片本地存储失败，图片路径错误，请重新上传图片!");
				logger.info(sessionId + ":存储图片路径：" + imagePath);
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardH5方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("sessionId", sessionId);
			params.put("imagePath", ocrIDCard_front_image_path);
			params.put("user_id", borrowerId);
			result = faceIDService.ocrIDCardFront(params);
			if ("200".equals(result.getCode())) {
				result.setCode("000");
				HashMap<String, String> resultMap = (HashMap<String, String>) result.getResult();
				String nameAndNumber = resultMap.get("name") + "/" + resultMap.get("id_card_number");
				String fileName = orderNumber + "_01.jpg";
				String diskName = "upload/backend/" + DateUtils.getDate_ToString2() + "/" + fileName;
				boolean bo2 = YunOSSUtil.uploadObject2OSS(imagePath, FaceIDConstant.BACKETNAME, diskName);
				resultMap.put(" sfzzmUrl", diskName);
				result.setResult(resultMap);
				if (bo2) {
					// 保存身份证信息
					faceIDService.saveIdCard(resultMap);
					// 保存图片路径
					bwOrderAuthService.saveOcrIDFront(orderId, borrowerId, diskName, nameAndNumber, "1");

					Map<String, String> zxparams = new HashMap<String, String>();
					// String msg = "{\"code\" : " + result.getCode() + ",\"msg\" : " + result.getMsg() + "}"; //
					// 定义一个json类型的msg
					Map<String, String> map = (Map<String, String>) result.getResult();
					String idcard_name = map.get("name");
					String idcard_number = map.get("id_card_number");
					zxparams.put("sessionId", sessionId);
					zxparams.put("borrowerId", borrowerId);
					zxparams.put("orderId", orderId);
					zxparams.put("idcard_name", idcard_name);
					zxparams.put("idcard_number", idcard_number);
					zxparams.put("msg", result.getMsg());
					zxparams.put("score", CommUtils.isNull(resultMap.get("score")) ? "0" : resultMap.get("score"));
					zxparams.put("sourceItem", "1");
					faceIDService.saveZxBlack(zxparams);

				} else {
					result.setCode("207");
					result.setMsg("图片未上传成功，请重新拍摄！");
					// 删除缓存的图片
					GetImageUtils.delImage(imagePath);
					return result;
				}

			}
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
		} catch (Exception e) {
			result.setCode("900");
			result.setMsg(e.getMessage());
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
			logger.error(sessionId + ":执行APP的FaceIDController的ocrIDCard方法，异常：", e);

		}

		// 返回数据给app
		logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCard方法，出参" + JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * FaceID - H5 - 身份证反面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/faceID/appCheckLogin/ocrIDCardBackH5.do")
	public AppResponseResult ocrIDCardBackH5(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String sessionId = DateUtils.getDateHMToString();
		logger.info(sessionId + ":开始FaceIDController的ocrIDCardBack方法...");
		// 获取app传过来的二进制图片
		// String ocrIDCardBackPath =
		// request.getParameter("ocrIDCard_back_image_path");
		String ocrIDCardBackPath = request.getParameter("ocrIDCard_back_image_path");
		String user_id = request.getParameter("user_id");
		String orderId = request.getParameter("orderId");
		String orderNumber = request.getParameter("orderNo");
		String imagePath = fileUrl + "/" + user_id + "_back.jpg";
		String fileNameBack = user_id + "_back.jpg";
		try {
			// 判断用户id和工单id是否为空
			boolean flag = StringUtils.isEmpty(user_id) || StringUtils.isEmpty(orderId);
			if (flag) {
				result.setCode("103");
				result.setMsg("用户id或工单id不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardBack方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			// 判断身份证路径是否为空
			if (StringUtils.isEmpty(ocrIDCardBackPath)) {
				result.setCode("103");
				result.setMsg("图片信息不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardBack方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			if (StringUtils.isEmpty(orderNumber)) {
				result.setCode("103");
				result.setMsg("orderNumber不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardH5方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			// boolean bo = Base64ImageConverter.saveUrlAs(sessionId,
			// ocrIDCardBackPath, fileUrl, fileNameBack);
			boolean bo = GetImageUtils.base64SaveImage(ocrIDCardBackPath, fileUrl, fileNameBack);
			// 判断图片是否存储成功
			if (bo == false) {
				result.setCode("208");
				result.setMsg("身份证图片本地存储失败，图片路径错误，请重新上传图片!");
				logger.info(sessionId + ":存储图片路径：" + imagePath);
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardBack方法，出参" + JSONObject.toJSONString(result));
				return result;
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("sessionId", sessionId);
			params.put("imagePath", ocrIDCardBackPath);
			params.put("user_id", user_id);
			result = faceIDService.ocrIDCardBack(params);
			// 如果code为200则返回前台code为000并保存身份证信息到数据库
			if ("200".equals(result.getCode())) {
				result.setCode("000");
				HashMap<String, String> resultMap = (HashMap<String, String>) result.getResult();
				String fileName = orderNumber + "_02.jpg";
				String diskName = "upload/backend/" + DateUtils.getDate_ToString2() + "/" + fileName;
				boolean bo2 = YunOSSUtil.uploadObject2OSS(imagePath, FaceIDConstant.BACKETNAME, diskName);
				resultMap.put(" sfzfmUrl", diskName);
				result.setResult(resultMap);
				if (bo2) {
					// 保存身份证信息
					faceIDService.saveIdCard(resultMap);
					// 保存图片路径
					bwOrderAuthService.saveOcrIDBack(orderId, user_id, diskName, "1");

					// TODO
					Map<String, String> zxparams = new HashMap<String, String>();
					// String msg = "{\"code\" : " + result.getCode() + ",\"msg\" : " + result.getMsg() + "}"; //
					// 定义一个json类型的msg
					zxparams.put("sessionId", sessionId);
					zxparams.put("borrowerId", user_id);
					zxparams.put("orderId", orderId);
					// zxparams.put("idcard_name", idcard_name);
					// zxparams.put("idcard_number", idcard_number);
					zxparams.put("msg", result.getMsg());
					zxparams.put("score", CommUtils.isNull(resultMap.get("score")) ? "0" : resultMap.get("score"));
					zxparams.put("sourceItem", "2");
					faceIDService.saveZxBlack(zxparams);
				} else {
					result.setCode("207");
					result.setMsg("图片未上传成功，请重新拍摄！");
					// 删除缓存的图片
					GetImageUtils.delImage(imagePath);
					return result;
				}
			}
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
			// 将接收到的参数保存到AppResult中
		} catch (Exception e) {
			result.setCode("900");
			result.setMsg(e.getMessage());
			// 删除缓存的图片
			GetImageUtils.delImage(imagePath);
			logger.error(sessionId + ":执行APP的FaceIDController的ocrIDCardBack方法，异常:", e);

		}

		// 返回数据给app
		logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardBack方法，出参" + JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * H5 - 保存持证照
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/faceID/appCheckLogin/saveCzzH5.do")
	public AppResponseResult saveCzz(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		String sessionId = DateUtils.getDateHMToString();
		logger.info(sessionId + ":开始执行开始FaceIDController的saveCzz方法");

		// 第一步：取参
		try {
			String orderId = request.getParameter("orderId");
			String borrowerId = request.getParameter("borrowerId");
			String authChannel = request.getParameter("authChannel");
			String imageStr = request.getParameter("imageStr");
			String orderNumber = request.getParameter("orderNo");
			String adjunctDesc = request.getParameter("adjunctDesc");
			String[] str = adjunctDesc.split("/");
			String idcard_name = str[0];
			String idcard_number = str[1];
			logger.info("orderId:" + orderId + "borrowerId:" + borrowerId + "authChannel:" + authChannel);
			// 第二步：验证参数
			if (StringUtils.isEmpty(orderId)) {
				respResult.setCode("1001");
				respResult.setMsg("工单ID不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveCzz方法,出参：" + JSONObject.toJSONString(respResult));
				return respResult;
			}
			if (StringUtils.isEmpty(borrowerId)) {
				respResult.setCode("1001");
				respResult.setMsg("借款人ID不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveCzz方法,出参：" + JSONObject.toJSONString(respResult));
				return respResult;
			}
			if (StringUtils.isEmpty(authChannel)) {
				respResult.setCode("1001");
				respResult.setMsg("认证渠道不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveCzz方法,出参：" + JSONObject.toJSONString(respResult));
				return respResult;
			}
			if (StringUtils.isEmpty(imageStr)) {
				respResult.setCode("1001");
				respResult.setMsg("持证照不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveCzz方法,出参：" + JSONObject.toJSONString(respResult));
				return respResult;
			}
			if (StringUtils.isEmpty(adjunctDesc)  || adjunctDesc.split("/") == null || adjunctDesc.split("/").length != 2) {
				respResult.setCode("1001");
				respResult.setMsg("身份信息不能为空");
				logger.info(sessionId + ":结束执行FaceIDController的saveCzz方法,出参：" + JSONObject.toJSONString(respResult));
				return respResult;
			}
			if (StringUtils.isEmpty(orderNumber)) {
				respResult.setCode("1001");
				respResult.setMsg("orderNumber不能为空！");
				logger.info(sessionId + ":结束APP的FaceIDControlle的ocrIDCardH5方法，出参" + JSONObject.toJSONString(respResult));
				return respResult;
			}
			// 第三步：将base64的照片保存本地并上传云服务器
			String fileName = borrowerId + "_" + orderId + ".jpg";
			boolean bo1 = GetImageUtils.base64SaveImage(imageStr, fileUrl, fileName);
			String imagePath = fileUrl + "/" + fileName;
			String diskName = "upload/backend/" + DateUtils.getDate_ToString2() + "/" + orderNumber + "_03.jpg";
			boolean bo2 = false;
			if (bo1) {
				// 将照片上传阿里云
				bo2 = YunOSSUtil.uploadObject2OSS(imagePath, FaceIDConstant.BACKETNAME, diskName);
			}
			if (bo2) {
				// 将信息保存数据库
				bwOrderAuthService.savePicInfoH5(orderId, borrowerId, diskName, authChannel, "0", "0");
				// 将信息保存征信黑名单进行人工审核
				Map<String, String> zxparams = new HashMap<String, String>();
				zxparams.put("sessionId", sessionId);
				zxparams.put("borrowerId", borrowerId);
				zxparams.put("orderId", orderId);
				zxparams.put("idcard_name", idcard_name);
				zxparams.put("idcard_number", idcard_number);
				zxparams.put("msg", "持证照认证,需要人工审核");
				zxparams.put("score", "0");
				zxparams.put("sourceItem", "3");
				faceIDService.saveZxBlack(zxparams);

				respResult.setCode("000");
				respResult.setMsg("保存信息成功！");
				logger.info(sessionId + ":结束执行FaceIDController的saveCzz方法,出参：" + JSONObject.toJSONString(respResult));
				return respResult;
			}
			respResult.setCode("403");
			respResult.setMsg("持证照图片格式不正确，请重新上传！");
		} catch (Exception e) {
			respResult.setCode("900");
			respResult.setMsg("保存持证照出现异常！");
			logger.error(sessionId + ":执行FaceIDController的saveCzz方法,异常，异常信息为：", e);
		}
		logger.info(sessionId + ":结束执行FaceIDController的saveCzz方法,出参：" + JSONObject.toJSONString(respResult));
		return respResult;
	}

	/**
	 * faceID - H5 - 查询图片信息(faceID专用)
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/faceID/appCheckLogin/findAdjunctH5.do")
	public Map<String, Object> findFaceIdAdjunctH5(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtils.getDateHMToString();
		logger.info(sessionId + "开始执行开始FaceIDController的findFaceIdAdjunct方法...");

		// 第一步：取参
		Map<String, Object> result = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		String bwId = request.getParameter("borrowerId");
		if (StringUtils.isEmpty(orderId)) {
			result.put("code", "301");
			result.put("msg", "工单id为空");
			return result;
		}
		if (StringUtils.isEmpty(bwId)) {
			result.put("code", "302");
			result.put("msg", "借款人id为空");
			return result;
		}
		List<BwAdjunct> list = null;
		// 第二步：判断当前工单是否有对应附件信息
		list = bwAdjunctService.findBwAdjunctPhotoByOrderId(Long.parseLong(orderId));
		List<BwAdjunct> newlist = new ArrayList<BwAdjunct>();
		if (list != null && list.size() > 0) {
			logger.info("根据当前工单" + orderId + "查找到图片附件信息。");
			result.put("code", "000");
			result.put("msg", "查询附件信息成功");
			for (int i = 0; i < list.size(); i++) {
				BwAdjunct bwAdjunct = list.get(i);
				if (bwAdjunct.getAdjunctType() == 1) {
					result.put("adjunctDesc", bwAdjunct.getAdjunctDesc());
				}
				if (bwAdjunct != null) {
					newlist.add(bwAdjunct);
				}
			}
			if (newlist != null && newlist.size() > 0) {
				result.put("result", newlist);
			} else {
				result.put("result", null);
			}
		} else {
			result.put("code", "303");
			result.put("msg", "当前工单没有附件信息");
		}
		logger.info(sessionId + ":结束执行FaceIDController的findFaceIdAdjunct方法,出参：" + JsonUtils.toJson(result));
		return result;
	}

	// /**
	// * 获取随机朗读字符传给前端
	// *
	// * @param request
	// * @param response
	// * @return
	//
	// @ResponseBody
	// @RequestMapping("/app/faceID/appCheckLogin/getRandomNumberH5.do")
	// public AppResponseResult getRandomNumber(HttpServletRequest request,
	// HttpServletResponse response) {
	// AppResponseResult result = new AppResponseResult();
	// Map<String, String> params = new HashMap<String, String>();
	// String sessionId = DateUtils.getDateHMToString();
	// logger.info(sessionId + ":开始FaceIDController的getRandomNumber方法...");
	// try {
	// String biz_no = sessionId;
	// params.put("sessionId", sessionId);
	// params.put("biz_no", biz_no);
	// result = faceIDService.getRandomNumber(params);
	// if ("200".equals(result.getCode())) {
	// result.setCode("0000");
	// }
	// } catch (Exception e) {
	// result.setCode("900");
	// result.setMsg(e.getMessage());
	// logger.info(sessionId + ":执行APP的FaceIDController的ocrIDCard方法，异常："
	// + e.getMessage());
	//
	// }
	// return result;
	// }
	// */
	// /**
	// * 活体验证
	// *
	// * @param paramMap
	// * @return
	//
	// public Map<String, String> validateVideo(Map<String, String> paramMap) {
	// Map<String, String> params = new HashMap<String, String>();
	// String sessionId = paramMap.get("sessionId");
	// logger.info(sessionId + ":开始FaceIDController的validateVideo()方法...");
	// try {
	// String biz_no = sessionId;
	// String token_random_number = paramMap.get("token_random_number");
	// String videoPath = paramMap.get("videoPath");
	// String borrowId = paramMap.get("borrowId");
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("biz_no", biz_no);
	// map.put("borrowId", borrowId);
	// map.put("sessionId", sessionId);
	// map.put("token_random_number", token_random_number);
	// map.put("videoPath", videoPath);
	// params = faceIDService.validateVideo(map);
	// } catch (Exception e) {
	// params.put("code", "900");
	// params.put("msg", e.getMessage());
	// logger.info(sessionId + ":执行APP的FaceIDController的ocrIDCard方法，异常："
	// + e.getMessage());
	// }
	// return params;
	// }
	// */
	// /**
	// * 活体比对
	// *
	// * @param request
	// * @param response
	// * @return
	//
	// public String verify(Map<String, Object> verifyMap) {
	// AppResponseResult result = new AppResponseResult();
	// // 第一步：取参
	// String sessionId = verifyMap.get("biz_no").toString();
	// String borrowId = verifyMap.get("borrowId").toString();
	// String token_random_number = verifyMap.get("token_random_number")
	// .toString();
	// String adjunctDesc = verifyMap.get("adjunctDesc").toString();
	// MultipartFile file = (MultipartFile) verifyMap.get("video");
	// String imageStr = verifyMap.get("imageStr").toString();
	// String biz_no = sessionId;
	// String videoName = borrowId + "_video.mp4";
	// String[] str = adjunctDesc.split("/");
	// String idcard_name = str[0];
	// String idcard_number = str[1];
	// String imageName = borrowId + "_video.jpg";
	// String imagePath = fileUrl + "/" + imageName;
	// String videoPath = fileUrl + "/" + videoName;
	// logger.info(sessionId + ":开始FaceIDController的verify()方法...");
	//
	// // 第二步：验证参数
	// if (StringUtils.isNullOrEmpty(biz_no)) {
	// result.setCode("103");
	// result.setMsg("流程唯一识别号不能为空");
	// return JSONObject.toJSONString(result);
	// }
	// if (StringUtils.isNullOrEmpty(token_random_number)) {
	// result.setCode("103");
	// result.setMsg("验证token不能为空！");
	// return JSONObject.toJSONString(result);
	// }
	// if (file == null) {
	// result.setCode("103");
	// result.setMsg("视频不能为空！");
	// return JSONObject.toJSONString(result);
	// }
	// boolean bo1 = StringUtils.isNullOrEmpty(idcard_name)
	// || StringUtils.isNullOrEmpty(idcard_number);
	// if (bo1) {
	// result.setCode("103");
	// result.setMsg("身份证姓名或身份证号码不能为空！");
	// return JSONObject.toJSONString(result);
	// }
	//
	// // 第三步：将视频存储本地
	//
	// boolean bo2 = GetImageUtils.saveFile(file, fileUrl, videoName);
	// if (bo2) { // 判断本地存储是否成功
	//
	// // 第四步：将视频校验参数封装到parmaMap集合中
	// Map<String, String> paramMap = new HashMap<String, String>();
	// paramMap.put("sessionId", sessionId);
	// paramMap.put("borrowId", borrowId);
	// paramMap.put("token_random_number", token_random_number);
	// paramMap.put("videoPath", videoPath);
	// Map<String, String> resultMap = validateVideo(paramMap); // 活体检测
	// boolean bo3 = "200".equals(resultMap.get("code"));
	// if (bo3) { // 判断校验是否成功
	//
	// // 第六步：将活体认证需要的参数分装到parmas集合中
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("biz_no", biz_no);
	// params.put("token", resultMap.get("token_video"));
	// params.put("idcard_name", idcard_name);
	// params.put("idcard_number", idcard_number);
	// result = faceIDService.verify(params); // 活体认证
	// if ("200".equals(result.getCode())) {
	// result.setCode("000"); // 活体认证成功则返回code为000
	// }
	//
	// // 第七步：将截图的图片存储本地
	// boolean bo4 = GetImageUtils.base64SaveImage(imageStr, fileUrl,
	// imageName);
	// if (bo4) {
	// String diskFileName = sessionId + "_" + borrowId + "_"
	// + RandomNumber.numberChar() + ".jpg";
	// String diskName = "upload/idcardvideoimage/"
	// + DateUtils.getDate_ToString() + "/" + diskFileName;
	// boolean bo5 = YunOSSUtil.uploadObject2OSS(imagePath,
	// FaceIDConstant.BACKETNAME, diskName);
	// String videoFileName = RandomNumber.numberChar() + "_"
	// + RandomNumber.numberChar() + "_"
	// + RandomNumber.numberChar() + ".mp4";
	// String videoDiskName = "upload/idcardvideo/"
	// + DateUtils.getDate_ToString() + "/"
	// + videoFileName;
	// boolean bo6 = YunOSSUtil.uploadObject2OSS(
	// resultMap.get("videoPath"),
	// FaceIDConstant.BACKETNAME, videoDiskName);
	// if (bo5 && bo6) {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("videoImagePath", diskName);
	// map.put("videoPath", videoDiskName);
	// map.put("idcard_name", idcard_name);
	// map.put("idcard_number", idcard_number);
	// result.setResult(map);
	// } else {
	// result.setCode("209");
	// result.setMsg("视频截图上传云服务器失败！");
	// }
	// }
	//
	// } else {
	// result.setCode(resultMap.get("code"));
	// result.setMsg(resultMap.get("msg"));
	// }
	// }
	// GetImageUtils.delImage(videoPath); // 删除本地视频
	// GetImageUtils.delImage(imagePath); // 删除本地图片
	// return JSONObject.toJSONString(result);
	// }
	// */
	// /**
	// * FaceID - H5 - 保存拍照信息
	// *
	// * @param request
	// * @param response
	// * @return
	//
	// @ResponseBody
	// @RequestMapping("/app/faceID/appCheckLogin/saveVerifyH5.do")
	// public AppResponseResult saveVerifyInfoH5(
	// @RequestParam(value = "video", required = false) MultipartFile file,
	// HttpServletRequest request, HttpServletResponse response) {
	// String sessionId = DateUtils.getDateHMToString();
	// logger.info(sessionId + "开始执行开始FaceIDController的saveIDCarInfo方法");
	// AppResponseResult respResult = new AppResponseResult();
	// try {
	// // 第一步：验证参数
	// // MultipartFile file = (MultipartFile)
	// // request.getParameter("video");
	// String orderId = request.getParameter("orderId");
	// String borrowId = request.getParameter("borrowerId");
	// if (StringUtils.isNullOrEmpty(orderId)
	// || StringUtils.isNullOrEmpty(borrowId)) {
	// respResult.setCode("1001");
	// respResult.setMsg("工单ID或借款人ID不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	// String authChannel = request.getParameter("authChannel");
	// if (StringUtils.isNullOrEmpty(authChannel)) {
	// respResult.setCode("1001");
	// respResult.setMsg("来源渠道不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	// String imageStr = request.getParameter("imageStr"); // 视频截图，最好能包含人脸
	// // 定义判断条件
	// boolean flag1 = file == null || StringUtils.isNullOrEmpty(imageStr);
	// if (flag1) {
	// respResult.setCode("1001");
	// respResult.setMsg("图片路径不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	// // 身份证信息
	// String adjunctDesc = request.getParameter("adjunctDesc");
	// if (StringUtils.isNullOrEmpty(adjunctDesc)) {
	// respResult.setCode("1001");
	// respResult.setMsg("用姓名和身份证号码不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	// String biz_no = request.getParameter("biz_no");
	// if (StringUtils.isNullOrEmpty(biz_no)) {
	// respResult.setCode("1001");
	// respResult.setMsg("流程唯一识别号不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	//
	// // 照片类型，活体还是手持
	// String photoState = request.getParameter("photoState");
	// if (StringUtils.isNullOrEmpty(photoState)) {
	// photoState = "0";
	// }
	// // 朗读随机字符串token
	// String token_random_number = request
	// .getParameter("token_random_number");
	// if (StringUtils.isNullOrEmpty(token_random_number)) {
	// respResult.setCode("1001");
	// respResult.setMsg("朗读校验字符串不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法，出参"
	// + JSONObject.toJSONString(respResult));
	// return respResult;
	// }
	//
	// String verifyResult = null; // 定义存储错误信息的字符串
	//
	// // 第二步：将活体校验需要的参数封装到map中
	// Map<String, Object> verifyMap = new HashMap<String, Object>();
	// verifyMap.put("biz_no", biz_no);
	// verifyMap.put("sessionId", sessionId);
	// verifyMap.put("borrowId", borrowId);
	// verifyMap.put("token_random_number", token_random_number);
	// verifyMap.put("video", file);
	// verifyMap.put("adjunctDesc", adjunctDesc);
	// verifyMap.put("imageStr", imageStr);
	//
	// // 第四步:进行活体验证
	// verifyResult = verify(verifyMap);
	// AppResponseResult result = new AppResponseResult();
	// result = JSONObject.parseObject(verifyResult,
	// AppResponseResult.class);
	// Map<String, String> resultMap = (Map<String, String>) result
	// .getResult(); // 取出resul中的reuslt信息，存入map中
	// logger.info(sessionId + "入参，身份证信息：" + adjunctDesc + ",phtoState:"
	// + photoState);
	//
	// // 第五步：验证返回结果
	// boolean flag3 = "上传的图像过大".equals(result.getMsg())
	// || "图像质量过低".equals(result.getMsg())
	// || "图像上传不完整".equals(result.getMsg())
	// || "上传视频中没有检测到人脸".equals(result.getMsg())
	// || "上传视频中人脸质量太差".equals(result.getMsg())
	// || "上传视频时长有误".equals(result.getMsg())
	// || "上传视频中有多张人脸".equals(result.getMsg())
	// || "上传视频中没有语音，请重新上传".equals(result.getMsg())
	// || "当前视频无法解析,请重新上传".equals(result.getMsg());
	// if (flag3) {
	// respResult.setCode("1001");
	// respResult.setMsg(result.getMsg());
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	// if ("并发数超过限制".equals(result.getMsg())) {
	// respResult.setCode("1001");
	// respResult.setMsg("请求超时，请稍后再试");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	// if ("103".equals(result.getCode())) {
	// respResult.setCode("1001");
	// respResult.setMsg(result.getMsg());
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	//
	// // 第六步：将需要系统审核的重新定义code
	// boolean flag4 = "idcard_name字符过多".equals(result.getMsg())
	// || "不是正确的身份证格式".equals(result.getMsg())
	// || "数据源中没有此身份证号码".equals(result.getMsg())
	// || "身份证与姓名不匹配".equals(result.getMsg());
	//
	// // 第七步：修改存入数据库的code信息，方便以后使用
	// if (flag4) {
	// respResult.setCode("207");// 207标识需要系统审核的
	// }
	// if ("200".equals(result.getCode())) {
	// respResult.setCode("000");// 000标识审核通过的
	// }
	// if ("000".equals(respResult.getCode())
	// || "207".equals(respResult.getCode())) {
	// respResult.setCode("400");// 400标识需要人工审核的
	// // 第八步：人工审核的需要加入到ZxBlack中
	// String idcard_name = resultMap.get("idcard_name"); // 取出认证人姓名
	// String idcard_number = resultMap.get("idcard_number");// 取出认证人身份证号码
	// Map<String, String> zxparams = new HashMap<String, String>();
	// zxparams.put("sessionId", sessionId);
	// zxparams.put("borrowerId", borrowId);
	// zxparams.put("orderId", orderId);
	// zxparams.put("idcard_name", idcard_name);
	// zxparams.put("idcard_number", idcard_number);
	// faceIDService.saveZxBlack(zxparams);
	// } else {
	// respResult.setCode(result.getCode());
	// }
	// respResult.setMsg(result.getMsg());
	// // 第九步：短信通知
	// boolean flag5 = "API_KEY和API_SECRET不匹配".equals(result.getMsg())
	// || "服务器内部错误，请联系服务提供商".equals(result.getMsg())
	// || "API_KEY被停用或次数超限".equals(result.getMsg());
	// if (flag5) {
	// MsgReqData msgReqData1 = new MsgReqData();
	// if ("API_KEY和API_SECRET不匹配".equals(result.getMsg())) {
	// msgReqData1
	// .setMsg("【提醒】faceIDapi_key 和 api_secret 不匹配，请立即联系faceID客服");
	// } else if ("API_KEY被停用或次数超限".equals(result.getMsg())) {
	// msgReqData1
	// .setMsg("【提醒】faceIDapi_key被停用、调用次数超限、没有调用此API的权限，或者没有以当前方式调用此API的权限。请立即联系faceID客服");
	// } else {
	// msgReqData1.setMsg("【提醒】FaceID服务器内部错误，请立即联系faceID客服.");
	// }
	// msgReqData1.setPhone("15623060179");
	// msgReqData1.setType("0");
	// Response<Object> response1 = BeadWalletSendMsgService
	// .senMarketingMsg(msgReqData1);
	// MsgReqData msgReqData2 = new MsgReqData();
	// if ("API_KEY和API_SECRET不匹配".equals(result.getMsg())) {
	// msgReqData1
	// .setMsg("【提醒】faceIDapi_key 和 api_secret 不匹配，请立即联系faceID客服");
	// } else if ("API_KEY被停用或次数超限".equals(result.getMsg())) {
	// msgReqData1
	// .setMsg("【提醒】faceIDapi_key被停用、调用次数超限、没有调用此API的权限，或者没有以当前方式调用此API的权限。请立即联系faceID客服");
	// } else {
	// msgReqData1.setMsg("【提醒】FaceID服务器内部错误，请立即联系faceID客服.");
	// }
	// msgReqData2.setPhone("17702708862");
	// msgReqData2.setType("0");
	// Response<Object> response2 = BeadWalletSendMsgService
	// .senMarketingMsg(msgReqData2);
	// logger.info(response1.getRequestMsg());
	// logger.info(response2.getRequestMsg());
	// }
	//
	// logger.info(sessionId + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// Map<String, String> jsonMap = new HashMap<String, String>();
	// jsonMap.put("code", respResult.getCode());
	// jsonMap.put("msg", respResult.getMsg());
	// String saveResult = JSONObject.toJSONString(jsonMap);
	// // 第十步：将信息写入到数据库中
	// if (resultMap != null) {
	// String videoImageUrl = resultMap.get("videoImagePath");
	// String videoUrl = resultMap.get("videoPath");
	// bwOrderAuthService.savePicInfoH5(orderId, borrowId,
	// videoImageUrl, videoUrl, authChannel, photoState,
	// saveResult, "1");
	// }
	// if ("209".equals(result.getCode())) {
	// respResult.setCode("1001");
	// respResult.setMsg("网络繁忙，请稍后再试！");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法,阿里云网络繁忙保存信息失败！");
	// return respResult;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error(e.toString());
	// respResult.setCode("1001");
	// respResult.setMsg("系统异常,联系管理员！");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法,系统异常，异常原因："
	// + e.getMessage());
	// }
	// return respResult;
	// }
	// */
	// /**
	// * faceID - H5 - 保存认证信息
	// * @param request
	// * @param respose
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping("/app/faceID/appCheckLogin/saveVerifyH5.do")
	// public AppResponseResult saveValidateFace(HttpServletRequest request, HttpServletResponse respose){
	// String sessionId = DateUtils.getDateHMToString();
	// logger.info(sessionId + "开始执行开始FaceIDController的saveValidateFace方法");
	// AppResponseResult respResult = new AppResponseResult();
	// try {
	// // 第一步：取参
	// String orderId = request.getParameter("orderId");
	// String borrowId = request.getParameter("borrowerId");
	// String authChannel = request.getParameter("authChannel");
	// String adjunctDesc = request.getParameter("adjunctDesc");
	// String imageFrontFace = request.getParameter("imageFrontFace");
	// String imageSideFace = request.getParameter("imageSideFace");
	// String photoState = request.getParameter("photoState");
	// String biz_no = sessionId;
	//
	// // 第二步：验证
	// if (StringUtils.isNullOrEmpty(orderId)
	// || StringUtils.isNullOrEmpty(borrowId)) {
	// respResult.setCode("1001");
	// respResult.setMsg("工单ID或借款人ID不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,出参：" + JSONObject.toJSONString(respResult));
	// return respResult;
	// }
	// if (StringUtils.isNullOrEmpty(authChannel)) {
	// respResult.setCode("1001");
	// respResult.setMsg("来源渠道不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,出参：" + JSONObject.toJSONString(respResult));
	// return respResult;
	// }
	//
	// boolean bo1 = StringUtils.isNullOrEmpty(imageFrontFace) || StringUtils.isNullOrEmpty(imageSideFace);
	// if (bo1) {
	// respResult.setCode("1001");
	// respResult.setMsg("正侧脸图片均不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,出参：" + JSONObject.toJSONString(respResult));
	// return respResult;
	// }
	// if (StringUtils.isNullOrEmpty(adjunctDesc)) {
	// respResult.setCode("1001");
	// respResult.setMsg("用姓名和身份证号码不能为空");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,出参：" + JSONObject.toJSONString(respResult));
	// return respResult;
	// }
	// if (StringUtils.isNullOrEmpty(photoState)) {
	// photoState = "0";
	// }
	//
	// // 第三步：将图片存储本地
	// String frontFaceFileName = borrowId + "_front_face.jpg";
	// String sideFaceFileName = borrowId + "_side_face.jpg";
	// boolean bo2 = GetImageUtils.base64SaveImage(imageFrontFace, fileUrl, frontFaceFileName);
	// boolean bo3 = GetImageUtils.base64SaveImage(imageSideFace, fileUrl, sideFaceFileName);
	// if (!bo2 && !bo3) {
	// respResult.setCode("103");
	// respResult.setMsg("图片上传失败，请重新上传！");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,出参：" + JSONObject.toJSONString(respResult));
	// return respResult;
	// }
	//
	// // 第四步：活体检测
	// String imageFrontFacePath = fileUrl + "/" + frontFaceFileName;
	// String imageSideFacePath = fileUrl + "/" + sideFaceFileName;
	// Map<String, String> verifyMap = new HashMap<String, String>();
	// verifyMap.put("biz_no", biz_no);
	// verifyMap.put("adjunctDesc", adjunctDesc);
	// verifyMap.put("imageFrontFacePath", imageFrontFacePath);
	// verifyMap.put("imageSideFacePath", imageSideFacePath);
	// AppResponseResult result = new AppResponseResult();
	// result = verify(verifyMap);
	// respResult.setCode(result.getCode());
	// respResult.setMsg(result.getMsg());
	// // 第五步：验证返回结果
	// boolean flag3 = "图像无法解析".equals(result.getMsg())
	// || "图像中未检测到人脸".equals(result.getMsg())
	// || "图像中检测到多张人脸".equals(result.getMsg())
	// || "人脸太模糊，不适宜比对".equals(result.getMsg())
	// || "人脸区过亮或过暗，不适宜比对".equals(result.getMsg())
	// || "人脸非正面朝向".equals(result.getMsg())
	// || "人脸位置不合适，请将人脸放置中央区域再拍摄".equals(result.getMsg())
	// || "token_front_face不存在".equals(result.getMsg());
	// if (flag3) {
	// respResult.setCode("1001");
	// respResult.setMsg(result.getMsg());
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveIDCarInfo方法...");
	// return respResult;
	// }
	//
	// // 第六步：将需要系统审核的重新定义code
	// boolean flag4 = "idcard_name字符过多".equals(result.getMsg())
	// || "不是正确的身份证格式".equals(result.getMsg())
	// || "数据源中没有此身份证号码".equals(result.getMsg())
	// || "身份证与姓名不匹配".equals(result.getMsg());
	//
	// // 第七步：修改存入数据库的code信息，方便以后使用
	// if (flag4) {
	// respResult.setCode("207");// 207标识需要系统审核的
	// }
	// if ("200".equals(result.getCode())) {
	// respResult.setCode("000");
	// respResult.setMsg("活体校验成功");
	// }
	// // 第八步：短信通知
	// boolean flag5 = "api_key 和 api_secret 不匹配".equals(result.getMsg())
	// || "服务器内部错误，请联系服务提供商".equals(result.getMsg())
	// || "api_key被停用或调用次数超限".equals(result.getMsg());
	// if (flag5) {
	// MsgReqData msgReqData1 = new MsgReqData();
	// if ("API_KEY和API_SECRET不匹配".equals(result.getMsg())) {
	// msgReqData1
	// .setMsg("【提醒】faceIDapi_key 和 api_secret 不匹配，请立即联系faceID客服");
	// } else if ("API_KEY被停用或次数超限".equals(result.getMsg())) {
	// msgReqData1
	// .setMsg("【提醒】faceIDapi_key被停用、调用次数超限、没有调用此API的权限，或者没有以当前方式调用此API的权限。请立即联系faceID客服");
	// } else {
	// msgReqData1.setMsg("【提醒】FaceID服务器内部错误，请立即联系faceID客服.");
	// }
	// msgReqData1.setPhone("15623060179");
	// msgReqData1.setType("0");
	// Response<Object> response1 = BeadWalletSendMsgService
	// .senMarketingMsg(msgReqData1);
	// MsgReqData msgReqData2 = new MsgReqData();
	// if ("API_KEY和API_SECRET不匹配".equals(result.getMsg())) {
	// msgReqData1
	// .setMsg("【提醒】faceIDapi_key 和 api_secret 不匹配，请立即联系faceID客服");
	// } else if ("API_KEY被停用或次数超限".equals(result.getMsg())) {
	// msgReqData1
	// .setMsg("【提醒】faceIDapi_key被停用、调用次数超限、没有调用此API的权限，或者没有以当前方式调用此API的权限。请立即联系faceID客服");
	// } else {
	// msgReqData1.setMsg("【提醒】FaceID服务器内部错误，请立即联系faceID客服.");
	// }
	// msgReqData2.setPhone("17702708862");
	// msgReqData2.setType("0");
	// Response<Object> response2 = BeadWalletSendMsgService
	// .senMarketingMsg(msgReqData2);
	// logger.info(response1.getRequestMsg());
	// logger.info(response2.getRequestMsg());
	// }
	//
	// // 第九步：将数据保存到数据库中
	// Map<String, String> saveMap = new HashMap<String, String>();
	// saveMap.put("code", respResult.getCode());
	// saveMap.put("msg", respResult.getMsg());
	// String saveResult = JSONObject.toJSONString(saveMap);
	// // 第十步：将正脸和侧脸图片存储到云端
	// String frontFaceDiskName = "upload/frontface/"+ DateUtils.getDate_ToString() + "/"+ RandomNumber.numberChar() +
	// "_" + DateUtils.getDateHMToString() + ".jpg";
	// String sideFaceDiskName = "upload/sideface/"+ DateUtils.getDate_ToString() + "/"+ RandomNumber.numberChar() + "_"
	// + DateUtils.getDateHMToString() + ".jpg";
	// boolean flag6 = YunOSSUtil.uploadObject2OSS(imageFrontFacePath, FaceIDConstant.BACKETNAME, frontFaceDiskName);
	// boolean flag7 = YunOSSUtil.uploadObject2OSS(imageSideFacePath, FaceIDConstant.BACKETNAME, sideFaceDiskName);
	// boolean flag8 = flag6 & flag7;
	// if(flag8 == false){
	// respResult.setCode("103");
	// respResult.setMsg("网络繁忙，请稍后重试！");
	// return respResult;
	// }
	// bwOrderAuthService.savePicInfoH5(orderId, borrowId,
	// frontFaceDiskName, sideFaceDiskName, authChannel, photoState,
	// saveResult, "1");
	//
	// // 第十一步：保存后所有code改为000，让前端不弹出错误信息
	// respResult.setCode("000");
	// GetImageUtils.delImage(fileUrl + "/" + frontFaceFileName); // 删除本地视频
	// GetImageUtils.delImage(fileUrl + "/" + sideFaceFileName); // 删除本地图片
	// }catch (Exception e) {
	// e.printStackTrace();
	// logger.error(e.toString());
	// respResult.setCode("1001");
	// respResult.setMsg("系统异常,联系管理员！");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,系统异常，异常原因："
	// + e.getMessage());
	// }
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,出参：" + JSONObject.toJSONString(respResult));
	// return respResult;
	// }
	//
	// /**
	// * FaceID - H5 - 活体比对
	// *
	// * @param request
	// * @param response
	// * @return
	// */
	// public AppResponseResult verify(Map<String, String> verifyMap) {
	// AppResponseResult result = new AppResponseResult();
	// // 第一步：取参
	// String sessionId = verifyMap.get("biz_no").toString();
	// String adjunctDesc = verifyMap.get("adjunctDesc").toString();
	// String biz_no = sessionId;
	// String[] str = adjunctDesc.split("/");
	// String idcard_name = str[0];
	// String idcard_number = str[1];
	// String imageFrontFacePath = verifyMap.get("imageFrontFacePath");
	// String imageSideFacePath = verifyMap.get("imageSideFacePath");
	// logger.info(sessionId + ":开始FaceIDController的verify()方法...");
	//
	// // 第二步：验证参数
	// if (StringUtils.isNullOrEmpty(biz_no)) {
	// result.setCode("103");
	// result.setMsg("流程唯一识别号不能为空");
	// return result;
	// }
	// boolean bo1 = StringUtils.isNullOrEmpty(idcard_name)
	// || StringUtils.isNullOrEmpty(idcard_number);
	// if (bo1) {
	// result.setCode("103");
	// result.setMsg("身份证姓名或身份证号码不能为空！");
	// return result;
	// }
	// boolean bo2 = StringUtils.isNullOrEmpty(imageFrontFacePath) || StringUtils.isNullOrEmpty(imageSideFacePath);
	// if (bo2) {
	// result.setCode("103");
	// result.setMsg("正侧脸图片不能为空！");
	// return result;
	// }
	// try{
	// // 第三步：活体检验
	// Map<String, String> paramMap = new HashMap<String, String>();
	// paramMap.put("biz_no", biz_no);
	// paramMap.put("imageFrontFacePath",imageFrontFacePath);
	// paramMap.put("imageSideFacePath", imageSideFacePath);
	// result = validateFrontFace(paramMap);
	// Map<String, String> map = (Map<String, String>)result.getResult();
	// if (map != null) {
	// // 第四步：活体比对
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("biz_no", biz_no);
	// params.put("token", map.get("token_side_face"));
	// params.put("idcard_name", idcard_name);
	// params.put("idcard_number", idcard_number);
	// result = faceIDService.verify(params);
	// }
	// }catch (Exception e) {
	// e.printStackTrace();
	// logger.error(e.toString());
	// result.setCode("1001");
	// result.setMsg("系统异常,联系管理员！");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,系统异常，异常原因："
	// + e.getMessage());
	// }
	//
	// return result;
	// }
	//
	// /**
	// * faceID - H5 - 正脸验证
	// * @param paramMap
	// * @return
	// */
	// public AppResponseResult validateFrontFace(Map<String, String> paramMap) {
	// AppResponseResult result = new AppResponseResult();
	// String sessionId = paramMap.get("biz_no");
	// logger.info(sessionId + "开始执行开始FaceIDController的validateFrontFace方法");
	// // 第一步：取参
	// String biz_no = sessionId;
	// String imageFrontFacePath = paramMap.get("imageFrontFacePath");
	// String imageSideFacePath = paramMap.get("imageSideFacePath");
	// try{
	// // 第二步：正脸验证
	// Map<String, String> frontFaceMap = new HashMap<String, String>();
	// frontFaceMap.put("biz_no", biz_no);
	// frontFaceMap.put("sessionId", sessionId);
	// frontFaceMap.put("imagePath", imageFrontFacePath);
	// AppResponseResult frontFaceResult = faceIDService.validateFrontFace(frontFaceMap);
	// if (frontFaceResult != null) {
	// result.setCode(frontFaceResult.getCode());
	// result.setMsg(frontFaceResult.getMsg());
	// //第三步：侧脸验证
	// Map<String, String> objMap = (Map<String, String>)frontFaceResult.getResult();
	// AppResponseResult sideFaceResult = null;
	// if (objMap != null) {
	// Map<String, String> sideFaceMap = new HashMap<String, String>();
	// sideFaceMap.put("biz_no", biz_no);
	// sideFaceMap.put("imageSideFacePath", imageSideFacePath);
	// sideFaceMap.put("token_front_face", objMap.get("token_front_face"));
	// sideFaceResult = validateSideFace(sideFaceMap);
	// }
	// if (sideFaceResult != null) {
	// result.setCode(sideFaceResult.getCode());
	// result.setMsg(sideFaceResult.getMsg());
	// result.setResult(sideFaceResult.getResult());
	// }
	// }
	// }catch (Exception e) {
	// e.printStackTrace();
	// logger.error(e.toString());
	// result.setCode("1001");
	// result.setMsg("系统异常,联系管理员！");
	// logger.info(sessionId
	// + ":结束执行FaceIDController的saveValidateFace方法,系统异常，异常原因："
	// + e.getMessage());
	// }
	//
	// return result;
	// }
	//
	// /**
	// * faceID - H5 - 侧脸验证
	// * @param paramMap
	// * @return
	// */
	// public AppResponseResult validateSideFace(Map<String, String> paramMap) {
	// AppResponseResult result = new AppResponseResult();
	// String sessionId = paramMap.get("biz_no");
	// logger.info(sessionId + "开始执行开始FaceIDController的validateFrontFace方法");
	// // 第一步：取参
	// String biz_no = sessionId;
	// String token_front_face = paramMap.get("token_front_face");
	// String imageSideFacePath = paramMap.get("imageSideFacePath");
	//
	// // 第二步：侧脸验证
	// Map<String, String> sideFaceMap = new HashMap<String, String>();
	// sideFaceMap.put("biz_no", biz_no);
	// sideFaceMap.put("sessionId", sessionId);
	// sideFaceMap.put("token_front_face", token_front_face);
	// sideFaceMap.put("imagePath", imageSideFacePath);
	// result = faceIDService.validateSideFace(sideFaceMap);
	//
	// return result;
	// }
	//
	// /**
	// * faceID - H5 - 查询图片信息(faceID专用)
	// *
	// * @param request
	// * @param response
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping("/app/faceID/appCheckLogin/findAdjunctH5.do")
	// public Map<String, Object> findFaceIdAdjunctH5(HttpServletRequest request,
	// HttpServletResponse response) {
	// String sessionId = DateUtils.getDateHMToString();
	// logger.info(sessionId + "开始执行开始FaceIDController的findFaceIdAdjunct方法...");
	//
	// // 第一步：取参
	// Map<String, Object> result = new HashMap<String, Object>();
	// String orderId = request.getParameter("orderId");
	// String bwId = request.getParameter("borrowerId");
	// if (StringUtils.isNullOrEmpty(orderId)) {
	// result.put("code", "301");
	// result.put("msg", "工单id为空");
	// return result;
	// }
	// if (StringUtils.isNullOrEmpty(bwId)) {
	// result.put("code", "302");
	// result.put("msg", "借款人id为空");
	// return result;
	// }
	// List<BwAdjunct> list = null;
	// // 第二步：判断当前工单是否有对应附件信息
	// list = bwAdjunctService.findBwAdjunctPhotoByOrderId(Long
	// .parseLong(orderId));
	// List<BwAdjunct> newlist = new ArrayList<BwAdjunct>();
	// if (list != null && list.size() > 0) {
	// logger.info("根据当前工单" + orderId + "查找到图片附件信息。");
	// result.put("code", "000");
	// result.put("msg", "查询附件信息成功");
	// for (int i = 0; i < list.size(); i++) {
	// BwAdjunct bwAdjunct = list.get(i);
	// if (bwAdjunct.getAdjunctType() == 1) {
	// result.put("adjunctDesc", bwAdjunct.getAdjunctDesc());
	// }
	// if (bwAdjunct.getPhotoState() != 1) {// 图片为手持图片的时候则不给前台路径
	// bwAdjunct = null;
	// }
	// if (bwAdjunct != null) {
	// newlist.add(bwAdjunct);
	// }
	// }
	// if (newlist != null && newlist.size() > 0) {
	// result.put("result", newlist);
	// } else {
	// result.put("result", null);
	// }
	// } else {
	// result.put("code", "303");
	// result.put("msg", "当前工单没有附件信息");
	// }
	// logger.info(sessionId + ":结束执行FaceIDController的findFaceIdAdjunct方法,出参："
	// + JsonUtils.toJson(result));
	// return result;
	// }

	// /**
	// * 保存图片
	// * @param request
	// * @param response
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping("/app/faceID/appCheckLogin/saveH5FaceIDImage")
	// public AppResponseResult saveH5FaceIDImage(HttpServletRequest
	// request,HttpServletResponse response){
	// String sessionId = DateUtils.getDateHMToString();
	// AppResponseResult result = new AppResponseResult();
	// String orderId = request.getParameter("orderId");
	// String borrowerId = request.getParameter("borrowerId");
	// String idCardFrontUrl = request.getParameter("idCardFrontUrl");
	// String idCardBackUrl = request.getParameter("idCardBackUrl");
	// String verifyFaceUrl = request.getParameter("verifyFaceUrl");
	// String nameAndNumber = request.getParameter("adjunctDesc");
	// logger.info(sessionId +":开始执行FaceID的saveH5FaceIDImage方法，入参：orderId:" +
	// orderId + ",borrowerId:" + borrowerId +
	// ",idCardFrontUrl:"
	// + idCardFrontUrl + ",nameAndNumber:" + nameAndNumber + ",idCardBackUrl:"
	// + idCardBackUrl + ",verifyFaceUrl:" +
	// verifyFaceUrl);
	// boolean flag1 = StringUtils.isNullOrEmpty(orderId) ||
	// StringUtils.isNullOrEmpty(borrowerId);
	// if (flag1) {
	// result.setCode("1001");
	// result.setMsg("工单ID或用户ID不能为空！");
	// }
	// boolean flag2 = StringUtils.isNullOrEmpty(idCardFrontUrl) ||
	// StringUtils.isNullOrEmpty(idCardBackUrl) ||
	// StringUtils.isNullOrEmpty(verifyFaceUrl);
	// if (flag2) {
	// result.setCode("1001");
	// result.setMsg("上传图片地址为空，请重新上传！");
	// }
	// if(!StringUtils.isNullOrEmpty(idCardFrontUrl)){
	// bwOrderAuthService.saveOcrIDFront(orderId, borrowerId, idCardFrontUrl,
	// nameAndNumber, "1");
	// result.setCode("0000");
	// result.setMsg("保存身份证正面成功！");
	// logger.info(sessionId + ":身份证正面保存成功！");
	// return result;
	// }
	// if(!StringUtils.isNullOrEmpty(idCardBackUrl)){
	// bwOrderAuthService.saveOcrIDBack(orderId, borrowerId, idCardBackUrl,
	// "1");
	// result.setCode("0000");
	// result.setMsg("保存身份证反面成功！");
	// logger.info(sessionId + ":身份证反面保存成功！");
	// return result;
	// }
	//
	//
	// return result;
	// }

	// /**
	// * 查询工单认证状态
	// *
	// * @param request
	// * @param response
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping("/app/faceID/appCheckLogin/findOrderAuthStatus.do")
	// public AppResponseResult findOrderAuthStatus(HttpServletRequest request,
	// HttpServletResponse response) {
	// String sessionId = DateUtils.getDateHMToString();
	// AppResponseResult respResult = new AppResponseResult();
	// try {
	// String orderId = request.getParameter("orderId");
	// String bwId = request.getParameter("bwId");
	// if (CommUtils.isNull(orderId) || CommUtils.isNull(bwId)) {
	// respResult.setCode("1001");
	// respResult.setMsg("工单ID或借款人ID不能为空");
	// return respResult;
	// }
	// String authChannel = request.getParameter("authChannel");
	// if (CommUtils.isNull(authChannel)) {
	// respResult.setCode("1001");
	// respResult.setMsg("来源渠道不能为空");
	// return respResult;
	// }
	// Long order_id = Long.parseLong(orderId);
	// Long bw_id = Long.parseLong(bwId);
	// Integer auth_channel = Integer.parseInt(authChannel);
	// BwZmxyGrade bwZmxyGrade =
	// bwZmxyGradeService.findZmxyGradeByBorrowerId(bw_id);
	// if (bwZmxyGrade != null) {
	// Date updateTime = bwZmxyGrade.getUpdateTime();
	// Date now = new Date();
	// int interval = MyDateUtils.getDaySpace(updateTime, now);
	// BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(order_id,
	// 4);
	// if (interval > 30) {
	// if (bwOrderAuth != null) {
	// bwOrderAuthService.deleteBwOrderAuth(bwOrderAuth);
	// }
	// } else {
	// if (bwOrderAuth == null) {
	// bwOrderAuth = new BwOrderAuth();
	// bwOrderAuth.setAuth_channel(auth_channel);
	// bwOrderAuth.setAuth_type(4);
	// bwOrderAuth.setCreateTime(now);
	// bwOrderAuth.setOrderId(order_id);
	// bwOrderAuth.setUpdateTime(now);
	// bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
	// }
	// }
	// }
	// List<FaceIDOrderAuthDto> dtoList = new ArrayList<FaceIDOrderAuthDto>();
	// dtoList.add(new FaceIDOrderAuthDto(1, 0, 0));
	// dtoList.add(new FaceIDOrderAuthDto(2, 0, 0));
	// dtoList.add(new FaceIDOrderAuthDto(3, 0, 0));
	// dtoList.add(new FaceIDOrderAuthDto(4, 0, 0));
	// List<FaceIDOrderAuthDto> list =
	// bwOrderAuthService.findBwOrderAuthAndPhotoState(order_id);
	// if (CommUtils.isNull(list)) {
	// respResult.setResult(dtoList);
	// } else {
	// for (FaceIDOrderAuthDto faceIdOrderAuthDto : dtoList) {
	// for(FaceIDOrderAuthDto faceIdOrderAuthDtoList : list){
	// List<Integer> listAuthType = new ArrayList<Integer>();
	// listAuthType.add(faceIdOrderAuthDtoList.getAuthType());
	// if(listAuthType.contains(faceIdOrderAuthDto.getAuthType())){
	// faceIdOrderAuthDto.setAuthStatus(1);
	// faceIdOrderAuthDto.setPhotoState(faceIdOrderAuthDtoList.getPhotoState());
	// }
	// }
	// }
	// respResult.setResult(dtoList);
	// }
	// respResult.setCode("0000");
	// respResult.setMsg("查询成功");
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error(e.getMessage());
	// respResult.setCode("1001");
	// respResult.setMsg("系统异常");
	// }
	// logger.info(sessionId + ":app的FaceIDController的findOrderAuthStatus结束，出参："
	// + JsonUtils.toJson(respResult));
	// return respResult;
	// }
}
