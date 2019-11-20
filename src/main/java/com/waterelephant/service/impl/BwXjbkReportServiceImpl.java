/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkReport;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwXjbkReportService;
// import org.springframework.stereotype.Service;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkReportServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Service
// public class BwXjbkReportServiceImpl extends BaseService<BwXjbkReport, Long> implements BwXjbkReportService {
//
// /**
// * @see com.waterelephant.service.BwXjbkReportService#findByAttr(com.waterelephant.entity.BwXjbkReport)
// */
// @Override
// public BwXjbkReport findByAttr(BwXjbkReport bwXjbkReport) {
// return mapper.selectOne(bwXjbkReport);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkReportService#save(com.waterelephant.entity.BwXjbkReport)
// */
// @Override
// public int save(BwXjbkReport bwXjbkReport) {
// return mapper.insert(bwXjbkReport);
// }
//
// /**
// * @see com.waterelephant.service.BwXjbkReportService#update(com.waterelephant.entity.BwXjbkReport)
// */
// @Override
// public int update(BwXjbkReport bwXjbkReport) {
// return mapper.updateByPrimaryKey(bwXjbkReport);
// }
//
// }
