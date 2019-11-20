package com.waterelephant.service;

import java.util.Date;
import java.util.List;

import com.waterelephant.entity.BwCheckRecord;
import com.waterelephant.entity.BwOrder;



/**
 * 
* @ClassName: BwCheckRecordService 
* @Description: TODO(工单审核记录业务接口) 
* @author SongYajun
* @date 2016年8月27日 下午9:05:19 
*
 */
public interface BwCheckRecordService {
		
		/**
		 * @param result 
		 * 
		* @Title: saveBwCheckRecord 
		* @Description: TODO(添加工单审核记录,更新工单) 
		* @param @param bwOrder
		* @param @param bwCheckRecord
		* @param @return    设定文件 
		* @return int    返回类型 
		* @throws
		 */
		int saveBwCheckRecord(BwOrder bwOrder ,BwCheckRecord bwCheckRecord, String result );
		
		int addBwCheckRecordUpOrder(BwOrder bwOrder ,BwCheckRecord bwCheckRecord, String result);
		
		/**
		 * 
		* @Title: orderRefused 
		* @Description: TODO(副理拒绝，客户拒绝) 
		* @param @param bwCheckRecord    设定文件 
		* @return void    返回类型 
		* @throws
		 */
		void updateOrderRefused(BwCheckRecord bwCheckRecord);

		int saveCardinal(String grade, String limit, String id);

		List<BwCheckRecord> queryCheck(String orderId);

		void add(BwCheckRecord bwCheckRecord);
		
		int isThrough(String orderId);
		
		/**
		 * 根据工单id查询工单撤回或者拒绝的工单创建时间
		 * @param orederId
		 * @return
		 */
		Date findCreateTimeByOrderId(Long orederId);
		
		/**
		 * 根据工单id、工单状态、工单结果查询创建时间
		 * @param orederId
		 * @return
		 */
		Date findCreateTimeByOrderId(BwCheckRecord bwCheckRecord);
		
		/**
		 * 
		 * @Description: 根据工单Id查询最新被撤回的工单记录
		 * @return BwCheckRecord 
		 * @author liven
		 * @date 2017年5月4日 上午9:02:29
		 *
		 */
		BwCheckRecord findNewWithdrawByOrderId(String orderId); 
}	
