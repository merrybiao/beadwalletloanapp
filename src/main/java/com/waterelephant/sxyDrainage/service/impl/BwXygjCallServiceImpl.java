//package com.waterelephant.sxyDrainage.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import com.waterelephant.service.BaseService;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjCall;
//import com.waterelephant.sxyDrainage.service.BwXygjCallService;
//
///**
// * 
// * <p>Title: BwXygjCallService</p>  
// * <p>Description: 信用管家 运营商通话记录</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Service
//public class BwXygjCallServiceImpl extends BaseService<BwXygjCall, Long> implements BwXygjCallService {
//
//	/**
//	 * 保存通话记录信息
//	 */
//	@Override
//	public Integer saveCall(BwXygjCall data) {
//		// TODO Auto-generated method stub
//		return mapper.insert(data);
//	}
//
//	/**
//	 * 删除基本信息
//	 */
//    @Override
//    public int deleteAllByOrderId(Long orderId) {
//        String sql = "delete from bw_xygj_call where order_id = " + orderId;
//        return sqlMapper.delete(sql);
//    }
//}
