package com.waterelephant.yixin.util;

/**
 * Yixin端常量
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月16日 上午11:47:29
 */
public class YiXinConstant {


	/*********************************************审批结果码 **********************************/
	//201    审核中
	public static final String REVIEWING = "201";

	//202 	批贷已放款
	public static final String BATCHLOAN = "202";
	
	//203	拒贷
	public static final String  DENIEDLOANS = "203";
	
	//204	 客户放弃
	public static final String CUSTOMERUP  = "204";
	
	
	/*******************************************还款状态码**************************************/
	 //301 正常
	 public static final String BACK_MONEY_NORAMT = "301";
	
	 // 302 逾期
	 public static final String BACK_MONEY_WITHIN = "302";
	 
	 //303 结清
	 public static final String BACK_MONEY_END = "303";
	
	 
	/******************************************** 借款类型码 **************************************/
	 
	 //21 信用
	 public static final String  BORROWING_TYPE_CREDIT = "21";
	 
	 //22 抵押
	 public static final String  BORROWING_TYPE_MORTGAGE = "22";
	 
	 //23 担保
	 public static final String  BORROWING_TYPE_GUARANTEE = "23";
	
	
}
