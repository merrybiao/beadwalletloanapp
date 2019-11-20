package com.waterelephant.drainage.service;

import com.waterelephant.drainage.entity.common.DrainageResponse;
import com.waterelephant.drainage.entity.common.PushOrderRequest;
import com.waterelephant.drainage.entity.fqgj.DrainageResp;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.jiedianqian.entity.UserCheckResp;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/26 10:36
 */
public interface DrainageService {

	/**
	 * 判断老用户
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 */
	DrainageResp isOldUser(String idCard, String name);

	/**
	 * 公共方法 - 判断是否是老用户
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 * @author liuDaodao
	 * 
	 */
	boolean isOldUser2(String idCard, String name);

	/**
	 * 公共方法 - 判断是否是老用户
	 * 
	 * @param name
	 * @param phone
	 * @param id_number
	 * @return
	 * @author 张博
	 * 
	 */
	boolean oldUserFilter2(String name, String phone, String id_number);

	/**
	 * 公共方法 - 判断是否黑名单
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 * @author liuDaodao
	 * 
	 */
	boolean isBlackUser(String idCard, String name);

	/**
	 * 公共方法 - 判断是否黑名单
	 * 
	 * @param id_number
	 * @param name
	 * @return
	 * @author 张博
	 * 
	 */
	boolean isBlackUser2(String name, String phone, String id_number);

	/**
	 * 公共方法 - 判断是否有进行中的订单
	 * 
	 * @param idCard
	 * @return boolean
	 * @author liuDaodao
	 * 
	 */
	boolean isPocessingOrder(String idCard);

	/**
	 * 公共方法 - 判断是否有进行中的订单(掩码版)
	 * 
	 * @param id_number
	 * @return boolean
	 * @author 张博
	 * 
	 */
	boolean isPocessingOrder2(String name, String phone, String id_number);

	/**
	 * 公共方法 - 是否被拒
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 * @author liuDaodao
	 */
	boolean isRejectRecord(String idCard, String name);

	/**
	 * 公共方法 - 是否被拒(掩码版)
	 * 
	 * @param name
	 * @param phone
	 * @param id_number
	 * @return
	 * @author 张博
	 */
	UserCheckResp isRejectRecord2(String name, String phone, String id_number);

	/**
	 * 公共方法 - 添加或修改用户信息
	 * 
	 * @param name
	 * @param idCard
	 * @param phone
	 * @param channel(渠道ID)
	 * @return BwBorrower
	 * @author liuDaodao
	 * 
	 */
	BwBorrower addOrUpdateBwer(String name, String idCard, String phone, int channel);

	/**
	 * 推单保存
	 * 
	 * @param sessionId
	 * @param pushOrderRequest
	 */
	DrainageResponse savePushOrder(long sessionId, PushOrderRequest pushOrderRequest);

}
