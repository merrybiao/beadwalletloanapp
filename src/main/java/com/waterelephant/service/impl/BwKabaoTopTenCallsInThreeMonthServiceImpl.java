// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKabaoTopTenCallsInThreeMonth;
// import com.waterelephant.service.BwKabaoTopTenCallsInThreeMonthService;
//
//
//
// @Service
// public class BwKabaoTopTenCallsInThreeMonthServiceImpl extends BaseService<BwKabaoTopTenCallsInThreeMonth, Long>
// implements BwKabaoTopTenCallsInThreeMonthService {
//
// @Override
// public Integer save(BwKabaoTopTenCallsInThreeMonth bwKabaoTopTenCallsInThreeMonth) {
//
// return mapper.insert(bwKabaoTopTenCallsInThreeMonth);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_top_ten_calls_in_three_month where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
