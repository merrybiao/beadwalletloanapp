///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import com.waterelephant.service.BaseService;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdMobileSrc;
//import com.waterelephant.sxyDrainage.service.TodHdMobileSrcService;
//
///**
// * TodHdMobileSrcServiceImpl.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月19日
// * @Description: <TODO>
// * 
// */
//@Service
//public class TodHdMobileSrcServiceImpl extends BaseService<TodHdMobileSrc, Long> implements TodHdMobileSrcService {
//
//	@Override
//	public Integer save(TodHdMobileSrc hdMobileSrc) {
//		return mapper.insert(hdMobileSrc);
//	}
//
//	@Override
//	public Integer delAllByOrderId(Long orderId) {
//		String sql = "delete from bw_haodai_mobile where order_id = " + orderId;
//		return sqlMapper.delete(sql);
//	}
//
//}
