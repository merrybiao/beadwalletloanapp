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
// import com.waterelephant.service.FqgjRechargeService;
// import com.waterelephant.sxyDrainage.entity.fqgj.FqgjRecharge;
//
/// **
// *
// *
// * Module:
// *
// * FqgjRechargeServiceImpl.java
// *
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
// @Service
// public class FqgjRechargeServiceImpl extends BaseService<FqgjRecharge, Long> implements FqgjRechargeService {
//
// /**
// *
// * @see com.waterelephant.service.FqgjRechargeService#save(com.waterelephant.sxyDrainageJob.entity.fqgj.FqgjRecharge)
// */
// @Override
// public Integer save(FqgjRecharge fqgjRecharge) {
// return mapper.insert(fqgjRecharge);
// }
//
// /**
// *
// * @see
/// com.waterelephant.service.FqgjRechargeService#updateByOrderId(com.waterelephant.sxyDrainageJob.entity.fqgj.FqgjRecharge)
// */
// @Override
// public Integer updateByOrderId(FqgjRecharge fqgjRecharge) {
// return mapper.updateByPrimaryKey(fqgjRecharge);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjRechargeService#getFqgjRechargeByOrderId(java.lang.Long)
// */
// @Override
// public FqgjRecharge getFqgjRechargeByOrderId(Long orderId) {
// String sql = "select * from bw_fqgj_recharge where order_id = " + orderId + " limit 1";
// return sqlMapper.selectOne(sql, FqgjRecharge.class);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjRechargeService#getFqgjRechargeList(java.lang.Long)
// */
// @Override
// public List<FqgjRecharge> getFqgjRechargeList(Long orderId) {
// String sql = "select * from bw_fqgj_recharge p where p.order_id = " + orderId;
// return sqlMapper.selectList(sql, FqgjRecharge.class);
// }
//
// /**
// *
// * @see
/// com.waterelephant.service.FqgjRechargeService#delFqgjRecharge(com.waterelephant.sxyDrainage.entity.fqgj.FqgjRecharge)
// */
// @Override
// public int delFqgjRecharge(FqgjRecharge fqgjRecharge) {
//
// return mapper.delete(fqgjRecharge);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjRechargeService#delFqgjRechargeByOrderId(java.lang.Long)
// */
// @Override
// public int delFqgjRechargeByOrderId(Long orderId) {
// String sql = "delete from bw_fqgj_recharge where order_id=" + orderId;
// return sqlMapper.delete(sql);
// }
//
// }
