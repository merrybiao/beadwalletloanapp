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
// import com.waterelephant.service.FqgjMsgBillService;
// import com.waterelephant.sxyDrainage.entity.fqgj.FqgjMsgBill;
//
/// **
// *
// *
// * Module:
// *
// * FqgjMsgBillServiceImpl.java
// *
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
// @Service
// public class FqgjMsgBillServiceImpl extends BaseService<FqgjMsgBill, Long> implements FqgjMsgBillService {
//
// /**
// *
// * @see com.waterelephant.service.FqgjMsgBillService#save(com.waterelephant.sxyDrainageJob.entity.fqgj.FqgjMsgBill)
// */
// @Override
// public Integer save(FqgjMsgBill fqgjMsgBill) {
// return mapper.insert(fqgjMsgBill);
//
// }
//
// /**
// *
// * @see
/// com.waterelephant.service.FqgjMsgBillService#updateByOrderId(com.waterelephant.sxyDrainageJob.entity.fqgj.FqgjMsgBill)
// */
// @Override
// public Integer updateByOrderId(FqgjMsgBill fqgjMsgBill) {
// return mapper.updateByPrimaryKey(fqgjMsgBill);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjMsgBillService#getFqgjMsgBillByOrderId(java.lang.Long)
// */
// @Override
// public FqgjMsgBill getFqgjMsgBillByOrderId(Long orderId) {
// String sql = "select * from bw_fqgj_msg_bill where order_id = " + orderId + " limit 1";
// return sqlMapper.selectOne(sql, FqgjMsgBill.class);
//
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjMsgBillService#getFqgjMsgBillList(java.lang.Long)
// */
// @Override
// public List<FqgjMsgBill> getFqgjMsgBillList(Long orderId) {
// String sql = "select * from bw_fqgj_msg_bill p where p.order_id = " + orderId;
// return sqlMapper.selectList(sql, FqgjMsgBill.class);
// }
//
// /**
// *
// * @see
/// com.waterelephant.service.FqgjMsgBillService#delFqgjMsgBill(com.waterelephant.sxyDrainage.entity.fqgj.FqgjMsgBill)
// */
// @Override
// public int delFqgjMsgBill(FqgjMsgBill fqgjMsgBill) {
// return mapper.delete(fqgjMsgBill);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjMsgBillService#delFqgjMsgBillByOrderId(java.lang.Long)
// */
// @Override
// public int delFqgjMsgBillByOrderId(Long orderId) {
// String sql = "delete from bw_fqgj_msg_bill where order_id=" + orderId;
// return sqlMapper.delete(sql);
// }
//
// }
