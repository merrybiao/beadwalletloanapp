/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkTripInfo;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkTripInfoService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkTripInfoServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkTripInfoServiceImpl extends BaseService<BwXjbkTripInfo, Long> implements BwXjbkTripInfoService {
//
// /**
// * @see com.waterelephant.service.BwXjbkTripInfoService#findByAttr(com.waterelephant.entity.BwXjbkTripInfo)
// */
// @Override
// public List<BwXjbkTripInfo> findByAttr(BwXjbkTripInfo bwXjbkTripInfo) {
// return mapper.select(bwXjbkTripInfo);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkTripInfoService#save(com.waterelephant.entity.BwXjbkTripInfo)
// */
// @Override
// public int save(BwXjbkTripInfo bwXjbkTripInfo) {
// return mapper.insert(bwXjbkTripInfo);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkTripInfoService#update(com.waterelephant.entity.BwXjbkTripInfo)
// */
// @Override
// public int update(BwXjbkTripInfo bwXjbkTripInfo) {
// return mapper.updateByPrimaryKey(bwXjbkTripInfo);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkTripInfoService#del(com.waterelephant.entity.BwXjbkTripInfo)
// */
// @Override
// public int del(BwXjbkTripInfo bwXjbkTripInfo) {
// return mapper.delete(bwXjbkTripInfo);
// }
//
// }
