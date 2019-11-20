// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwJbSmses;
// import com.waterelephant.service.BwJbSmsesService;
// import org.springframework.stereotype.Service;
//
/// **
// * (code:jb001)
// */
// @Service
// public class BwJbSmsesServiceImpl extends BaseCommonServiceImpl<BwJbSmses, Long> implements BwJbSmsesService {
//
//
// @Override
// public Integer saveBwJbSmsesByAttr(BwJbSmses bwJbSmses) {
// return mapper.insert(bwJbSmses);
// }
//
// @Override
// public Integer deleteBwJbSmsesByOrderId(Long orderId) {
// BwJbSmses bwJbSmses = new BwJbSmses();
// bwJbSmses.setOrderId(orderId);
// return mapper.delete(bwJbSmses);
// }
// }