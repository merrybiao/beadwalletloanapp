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
// import com.waterelephant.service.FqgjCallService;
// import com.waterelephant.sxyDrainage.entity.fqgj.FqgjCall;
//
/// **
// *
// *
// * Module:
// *
// * FqgjCallServiceImpl.java
// *
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
// @Service
// public class FqgjCallServiceImpl extends BaseService<FqgjCall, Long> implements FqgjCallService {
//
// /**
// *
// * @see com.waterelephant.service.FqgjCallService#save(com.waterelephant.sxyDrainageJob.entity.fqgj.FqgjCall)
// */
// @Override
// public Integer save(FqgjCall fqgjCall) {
// return mapper.insert(fqgjCall);
// }
//
// /**
// *
// * @see
/// com.waterelephant.service.FqgjCallService#updateByOrderId(com.waterelephant.sxyDrainageJob.entity.fqgj.FqgjCall)
// */
// @Override
// public Integer updateByOrderId(FqgjCall fqgjCall) {
// return mapper.updateByPrimaryKey(fqgjCall);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjCallService#getFqgjCallByOrderId(java.lang.Long)
// */
// @Override
// public FqgjCall getFqgjCallByOrderId(Long orderId) {
// String sql = "select * from bw_fqgj_call where order_id = " + orderId + " limit 1";
// return sqlMapper.selectOne(sql, FqgjCall.class);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjCallService#getFqgjCallList(java.lang.Long)
// */
// @Override
// public List<FqgjCall> getFqgjCallList(Long orderId) {
// String sql = "select * from bw_fqgj_msg_bill p where p.order_id = " + orderId;
// return sqlMapper.selectList(sql, FqgjCall.class);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjCallService#delFqgjCall(com.waterelephant.sxyDrainage.entity.fqgj.FqgjCall)
// */
// @Override
// public int delFqgjCall(FqgjCall fqgjCall) {
// return mapper.delete(fqgjCall);
// }
//
// /**
// *
// * @see com.waterelephant.service.FqgjCallService#delFqgjCallByOrderId(java.lang.Long)
// */
// @Override
// public int delFqgjCallByOrderId(Long orderId) {
//
// String sql = "delete from bw_fqgj_call where order_id=" + orderId;
//
// return sqlMapper.delete(sql);
//
// }
//
// }
