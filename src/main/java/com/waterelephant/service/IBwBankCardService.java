package com.waterelephant.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.dto.PaySignDto;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;

import com.waterelephant.utils.AppResponseResult;
import tk.mybatis.mapper.entity.Example;

public interface IBwBankCardService {

	BwBankCard findBwBankCardByAttr(BwBankCard bwBankCard);

	int saveBwBankCard(BwBankCard bwBankCard, Long bId);

	// 根据借款人id查询银行卡信息
	BwBankCard findBwBankCardByBorrowerId(Long borrowerId);

	// 根据历史id和借款人id查询信息
	BwBankCard findBwBankCardById(Long id);

	// 修改银行卡信息
	int update(BwBankCard bwBankCard);

	/**
	 * 根据条件查找银行卡信息
	 * 
	 * @param example 查询条件
	 * @return
	 */
	List<BwBankCard> findBwBankCardByExample(Example example);

	int addBwBankCard(BwBankCard bwBankCard);

	/**
	 * 根据银行卡开户省份、城市名称查询省份code和城市code
	 * 
	 * @param provinceName 银行开户省份名称
	 * @param cityName 银行卡开户城市名称
	 * @return
	 */
	BwBankCard findBwBankCardByCityNameAndPName(String provinceName, String cityName);

	Map<String, Object> query(String userId);

	Map<String, Object> toSigned(String orderId);

	int updateBwBankCard(BwBankCard bwBankCard);

	BwBankCard findBwBankCardByBoorwerId(Long boorwerId);

	void updateSign(String login_id);

	/**
	 * 根据借款人id查询用户是否签约
	 * 
	 * @param borrowerId 借款人id
	 * @return
	 */
	Integer findSignStatusByBorrowerId(Long borrowerId);

	/**
	 * 根据用户id更新签约状态为已签约
	 * 
	 * @param borrowerId 借款人id
	 * @return
	 */
	int updateSignStatusByBorrowerId(Long borrowerId);

	void saveOrUpdBorrowerAndBankCard(BwBorrower bw, String bankCode, String bankName, String cardNo);

	void deleteBankCard(Long borrowerId);

    BwBankCard selectByBorrowerId(Long borrowerId);

	boolean isBindCardAgain(BwBankCard bwBankCard);

    boolean hasUseIdCard(String idCard, String phone);

	/**
	 * 根据身份证查询是否有绑定
	 *
	 * @param idCard
	 * @param phone
	 * @param appId 用户app类型(1-水象分期，2-77钱包，3-乐分期)
	 * @return
	 */
	boolean hasUseIdCard(String idCard, String phone, Integer appId);

	int clacAgeByIdCard(String idCard);

    AppResponseResult canBindCard(PaySignDto paySignDto);

	/**
	 * 预绑卡
	 *
	 * @param paySignDto 必传参数：borrowerId、phone、cardNo，新绑卡必传name、idCard
	 * @return
	 */
	AppResponseResult updateAndReadyBindBankCard(PaySignDto paySignDto);

	/**
	 * 确认绑卡
	 *
	 * @param paySignDto 必传参数：borrowerId借款人ID，verifyCode验证码
	 * @return
	 */
    AppResponseResult updateAndSureBindCard(PaySignDto paySignDto);

    PaySignDto queryRedisPaySign(Long borrowerId);
    
    /**
	 * 根据三方订单号和银行卡号，查询银行卡
	 * 
	 * @param thirdOrderNo
	 * @param bankCode
	 * @return BwBankCard
	 */
	BwBankCard findBwBankCardByThirdOrderNoAndCardNo(String thirdOrderNo, String cardNo);
}
