package com.waterelephant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.waterelephant.entity.BwMetlifeInsuranceOrder;
import com.waterelephant.metlife.dto.MetlifeInsuranceOrderDto;

import tk.mybatis.mapper.common.Mapper;

public interface BwMetlifeInsuranceOrderMapper extends Mapper<BwMetlifeInsuranceOrder> {
	
	@Select("SELECT DISTINCT t.policy_number "
	+ " FROM bw_metlife_insurance_order t"
	+ " JOIN bw_metlife_insurance_apply_record r ON r.id = t.r_id"
	+ " WHERE t.trim_date =#{trimDate}"
	+ " AND r.product_no = #{productNo}"
	+ " AND t.policy_number IS NOT NULL"
	+ " ORDER BY t.create_time DESC")
	List<String> queryPolicyNoByTrimDate(@Param(value = "trimDate") String trimDate,@Param(value = "productNo") String productNo);
	
	@Select("SELECT DISTINCT t.policy_number "
	+ " FROM bw_metlife_insurance_order t"
	+ " JOIN bw_metlife_insurance_apply_record r ON r.id = t.r_id"
	+ " WHERE t.order_no =#{orderNo}"
	+ " AND r.product_no = #{productNo}"
	+ " AND t.policy_number IS NOT NULL"
	+ " ORDER BY t.trim_date ASC")
	List<String> queryPolicyNoByOrderNo(@Param(value = "orderNo") String orderNo,@Param(value = "productNo") String productNo);
	
	@Select("SELECT " + 
	"	t.uuid," + 
    "   t.order_no AS orderNo," +
	"	t.batch_no AS batchNo," + 
	"	t.send_time AS sendTime," + 
	"	t.grp_cont_plancode AS grpConPlancode," + 
	"	t.trim_date AS trimDate," + 
	"	t.grant_loans_date AS grantLoansDate," + 
	"	t.grant_loans_end_date AS grantLoansEndDate," + 
	"	t.loan_contract_code AS loanContractCode," + 
	"	t.loan_contract_amount AS loanContractAmount," + 
	"	t.amount," + 
	"	t.premium," + 
	"	t.rate," + 
	"	t.insured_name AS insuredName," + 
	"	t.insured_id_type AS insuredIdType," + 
	"	t.insured_id_no AS insuredIdNo," + 
	"	t.insured_gender AS insuredGender," + 
	"	t.insured_birthday AS insuredBirthday," + 
	"	t.insured_mobile AS insuredMobile," + 
	"	t.is_sick AS isSick," + 
	"	t.is_absenteeism AS isAbsenteeism," + 
	"	t.is_serious_illness AS isSeriousIllness," + 
	"	t.is_occupational_disease AS isOccupationalDisease," + 
	"	t.is_disability AS isDisability," + 
	"	t.is_pregnancy AS isPregnancy," + 
	"	t.go_abroad AS goAbroad," + 
	"   t.policy_Number AS policyNo," +
	"	t.insure_state AS insureState," +
	"   t.insure_message AS insureMessage" +
	"  FROM bw_metlife_insurance_order t" +
	"  WHERE t.r_id = #{rId}" +
	"  AND t.order_no = #{orderNo}" +
	"  ORDER BY t.create_time DESC")
	List<MetlifeInsuranceOrderDto> queryInsuranceOrderList(@Param(value = "rId") Long rId,@Param(value="orderNo") String orderNo);

