package com.waterelephant.service;


import java.util.HashMap;

import com.beadwallet.entity.response.QueryBalanceRspData;
import com.beadwallet.entity.response.ResponsePayResult;
import com.beadwallet.entity.response.WithdrawRspData;
import com.beadwallet.entity.response.WtrechargeRspData;
import com.waterelephant.utils.AppResponseResult;

/**
 * 
* @ClassName: FuYouService 
* @Description: TODO(和富有之间查询余额、冻结、解冻、卡扣接口) 
* @author SongYaJun
* @date 2016年11月15日 上午10:18:48 
*
 */
public interface FuYouService {
		
	/**
	 * 根据富有账号查询余额
	 * @param fuYouAccount 用户账户
	 */
	ResponsePayResult<QueryBalanceRspData> getAccountBalance(String fuYouAccount);

	/**
	 * 根据账户冻结金额
	 * @param fuYouAccount  用户账户
	 * @param monery		需要冻结的金额
	 */
	AppResponseResult freezeAccount(String fuYouAccount, Double monery);
	
	/**
	 * 卡扣
	 */
	WtrechargeRspData Wtrecharge(String fuYouAccount,Double monery) throws Exception;
	
	/**
	 * 解冻
	 * @param fuYouAccount 富有账户
	 * @param monery 解冻金额
	 * @return
	 * @throws Exception
	 */
	AppResponseResult removeFreeze(String userId,Double monery,String mchnt_txn_ssn);
	
	/**
	 * 委托提现
	 * @param fuYouAccount
	 * @param monery
	 * @return
	 * @throws Exception
	 */
	WithdrawRspData withdraw(String fuYouAccount,Double monery)throws Exception;
	/**
	 *查询富友账号余额
	 */
	HashMap<String,Double>  getFuiouAmount(String userId) ;
	/**
	 *转账
	 */
	AppResponseResult bwTransferBu(String userId, Double amt, String mchnt_txn_ssn);
}
