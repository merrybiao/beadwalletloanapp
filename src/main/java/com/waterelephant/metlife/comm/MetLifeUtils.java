package com.waterelephant.metlife.comm;

import java.util.Calendar;
import java.util.Date;

import com.beadwallet.service.metlife.common.MetLifeConstant;
import com.waterelephant.utils.DateUtil;

public class MetLifeUtils {
	
	/**
	 * 通过身份证号解析生日
	 */
	public static String getBirthday(String idNo) {
		String year = idNo.substring(6, 10);
		String month = idNo.substring(10, 12);
		String day = idNo.substring(12, 14);
		String birthDate = year + "-" + month + "-" + day;
		return birthDate;
	}
	/**
	 * 还款日=放款日+放款期限
	 * @param grantLoansDate 放款日
	 * @param loanDays 放款期限
	 * @return
	 */
	public static String getLoanEndDate(String grantLoansDate,int loanDays) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.stringToDate(grantLoansDate, DateUtil.yyyy_MM_dd));
		calendar.add(Calendar.DATE, +loanDays);
		return DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd);
	}
	
	public static int getLoanDays(String grantLoansDate,int loanMonths) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.stringToDate(grantLoansDate, DateUtil.yyyy_MM_dd));
		Date loansDate = calendar.getTime();
		calendar.add(Calendar.MONTH, +loanMonths);
		Date loansEndDate = calendar.getTime();
		
		return DateUtil.intervalDay(loansDate,loansEndDate);
	}
	
	public static double getLoanMaxAmount(String grpContPlancode,double loanAmount) {
		
		double maxAmount = 10000;
		
		double rate = 0d;
		switch(grpContPlancode) {
			case MetLifeConstant.GRP_CONT_PLAN_CODE_01:
				rate = MetLifeConstant.LOAN_AMOUNT_RATE_01;
				break;
			case MetLifeConstant.GRP_CONT_PLAN_CODE_02:
				rate = MetLifeConstant.LOAN_AMOUNT_RATE_02;
				break;
			case MetLifeConstant.GRP_CONT_PLAN_CODE_03:
				rate = MetLifeConstant.LOAN_AMOUNT_RATE_03;
				break;
			default:
				break;
		}
		
		System.out.println("--------loanAmount:"+loanAmount+",grpCode:"+grpContPlancode+",rate:"+rate+"------------");
		
		double amount = MetLifeConstant.mul(loanAmount, rate);
		
		System.out.println("--------amount:"+amount+",maxAmount:"+maxAmount+", "+(amount>maxAmount?"[需要]":"[不需要]")+" 分单----------");
		double maxLoanAmount = Double.valueOf(maxAmount/ rate/100).intValue()*100;
		
		int count = loanAmount % maxLoanAmount ==0 ? Double.valueOf(loanAmount / maxLoanAmount).intValue() : Double.valueOf(loanAmount / maxLoanAmount).intValue() +1;
		System.out.println("------分单次数："+count+"次，每单合同金额为："+maxLoanAmount+"-------");
		
		return maxLoanAmount;
	}

}
