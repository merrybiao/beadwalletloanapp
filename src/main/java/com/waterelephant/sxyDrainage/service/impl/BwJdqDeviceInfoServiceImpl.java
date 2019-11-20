//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.waterelephant.service.BaseService;
//import com.waterelephant.sxyDrainage.entity.jdq.DeviceInfo;
//import com.waterelephant.sxyDrainage.mapper.BwJdqDeviceInfoMapper;
//import com.waterelephant.sxyDrainage.service.BwJdqDeviceInfoService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-11
// */
//@Service
//public class BwJdqDeviceInfoServiceImpl extends BaseService<DeviceInfo, Long>
//        implements BwJdqDeviceInfoService {
//
//    @Autowired
//    private BwJdqDeviceInfoMapper bwJdqDeviceInfoMapper;
//
//    @Override
//    public Integer saveOrUpdate(DeviceInfo data) {
//        DeviceInfo deviceInfo = bwJdqDeviceInfoMapper.findByOrderId(data.getOrderId());
//
//        //更新时间
//        Date date = new Date();
//        data.setGmtModified(date);
//        if (deviceInfo != null) {
//            data.setId(deviceInfo.getId());
//            return bwJdqDeviceInfoMapper.updateByPrimaryKeySelective(data);
//        }
//        data.setGmtCreate(date);
//        return bwJdqDeviceInfoMapper.insert(data);
//    }
//}
