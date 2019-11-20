package com.waterelephant.cashloan.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.waterelephant.cashloan.entity.CashLoanReq;
import com.waterelephant.cashloan.entity.PullOrderReqData;
import com.waterelephant.cashloan.entity.PullOrderResData;
import com.waterelephant.cashloan.entity.PushUserReqData;
import com.waterelephant.cashloan.entity.PushUserResData;
import com.waterelephant.utils.CommUtils;

public class CashLoanUtils {
	
	private static Map<String, String> STATUS_MAP = new HashMap<String, String>();
	
	static{							
		STATUS_MAP.put("1", "1");
		STATUS_MAP.put("2", "3");
		STATUS_MAP.put("3", "3");
		STATUS_MAP.put("4", "3");
		STATUS_MAP.put("6", "5");
		STATUS_MAP.put("7", "4");
		STATUS_MAP.put("8", "4");
		STATUS_MAP.put("9", "5");
		STATUS_MAP.put("11", "3");
		STATUS_MAP.put("12", "3");
//		STATUS_MAP.put("13", "1");
		STATUS_MAP.put("14", "3");
	}
	
	public static String commonCheck(HttpServletRequest request){			//接收参数,判断是否为空
		String check = null;
		
		if (CommUtils.isNull(request)) {
			return "";
		}
		
		if (StringUtils.isBlank(request.getParameter("appid"))) {
			return "商户号为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("signkey"))) {
			return "数据签名串为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("data"))) {
			return "业务数据为空";
		}
		
		return check;
	}
	
	public static CashLoanReq convertReq2CashLoanReq(HttpServletRequest request){		//封装天眼字段
		CashLoanReq req = new CashLoanReq();
		
		req.setAppid(request.getParameter("appid"));
		req.setData(request.getParameter("data"));
		req.setSignkey(request.getParameter("signkey"));
		
		return req;
	}
	
	public static PushUserReqData convertData2PushUserReqData(String data){
		PushUserReqData reqData = new PushUserReqData();
		
		String[] paramArray = data.split("&");
		
		Map<String, String> map = new HashMap<String, String>();
		
		for (String param : paramArray){
			map.put(param.split("=")[0], param.split("=")[1]);
		}
		
		reqData.setAmount(map.get("amount"));
		reqData.setMobile(map.get("mobile"));
		reqData.setPeriod(map.get("period"));
		reqData.setRealname(map.get("realname"));
		reqData.setScureid(map.get("scureid"));
		reqData.setSend_time(map.get("send_time"));
		reqData.setSid(map.get("sid"));
		reqData.setSource_device(map.get("source_device"));
		
		return reqData;
	}
	
	public static PullOrderReqData convertData2PullOrderReqData(String data){
		PullOrderReqData reqData = new PullOrderReqData();
		
		String[] paramArray = data.split("&");
		
		Map<String, String> map = new HashMap<String, String>();
		
		for (String param : paramArray){
			map.put(param.split("=")[0], param.split("=")[1]);
		}
		
		reqData.setSend_time(map.get("send_time"));
		reqData.setSid(map.get("sid"));
		
		return reqData;
	}
	
	@SuppressWarnings("deprecation")
	public static String convertPushUserResData2Data(PushUserResData pushUserResData){
		String data = "is_succeed="+pushUserResData.getIs_succeed()+"&referrer_url="+URLEncoder.encode(pushUserResData.getReferrer_url());		//拼接回调的data
		return data;
	}
	
	public static String convertPullOrderResData2Data(PullOrderResData pullOrderResData){
		String data = "mobile="+pullOrderResData.getMobile()
		+"&status="+pullOrderResData.getStatus()
		+"&product_name="+pullOrderResData.getProduct_name()
		+"&realname="+pullOrderResData.getRealname()
		+"&scureid="+pullOrderResData.getScureid()
		+"&amount="+pullOrderResData.getAmount()
		+"&period="+pullOrderResData.getPeriod()
		+"&rate="+pullOrderResData.getRate()
		+"&send_time="+pullOrderResData.getSend_time()
		+"&update_time="+pullOrderResData.getUpdate_time()
		+"&sid="+pullOrderResData.getSid()
		+"&repayment="+pullOrderResData.getRepayment()
		+"&message="+pullOrderResData.getMessage();
		return data;
	}
	
	@SuppressWarnings("deprecation")
	public static String desData(String data){
		String desData = null;
		try {
			desData = TripleDesUtil.tripleDesDecode(URLEncoder.encode(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desData;
	}
	
	public static String enData(String data){
		String enData = null;
		try {
			enData = TripleDesUtil.tripleDesEncode(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enData;
	}
	//data已经解密了, signkey未解密
	public static boolean checkSign(String data, String signKey){
		boolean b = false;
		try {
			if (signKey.equals(CheckSign.checkSignKey(data))) {
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public static String convertStatus(Long statusId){
		
		if (statusId == null) {
			return null;
		}
		
		return STATUS_MAP.get(String.valueOf(statusId));
	}
}