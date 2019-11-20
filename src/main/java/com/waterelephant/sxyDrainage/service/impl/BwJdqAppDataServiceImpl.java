//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.waterelephant.service.BaseService;
//import com.waterelephant.sxyDrainage.entity.jdq.AppData;
//import com.waterelephant.sxyDrainage.mapper.BwJdqAppDataMapper;
//import com.waterelephant.sxyDrainage.service.BwJdqAppDataService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-18
// */
//@Service
//public class BwJdqAppDataServiceImpl extends BaseService<AppData, Long> implements BwJdqAppDataService {
//
//    @Autowired
//    private BwJdqAppDataMapper bwJdqAppDataMapper;
//
//    @Override
//    public Integer saveOrUpdate(AppData data) {
//        bwJdqAppDataMapper.delete(data);
//        Date date = new Date();
//        data.setGmtModified(date);
//        data.setGmtCreate(date);
//        return bwJdqAppDataMapper.insert(data);
//    }
//}
