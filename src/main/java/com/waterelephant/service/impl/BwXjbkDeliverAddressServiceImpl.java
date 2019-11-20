/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkDeliverAddress;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkDeliverAddressService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkDeliverAddressServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkDeliverAddressServiceImpl extends BaseService<BwXjbkDeliverAddress, Long>
// implements BwXjbkDeliverAddressService {
//
// /**
// * @see
/// com.waterelephant.service.BwXjbkDeliverAddressService#findByAttr(com.waterelephant.entity.BwXjbkDeliverAddress)
// */
// @Override
// public List<BwXjbkDeliverAddress> findByAttr(BwXjbkDeliverAddress bwXjbkDeliverAddress) {
// return mapper.select(bwXjbkDeliverAddress);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkDeliverAddressService#save(com.waterelephant.entity.BwXjbkDeliverAddress)
// */
// @Override
// public int save(BwXjbkDeliverAddress bwXjbkDeliverAddress) {
// return mapper.insert(bwXjbkDeliverAddress);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkDeliverAddressService#update(com.waterelephant.entity.BwXjbkDeliverAddress)
// */
// @Override
// public int update(BwXjbkDeliverAddress bwXjbkDeliverAddress) {
// return mapper.updateByPrimaryKey(bwXjbkDeliverAddress);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkDeliverAddressService#del(com.waterelephant.entity.BwXjbkDeliverAddress)
// */
// @Override
// public int del(BwXjbkDeliverAddress bwXjbkDeliverAddress) {
// return mapper.delete(bwXjbkDeliverAddress);
// }
//
// }
