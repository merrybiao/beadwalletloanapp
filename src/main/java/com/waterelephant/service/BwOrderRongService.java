package com.waterelephant.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.BwOrderRong;

public interface BwOrderRongService {

	// 添加
	public int save(BwOrderRong bwOrderRong);

	// 根据rong订单号查询
	public BwOrderRong getByOrderNo(String orderNo);

	// 查询
	public BwOrderRong findBwOrderRongByAttr(BwOrderRong bwOrderRong);

	/**
	 * 用我们得工单id、渠道来源查询第三方工单id
	 * 
	 * @param orderId 我方工单id
	 * @return
	 */
	public String findThirdOrderNoByOrderId(String orderId);

	/**
	 * @author 崔雄健
	 * @date 2017年3月10日
	 * @description
	 * @param
	 * @return
	 */
	public int findThirdOrderNoCount(String thirdOrderNo, int channelId);

	public BwOrderRong findBwOrderRongByOrderId(Long orderId);

	/**
	 * @author 崔雄健
	 * @date 2017年4月5日
	 * @description
	 * @param
	 * @return
	 */
	public int updateBwOrderRongNo(BwOrderRong bwOrderRong);

	/**
	 * @author 崔雄健
	 * @date 2017年5月12日
	 * @description
	 * @param
	 * @return
	 */
	List<Map<String, Object>> btsj();

	/**
	 * 修改融360订单（code0091）
	 * 
	 * @param bwOrderRong
	 * @return
	 * @author liuDaodao
	 */
	public int update(BwOrderRong bwOrderRong);

	/**
	 * 
	 * @param orderId
	 * @param channelId
	 * @return
	 */
	BwOrderRong findBwOrderRongByOrderIdAndChannel(Long orderId, Long channelId);

}
