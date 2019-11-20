package com.waterelephant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.service.LivenessVerificationService;
import com.waterelephant.utils.AppResponseResult;
/**
 * <p>活体检测
 * <p>活体检测来源（商汤，face++）
 * <p>目前用商汤的源
 * @author dinglinhao
 * @date 2018年11月29日10:51:29
 * @version 1.0
 * @since 1.8
 *
 */
@RestController
@RequestMapping("/liveness")
public class LivenessVerificationController {
	
	private Logger logger = LoggerFactory.getLogger(LivenessVerificationController.class);
	
	@Autowired
	private LivenessVerificationService livenessVerificationService;
	
	/**
	 * 根据工单号做活体检测
	 * //1、ocr识别
	 * 2、活体照比对
	 * @return 
	 */
	@RequestMapping("/verification_order.do")
	public AppResponseResult livenessVerificationByOrder(@RequestBody JSONObject params){
		AppResponseResult response = new AppResponseResult();
		long start = System.currentTimeMillis();
		String orderNo = params.getString("orderNo");
		
		try {
			Assert.hasText(orderNo,"参数错误，参数[orderNo]字段不能为空~");
			
			int index = orderNo.indexOf("_");
			boolean flag = false;
 			if(index > 0) {
 				String prefix = orderNo.substring(0, index+1).trim();
 				String content = orderNo.substring(index+1).trim();
 				
 				Assert.hasText(prefix, "传入参数searchId不正确~");
 				Assert.hasText(content, "传入参数searchId不正确~");
 				
 				//授信单
 				if(prefix.equalsIgnoreCase("SXD_")) {
 					String creditNo = content;
 					flag = livenessVerificationService.savelivenessVerificationBycreditNo(creditNo);
 				} 
 			}else {
 				
 				//活体检测
 				flag = livenessVerificationService.savelivenessVerificationByOrder(orderNo);
 			}
			
			response.setCode("0000");
			response.setMsg("请求接口成功~");
			response.setResult(flag);
			
		} catch(IllegalArgumentException | BusinessException e) {
			response.setCode("600");
			response.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMsg("请求接口失败，请稍后再试~");
			logger.error("---活体检测--根据工单号orderNo:{}进行活体检测异常:{}----",orderNo,e.getMessage());
		} finally {
			long end = System.currentTimeMillis();
			logger.info("---请求{}接口orderNo:{},耗时：{}ms--","/liveness/verification_order.do",orderNo,(end-start));
		}
		return response;
	}

}
