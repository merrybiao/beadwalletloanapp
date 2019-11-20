package com.waterelephant.linkface.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.authentication.annotation.ApiCheckSign;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.linkface.service.SenseTimeService;
import com.waterelephant.service.BwIdentityLivenessRecordService;
import com.waterelephant.service.BwOcrIdcardRecordService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.ProductType;

/**
 * 商汤-OCR识别、活体检测
 * @author dinglinhao
 * @date 2018年8月30日15:55:47
 */
@Controller
@RequestMapping("/senseTime")
public class SenseTimeH5Controller {
	
	private Logger logger = LoggerFactory.getLogger(SenseTimeH5Controller.class);
	
	@Autowired
	private SenseTimeService senseTimeService;
	
	@Autowired
	private BwOcrIdcardRecordService bwOcrIdcardRecordService;
	
	@Autowired
	private BwIdentityLivenessRecordService bwIdentityLivenessRecordService;
	
	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/ocr/idcard_front_h5.do")
	public AppResponseResult ocrIdcardFront(@RequestBody JSONObject params) {
		AppResponseResult result = new AppResponseResult();
		String side = "front";
		String productNo = params.getString("productNo");
		String imageData = params.getString("imageData");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(imageData,"缺少参数，[imageData]不能为空~");
//			resultMap = senseTimeService.ocrIdcard(null,imageData, side);
//			senseTimeService.saveIdentityCardInfo(resultMap);
			result.setCode("0000");
			result.setMsg("请求成功~");
			result.setResult(resultMap);
		} catch (IllegalArgumentException | BusinessException e) {
			result.setCode("600");
			result.setMsg(e.getMessage());
		} catch (Exception e) {
			result.setCode("9999");
			result.setMsg("请求接口失败，请稍后再试~");
			logger.error("----【商汤OCR】 身份证识别异常：{}",e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				bwOcrIdcardRecordService.saveFront(productNo, "2", resultMap,result,null,imageData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/ocr/idcard_back_h5.do")
	public AppResponseResult ocrIdcardBack(@RequestBody JSONObject params) {
		AppResponseResult result = new AppResponseResult();
		String side = "back";
		String productNo = params.getString("productNo");
		String imageData = params.getString("imageData");
		String idcardNumber = params.getString("idcardNum");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(idcardNumber, "缺失参数，字段[idcardNum]不能为空~");
			Assert.hasText(imageData,"缺少参数，[imageData]不能为空~");
//			resultMap = senseTimeService.ocrIdcard(null,imageData, side);
//			resultMap.put("idcardNumber", idcardNumber);
//			senseTimeService.saveIdentityCardInfo(resultMap);
			result.setCode("0000");
			result.setMsg("请求成功~");
			result.setResult(resultMap);
		} catch (IllegalArgumentException | BusinessException e) {
			result.setCode("600");
			result.setMsg(e.getMessage());
		} catch (Exception e) {
			result.setCode("9999");
			result.setMsg("请求接口失败，请稍后再试~");
			logger.error("----【商汤OCR】 身份证识别异常：{}",e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				bwOcrIdcardRecordService.saveBack(productNo, "2", idcardNumber,resultMap,result,null,imageData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/ocr/bankcard_h5.do")
	public AppResponseResult ocrBankcard(@RequestBody JSONObject params) {
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String imageData = params.getString("imageData");
		String idcardNumber = params.getString("idcardNum");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(idcardNumber, "缺失参数，字段[idcardNum]不能为空~");
			Assert.hasText(imageData,"缺少参数，[imageData]不能为空~");
//			resultMap = senseTimeService.ocrBankcard(null,imageData);
//			senseTimeService.saveIdentityCardInfo(resultMap);
			result.setCode("0000");
			result.setMsg("请求成功~");
			result.setResult(resultMap);
		} catch (IllegalArgumentException | BusinessException e) {
			result.setCode("600");
			result.setMsg(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			result.setCode("9999");
			result.setMsg("请求接口失败，请稍后再试~");
			logger.error("----【商汤OCR】 银行卡识别异常：{}",e.getMessage());
			e.printStackTrace();
		} finally {
			try {
//				bwOcrIdcardRecordService.saveBack(productNo, "2", idcardNumber,resultMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/image/verification_h5.do")
	public AppResponseResult imageVerification(@RequestBody JSONObject params) {
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String firstImgData  = params.getString("firstImgData");
		String secondImgData = params.getString("secondImgData");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(firstImgData,"缺少参数[firstImgData]不能为空~");
			Assert.hasText(secondImgData,"缺少参数[secondImgData]不能为空~");
			
			
//			resultMap = senseTimeService.imageVerification(firstImgData, secondImgData);
			result.setCode("0000");
			result.setMsg("请求成功~");
			result.setResult(resultMap);
		} catch (IllegalArgumentException | BusinessException e) {
			result.setCode("600");
			result.setMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("----【商汤图片对比】接口异常：{}",e.getMessage());
			result.setCode("9999");
			result.setMsg("请求接口失败，请稍后再试~");
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
	
	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/identity/idnumber_verification_h5.do")
	public AppResponseResult idnumberVerification(@RequestBody JSONObject params) {
		//用姓名、身份证号到第三方核查后台查询预留照片，然后与存在云端的的人脸照片进行比对，来判断是否为同一个人。
		//https://v2-auth-api.visioncloudapi.com/identity/idnumber_verification
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String name = params.getString("name");
		String idcardNumber = params.getString("idcardNum");
		String imgData = params.getString("imageData");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(name,"缺少参数[name]不能为空~");
			Assert.hasText(idcardNumber,"缺少参数[idcardNum]不能为空~");
			Assert.hasText(imgData,"缺少参数[imageData]不能为空~");
//			resultMap = senseTimeService.idnumberVerification(name, idcardNumber, null, imgData);
			result.setCode("0000");
			result.setMsg("请求成功~");
			result.setResult(resultMap);
		} catch (IllegalArgumentException | BusinessException e) {
			result.setCode("600");
			result.setMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("----【商汤图片对比】接口异常：{}",e.getMessage());
			result.setCode("9999");
			result.setMsg("请求接口失败，请稍后再试~");
			e.printStackTrace();
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord(productNo, "2", idcardNumber, name, null, imgData, (resultMap == null || resultMap.isEmpty()) ? result:resultMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
