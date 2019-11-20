/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import java.util.List;
//
// import org.springframework.stereotype.Service;
//
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.FqgjPhoneBillService;
// import com.waterelephant.sxyDrainage.entity.fqgj.FqgjPhoneBill;
//
/// **
// *
// *
// * Module:
// *
// * FqgjPhoneBillServiceImpl.java
// *
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
// @Service
// public class FqgjPhoneBillServiceImpl extends BaseService<FqgjPhoneBill, Long> implements FqgjPhoneBillService {
//
// /**
// *
// * @see
/// com.waterelephant.service.FqgjPhoneBillService#save(com.waterelephant.sxyDrainageJob.entity.fqgj.FqgjPhoneBill)
// */
// @Override
// public Integer save(FqgjPhoneBill fqgjPhoneBill) {
// return mapper.insert(fqgjPhoneBill);
// }
//
// /**
// *
// * @see
/// com.waterelephant.service.FqgjPhoneBillService#updateByOrderId(com.waterelephant.sxyDrainageJob.entity.fqgj.FqgjPhoneBill)
// */
// @Override
// public Integer updateByOrderId(FqgjPhoneBill fqgjPhoneBill) {
// return mapper.updateByPrimaryKey(fqgjPhoneBill);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjPhoneBillService#getFqgjPhoneBillByOrderId(java.lang.Long)
// */
// @Override
// public FqgjPhoneBill getFqgjPhoneBillByOrderId(Long orderId) {
// String sql = "select * from bw_fqgj_phone_bill where order_id = " + orderId + " limit 1";
// return sqlMapper.selectOne(sql, FqgjPhoneBill.class);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjPhoneBillService#getFqgjPhoneBillList(java.lang.Long)
// */
// @Override
// public List<FqgjPhoneBill> getFqgjPhoneBillList(Long orderId) {
// String sql = "select * from bw_fqgj_phone_bill p where p.order_id = " + orderId;
// return sqlMapper.selectList(sql, FqgjPhoneBill.class);
// }
//
// /**
// *
// * @see
/// com.waterelephant.service.FqgjPhoneBillService#delFqgjPhoneBill(com.waterelephant.sxyDrainage.entity.fqgj.FqgjPhoneBill)
// */
// @Override
// public int delFqgjPhoneBill(FqgjPhoneBill fqgjPhoneBill) {
// return mapper.delete(fqgjPhoneBill);
//
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjPhoneBillService#delFqgjPhoneBillByOrderId(java.lang.Long)
// */
// @Override
// public int delFqgjPhoneBillByOrderId(Long orderId) {
// String sql = "delete from bw_fqgj_phone_bill where order_id=" + orderId;
//
// return sqlMapper.delete(sql);
// }
//
// }
