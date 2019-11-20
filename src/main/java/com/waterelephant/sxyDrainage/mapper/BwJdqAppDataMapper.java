//package com.waterelephant.sxyDrainage.mapper;
//
//import com.waterelephant.sxyDrainage.entity.jdq.AppData;
//
//import org.apache.ibatis.annotations.Select;
//
//import java.util.List;
//
//import tk.mybatis.mapper.common.Mapper;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-18
// */
//
//public interface BwJdqAppDataMapper extends Mapper<AppData> {
//
//    @Select("select * from bw_jdq_app_data where order_id = #{orderId}")
//    List<AppData> findByOrderId(Long orderId);
//}
