//
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.dto.DrainageBankSupportDto;
//import com.waterelephant.dto.PaySignDto;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwOrderService;
//import com.waterelephant.sxyDrainage.service.ThirdBindCardService;
//import com.waterelephant.sxyDrainage.utils.BankUtils;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.KaNiuConstant;
//import com.waterelephant.third.utils.HttpClientHelper;
//import com.waterelephant.utils.AppResponseResult;
//import com.waterelephant.utils.MD5Util;
//import com.waterelephant.utils.RedisUtils;
//
///**
// * 
// * @ClassName: ThirdBindCardServiceImpl  
// * @Description:   
// * @author liwanliang  
// * @date 2018年5月16日  
// *
// */
//@Service
//public class ThirdBindCardServiceImpl implements ThirdBindCardService {
//
//	private Logger logger = LoggerFactory.getLogger(ThirdBindCardServiceImpl.class);
//	
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//	
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//	
//	@Autowired
//	private BwOrderService bwOrderService;
//	
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	
//	
//	
//
//
//	//获取支持的银行
//	@Override
//	public AppResponseResult querySupportBank(String cardNo) {
//		AppResponseResult result = new AppResponseResult();
//		DrainageBankSupportDto bankSupportInfo = new DrainageBankSupportDto();
//		
//		String url = KaNiuConstant.Query_SupportBank_Url;
//		
//		Map<String, String> jsonStrData = new HashMap<String, String>();
//		
//		jsonStrData.put("cardNo", cardNo);
//		
//		try {
//			String jsonData = HttpClientHelper.post(url, "UTF-8", jsonStrData);
//			logger.info("【调用根据cardNo支持银行的接口返回的json字符串】" + jsonData);
//			
//			if(StringUtils.isNotBlank(jsonData)){
//				JSONObject jsonObject = JSON.parseObject(jsonData);
//				
//				String msg = jsonObject.getString("msg");
//				
//				if(StringUtils.isNotBlank(msg) && "SUCCESS".equals(msg)){
//					
//					JSONObject jsonResult = jsonObject.getJSONObject("result");
//					
//					if(null != jsonResult && Boolean.valueOf(jsonResult.getString("supportBank"))){
//						bankSupportInfo.setBankCode(jsonResult.getString("bankCode"));
//						bankSupportInfo.setBankName(jsonResult.getString("bankName"));
//						bankSupportInfo.setSupportBank(Boolean.valueOf(jsonResult.getString("supportBank")));
//						result.setCode("000");
//						result.setMsg("操作成功！");
//					}else{
//						bankSupportInfo.setBankCode(jsonResult.getString("bankCode"));
//						bankSupportInfo.setSupportBank(Boolean.valueOf(jsonResult.getString("supportBank")));
//						result.setCode("400");
//						result.setMsg("不支持该银行卡！");
//					}
//					result.setResult(bankSupportInfo);
//				}
//			}
//		} catch (Exception e) {
//			logger.info("【ThirdBindCardServiceImpl.querySupportBank】", e);
//			result.setCode("111");
//			result.setMsg("操作失败，请重试操作或联系客服人员！");
//		}
//		return result;
//	}
//
//
//	//预绑卡
//	@Override
//	public AppResponseResult updateAndReadyBindBankCard(PaySignDto paySignDto,String relPhone) {
//		BwBorrower borrower = getBorrowerByPhone(relPhone);
//		Long borrowerId = null;
//		if(null != borrower){
//			borrowerId = borrower.getId();
//		}
//		paySignDto.setBorrowerId(borrowerId);
//		return bwBankCardService.updateAndReadyBindBankCard(paySignDto);
//	}
//	
//	
//	//确认绑卡
//	@Override
//	public AppResponseResult updateAndSureBindCard(PaySignDto paySignDto,String relPhone) {
//		BwBorrower borrower = getBorrowerByPhone(relPhone);
//		Long borrowerId = null;
//		if(null != borrower){
//			borrowerId = borrower.getId();
//		}
//		
//		paySignDto.setBorrowerId(borrowerId);
//		BwOrder bwOrder = bwOrderService.findOrderIdByBorrwerId(borrowerId);
//        String order_no = bwOrderRongService.findThirdOrderNoByOrderId(String.valueOf(bwOrder.getId()));
//		
//		AppResponseResult result = bwBankCardService.updateAndSureBindCard(paySignDto);
//		
//		String jsonStr = RedisUtils.get("tripartite:bindCardURL:" + order_no);
//		String userId = null;
//		if(StringUtils.isNotBlank(jsonStr)){
//			JSONObject jsonObject = JSON.parseObject(jsonStr);
//			if(null != jsonObject){
//				userId = jsonObject.getString("userId");
//			}
//		}
//		
//		String bankCode = null;
//		String bankName = null;
//		String bankCodeNum = null;
//		
//		//判断绑卡是否成功
//		if(Objects.equals("000", result.getCode())){
//			logger.info("绑卡成功,删除redis---->tripartite:bindCardURL:{}",order_no);
//			RedisUtils.del("tripartite:bindCardURL:" + order_no);
//            String contract_url = KaNiuConstant.CONTRACT_URL;
//            String data = "phone=" + borrower.getPhone() + "&" + "order_no=" + order_no;
//            String params = null;
//			try {
//				params = MD5Util.md5(data);
//			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//				logger.info("【ThirdBindCardServiceImpl.updateAndSureBindCard】：加密签约地址中param参数出现异常",e);
//			}
//            
//			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
//			
//			bankCodeNum = bwBankCard.getBankCode();
//			bankName = bwBankCard.getBankName();
//			
//            //拼接跳转签约的地址
//			StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append(contract_url);
//            stringBuffer.append("?phone=" + borrower.getPhone());
//            stringBuffer.append("&order_no=" + order_no);
//            stringBuffer.append("&platform=4");
//            stringBuffer.append("&params=" + params);
//            result.setResult(stringBuffer.toString());
//		}
//		
//		if(StringUtils.isBlank(bankName)){
//			bankName = BankUtils.getname(paySignDto.getCardNo());
//		}
//		if(StringUtils.isBlank(bankCode) && StringUtils.isNotBlank(bankName)){
//			bankCodeNum = DrainageUtils.convertToBankCode(bankName);
//		}
//		bankCode = DrainageUtils.convertFuiouBankCodeToBaofu(bankCodeNum);
//		bankCode = "PAB".equals(bankCode) ?  "SPABANK": bankCode;
//		bankCode = "BCOM".equals(bankCode) ?  "BCM": bankCode;
//		
//		JSONObject obj = new JSONObject();
//		obj.put("bankName", bankName);
//		obj.put("bankCode", bankCode);
//		obj.put("cardNo", paySignDto.getCardNo());
//		
//		//添加redis
//		Map<String, Object> map = new HashMap<>();
//        map.put("channelId", bwOrder.getChannel());
//        map.put("orderId", bwOrder.getId());
//        map.put("userId", userId);
//        map.put("jsonObject", obj);
//        String json = JSON.toJSONString(map);
//        RedisUtils.lpush("tripartite:bindCardNotify:" + bwOrder.getChannel(), json);
//        logger.info("向redis中添加绑卡记录:{}",json);
//		return result;
//	}
//	
//	
//	//获取借款人
//	public BwBorrower getBorrowerByPhone(String relPhone){
//		BwBorrower example = new BwBorrower();
//		example.setPhone(relPhone);
//		BwBorrower borrower = bwBorrowerService.findBwBorrowerByAttr(example);
//		if(borrower == null){
//			logger.info("根据注册手机号未关联上借款人!");
//			return null;
//		}
//		return borrower;
//	}
//	
//	
//	
//	
//	public static void main(String[] args) throws Exception {
//		AppResponseResult result = new AppResponseResult();
//		DrainageBankSupportDto bankSupportInfo = new DrainageBankSupportDto();
//		String url = "https://www.beadwallet.com/beadwalletloanapp/lianlian/getBankByCardNo.do";
//		
//		Map<String, String> jsonStrData = new HashMap<String, String>();
//		
//		jsonStrData.put("cardNo", "6217231302002204639");
//		
//		try {
//			String jsonData = HttpClientHelper.post(url, "UTF-8", jsonStrData);
//			System.out.println("【调用根据cardNo支持银行的接口返回的json字符串】" + jsonData);
//			
//			if(StringUtils.isNotBlank(jsonData)){
//				JSONObject jsonObject = JSON.parseObject(jsonData);
//				
//				String msg = jsonObject.getString("msg");
//				
//				if(StringUtils.isNotBlank(msg) && "SUCCESS".equals(msg)){
//					
//					JSONObject jsonResult = jsonObject.getJSONObject("result");
//					
//					if(null != jsonResult && Boolean.valueOf(jsonResult.getString("supportBank"))){
//						bankSupportInfo.setBankCode(jsonResult.getString("bankCode"));
//						bankSupportInfo.setBankName(jsonResult.getString("bankName"));
//						bankSupportInfo.setSupportBank(Boolean.valueOf(jsonResult.getString("supportBank")));
//						result.setCode("000");
//						result.setMsg("操作成功！");
//					}else{
//						bankSupportInfo.setBankCode(jsonResult.getString("bankCode"));
//						bankSupportInfo.setSupportBank(Boolean.valueOf(jsonResult.getString("supportBank")));
//						result.setCode("400");
//						result.setMsg("不支持该银行卡！");
//					}
//					result.setResult(bankSupportInfo);
//				}
//			}
//		} catch (Exception e) {
//			result.setCode("111");
//			result.setMsg("操作失败，请重试操作或联系客服人员！");
//		}
//		System.out.println(result.getCode() + ":" + result.getMsg());
//	}
//	
//
//}
//
