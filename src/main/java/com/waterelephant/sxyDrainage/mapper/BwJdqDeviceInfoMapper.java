//package com.waterelephant.sxyDrainage.mapper;
//
//import com.waterelephant.sxyDrainage.entity.jdq.DeviceInfo;
//
//import org.apache.ibatis.annotations.Select;
//
//import tk.mybatis.mapper.common.Mapper;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-11
// */
//
//public interface BwJdqDeviceInfoMapper extends Mapper<DeviceInfo> {
//
//    @Select("select * from bw_jdq_device_info where order_id = #{orderId}")
//    DeviceInfo findByOrderId(Long orderId);
//}
