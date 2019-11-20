package com.waterelephant.drainage.service;

import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderRong;

/**
 * 百度金融
 * 
 * 
 * @author dengyan
 *
 */
public interface BaiduService {

	/**
	 * 根据身份证号码和用户姓名查询用户信息
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 */
	BwBorrower getUserInfoByIdCardAndName(String idCard, String name);

	// /**
	// * 存储百度补充工单信息
	// * @param req
	// * @param result
	// * @return
	// * @throws Exception
	// */
	// HttpResult saveOrderPush(SignEntity req, HttpResult result) throws Exception;
	//

	/**
	 * 生成工单空白工单
	 * 
	 * @param borrowerId
	 * @return
	 */
	Long saveOrder(Long borrowerId);

	/**
	 * 保存三方工单
	 * 
	 * @param orderId
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	BwOrderRong saveBwROrder(Long orderId, String orderNo) throws Exception;

	/**
	 * 签约之前获取预估还款日期
	 * 
	 * @return
	 */
	String getRepayDate();
}
