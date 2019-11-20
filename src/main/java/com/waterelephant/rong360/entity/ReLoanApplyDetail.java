package com.waterelephant.rong360.entity;
@Deprecated
public class ReLoanApplyDetail {
	private String application_amount;
	private String loan_apply_term;
	private String asset_auto_type;
	private String user_education;
	private String work_period;
	private String user_income_by_card;
	private String max_monthly_repayment;
	private String operating_year;
	private String is_op_type;
	private String user_id;
	private String bureau_user_name;
	private String user_social_security;
	private String phone_number_house;
	private String monthly_average_income;
	private String credit_status;
	
	public String getApplication_amount() {
		return application_amount;
	}
	public void setApplication_amount(String application_amount) {
		this.application_amount = application_amount;
	}
	public String getLoan_apply_term() {
		return loan_apply_term;
	}
	public void setLoan_apply_term(String loan_apply_term) {
		this.loan_apply_term = loan_apply_term;
	}
	public String getAsset_auto_type() {
		return asset_auto_type;
	}
	public void setAsset_auto_type(String asset_auto_type) {
		this.asset_auto_type = asset_auto_type;
	}
	public String getUser_education() {
		return user_education;
	}
	public void setUser_education(String user_education) {
		this.user_education = user_education;
	}
	public String getWork_period() {
		return work_period;
	}
	public void setWork_period(String work_period) {
		this.work_period = work_period;
	}
	public String getUser_income_by_card() {
		return user_income_by_card;
	}
	public void setUser_income_by_card(String user_income_by_card) {
		this.user_income_by_card = user_income_by_card;
	}
	public String getMax_monthly_repayment() {
		return max_monthly_repayment;
	}
	public void setMax_monthly_repayment(String max_monthly_repayment) {
		this.max_monthly_repayment = max_monthly_repayment;
	}
	public String getOperating_year() {
		return operating_year;
	}
	public void setOperating_year(String operating_year) {
		this.operating_year = operating_year;
	}
	public String getIs_op_type() {
		return is_op_type;
	}
	public void setIs_op_type(String is_op_type) {
		this.is_op_type = is_op_type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getBureau_user_name() {
		return bureau_user_name;
	}
	public void setBureau_user_name(String bureau_user_name) {
		this.bureau_user_name = bureau_user_name;
	}
	public String getUser_social_security() {
		return user_social_security;
	}
	public void setUser_social_security(String user_social_security) {
		this.user_social_security = user_social_security;
	}
	public String getPhone_number_house() {
		return phone_number_house;
	}
	public void setPhone_number_house(String phone_number_house) {
		this.phone_number_house = phone_number_house;
	}
	public String getMonthly_average_income() {
		return monthly_average_income;
	}
	public void setMonthly_average_income(String monthly_average_income) {
		this.monthly_average_income = monthly_average_income;
	}
	public String getCredit_status() {
		return credit_status;
	}
	public void setCredit_status(String credit_status) {
		this.credit_status = credit_status;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReLoanApplyDetail [");
		if (application_amount != null) {
			builder.append("application_amount=");
			builder.append(application_amount);
			builder.append(", ");
		}
		if (loan_apply_term != null) {
			builder.append("loan_apply_term=");
			builder.append(loan_apply_term);
			builder.append(", ");
		}
		if (asset_auto_type != null) {
			builder.append("asset_auto_type=");
			builder.append(asset_auto_type);
			builder.append(", ");
		}
		if (user_education != null) {
			builder.append("user_education=");
			builder.append(user_education);
			builder.append(", ");
		}
		if (work_period != null) {
			builder.append("work_period=");
			builder.append(work_period);
			builder.append(", ");
		}
		if (user_income_by_card != null) {
			builder.append("user_income_by_card=");
			builder.append(user_income_by_card);
			builder.append(", ");
		}
		if (max_monthly_repayment != null) {
			builder.append("max_monthly_repayment=");
			builder.append(max_monthly_repayment);
			builder.append(", ");
		}
		if (operating_year != null) {
			builder.append("operating_year=");
			builder.append(operating_year);
			builder.append(", ");
		}
		if (is_op_type != null) {
			builder.append("is_op_type=");
			builder.append(is_op_type);
			builder.append(", ");
		}
		if (user_id != null) {
			builder.append("user_id=");
			builder.append(user_id);
			builder.append(", ");
		}
		if (bureau_user_name != null) {
			builder.append("bureau_user_name=");
			builder.append(bureau_user_name);
			builder.append(", ");
		}
		if (user_social_security != null) {
			builder.append("user_social_security=");
			builder.append(user_social_security);
			builder.append(", ");
		}
		if (phone_number_house != null) {
			builder.append("phone_number_house=");
			builder.append(phone_number_house);
			builder.append(", ");
		}
		if (monthly_average_income != null) {
			builder.append("monthly_average_income=");
			builder.append(monthly_average_income);
			builder.append(", ");
		}
		if (credit_status != null) {
			builder.append("credit_status=");
			builder.append(credit_status);
		}
		builder.append("]");
		return builder.toString();
	}
}