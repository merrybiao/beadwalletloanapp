package com.waterelephant.register.util;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.waterelephant.utils.CommUtils;

public class RegisterUtils {
	public static String commonCheck(HttpServletRequest request) {
		if (CommUtils.isNull(request)) {
			return "请求异常";
		}

		if (StringUtils.isBlank(request.getParameter("data"))) {
			return "请求报文主体为空";
		}

		if (StringUtils.isBlank(request.getParameter("channel"))) {
			return "请求渠道标识为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("sign"))) {
			return "请求签名为空";
		}
		
		return null;
	}
	
	public static String checkJieDianQian(HttpServletRequest request){
		if (CommUtils.isNull(request)) {
			return "请求异常";
		}
		
		if (StringUtils.isBlank(request.getParameter("apply_no"))) {
			return "唯一标识为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("apply_no"))) {
			return "渠道标识为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("apply_no"))) {
			return "申请信息为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("apply_no"))) {
			return "用户信息为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("apply_no"))) {
			return "申请时刻为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("apply_no"))) {
			return "签名为空";
		}
		
		return null;
	}
	
	public static String getRandomPwd(){
    	char[] r = getChar();
        Random rr = new Random();
        char[] pw= new char[6];
        for(int i=0;i<pw.length;i++){
            int num = rr.nextInt(1);
            pw[i]=r[num];
        }
        
        return new String(pw);
    }
	
	public static String getRandNum(int min, int max) {
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return String.valueOf(randNum) + "a";
	}
    
    public static String getMsg(String pwd){
    	String pattern = "尊敬的用户您好，恭喜您成功注册，您的登录密码为：{0}，您还可以登录t.cn/R6rhkzU查看最新的借款进度哦！";
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
    
    public static void main(String[] args) {
		System.out.println(CommUtils.getMD5("fjsi*3343j@ffac".getBytes()));
		
	}
    
    private static char[] getChar(){
        char[] passwordLit = new char[62];
        char fword = 'A';
        char mword = 'a';
        char bword = '0';
        for (int i = 0; i < 62; i++) {
            if (i < 26) {
                passwordLit[i] = fword;
                fword++;
            }else if(i<52){
                passwordLit[i] = mword;
                mword++;
            }else{
                passwordLit[i] = bword;
                bword++;
            }
        }
     return passwordLit;
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