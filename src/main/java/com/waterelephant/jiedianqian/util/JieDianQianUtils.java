package com.waterelephant.jiedianqian.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.jiedianqian.entity.JieDianQianResponse;
import com.waterelephant.jiedianqian.entity.OrderInfoRequest;
import com.waterelephant.utils.CommUtils;

public class JieDianQianUtils {

	// 订单状态转换
	private static Map<String, String> ORDER_STATUS = new HashMap<String, String>();

	// 还款计划状态转换
	private static Map<String, String> REPAY_STATUS = new HashMap<String, String>();

	static {
		ORDER_STATUS.put("1", "0");
		ORDER_STATUS.put("2", "0");
		ORDER_STATUS.put("3", "0");
		ORDER_STATUS.put("4", "4");
		ORDER_STATUS.put("5", "6");
		ORDER_STATUS.put("6", "8");
		ORDER_STATUS.put("7", "2");
		ORDER_STATUS.put("8", "2");
		ORDER_STATUS.put("9", "7");
		ORDER_STATUS.put("12", "6");
		ORDER_STATUS.put("14", "6");
		ORDER_STATUS.put("13", "13"); // 1 未还款 2 已还款 3垫付 4展期',
		

		// 借点钱 0:未出账， 1:待还款,，2:正常结清，3:逾期结清， 4:逾期， 5:部分还款（已逾期），6:部分还款（未逾期），7:还款中
		// 我方 1 未还款 2 已还款 3垫付 4展期',
		REPAY_STATUS.put("7", "1");
		REPAY_STATUS.put("6", "2");
		REPAY_STATUS.put("13", "4");
	}

	/**
	 * 把第三方传递的业务数据 封装到pojo
	 * 
	 * @param data
	 * @return
	 */
	public static String checkAll(HttpServletRequest request) {
		String check = null;

		if (CommUtils.isNull(request)) {
			return "";
		}

		if (StringUtils.isBlank(request.getParameter("data"))) {
			return "拉取数据为空";
		}
		if (StringUtils.isBlank(request.getParameter("sign"))) {
			return "拉取签名为空";
		}
		return check;
	}

	public static String checkThridOrder(OrderInfoRequest req) {
		String result = null;

		if (req == null) {
			return "拉取订单请求内容为空";
		}

		if (StringUtils.isBlank(req.getSource_order_id())) {
			return "拉取借点钱订单号数据为空";
		}

		return result;
	}

	public static String commonCheck(HttpServletRequest request) { // 接收参数,判断是否为空
		String check = null;

		if (CommUtils.isNull(request)) {
			return "";
		}

		if (StringUtils.isBlank(request.getParameter("name"))) {
			return "姓名为空";
		}

		if (StringUtils.isBlank(request.getParameter("phone"))) {
			return "手机号为空";
		}

		if (StringUtils.isBlank(request.getParameter("id_number"))) {
			return "身份证号码为空";
		}
		return check;
	}

	public static String checkOrderPush(JSONObject json) {
		String check = null;

		if (CommUtils.isNull(json)) {
			return "";
		}

		if (StringUtils.isBlank(json.getString("data"))) {
			return "拉取推送信息为空";
		}

		if (StringUtils.isBlank(json.getString("sign"))) {
			return "拉取订单签名串为空";
		}

		return check;
	}

	public static String checkCalculateData(JSONObject parseObject) {
		String check = null;

		if (StringUtils.isBlank(parseObject.getString("order_id"))) {
			return "拉取合作方订单id为空";
		}

		if (StringUtils.isBlank(parseObject.getString("amount"))) {
			return "拉取金额为空";
		}

		return check;
	}

	public static String checkOrderId(JSONObject parseObject) {
		String check = null;

		if (CommUtils.isNull(parseObject)) {
			return "";
		}

		if (StringUtils.isBlank(parseObject.getString("order_id"))) {
			return "拉取订单id为空";
		}
		return check;
	}

	public static String checkPhone(String phone) {
		if (phone == null) {
			return phone;
		}

		if (isChinaPhoneLegal(phone)) {
			return phone;
		}

		phone = phone.replaceAll("\\D", "");

		if (phone.length() < 2) {
			return phone;
		}

		if ("86".equals(phone.substring(0, 2))) {
			phone = phone.substring(2);
		}
		return phone;
	}

	public static String checkpaymentData(JSONObject parseObject) {
		String check = null;

		if (StringUtils.isBlank(parseObject.getString("order_id"))) {
			return "拉取订单id为空";
		}

		if (StringUtils.isBlank(parseObject.getString("successReturnUrl"))) {
			return "拉取成功跳转页面为空";
		}

		if (StringUtils.isBlank(parseObject.getString("errorReturnUrl"))) {
			return "拉取失败跳转页面为空";
		}
		return check;
	}

	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static String convertStatus(Long statusId) {

		if (statusId == null) {
			return null;
		}

		return ORDER_STATUS.get(String.valueOf(statusId));
	}

	public static String convertRepayStatus(Long statusId) {

		if (statusId == null) {
			return null;
		}

		return REPAY_STATUS.get(String.valueOf(statusId));
	}

	public static String checkBaseData(JSONObject parseObject) {
		String check = null;

		if (StringUtils.isBlank(parseObject.getString("user_name"))) {
			return "姓名为空";
		}

		if (StringUtils.isBlank(parseObject.getString("phone"))) {
			return "手机号为空";
		}

		if (StringUtils.isBlank(parseObject.getString("id_number"))) {
			return "身份证号码为空";
		}
		return check;
	}

	public static String checkConfirmData(JSONObject parseObject) {
		String check = null;

		if (StringUtils.isBlank(parseObject.getString("order_id"))) {
			return "合作方订单id为空";
		}

		if (StringUtils.isBlank(parseObject.getString("loan_amount"))) {
			return "确认提现金额为空";
		}

		if (StringUtils.isBlank(parseObject.getString("loan_periods"))) {
			return "贷款期数为空";
		}
		return check;
	}

	public static String checkJDQResp(JieDianQianResponse jieDianQianResponse) {
		String check = null;

		if (StringUtils.isBlank(jieDianQianResponse.getCode() + "")) {
			return "借点钱返回状态为空";
		}
		return check;
	}

	public static String checkBindCardData(JSONObject parseObject) {
		String check = null;

		if (StringUtils.isBlank(parseObject.getString("order_id"))) {
			return "合作方订单id为空";
		}

		if (StringUtils.isBlank(parseObject.getString("successReturnUrl"))) {
			return "借点钱操作成功页面为空";
		}
		
		return check;
	}


}
