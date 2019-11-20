// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKaBaoOperatorBase;
// import com.waterelephant.service.BwKaBaoOperatorBaseService;
//
//
//
// @Service
// public class BwKaBaoOperatorBaseServiceImpl extends BaseService<BwKaBaoOperatorBase, Long> implements
// BwKaBaoOperatorBaseService {
//
// @Override
// public Integer save(BwKaBaoOperatorBase bwKabaoOperatorBase) {
// return insert(bwKabaoOperatorBase);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_operator_base where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
