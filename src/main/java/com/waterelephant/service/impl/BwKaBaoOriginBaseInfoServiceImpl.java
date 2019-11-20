// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKaBaoOriginBaseInfo;
// import com.waterelephant.service.BwKaBaoOriginBaseInfoService;
//
//
//
// @Service
// public class BwKaBaoOriginBaseInfoServiceImpl extends BaseService<BwKaBaoOriginBaseInfo, Long> implements
// BwKaBaoOriginBaseInfoService {
//
// @Override
// public Integer save(BwKaBaoOriginBaseInfo bwKaBaoOriginBaseInfo) {
//
// return insert(bwKaBaoOriginBaseInfo);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_origin_base_info where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
