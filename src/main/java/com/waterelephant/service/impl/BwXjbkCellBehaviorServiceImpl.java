/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkCellBehavior;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkCellBehaviorService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkCellBehaviorServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkCellBehaviorServiceImpl extends BaseService<BwXjbkCellBehavior, Long>
// implements BwXjbkCellBehaviorService {
//
// /**
// * @see com.waterelephant.service.BwXjbkCellBehaviorService#findByAttr(com.waterelephant.entity.BwXjbkCellBehavior)
// */
// @Override
// public List<BwXjbkCellBehavior> findByAttr(BwXjbkCellBehavior bwXjbkCellBehavior) {
// return mapper.select(bwXjbkCellBehavior);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkCellBehaviorService#save(com.waterelephant.entity.BwXjbkCellBehavior)
// */
// @Override
// public int save(BwXjbkCellBehavior bwXjbkCellBehavior) {
// return mapper.insert(bwXjbkCellBehavior);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkCellBehaviorService#update(com.waterelephant.entity.BwXjbkCellBehavior)
// */
// @Override
// public int update(BwXjbkCellBehavior bwXjbkCellBehavior) {
// return mapper.updateByPrimaryKey(bwXjbkCellBehavior);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkCellBehaviorService#del(com.waterelephant.entity.BwXjbkCellBehavior)
// */
// @Override
// public int del(BwXjbkCellBehavior bwXjbkCellBehavior) {
// return mapper.delete(bwXjbkCellBehavior);
// }
//
// @Override
// public String findBwXjbkCellBehaviorMonth(Long orderId, int num) {
// String sql = "select group_concat(a.month Separator ',') from "
// + "(select DISTINCT(cell_mth) month from bw_xjbk_cell_behavior where order_id = " + orderId
// + " and total_amount>0 order by cell_mth desc limit " + num + ") a";
// String monthStr = sqlMapper.selectOne(sql, String.class);
// return monthStr;
// }
//
// @Override
// public String findBwXjbkCellBehaviorAVG(Long orderId) {
//
// String sql = "select CONCAT(avg(total_amount)) from bw_xjbk_cell_behavior where order_id = " + orderId
// + " and total_amount>0";
// String avg = sqlMapper.selectOne(sql, String.class);
// return avg;
// }
//
// }
