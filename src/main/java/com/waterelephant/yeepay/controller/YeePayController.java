//package com.waterelephant.yeepay.controller;
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.service.baiqishi.entity.ServiceResult91;
//import com.beadwallet.service.yeepay.entity.YeePayBatchdetail;
//import com.beadwallet.service.yeepay.service.YeePaySDK;
//import com.waterelephant.dto.RepaymentDto;
//import com.waterelephant.entity.BwCapitalWithhold;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.faceID.utils.DateUtils;
//import com.waterelephant.service.BwCapitalWithholdService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwRepaymentService;
//import com.waterelephant.utils.*;
//import com.waterelephant.yeepay.JsonEntity.YeePayAsyncPayReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePayAsyncPaySingle;
//import com.waterelephant.yeepay.JsonEntity.YeePayAsyncRefundReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePayDetailsResult;
//import com.waterelephant.yeepay.JsonEntity.YeePayDetailsReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePayPayBill;
//import com.waterelephant.yeepay.JsonEntity.YeePayQueryResult;
//import com.waterelephant.yeepay.JsonEntity.YeePayQueryReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePayQuerySinglePayReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePayRefundBill;
//import com.waterelephant.yeepay.JsonEntity.YeePayRefundDetailsResult;
//import com.waterelephant.yeepay.JsonEntity.YeePayRefundDetailsReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePayRefundQueryResult;
//import com.waterelephant.yeepay.JsonEntity.YeePayRefundQueryReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePayRefundResult;
//import com.waterelephant.yeepay.JsonEntity.YeePayRefundReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePayResp;
//import com.waterelephant.yeepay.JsonEntity.YeePayResult;
//import com.waterelephant.yeepay.JsonEntity.YeePayReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePaySingleCharge;
//import com.waterelephant.yeepay.JsonEntity.YeePayAsyncSinglePayResult;
//import com.waterelephant.yeepay.JsonEntity.YeePaySinglePayReturn;
//import com.waterelephant.yeepay.JsonEntity.YeePaySyncSinglePayResult;
//import com.waterelephant.yeepay.entity.YeepayBatchCharge;
//import com.waterelephant.yeepay.entity.YeepayBatchDetail;
//import com.waterelephant.yeepay.entity.YeepayRefundDetail;
//import com.waterelephant.yeepay.service.YeepayBatchChargeService;
//import com.waterelephant.yeepay.service.YeepayBatchDetailService;
//import com.waterelephant.yeepay.service.YeepayRefundDetailService;
//import com.waterelephant.yeepay.utils.IdGenerator;
//import com.yeepay.g3.facade.yop.ca.dto.DigitalEnvelopeDTO;
//import com.yeepay.g3.facade.yop.ca.enums.CertTypeEnum;
//import com.yeepay.g3.frame.yop.ca.DigitalEnvelopeUtils;
//import com.yeepay.g3.sdk.yop.utils.InternalConfig;
//
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.math.BigDecimal;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author GuoKun
// * @version 1.0
// * @create_date 2017/6/20 10:24
// */
//@Controller
//@RequestMapping("/yeePay")
//public class YeePayController {
//    private Logger logger = Logger.getLogger(YeePayController.class);
//    private static String AVALIABLETIME = "120";	//设置有效期为两个小时（120分钟）
//    private static String PRODUCTNAME = "waterelephantrepayment";
//
//    @Autowired
//    private YeepayBatchChargeService yeepayBatchChargeService;
//    @Autowired
//    private YeepayBatchDetailService yeepayBatchDetailService;
//    @Autowired
//    private YeepayRefundDetailService yeepayRefundDetailService;
//    @Autowired
//	private BwCapitalWithholdService bwCapitalWithholdService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private IBwRepaymentService bwRepaymentService;
//    
//    
//    /**
//     * 批量扣款接口
//     * 注意传入参数的格式类似[{"amount":"2.00","requestNo":"haha"},{"amount":"2.00","requestNo":"haha"}]
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/batchCharge.do")
//    public YeePayResp batchCharge(HttpServletRequest request,HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//		String methodName = "YeePayController.batchCharge";
//		logger.info(sessionId+":"+methodName+"start");
//		YeePayResp yeePayResp = new YeePayResp();
//		
//		//获取商户批次号（商户范围内唯一，不能重复）
//		String merchantBatchNo = IdGenerator.INSTANCE.nextId();
//		logger.info(sessionId+methodName+"商户批次号为："+merchantBatchNo);
//    	
//    	//获得扣款请求列表
//    	String  batchdetailes= request.getParameter("batchdetailes");
//    	logger.info(sessionId+methodName+"批量扣款明细信息batchdetailes："+batchdetailes);
//    	
//    	if(CommUtils.isNull(batchdetailes)){
//    		yeePayResp.setCode("100");
//         	yeePayResp.setMessage("请求参数为空");
//         	return yeePayResp;
//    	}
//    	List<YeepayBatchDetail> list = new ArrayList<>();
//    	
//    	
//    	try{
//    		list =  JSON.parseArray(batchdetailes,YeepayBatchDetail.class);
//    	}catch(Exception e){
//    		logger.error(sessionId+methodName+"参数格式不正确",e);
//    		yeePayResp.setCode("101");
//         	yeePayResp.setMessage("请求参数格式不正确");
//         	return yeePayResp;
//    	}
//    	
//    	if(CommUtils.isNull(list)){
//    		yeePayResp.setCode("101");
//         	yeePayResp.setMessage("请求参数格式不正确");
//         	return yeePayResp;
//    	}
//    	
//    	//将请求参数转换成SDK要求的格式
//    	List<YeePayBatchdetail> yeePayBatchdetailList = new ArrayList<>();
//    	
//    	//将请求中的借款人ID赋值与协议号，以及生成请求号，转化成SDK中的参数类型
//    	for (YeepayBatchDetail yeepayBatchDetail : list) {
//    		
//    		Long borrowerId = yeepayBatchDetail.getBorrowerId();
//    		if(!CommUtils.isNull(borrowerId)){
//    			yeepayBatchDetail.setBathprotocolnNo(borrowerId.toString());
//    		}
//    		String requestno = IdGenerator.INSTANCE.nextId();
//        	requestno = "Q"+ requestno;
//    		yeepayBatchDetail.setRequestNo(requestno);
//    		YeePayBatchdetail detail = changeToYeePayBatchdetail(yeepayBatchDetail);
//    		yeePayBatchdetailList.add(detail);
//    	}
//    	
//    	
//    	logger.info(sessionId+methodName+"扣款详细信息为："+JsonUtils.toJson(yeePayBatchdetailList));
//    	
//    	logger.info(sessionId+methodName+"开始调用SDK中的扣款方法");
//    	
//    	ServiceResult91 result91 = YeePaySDK.decision(merchantBatchNo, yeePayBatchdetailList);
//    	
//    	//对返回的json串进行非空判断，若为空则请求参数存在错误，返回相应的错误信息
//    	 if(CommUtils.isNull(result91.getObj())){
//         	yeePayResp.setCode(result91.getRequestCode());
//         	yeePayResp.setMessage(result91.getRequestMsg());
//         	return yeePayResp;
//         }
//    	 //得到同步返回的json串，将其转化为YeePayReturn对象
//    	 String json = (String) result91.getObj();
//    	 YeePayReturn yeePayReturn = new YeePayReturn();
//    	try {
//    		yeePayReturn  = JSON.parseObject(json,YeePayReturn.class );
//			
//		} catch (Exception e) {
//			logger.error(sessionId+methodName+"json转换异常", e);
//			yeePayResp.setCode("110");
//	        yeePayResp.setMessage("易宝支付返回数据异常");
//	        return yeePayResp;
//		}
//    	 
//    	 
//    	 if(CommUtils.isNull(yeePayReturn)){
//    		 	logger.info(sessionId+methodName+"返回结果转换成YeePayReturn对象为空");
//    			yeePayResp.setCode("110");
//    	        yeePayResp.setMessage("易宝支付返回数据异常");
//    	        return yeePayResp;
//    	 }
//    	 //首先对返回的error做判断
//    	 if(!CommUtils.isNull(yeePayReturn.getError())){
//    		 logger.info(sessionId+methodName+"易宝批扣接口调用失败");
//    		 yeePayResp.setCode("110");
// 	         yeePayResp.setMessage(yeePayReturn.getError());
// 	         return yeePayResp;
//    	 }
//    	//对yeePayReturn做判断
//    	 if("FAILURE".equals(yeePayReturn.getState())){
//    			yeePayResp.setCode("1201");
//    			yeePayResp.setMessage(yeePayReturn.getError());
//    			return yeePayResp;
//    	 }
//    	//请求成功
//    	yeePayResp.setCode("200");
//    	yeePayResp.setMessage("请求成功");
//    	yeePayResp.setObj(yeePayReturn.getResult());
//    	logger.info(sessionId+methodName+"yeePayResp返回结果为："+JsonUtils.toJson(yeePayResp));
//    	//进行数据的持久化操作
//    	
//    	try{
//    		//将扣款批次信息返回数据存储到bw_yeepay_batch_charge表中
//    		YeePayResult yeePayResult  = yeePayReturn.getResult();
//    		//将返回数据转化成YeepayBatchCharge类型存入数据库
//    		YeepayBatchCharge yeepayBatchCharge  = toYeepayBatchCharge(yeePayResult);
//    		logger.info(sessionId+methodName+"保存该批次的扣款记录");
//    		yeepayBatchChargeService.saveYeepayBatchCharge(yeepayBatchCharge);
//    		logger.info(sessionId+methodName+"保存该批次的扣款记录成功");
//    		
//    		//将批次的详细信息存入数据库bw_yeepay_batch_detail表中
//    		
//    		logger.info(sessionId+methodName+"保存该批次的扣款记录详情");
//    		for (YeepayBatchDetail yeepayBatchDetail : list) {
//    			yeepayBatchDetail.setAvaliableTime(AVALIABLETIME);//有效时间
//    			yeepayBatchDetail.setProductName(PRODUCTNAME);//产品名称
//				yeepayBatchDetail.setYeepay_type(1);
//    			yeepayBatchDetail.setMerchantBatchNo(merchantBatchNo);//商户批次号：是一个一对多关系
//    			yeepayBatchDetail.setStatus(yeepayBatchCharge.getStatus());//这里有两个状态：1.受理中PROCESSING，2接收失败ACCEPT_FAIL
//    			
//    			if(!CommUtils.isNull(yeepayBatchCharge.getErrorMsg())){
//    				logger.info(sessionId+methodName+"批次扣款出错，所有的扣款详情错误信息和批次信息一致");
//    				yeepayBatchDetail.setErrorCode(yeepayBatchCharge.getErrorCode());
//    				yeepayBatchDetail.setErrorMsg(yeepayBatchCharge.getErrorMsg());
//    			}
//    			
//    			
//    			yeepayBatchDetailService.saveYeepayBatchDetail(yeepayBatchDetail);
//    			logger.info(sessionId+methodName+"保存该批次的扣款记录详情成功");
//    			
//    			//将数据保存到bw_capital_withhold表中
//    			BwCapitalWithhold bwCapitalWithhold = getBwCapitalWithholdByYeepayBatchDetail(yeepayBatchDetail);
//    			
//    			bwCapitalWithholdService.save(bwCapitalWithhold);
//    		
//    			
//    			
//    			if("ACCEPT_FAIL".equals(yeepayBatchCharge.getStatus())){
//    				//TODO 表示该笔扣款请求接收失败(将不会接收到异步通知，也无法使用批扣查询接口查询到该笔扣款)，但是本地数据库中有保存该请求的信息
//    			}
//    			
//    			
//    		}
//    	}catch(Exception e){
//    		logger.error(sessionId+methodName+"数据库数据插入异常", e);
//    		return yeePayResp;
//    	}
//		
//    	return yeePayResp;
//         
//    }
//    
//    /**
//     * 批量扣款易宝回调接口，当批次总单状态变化时，对bw_yeepay_batch_charge表中的状态做改变
//     * @param request
//     * @param response
//     */
//  
//    @ResponseBody
//    @RequestMapping("/batchChargeCallBack.do")
//    public void batchChargeCallBack(HttpServletRequest request, HttpServletResponse response) {
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.batchChargeCallBack";
//    	logger.info(sessionId+":"+methodName+"start");
//    	try {
//    		request.setCharacterEncoding("utf-8");
//    		String responseMesg = request.getParameter("response");
//    		//代理商商户编号
//    		String customerIdentification = request.getParameter("customerIdentification");
//    		response.setStatus(200);
//    		response.getWriter().append("SUCCESS");
//    		
//    		logger.info(sessionId+":"+methodName+"代理商商户编号customerIdentification:" + customerIdentification);
//    		
//    		
//    		DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
//    		dto.setCipherText(responseMesg);
//   
//    		InternalConfig internalConfig = InternalConfig.Factory.getInternalConfig();
//    		PrivateKey privateKey = internalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
//    		PublicKey publicKey = internalConfig.getYopPublicKey(CertTypeEnum.RSA2048);
//    		dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
//    		String plainText = dto.getPlainText();
//    		logger.info(sessionId+":"+methodName+"解密后的明文为："+plainText);
//    		
//    		YeePayAsyncPayReturn yeePayAsyncPayReturn= JSON.parseObject(plainText, YeePayAsyncPayReturn.class);
//    		//做数据库数据更新.该方法只会调用一次。在异步通知时执行
//    		//更新bw_yeepay_batch_charge表中的总状态
//    		String merchantBatchNo = yeePayAsyncPayReturn.getMerchantbatchno();
//    		String status = yeePayAsyncPayReturn.getStatus();
//    		String errorCode = yeePayAsyncPayReturn.getErrorcode();
//    		String errorMsg = yeePayAsyncPayReturn.getErrormsg();
//    		YeepayBatchCharge yeepayBatchCharge = yeepayBatchChargeService.findByMerchantBatchNo(merchantBatchNo);
//    		yeepayBatchCharge.setStatus(status);
//    		yeepayBatchCharge.setErrorCode(errorCode);
//    		yeepayBatchCharge.setErrorMsg(errorMsg);
//    		yeepayBatchChargeService.updateYeepayBatchCharge(yeepayBatchCharge);
//    		
//    		//更新bw_yeepay_batch_detail表中的每笔订单的状态
//    		String json = yeePayAsyncPayReturn.getResultdetails();
//    		List<YeePayAsyncPaySingle> singleList = JSON.parseArray(json, YeePayAsyncPaySingle.class);
//    		logger.info(sessionId+":"+methodName+":转化的到的singleList:"+JSON.toJSONString(singleList));
//    		logger.info(sessionId+":"+methodName+"开始改变数据库中的状态");
//    		 
//    		YeepayBatchDetailUseCallBack(singleList, merchantBatchNo);
//    		//数据状态的更改
//    	} catch (Exception e) {
//    		e.printStackTrace();
//    		logger.error(sessionId+":"+methodName+"异步返回参数异常",e);
//    	}
//    }
//    
//    /**
//     * 批量扣款查询接口
//     * 这个接口中要获得易宝支付单笔扣款的流水号存入数据库
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("batchChargeQuery.do")
//    public YeePayResp batchChargeQuery(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.batchChargeQuery";
//    	YeePayResp yeePayResp = new YeePayResp();
//    	logger.info(sessionId+":"+methodName+"start");
//    	
//    	//获取请求参数
//    	String merchantbatchno = request.getParameter("merchantbatchno");
//    	logger.info(sessionId+":"+methodName+":商户批次号:"+merchantbatchno);
//    	String ybbatchno = request.getParameter("ybbatchno");
//    	logger.info(sessionId+":"+methodName+":易宝批次号:"+ybbatchno);
//    	
//    	logger.info(sessionId+methodName+"开始调用SDK中的批量扣款查询方法");
//    	
//    	ServiceResult91 result91 = YeePaySDK.queryDecision(merchantbatchno, ybbatchno);
//    	
//    	logger.info(sessionId+methodName+"调用SDK中的批量扣款查询方法结束");
//    	
//    	
//    	if(CommUtils.isNull(result91.getObj())){
//         	yeePayResp.setCode(result91.getRequestCode());
//         	yeePayResp.setMessage(result91.getRequestMsg());
//         	return yeePayResp;
//         }
//    	
//    	//得到同步返回的json串，将其转化为对象YeePayQueryReturn
//    	 String json = (String) result91.getObj();
//    	 
//    	 logger.info(sessionId+methodName+"返回的json串为："+json);
//    	 YeePayQueryReturn yeePayQueryReturn = new YeePayQueryReturn();
//    	 try {
//    		  yeePayQueryReturn = JSON.parseObject(json, YeePayQueryReturn.class);
//			
//		} catch (Exception e) {
//			logger.info(sessionId+methodName+"json转对象异常", e);
//		}
//    	
//    	 if(CommUtils.isNull(yeePayQueryReturn)){
//    		yeePayResp.setCode("100");
//          	yeePayResp.setMessage("易宝返回数据为空");
//          	return yeePayResp; 
//    	 }
//        	
//    	logger.info(sessionId+":"+methodName+":查询结果为:"+JSON.toJSONString(yeePayQueryReturn));
//        	 
//        //对请求状态进行判断
//        String state = yeePayQueryReturn.getState();
//        if("FAILURE".equals(state)){
//        	yeePayResp.setCode("110");
//        	yeePayResp.setMessage(yeePayQueryReturn.getError());
//        	logger.info(sessionId+":"+methodName+":批量扣款查询异常:"+yeePayResp.getMessage());
//        	return yeePayResp;
//        }
//        	 
//        YeePayQueryResult yeePayQueryResult = yeePayQueryReturn.getResult();
//        	 
//        logger.info(sessionId+":"+methodName+":批次信息为:"+JSON.toJSONString(yeePayQueryResult));
//        //获得批次的处理状态
//        	 
//        String status = yeePayQueryResult.getStatus();
//        //首先进行空判断
//        if(CommUtils.isNull(status)){
//        	yeePayResp.setCode("120");
//        	yeePayResp.setMessage(yeePayQueryResult.getErrormsg());
//        	logger.info(sessionId+":"+methodName+yeePayQueryResult.getErrormsg());
//        	return yeePayResp;
//        }
//        	 
//        	 
//        if("PROCESSING".equals(status)){
//        	yeePayResp.setCode("120");
//        	yeePayResp.setMessage("订单还在处理中");
//        	logger.info(sessionId+":"+methodName+"订单还在处理中");
//        	return yeePayResp;
//        }
//        	 
//        if("TIME_OUT".equals(status)){
//        	yeePayResp.setCode("120");
//        	yeePayResp.setMessage("订单处理超时");
//        		 //查询出现超时的话就是在这里做该状态为超时，修改状态
//        	
//        	logger.info(sessionId+":"+methodName+"订单处理超时");
//        	return yeePayResp;
//        }
//        
//        //标识整个扣款订单已经到达终态，可以返回参数，剩下数据库的检查操作
//        yeePayResp.setCode("200");
//        yeePayResp.setMessage("请求成功");
//        yeePayResp.setObj(yeePayQueryResult);
//        
//     
//        
//        //这个地方从结果中获取商户批次号的目的是，因为开始传进来的商户批次号可能为空	 
//        String merchantBatchNo = yeePayQueryResult.getMerchantbatchno();
//     	String errorCode = yeePayQueryResult.getErrorcode();
//     	String errorMsg = yeePayQueryResult.getErrormsg();
//     	
//     	try {
//     		YeepayBatchCharge yeepayBatchCharge = new YeepayBatchCharge();
//     		yeepayBatchCharge = yeepayBatchChargeService.findByMerchantBatchNo(merchantBatchNo);
//     		//进行空指针判断，按道理是不会进入这个if的，但是前面的测试也会导致这个数据未记录到数据库中，那这里需要将这个数据补录到我们的数据库中吗？
//     		if(CommUtils.isNull(yeepayBatchCharge)){
//     			//将批次总单补入数据库
//     			yeepayBatchCharge = new YeepayBatchCharge();
//     			yeepayBatchCharge.setStatus(status);
//     			yeepayBatchCharge.setErrorCode(errorCode);
//     			yeepayBatchCharge.setErrorMsg(errorMsg);
//     			yeepayBatchCharge.setMerchantBatchNo(merchantBatchNo);
//     			yeepayBatchCharge.setYbBatchNo(ybbatchno);
//     			yeepayBatchChargeService.saveYeepayBatchCharge(yeepayBatchCharge);
//     			List<YeePaySingleCharge> list = yeePayQueryResult.getResultdetails();
//     			YeepayBatchDetailUseQuerry(list, merchantbatchno);
//     			return yeePayResp;
//     		}
//     		//不为空,对数据中保存的状态和现在返回的状态进行比较，如果不一样则进行状态更新
//     		String status2 = yeepayBatchCharge.getStatus();
//     		if(!status.equals(status2)){
//     			yeepayBatchCharge.setStatus(status);
//     			yeepayBatchCharge.setErrorCode(errorCode);
//     			yeepayBatchCharge.setErrorMsg(errorMsg);
//     			yeepayBatchChargeService.updateYeepayBatchCharge(yeepayBatchCharge);
//     			logger.info(sessionId+":"+methodName+"批量总状态更新成功");
//     			//并且对明细做状态更改
//     		}
//     		List<YeePaySingleCharge> list = yeePayQueryResult.getResultdetails();
//     		if(!CommUtils.isNull(list)){
//     			logger.info(sessionId+":"+methodName+"明细的状态为："+JSON.toJSONString(list));
//     			logger.info(sessionId+":"+methodName+"开始更新批量明细的状态");
//     			YeepayBatchDetailUseQuerry(list, merchantbatchno);
//     		}
//			
//		} catch (Exception e) {
//			logger.error(sessionId+":"+methodName+"数据库数据更新出现异常", e);
//			return yeePayResp;
//		}
//        	return yeePayResp;
//    }
//  
//    /**
//     * 退款接口
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("refund.do")
//    public YeePayResp singleRefund(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.singleRefund";
//    	YeePayResp yeePayResp = new YeePayResp();
//    	logger.info(sessionId+":"+methodName+"start");
//    	//获取请求参数
//    	//退款请求号也必须唯一，生成退款请求号
//    	String requestNo = IdGenerator.INSTANCE.nextId();
//    	requestNo = "R"+requestNo;
//    	logger.info(sessionId+":"+methodName+"生成的退款请求号为："+requestNo);
//    	String paymentybOrderId = request.getParameter("paymentyborderid");
//    	String amount = request.getParameter("amount");
//    	String remark = request.getParameter("remark");
//    	
//    	logger.info(sessionId+":"+methodName+":requestno:"+requestNo+",paymentyborderid:"+paymentybOrderId+",amount:"+amount+",remark:"+remark);
//    	
//    	//调用SDK中的退款方法
//    	
//    	ServiceResult91 result91 = YeePaySDK.refund(requestNo, paymentybOrderId, amount, remark);
//    	
//    	if(CommUtils.isNull(result91.getObj())){
//         	yeePayResp.setCode(result91.getRequestCode());
//         	yeePayResp.setMessage(result91.getRequestMsg());
//         	return yeePayResp;
//         }
//    	 String json = (String) result91.getObj();
//    	 //将json串转成对象
//    	 
//    	 YeePayRefundReturn yeePayRefundReturn  = JSON.parseObject(json, YeePayRefundReturn.class);
//    	
//    	 logger.info(sessionId+":"+methodName+":退款的结果为:"+JSON.toJSONString(yeePayRefundReturn));
//    	 
//    	 //对请求状态进行判断
//    	 String state = yeePayRefundReturn.getState();
//    	 if("FAILURE".equals(state)){
//    		 yeePayResp.setCode("110");
//    		 yeePayResp.setMessage(yeePayRefundReturn.getError());
//    		 logger.info(sessionId+":"+methodName+":易宝退款异常:"+yeePayResp.getMessage());
//    		 return yeePayResp;
//    	 }
//    	 YeePayRefundResult yeePayRefundResult = yeePayRefundReturn.getResult();
//    	 if(CommUtils.isNull(yeePayRefundResult)){
//    		 yeePayResp.setCode("110");
//    		 yeePayResp.setMessage("系统异常");
//    		 logger.info(sessionId+":"+methodName+"JSON转yeePayRefundResult类存在问题");
//    		 return yeePayResp;
//    	 }
//    	 
//    	 try{
//    		 YeepayRefundDetail yeepayRefundDetail = toYeepayRefundDetail(yeePayRefundResult);
//    		 logger.info(sessionId+":"+methodName+"退款详情为"+JSON.toJSONString(yeepayRefundDetail));
//    		 yeepayRefundDetail.setPaymentYborderId(paymentybOrderId);
//    		 yeepayRefundDetail.setRemark(remark);
//    		 //存储到数据库中
//    		 yeepayRefundDetailService.saveYeepayRefundDetail(yeepayRefundDetail);
//    	 }catch(Exception e){
//    		 
//    		 logger.error(sessionId+":"+methodName+"存储发生问题"+e);
//    		 //请求成功
//    		 yeePayResp.setCode("200");
//    		 yeePayResp.setMessage("请求成功,数据库存储异常");
//    		 yeePayResp.setObj(yeePayRefundResult);
//    		 return yeePayResp;
//    	 }
//    	 yeePayResp.setCode("200");
//		 yeePayResp.setMessage("请求成功");
//		 yeePayResp.setObj(yeePayRefundResult);
//		 return yeePayResp;
//    }
//
//   
//    /**
//     * 退款异步通知接口
//     * @param request
//     * @param response
//     */
//    @ResponseBody
//    @RequestMapping("refundCallBack.do")
//    public void singleRefundCallBack(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.singleRefundCallBack";
//    	logger.info(sessionId+":"+methodName+"start");
//    	try {
//    		request.setCharacterEncoding("utf-8");
//    		String responseMesg = request.getParameter("response");
//    		//代理商商户编号
//    		String customerIdentification = request.getParameter("customerIdentification");
//    		response.setStatus(200);
//    		response.getWriter().append("SUCCESS");
//    		
//    		logger.info(sessionId+":"+methodName+"代理商商户编号customerIdentification:" + customerIdentification);
//    		
//    		
//    		DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
//    		dto.setCipherText(responseMesg);
//   
//    		InternalConfig internalConfig = InternalConfig.Factory.getInternalConfig();
//    		PrivateKey privateKey = internalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
//    		PublicKey publicKey = internalConfig.getYopPublicKey(CertTypeEnum.RSA2048);
//    		dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
//    		String plainText = dto.getPlainText();
//    		logger.info(sessionId+":"+methodName+"解密后的明文为："+plainText);
//    		YeePayAsyncRefundReturn yeePayAsyncRefundReturn= JSON.parseObject(plainText, YeePayAsyncRefundReturn.class);
//    		//进行数据持久化
//    		String requestNo= yeePayAsyncRefundReturn.getRequestno();
//    		String status = yeePayAsyncRefundReturn.getStatus();
//    		String bankCode = yeePayAsyncRefundReturn.getBankcode();
//    		String cardLast = yeePayAsyncRefundReturn.getCardlast();
//    		String cardTop = yeePayAsyncRefundReturn.getCardtop();
//    		String errorCode = yeePayAsyncRefundReturn.getErrorcode();
//    		String errorMsg = yeePayAsyncRefundReturn.getErrormsg();
//    		YeepayRefundDetail yeepayRefundDetail= yeepayRefundDetailService.findYeepayRefundDetailByRequestNo(requestNo);
//    		yeepayRefundDetail.setBankCode(bankCode);
//    		yeepayRefundDetail.setCardLast(cardLast);
//    		yeepayRefundDetail.setCardTop(cardTop);
//    		yeepayRefundDetail.setErrorCode(errorCode);
//    		yeepayRefundDetail.setErrorMsg(errorMsg);
//    		yeepayRefundDetail.setStatus(status);
//    		yeepayRefundDetailService.updateYeepayRefundDetail(yeepayRefundDetail);
//    		if("REFUND_SUCCESS".equals(status)){
//    			//TODO 退款成功
//    		}else{
//    			//TODO 退款失败
//    			
//    		}
//    		}catch (Exception e) {
//        		e.printStackTrace();
//        		logger.error(sessionId+":"+methodName+"数据库状态修改异常",e);
//        	}
//    }
// 
//    /**
//     * 退款查询接口
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/refundQuery.do")
//    public YeePayResp singleRefundQuery(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.singleRefundQuery";
//    	YeePayResp yeePayResp = new YeePayResp();
//    	logger.info(sessionId+":"+methodName+"start");
//    	//获取请求参数
//    	String requestno = request.getParameter("requestno");
//    	String yborderid = request.getParameter("yborderid");
//    	
//    	logger.info(sessionId+":"+methodName+":requestno:"+requestno+",yborderid:"+yborderid);
//    	
//    	//调用SDK中的查询方法
//    	 ServiceResult91 result91= YeePaySDK.queryRefund(requestno, yborderid);
//    	 
//    	 if(CommUtils.isNull(result91.getObj())){
//          	yeePayResp.setCode(result91.getRequestCode());
//          	yeePayResp.setMessage(result91.getRequestMsg());
//          	return yeePayResp;
//          }
//    	 String json = (String) result91.getObj();
//    	 YeePayRefundQueryReturn yeePayRefundQueryReturn =  JSON.parseObject(json, YeePayRefundQueryReturn.class);
//    	//对请求状态进行判断
//    	 String state = yeePayRefundQueryReturn.getState();
//    	 if("FAILURE".equals(state)){
//    		 yeePayResp.setCode("110");
//    		 yeePayResp.setMessage(yeePayRefundQueryReturn.getError());
//    		 logger.info(sessionId+":"+methodName+":退款查询异常:"+yeePayResp.getMessage());
//    		 return yeePayResp;
//    	 }
//    	 
//    	 YeePayRefundQueryResult yeePayRefundQueryResult = yeePayRefundQueryReturn.getResult();
//    	 
//    	 logger.info(sessionId+":"+methodName+":退款查询结果为:"+JSON.toJSONString(yeePayRefundQueryResult));
//    	 //如果错误码不为空则证明查询出错，返回相应的错误信息
//    	 if(!CommUtils.isNull(yeePayRefundQueryResult.getErrorcode())){
//    		 yeePayResp.setCode("110");
//    		 yeePayResp.setMessage(yeePayRefundQueryResult.getErrormsg());
//    		 logger.info(sessionId+":"+methodName+":退款查询异常:"+yeePayResp.getMessage());
//    		 return yeePayResp;
//    	 }
//    	 
//    	 //由于传入参数中请求号可能为空，所以将查询返回得到的请求号赋值与requestno 
//    	 requestno = yeePayRefundQueryResult.getRequestno();
//    	 logger.info(sessionId+":"+methodName+":退款请求号为:"+requestno);
//    	 //查询得到的结果
//    	 
//    	 YeepayRefundDetail yeepayRefundDetail = yeepayRefundDetailService.findYeepayRefundDetailByRequestNo(requestno);
//    	 if(CommUtils.isNull(yeepayRefundDetail)){
//    		 //数据库没查询到该笔退款记录，进行记录补全？
//    		 yeepayRefundDetail = new YeepayRefundDetail();
//    		 yeepayRefundDetail = toYeepayRefundDetail(yeePayRefundQueryResult);
//    		 yeepayRefundDetailService.saveYeepayRefundDetail(yeepayRefundDetail);
//    		 yeePayResp.setCode("200");
//             yeePayResp.setMessage("请求成功");
//             yeePayResp.setObj(yeePayRefundQueryResult);
//             logger.info(sessionId+":"+methodName+":数据库没查询到退款记录,已补录");
//             return yeePayResp;
//    	 }
//    	 logger.info(sessionId+":"+methodName+"数据库查询得到退款详情为："+JSON.toJSONString(yeepayRefundDetail));
//    	 //对status做判断来做数据的持久化更新
//    	 String status1 = yeePayRefundQueryResult.getStatus();
//    	 logger.info(sessionId+":"+methodName+"查询得到的退款状态为："+status1);
//    	 //数据库中保存的结果
//    	 String  status2= yeepayRefundDetail.getStatus();
//    	 logger.info(sessionId+":"+methodName+"数据库中保存的状态为："+status2);
//    	 if(!"REFUND_SUCCESS".equals(status2)&&!status2.equals(status1)){
//    		 logger.info(sessionId+":"+methodName+"原退款状态不为成功且 退款状态发生改变，开始更新状态");
//    		  boolean bool = updateYeepayRefundDetailByYeePayRefundQueryResult(yeepayRefundDetail,yeePayRefundQueryResult);
//    		  logger.info(sessionId+":"+methodName+":更新退款信息:"+bool);
//    		  if("REFUND_SUCCESS".equals(status1)){
//    			  //TODO 退款成功
//    		  }else{
//    			  //TODO 退款失败
//    		  }
//    	 }
//    	//请求成功
//         yeePayResp.setCode("200");
//         yeePayResp.setMessage("请求成功");
//         yeePayResp.setObj(yeePayRefundQueryResult);
//         return yeePayResp;
//    }
//    
//  
//    /**
//     * 退款对账接口
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("queryRefundDetails.do")
//    public YeePayResp queryRefundDetails(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.queryRefundDetails";
//    	YeePayResp yeePayResp = new YeePayResp();
//    	logger.info(sessionId+":"+methodName+"start");
//    	//获取请求参数
//    	String startdate = request.getParameter("startdate");
//    	String enddate = request.getParameter("enddate");
//    	startdate = startdate.replaceAll(" ", "");
//    	enddate = enddate.replaceAll(" ", "");
//    	logger.info(sessionId+":"+methodName+"startdate:"+startdate);
//    	logger.info(sessionId+":"+methodName+"enddate:"+enddate);
//    	//加上一个非空判断吧
//    	if(CommUtils.isNull(startdate)||CommUtils.isNull(enddate)){
//    		logger.error(sessionId+":"+methodName+"日期信息为空，请查实！");
//    		yeePayResp.setCode("110");
//    		yeePayResp.setMessage("日期信息为空");
//    		return yeePayResp;
//    	}
//    	
//    	
//    	
//    	//调用SDK中的方法
//    	ServiceResult91 result91= YeePaySDK.queryRefundDetails(startdate, enddate);
//    	if(CommUtils.isNull(result91.getObj())){
//           	yeePayResp.setCode(result91.getRequestCode());
//           	yeePayResp.setMessage(result91.getRequestMsg());
//           	return yeePayResp;
//        }
//    	String json = (String) result91.getObj();
//    	YeePayRefundDetailsReturn yeePayRefundDetailsReturn = JSON.parseObject(json, YeePayRefundDetailsReturn.class);
//    	//对请求状态进行判断
//	   	String state = yeePayRefundDetailsReturn.getState();
//	   	if("FAILURE".equals(state)){
//	   		 yeePayResp.setCode("110");
//	   		 yeePayResp.setMessage(yeePayRefundDetailsReturn.getError());
//	   		 logger.info(sessionId+":"+methodName+":退款对账查询异常:"+yeePayResp.getMessage());
//	   		 return yeePayResp;
//	   	 }
//	   	
//	   	
//	   	
//	   	 //获得详细数据
//	   	 YeePayRefundDetailsResult yeePayRefundDetailsResult= yeePayRefundDetailsReturn.getResult();
//	   	 
//	   	 //当错误码非空时直接返回结果
//	   	 if(!CommUtils.isNull(yeePayRefundDetailsResult.getErrorcode())){
//	   		 yeePayResp.setCode("110");
//	   		 yeePayResp.setMessage(yeePayRefundDetailsResult.getErrormsg());
//	   		logger.info(sessionId+":"+methodName+":退款对账查询异常:"+yeePayResp.getMessage());
//	   		return yeePayResp;
//	   	 }
//	   	 
//	   	 
//	   	 
//	   	 String resultdata = yeePayRefundDetailsResult.getResultdata();
//	   	 //这个数据要不要在这里给他分割一下？返回回去？
//	   	 try {
//	   		 //这里没有进行非空判断
//	   		 if(CommUtils.isNull(resultdata)){
//	   			yeePayResp.setCode("200");
//	            yeePayResp.setMessage("请求成功");
//	            yeePayResp.setObj(yeePayRefundDetailsResult);
//	            return yeePayResp; 
//	   		 }
//	   		 yeePayRefundDetailsResult= changeToYeePayRefundDetailsResult(yeePayRefundDetailsResult, resultdata);
//		} catch (Exception e) {
//			logger.error(sessionId+":"+methodName+":转换异常",e);
//		}
//	   	 
//	   	 logger.info(sessionId+":"+methodName+":退款对账查询详细结果为:"+JSON.toJSONString(yeePayRefundDetailsResult));
//	   	 //请求成功
//         yeePayResp.setCode("200");
//         yeePayResp.setMessage("请求成功");
//         yeePayResp.setObj(yeePayRefundDetailsResult);
//         return yeePayResp;
//    }
//    
//    /**
//     * 扣款对账查询接口
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("queryPayDetails.do")
//    public YeePayResp queryPayDetails(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.queryPayDetails";
//    	YeePayResp yeePayResp = new YeePayResp();
//    	logger.info(sessionId+":"+methodName+"start");
//    	//获取请求参数
//    	String startdate = request.getParameter("startdate");
//    	String enddate = request.getParameter("enddate");
//    	if(CommUtils.isNull(startdate)||CommUtils.isNull(enddate)){
//    		logger.error(sessionId+":"+methodName+"日期信息为空，请查实！");
//    		yeePayResp.setCode("110");
//    		yeePayResp.setMessage("日期信息为空");
//    		return yeePayResp;
//    	}
//    	
//    	
//    	//调用SDK中的方法
//    	ServiceResult91 result91= YeePaySDK.queryBillingDetails(startdate, enddate);
//    	if(CommUtils.isNull(result91.getObj())){
//           	yeePayResp.setCode(result91.getRequestCode());
//           	yeePayResp.setMessage(result91.getRequestMsg());
//           	return yeePayResp;
//        }
//    	String json = (String) result91.getObj();
//    	YeePayDetailsReturn yeePayDetailsReturn = JSON.parseObject(json, YeePayDetailsReturn.class);
//    	//对请求状态进行判断
//	   	String state = yeePayDetailsReturn.getState();
//	   	if("FAILURE".equals(state)){
//	   		 yeePayResp.setCode("110");
//	   		 yeePayResp.setMessage(yeePayDetailsReturn.getError());
//	   		 logger.info(sessionId+":"+methodName+":扣款对账异常:"+yeePayResp.getMessage());
//	   		 return yeePayResp;
//	   	 }
//	   	YeePayDetailsResult yeePayDetailsResult = yeePayDetailsReturn.getResult();
//	   	//对得到的结果的errormsg进行判断
//	   	if(!CommUtils.isNull(yeePayDetailsResult.getErrormsg())){
//	   	 yeePayResp.setCode("110");
//	   	 yeePayResp.setMessage(yeePayDetailsResult.getErrormsg());
//	   	 logger.info(sessionId+":"+methodName+"扣款对账异常"+yeePayDetailsResult.getErrormsg());
//	   	 return yeePayResp;
//	   	}
//	   	
//	   	
//	    String resultdata = yeePayDetailsResult.getResultdata();
//	    logger.info(sessionId+":"+methodName+"=======扣款的详细数据为:"+resultdata);
//	    
//	    
//	    try {
//	    	if(!CommUtils.isNull(resultdata)){
//	    		
//	    		yeePayDetailsResult = changeToYeePayDetailsResult(yeePayDetailsResult, resultdata);
//	    	}
//			
//		} catch (Exception e) {
//			logger.error(sessionId+":"+methodName+":转换异常",e);
//		}
//	    logger.info(sessionId+":"+methodName+":扣款对账查询详细结果为:"+JSON.toJSONString(yeePayDetailsResult));
//	  //请求成功
//        yeePayResp.setCode("200");
//        yeePayResp.setMessage("请求成功");
//        yeePayResp.setObj(yeePayDetailsResult);
//        return yeePayResp;
//    }
//    
//    
//    /**
//     * 单笔扣款接口
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("singlePay.do")
//    public YeePayResp singlePayForUser(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.singlePayForUser";
//    	YeePayResp yeePayResp = new YeePayResp();
//    	logger.info(sessionId+":"+methodName+"start");
//    	//获取请求参数
//    	String amount = request.getParameter("amount");
//    	String phone = request.getParameter("phone");
//    	String username = request.getParameter("username");
//    	String idcardno = request.getParameter("idcardno");
//    	String cardno = request.getParameter("cardno");
//    	String identityid = request.getParameter("identityid"); //借款人ID
//    	String orderId = request.getParameter("orderid"); //订单ID
//    	
//    	
//    	//请求号生成策略
//    	String requestno = IdGenerator.INSTANCE.nextId();
//    	requestno = "Y"+ requestno;
//    	
//		logger.info(sessionId+methodName+"商户请求号为："+requestno);
//    	
//		logger.info(sessionId+":"+methodName+"amount:"+amount+",phone:"+phone+",username:"+username+",idcardno:"+idcardno+",cardno:"+cardno+",identityid:"+identityid);
//		
//		logger.info(sessionId+":"+methodName+"开始调用SDK中的方法");
//		
//		ServiceResult91 result91 = YeePaySDK.singlePay(amount, phone, username, idcardno, cardno, identityid, requestno);
//		if(CommUtils.isNull(result91.getObj())){
//           	yeePayResp.setCode(result91.getRequestCode());
//           	yeePayResp.setMessage(result91.getRequestMsg());
//           	return yeePayResp;
//        }
//		String json = (String) result91.getObj();
//		logger.info(sessionId+":"+methodName+"单笔还款返回结果为："+json);
//		try{
//			
//			YeePaySinglePayReturn yeePaySinglePayReturn = JSON.parseObject(json, YeePaySinglePayReturn.class);
//			
//			//对请求状态进行判断
//			String state = yeePaySinglePayReturn.getState();
//			if("FAILURE".equals(state)){
//				yeePayResp.setCode("110");
//				yeePayResp.setMessage(yeePaySinglePayReturn.getError());
//				logger.info(sessionId+":"+methodName+":单笔扣款异常:"+yeePayResp.getMessage());
//				return yeePayResp;
//			}
//			
//			YeePaySyncSinglePayResult yeePaySyncSinglePayResult = yeePaySinglePayReturn.getResult();
//			
//			YeepayBatchDetail yeepayBatchDetail = new YeepayBatchDetail();
//			//数据持久化
//			yeepayBatchDetail.setAmount(amount);
//			yeepayBatchDetail.setBorrowerId(Long.valueOf(identityid));
//			yeepayBatchDetail.setOrderId(Long.valueOf(orderId));
//			yeepayBatchDetail.setIdCardNo(idcardno);
//			yeepayBatchDetail.setPhone(phone);
//			yeepayBatchDetail.setUserName(username);
//			yeepayBatchDetail.setCardNo(cardno);
//			yeepayBatchDetail.setStatus(yeePaySyncSinglePayResult.getStatus());
//			yeepayBatchDetail.setRequestNo(requestno);
//			yeepayBatchDetail.setErrorCode(yeePaySyncSinglePayResult.getErrorcode());
//			yeepayBatchDetail.setErrorMsg(yeePaySyncSinglePayResult.getErrormsg());
//			yeepayBatchDetailService.saveYeepayBatchDetail(yeepayBatchDetail);
//			
//			//请求成功
//			yeePayResp.setCode("200");
//			yeePayResp.setMessage("请求成功");
//			yeePayResp.setObj(yeePaySyncSinglePayResult);
//			return yeePayResp;
//		}catch(Exception e){
//			yeePayResp.setCode("100");
//			yeePayResp.setMessage("系统异常");
//			logger.error(sessionId+":"+methodName+"扣款请求异常", e);
//			return yeePayResp;
//		}
//    	
//    }
//    
//    /**
//     * 单笔扣款异步通知
//     * @param request
//     * @param response
//     */
//    @ResponseBody
//    @RequestMapping("singleCallBack.do")
//    public void singlePayCallBack(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.batchChargeCallBack";
//    	logger.info(sessionId+":"+methodName+"start");
//    	try {
//    		request.setCharacterEncoding("utf-8");
//    		String responseMesg = request.getParameter("response");
//    		
//    		logger.info("responseMesg:"+responseMesg);
//    		//代理商商户编号
//    		String customerIdentification = request.getParameter("customerIdentification");
//    		response.setStatus(200);
//    		response.getWriter().append("SUCCESS");
//    		
//    		logger.info(sessionId+":"+methodName+"代理商商户编号customerIdentification:" + customerIdentification + ",responseMesg:" + responseMesg);
//    		
//    		
//    		DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
//    		dto.setCipherText(responseMesg);
//   
//    		InternalConfig internalConfig = InternalConfig.Factory.getInternalConfig();
//    		PrivateKey privateKey = internalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
//    		PublicKey publicKey = internalConfig.getYopPublicKey(CertTypeEnum.RSA2048);
//    		dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
//    		String plainText = dto.getPlainText();
//    		logger.info(sessionId+":"+methodName+"解密后的明文为："+plainText);
//    		
//    		//这个地方不确定异步返回数据的格式
//    		
//    		YeePayAsyncSinglePayResult yeePayAsyncSinglePayResult = JSON.parseObject(plainText, YeePayAsyncSinglePayResult.class);
//    		
//    		String requestNo = yeePayAsyncSinglePayResult.getRequestno();
//    		
//        	YeepayBatchDetail yeepayBatchDetail = yeepayBatchDetailService.findByRequestNo(requestNo);
//    		
//        	//进行非空判断
//        	if(!CommUtils.isNull(yeepayBatchDetail)){
//        		
//        		String status2 = yeepayBatchDetail.getStatus();
//        		if("PAY_SUCCESS".equals(status2)){
//					logger.info("还款状态重复推送");
//					return;
//				}
//        		yeepayBatchDetail  = changeToYeepayBatchDetail(yeepayBatchDetail,yeePayAsyncSinglePayResult);
//        		
//        		
//        		boolean bool = yeepayBatchDetailService.updateYeepayBatchDetail(yeepayBatchDetail);
//
//        		if(!bool){
//        			logger.info(sessionId+":"+methodName+"扣款状态更新成功");
//        		}else{
//        			logger.info(sessionId+":"+methodName+"扣款状态更新失败");
//        		}
//        	}else{
//        		logger.info(sessionId+":"+methodName+"查询原扣款记录为空,扣款状态更新失败");
//    		}
//        	
//    		//数据持久化
//
//
//    	}catch(Exception e){
//    		
//    		logger.error(sessionId+":"+methodName+"系统异常", e);
//    	}
//    	
//    }
//
//	/**
//	 * 单笔扣款异步通知
//	 * @param request
//	 * @param response
//	 */
//	@ResponseBody
//	@RequestMapping("autoSinglePayCallBack.do")
//	public void autoSinglePayCallBack(HttpServletRequest request, HttpServletResponse response){
//		String sessionId = DateUtils.getDateHMToString();
//		String methodName = "YeePayController.batchChargeCallBack";
//		logger.info(sessionId+":"+methodName+"start");
//		try {
//			request.setCharacterEncoding("utf-8");
//			String responseMesg = request.getParameter("response");
//			//代理商商户编号
//			String customerIdentification = request.getParameter("customerIdentification");
//			response.setStatus(200);
//			response.getWriter().append("SUCCESS");
//
//			logger.info(sessionId+":"+methodName+"代理商商户编号customerIdentification:" + customerIdentification);
//
//
//			DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
//			dto.setCipherText(responseMesg);
//
//			InternalConfig internalConfig = InternalConfig.Factory.getInternalConfig();
//			PrivateKey privateKey = internalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
//			PublicKey publicKey = internalConfig.getYopPublicKey(CertTypeEnum.RSA2048);
//			dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
//			String plainText = dto.getPlainText();
//			logger.info(sessionId+":"+methodName+"解密后的明文为："+plainText);
//
//			//这个地方不确定异步返回数据的格式
//
//			YeePayAsyncSinglePayResult yeePayAsyncSinglePayResult = JSON.parseObject(plainText, YeePayAsyncSinglePayResult.class);
//
//			String requestNo = yeePayAsyncSinglePayResult.getRequestno();
//
//			YeepayBatchDetail yeepayBatchDetail = yeepayBatchDetailService.findByRequestNo(requestNo);
//
//			//进行非空判断
//			if(!CommUtils.isNull(yeepayBatchDetail)){
//
//				String status2 = yeepayBatchDetail.getStatus();
//				if("PAY_SUCCESS".equals(status2)){
//					logger.info("放款状态重复推送");
//					return;
//				}
//				yeepayBatchDetail  = changeToYeepayBatchDetail(yeepayBatchDetail,yeePayAsyncSinglePayResult);
//
//
//				boolean bool = yeepayBatchDetailService.updateYeepayBatchDetail(yeepayBatchDetail);
//
//				Double payMoney = null;
//				Long orderId = yeepayBatchDetail.getOrderId();
//				RepaymentDto repaymentDto = new RepaymentDto();
//				String pay_info = RedisUtils.hget("pay_info", orderId.toString());
//				if (StringUtils.isNotEmpty(pay_info)) {
//					RepaymentDto redisDto = JSON.parseObject(pay_info, RepaymentDto.class);
//					if (redisDto != null) {
//						repaymentDto.setTerminalType(redisDto.getTerminalType());
//						if (SystemConstant.WITHHOLD_TEST_BOOL) {// 测试
//							payMoney = redisDto.getAmount();
//						}
//					}
//				}
//				repaymentDto.setUseCoupon(false);
//				repaymentDto.setOrderId(orderId);
//				repaymentDto.setType(1);
//				//repaymentDto.setTradeNo(notifyResult.getOid_paybill());
//				repaymentDto.setAmount(payMoney);
//				repaymentDto.setPayChannel(2);
//				repaymentDto.setTradeTime(new Date());
//				repaymentDto.setTradeType(1);
//				//repaymentDto.setTradeCode(notifyResult.getResult_pay());
//
//				// 支付成功
//				AppResponseResult appResponseResult = bwRepaymentService.updateOrderByTradeMoney(repaymentDto);
//				if ("000".equals(appResponseResult.getCode())) {
//					logger.info("支付成功orderId：" + orderId + JSON.toJSONString(appResponseResult));
//				} else {
//					logger.info("支付失败orderId：" + orderId + JSON.toJSONString(appResponseResult));
//				}
//			}else{
//				logger.info(sessionId+":"+methodName+"查询原扣款记录为空,扣款状态更新失败");
//			}
//
//			//数据持久化
//
//
//		}catch(Exception e){
//
//			logger.error(sessionId+":"+methodName+"系统异常", e);
//		}
//
//	}
//    
//    
//    /**
//     * 单笔扣款查询接口
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("querySinglePay.do")
//    public YeePayResp querySinglePay(HttpServletRequest request, HttpServletResponse response){
//    	String sessionId = DateUtils.getDateHMToString();						
//    	String methodName = "YeePayController.singleRefundQuery";
//    	YeePayResp yeePayResp = new YeePayResp();
//    	logger.info(sessionId+":"+methodName+"start");
//    	//获取请求参数
//    	String requestno = request.getParameter("requestno");
//    	String yborderid = request.getParameter("yborderid");
//    	
//    	logger.info(sessionId+":"+methodName+":requestno:"+requestno+",yborderid:"+yborderid);
//    	
//    	ServiceResult91 result91 = YeePaySDK.querySinglePay(requestno, yborderid);
//    	if(CommUtils.isNull(result91.getObj())){
//           	yeePayResp.setCode(result91.getRequestCode());
//           	yeePayResp.setMessage(result91.getRequestMsg());
//           	return yeePayResp;
//        }
//    	String json = (String) result91.getObj();
//		logger.info(sessionId+":"+methodName+"单笔还款查询返回结果为："+json);
//    	
//    	YeePayQuerySinglePayReturn yeePayQuerySinglePayReturn	 = JSON.parseObject(json, YeePayQuerySinglePayReturn.class);
//    	String state = yeePayQuerySinglePayReturn.getState();
//		if("FAILURE".equals(state)){
//				yeePayResp.setCode("110");
//				yeePayResp.setMessage(yeePayQuerySinglePayReturn.getError());
//				logger.info(sessionId+":"+methodName+":单笔扣款查询异常:"+yeePayResp.getMessage());
//				return yeePayResp;
//		}
//		try {
//			//单笔扣款异步通知和查询返回的数据结构一致
//			YeePayAsyncSinglePayResult yeePayAsyncSinglePayResult = yeePayQuerySinglePayReturn.getResult();
//			requestno = yeePayAsyncSinglePayResult.getRequestno();
//			YeepayBatchDetail yeepayBatchDetail = yeepayBatchDetailService.findByRequestNo(requestno);
//			if(!CommUtils.isNull(yeepayBatchDetail)){
//				String status1 = yeepayBatchDetail.getStatus();
//				String status2 = yeePayAsyncSinglePayResult.getStatus();
//				if(!CommUtils.isNull(status2)){
//					if(!"PAY_SUCCESS".equals(status1)&&!status2.equals(status1)){
//						yeepayBatchDetail  = changeToYeepayBatchDetail(yeepayBatchDetail,yeePayAsyncSinglePayResult);
//						boolean bool = yeepayBatchDetailService.updateYeepayBatchDetail(yeepayBatchDetail);
//		        		
//		        		if(!bool){
//		        			logger.info(sessionId+":"+methodName+"扣款状态更新成功");
//		        		}else{
//		        			logger.info(sessionId+":"+methodName+"扣款状态更新失败");
//		        		}
//					}
//				}
//				
//			}else{
//				logger.info(sessionId+":"+methodName+"查询原扣款记录为空,扣款状态更新失败");
//			}
//			//请求成功
//	        yeePayResp.setCode("200");
//	        yeePayResp.setMessage("请求成功");
//	        yeePayResp.setObj(yeePayAsyncSinglePayResult);
//	        return yeePayResp;
//		} catch (Exception e) {
//			logger.error(sessionId+":"+methodName+"系统异常", e);
//			 yeePayResp.setCode("100");
//		     yeePayResp.setMessage("系统异常");
//		     return yeePayResp;
//		}
//    }
//    
//    
//    
//    /**
//     * 根据异步通知更改状态
//     * @param yeePayAsyncSinglePayResult
//     * @return
//     * @throws Exception
//     */
//    private YeepayBatchDetail changeToYeepayBatchDetail(YeepayBatchDetail yeepayBatchDetail,YeePayAsyncSinglePayResult yeePayAsyncSinglePayResult){
//    	yeepayBatchDetail.setBankCode(yeePayAsyncSinglePayResult.getBankcode());
//    	yeepayBatchDetail.setStatus(yeePayAsyncSinglePayResult.getStatus());
//    	yeepayBatchDetail.setYbOrderId(yeePayAsyncSinglePayResult.getYborderid());
//    	yeepayBatchDetail.setErrorCode(yeePayAsyncSinglePayResult.getErrorcode());
//    	yeepayBatchDetail.setErrorMsg(yeePayAsyncSinglePayResult.getErrormsg());
//    	yeepayBatchDetail.setBankSuccessDate(yeePayAsyncSinglePayResult.getBanksuccessdate());
//    	return yeepayBatchDetail;
//    }
//    
//    /**
//     * 扣款对账查询返回参数封装实体类
//     * @param yeePayDetailsResult
//     * @param resultdata
//     * @return
//     */
//    private  YeePayDetailsResult changeToYeePayDetailsResult(YeePayDetailsResult yeePayDetailsResult,String resultdata){
//    	String[] strings = resultdata.split("\r\n");
//    	String str1 = strings[0];
//    	String[] split1 = str1.split(",");
//    	String totalRequstNum = split1[0];
//    	String totalPayAmount = split1[1];
//    	String totalProcedureFee = split1[2];
//    	yeePayDetailsResult.setTotalRequstNum(totalRequstNum);
//    	yeePayDetailsResult.setTotalPayAmount(totalPayAmount);
//    	yeePayDetailsResult.setTotalProcedureFee(totalProcedureFee);
//    	
//    	List<YeePayPayBill> list = new ArrayList<>();
//    	
//    	
//    	String str2 = strings[1];
//    	String[] split2 = str2.split("\n");
//    	for(int i = 0;i<split2.length;i++){
//    		YeePayPayBill yeePayPayBill = new YeePayPayBill();
//    		String[] split3 = split2[i].split(",");
//    		yeePayPayBill.setMerchantno(split3[0]);
//    		yeePayPayBill.setRequestno(split3[1]);
//    		yeePayPayBill.setYborderid(split3[2]);
//    		yeePayPayBill.setRequsttime(split3[3]);
//    		yeePayPayBill.setAmount(split3[4]);
//    		yeePayPayBill.setAAA(split3[5]);
//    		yeePayPayBill.setProcedureFee(split3[6]);
//    		yeePayPayBill.setClosetime(split3[7]);
//    		//发现单笔扣款的时候这个地方不存在
//    		if(split3.length>=10){
//    			yeePayPayBill.setMerchantbatchno(split3[8]);
//    			yeePayPayBill.setYbbatchno(split3[9]);
//    			if(split3.length>=11){
//    				yeePayPayBill.setRemark(split3[10]);
//    			}
//    		}
//    		list.add(yeePayPayBill);
//    	}
//    	yeePayDetailsResult.setList(list);
//    	return yeePayDetailsResult;
//    	
//    }
//    
//    /**
//     * 将原接口返回的字符串数据封装成实体对象
//     * @param yeePayRefundDetailsResult
//     * @param resultdata
//     * @return
//     */
//    private  YeePayRefundDetailsResult changeToYeePayRefundDetailsResult(YeePayRefundDetailsResult yeePayRefundDetailsResult,String resultdata){
//    	String[] strings = resultdata.split("\r\n");
//    	String str1 = strings[0];
//    	String[] split1 = str1.split(",");
//    	String totalRequstNum = split1[0];
//    	String totalRefundAmount = split1[1];
//    	
//    	yeePayRefundDetailsResult.setTotalRefundAmount(totalRefundAmount);
//    	yeePayRefundDetailsResult.setTotalRequstNum(totalRequstNum);
//    	List<YeePayRefundBill> list = new ArrayList<>();
//    	
//    	String str2 = strings[1];
//    	String[] split2 = str2.split("\n");
//    	for(int i = 0;i<split2.length;i++){
//    		YeePayRefundBill yeePayRefundBill = new YeePayRefundBill();
//    		String[] split3 = split2[i].split(",");
//    		if(split3.length==9){//格式转换成YeePayRefundBill
//    			yeePayRefundBill.setMerchantno(split3[0]);
//    			yeePayRefundBill.setRequstno(split3[1]);
//    			yeePayRefundBill.setYborderid(split3[2]);
//    			yeePayRefundBill.setRequsttime(split3[3]);
//    			yeePayRefundBill.setClosetime(split3[4]);
//    			yeePayRefundBill.setPaymentyborderid(split3[5]);
//    			yeePayRefundBill.setStatus(split3[6]);
//    			yeePayRefundBill.setAmount(split3[7]);
//    			yeePayRefundBill.setRemark(split3[8]);
//    		}
//    		//用map不用对象这样好像可以解决问题
//    		list.add(yeePayRefundBill);
//    	}
//    	yeePayRefundDetailsResult.setList(list);
//    	
//    	return yeePayRefundDetailsResult;
//    }
//   
//    /**
//     * 将传入参数转化为SKD方法中的参数类型
//     * @param yeepayBatchDetail
//     * @return 
//     */
//    private YeePayBatchdetail changeToYeePayBatchdetail(YeepayBatchDetail yeepayBatchDetail){
//    	YeePayBatchdetail detail = new YeePayBatchdetail();
//    	detail.setAmount(yeepayBatchDetail.getAmount());
//    	detail.setAvaliabletime(AVALIABLETIME);
//    	detail.setRequestno(yeepayBatchDetail.getRequestNo());
//    	detail.setBathprotocolno(yeepayBatchDetail.getBathprotocolnNo());
//    	detail.setCardno(yeepayBatchDetail.getCardNo());
//    	detail.setIdcardno(yeepayBatchDetail.getIdCardNo());
//    	detail.setPhone(yeepayBatchDetail.getPhone());
//    	detail.setProductname(PRODUCTNAME);
//    	detail.setRequestno(yeepayBatchDetail.getRequestNo());
//    	detail.setUsername(yeepayBatchDetail.getUserName());
//    	return detail;
//    }
//    
//    /**
//     * 将返回结果转化成YeepayBatchCharge类型存入数据库
//     * @param yeePayResult
//     * @return
//     */
//    private YeepayBatchCharge toYeepayBatchCharge(YeePayResult yeePayResult){
//    	 YeepayBatchCharge yeepayBatchCharge = new YeepayBatchCharge();
//    	 yeepayBatchCharge.setMerchantBatchNo(yeePayResult.getMerchantbatchno());
//    	 yeepayBatchCharge.setYbBatchNo(yeePayResult.getYbbatchno());
//    	 yeepayBatchCharge.setStatus(yeePayResult.getStatus());
//    	 yeepayBatchCharge.setErrorCode(yeePayResult.getErrorcode());
//    	 yeepayBatchCharge.setErrorMsg(yeePayResult.getErrormsg());
////    	 yeepayBatchCharge.setFree1(yeePayResult.getFree1());
////    	 yeepayBatchCharge.setFree2(yeePayResult.getFree2());
////    	 yeepayBatchCharge.setFree3(yeePayResult.getFree3());
//    	return yeepayBatchCharge;
//    }
//    
//    /**
//     * 将返回结果转化成YeepayRefundDetail类型存入数据库
//     * @param yeePayRefundResult
//     * @return
//     */
//    private YeepayRefundDetail toYeepayRefundDetail(YeePayRefundResult yeePayRefundResult){
//    	YeepayRefundDetail yeepayRefundDetail = new YeepayRefundDetail();
//    	yeepayRefundDetail.setAmount(yeePayRefundResult.getAmount());
//    	yeepayRefundDetail.setErrorCode(yeePayRefundResult.getErrorcode());
//    	yeepayRefundDetail.setErrorMsg(yeePayRefundResult.getErrormsg());
//    	yeepayRefundDetail.setRequestNo(yeePayRefundResult.getRequestno());
//    	yeepayRefundDetail.setYborderId(yeePayRefundResult.getYborderid());
//    	yeepayRefundDetail.setStatus(yeePayRefundResult.getStatus());
//    	return yeepayRefundDetail;
//    }
//    
//    private YeepayRefundDetail toYeepayRefundDetail(YeePayRefundQueryResult yeePayRefundQueryResult){
//    	YeepayRefundDetail yeepayRefundDetail = new YeepayRefundDetail();
//    	yeepayRefundDetail.setAmount(yeePayRefundQueryResult.getAmount());
//    	yeepayRefundDetail.setErrorCode(yeePayRefundQueryResult.getErrorcode());
//    	yeepayRefundDetail.setErrorMsg(yeePayRefundQueryResult.getErrormsg());
//    	yeepayRefundDetail.setRequestNo(yeePayRefundQueryResult.getRequestno());
//    	yeepayRefundDetail.setYborderId(yeePayRefundQueryResult.getYborderid());
//    	yeepayRefundDetail.setStatus(yeePayRefundQueryResult.getStatus());
//    	yeepayRefundDetail.setBankCode(yeePayRefundQueryResult.getBankcode());
//    	yeepayRefundDetail.setCardLast(yeePayRefundQueryResult.getCardlast());
//    	yeepayRefundDetail.setCardTop(yeePayRefundQueryResult.getCardtop());
//    	
//    	return yeepayRefundDetail;
//    }
//    
//    
//    
//    
//    
//    
//    private boolean updateYeepayRefundDetailByYeePayRefundQueryResult(YeepayRefundDetail yeepayRefundDetail,YeePayRefundQueryResult yeePayRefundQueryResult){
//    	yeepayRefundDetail.setStatus(yeePayRefundQueryResult.getStatus());
//    	yeepayRefundDetail.setCardTop(yeePayRefundQueryResult.getCardtop());
//    	yeepayRefundDetail.setCardLast(yeePayRefundQueryResult.getCardlast());
//    	yeepayRefundDetail.setErrorCode(yeePayRefundQueryResult.getErrorcode());
//    	yeepayRefundDetail.setErrorMsg(yeePayRefundQueryResult.getErrormsg());
//    	yeepayRefundDetail.setBankCode(yeePayRefundQueryResult.getBankcode());
//    	boolean bool = yeepayRefundDetailService.updateYeepayRefundDetail(yeepayRefundDetail);
//    	return bool;
//    }
//
//    /**
//     * 批量扣款查询获得扣款状态
//     * @param SingleChargeList
//     * @param merchantBatchNo
//     */
//    private void YeepayBatchDetailUseQuerry(List<YeePaySingleCharge> SingleChargeList,String merchantBatchNo){
//    	for (YeePaySingleCharge yeePaySingleCharge : SingleChargeList) {
//    		String requestNo = yeePaySingleCharge.getRequestno();
//			String status = yeePaySingleCharge.getStatus();
//			String ybOrderId = yeePaySingleCharge.getYborderid();
//			String bankCode = yeePaySingleCharge.getBankcode();
//			String errorCode = yeePaySingleCharge.getErrorcode();
//			String errorMsg = yeePaySingleCharge.getErrormsg();
//			String amount = yeePaySingleCharge.getAmount();
//			String bankSuccessDate = yeePaySingleCharge.getBanksuccessdate();
//			logger.info("根据请求号查询扣款详情：请求号为"+requestNo);
//			YeepayBatchDetail yeepayBatchDetail = yeepayBatchDetailService.findByRequestNo(requestNo);
//			
//			BwCapitalWithhold bwCapitalWithhold = bwCapitalWithholdService.queryBwCapitalWithhold(requestNo);
//			
//			Long borrowerId = yeepayBatchDetail.getBorrowerId();//预备给做其他表数据修改用
//			Long orderId = yeepayBatchDetail.getOrderId();//预备给做其他表数据修改用
//			
//			//按道理也不应该会进这个判断中,补全部分数据，请求中的具体用户数据拿不到
//			if(CommUtils.isNull(yeepayBatchDetail)){
//				logger.info("======数据库中该笔扣款记录没有保存=======");
//				yeepayBatchDetail = new YeepayBatchDetail();
//				yeepayBatchDetail.setStatus(status);
//				yeepayBatchDetail.setAmount(amount);
//				yeepayBatchDetail.setBankCode(bankCode);
//				yeepayBatchDetail.setErrorCode(errorCode);
//				yeepayBatchDetail.setErrorMsg(errorMsg);
//				yeepayBatchDetail.setMerchantBatchNo(merchantBatchNo);
//				yeepayBatchDetail.setRequestNo(requestNo);
//				yeepayBatchDetail.setYbOrderId(ybOrderId); 
//				yeepayBatchDetailService.saveYeepayBatchDetail(yeepayBatchDetail);
//				logger.info("=====扣款信息补录成功=====");
//			}
//			logger.info("查询得到的详情为："+JSON.toJSONString(yeepayBatchDetail));
//			
//			//获得数据库中的扣款状态为
//			String status2 = yeepayBatchDetail.getStatus();
//			logger.info("获得数据库中的扣款状态为："+status2);
//			if(!"PAY_SUCCESS".equals(status2)&&!status.equals(status2)){
//				logger.info("=========原数据库中的状态不为支付完成且与返回状态不相同，开始更新扣款详情信息");
//				yeepayBatchDetail.setStatus(status);
//				yeepayBatchDetail.setYbOrderId(ybOrderId);
//				yeepayBatchDetail.setBankCode(bankCode);
//				yeepayBatchDetail.setErrorCode(errorCode);
//				yeepayBatchDetail.setErrorMsg(errorMsg);
//				yeepayBatchDetail.setBankSuccessDate(bankSuccessDate);
//				yeepayBatchDetailService.updateYeepayBatchDetail(yeepayBatchDetail);
//				logger.info("==========扣款详情信息更新成功。");
//				if("PAY_SUCCESS".equals(status)){
//					bwCapitalWithhold.setCode("0");//成功
//					bwCapitalWithhold.setMsg("扣款成功");
//					bwCapitalWithhold.setPushStatus(2);//扣款成功
//					bwCapitalWithhold.setMoneyWithhold(bwCapitalWithhold.getMoney());
//					Date now = new Date();
//					bwCapitalWithhold.setUpdateTime(now);//更新时间
//					bwCapitalWithholdService.updateBwCapitalWithhold(bwCapitalWithhold);
//					//TODO 该笔扣款成功
//				}else if("PAY_FAIL".equals(status)||"TIME_OUT".equals(status)){
////					bwCapitalWithhold.setCode("1");//失败
//					bwCapitalWithhold.setMsg(yeepayBatchDetail.getErrorMsg());
//					bwCapitalWithhold.setPushStatus(3);//失败
//					Date now = new Date();
//					bwCapitalWithhold.setUpdateTime(now);//更新时间
//					bwCapitalWithholdService.updateBwCapitalWithhold(bwCapitalWithhold);
//					//TODO 该笔扣款失败
//				}
//				
//				
//			}else{
//				logger.info("=========存储状态和返回状态相同");
//			}
//			
//		}
//    }
//
//    /**
//     * 批量扣款异步通知获得扣款状态
//     * 
//     * @param singleList
//     * @param merchantBatchNo
//     */
//    private void YeepayBatchDetailUseCallBack(List<YeePayAsyncPaySingle> singleList,String merchantBatchNo){
//    	for (YeePayAsyncPaySingle yeePayAsyncPaySingle : singleList) {
//    		String requestNo = yeePayAsyncPaySingle.getRequestno();
//			String status = yeePayAsyncPaySingle.getStatus();
//			String ybOrderId = yeePayAsyncPaySingle.getYborderid();
//			String bankCode = yeePayAsyncPaySingle.getBankcode();
//			String errorCode = yeePayAsyncPaySingle.getErrorcode();
//			String errorMsg = yeePayAsyncPaySingle.getErrormsg();
//			String bankSuccessDate = yeePayAsyncPaySingle.getBanksuccessdate();
//
//			logger.info("======开始根据批次号和请求号查询扣款详情记录");
//			YeepayBatchDetail yeepayBatchDetail = yeepayBatchDetailService.findByMerchantBatchNoAndRequestNo(merchantBatchNo, requestNo);
//			BwCapitalWithhold bwCapitalWithhold = bwCapitalWithholdService.queryBwCapitalWithhold(requestNo);
//			
//			
//			
//			Long borrowerId = yeepayBatchDetail.getBorrowerId();//预备给做其他表数据修改用
//			Long orderId = yeepayBatchDetail.getOrderId();//预备给做其他表数据修改用
//			
//			String status2 = yeepayBatchDetail.getStatus();//我方数据库中存储的状态
//			
//			
//			
//			if(!CommUtils.isNull(yeepayBatchDetail)){
//				if("PAY_SUCCESS".equals(status2)){
//					logger.info("放款状态重复推送");
//					return;
//				}
//				
//				logger.info("=======查询到扣款详细信息为："+JSON.toJSONString(yeepayBatchDetail));
//				yeepayBatchDetail.setStatus(status);
//				yeepayBatchDetail.setYbOrderId(ybOrderId);
//				yeepayBatchDetail.setBankCode(bankCode);
//				yeepayBatchDetail.setErrorCode(errorCode);
//				yeepayBatchDetail.setErrorMsg(errorMsg);
//				yeepayBatchDetail.setBankSuccessDate(bankSuccessDate);
//				yeepayBatchDetailService.updateYeepayBatchDetail(yeepayBatchDetail);
//				logger.info("========更新扣款详情成功");
//				if("PAY_SUCCESS".equals(status)){
//					bwCapitalWithhold.setCode("0");//成功
//					bwCapitalWithhold.setMsg("扣款成功");
//					bwCapitalWithhold.setPushStatus(2);//扣款成功
//					bwCapitalWithhold.setMoneyWithhold(bwCapitalWithhold.getMoney());
//					Date now = new Date();
//					bwCapitalWithhold.setUpdateTime(now);//更新时间
//					bwCapitalWithholdService.updateBwCapitalWithhold(bwCapitalWithhold);
//					//TODO 扣款成功
//				}else if("PAY_FAIL".equals(status)||"TIME_OUT".equals(status)){
////					bwCapitalWithhold.setCode("1");//失败
//					bwCapitalWithhold.setMsg(yeepayBatchDetail.getErrorMsg());
//					bwCapitalWithhold.setPushStatus(3);//失败
//					Date now = new Date();
//					bwCapitalWithhold.setUpdateTime(now);//更新时间
//					bwCapitalWithholdService.updateBwCapitalWithhold(bwCapitalWithhold);
//					//TODO 扣款失败
//				}
//				
//				
//			}
//		}
//    }
//    
//    /**
//     * 生成一个BwCapitalWithhold对象存入bw_capital_withhold表中
//     * @param yeepayBatchDetail
//     * @return
//     */
//    private BwCapitalWithhold getBwCapitalWithholdByYeepayBatchDetail(YeepayBatchDetail yeepayBatchDetail){
//    	BwCapitalWithhold bwCapitalWithhold = new BwCapitalWithhold();
//		bwCapitalWithhold.setCardNo(yeepayBatchDetail.getCardNo());//银行卡号
//		Date createTime = new Date();
//		bwCapitalWithhold.setCreateTime(createTime);//请求时间
//		bwCapitalWithhold.setCapitalId(6);//代表是易宝
//		String amount = yeepayBatchDetail.getAmount();
//		BigDecimal money = new BigDecimal(amount);
//		money=money.setScale(2, BigDecimal.ROUND_HALF_UP);   
//		bwCapitalWithhold.setMoney(money);//扣款金额
//		Long orderId = yeepayBatchDetail.getOrderId();
//		bwCapitalWithhold.setOrderId(orderId);//订单ID
//		//获得order_no
//		BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
//		if(!CommUtils.isNull(bwOrder)){
//			
//			String orderNo = bwOrder.getOrderNo();
//			bwCapitalWithhold.setOrderNo(orderNo);//订单编号
//		}
//		bwCapitalWithhold.setOtherOrderNo(yeepayBatchDetail.getRequestNo());//由于易宝的流水号是异步通知时返回，这里只能存我们的请求号
//		
//		bwCapitalWithhold.setPayType(yeepayBatchDetail.getPayType());//支付类型，1.还款 2.展期
//		bwCapitalWithhold.setTerminalType(yeepayBatchDetail.getTerminalType());// 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
//		bwCapitalWithhold.setThirdPlatform(7);//TODO 资方放款渠道不明
//		//对yeepayBatchDetail对的状态进行判断
//		if("PROCESSING".equals(yeepayBatchDetail.getStatus())){
//			bwCapitalWithhold.setPushStatus(1);//扣款中
//			bwCapitalWithhold.setMsg("扣款中");
//		}
//		if("ACCEPT_FAIL".equals(yeepayBatchDetail.getStatus())){
//			bwCapitalWithhold.setPushStatus(3);//扣款失败
//			bwCapitalWithhold.setMsg(yeepayBatchDetail.getErrorMsg());//错误描述
//			Date now = new Date();
//			bwCapitalWithhold.setUpdateTime(now);//更新时间
//		}
//    	return bwCapitalWithhold;
//    }
//    
//    
//}
