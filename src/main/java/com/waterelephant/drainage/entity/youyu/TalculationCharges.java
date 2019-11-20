package com.waterelephant.drainage.entity.youyu;

/**
 * 费用计算
 * 
 * @ClassName: TalculationCharges
 * @Description:
 * @author He LongYun
 * @date 2017年11月28日 上午10:46:58
 * @version V1.0
 * @since JDK 1.8
 *
 */
public class TalculationCharges {
	String loan_money; // 选择的贷款的金额 "1000.00"
	String money_unit; // 金额单位(1元 2万) "1"
	String loan_term; // 选择的贷款期限 "30"
	String term_unit; // 期限的单位(1天 2月) “1”

	public String getLoan_money() {
		return loan_money;
	}

	public void setLoan_money(String loan_money) {
		this.loan_money = loan_money;
	}

	public String getMoney_unit() {
		return money_unit;
	}

	public void setMoney_unit(String money_unit) {
		this.money_unit = money_unit;
	}

	public String getLoan_term() {
		return loan_term;
	}

	public void setLoan_term(String loan_term) {
		this.loan_term = loan_term;
	}

	public String getTerm_unit() {
		return term_unit;
	}

	public void setTerm_unit(String term_unit) {
		this.term_unit = term_unit;
	}

}
