// package com.waterelephant.service.impl;
//
//
// import com.waterelephant.entity.BwJbBills;
// import com.waterelephant.service.BwJbBillsService;
// import org.springframework.stereotype.Service;
//
/// **
// * (code:jb001)
// */
// @Service
// public class BwJbBillsServiceImpl extends BaseCommonServiceImpl<BwJbBills, Long> implements BwJbBillsService {
//
// @Override
// public Integer saveBwJbBillsByAttr(BwJbBills bwJbBills) {
// return mapper.insert(bwJbBills);
// }
//
// @Override
// public Integer deleteBwJbBillsByOrderId(Long orderId) {
// BwJbBills bwJbBills = new BwJbBills();
// bwJbBills.setOrderId(orderId);
// return mapper.delete(bwJbBills);
// }
// }