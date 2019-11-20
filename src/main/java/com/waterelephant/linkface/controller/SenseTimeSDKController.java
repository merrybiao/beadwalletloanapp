package com.waterelephant.linkface.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
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
public class SenseTimeSDKController {
	
	private Logger logger = LoggerFactory.getLogger(SenseTimeSDKController.class);
	
	@Autowired
	private SenseTimeService senseTimeService;
	
	@Autowired
	private BwOcrIdcardRecordService bwOcrIdcardRecordService;
	
	@Autowired
	private BwIdentityLivenessRecordService bwIdentityLivenessRecordService;
	
//	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/ocr/idcard_front.do")
	public AppResponseResult ocrIdcardFront(@RequestBody JSONObject params) {
		AppResponseResult result = new AppResponseResult();
		String side = "front";
		String productNo = params.getString("productNo");
		String httpImgUrl = params.getString("imgUrl");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(httpImgUrl,"缺少参数，[imgUrl]不能为空~");
//			resultMap = senseTimeService.ocrIdcard(httpImgUrl,null, side);
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
				bwOcrIdcardRecordService.saveFront(productNo, "2", resultMap,result,httpImgUrl,null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
//	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/ocr/idcard_back.do")
	public AppResponseResult ocrIdcardBack(@RequestBody JSONObject params) {
		
		logger.info("----【商汤】ocr身份证反面识别接口：{}参数：{}--","/ocr/idcard_back.do",params.toJSONString());
		AppResponseResult result = new AppResponseResult();
		String side = "back";
		String productNo = params.getString("productNo");
		String httpImgUrl = params.getString("imgUrl");
		String idcardNumber = params.getString("idcardNum");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(idcardNumber, "缺失参数，字段[idcardNum]不能为空~");
			Assert.hasText(httpImgUrl,"缺少参数，[imgUrl]不能为空~");
//			resultMap = senseTimeService.ocrIdcard(httpImgUrl,null, side);
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
				bwOcrIdcardRecordService.saveBack(productNo, "2", idcardNumber,resultMap,result,httpImgUrl,null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
//	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/ocr/bankcard.do")
	public AppResponseResult ocrBankcard(@RequestBody JSONObject params) {
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String httpImgUrl = params.getString("imgUrl");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(httpImgUrl,"缺少参数，[imgUrl]不能为空~");
//			resultMap = senseTimeService.ocrBankcard(httpImgUrl,null);
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
			logger.error("----【商汤OCR】 身份证识别异常：{}",e.getMessage());
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
	
//	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/image/verification.do")
	public AppResponseResult imageVerification(@RequestBody JSONObject params) {
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String firstImgUrl  = params.getString("firstImgUrl");
		String secondImgUrl = params.getString("secondImgUrl");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(firstImgUrl,"缺少参数[firstImgUrl]不能为空~");
			Assert.hasText(secondImgUrl,"缺少参数[secondImgUrl]不能为空~");
//			String firstImg = ImageUtils.httpImageToBaseB4(firstImgUrl);
//			String secondImg = ImageUtils.httpImageToBaseB4(secondImgUrl);
//			Map<String,Object> resultMap = senseTimeService.imageVerification(firstImg, secondImg);
//			resultMap = senseTimeService.imageVerificationUrl(firstImgUrl, secondImgUrl);
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
				bwIdentityLivenessRecordService.saveLivenessRecord(productNo, "2", null, null, firstImgUrl+","+secondImgUrl, null, (resultMap == null || resultMap.isEmpty()) ? result:resultMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
//	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/identity/liveness_idnumber_verification.do")
	public AppResponseResult livenessIdnumberVerification(@RequestBody JSONObject params) {
		//用姓名、身份证号到第三方核查后台查询预留照片，然后与存在云端的的人脸照片进行比对，来判断是否为同一个人。
		//https://v2-auth-api.visioncloudapi.com/identity/idnumber_verification
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String name = params.getString("name");
		String idcardNumber = params.getString("idcardNum");
		String livenessUrl = params.getString("livenessUrl");
		String livenessData = params.getString("livenessData");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(name,"缺少参数[name]不能为空~");
			Assert.hasText(idcardNumber,"缺少参数[idcardNum]不能为空~");
			if(StringUtils.isEmpty(livenessUrl) && StringUtils.isEmpty(livenessData)) 
				throw new IllegalArgumentException("缺少参数,字段[livenessUrl]或[livenessData]不能为空~");
//			resultMap = senseTimeService.livenessIdnumberVerification(name, idcardNumber, livenessUrl, livenessData);
			result.setCode("0000");
			result.setMsg("请求成功~");
			result.setResult(resultMap);
		} catch (IllegalArgumentException | BusinessException e) {
			result.setCode("600");
			result.setMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("----【商汤活体对比】接口异常：{}",e.getMessage());
			result.setCode("9999");
			result.setMsg("请求接口失败，请稍后再试~");
			e.printStackTrace();
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord(productNo, "2", idcardNumber, name, livenessUrl, null, (resultMap == null || resultMap.isEmpty()) ? result:resultMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
//	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/identity/liveness_yun_idnumber_verification.do")
	public AppResponseResult livenessUrlIdnumberVerification(@RequestBody JSONObject params) {
		//用姓名、身份证号到第三方核查后台查询预留照片，然后与存在云端的的人脸照片进行比对，来判断是否为同一个人。
		//https://v2-auth-api.visioncloudapi.com/identity/idnumber_verification
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String name = params.getString("name");
		String idcardNumber = params.getString("idcardNum");
		String livenessUrl = params.getString("livenessUrl");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(name,"缺少参数[name]不能为空~");
			Assert.hasText(idcardNumber,"缺少参数[idcardNum]不能为空~");
			Assert.hasText(livenessUrl,"缺少参数[livenessUrl]不能为空~");
//			resultMap = senseTimeService.livenessIdnumberVerification(name, idcardNumber, livenessUrl, null);
			result.setCode("0000");
			result.setMsg("请求成功~");
			result.setResult(resultMap);
		} catch (IllegalArgumentException | BusinessException e) {
			result.setCode("600");
			result.setMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("----【商汤活体对比】接口异常：{}",e.getMessage());
			result.setCode("9999");
			result.setMsg("请求接口失败，请稍后再试~");
			e.printStackTrace();
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord(productNo, "2", idcardNumber, name, livenessUrl, null, (resultMap == null || resultMap.isEmpty()) ? result:resultMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/identity/idnumber_verification_channel.do")
	public AppResponseResult idnumberVerification(@RequestBody JSONObject params) {
		//用姓名、身份证号到第三方核查后台查询预留照片，然后与存在云端的的人脸照片进行比对，来判断是否为同一个人。
		//https://v2-auth-api.visioncloudapi.com/identity/idnumber_verification
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String name = params.getString("name");
		String idcardNumber = params.getString("idcardNum");
		String imageUrl = params.getString("imageUrl");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(name,"缺少参数[name]不能为空~");
			Assert.hasText(idcardNumber,"缺少参数[idcardNum]不能为空~");
			Assert.hasText(imageUrl,"缺少参数[imageUrl]不能为空~");
//			resultMap = senseTimeService.idnumberVerification(name, idcardNumber, imageUrl, null);
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
				bwIdentityLivenessRecordService.saveLivenessRecord(productNo, "2", idcardNumber, name, imageUrl, null, (resultMap == null || resultMap.isEmpty()) ? result:resultMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/testSign.do")
	public AppResponseResult testSign(@RequestBody JSONObject params){
		AppResponseResult result = new AppResponseResult();
		logger.info("----华丽的分割线----{}---",params.toJSONString());
		result.setCode("0000");
		result.setMsg("OK");
		result.setResult(params);
		return result;
	}
	
	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/test_sign.do")
	public AppResponseResult testSignV2(HttpServletRequest request,HttpServletResponse response){
		AppResponseResult result = new AppResponseResult();
		logger.info("----华丽的分割线----{}---",request.getParameterMap().toString());
		result.setCode("0000");
		result.setMsg("OK");
		result.setResult(request.getParameterMap());
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/image/liveness_verification_channel.do")
	public AppResponseResult livenessVerificationChannel(@RequestBody JSONObject params) {
		AppResponseResult result = new AppResponseResult();
		String productNo = params.getString("productNo");
		String firstImgUrl  = params.getString("firstImgUrl");
		String secondImgUrl = params.getString("secondImgUrl");
		Map<String,Object> resultMap = null;
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(firstImgUrl,"缺少参数[firstImgUrl]不能为空~");
			Assert.hasText(secondImgUrl,"缺少参数[secondImgUrl]不能为空~");
//			String firstImg = ImageUtils.httpImageToBaseB4(firstImgUrl);
//			String secondImg = ImageUtils.httpImageToBaseB4(secondImgUrl);
//			Map<String,Object> resultMap = senseTimeService.imageVerification(firstImg, secondImg);
//			resultMap = senseTimeService.imageVerificationUrl(firstImgUrl, secondImgUrl);
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
				bwIdentityLivenessRecordService.saveLivenessRecord(productNo, "2", null, null, firstImgUrl+","+secondImgUrl, null, (resultMap == null || resultMap.isEmpty()) ? result:resultMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
