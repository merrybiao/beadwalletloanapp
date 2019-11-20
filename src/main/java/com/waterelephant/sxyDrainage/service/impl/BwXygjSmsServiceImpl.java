//package com.waterelephant.sxyDrainage.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import com.waterelephant.service.BaseService;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjSms;
//import com.waterelephant.sxyDrainage.service.BwXygjSmsService;
//
//
///**
// * 
// * <p>Title: BwXygjCallService</p>  
// * <p>Description: 信用管家 运营商短信记录</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Service
//public class BwXygjSmsServiceImpl extends BaseService<BwXygjSms, Long> implements BwXygjSmsService {
//
//	/**
//	 * 保存短信记录信息
//	 */
//	@Override
//	public Integer saveSms(BwXygjSms data) {
//		// TODO Auto-generated method stub
//		return mapper.insert(data);
//	}
//	
//	/**
//	 * 删除短信记录
//	 */
//    @Override
//    public int deleteAllByOrderId(Long orderId) {
//        String sql = "delete from bw_xygj_sms where order_id = " + orderId;
//        return sqlMapper.delete(sql);
//    }
//}
