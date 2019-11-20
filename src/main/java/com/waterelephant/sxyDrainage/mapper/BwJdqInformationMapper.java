//package com.waterelephant.sxyDrainage.mapper;
//
//import com.waterelephant.sxyDrainage.entity.jdq.BwJdqInformation;
//
//import org.apache.ibatis.annotations.Delete;
//
//import tk.mybatis.mapper.common.Mapper;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-11
// */
//
//public interface BwJdqInformationMapper extends Mapper<BwJdqInformation> {
//
//    @Delete("delete from bw_jdq_information where order_id = #{orderId}")
//    Integer deleteByOrderId(Long orderId);
//}
