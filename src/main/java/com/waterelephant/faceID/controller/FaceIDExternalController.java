package com.waterelephant.faceID.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.service.faceID.entity.Birthday;
import com.beadwallet.service.faceID.entity.OCRIDCard;
import com.beadwallet.service.faceID.entity.Verify;
import com.waterelephant.faceID.service.FaceIDService;
import com.waterelephant.faceID.utils.DateUtils;
import com.waterelephant.utils.AppResponseResult;

/**
 * FaceIDExternalController.java
 * Face++
 * @author dinglinhao
 * @date 2018年7月12日17:14:48
 *
 */
@Controller
@RequestMapping("/faceid")
public class FaceIDExternalController {
	
	private Logger logger = LoggerFactory.getLogger(FaceIDExternalController.class);
	
	@Autowired
	private FaceIDService faceIDService;
	
	/**
	 * OCR识别身份证
	 * @param idcard_img 身份证正面照base64字符串
	 * @param idcard_name 姓名
	 * @param idcard_num 身份证号
	 */
	@ResponseBody
	@RequestMapping("/orc/idcard_front.do")
	public AppResponseResult ocrIdcardFront(HttpServletRequest request,HttpServletResponse response) {
		AppResponseResult responseResult = new AppResponseResult();
		
		
		String userId = request.getParameter("user_id");
		String imgBase64 = request.getParameter("idcard_img");
		String side = "front";
		String sessionId = null;
		try {
			Assert.hasText(userId, "缺少参数[user_id]字段不能为空~");
			Assert.hasText(imgBase64,"缺少参数[idcard_img]字段不能为空~");
			Map<String,String> idcardInfo = new HashMap<>();
			
			//身份证正面
			sessionId = DateUtils.getDateHMToString();
			
			OCRIDCard ocrIdcard = faceIDService.ocrIdcard(sessionId, userId, imgBase64, side);

			Birthday ocrBirthday = ocrIdcard.getBirthday();
			String birthday = null;
			if(null != ocrBirthday) {
				birthday = ocrBirthday.getYear() + "-" + ocrBirthday.getMonth() + "-" + ocrBirthday.getDay();
			}
			idcardInfo.put("side", ocrIdcard.getSide());
			idcardInfo.put("address", ocrIdcard.getAddress());
			idcardInfo.put("birthday", birthday);
			idcardInfo.put("gender", ocrIdcard.getGender());
			idcardInfo.put("id_card_number", ocrIdcard.getId_card_number());
			idcardInfo.put("name", ocrIdcard.getName());
			idcardInfo.put("race", ocrIdcard.getRace());
			idcardInfo.put("score", String.valueOf(ocrIdcard.getLegality().getID_Photo()));
			idcardInfo.put("borrowerId", userId);
				
			//保存记录
			boolean flag = faceIDService.saveIdcardInfo(idcardInfo);
			
			if(flag) {
				responseResult.setCode("0000");
				responseResult.setMsg("身份识别成功~");
				responseResult.setResult(ocrIdcard);
			} else {
				responseResult.setCode("9999");
				responseResult.setMsg("身份识别失败~");
				responseResult.setResult(ocrIdcard);
			}
		} catch (IllegalArgumentException e) {
			logger.error("OCR识别失败{}",e.getMessage());
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("OCR识别异常{}",e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg("身份识别失败，请稍后再试~");
		}
		return responseResult;
	}
	
	/**
	 * OCR识别身份证-反面
	 * @param idcard_img 身份证反面照base64字符串
	 * @param idcard_name 姓名
	 * @param idcard_num 身份证号
	 */
	@ResponseBody
	@RequestMapping("/orc/idcard_back.do")
	public AppResponseResult ocrIdcardBack(HttpServletRequest request,HttpServletResponse response) {
		AppResponseResult responseResult = new AppResponseResult();
		
		String userId = request.getRequestedSessionId();
		String imgBase64 = request.getParameter("idcard_img");
		String idcardNum = request.getParameter("idcard_num");
		String side = "back";
		
		String sessionId = null;
		try {
			Assert.hasText(imgBase64,"缺少参数[idcard_img]字段不能为空~");
			Assert.hasText(idcardNum, "缺少参数[idcard_num]字段不能为空~");
			Map<String,String> idcardInfo = new HashMap<>();
			
			//身份证反面
			sessionId = DateUtils.getDateHMToString();
			OCRIDCard  ocrIdcard = faceIDService.ocrIdcard(sessionId, userId, imgBase64, side);
			idcardInfo.put("side", ocrIdcard.getSide());
			idcardInfo.put("issued_by", ocrIdcard.getIssued_by());
			idcardInfo.put("valid_date", ocrIdcard.getValid_date());
			idcardInfo.put("id_card_number", idcardNum);
			
			ocrIdcard.setId_card_number(idcardNum);
			//保存记录
			boolean flag = faceIDService.saveIdcardInfo(idcardInfo);
			
			if(flag) {
				responseResult.setCode("0000");
				responseResult.setMsg("身份识别成功~");
				responseResult.setResult(ocrIdcard);
			} else {
				responseResult.setCode("9999");
				responseResult.setMsg("身份识别失败~");
				responseResult.setResult(ocrIdcard);
			}
		} catch (IllegalArgumentException e) {
			logger.error("OCR识别失败{}",e.getMessage());
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("OCR识别异常{}",e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg("身份识别失败，请稍后再试~");
		}
		return responseResult;
	}
	
//	/**
//	 * 身份验证
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/verifyIdcard.do")
//	public AppResponseResult verifyIdcard(HttpServletRequest request,HttpServletResponse response) {
//		AppResponseResult responseResult = new AppResponseResult();
//		
//		return responseResult;
//	}
	
	/**
	 * 人脸识别/活体检测
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/veriface.do")
	public AppResponseResult verifyFace(HttpServletRequest request,HttpServletResponse response) {
		AppResponseResult responseResult = new AppResponseResult();
		
		String sessionId = DateUtils.getDateHMToString();
		String userId = request.getRequestedSessionId();
		String idcardName = request.getParameter("idcard_name");
		String idcardNum = request.getParameter("idcard_num");
		String faceImg = request.getParameter("face_img");
		String delta = request.getParameter("delta");
		
		try {
			Assert.hasText(idcardName,"缺少参数[idcard_name]字段不能为空~");
			Assert.hasText(idcardNum, "缺少参数[idcard_num]字段不能为空~");
			Assert.hasText(faceImg,"缺少参数[face_img]字段不能为空~");
			Assert.hasText(delta,"缺少参数[delta]字段不能为空~");
			
			Verify verify = faceIDService.verifyFace(sessionId, userId, idcardName, idcardNum, faceImg, delta);
			
			float confidence = verify.getResult_faceid().getConfidence();
			//保存记录
			boolean flag = faceIDService.saveVerifyFaceInfo(idcardName,idcardNum,confidence);
			
			if(flag) {
				responseResult.setCode("0000");
				responseResult.setMsg("活体检测成功~");
				responseResult.setResult(verify);
			} else {
				responseResult.setCode("9999");
				responseResult.setMsg("活体检测失败~");
				responseResult.setResult(verify);
			}
		} catch (IllegalArgumentException e) {
			logger.error("verifyFace识别失败{}",e.getMessage());
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("verifyFace识别异常{}",e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg("活体检测失败，请稍后再试~");
		}
		
		return responseResult;
	}

}
