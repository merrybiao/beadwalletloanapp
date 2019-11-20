/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkBehaviorCheck;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkBehaviorCheckService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkBehaviorCheckServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkBehaviorCheckServiceImpl extends BaseService<BwXjbkBehaviorCheck, Long>
// implements BwXjbkBehaviorCheckService {
//
// /**
// * @see com.waterelephant.service.BwXjbkBehaviorCheckService#findByAttr(com.waterelephant.entity.BwXjbkBehaviorCheck)
// */
// @Override
// public List<BwXjbkBehaviorCheck> findByAttr(BwXjbkBehaviorCheck bwXjbkBehaviorCheck) {
// return mapper.select(bwXjbkBehaviorCheck);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkBehaviorCheckService#save(com.waterelephant.entity.BwXjbkBehaviorCheck)
// */
// @Override
// public int save(BwXjbkBehaviorCheck bwXjbkBehaviorCheck) {
// return mapper.insert(bwXjbkBehaviorCheck);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkBehaviorCheckService#update(com.waterelephant.entity.BwXjbkBehaviorCheck)
// */
// @Override
// public int update(BwXjbkBehaviorCheck bwXjbkBehaviorCheck) {
// return mapper.updateByPrimaryKey(bwXjbkBehaviorCheck);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkBehaviorCheckService#del(com.waterelephant.entity.BwXjbkBehaviorCheck)
// */
// @Override
// public int del(BwXjbkBehaviorCheck bwXjbkBehaviorCheck) {
// return mapper.delete(bwXjbkBehaviorCheck);
// }
//
// @Override
// public BwXjbkBehaviorCheck findBwXjbkBehaviorCheck(Long orderId, String checkPoint) {
//
// String sql = "select * from bw_xjbk_behavior_check where order_id = " + orderId + " and check_point='"
// + checkPoint + "' order by id desc limit 1 ";
// return sqlMapper.selectOne(sql, BwXjbkBehaviorCheck.class);
// }
//
// }
