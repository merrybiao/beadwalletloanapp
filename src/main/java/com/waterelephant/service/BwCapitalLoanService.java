package com.waterelephant.service;

import com.waterelephant.entity.BwCapitalLoan;

/**
 * 资方还款计划推送记录
 * 
 * @author 崔雄健
 * @date 2017年4月10日
 * @description
 */
public interface BwCapitalLoanService {

	/**
	 * @author 崔雄健
	 * @date 2017年4月6日
	 * @description 保存资方订单信息
	 * @param
	 * @return
	 */
	void save(BwCapitalLoan bwCapitalLoan);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年4月26日
	 * @description 更新推送信息
	 * @param
	 * @return
	 */
	int updateBwCapitalLoan(BwCapitalLoan bwCapitalLoan);

	BwCapitalLoan queryBwCapitalLoan(Long orderId, int capitalId);

	int updateBwCapitalLoanByorderId(Long orderId, int capitalId, int pushStatus);

	BwCapitalLoan queryBwCapitalLoanCapitalNo(Long orderId, int capitalId, String capitalNo);

}
