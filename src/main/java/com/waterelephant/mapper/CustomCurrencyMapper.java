package com.waterelephant.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface CustomCurrencyMapper {

	@Select("select a.id,b.name,b.phone,c.phone bank_phone,b.id_card,c.card_no,c.bank_code,c.bank_name, a.borrow_amount,a.borrower_id,a.order_no,d.p_term_type,d.p_term,d.zjw_cost"
			+ " ,a.product_type,a.product_id,a.borrow_number expect_number,a.borrow_number,e.address,e.relation_name,e.relation_phone,f.com_name,b.sex"
			+ " from bw_order a " + "LEFT JOIN bw_borrower b on b.id=a.borrower_id "
			+ "LEFT JOIN bw_product_dictionary d on a.product_id=d.id "
			+ "LEFT JOIN bw_bank_card c on c.borrower_id=b.id " + "LEFT JOIN bw_work_info f on f.order_id=a.id "
			+ "LEFT JOIN bw_person_info e on a.id=e.order_id where a.id=#{orderId} and (a.status_id =12 or a.status_id =14) limit 1")
	public Map<String, Object> findCapitalBaseNew(Long orderId);
}
