package com.waterelephant.jiufu.util;

import java.text.MessageFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.beadwallet.utils.CommUtils;
import com.waterelephant.jiufu.entity.JiufuReq;


public class JiufuUtils {
	public static String commonCheck(JiufuReq req){
		String check = null;
		if(CommUtils.isNull(req)){
			return "请求参数为空";
		}
		if(StringUtils.isBlank(req.getChannel_id())){
			return "渠道编号为空";
		}
		if(StringUtils.isBlank(req.getDingdang_id())){
			return "叮当编号为空";
		}
		if(StringUtils.isBlank(req.getSerial_number())){
			return "流水号号为空";
		}
		if(StringUtils.isBlank(req.getMobile())){
			return "手机号码为空";
		}
		if(StringUtils.isBlank(req.getName())){
			return "用户姓名为空";
		}
		if(StringUtils.isBlank(req.getCert_id())){
			return "用户身份证号为空";
		}
		return check;
	}
	
	public static String commonCheck(HttpServletRequest request){
		String check = null;
		if(CommUtils.isNull(request)){
			return "请求参数为空";
		}
		if(StringUtils.isBlank(request.getParameter("channel_id"))){
			return "渠道编号为空";
		}
		if(StringUtils.isBlank(request.getParameter("dingdang_id"))){
			return "叮当编号为空";
		}
		if(StringUtils.isBlank(request.getParameter("serial_number"))){
			return "流水号号为空";
		}
		if(StringUtils.isBlank(request.getParameter("mobile"))){
			return "手机号码为空";
		}
		if(StringUtils.isBlank(request.getParameter("name"))){
			return "用户姓名为空";
		}
		if(StringUtils.isBlank(request.getParameter("cert_id"))){
			return "用户身份证号为空";
		}
		return check;
	}
	public static String getRandNum(int min, int max) {
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return String.valueOf(randNum) + "a";
	}
    
    public static String getMsg(String pwd){
    	String pattern = "尊敬的用户您好，恭喜您成功注册，您的登录密码为：{0}，您还可以登录www.beadwallet.com/loanpage/html/Home查看最新的借款进度哦！";
		String msg = MessageFormat.format(pattern, new Object[]{pwd});
		return msg;
    }
    
    public static String getReturnUrl(Object[] param, String pattern){
    	if (StringUtils.isBlank(pattern)) {
			return null;
		}
    	String msg = MessageFormat.format(pattern, param);
		return msg;
    }
    
    public static int getAgeByIdCard(String idCard) {
		Calendar c = Calendar.getInstance();
		int age = 0;
		int year = c.get(Calendar.YEAR);
		String ageNum = idCard.substring(6, 10);
		age = year - Integer.parseInt(ageNum);

		return age;
	}

	public static int getSexByIdCard(String idCard) {
		int sex = 0;
		String sexNum = idCard.substring(idCard.length() - 2, idCard.length() - 1);
		if ((Integer.parseInt(sexNum)) % 2 == 0) {
			sex = 0;
		} else {
			sex = 1;
		}
		return sex;
	}

}
