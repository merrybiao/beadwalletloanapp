/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.bd;

import java.util.Map;

/**
 * @author 崔雄健
 * @date 2017年3月21日
 * @description
 */
public interface ChannelService {

	public Map<String, Object> sendOrderStatus(String channelId, String orderId, String statusId);

	/**
	 * @author 崔雄健
	 * @date 2017年4月14日
	 * @description 消息队列
	 * @param
	 * @return
	 */
	public Map<String, Object> sendOrderStatusMQ(String channelId, String orderId, String statusId);

	/**
	 * @author liuDaodao
	 * @date 2017年5月16日
	 * @description 贷款钱包 - 消息队列（code0082）
	 * @param
	 * @return
	 */
	public Map<String, Object> sendOrderStatusMQ(Map<String, String> paramMap);

	/**
	 * @author liuDaodao
	 * @date 2017年5月16日
	 * @description 贷款钱包 - 消息队列 - 审核通过（code0082）
	 * @param
	 * @return
	 */
	public Map<String, Object> loanWallet_sendOrderStatusMQ_checkThrough(Map<String, String> paramMap);

	/**
	 * @author liuDaodao
	 * @date 2017年5月16日
	 * @description 贷款钱包 - 消息队列 - 审核拒绝（code0082）
	 * @param
	 * @return
	 */
	public Map<String, Object> loanWallet_sendOrderStatusMQ_checkReject(Map<String, String> paramMap);

	/**
	 * @author liuDaodao
	 * @date 2017年5月16日
	 * @description 贷款钱包 - 消息队列 - 回调（code0082）
	 * @param
	 * @return
	 */
	public Map<String, Object> loanWallet_sendOrderStatusMQ_callback(Map<String, String> paramMap);

}
