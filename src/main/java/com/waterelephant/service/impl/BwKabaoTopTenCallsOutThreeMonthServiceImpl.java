// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKabaoTopTenCallsOutThreeMonth;
// import com.waterelephant.service.BwKabaoTopTenCallsOutThreeMonthService;
//
//
//
// @Service
// public class BwKabaoTopTenCallsOutThreeMonthServiceImpl extends BaseService<BwKabaoTopTenCallsOutThreeMonth, Long>
// implements BwKabaoTopTenCallsOutThreeMonthService {
//
// @Override
// public Integer save(BwKabaoTopTenCallsOutThreeMonth BwKabaoTopTenCallsOutThreeMonth) {
//
// return mapper.insert(BwKabaoTopTenCallsOutThreeMonth);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_top_ten_calls_out_three_month where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
