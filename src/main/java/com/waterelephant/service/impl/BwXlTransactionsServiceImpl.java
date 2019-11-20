// package com.waterelephant.service.impl;
//
// import org.springframework.stereotype.Service;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.entity.BwXlTransactions;
// import com.waterelephant.service.BwXlTransactionsService;
//
//
//
// @Service
// public class BwXlTransactionsServiceImpl extends BaseService<BwXlTransactions, Long> implements
// BwXlTransactionsService {
//
// @Override
// public int save(BwXlTransactions t) {
// return mapper.insert(t);
// }
//
// @Override
// public int deleteByOrderId(Long orderId) {
// BwXlTransactions t = new BwXlTransactions();
// t.setOrderId(orderId);
// return mapper.delete(t);
// }
// }
