/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkEbusinessExpense;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkEbusinessExpenseService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkEbusinessExpenseServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkEbusinessExpenseServiceImpl extends BaseService<BwXjbkEbusinessExpense, Long>
// implements BwXjbkEbusinessExpenseService {
//
// /**
// * @see
/// com.waterelephant.service.BwXjbkEbusinessExpenseService#findByAttr(com.waterelephant.entity.BwXjbkEbusinessExpense)
// */
// @Override
// public List<BwXjbkEbusinessExpense> findByAttr(BwXjbkEbusinessExpense bwXjbkEbusinessExpense) {
// return mapper.select(bwXjbkEbusinessExpense);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkEbusinessExpenseService#save(com.waterelephant.entity.BwXjbkEbusinessExpense)
// */
// @Override
// public int save(BwXjbkEbusinessExpense bwXjbkEbusinessExpense) {
// return mapper.insert(bwXjbkEbusinessExpense);
// }
//
// /**
// * @see
/// com.waterelephant.service.BwXjbkEbusinessExpenseService#update(com.waterelephant.entity.BwXjbkEbusinessExpense)
// */
// @Override
// public int update(BwXjbkEbusinessExpense bwXjbkEbusinessExpense) {
// return mapper.updateByPrimaryKey(bwXjbkEbusinessExpense);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkEbusinessExpenseService#del(com.waterelephant.entity.BwXjbkEbusinessExpense)
// */
// @Override
// public int del(BwXjbkEbusinessExpense bwXjbkEbusinessExpense) {
// return mapper.delete(bwXjbkEbusinessExpense);
// }
//
// }
