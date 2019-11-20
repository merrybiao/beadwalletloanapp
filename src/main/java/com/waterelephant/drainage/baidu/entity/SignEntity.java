package com.waterelephant.drainage.baidu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 签约
 * 
 * @author dengyan
 *
 */
public class SignEntity extends CommonEntity {

	@JSONField(name = "auth_code")
	private String auth_code; // 授权唯一凭证

	@JSONField(name = "order_id")
	private String order_id; // 百度金融商城订单id

	@JSONField(name = "amount")
	private int amount; // 贷款金额

	@JSONField(name = "term")
	private int term; // 申请期限

	@JSONField(name = "loan_usage")
	private String loan_usage; // 借款用途：1、购物，2、付房租，3、教育支出，4、旅游，5、买车，6、买房，7、装修，8、结婚，9、其它

	@JSONField(name = "card_no")
	private String card_no; // 体现银行卡号

	@JSONField(name = "user_info")
	private UserInfo user_info; // 用户信息

	/**
	 * @return 获取 auth_code属性值
	 */
	public String getAuth_code() {
		return auth_code;
	}

	/**
	 * @param auth_code 设置 auth_code 属性值为参数值 auth_code
	 */
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	/**
	 * @return 获取 order_id属性值
	 */
	public String getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id 设置 order_id 属性值为参数值 order_id
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * @return 获取 amount属性值
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount 设置 amount 属性值为参数值 amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return 获取 term属性值
	 */
	public int getTerm() {
		return term;
	}

	/**
	 * @param term 设置 term 属性值为参数值 term
	 */
	public void setTerm(int term) {
		this.term = term;
	}

	/**
	 * @return 获取 loan_usage属性值
	 */
	public String getLoan_usage() {
		return loan_usage;
	}

	/**
	 * @param loan_usage 设置 loan_usage 属性值为参数值 loan_usage
	 */
	public void setLoan_usage(String loan_usage) {
		this.loan_usage = loan_usage;
	}

	/**
	 * @return 获取 card_no属性值
	 */
	public String getCard_no() {
		return card_no;
	}

	/**
	 * @param card_no 设置 card_no 属性值为参数值 card_no
	 */
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	/**
	 * @return 获取 user_info属性值
	 */
	public UserInfo getUser_info() {
		return user_info;
	}

	/**
	 * @param user_info 设置 user_info 属性值为参数值 user_info
	 */
	public void setUser_info(UserInfo user_info) {
		this.user_info = user_info;
	}
}
