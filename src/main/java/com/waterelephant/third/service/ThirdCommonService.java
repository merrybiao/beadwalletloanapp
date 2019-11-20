package com.waterelephant.third.service;

import javax.servlet.http.HttpServletResponse;

import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.third.entity.ThirdResponse;

/**
 * 统一对外接口 - 公共service（code0091）
 *
 *
 * Module:
 *
 * ThirdCommonService.java
 *
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ThirdCommonService {
	/**
     * 判断用户是否有多个账号，是否有进行中的订单
     *
     * @param sessionId
     * @param idCard
     * @return false :表示不存在多个账号，true :表示存在多个账号和进行中的订单
     */
    boolean checkUserAccountProgressOrder(long sessionId, String idCard) throws Exception;

	/**
	 * 判断用户是否有多个账号，是否有进行中的订单(掩码身份证+姓名判断)
	 * @param sessionId
	 * @param idCard
	 * @param userName
	 * @return
	 * @throws Exception
	 */
    boolean checkUserAccountProgressOrder(long sessionId, String idCard, String userName) throws Exception;

	/**
     * 通过判断新老用户和产品期限获得产品
     *
     * @param name
     * @param idCard
     * @param phone
     * @param pTerm
     * @return
     */
	BwProductDictionary getProduct(String name, String idCard, String phone, Integer pTerm);

	/**
     * 通过判断新老用户和产品期限获得产品
     *
     * @param name
     * @param idCard
     * @param phone
     * @param pTerm
     * @return
     */
	BwProductDictionary getProductByLike(String name, String idCard, String phone, Integer pTerm);

	/**
     * 公共方法 - 保存或修改borrower
     *
     * @param name
     * @param idCard
     * @param phone
     * @param channelId
     * @return
     * @author liuDaodao
     */
	BwBorrower addOrUpdateBorrower(long sessionId, String name, String idCard, String phone, int channelId) throws Exception;

	/**
     * 公共方法 - 保存或修改BwOrderAuth
     *
     * @param sessionId
     * @param orderId
     * @param type
     * @param channelId
     * @return
     * @author liuDaodao
     */
	boolean addOrUpdateBwOrderAuth(long sessionId, long orderId, int type, int channelId);

	/**
     * 公共方法 - 保存或修改BwAdjunct
     *
     * @param sessionId
     * @param adjunctType
     * @param adjunctPath
     * @param adjunctDesc
     * @param orderId
     * @param borrowerId
     * @param photoState
     * @return
     * @author liuDaodao
     */
	boolean addOrUpdateBwAdjunct(long sessionId, int adjunctType, String adjunctPath, String adjunctDesc, long orderId,
            long borrowerId, Integer photoState);

	/**
     * 公共方法 - 还款
     *
     * @param sessionId
     * @param bwOrder
     * @param bwBankCard
     * @param type
     * @return
     */
	ThirdResponse thirdRepay(long sessionId, BwOrder bwOrder, BwBankCard bwBankCard, String amount, String type);

	/**
     *
     * 公共方法 - 绑卡
     *
     * @param borrowerID
     * @param orderNO
     * @param bankCardNO
     */
	void bindCard(String borrowerID, String orderNO, String bankCardNO, HttpServletResponse response);

	/**
     * 公共方法 - 签约
     *
     * @param orderId
     * @return
     */
	ThirdResponse updateSignContract(String thirdOrderNo, Integer channelId);
}
