//package com.waterelephant.sxyDrainage.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import com.waterelephant.service.BaseService;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjBase;
//import com.waterelephant.sxyDrainage.service.BwXygjBaseService;
//
///**
// * 
// * <p>Title: BwXygjBase</p>  
// * <p>Description: 信用管家运营商基本信息</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Service
//public class BwXygjBaseServiceImpl extends BaseService<BwXygjBase, Long> implements BwXygjBaseService {
//
//	/**
//	 * 保存基本信息
//	 */
//	@Override
//	public Integer saveBase(BwXygjBase data) {
//		// TODO Auto-generated method stub
//		return mapper.insert(data);
//	}
//
//	/**
//	 * 删除基本信息
//	 */
//    @Override
//    public int deleteAllByOrderId(Long orderId) {
//        String sql = "delete from bw_xygj_base where order_id = " + orderId;
//        return sqlMapper.delete(sql);
//    }
//
//}
