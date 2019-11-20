// package com.waterelephant.service.impl;
//
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.FqgjDeviceInfoService;
// import com.waterelephant.sxyDrainage.entity.fqgj.FqgjDeviceInfo;
// import org.springframework.stereotype.Service;
//
/// **
// * @author wangfei
// * @version 1.0
// * @date 2018/6/14
// * @Description <TODO>
// * @since JDK 1.8
// */
// @Service
// public class FqgjDeviceInfoServiceImpl extends BaseService<FqgjDeviceInfo,Long> implements FqgjDeviceInfoService {
// @Override
// public Integer save(FqgjDeviceInfo fqgjDeviceInfo) {
// return mapper.insert(fqgjDeviceInfo);
// }
//
// @Override
// public FqgjDeviceInfo getFqgjDeviceInfoByOrderId(Long orderId) {
// String sql = "select * from bw_fqgj_device_info p where p.order_id = " + orderId +" limit 1";
// return sqlMapper.selectOne(sql,FqgjDeviceInfo.class);
// }
//
// @Override
// public Integer updateFqgjDeviceInfo(FqgjDeviceInfo fqgjDeviceInfo) {
// return mapper.updateByPrimaryKey(fqgjDeviceInfo);
// }
// }
