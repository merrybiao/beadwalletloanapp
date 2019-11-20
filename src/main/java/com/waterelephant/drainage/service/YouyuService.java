
package com.waterelephant.drainage.service;

import com.waterelephant.drainage.entity.youyu.QueryOrderStatus;
import com.waterelephant.drainage.entity.youyu.SignOrder;
import com.waterelephant.drainage.entity.youyu.TalculationCharges;
import com.waterelephant.drainage.entity.youyu.YouyuBankCard;
import com.waterelephant.drainage.entity.youyu.YouyuRequestCheckUser;
import com.waterelephant.drainage.entity.youyu.YouyuRequestPush;
import com.waterelephant.drainage.entity.youyu.YouyuResponse;
import com.waterelephant.entity.BwOrder;

/***
 * 
 * 
 * 
 * Module: 有鱼
 * 
 * YouyuService.java
 * 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface YouyuService {

	/**
	 * 
	 * @Title: checkUser @Description: 检查用户 @param sessionId @param
	 * requestCheckUser @return @return YouyuResponse @throws
	 */
	YouyuResponse checkUser(long sessionId, YouyuRequestCheckUser requestCheckUser);

	YouyuResponse savePushLoanBaseInfo(long sessionId, YouyuRequestPush youyuRequestPush);

	/**
	 * 
	 * 
	 */
	YouyuResponse queryOrderStatus(long sessionId, QueryOrderStatus queryOrderStatus);

	/**
	 * 有鱼-绑卡 @Title: bindCard @Description: TODO @param sessionId @param
	 * youyuBankCard @return @return YouyuResponse @throws
	 */
	//YouyuResponse bindCard(long sessionId, YouyuBankCard youyuBankCard);

	/**
	 * 
	 * @Title: updateSignContract @Description: TODO @param sessionId @param
	 * queryOrderStatus @return @return YouyuResponse @throws
	 */
	YouyuResponse updateSignContract(long sessionId, SignOrder signOrder);

	/**
	 * 
	 * @Title: agreement @Description: 合同接口 @param sessionId @param
	 * signOrder @return @return YouyuResponse @throws
	 */

	YouyuResponse agreement(long sessionId, SignOrder signOrder);

	/**
	 * 
	 * @Title: count @Description: 费用试算 @param sessionId @param
	 * talculationCharges @return @return YouyuResponse @throws
	 */

	YouyuResponse count(long sessionId, String loan_money, String loan_term, String money_unit, String term_unit);

	/**
	 * 
	 * @Title: repayment @Description: 还款接口 @param sessionId @param
	 * signOrder @return @return YouyuResponse @throws
	 */
	YouyuResponse repayment(long sessionId, String order_no);

	//跳转他们成功或失败页面
	String montageURL(String orderNo,String torder_no);
/**
 * 存url
 * @Title: syntonyUrl 
 * @Description:
 * @return: String
 */
	String syntonyUrl(String orderNo, String torder_no);

	YouyuResponse finish(long sessionId, BwOrder bwOrder);

	

}