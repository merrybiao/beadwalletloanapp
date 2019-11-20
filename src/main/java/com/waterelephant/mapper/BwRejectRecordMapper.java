package com.waterelephant.mapper;

import com.waterelephant.entity.BwRejectRecord;

import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;

public interface BwRejectRecordMapper extends Mapper<BwRejectRecord> {

    @Delete("delete from bw_reject_record where order_id=#{orderId}")
    int deleteByOrderId(Long orderId);

}
