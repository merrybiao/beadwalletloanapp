// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwKabaoReportTotalDict;
// import com.waterelephant.service.BwKabaoReportTotalDictService;
//
//
//
// @Service
// public class BwKabaoReportTotalDictServiceImpl extends BaseService<BwKabaoReportTotalDict, Long> implements
// BwKabaoReportTotalDictService {
//
// @Override
// public int save(BwKabaoReportTotalDict t) {
// return mapper.insert(t);
// }
//
// @Override
// public int deleteByOrderId(Long orderId) {
// BwKabaoReportTotalDict t = new BwKabaoReportTotalDict();
// t.setOrderId(orderId);
// return mapper.delete(t);
// }
// }
