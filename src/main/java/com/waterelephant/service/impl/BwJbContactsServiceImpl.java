// package com.waterelephant.service.impl;
//
//
// import com.waterelephant.entity.BwJbContacts;
// import com.waterelephant.service.BwJbContactsService;
// import org.springframework.stereotype.Service;
//
// import java.io.BufferedWriter;
//
/// **
// * (code:jb001)
// */
// @Service
// public class BwJbContactsServiceImpl extends BaseCommonServiceImpl<BwJbContacts, Long> implements BwJbContactsService
// {
//
//
// @Override
// public Integer saveBwJbContactsByAttr(BwJbContacts bwJbContacts) {
// return mapper.insert(bwJbContacts);
// }
//
// @Override
// public Integer deleteBwJbContactsByOrderId(Long orderId) {
// BwJbContacts bwJbContacts = new BwJbContacts();
// bwJbContacts.setOrderId(orderId);
// return mapper.delete(bwJbContacts);
// }
//
// @Override
// public BwJbContacts findByAttr(BwJbContacts bwJbContacts) {
// return mapper.selectOne(bwJbContacts);
// }
//
// @Override
// public Integer updateByAttr(BwJbContacts bwJbContacts) {
// return mapper.updateByPrimaryKey(bwJbContacts);
// }
// }