/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkCollectionContact;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkCollectionContactService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkCollectionContactServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkCollectionContactServiceImpl extends BaseService<BwXjbkCollectionContact, Long>
// implements BwXjbkCollectionContactService {
//
// /**
// * @see
/// com.waterelephant.service.BwXjbkCollectionContactService#findByAttr(com.waterelephant.entity.BwXjbkCollectionContact)
// */
// @Override
// public List<BwXjbkCollectionContact> findByAttr(BwXjbkCollectionContact bwXjbkCollectionContact) {
// return mapper.select(bwXjbkCollectionContact);
// }
//
// /**
// * @see
/// com.waterelephant.service.BwXjbkCollectionContactService#save(com.waterelephant.entity.BwXjbkCollectionContact)
// */
// @Override
// public int save(BwXjbkCollectionContact bwXjbkCollectionContact) {
// return mapper.insert(bwXjbkCollectionContact);
// }
//
// /**
// * @see
/// com.waterelephant.service.BwXjbkCollectionContactService#update(com.waterelephant.entity.BwXjbkCollectionContact)
// */
// @Override
// public int update(BwXjbkCollectionContact bwXjbkCollectionContact) {
// return mapper.updateByPrimaryKey(bwXjbkCollectionContact);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkCollectionContactService#del(com.waterelephant.entity.BwXjbkCollectionContact)
// */
// @Override
// public int del(BwXjbkCollectionContact bwXjbkCollectionContact) {
// return mapper.delete(bwXjbkCollectionContact);
// }
//
// }
