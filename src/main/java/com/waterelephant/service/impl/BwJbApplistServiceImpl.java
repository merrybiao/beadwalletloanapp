// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwJbApplist;
// import com.waterelephant.service.BwJbApplistService;
// import org.springframework.stereotype.Service;
//
/// **
// * (code:jb001)
// */
// @Service
// public class BwJbApplistServiceImpl extends BaseCommonServiceImpl<BwJbApplist,Long> implements BwJbApplistService {
//
//
// @Override
// public Integer saveBwJbAppListByAttr(BwJbApplist bwJbApplist) {
// return mapper.insert(bwJbApplist);
// }
//
// @Override
// public Integer deleteBwJbAppListByOrderId(Long orderId) {
// BwJbApplist bwJbApplist = new BwJbApplist();
// bwJbApplist.setOrderId(orderId);
// return mapper.delete(bwJbApplist);
// }
// }