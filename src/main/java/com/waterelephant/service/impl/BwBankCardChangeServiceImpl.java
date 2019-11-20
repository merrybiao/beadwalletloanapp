package com.waterelephant.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBankCardChange;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBankCardChangeService;
import com.waterelephant.service.IBwBankCardService;

/**
 * Module: 银行卡重绑记录表service的实现类
 * 
 * BwBankCardChangeServiceImpl.java
 * 
 * @author wangkun
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

@Service
public class BwBankCardChangeServiceImpl extends BaseService<BwBankCardChange, Long>
		implements BwBankCardChangeService {
	private Logger logger = Logger.getLogger(BwBankCardServiceImpl.class);
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwBorrowerService bwBorrowerService;

	/**
	 * 通过借款人id查询出所有的修改的个数
	 * 
	 * @see com.waterelephant.service.BwBankCardChangeService#findBwBankCardChangeByBorrowid(long)
	 */
	@Override
	public int findBwBankCardChangeByBorrowid(Long borrower_id) {
		String sql = "select count(1)  from bw_bank_card_change b where DATE_FORMAT(create_time,'%Y%m')=DATE_FORMAT(CURDATE(),'%Y%m') and borrower_id="
				+ borrower_id;
		int count = 0;
		Map<String, Object> selectOne = sqlMapper.selectOne(sql);
		if (!CommUtils.isNull(selectOne)) {
			count = Integer.parseInt(selectOne.get("count(1)").toString());
		}
		return count;
	}

	/**
	 * 修改借款人个人信息，修改银行卡提现卡，添加银行卡绑定记录
	 * 
	 * @param bw
	 * @param bankCode
	 * @param bankName
	 * @param cardNo
	 */
	@Override
	public void saveOrUpdBorrowerAndBankCardAndBankCardChange(BwBorrower bw, String bankCode, String bankName,
			String cardNo) {
		logger.info("保存到数据库=银行卡编码======" + bankCode);
		logger.info("保存到数据库=银行名字======" + bankName);
		logger.info("保存到数据库=银行卡号=====" + cardNo);

		int upd = bwBorrowerService.updateBwBorrower(bw);// 修改用户信息
		logger.info("修改用户记录条数=======" + upd);
		BwBankCard bbc = new BwBankCard();
		bbc.setBorrowerId(bw.getId());
		BwBankCard bbc1 = bwBankCardService.findBwBankCardByAttr(bbc);
		if (bbc1 != null) {
			// 添加设置卡
			BwBankCardChange cardChange = new BwBankCardChange();
			cardChange.setBankCode(bbc1.getBankCode());
			cardChange.setBankName(bbc1.getBankName());
			cardChange.setBorrowerId(bbc1.getBorrowerId());
			cardChange.setCardNo(bbc1.getCardNo());
			cardChange.setCityCode(bbc1.getCityCode());
			cardChange.setCreateTimeOld(bbc1.getCreateTime());
			cardChange.setId(null);
			cardChange.setPhone(bbc1.getPhone());
			cardChange.setProvinceCode(bbc1.getProvinceCode());
			cardChange.setSignStatus(bbc1.getSignStatus());
			cardChange.setUpdateTimeOld(bbc1.getUpdateTime());
			cardChange.setCreatedTime(new Date());
			this.mapper.insert(cardChange);

			bbc1.setCardNo(cardNo);
			bbc1.setUpdateTime(new Date());
			bbc1.setBankName(bankName);
			bbc1.setBankCode(bankCode);
			bbc1.setSignStatus(2);// 连连支付签约
			bbc1.setUpdateTime(new Date());
			int interBank = bwBankCardService.update(bbc1);
			logger.info("修改银行卡条数=======" + interBank);

		} else {

			bbc.setId(null);
			bbc.setCardNo(cardNo);
			bbc.setBankName(bankName);
			bbc.setBankCode(bankCode);
			bbc.setCreateTime(new Date());
			bbc.setUpdateTime(new Date());
			bbc.setSignStatus(2);
			int interBank = bwBankCardService.addBwBankCard(bbc);
			logger.info("添加银行卡条数=======" + interBank);
		}

	}

	/**
	 * 
	 * @see com.waterelephant.service.BwBankCardChangeService#insertByAtt(com.waterelephant.entity.BwBankCardChange)
	 */
	@Override
	public Integer insertByAtt(BwBankCardChange bwBankCardChange) {

		return mapper.insert(bwBankCardChange);
	}

}
