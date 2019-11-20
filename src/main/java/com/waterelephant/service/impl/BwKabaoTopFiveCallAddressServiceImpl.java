// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKabaoTopFiveCallAddress;
// import com.waterelephant.service.BwKabaoTopFiveCallAddressService;
//
//
//
// @Service
// public class BwKabaoTopFiveCallAddressServiceImpl extends BaseService<BwKabaoTopFiveCallAddress, Long> implements
// BwKabaoTopFiveCallAddressService {
//
// @Override
// public Integer save(BwKabaoTopFiveCallAddress bwKabaoTopFiveCallAddress) {
//
// return mapper.insert(bwKabaoTopFiveCallAddress);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_top_five_call_address where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
