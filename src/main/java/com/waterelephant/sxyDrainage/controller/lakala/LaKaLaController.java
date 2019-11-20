//package com.waterelephant.sxyDrainage.controller.lakala;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.utils.CommUtils;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwPlatformRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwRepaymentService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwOrderService;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.lakala.LklRespData;
//import com.waterelephant.sxyDrainage.entity.lakala.LklResponse;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsCheckUserInfo;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.LakalaService;
//import com.waterelephant.sxyDrainage.utils.lakala.LakalaConstant;
//import com.waterelephant.sxyDrainage.utils.lakala.LakalaUtils;
//import com.waterelephant.sxyDrainage.utils.lakala.SignService;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.third.service.ThirdService;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
///**
// * 榕树
// * 
// * Module:
// * 
// * LaKaLaController.java
// *
// * @author zhangyuan
// * @version 1.0
// * @description: <榕树>
// * @since JDK 1.8
// */
//@Controller
//public class LaKaLaController {
//
//	private Logger logger = Logger.getLogger(LaKaLaController.class);
//	@Autowired
//	private BwOrderService bwOrderService;
//	@Autowired
//	private IBwRepaymentService bwRepaymentService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//	@Autowired
//	private IBwRepaymentPlanService bwRepaymentPlanService;
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private BwPlatformRecordService bwPlatformRecordService;
//	@Autowired
//	private ThirdService thirdService;
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private LakalaService lakalaService;
//
//	/**
//	 * 拉卡拉- 存量用户检验接口
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/sxy/lakala/checkUser.do")
//	@ResponseBody
//	public LklRespData checkUser(HttpServletRequest request, HttpServletResponse reponse) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入拉卡拉存量用户检验接口"+request);
//		RsCheckUserInfo checkUserInfo = new RsCheckUserInfo();
//		//RsRequest rsRequest = new RsRequest();
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//
//			if (null==request) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("接收参数对象为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉存量用户检验接口，参数对象为空，返回结果：" + request);
//				return lklRespData;
//			}
//			String data = new SignService().verifyReceivedRequest(request);
//			logger.info("拉卡拉存量用户检验接口验签结果：" + data); 
//			if (StringUtils.isEmpty(data)) {
//				lklResponse.setCode(LklResponse.CODE_SIGNERR);
//				lklResponse.setDesc("验签不通过,返回为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉存量用户检验接口接口异常，返回结果验签不通过,参数data:"+data);
//				return lklRespData;
//			}
//			
//			//解开三元信息
//			Map<String,String> map = JSON.parseObject(data, Map.class);
//			Map<String,Object> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//			//System.out.println(mapRequest.get("loanDate"));
//			String name = String.valueOf(mapRequest.get("userName"));
//			String phone = String.valueOf(mapRequest.get("userMobile"));
//			String cid = String.valueOf(mapRequest.get("idNo"));
//			logger.info("拉卡拉存量用户检验接口参数结果：name:" + name+"phone:"+phone+"cid:"+cid); 
//			//判断增量用户
//			BwBorrower bw = bwBorrowerService.oldUserFilter(phone,cid,name);
//			if(null!=bw){
//				//老用户
//				checkUserInfo.setIsStock(1);//增量用户0否1是
//			}else{
//				checkUserInfo.setIsStock(0);
//			}
//			
//			// 第三步：用户检验
//			LklRespData checkResult = lakalaService.checkUser(sessionId, name, phone, cid);
//			String resultCode = checkResult.getHead();
//			Map<String,String> map1 = JSON.parseObject(resultCode, Map.class);
//			Map<String,Object> mapDate= new HashMap<String, Object>();
//			if(!"000000".equals(map1.get("code"))){
//				mapDate.put("userStatus", "2");
//				lklResponse.setCode(LklResponse.CODE_SUCCESS);
//				lklResponse.setDesc("查询成功");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				lklRespData.setResponse(JSON.toJSONString(mapDate));
//				return lklRespData;
//			}else{
//				if(checkUserInfo.getIsStock()==0){
//					mapDate.put("userStatus", "0");
//				}else if(checkUserInfo.getIsStock()==1){
//					mapDate.put("userStatus", "1");
//				}
//				logger.info("拉卡拉效验成功"+JSON.toJSONString(mapDate));
//				lklResponse.setCode(LklResponse.CODE_SUCCESS);
//				lklResponse.setDesc("查询成功");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				lklRespData.setResponse(JSON.toJSONString(mapDate));
//				return lklRespData;
//			}
//			
//		} catch (Exception e) {
//			logger.error(sessionId + "结束拉卡拉存量用户检验接口异常", e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("接口调用异常，请稍后再试");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			return lklRespData;
//		}
//	}
//	
//	
//	
//	@RequestMapping("/sxy/lakala/saveContact.do")
//	@ResponseBody
//	public LklResponse saveContact(HttpServletRequest request, HttpServletResponse reponse) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入拉卡拉补充信息保存接口");
//		LklResponse lklResponse = new LklResponse();
//		int result = 0;
//		try {
//
//			if (null==request) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("接收参数对象为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数对象为空，返回结果：" + request);
//				return lklResponse;
//			}
//			String wechat = request.getParameter("wechat");//微信
//			String qq = request.getParameter("qq");//qq号
//			String colleagueName = request.getParameter("colleagueName");//同事名称
//			String colleaguePhone = request.getParameter("colleaguePhone");//同事电话
//			String friendName1 = request.getParameter("friendName1");
//			String friendPhone1 = request.getParameter("friendPhone1");
//			String friendName2 = request.getParameter("friendName2");
//			String friendPhone2 = request.getParameter("friendPhone2");
//			String orderId = request.getParameter("orderId");
//			if (StringUtils.isEmpty(wechat)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数wechat为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数wechat为空：" + wechat);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(qq)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数qq为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数qq为空：" + qq);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(colleagueName)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数colleagueName为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数colleagueName为空：" + colleagueName);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(colleaguePhone))) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数colleaguePhone为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数colleaguePhone为空：" + colleaguePhone);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(friendName1)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数friendName1为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数friendName1为空：" + friendName1);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(friendPhone1))) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数friendPhone1为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数friendPhone1为空：" + friendPhone1);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(friendName2)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数friendName2为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数friendName2为空：" + friendName2);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(friendPhone2))) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数friendPhone2为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数friendPhone2为空：" + friendPhone2);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(orderId))) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数orderId为空");
//				logger.info(sessionId + "结束拉卡拉补充信息保存接口，参数orderId为空：" + orderId);
//				return lklResponse;
//			}
//			// 亲属联系人
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			if(null==bwPersonInfo){
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("用户表为空,数据存储异常");
//				return lklResponse;
//						
//			}else{
//				bwPersonInfo.setColleagueName(colleagueName);
//				bwPersonInfo.setColleaguePhone(colleaguePhone);
//				bwPersonInfo.setFriend1Name(friendName1);
//				bwPersonInfo.setFriend1Phone(friendPhone1);
//				bwPersonInfo.setFriend2Name(friendName2);
//				bwPersonInfo.setFriend2Phone(friendPhone2);			
//				bwPersonInfo.setQqchat(qq);//qq
//				bwPersonInfo.setWechat(wechat);//微信
//				result = bwPersonInfoService.update(bwPersonInfo);
//			}
//			
//		} catch (Exception e) {
//			logger.error(sessionId + "结束拉卡拉补充信息保存接口异常", e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("接口调用异常，请稍后再试");
//		}
//		if(result>0){
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("拉卡拉补充信息存储成功");			
//		}else{
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("拉卡拉补充信息存储失败");
//		}
//		logger.info(sessionId + "结束拉卡拉补充信息保存接口，返回结果：" + JSON.toJSONString(lklResponse));
//		return lklResponse;
//	}
//	
//	
//	@RequestMapping("/sxy/lakala/getUserInfo.do")
//	@ResponseBody
//	public LklResponse getUserInfo(HttpServletRequest request, HttpServletResponse reponse) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入拉卡拉查询用户信息接口");
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		Map<String,Object> map = new HashMap<String, Object>();
//		try {
//
//			String orderId = request.getParameter("orderId");
//			if (StringUtils.isEmpty(String.valueOf(orderId))) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数orderId为空");
//				logger.info(sessionId + "结束拉卡拉查询用户信息接口，参数orderId为空：" + orderId);
//				return lklResponse;
//			}
//			
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setId(Long.parseLong(orderId));
//			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//			if(null==bwOrder){
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("订单表为空,查询异常");
//				return lklResponse;
//			}
//			BwBorrower bwBorrower = new BwBorrower();
//			bwBorrower.setId(bwOrder.getBorrowerId());
//			bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//			if(null==bwBorrower){
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("用户表为空,数据存储异常");
//				return lklResponse;
//			}else{
//				map.put("orderId",orderId);
//				map.put("borrower_id", bwBorrower.getId());
//				map.put("name", bwBorrower.getName());
//				map.put("id_card", bwBorrower.getIdCard());
//				map.put("phone", bwBorrower.getPhone());
//				lklResponse.setCode(LklResponse.CODE_SUCCESS);
//				lklResponse.setDesc("查询成功");
//				lklRespData.setResponse(JSON.toJSONString(map));
//			}
//			
//		} catch (Exception e) {
//			logger.error(sessionId + "结束拉卡拉查询用户信息接口异常", e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("接口调用异常，请稍后再试");
//		}
//		logger.info(sessionId + "结束拉卡拉补充信息保存接口，返回结果：" + JSON.toJSONString(lklResponse));
//		return lklResponse;
//	}
//	
//	
//
//	/**
//	 * 拉卡拉 -贷款试算器接口
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/sxy/lakala/loanCalculation.do")
//	@ResponseBody
//	public LklRespData loanCalculation(HttpServletRequest request, HttpServletResponse reponse) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入拉卡拉贷款试算期接口");
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			if (null==request) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("接收参数对象为空");
//				logger.info(sessionId + "结束拉卡拉存量用户检验接口，参数对象为空，返回结果：" + request);
//				return lklRespData;
//			}
//			String data = new SignService().verifyReceivedRequest(request);
//			logger.info("拉卡拉存量用户检验接口验签结果：" + data); 
//			if (StringUtils.isEmpty(data)) {
//				lklResponse.setCode(LklResponse.CODE_SIGNERR);
//				lklResponse.setDesc("验签不通过,返回为空");
//				logger.info(sessionId + "结束拉卡拉存量用户检验接口接口异常，返回结果验签不通过,参数data:"+data);
//				return lklRespData;
//			}
//
//			//解开数据
//			Map<String,String> map = JSON.parseObject(data, Map.class);
//			Map<String,Object> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//
//			Double loanAmount = Double.parseDouble(String.valueOf(mapRequest.get("applyAmt")))/100;//借款金额
//			
//			String applySerial = String.valueOf(mapRequest.get("applySerial"));
//		
//		
//			logger.info(sessionId + "得到拉卡拉参数,参数loanAmount:"+loanAmount+",参数applyserial:"+applySerial);
//			//查询水象云产品
//			BwProductDictionary bwProductDictionary = bwProductDictionaryService
//								.findBwProductDictionaryById(Integer.valueOf(7));
//			// 第四步，获取放款金额限制
//			Integer maxLoanAmount = bwProductDictionary.getMaxAmount();
//			Integer minLoanAmount = bwProductDictionary.getMinAmount();
//			Double interestRate = bwProductDictionary.getInterestRate();// 分期利息率
//
//			if (loanAmount > maxLoanAmount) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("借款金额不通过");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉贷款试算期接口异常，返回结果借款金额过大,参数loanAmount:"+loanAmount);
//				return lklRespData;
//			} else if (loanAmount < minLoanAmount) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("借款金额不通过");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉贷款试算期接口异常，返回结果借款金额过小,参数loanAmount:"+loanAmount);
//				return lklRespData;
//			}
//			
//			List<Map<String,Object>> productList = new ArrayList<Map<String,Object>>();
//			List<Map<String,Object>> scheduleList = null;
//			// 每期应还本金
//			Double eachAmount = Double.valueOf(loanAmount) / 4;
//			logger.info(sessionId + "拉卡拉每期应还本金:"+eachAmount);
//			// 每期应还利息
//			//Double periodOne = LakalaUtils.calculateRepayMoney(loanAmount, 1, interestRate);
//			//Double periodTwo = LakalaUtils.calculateRepayMoney(loanAmount, 2, interestRate);
//			//Double periodThree = LakalaUtils.calculateRepayMoney(loanAmount, 3, interestRate);
//			//Double periodFour = LakalaUtils.calculateRepayMoney(loanAmount, 4, interestRate);
//
//			//Double periodAll = periodOne + periodTwo + periodThree + periodFour;	
//			//Double amount = Double.valueOf(loanAmount) + periodAll;
//			Map<String,Object> productMap = null;
//			Map<String,Object> scheduleMap = null;
//			for(int i=1;i<5;i++){
//				scheduleList = new ArrayList<Map<String,Object>>();
//				Double fee = LakalaUtils.calculateRepayMoney(loanAmount, i, interestRate);
//				productMap = new HashMap<String,Object>();
//				productMap.put("totalAmt", Integer.parseInt(String.valueOf((eachAmount+fee)*100)));//当期应还金额（分）
//				productMap.put("term", i);
//				productMap.put("dueDate", LakalaUtils.getFetureDate(i*7));
//				for(int j=0;j<2;j++){
//					scheduleMap = new HashMap<String,Object>();
//					scheduleMap.put("assetType",0==j?"本金":"利息");
//					scheduleMap.put("amtReceivable",0==j?Integer.parseInt(String.valueOf(eachAmount*100)):Integer.parseInt(String.valueOf(fee*100)));
//					scheduleList.add(scheduleMap);
//				}
//				productMap.put("scheduleList", scheduleList);
//				productList.add(productMap);
//			}
//			
//			Map<String,Object> mapData = new TreeMap<String, Object>(); 
//			mapData.put("applySerial", applySerial);
//			mapData.put("interestMode", 30);
//			mapData.put("interestRt", interestRate/7);//基础利率
//			mapData.put("productList", productList);
//			logger.info(sessionId + "拉卡拉响应参数:"+JSON.toJSONString(mapData));
//			//mapData.put("refundAmount", amount);//还款总金额
//			//mapData.put("actualAmount", loanAmount);//实际到账金额
//			//mapData.put("remark", "到账金额"+loanAmount+"元，手续费"+periodAll+"元，预计还款总金额"+amount+"元");//到账金额描述
//			
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("查询贷款试算接口成功");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			lklRespData.setResponse(JSON.toJSONString(mapData));
//					
//		} catch (Exception e) {
//			logger.error(sessionId + "执行拉卡拉贷款试算期接口异常", e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("接口调用异常，请稍后再试");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//		logger.info(sessionId + "结束拉卡拉贷款试算期接口，返回结果：" + JSON.toJSONString(lklResponse));
//		return lklRespData;
//	}
//
//	/**
//	 * 拉卡拉 - 进件推送接口
//	 *
//	 * @return
//	 */
//	@RequestMapping("/sxy/lakala/pushOrder.do")
//	@ResponseBody
//	public LklRespData pushOrder(HttpServletRequest request, HttpServletResponse reponse) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入拉卡拉进件推送接口");
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			if (null==request) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("接收参数对象为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉存量用户检验接口，参数对象为空，返回结果：" + request);
//				return lklRespData;
//			}
//			String data = new SignService().verifyReceivedRequest(request);
//			logger.info("拉卡拉存量用户检验接口验签结果：" + data); 
//			if (StringUtils.isEmpty(data)) {
//				lklResponse.setCode(LklResponse.CODE_SIGNERR);
//				lklResponse.setDesc("验签不通过,返回为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉存量用户检验接口接口异常，返回结果验签不通过,参数data:"+data);
//				return lklRespData;
//			}
//
//			//解开数据
//			Map<String,String> map = JSON.parseObject(data, Map.class);
//			Map<String,Object> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//			lklRespData = lakalaService.savePushOrder(sessionId, mapRequest);
//
//			
//		} catch (Exception e) {
//			logger.error(sessionId + "执行拉卡拉进件推送接口异常", e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("进件推送接口调用异常，请稍后再试");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//		logger.info(sessionId + "结束拉卡拉进件推送接口，返回结果：" + JSON.toJSONString(lklResponse));
//		return lklRespData;
//	}
//
//	// 审核结果查询接口
//	/*@RequestMapping("/sxy/rongShu/queryAuditResult.do")
//	@ResponseBody
//	public LklResponse queryAuditResult(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 进件推送接口");
//		LklResponse lklResponse = new LklResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数channelId为空");
//				logger.info(sessionId + "结束榕树 审核结果查询接口，参数channelId为空：" + channelId);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数sign为空");
//				logger.info(sessionId + "结束榕树 审核结果查询接口，参数sign为空：" + sign);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数request为空");
//				logger.info(sessionId + "结束榕树 审核结果查询接口，参数request为空：" + requestData);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 审核结果查询接口，参数timestamp为空：" + timestamp);
//				return lklResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("进件推送接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				lklResponse.setCode(LklResponse.CODE_SIGNERR);
//				lklResponse.setDesc("验签不通过");
//				logger.info(sessionId + "结束审核结果查询接口异常，返回结果验签不通过,参数sign:"+sign);
//				return lklResponse;
//			}
//			
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			lklResponse = rongShuNewService.getApprovalResult(sessionId, map);
//		} catch (Exception e) {
//			logger.info("榕树拉取订单状态异常" + e);
//			lklResponse.setCode(RongShuResponse.CODE_FAIL);
//			lklResponse.setDesc("接口调用异常，请稍后再试");
//		}
//		logger.info(sessionId + "结束 审核结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//		return lklResponse;
//	}*/
//
//
//	//绑卡 结果 查询 接口
//	/*@ResponseBody
//	@RequestMapping("/sxy/rongShu/queryBindCardResult.do")
//	public LklResponse queryBindCardResult(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 绑卡结果查询接口");
//		LklResponse lklResponse = new LklResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数channelId为空");
//				logger.info(sessionId + "结束榕树 绑卡结果查询接口，参数channelId为空：" + channelId);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数sign为空");
//				logger.info(sessionId + "结束榕树 绑卡结果查询接口，参数sign为空：" + sign);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数request为空");
//				logger.info(sessionId + "结束榕树 绑卡结果查询接口，参数request为空：" + requestData);
//				return lklResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 绑卡结果查询接口，参数timestamp为空：" + timestamp);
//				return lklResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("绑卡结果查询接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				lklResponse.setCode(LklResponse.CODE_SIGNERR);
//				lklResponse.setDesc("验签不通过");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口异常，返回结果验签不通过,参数sign:"+sign);
//				return lklResponse;
//			}
//			
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			String thirdOrderNo = map.get("orderId");
//			if(StringUtils.isBlank(thirdOrderNo)){
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("参数orderId为空");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口异常，参数orderId为空,参数orderId:"+thirdOrderNo);
//				return lklResponse;
//			}
//			BwOrderRong bwRong = new BwOrderRong();
//			bwRong.setThirdOrderNo(thirdOrderNo);
//			bwRong = bwOrderRongService.findBwOrderRongByAttr(bwRong);
//			if (CommUtils.isNull(bwRong)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("工单不存在");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklResponse;
//			}
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setId(bwRong.getOrderId());
//			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//			if (CommUtils.isNull(bwOrder)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("工单不存在");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklResponse;
//			}
//
//			// 查询银行卡信息
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
//			bwBankCard = findBwBankCardByAttrProxy(bwBankCard);
//			if (bwBankCard == null) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("银行卡信息不存在");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklResponse;
//			}
//
//			Map<String, String> data = new HashMap<>();
//			if (bwBankCard.getSignStatus() >=1) {
//				data.put("status","3001");//绑卡成功
//			} else {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("绑卡状态为未绑定");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklResponse;
//			}
//			String bankCode = bwBankCard.getBankCode();
//			if(!StringUtils.isBlank(bankCode)){
//				bankCode = DrainageUtils.convertFuiouBankCodeToBaofu(bankCode);
//				if("GDB".equals(bankCode)){
//					bankCode = "CGB";
//				}
//				else if("BCOM".equals(bankCode)){
//					bankCode = "BCM";
//				}
//				
//			}
//			data.put("bankCode", bankCode);
//			data.put("bankName", bwBankCard.getBankName());
//			data.put("bankCardNum", bwBankCard.getCardNo());
//			data.put("phone", bwBankCard.getPhone());
//
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("查询成功");
//			lklResponse.setResponse(JSON.toJSONString(data));
//		} catch (Exception e) {
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("榕树绑卡发生异常");
//			logger.debug(sessionId + "榕树绑卡结果查询接口异常," + e);
//		}
//		logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//		return lklResponse;
//	}*/
//
//	
//	// 放款结果查询接口
//		/*@RequestMapping("/sxy/rongShu/queryFk.do")
//		@ResponseBody
//		public LklResponse queryFkResult(@RequestBody RsRequest request) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入榕树 放款结果查询接口");
//			LklResponse lklResponse = new LklResponse();
//			try {
//				// 第一步：取参数
//				String sign = request.getSign();
//				String channelId = request.getChannelId();
//				String requestData = request.getRequest();
//				Long timestamp = request.getTimestamp();
//				// 第二步：参数验证
//				if (StringUtils.isEmpty(channelId)) {
//					lklResponse.setCode(LklResponse.CODE_PARMERR);
//					lklResponse.setDesc("参数channelId为空");
//					logger.info(sessionId + "结束榕树 放款结果查询接口，参数channelId为空：" + channelId);
//					return lklResponse;
//				}
//				if (StringUtils.isEmpty(sign)) {
//					lklResponse.setCode(LklResponse.CODE_PARMERR);
//					lklResponse.setDesc("参数sign为空");
//					logger.info(sessionId + "结束榕树 放款结果查询接口，参数sign为空：" + sign);
//					return lklResponse;
//				}
//				if (StringUtils.isEmpty(requestData)) {
//					lklResponse.setCode(LklResponse.CODE_PARMERR);
//					lklResponse.setDesc("参数request为空");
//					logger.info(sessionId + "结束榕树 放款结果查询接口，参数request为空：" + requestData);
//					return lklResponse;
//				}
//				if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//					lklResponse.setCode(LklResponse.CODE_PARMERR);
//					lklResponse.setDesc("参数timestamp为空");
//					logger.info(sessionId + "结束榕树 放款结果查询接口，参数timestamp为空：" + timestamp);
//					return lklResponse;
//				}
//
//				Map<String, String> paramMap = new TreeMap<String, String>();
//				paramMap.put("channelId", channelId);
//				paramMap.put("request", requestData);
//				paramMap.put("timestamp", String.valueOf(timestamp));
//				// 第三步验签
//				logger.info("放款结果查询接口-------开始验签-------");
//				boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//				if (!isOk) {
//					lklResponse.setCode(LklResponse.CODE_SIGNERR);
//					lklResponse.setDesc("验签不通过");
//					logger.info(sessionId + "结束榕树放款结果查询接口异常，返回结果验签不通过,参数sign:"+sign);
//					return lklResponse;
//				}
//				
//				Map<String,String> map = JSON.parseObject(requestData, Map.class);
//				lklResponse = rongShuNewService.queryFk(sessionId, map);
//
//			} catch (Exception e) {
//				logger.error("放款结果查询接口异常", e);
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("接口调用异常，请稍后再试");
//			}
//			logger.info(sessionId + "结束放款结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//			return lklResponse;
//		}*/
//
//		
//		// 还款计划查询接口
//		@RequestMapping("/sxy/lakala/getRepayPlan.do")
//		@ResponseBody
//		public LklRespData getRepayPlan(HttpServletRequest request, HttpServletResponse reponse) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入拉卡拉还款计划查询接口");
//			LklResponse lklResponse = new LklResponse();
//			LklRespData lklRespData = new LklRespData();
//			try {
//				if (null==request) {
//					lklResponse.setCode(LklResponse.CODE_PARMERR);
//					lklResponse.setDesc("接收参数对象为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉还款计划接口，参数对象为空，返回结果：" + request);
//					return lklRespData;
//				}
//				String data = new SignService().verifyReceivedRequest(request);
//				logger.info("拉卡拉还款计划接口验签结果：" + data); 
//				if (StringUtils.isEmpty(data)) {
//					lklResponse.setCode(LklResponse.CODE_SIGNERR);
//					lklResponse.setDesc("验签不通过,返回为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉还款计划接口异常，返回结果验签不通过,参数data:"+data);
//					return lklRespData;
//				}
//				
//				Map<String,String> map = JSON.parseObject(data, Map.class);
//				Map<String,String> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//				
//				lklRespData = lakalaService.getRepayPlan(sessionId, mapRequest); 
//
//			} catch (Exception e) {
//				logger.error("拉卡拉还款计划查询接口异常", e);
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("接口调用异常，请稍后再试");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//			}
//			logger.info(sessionId + "拉卡拉还款计划查询接口，返回结果：" + JSON.toJSONString(lklRespData));
//			return lklRespData;
//		}
//	
//		// 主动还款试算
//		@RequestMapping("/sxy/lakala/getPaymentCell.do")
//		@ResponseBody
//		public LklRespData getPaymentCell(HttpServletRequest request, HttpServletResponse reponse) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入拉卡拉还款试算接口");
//			LklResponse lklResponse = new LklResponse();
//			LklRespData lklRespData = new LklRespData();
//			try {
//				if (null==request) {
//					lklResponse.setCode(lklResponse.CODE_PARMERR);
//					lklResponse.setDesc("接收参数对象为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉还款试算接口，参数对象为空，返回结果：" + request);
//					return lklRespData;
//				}
//				String data = new SignService().verifyReceivedRequest(request);
//				logger.info("拉卡拉还款试算验签结果：" + data); 
//				if (StringUtils.isEmpty(data)) {
//					lklResponse.setCode(LklResponse.CODE_SIGNERR);
//					lklResponse.setDesc("验签不通过,返回为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉还款试算异常，返回结果验签不通过,参数data:"+data);
//					return lklRespData;
//				}
//				
//				Map<String,String> map = JSON.parseObject(data, Map.class);
//				Map<String,String> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//				lklRespData = lakalaService.getPaymentCell(sessionId, mapRequest);
//
//			} catch (Exception e) {
//				logger.error("拉卡拉还款试算接口异常", e);
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("接口调用异常，请稍后再试");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			logger.info(sessionId + "结束拉卡拉还款试算接口，返回结果：" + JSON.toJSONString(lklRespData));
//			return lklRespData;
//		}
//	
//		
//		//主动还款接口
//		@RequestMapping("/sxy/lakala/repayment.do")
//		@ResponseBody
//		public LklRespData repayment(HttpServletRequest request, HttpServletResponse reponse) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入拉卡拉主动还款接口");
//			LklResponse lklResponse = new LklResponse();
//			LklRespData lklRespData = new LklRespData();
//			try {
//				if (null==request) {
//					lklResponse.setCode(lklResponse.CODE_PARMERR);
//					lklResponse.setDesc("接收参数对象为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉主动还款接口，参数对象为空，返回结果：" + request);
//					return lklRespData;
//				}
//				String data = new SignService().verifyReceivedRequest(request);
//				logger.info("拉卡拉主动还款验签结果：" + data); 
//				if (StringUtils.isEmpty(data)) {
//					lklResponse.setCode(LklResponse.CODE_SIGNERR);
//					lklResponse.setDesc("验签不通过,返回为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉主动还款异常，返回结果验签不通过,参数data:"+data);
//					return lklRespData;
//				}
//				
//				Map<String,String> map = JSON.parseObject(data, Map.class);
//				Map<String,String> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//				
//				lklRespData = lakalaService.updateRepayment(sessionId, mapRequest);
//
//			} catch (Exception e) {
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("发生异常");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "拉卡拉主动还款接口，返回结果：" + JSON.toJSONString(lklResponse));
//			}
//			logger.info(sessionId + "拉卡拉主动还款调用成功，返回结果：" + JSON.toJSONString(lklResponse));
//			return lklRespData;
//
//		}
//		
//	
//	//还款结果
//	@RequestMapping("/sxy/lakala/getRepaymentResult.do")
//	@ResponseBody
//	public LklRespData getRepaymentResult(HttpServletRequest request, HttpServletResponse reponse) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入拉卡拉主动还款接口");
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			if (null==request) {
//				lklResponse.setCode(lklResponse.CODE_PARMERR);
//				lklResponse.setDesc("接收参数对象为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉主动还款接口，参数对象为空，返回结果：" + request);
//				return lklRespData;
//			}
//			String data = new SignService().verifyReceivedRequest(request);
//			logger.info("拉卡拉主动还款验签结果：" + data); 
//			if (StringUtils.isEmpty(data)) {
//				lklResponse.setCode(LklResponse.CODE_SIGNERR);
//				lklResponse.setDesc("验签不通过,返回为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉主动还款异常，返回结果验签不通过,参数data:"+data);
//				return lklRespData;
//			}
//			
//			Map<String,String> map = JSON.parseObject(data, Map.class);
//			Map<String,String> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//			lklRespData = lakalaService.getRepaymentResult(sessionId, mapRequest);
//
//		} catch (Exception e) {
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("发生异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info(sessionId + "拉卡拉主动还款接口异常，返回结果：" + JSON.toJSONString(lklResponse));
//		}
//		logger.info(sessionId + "拉卡拉主动还款接口，返回结果：" + JSON.toJSONString(lklResponse));
//		return lklRespData;
//
//	}
//	
//	
//	//合同列表接口
//	@RequestMapping("/sxy/lakala/getContracts.do")
//	@ResponseBody
//	public LklRespData getContracts(HttpServletRequest request, HttpServletResponse reponse) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入拉卡拉合同列表接口");
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			if (null==request) {
//				lklResponse.setCode(lklResponse.CODE_PARMERR);
//				lklResponse.setDesc("接收参数对象为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉合同列表接口，参数对象为空，返回结果：" + request);
//				return lklRespData;
//			}
//			String data = new SignService().verifyReceivedRequest(request);
//			logger.info("拉卡拉合同列表接口验签结果：" + data); 
//			if (StringUtils.isEmpty(data)) {
//				lklResponse.setCode(LklResponse.CODE_SIGNERR);
//				lklResponse.setDesc("验签不通过,返回为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉合同列表接口异常，返回结果验签不通过,参数data:"+data);
//				return lklRespData;
//			}
//
//			//解开数据
//			Map<String,String> map = JSON.parseObject(data, Map.class);
//			Map<String,String> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//			lklRespData = lakalaService.getContracts(sessionId, mapRequest);
//
//		} catch (Exception e) {
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("发生异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info(sessionId + "结束拉卡拉合同列表接口，返回结果：" + JSON.toJSONString(lklRespData));
//			return lklRespData;
//		}
//		logger.info(sessionId + "结束拉卡拉合同列表接口，返回结果：" + JSON.toJSONString(lklRespData));
//		return lklRespData;
//	}
//	
//	
//	//确认贷款(签约)
//	@RequestMapping("/sxy/lakala/confirmLoan.do")
//	@ResponseBody
//	public LklRespData confirmLoan(HttpServletRequest request, HttpServletResponse reponse) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入拉卡拉确认贷款接口");
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData(); 
//		try {
//			if (null==request) {
//				lklResponse.setCode(lklResponse.CODE_PARMERR);
//				lklResponse.setDesc("接收参数对象为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉确认贷款接口，参数对象为空，返回结果：" + request);
//				return lklRespData;
//			}
//			String data = new SignService().verifyReceivedRequest(request);
//			logger.info("拉卡拉确认贷款验签结果：" + data); 
//			if (StringUtils.isEmpty(data)) {
//				lklResponse.setCode(LklResponse.CODE_SIGNERR);
//				lklResponse.setDesc("验签不通过,返回为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉确认贷款异常，返回结果验签不通过,参数data:"+data);
//				return lklRespData;
//			}
//
//			//解开数据
//			Map<String,String> map = JSON.parseObject(data, Map.class);
//			Map<String,String> mapRequest = JSON.parseObject(map.get("request"),Map.class);
//			//Map<String,String> mapRequest = new HashMap<String,String>();
//			//mapRequest.put("applySerial", "APS2018062900000180806SN");
//			lklRespData = lakalaService.updateLoan(sessionId, mapRequest);
//			logger.info(sessionId + "结束拉卡拉确认贷款接口，返回结果：" + JSON.toJSONString(lklRespData));
//            return lklRespData;
//		} catch (Exception e) {
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("发生异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info(sessionId + "结束拉卡拉确认贷款接口，返回结果：" + JSON.toJSONString(lklResponse));
//			return lklRespData;
//		}
//	}
//	
//	
//	    // 6.1.协议绑卡预绑卡接口
//		@RequestMapping("/sxy/lakala/bindCardReady.do")
//		@ResponseBody
//		public LklRespData saveBindCardReady(HttpServletRequest request, HttpServletResponse reponse) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入拉卡拉绑卡接口");
//			LklResponse lklResponse = new LklResponse();
//			LklRespData lklRespData = new LklRespData();
//			try {
//				if (null==request) {
//					lklResponse.setCode(lklResponse.CODE_PARMERR);
//					lklResponse.setDesc("接收参数对象为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉绑卡接口，参数对象为空，返回结果：" + request);
//					return lklRespData;
//				}
//				String data = new SignService().verifyReceivedRequest(request);
//				logger.info("拉卡拉绑卡接口验签结果：" + data); 
//				if (StringUtils.isEmpty(data)) {
//					lklResponse.setCode(LklResponse.CODE_SIGNERR);
//					lklResponse.setDesc("验签不通过,返回为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉绑卡接口异常，返回结果验签不通过,参数data:"+data);
//					return lklRespData;
//				}
//				
//				Map<String,String> mapdata = JSON.parseObject(data, Map.class);
//				Map<String,String> mapRequest = JSON.parseObject(mapdata.get("request"),Map.class);
//				
//				//BankCardNewInfo bankcardinfo = JSONObject.parseObject(requestData, BankCardNewInfo.class);
//				//String bankName = bankcardinfo.getBankName();
//				String idNumber = mapRequest.get("idNo");
//				String bankCode = mapRequest.get("bankCode");
//				bankCode = LakalaUtils.getBankCode(bankCode); 
//				String thirdOrderNo = String.valueOf(mapRequest.get("applySerial"));
//				String phone = mapRequest.get("bankMobile");
//				logger.info(sessionId + "拉卡拉预绑卡参数,参数bankCode:"+bankCode);
//				// 获取借款人信息
//				BwBorrower borrower = new BwBorrower();
//				borrower.setIdCard(idNumber);
//				borrower.setPhone(phone);
//				borrower.setFlag(1);// 未删除的
//				borrower = findBwBorrowerByAttrProxy(borrower);
//				logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));
//				// 查找订单orderNO
//				BwOrderRong bwOrderRong = new BwOrderRong();
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//				logger.info("订单查询结果：" + JSONObject.toJSONString(bwOrderRong));
//				//String userId = String.valueOf(borrower.getId());// 借款人id
//				String accName = mapRequest.get("userName");// 姓名
//				String cardNo = mapRequest.get("acctNo");// 银行卡号
//				String ptChannelId = LakalaConstant.channelId;//平台渠道号
//				Map<String, Object> mapResp = new HashMap<String, Object>();
//				if (!CommUtils.isNull(bwOrderRong)) {
//					BwOrder bwOrder = new BwOrder();
//					bwOrder.setId(bwOrderRong.getOrderId());
//					bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//					DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//					drainageBindCardVO.setBankCardNo(cardNo);
//					drainageBindCardVO.setBankCode(bankCode);
//					drainageBindCardVO.setChannelId(Integer.parseInt(ptChannelId));
//					drainageBindCardVO.setName(accName);
//					drainageBindCardVO.setPhone(phone);
//					drainageBindCardVO.setThirdOrderNo(thirdOrderNo);
//					drainageBindCardVO.setIdCardNo(idNumber);
//					drainageBindCardVO.setRegPhone(phone);
//					logger.info("拉卡拉调用预绑卡接口入参:"+JSON.toJSONString(drainageBindCardVO));
//					// 公共绑卡接口
//					DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//					logger.info("拉卡拉调用预绑卡接口结果:"+JSON.toJSONString(drainageRsp));
//							if (null != drainageRsp) {
//								if ("0000".equals(drainageRsp.getCode())) {
//									//预绑卡成功
//									lklResponse.setCode(LklResponse.CODE_SUCCESS);
//									lklResponse.setDesc("预绑卡成功");
//									mapResp.put("status", "3002");
//									mapResp.put("remark", "预绑卡成功");
//									lklRespData.setHead(JSON.toJSONString(lklResponse));
//									lklRespData.setResponse(JSON.toJSONString(mapResp));
//								}else{
//									//预绑卡成功
//									lklResponse.setCode(LklResponse.CODE_SUCCESS);
//									lklResponse.setDesc("预绑卡失败");
//									mapResp.put("status", "3003");
//									mapResp.put("remark", drainageRsp.getMessage());
//									lklRespData.setHead(JSON.toJSONString(lklResponse));
//									lklRespData.setResponse(JSON.toJSONString(mapResp));
//								}
//							}
//				} else {
//					lklResponse.setCode(LklResponse.CODE_PARMERR);
//					lklResponse.setDesc("未找到该工单");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//				}
//
//			} catch (Exception e) {
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("发生异常");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "拉卡拉绑定银行卡接口发生异常"+e);
//			}
//
//			return lklRespData;
//
//		}
//	
//		
//		
//	    // 6.1.协议绑卡提交接口
//		@RequestMapping("/sxy/lakala/bindCardSure.do")
//		@ResponseBody
//		public LklRespData saveBindCardSure(HttpServletRequest request, HttpServletResponse reponse) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入拉卡拉绑卡提交接口");
//			LklResponse lklResponse = new LklResponse();
//			LklRespData lklRespData = new LklRespData();
//			try {
//				if (null==request) {
//					lklResponse.setCode(lklResponse.CODE_PARMERR);
//					lklResponse.setDesc("接收参数对象为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉绑卡提交接口，参数对象为空，返回结果：" + request);
//					return lklRespData;
//				}
//				String data = new SignService().verifyReceivedRequest(request);
//				logger.info("拉卡拉绑卡提交接口验签结果：" + data); 
//				if (StringUtils.isEmpty(data)) {
//					lklResponse.setCode(LklResponse.CODE_SIGNERR);
//					lklResponse.setDesc("验签不通过,返回为空");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					logger.info(sessionId + "结束拉卡拉绑卡提交接口异常，返回结果验签不通过,参数data:"+data);
//					return lklRespData;
//				}
//				
//				Map<String,String> mapdata = JSON.parseObject(data, Map.class);
//				Map<String,String> mapRequest = JSON.parseObject(mapdata.get("request"),Map.class);
//				//String bankName = bankcardinfo.getBankName();
//				String captcha = String.valueOf(mapRequest.get("smsno"));
//				String thirdOrderNo = String.valueOf(mapRequest.get("applySerial"));
//				String phone = String.valueOf(mapRequest.get("bankMobile"));
//				String ptChannelId = LakalaConstant.channelId;//平台渠道号
//				String idNumber = String.valueOf(mapRequest.get("idNo"));
//				// 获取借款人信息
//				BwBorrower borrower = new BwBorrower();
//				borrower.setIdCard(idNumber);
//				borrower.setPhone(phone);
//				borrower.setFlag(1);// 未删除的
//				borrower = findBwBorrowerByAttrProxy(borrower);
//				logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));
//				// 查找订单orderNO
//				BwOrderRong bwOrderRong = new BwOrderRong();
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//				logger.info("订单查询结果：" + JSONObject.toJSONString(bwOrderRong));
//
//				Map<String, Object> mapResp = new HashMap<String, Object>();
//				if (!CommUtils.isNull(bwOrderRong)) {
//					BwOrder bwOrder = new BwOrder();
//					bwOrder.setId(bwOrderRong.getOrderId());
//					bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//					
//					DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//					drainageBindCardVO.setVerifyCode(captcha);
//					drainageBindCardVO.setChannelId(Integer.parseInt(ptChannelId));
//					drainageBindCardVO.setPhone(phone);
//					drainageBindCardVO.setThirdOrderNo(thirdOrderNo);
//					drainageBindCardVO.setNotify(true);//存redis
//					logger.info("拉卡拉绑卡提交接口入参:"+JSON.toJSONString(drainageBindCardVO));
//					// 公共绑卡接口
//					DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//					logger.info("拉卡拉绑卡提交接口结果:"+JSON.toJSONString(drainageRsp));
//							if (null != drainageRsp) {
//								if ("0000".equals(drainageRsp.getCode())) {
//									//绑卡成功
//									lklResponse.setCode(LklResponse.CODE_SUCCESS);
//									lklResponse.setDesc("绑卡成功");
//									lklRespData.setHead(JSON.toJSONString(lklResponse));
//									mapResp.put("status", "3001");
//									mapResp.put("remark", "绑卡成功");
//									lklRespData.setResponse(JSON.toJSONString(mapResp));
//									
//									//更新订单进件状态
//									bwOrder.setStatusId(2L);
//									bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//									bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//									bwOrderService.updateBwOrder(bwOrder);
//									
//									/**
//									 ** 订单状态写入redis
//									 */
//									HashMap<String, String> hm = new HashMap<String, String>();
//									hm.put("channelId", LakalaConstant.channelId + "");
//									hm.put("orderId", String.valueOf(bwOrder.getId()));
//									hm.put("orderStatus", "2");
//									hm.put("result", "");
//									String hmData = JSON.toJSONString(hm);
//									RedisUtils.rpush("tripartite:orderStatusNotify:" + LakalaConstant.channelId, hmData);
//									// 审核 放入redis
//									SystemAuditDto systemAuditDto = new SystemAuditDto();
//									systemAuditDto.setIncludeAddressBook(1);
//									systemAuditDto.setOrderId(bwOrder.getId());
//									systemAuditDto.setBorrowerId(borrower.getId());
//									systemAuditDto.setName(borrower.getName());
//									systemAuditDto.setPhone(borrower.getPhone());
//									systemAuditDto.setIdCard(borrower.getIdCard());
//									systemAuditDto.setChannel(Integer.parseInt(LakalaConstant.channelId));
//									systemAuditDto.setThirdOrderId(thirdOrderNo);
//									systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//									RedisUtils.hset(SystemConstant.AUDIT_KEY, bwOrder.getId() + "", JsonUtils.toJson(systemAuditDto));
//									logger.info(sessionId + ">>> 修改订单状态，并放入redis,参数:"+JSON.toJSONString(systemAuditDto));
//									
//								}
//							}
//				} else {
//					
//					lklResponse.setCode(LklResponse.CODE_PARMERR);
//					lklResponse.setDesc("未找到该工单");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//				}
//
//			} catch (Exception e) {
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("发生异常");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "拉卡拉绑卡提交接口发生异常"+e);
//			}
//
//			return lklRespData;
//
//		}
//	
//
//	/**
//	 * 查询银行卡信息
//	 * 
//	 * @param bwBankCard
//	 * @return
//	 */
//	private BwBankCard findBwBankCardByAttrProxy(BwBankCard bwBankCard) {
//		bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//		logger.info("银行卡信息查询结果：bwBankCard=" + JSONObject.toJSONString(bwBankCard));
//		return bwBankCard;
//	}
//
//	/**
//	 * 查找借款人
//	 * 
//	 * @param borrower
//	 * @return
//	 */
//	private BwBorrower findBwBorrowerByAttrProxy(BwBorrower borrower) {
//		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//		return borrower;
//	}
//
//}
