// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKabaoTotalContactInfo;
// import com.waterelephant.service.BwKabaoTotalContactInfoService;
//
//
//
// @Service
// public class BwKabaoTotalContactInfoServiceImpl extends BaseService<BwKabaoTotalContactInfo, Long> implements
// BwKabaoTotalContactInfoService {
//
// @Override
// public Integer save(BwKabaoTotalContactInfo bwKabaoTotalContactInfo) {
// return mapper.insert(bwKabaoTotalContactInfo);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_total_contact_info where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
