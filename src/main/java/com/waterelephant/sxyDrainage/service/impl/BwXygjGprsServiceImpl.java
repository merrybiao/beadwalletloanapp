//package com.waterelephant.sxyDrainage.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import com.waterelephant.service.BaseService;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjGprs;
//import com.waterelephant.sxyDrainage.service.BwXygjGprsService;
//
///**
// * 
// * <p>Title: BwXygjGprsService</p>  
// * <p>Description: 信用管家 运营商gprs记录</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Service
//public class BwXygjGprsServiceImpl extends BaseService<BwXygjGprs, Long> implements BwXygjGprsService {
//
//	/**
//	 * 保存gprs记录信息
//	 */
//	@Override
//	public Integer saveGprs(BwXygjGprs data) {
//		// TODO Auto-generated method stub
//		return mapper.insert(data);
//	}
//
//	/**
//	 * 删除gprs记录
//	 */
//    @Override
//    public int deleteAllByOrderId(Long orderId) {
//        String sql = "delete from bw_xygj_gprs where order_id = " + orderId;
//        return sqlMapper.delete(sql);
//    }
//}
