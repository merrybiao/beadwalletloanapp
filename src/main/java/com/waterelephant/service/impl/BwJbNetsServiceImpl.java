// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwJbNets;
// import com.waterelephant.service.BwJbNetsService;
// import org.springframework.stereotype.Service;
//
/// **
// * (code:jb001)
// */
// @Service
// public class BwJbNetsServiceImpl extends BaseCommonServiceImpl<BwJbNets, Long> implements BwJbNetsService {
//
//
// @Override
// public Integer saveBwJbNetsByAttr(BwJbNets bwJbNets) {
// return mapper.insert(bwJbNets);
// }
//
// @Override
// public Integer deleteBwJbNetsByOrderId(Long orderId) {
// BwJbNets bwJbNets = new BwJbNets();
// bwJbNets.setOrderId(orderId);
// return mapper.delete(bwJbNets);
// }
// }