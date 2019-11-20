// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
//
// import com.waterelephant.entity.BwKabaoOperatorRiskprofile;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwKabaoOperatorRiskprofileService;
//
//
//
// @Service
// public class BwKabaoOperatorRiskprofileServiceImpl extends BaseService<BwKabaoOperatorRiskprofile, Long> implements
// BwKabaoOperatorRiskprofileService {
//
// @Override
// public Integer save(BwKabaoOperatorRiskprofile bwKabaoOperatorRiskprofile) {
//
// return mapper.insert(bwKabaoOperatorRiskprofile);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_operator_riskprofile where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
