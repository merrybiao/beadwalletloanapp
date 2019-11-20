package com.waterelephant.drainage.entity.rongShu;

/**
 * 榕树 - 响应实体（code0097）
 * 
 * 
 * Module:
 * 
 * RongShuResponse.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <榕树 - 响应实体>
 */
public class RongShuResponse {
	
//	public static  String DEFAULT_URL="https://dymapi.xiaqiu.cn";
	public static  String DEFAULT_URL="https://openapi.rongshu.cn";
	public static int CODE_SUCCESS = 0; // 接口调用成功
	public static int CODE_FAIL = 10001; // 内部错误，接口调用失败
	public static int CODE_PARAMETERERROR = 10003; // 参数错误
	public static int CODE_ORDERSUCCESS = 50001; // 进件成功
	public static int CODE_ORDEFAIL_INNERERROR = 50002; // 进件失败，内部错误
	public static int CODE_ORDERFAIL_PARAMETERERROR = 50003; // 进件失败，参数错误
	public static int CODE_CHECKTHROUGH = 50004; // 审批通过
	public static int CODE_CHECKREJECT = 50005; // 审批拒绝
	public static int CODE_CHECKFAIL_INNERERROR = 50006; // 审批失败，内部错误
	public static int CODE_LOANING = 50007; // 放款中
	public static int CODE_LOANSUCCESS = 50008; // 放款成功
	public static int CODE_LOANFAIL = 50009; // 放款失败
	public static int CODE_PAYOFF = 50010; // 已还清
	public static int CODE_OVERDUE = 50011; // 已逾期
	public static int CODE_OVERDUEPAYOFF = 50012; // 逾期还清
	public static int CODE_BADDETS = 50013; // 坏账
	public static int CODE_BINDINGCARDSMS = 50014; // 绑卡验证码已发送
	public static int CODE_SMSERROR_BANDINGFAIL = 50015; // 验证码错误，绑卡失败
	public static int CODE_BINDINGSUCCESS = 50016; // 绑卡成功
	public static int CODE_BINDINGFAIL = 50017; // 绑卡失败
	public static int CODE_ACTIVEREPAYMENTSUCCESS = 50018; // 主动还款请求成功
	public static int CODE_ACTIVEREPAYMENT_SMS = 50019; // 主动还款短信验证码已发送
	public static int CODE_ACTIVEREPAYMENTFAIL = 50020; // 主动还款处理失败
	public static int CODE_ACTIVEREPAYMENTREJECT = 50021; // 主动还款请求拒绝

	private int code;
	private String message;
	private Object response;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
