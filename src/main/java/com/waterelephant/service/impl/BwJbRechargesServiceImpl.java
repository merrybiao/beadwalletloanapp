// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
//
/// **
// * (code:jb001)
// */
// @Service
// public class BwJbRechargesServiceImpl extends BaseCommonServiceImpl<BwJbRecharges, Long>
// implements BwJbRechargesService {
//
// @Override
// public Integer saveBwJbRechargesByAttr(BwJbRecharges bwJbRecharges) {
// return mapper.insert(bwJbRecharges);
// }
//
// @Override
// public Integer deleteBwJbRechargesByOrderId(Long orderId) {
// BwJbRecharges bwJbRecharges = new BwJbRecharges();
// bwJbRecharges.setOrderId(orderId);
// return mapper.delete(bwJbRecharges);
// }
// }