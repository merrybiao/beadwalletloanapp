// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKaBaoOriginBillInfo;
// import com.waterelephant.service.BwKaBaoOriginBillInfoService;
//
//
//
// @Service
// public class BwKaBaoOriginBillInfoServiceImpl extends BaseService<BwKaBaoOriginBillInfo, Long> implements
// BwKaBaoOriginBillInfoService {
//
// @Override
// public Integer save(BwKaBaoOriginBillInfo bwKaBaoOriginBillInfo) {
// return insert(bwKaBaoOriginBillInfo);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_origin_bill_info where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
