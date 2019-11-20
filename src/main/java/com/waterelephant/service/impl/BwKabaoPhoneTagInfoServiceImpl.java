// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
//
// import com.waterelephant.entity.BwKabaoPhoneTagInfo;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwKabaoPhoneTagInfoService;
//
//
//
// @Service
// public class BwKabaoPhoneTagInfoServiceImpl extends BaseService<BwKabaoPhoneTagInfo, Long> implements
// BwKabaoPhoneTagInfoService {
//
// @Override
// public Integer save(BwKabaoPhoneTagInfo bwKabaoPhoneTagInfo) {
//
// return insert(bwKabaoPhoneTagInfo);
// }
//
// @Override
// public Integer deleteAllByOrderId(Long orderId) {
// String sql = "delete from bw_kabao_phone_tag_info where order_id = " + orderId;
// return sqlMapper.delete(sql);
// }
//
//
// }
