// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKaBaoOriginCallInfo;
// import com.waterelephant.service.BwKaBaoOriginCallInfoService;
//
//
//
// @Service
// public class BwKaBaoOriginCallInfoServiceImpl extends BaseService<BwKaBaoOriginCallInfo, Long> implements
// BwKaBaoOriginCallInfoService {
//
// @Override
// public Integer save(BwKaBaoOriginCallInfo bwKaBaoOriginCallInfo) {
// return insert(bwKaBaoOriginCallInfo);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_origin_call_info where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