	@Select("SELECT" + 
	"	uuid," + 
	"	batch_no batchNo," + 
	"	request_type requestType," + 
	"	send_time sendTime," + 
	"	grp_cont_plancode grpContPlancode," + 
	"	trim_date trimDate," + 
	"	grant_loans_date grantLoansDate," + 
	"	grant_loans_end_date grantLoansEndDate," + 
	"	order_no loanContractCode," + 
	"	loan_contract_amount loanContractAmount," + 
	"	pol_term_months polTermMonths," + 
	"	pol_term_years polTermYears," + 
	"	rate," + 
	"	amount amount," + 
	"	premium premium," + 
	"	insured_name insuredName," + 
	"	insured_id_type insuredIdType," + 
	"	insured_id_no insuredIdNo," + 
	"	insured_gender insuredGender," + 
	"	insured_birthday insuredBirthday," + 
	"	insured_mobile insuredMobile," + 
	"	is_sick isSick," + 
	"	is_absenteeism isAbsenteeism," + 
	"	is_serious_illness isSeriousIllness," + 
	"	is_occupational_disease isOccupationalDisease," + 
	"	is_disability isDisability," + 
	"	is_pregnancy isPregnancy," + 
	"	go_abroad goAbroad," + 
	"	insure_state insureState," + 
	"	policy_number policyNumber," + 
	"	update_time updateTime " + 
	" FROM bw_metlife_insurance_order " + 
	" WHERE order_no = #{orderNo} " + 
	" GROUP BY order_no,request_type" + 
	" ORDER BY batch_no ASC")
	List<BwMetlifeInsuranceOrder> queryInsuranceDetail(@Param(value = "orderNo") String orderNo);
	
	@Select("SELECT " + 
	"	t.uuid," + 
	"   o.order_no AS oderNo," +
	"	t.batch_no AS batchNo," + 
	"	t.send_time AS sendTime," + 
	"	t.grp_cont_plancode AS grpConPlancode," + 
	"	t.trim_date AS trimDate," + 
	"	t.grant_loans_date AS grantLoansDate," + 
	"	t.grant_loans_end_date AS grantLoansEndDate," + 
	"	t.loan_contract_code AS loanContractCode," + 
	"	t.loan_contract_amount AS loanContractAmount," + 
	"	t.amount," + 
	"	t.premium," + 
	"	t.rate," + 
	"	t.insured_name AS insuredName," + 
	"	t.insured_id_type AS insuredIdType," + 
	"	t.insured_id_no AS insuredIdNo," + 
	"	t.insured_gender AS insuredGender," + 
	"	t.insured_birthday AS insuredBirthday," + 
	"	t.insured_mobile AS insuredMobile," + 
	"	t.is_sick AS isSick," + 
	"	t.is_absenteeism AS isAbsenteeism," + 
	"	t.is_serious_illness AS isSeriousIllness," + 
	"	t.is_occupational_disease AS isOccupationalDisease," + 
	"	t.is_disability AS isDisability," + 
	"	t.is_pregnancy AS isPregnancy," + 
	"	t.go_abroad AS goAbroad," + 
	"   t.policy_Number AS policyNo," +
	"	o.insure_state AS insureState," +
	"   o.insure_message AS insureMessage" +
	"  FROM bw_metlife_insurance_detail t" +
	"  JOIN bw_metlife_insurance_order o on o.uuid = t.uuid" +
	"  LEFT JOIN bw_metlife_insurance_apply_record r on r.id = o.r_id"+
	"  WHERE t.policy_Number = #{policyNo}" +
	"  AND r.product_no = #{productNo}" +
	"  ORDER BY t.create_time ASC")
	List<MetlifeInsuranceOrderDto> queryInsuranceDetailList(@Param(value = "policyNo") String policyNo, @Param(value = "productNo") String productNo);
	
	@Select("SELECT " + 
	"	t.trim_date AS trimDate," + 
	"	t.grp_cont_plancode AS grpConPlancode," + 
	"	t.grant_loans_date AS grantLoansDate," + 
	"	t.grant_loans_end_date AS grantLoansEndDate," + 
	"	t.loan_contract_code AS loanContractCode," + 
	"	t.loan_contract_amount AS loanContractAmount," +
	"	t.insure_state AS insureState," + 
	"	t.insure_message AS insureMessage" + 
	"	FROM bw_metlife_insurance_order t" + 
	"	WHERE t.order_no = #{orderNo} " +
	"   order by t.batch_no ASC")
	List<MetlifeInsuranceOrderDto> queryOrderListByOrderNo(@Param(value="orderNo")String orderNo);

}
