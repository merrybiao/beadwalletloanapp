/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkApplicationCheck;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkApplicationCheckService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkApplicationCheckServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkApplicationCheckServiceImpl extends BaseService<BwXjbkApplicationCheck, Long>
// implements BwXjbkApplicationCheckService {
//
// /**
// * @see
/// com.waterelephant.service.BwXjbkApplicationCheckService#findByAttr(com.waterelephant.entity.BwXjbkApplicationCheck)
// */
// @Override
// public List<BwXjbkApplicationCheck> findByAttr(BwXjbkApplicationCheck bwXjbkApplicationCheck) {
// return mapper.select(bwXjbkApplicationCheck);
// }
//
// /**
// * @see
/// com.waterelephant.service.BwXjbkApplicationCheckService#update(com.waterelephant.entity.BwXjbkApplicationCheck)
// */
// @Override
// public int update(BwXjbkApplicationCheck bwXjbkApplicationCheck) {
// return mapper.updateByPrimaryKey(bwXjbkApplicationCheck);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkApplicationCheckService#save(com.waterelephant.entity.BwXjbkApplicationCheck)
// */
// @Override
// public int save(BwXjbkApplicationCheck bwXjbkApplicationCheck) {
// return mapper.insert(bwXjbkApplicationCheck);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkApplicationCheckService#del(com.waterelephant.entity.BwXjbkApplicationCheck)
// */
// @Override
// public int del(BwXjbkApplicationCheck bwXjbkApplicationCheck) {
// return mapper.delete(bwXjbkApplicationCheck);
// }
//
// @Override
// public BwXjbkApplicationCheck findBwXjbkApplicationCheck(Long orderId, String appPoint) {
//
// String sql = "select * from bw_xjbk_application_check where order_id = " + orderId + " and app_point='"
// + appPoint + "' order by id desc limit 1 ";
// return sqlMapper.selectOne(sql, BwXjbkApplicationCheck.class);
// }
//
// }
