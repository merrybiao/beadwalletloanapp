//package com.waterelephant.sxyDrainage.mapper;
//
//import com.waterelephant.sxyDrainage.entity.dkdh360.BwDkdh360DeviceInfo;
//import org.apache.ibatis.annotations.Select;
//import org.springframework.stereotype.Repository;
//import tk.mybatis.mapper.common.Mapper;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/27 15:08
// * @Description: 设备信息Mapper
// */
//@Repository
//public interface BwDkdh360DeviceInfoMapper extends Mapper<BwDkdh360DeviceInfo> {
//
//    /**
//     * 根据订单ID查询
//     *
//     * @param orderId 订单ID
//     * @return BwDkdh360DeviceInfo
//     */
//    @Select("select * from bw_dkdh360_device_info where order_id = #{orderId}")
//    BwDkdh360DeviceInfo findByOrderId(Long orderId);
//}
