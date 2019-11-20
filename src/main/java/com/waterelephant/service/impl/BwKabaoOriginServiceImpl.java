// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
//
// import com.waterelephant.entity.BwKaBaoOrigin;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwKaBaoOriginService;
//
//
//
// @Service
// public class BwKabaoOriginServiceImpl extends BaseService<BwKaBaoOrigin, Long> implements BwKaBaoOriginService {
//
// @Override
// public Integer save(BwKaBaoOrigin bwKaBaoOrigin) {
// return insert(bwKaBaoOrigin);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_origin where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
