//package com.waterelephant.sxyDrainage.mapper;
//
//import com.waterelephant.sxyDrainage.entity.jdq.BwJdqTransaction;
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
//public interface BwJdqTransactionMapper extends Mapper<BwJdqTransaction> {
//
//    @Delete("delete from bw_jdq_transaction where order_id = #{orderId}")
//    Integer deleteByOrderId(Long orderId);
//}
