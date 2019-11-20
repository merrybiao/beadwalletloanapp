// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKabaoOperatorStatistics;
// import com.waterelephant.service.BwKabaoOperatorStatisticsService;
//
//
//
// @Service
// public class BwKabaoOperatorStatisticsServiceImpl extends BaseService<BwKabaoOperatorStatistics, Long> implements
// BwKabaoOperatorStatisticsService {
//
// @Override
// public Integer save(BwKabaoOperatorStatistics bwKabaoOperatorStatistics) {
//
// return mapper.insert(bwKabaoOperatorStatistics);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_operator_statistics where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
