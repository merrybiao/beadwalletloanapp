package com.waterelephant.mapper;

import com.waterelephant.dto.RepaymentBatch;
import com.waterelephant.dto.RepaymentBatchDetail;
import com.waterelephant.entity.BwOrderRepaymentBatchDetail;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BwOrderRepaymentBatchDetailMapper extends Mapper<BwOrderRepaymentBatchDetail> {

    RepaymentBatch getRepaymentBatch(Long orderId);

    Double getBatchDetailTotal(Long orderId);

    /**
     * 查询工单已还金额
     *
     * @param orderId
     * @return
     */
    Double selectHasRepaymentMoney(Long orderId);

    List<RepaymentBatchDetail> getRepaymentBatchDetailList(Long orderId);
}
