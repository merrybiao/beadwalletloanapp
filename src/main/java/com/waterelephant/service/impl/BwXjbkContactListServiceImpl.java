/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkContactList;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkContactListService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkContactListServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkContactListServiceImpl extends BaseService<BwXjbkContactList, Long>
// implements BwXjbkContactListService {
//
// /**
// * @see com.waterelephant.service.BwXjbkContactListService#findByAttr(com.waterelephant.entity.BwXjbkContactList)
// */
// @Override
// public List<BwXjbkContactList> findByAttr(BwXjbkContactList bwXjbkContactList) {
// return mapper.select(bwXjbkContactList);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkContactListService#save(com.waterelephant.entity.BwXjbkContactList)
// */
// @Override
// public int save(BwXjbkContactList bwXjbkContactList) {
// return insert(bwXjbkContactList);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkContactListService#update(com.waterelephant.entity.BwXjbkContactList)
// */
// @Override
// public int update(BwXjbkContactList bwXjbkContactList) {
// return updateByPrimaryKey(bwXjbkContactList);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkContactListService#del(com.waterelephant.entity.BwXjbkContactList)
// */
// @Override
// public int del(BwXjbkContactList bwXjbkContactList) {
// return mapper.delete(bwXjbkContactList);
// }
//
// @Override
// public List<BwXjbkContactList> findBwXjbkContactList(Long orderId, String phones) {
// String sql = "select * from bw_xjbk_contact_list where order_id = " + orderId + " and phone_num in (" + phones
// + ")";
// return sqlMapper.selectList(sql, BwXjbkContactList.class);
// }
//
// }
