package com.waterelephant.drainage.entity.xianJinCard;

import java.util.List;

/**
 * Module: XianJinCardCommonRequest.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class XianJinCardCommonRequest {
	// 审核签约
	private String order_sn;// 订单唯一编号
	private int repay_type;// 0：仅还当期，1，分期业务全额还款
	private String verify_code;// 扣款验证码，当已发送验证码之后再次请求提交
	private String confirm_result;// 用户签约确认结果. 200: 接受额度，可以放款； 403: 拒绝额度，放弃借款；
	// 订单推送
	private UserInfo user_info; // 用户基本信息
	private OrderInfo order_info; // 订单基本信息
	private UserAdditional user_additional; // 用户补充信息
	private UserVerify user_verify; // 用户认证信息
	// 绑卡
	private String bank_code; // 绑卡银行编码
	private String user_name; // 用户姓名
	private String user_idcard; // 用户身份证号
	private String card_number; // 银行卡号
	private String card_phone; // 银行预留手机号
	private String user_phone; // 用户准入时的明文手机号
	private String echo_data; // 回显数据字段, 此字段仅要求在绑卡结果回调接口中回传即可
	// private String verify_code; // 绑卡验证码，当已发送验证码之后再次请求提交
	// 还款计划
	private int total_amount;// 还款总额; 单位: 分
	private int total_svc_fee;// 总服务费; 单位: 分
	private int already_paid;// 已还金额; 单位: 分
	private int total_period;// 总期数
	private int finish_period;// 已还期数
	private List<RepaymentPlan> repayment_plan;// 具体还款计划数组
	// 用户过滤
	// private String user_name; // 用户姓名
	// private String user_phone; // 用户手机号（掩后3位）
	// private String user_idcard; // 用户身份证号码（掩后4位）
	private String md5; // md5(手机号+身份证)
	// 订单还款
	// private String order_sn;// 订单唯一编号
	// private int repay_type;// 0：仅还当期，1，分期业务全额还款
	// private String verify_code;// 扣款验证码，当已发送验证码之后再次请求提交

	// 获取订单状态
	// private String order_sn;// 订单唯一编号
	private int act_type;// 操作类型; 1: 拉取订单审批结果

	// 借款试算
	private String loan_amount;// 审批金额 单位（分）
	private String loan_term;// 审批期限
	private String term_type;// 1:按天; 2：按月; 3：按年

	public String getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(String loan_amount) {
		this.loan_amount = loan_amount;
	}

	public String getLoan_term() {
		return loan_term;
	}

	public void setLoan_term(String loan_term) {
		this.loan_term = loan_term;
	}

	public String getTerm_type() {
		return term_type;
	}

	public void setTerm_type(String term_type) {
		this.term_type = term_type;
	}

	public int getAct_type() {
		return act_type;
	}

	public void setAct_type(int act_type) {
		this.act_type = act_type;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public int getRepay_type() {
		return repay_type;
	}

	public void setRepay_type(int repay_type) {
		this.repay_type = repay_type;
	}

	public String getVerify_code() {
		return verify_code;
	}

	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}

	public String getConfirm_result() {
		return confirm_result;
	}

	public void setConfirm_result(String confirm_result) {
		this.confirm_result = confirm_result;
	}

	public UserInfo getUser_info() {
		return user_info;
	}

	public void setUser_info(UserInfo user_info) {
		this.user_info = user_info;
	}

	public OrderInfo getOrder_info() {
		return order_info;
	}

	public void setOrder_info(OrderInfo order_info) {
		this.order_info = order_info;
	}

	public UserAdditional getUser_additional() {
		return user_additional;
	}

	public void setUser_additional(UserAdditional user_additional) {
		this.user_additional = user_additional;
	}

	public UserVerify getUser_verify() {
		return user_verify;
	}

	public void setUser_verify(UserVerify user_verify) {
		this.user_verify = user_verify;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_idcard() {
		return user_idcard;
	}

	public void setUser_idcard(String user_idcard) {
		this.user_idcard = user_idcard;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getCard_phone() {
		return card_phone;
	}

	public void setCard_phone(String card_phone) {
		this.card_phone = card_phone;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getEcho_data() {
		return echo_data;
	}

	public void setEcho_data(String echo_data) {
		this.echo_data = echo_data;
	}

	public int getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}

	public int getTotal_svc_fee() {
		return total_svc_fee;
	}

	public void setTotal_svc_fee(int total_svc_fee) {
		this.total_svc_fee = total_svc_fee;
	}

	public int getAlready_paid() {
		return already_paid;
	}

	public void setAlready_paid(int already_paid) {
		this.already_paid = already_paid;
	}

	public int getTotal_period() {
		return total_period;
	}

	public void setTotal_period(int total_period) {
		this.total_period = total_period;
	}

	public int getFinish_period() {
		return finish_period;
	}

	public void setFinish_period(int finish_period) {
		this.finish_period = finish_period;
	}

	public List<RepaymentPlan> getRepayment_plan() {
		return repayment_plan;
	}

	public void setRepayment_plan(List<RepaymentPlan> repayment_plan) {
		this.repayment_plan = repayment_plan;
	}

}
