//package com.waterelephant.sxyDrainage.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import com.waterelephant.service.BaseService;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjBill;
//import com.waterelephant.sxyDrainage.service.BwXygjBillService;
//
///**
// * 
// * <p>Title: BwXygjBillService</p>  
// * <p>Description: 信用管家 运营商账单信息</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Service
//public class BwXygjBillServiceImpl extends BaseService<BwXygjBill, Long>  implements BwXygjBillService {
//
//	/**
//	 * 保存月账单信息
//	 */
//	@Override
//	public Integer saveBill(BwXygjBill data) {
//		// TODO Auto-generated method stub
//		return mapper.insert(data);
//	}
//	
//	/**
//	 * 删除账单信息
//	 */
//    @Override
//    public int deleteAllByOrderId(Long orderId) {
//        String sql = "delete from bw_xygj_bill where order_id = " + orderId;
//        return sqlMapper.delete(sql);
//    }
//}
