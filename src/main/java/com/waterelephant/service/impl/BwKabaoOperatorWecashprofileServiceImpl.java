// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKabaoOperatorWecashprofile;
// import com.waterelephant.service.BwKabaoOperatorWecashprofileService;
//
//
//
// @Service
// public class BwKabaoOperatorWecashprofileServiceImpl extends BaseService<BwKabaoOperatorWecashprofile, Long>
// implements BwKabaoOperatorWecashprofileService {
//
// @Override
// public Integer save(BwKabaoOperatorWecashprofile bwKabaoOperatorWecashprofile) {
//
// return mapper.insert(bwKabaoOperatorWecashprofile);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_operator_wecashprofile where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
