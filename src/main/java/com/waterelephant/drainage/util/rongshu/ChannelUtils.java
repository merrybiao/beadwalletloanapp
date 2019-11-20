package com.waterelephant.drainage.util.rongshu;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class ChannelUtils {
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
    
	public static String getRandomPwd(){
    	char[] r = getChar();
        Random rr = new Random();
        char[] pw= new char[6];
        for(int i=0;i<pw.length;i++){
            int num = rr.nextInt(62);
            pw[i]=r[num];
        }
        
        return new String(pw);
    }
    
    public static String getMsg(String pwd){
    	String pattern = "【水象借点花】尊敬的用户您好，恭喜您成功注册，您的登录密码为：{0}，您还可以登录 t.cn/R6rhkzU 查看最新的借款进度哦！";
		String msg = MessageFormat.format(pattern, new Object[]{pwd});
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
	
	public static String getWorkPeriod(String key){
		String workPeriod = null;
		if (StringUtils.isBlank(key)) {
			workPeriod = "1年以内";
		} else {
			switch (key) {
			case "1":
				workPeriod = "1年以内";
				break;
			case "2":
				workPeriod = "1年以内";
				break;
			case "3":
				workPeriod = "1-3年";
				break;
			case "4":
				workPeriod = "3-5年";
				break;
			case "5":
				workPeriod = "5-10年";
				break;
			default:
				workPeriod = "1年以内";
				break;
			}
		}
		
		return workPeriod;
	}
}
